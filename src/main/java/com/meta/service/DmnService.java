package com.meta.service;

import ch.qos.logback.core.util.StringUtil;
import com.meta.common.constants.BizConstants;
import com.meta.common.constants.ResponseCode;
import com.meta.common.exception.BizException;
import com.meta.common.util.BizUtils;
import com.meta.dto.TbStdDmnBscDto;
import com.meta.mapper.dbio.TbStdDmnBscMapper;
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
 *@ ID       : DmnService
 *@ NAME     : 표준도메인 Service
 */
@Service
public class DmnService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private TbStdDmnBscMapper dmnMapper;


    /**
     * method   : getListData
     * desc     : 표준도메인 목록 조회
     */
    public List<TbStdDmnBscDto> getListData(TbStdDmnBscDto inputDto)  {
        log.debug(BizUtils.logInfo());
        return dmnMapper.getListData(inputDto);
    }

    /**
     * method   : getData
     * desc     : 표준도메인 상세 조회
     */
    public List<TbStdDmnBscDto> getDmnComboData()  {
        log.debug(BizUtils.logInfo());
        return dmnMapper.getDmnComboData();
    }

    /**
     * method   : getData
     * desc     : 표준도메인 상세 조회
     */
    public TbStdDmnBscDto getData(TbStdDmnBscDto inputDto)  {
        log.debug(BizUtils.logInfo());
        return dmnMapper.getData(inputDto);
    }

    /**
     * method   : manageData
     * desc     : 표준도메인 관리
     */
    public void manageData(TbStdDmnBscDto inputDto)  {
        log.debug(BizUtils.logInfo("START"));
        log.debug(BizUtils.logVoKey(inputDto));
        TbStdDmnBscDto outputDto = dmnMapper.getLockData(inputDto); 

        switch (inputDto.getFunc()) {
            case BizConstants.FUNC_SE.INS:
                if (outputDto  != null) {
                    throw new BizException(ResponseCode.DUPLICATE_DATA);
                }
                if (dmnMapper.insertData(inputDto) == 0) {
                    throw new BizException(ResponseCode.INSERT_FAILED);
                }
                break;
            case BizConstants.FUNC_SE.UPD:
                if (outputDto == null) {
                    throw new BizException(ResponseCode.DATA_NOT_FOUND);
                }
                if (dmnMapper.updateData(inputDto) == 0) {
                    throw new BizException(ResponseCode.UPDATE_FAILED);
                }
                break;
            case BizConstants.FUNC_SE.DEL:
                if (outputDto == null) {
                    throw new BizException(ResponseCode.DATA_NOT_FOUND);
                }
                if (dmnMapper.deleteData(inputDto) == 0) {
                    throw new BizException(ResponseCode.DELETE_FAILED);
                }
                break;
            default:
                throw new BizException(ResponseCode.VALIDATION_FAILED);
        }
        log.debug(BizUtils.logInfo("END")); 
    }

    /**
     * @ ID : parsePreview
     * @ NAME : 표준도메인 엑셀업로드
     */
    public List<TbStdDmnBscDto> parsePreview(MultipartFile file) throws Exception {
        log.debug(BizUtils.logInfo());
        List<TbStdDmnBscDto> result = new ArrayList<>();
        Workbook workbook = WorkbookFactory.create(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null) continue;

            TbStdDmnBscDto dto = new TbStdDmnBscDto();

            dto.setDmnNm        (BizUtils.getCell(row, 1));
            dto.setDmnClsfNm    (BizUtils.getCell(row, 2));
            dto.setDmnEngNm     (BizUtils.getCell(row, 3));
            dto.setDmnAtrb      (BizUtils.getCell(row, 4));
            dto.setSttsCd       (BizUtils.getCell(row, 5));
            dto.setCrtId        (BizUtils.getCell(row, 6));

            // 검증 로직
            String error = validateRow(dto);
            if (StringUtil.notNullNorEmpty(error))  dto.setSttsCd(error);

            result.add(dto);
        }
        return result;
    }

    private String validateRow(TbStdDmnBscDto dto) {
        if (dto.getDmnNm() == null || dto.getDmnNm().isEmpty()) return "도메인명 누락";
        if (dto.getDmnClsfNm() == null || dto.getDmnClsfNm().isEmpty()) return "도메인분류명 누락";
        if (dto.getDmnEngNm() == null || dto.getDmnEngNm().isEmpty()) return "도메인영문명 누락";
        if (dto.getDmnAtrb() == null || dto.getDmnAtrb().isEmpty()) return "도메인속성 누락";
        // DB 중복 체크
        int exists = dmnMapper.countData(dto);
        if (exists > 0) return "이미 존재하는 코드";
        return null;  // 정상
    }

    public int saveUploaded(List<TbStdDmnBscDto> list) {
        log.debug(BizUtils.logInfo());
        int count = 0;
        for (TbStdDmnBscDto dto : list) {
            if(StringUtils.equals(dto.getSttsCd(), "0")) {
                dto.setUpdId(dto.getCrtId());
                dmnMapper.insertData(dto);
                count++;
            }
        }
        return count;
    }

}

