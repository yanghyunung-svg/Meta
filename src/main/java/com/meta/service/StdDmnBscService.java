package com.meta.service;

import ch.qos.logback.core.util.StringUtil;
import com.common.utils.ApiResponse;
import com.common.utils.BizUtils;
import com.meta.dto.TbStdDmnBscDto;
import com.meta.mapper.TbStdDmnBscMapper;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 *@ ID       : StdDmnBscService
 *@ NAME     : 표준도메인 Service
 */
@Service
public class StdDmnBscService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TbStdDmnBscMapper tbStdDmnBscMapper; 


    /**
     * method   : getListData
     * desc     : 표준도메인 목록 조회
     */
    public List<TbStdDmnBscDto> getListData(TbStdDmnBscDto inputDto)  {
        log.debug(BizUtils.logInfo("START"));
        return tbStdDmnBscMapper.getListData(inputDto);
    }

    /**
     * method   : getData
     * desc     : 표준도메인 상세 조회
     */
    public List<TbStdDmnBscDto> getDmnComboData()  {
        log.debug(BizUtils.logInfo("START"));
        return tbStdDmnBscMapper.getDmnComboData();
    }

    /**
     * method   : getData
     * desc     : 표준도메인 상세 조회
     */
    public TbStdDmnBscDto getData(TbStdDmnBscDto inputDto)  {
        log.debug(BizUtils.logInfo("START"));
        return tbStdDmnBscMapper.getData(inputDto);
    }

    /**
     * method   : insertData
     * desc     : 표준도메인 등록
     */
    public ApiResponse<Void> insertData(TbStdDmnBscDto inputDto)  {
        log.debug(BizUtils.logInfo("START"));
        log.debug(BizUtils.logVo(inputDto));
        try {
            TbStdDmnBscDto outputDto = tbStdDmnBscMapper.getData(inputDto);

            if (outputDto != null) {
                log.debug(BizUtils.logInfo("기등록된 데이타가 있습니다"));
                return new ApiResponse<Void>(false, "기등록된 데이타가 있습니다." + outputDto.getDmnNm());
            }

            if (tbStdDmnBscMapper.insertData(inputDto) == 0) {
                return new ApiResponse<Void>(false, "등록 오류");
            }

            return new ApiResponse<Void>(true, "등록 성공");

        } catch (Exception e) {
            log.debug(BizUtils.logInfo("Exception"));
            return new ApiResponse<Void>(false, "등록 처리 중 오류가 발생했습니다.");
        }
    }

    /**
     * method   : updateData
     * desc     : 표준도메인 변경
     */
    public ApiResponse<Void> updateData(TbStdDmnBscDto inputDto)  {
        log.debug(BizUtils.logInfo("START"));
        log.debug(BizUtils.logVo(inputDto));

        try {
            TbStdDmnBscDto outputDto = tbStdDmnBscMapper.getData(inputDto);

            if (outputDto == null) {
                return new ApiResponse<Void>(false, "수정할 데이타가 없습니다");
            }

            if (tbStdDmnBscMapper.updateData(inputDto) == 0) {
                return new ApiResponse<Void>(false, "수정 오류");
            }

            log.debug(BizUtils.logVoKey(outputDto));
            return new ApiResponse<Void>(true, "수정 성공");

        } catch (Exception e) {
            return new ApiResponse<Void>(false, "수정 처리 중 오류가 발생했습니다.");
        }
    }

    /**
     * @ ID : parseExcelPreview
     * @ NAME : 코드그룹 엑셀업로드
     */
    public List<TbStdDmnBscDto> parseExcelPreview(MultipartFile file) throws Exception {
        log.debug(BizUtils.logInfo("START"));

        List<TbStdDmnBscDto> result = new ArrayList<>();
        Workbook workbook = WorkbookFactory.create(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null) continue;

            TbStdDmnBscDto dto = new TbStdDmnBscDto();

            dto.setDmnNm        (getCell(row, 1));
            dto.setDmnClsfNm    (getCell(row, 2));
            dto.setDmnEngNm     (getCell(row, 3));
            dto.setDmnAtrb      (getCell(row, 4));
            dto.setSttsCd       (getCell(row, 5));
            dto.setCrtId        (getCell(row, 6));

            // 검증 로직
            String error = validateRow(dto);
            if (StringUtil.notNullNorEmpty(error))  dto.setSttsCd(error);

            result.add(dto);
        }

        log.debug(BizUtils.logInfo("END"));
        return result;
    }

    private String validateRow(TbStdDmnBscDto dto) {
        if (dto.getDmnNm() == null || dto.getDmnNm().isEmpty()) return "도메인명 누락";
        if (dto.getDmnClsfNm() == null || dto.getDmnClsfNm().isEmpty()) return "도메인분류명 누락";
        if (dto.getDmnEngNm() == null || dto.getDmnEngNm().isEmpty()) return "도메인영문명 누락";
        if (dto.getDmnAtrb() == null || dto.getDmnAtrb().isEmpty()) return "도메인속성 누락";
        // DB 중복 체크
        int exists = tbStdDmnBscMapper.countCode(dto);
        if (exists > 0) return "이미 존재하는 코드";
        return null;  // 정상
    }

    public int saveUploadedExcel(List<TbStdDmnBscDto> list) {
        log.debug(BizUtils.logInfo("START"));

        int count = 0;
        for (TbStdDmnBscDto dto : list) {
            if(StringUtils.equals(dto.getSttsCd(), "0")) {
                dto.setUpdId(dto.getCrtId());
                tbStdDmnBscMapper.insertData(dto);
                count++;
            }
        }

        log.debug(BizUtils.logInfo("END", String.valueOf(count)));
        return count;
    }

    private String getCell(Row row, int cellIndex) {

        Cell cell = row.getCell(cellIndex);
        if (cell == null) return "";

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                }
                return String.valueOf((long) cell.getNumericCellValue());
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                try {
                    return cell.getStringCellValue();
                } catch (IllegalStateException e) {
                    return String.valueOf(cell.getNumericCellValue());
                }
            case BLANK:
            default:
                return "";
        }
    }

    private Integer parseInt(String value) {
        if (value == null || value.trim().isEmpty()) return null;

        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            return null; // 또는 기본값 0
        }
    }
}

