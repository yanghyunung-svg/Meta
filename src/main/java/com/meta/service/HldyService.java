package com.meta.service;

import com.meta.common.constants.BizConstants;
import com.meta.common.constants.ResponseCode;
import com.meta.common.exception.BizException;
import com.meta.common.util.BizUtils;
import com.meta.dto.TbHldyInfDto;
import com.meta.mapper.TbHldyInfMapper;
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
 *@ ID       : HldyService
 *@ NAME     : 휴일정보기본 Service
 */
@Service
public class HldyService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private TbHldyInfMapper tbHldyInfMapper;

    /**
     * method   : getListData
     * desc     : 휴일정보기본 목록 조회
     */
    public List<TbHldyInfDto> getListData(TbHldyInfDto inputDto)  {


        return tbHldyInfMapper.getListData(inputDto);
    }

    private String getDowNm(String dowSeCd) {
        return switch (dowSeCd) {
            case "01" -> "일요일";
            case "02" -> "월요일";
            case "03" -> "화요일";
            case "04" -> "수요일";
            case "05" -> "목요일";
            case "06" -> "금요일";
            case "07" -> "토요일";
            default -> dowSeCd;
        };
    }

    /**
     * method   : getData
     * desc     : 휴일정보기본 상세 조회
     */
    public TbHldyInfDto getData(TbHldyInfDto inputDto)  {
        return tbHldyInfMapper.getData(inputDto);
    }

    /**
     * method   : manageData
     * desc     : 휴일정보기본 관리
     */
    public void manageData(TbHldyInfDto inputDto)  {
        log.debug(BizUtils.logInfo("START"));
        log.debug(BizUtils.logVoKey(inputDto));
        TbHldyInfDto outputDto = tbHldyInfMapper.getLockData(inputDto);

        switch (inputDto.getFunc()) {
            case BizConstants.FUNC_SE.INS:
                if (outputDto != null) {
                    throw new BizException(ResponseCode.DUPLICATE_DATA);
                }
                if (tbHldyInfMapper.insertData(inputDto) == 0) {
                    throw new BizException(ResponseCode.INSERT_FAILED);
                }
                break;
            case BizConstants.FUNC_SE.UPD:
                if (outputDto == null) {
                    throw new BizException(ResponseCode.DATA_NOT_FOUND);
                }
                if (tbHldyInfMapper.updateData(inputDto) == 0) {
                    throw new BizException(ResponseCode.UPDATE_FAILED);
                }
                break;
            case BizConstants.FUNC_SE.DEL:
                if (outputDto == null) {
                    throw new BizException(ResponseCode.DATA_NOT_FOUND);
                }
                if (tbHldyInfMapper.deleteData(inputDto) == 0) {
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
     * @ NAME : 휴일정보 엑셀업로드
     */
    public List<TbHldyInfDto> parsePreview(MultipartFile file) throws Exception {
        List<TbHldyInfDto> result = new ArrayList<>();
        Workbook workbook = WorkbookFactory.create(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null) continue;

            TbHldyInfDto dto = new TbHldyInfDto();

            dto.setCrtrYmd      (BizUtils.getCell(row, 0));
            dto.setHldyNm       (BizUtils.getCell(row, 1));
            dto.setHldySeCd     (BizUtils.getCell(row, 2));
            dto.setDowSeCd      (BizUtils.getCell(row, 3));

            // 검증 로직
            dto.setSttsCd(validateRow(dto));

            result.add(dto);
        }

        log.debug(BizUtils.logInfo("END"));
        return result;
    }

    private String validateRow(TbHldyInfDto dto) {
        if (dto.getCrtrYmd() == null || dto.getCrtrYmd().isEmpty()) return "기준일자 누락";
//        if (dto.getHldyNm() == null || dto.getHldyNm().isEmpty()) return "휴일명 누락";
        if (dto.getHldySeCd() == null || dto.getHldySeCd().isEmpty()) return "휴일구분코드 누락";
        if (dto.getDowSeCd() == null || dto.getDowSeCd().isEmpty()) return "요일코드 누락";
        // DB 중복 체크
        int exists = tbHldyInfMapper.countCode(dto);
        if (exists > 0) return "이미 존재하는 코드";
        return "0";  // 정상
    }

    public int saveUploaded(List<TbHldyInfDto> list) {
        int count = 0;
        for (TbHldyInfDto dto : list) {
            if(StringUtils.equals(dto.getSttsCd(), "0")) {
                dto.setUpdId(dto.getCrtId());
                tbHldyInfMapper.insertData(dto);
                count++;
            }
        }
        return count;
    }
 
}
