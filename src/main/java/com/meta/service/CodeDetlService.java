package com.meta.service;

import ch.qos.logback.core.util.StringUtil;
import com.meta.common.constants.BizConstants;
import com.meta.common.constants.ResponseCode;
import com.meta.common.exception.BizException;
import com.meta.common.util.BizUtils;
import com.meta.dto.TbCodeDetlDto;
import com.meta.mapper.dbio.TbCodeGroupMapper;
import com.meta.mapper.dbio.TbCodeDetlMapper;
import com.meta.mapper.meta.CodeMapper;
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
 *@ ID       : CodeDetlService
 *@ NAME     : 상세코드 Service
 */
@Service
public class CodeDetlService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private TbCodeDetlMapper tbCodeDetlMapper;
    @Autowired
    private CodeMapper codeMapper;
    @Autowired
    private TbCodeGroupMapper tbCodeGroupMapper;


    /**
     *@ ID   : getListData
     *@ NAME : 상세코드 목록 조회
     */
    public List<TbCodeDetlDto> getListData(TbCodeDetlDto inputDto)  {
        log.debug(BizUtils.logInfo());
        return tbCodeDetlMapper.getListData(inputDto);
    }

    /**
     *@ ID   : getData
     *@ NAME     : 상세코드 조회
     */
    public TbCodeDetlDto getData(TbCodeDetlDto inputDto)  {
        log.debug(BizUtils.logInfo());
        return tbCodeDetlMapper.getData(inputDto);
    }

    /**
     * method   : manageData
     * desc     : 상세코드 관리
     */
    public void manageData(TbCodeDetlDto inputDto) {
        log.debug(BizUtils.logInfo("START"));
        log.debug(BizUtils.logVoKey(inputDto));
        TbCodeDetlDto outputDto = tbCodeDetlMapper.getLockData(inputDto);

        switch (inputDto.getFunc()) {
            case BizConstants.FUNC_SE.INS:
                if (outputDto  != null) {
                    throw new BizException(ResponseCode.DUPLICATE_DATA);
                }
                if (tbCodeDetlMapper.insertData(inputDto) == 0) {
                    throw new BizException(ResponseCode.INSERT_FAILED);
                }
                break;
            case BizConstants.FUNC_SE.UPD:
                if (outputDto == null) {
                    throw new BizException(ResponseCode.DATA_NOT_FOUND);
                }
                if (tbCodeDetlMapper.updateData(inputDto) == 0) {
                    throw new BizException(ResponseCode.UPDATE_FAILED);
                }
                break;
            case BizConstants.FUNC_SE.DEL:
                if (outputDto == null) {
                    throw new BizException(ResponseCode.DATA_NOT_FOUND);
                }
                if (tbCodeDetlMapper.deleteData(inputDto) == 0) {
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
     * @ NAME : 상세코드 엑셀업로드
     */
    public List<TbCodeDetlDto> parsePreview(MultipartFile file) throws Exception {
        log.debug(BizUtils.logInfo());
        List<TbCodeDetlDto> result = new ArrayList<>();
        Workbook workbook = WorkbookFactory.create(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null) continue;

            TbCodeDetlDto dto = new TbCodeDetlDto();

            dto.setGrpCd    (BizUtils.getCell(row, 1));
            dto.setGrpNm    (BizUtils.getCell(row, 2));
            dto.setCd       (BizUtils.getCell(row, 3));
            dto.setCdNm     (BizUtils.getCell(row, 4));
            dto.setOrd      (BizUtils.parseInt(BizUtils.getCell(row, 5)));
            dto.setRmk      (BizUtils.getCell(row, 6));
            dto.setSttsCd     (BizUtils.getCell(row, 7));
            dto.setCrtId    (BizUtils.getCell(row, 8));

            // 검증 로직
            String error = validateRow(dto);
            if (StringUtil.notNullNorEmpty(error))  dto.setSttsCd(error);

            result.add(dto);
        }
        return result;
    }

    private String validateRow(TbCodeDetlDto dto) {
        if (dto.getGrpCd() == null || dto.getGrpCd().isEmpty()) return "공통코드 누락";
        if (dto.getCd() == null || dto.getCd().isEmpty()) return "상세코드 누락";
        // DB 중복 체크
        int exists = tbCodeDetlMapper.countData(dto);
        if (exists > 0) return "이미 존재하는 코드";
        return null;  // 정상
    }

    public int saveUploaded(List<TbCodeDetlDto> list) {
        log.debug(BizUtils.logInfo());
        int count = 0;
        for (TbCodeDetlDto dto : list) {
            if(StringUtils.equals(dto.getSttsCd(), "1")) {
                dto.setUpdId(dto.getCrtId());
                tbCodeDetlMapper.insertData(dto);
                count++;
            }
        }
        return count;
    }

    /**
     *@ ID    : getCodeDetlAllData
     *@ NAME  : 상세코드 그룹 조회
     */
    public List<TbCodeDetlDto> getCodeDetlAllData(TbCodeDetlDto inputDto)  {
        log.debug(BizUtils.logInfo());
        return codeMapper.getGrpData(inputDto);
    }
}

