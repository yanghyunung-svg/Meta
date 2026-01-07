package com.meta.service;

import ch.qos.logback.core.util.StringUtil;
import com.meta.common.constants.BizConstants;
import com.meta.common.constants.ResponseCode;
import com.meta.common.exception.BizException;
import com.meta.common.util.BizUtils;
import com.meta.dto.TbStdWordBscDto;
import com.meta.mapper.TbStdWordBscMapper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/** 
 *@ ID       : StdWordBscService
 *@ NAME     : 단어사전 Service
 */
@Service
public class StdWordBscService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TbStdWordBscMapper wordMapper;


    /**
     * method   : getListData
     * desc     : 단어사전 목록 조회
     */
    public List<TbStdWordBscDto> getListData(TbStdWordBscDto inputDto)  {
        return wordMapper.getListData(inputDto);
    }

    /**
     * method   : getData
     * desc     : 단어사전 상세 조회
     */
    public TbStdWordBscDto getData(TbStdWordBscDto inputDto)  {
        return wordMapper.getData(inputDto);
    }
    /**
     * method   : getDataByName
     * desc     : 단어사전 단어명 상세 조회
     */
    public TbStdWordBscDto getDataByName(TbStdWordBscDto inputDto)  {
        return wordMapper.getDataByName(inputDto);
    }

    /**
     * method   : manageData
     * desc     : 단어사전 관리
     */
    public void manageData(TbStdWordBscDto inputDto)  {
        log.debug(BizUtils.logInfo("START"));
        log.debug(BizUtils.logVoKey(inputDto));
        TbStdWordBscDto outputDto = wordMapper.getLockData(inputDto);

        switch (inputDto.getFunc()) {
            case BizConstants.FUNC_SE.INS:
                if (outputDto != null) {
                    throw new BizException(ResponseCode.DUPLICATE_DATA);
                }
                if (wordMapper.insertData(inputDto) == 0) {
                    throw new BizException(ResponseCode.INSERT_FAILED);
                }
                break;
            case BizConstants.FUNC_SE.UPD:
                if (outputDto == null) {
                    throw new BizException(ResponseCode.DATA_NOT_FOUND);
                }
                if (wordMapper.updateData(inputDto) == 0) {
                    throw new BizException(ResponseCode.UPDATE_FAILED);
                }
                break;
            case BizConstants.FUNC_SE.DEL:
                if (outputDto == null) {
                    throw new BizException(ResponseCode.DATA_NOT_FOUND);
                }
                if (wordMapper.deleteData(inputDto) == 0) {
                    throw new BizException(ResponseCode.DELETE_FAILED);
                }
                break;
            default:
                throw new BizException(ResponseCode.VALIDATION_FAILED);
        }
        log.debug(BizUtils.logInfo("END"));
    }

    /**
     * @ ID : parseExcelPreview
     * @ NAME : 표준단어 엑셀업로드
     */
    public List<TbStdWordBscDto> parseExcelPreview(MultipartFile file) throws Exception {

        List<TbStdWordBscDto> result = new ArrayList<>();
        Workbook workbook = WorkbookFactory.create(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null) continue;

            TbStdWordBscDto dto = new TbStdWordBscDto();

            dto.setWordNm       (BizUtils.getCell(row, 1));
            dto.setEngAbbrNm    (BizUtils.getCell(row, 2));
            dto.setEngNm        (BizUtils.getCell(row, 3));
            dto.setExpln        (BizUtils.getCell(row, 4));
            dto.setStat         (BizUtils.getCell(row, 5));
            dto.setCrtId        (BizUtils.getCell(row, 6));

            // 검증 로직
            String error = validateRow(dto);
            if (StringUtil.notNullNorEmpty(error))  dto.setStat(error);

            result.add(dto);
        }
        return result;
    }

    private String validateRow(TbStdWordBscDto dto) {
        if (dto.getWordNm() == null || dto.getWordNm().isEmpty()) return "단어명 누락";
        if (dto.getEngAbbrNm() == null || dto.getEngAbbrNm().isEmpty()) return "영문약어명 누락";
        if (dto.getEngNm() == null || dto.getEngNm().isEmpty()) return "영문명 누락";
        if (dto.getExpln() == null || dto.getExpln().isEmpty()) return "설명 누락";
        // DB 중복 체크
        int exists = wordMapper.countCode(dto);
        if (exists > 0) return "이미 존재하는 코드";
        return "0";  // 정상
    }

    /**
     * @ ID : saveUploadedExcel
     * @ NAME : 표준단어 엑셀업로드 저장
     */
    public int saveUploadedExcel(List<TbStdWordBscDto> list) {
        int count = 0;
        for (TbStdWordBscDto dto : list) {
            if(StringUtils.equals(dto.getStat(), "0")) {
                dto.setUpdId(dto.getCrtId());
                wordMapper.insertData(dto);
                count++;
            }
        }
        return count;
    }
 
}

