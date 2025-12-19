package com.meta.service;

import ch.qos.logback.core.util.StringUtil;
import com.common.utils.ApiResponse;
import com.common.utils.BizUtils;
import com.meta.dto.TbWordDictionaryDto;
import com.meta.mapper.TbWordDictionaryMapper;
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
 *@ ID       : WordDictionaryService
 *@ NAME     : 단어사전 Service
 */
@Service
public class WordDictionaryService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TbWordDictionaryMapper tbWordDictionaryMapper;


    /**
     * method   : getListData
     * desc     : 단어사전 목록 조회
     */
    public List<TbWordDictionaryDto> getListData(TbWordDictionaryDto inputDto)  {
        log.debug(BizUtils.logInfo("START"));
        return tbWordDictionaryMapper.getListData(inputDto);
    }

    /**
     * method   : getData
     * desc     : 단어사전 상세 조회
     */
    public TbWordDictionaryDto getData(TbWordDictionaryDto inputDto)  {
        log.debug(BizUtils.logInfo("START", BizUtils.logVo(inputDto)));
        TbWordDictionaryDto outputDto = tbWordDictionaryMapper.getData(inputDto);
        log.debug(BizUtils.logInfo("END", BizUtils.logVo(outputDto)));
        return outputDto;
    }
    /**
     * method   : getDataByName
     * desc     : 단어사전 단어명 상세 조회
     */
    public TbWordDictionaryDto getDataByName(TbWordDictionaryDto inputDto)  {
        log.debug(BizUtils.logInfo("START", BizUtils.logVo(inputDto)));
        TbWordDictionaryDto outputDto = tbWordDictionaryMapper.getDataByName(inputDto);
        log.debug(BizUtils.logInfo("END", BizUtils.logVo(outputDto)));
        return outputDto;
    }

    /**
     * method   : insertData
     * desc     : 단어사전 등록
     */
    public ApiResponse<Void> insertData(TbWordDictionaryDto inputDto)  {
        log.debug(BizUtils.logInfo("START"));
        log.debug(BizUtils.logVo(inputDto));
        try {
            TbWordDictionaryDto outputDto = tbWordDictionaryMapper.getDataByName(inputDto);

            if (outputDto != null) {
                return new ApiResponse<Void>(false, "기등록된 데이타가 있습니다." + outputDto.getEngAbbrNm());
            }

            if (tbWordDictionaryMapper.insertData(inputDto) == 0) {
                return new ApiResponse<Void>(false, "등록 오류");
            }

            return new ApiResponse<Void>(true, "등록 성공");

        } catch (Exception e) {
            return new ApiResponse<Void>(false, "등록 처리 중 오류가 발생했습니다.");
        }
    }


    /**
     * method   : updateData
     * desc     : 단어사전 변경
     */
    public ApiResponse<Void> updateData(TbWordDictionaryDto inputDto)  {
        log.debug(BizUtils.logInfo("START"));
        log.debug(BizUtils.logVo(inputDto));

        try {
            TbWordDictionaryDto outputDto = tbWordDictionaryMapper.getData(inputDto);

            if (outputDto == null) {
                return new ApiResponse<Void>(false, "수정할 데이타가 없습니다");
            }

            if (tbWordDictionaryMapper.updateData(inputDto) == 0) {
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
     * @ NAME : 표준단어 엑셀업로드
     */
    public List<TbWordDictionaryDto> parseExcelPreview(MultipartFile file) throws Exception {
        log.debug(BizUtils.logInfo("START"));

        List<TbWordDictionaryDto> result = new ArrayList<>();
        Workbook workbook = WorkbookFactory.create(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null) continue;

            TbWordDictionaryDto dto = new TbWordDictionaryDto();

            dto.setWordNm       (getCell(row, 1));
            dto.setEngAbbrNm    (getCell(row, 2));
            dto.setEngNm        (getCell(row, 3));
            dto.setExpln        (getCell(row, 4));
            dto.setStat         (getCell(row, 5));
            dto.setCrtId        (getCell(row, 6));

            // 검증 로직
            String error = validateRow(dto);
            if (StringUtil.notNullNorEmpty(error))  dto.setStat(error);

            result.add(dto);
        }

        log.debug(BizUtils.logInfo("END"));
        return result;
    }

    private String validateRow(TbWordDictionaryDto dto) {
        if (dto.getWordNm() == null || dto.getWordNm().isEmpty()) return "단어명 누락";
        if (dto.getEngAbbrNm() == null || dto.getEngAbbrNm().isEmpty()) return "영문약어명 누락";
        if (dto.getEngNm() == null || dto.getEngNm().isEmpty()) return "영문명 누락";
        if (dto.getExpln() == null || dto.getExpln().isEmpty()) return "설명 누락";
        // DB 중복 체크
        int exists = tbWordDictionaryMapper.countCode(dto);
        if (exists > 0) return "이미 존재하는 코드";
        return "0";  // 정상
    }

    /**
     * @ ID : saveUploadedExcel
     * @ NAME : 표준단어 엑셀업로드 저장
     */
    public int saveUploadedExcel(List<TbWordDictionaryDto> list) {
        log.debug(BizUtils.logInfo("START"));

        int count = 0;
        for (TbWordDictionaryDto dto : list) {
            if(StringUtils.equals(dto.getStat(), "0")) {
                dto.setUpdId(dto.getCrtId());
                tbWordDictionaryMapper.insertData(dto);
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

