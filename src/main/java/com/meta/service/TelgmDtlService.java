package com.meta.service;

import ch.qos.logback.core.util.StringUtil;
import com.meta.common.constants.BizConstants;
import com.meta.common.constants.ResponseCode;
import com.meta.common.exception.BizException;
import com.meta.common.util.BizUtils;
import com.meta.dto.TbTelgmDtlBscDto;
import com.meta.mapper.dbio.TbTelgmDtlBscMapper;
import com.meta.mapper.meta.TelgmMapper;
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
 *@ ID       : TelgmDtlService
 *@ NAME     : 전문상세 Service
 */
@Service
public class TelgmDtlService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private TbTelgmDtlBscMapper tbTelgmDtlBscMapper;
    @Autowired
    private TelgmMapper telgmMapper;

    /**
     * method   : getListData
     * desc     : 전문상세 목록 조회
     */
    public List<TbTelgmDtlBscDto> getListData(TbTelgmDtlBscDto inputDto) {
        log.debug(BizUtils.logInfo());
        return telgmMapper.getTelgmDtlListData(inputDto);
    }

    /**
     * method   : getData
     * desc     : 전문상세 상세 조회
     */
    public TbTelgmDtlBscDto getData(TbTelgmDtlBscDto inputDto)  {
        log.debug(BizUtils.logInfo());
        return tbTelgmDtlBscMapper.getData(inputDto);
    }

    /**
     * method   : manageData
     * desc     : 전문상세 관리
     */
    public void manageData(TbTelgmDtlBscDto inputDto)  {
        log.debug(BizUtils.logInfo("START"));
        log.debug(BizUtils.logVoKey(inputDto));
        TbTelgmDtlBscDto outputDto = tbTelgmDtlBscMapper.getLockData(inputDto);

        switch (inputDto.getFunc()) {
            case BizConstants.FUNC_SE.INS:
                if (outputDto  != null) {
                    throw new BizException(ResponseCode.DUPLICATE_DATA);
                }
                if (tbTelgmDtlBscMapper.insertData(inputDto) == 0) {
                    throw new BizException(ResponseCode.INSERT_FAILED);
                }
                break;
            case BizConstants.FUNC_SE.UPD:
                if (outputDto == null) {
                    throw new BizException(ResponseCode.DATA_NOT_FOUND);
                }
                if (tbTelgmDtlBscMapper.updateData(inputDto) == 0) {
                    throw new BizException(ResponseCode.UPDATE_FAILED);
                }
                break;
            case BizConstants.FUNC_SE.DEL:
                if (outputDto == null) {
                    throw new BizException(ResponseCode.DATA_NOT_FOUND);
                }
                if (tbTelgmDtlBscMapper.deleteData(inputDto) == 0) {
                    throw new BizException(ResponseCode.DELETE_FAILED);
                }
                break;
            default:
                throw new BizException(ResponseCode.VALIDATION_FAILED);
        }
        log.debug(BizUtils.logInfo("END"));
    }

    public List<TbTelgmDtlBscDto> uploadPreview(MultipartFile file) throws Exception {
        log.debug(BizUtils.logInfo());
        List<TbTelgmDtlBscDto> result = new ArrayList<>();

        Workbook workbook = WorkbookFactory.create(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null) continue;

            TbTelgmDtlBscDto dto = new TbTelgmDtlBscDto();

            dto.setInstCd           (BizUtils.getCell(row, 1));
            dto.setTaskSeCd         (BizUtils.getCell(row, 2));
            dto.setTelgmKndCd       (BizUtils.getCell(row, 3));
            dto.setDlngSeCd         (BizUtils.getCell(row, 4));
            dto.setArtclSeq         (BizUtils.parseInt(BizUtils.getCell(row, 5)));
            dto.setArtclNm          (BizUtils.getCell(row, 6));
            dto.setArtclEngNm       (BizUtils.getCell(row, 7));
            dto.setArtclAtrb        (BizUtils.getCell(row, 8));
            dto.setArtclLen         (BizUtils.parseInt(BizUtils.getCell(row, 9)));
            dto.setArtclBscVl       (BizUtils.getCell(row, 10));
            dto.setArtclTypeCd      (BizUtils.getCell(row, 11));
            dto.setArtclDpth        (BizUtils.parseInt(BizUtils.getCell(row, 12)));
            dto.setReptField        (BizUtils.getCell(row, 13));
            dto.setReptFixNmtm      (BizUtils.parseInt(BizUtils.getCell(row, 14)));
            dto.setSttsCd           ("1");

            // 검증 로직
            String error = validateRow(dto);
            if (StringUtil.notNullNorEmpty(error))  dto.setSttsCd(error);

            result.add(dto);
        }
        return result;
    }


    private String validateRow(TbTelgmDtlBscDto dto) {
        if (dto.getInstCd() == null || dto.getInstCd().isEmpty()) return "기관코드 누락";
        if (dto.getTaskSeCd() == null || dto.getTaskSeCd().isEmpty()) return "업무구분코드 누락";
        if (dto.getDlngSeCd() == null || dto.getDlngSeCd().isEmpty()) return "거래구분코드 누락";
        if (dto.getArtclNm() == null || dto.getArtclNm().isEmpty()) return "항목명 누락";
        if (dto.getArtclEngNm() == null || dto.getArtclEngNm().isEmpty()) return "항목영문명 누락";

        // DB 중복 체크
        int exists = tbTelgmDtlBscMapper.countData(dto);
        if (exists > 0) return "이미 존재하는 코드";
        return null;  // 정상
    }

    public int saveUploaded(List<TbTelgmDtlBscDto> list)  {
        log.debug(BizUtils.logInfo());
        int count = 0;
        for (TbTelgmDtlBscDto dto : list) {
            if (StringUtils.equals(dto.getSttsCd(), "1")) {
                int exists = tbTelgmDtlBscMapper.countData(dto);
                if (exists == 0) {
                    dto.setUpdId(dto.getCrtId());
                    tbTelgmDtlBscMapper.insertData(dto);
                    count++;
                }
            }
        }
        return count;
    } 
}

