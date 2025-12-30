package com.meta.service;

import ch.qos.logback.core.util.StringUtil;
import com.common.utils.ApiResponse;
import com.common.utils.BizUtils;
import com.meta.dto.TbCodeDto;
import com.meta.mapper.TbCodeGroupMapper;
import com.meta.mapper.TbCodeMapper;
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
 *@ ID       : CodeService
 *@ NAME     : 상세코드 Service
 */
@Service
public class CodeService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TbCodeMapper tbCodeMapper;
    @Autowired
    private TbCodeGroupMapper tbCodeGroupMapper;


    /**
     *@ ID   : getListData
     *@ NAME     : 상세코드 목록 조회
     */
    public List<TbCodeDto> getListData(TbCodeDto inputDto)  {
        return tbCodeMapper.getListData(inputDto);
    }

    /**
     *@ ID    : getAllData
     *@ NAME  : 상세코드 combo 조회
     */
    public List<TbCodeDto> getCodeAllData(TbCodeDto inputDto)  {
        return tbCodeMapper.getAllData(inputDto);
    }


    /**
     *@ ID   : getData
     *@ NAME     : 상세코드 조회
     */
    public TbCodeDto getData(TbCodeDto inputDto)  {
        return tbCodeMapper.getData(inputDto);
    }

    /**
     * method   : manageData
     * desc     : 상세코드 관리
     */
    public ApiResponse<Void> manageData(TbCodeDto inputDto)  {
        log.debug(BizUtils.logInfo("START"));
        log.debug(BizUtils.logVoKey(inputDto));
        TbCodeDto outputDto = tbCodeMapper.getLockData(inputDto);

        try {
            switch (inputDto.getFunc()) {
                case "I":
                    if (outputDto != null) {
                        return new ApiResponse<Void>(false, "기등록된 데이타가 있습니다.");
                    }
                    if (tbCodeMapper.insertData(inputDto) == 0) {
                        return new ApiResponse<Void>(false, "등록 오류");
                    }
                    break;
                case "U":
                    if (outputDto == null) {
                        return new ApiResponse<Void>(false, "데이타가 없습니다");
                    }
                    if (tbCodeMapper.updateData(inputDto) == 0) {
                        return new ApiResponse<Void>(false, "수정 오류");
                    }
                    break;
                case "D":
                    if (outputDto == null) {
                        return new ApiResponse<Void>(false, "데이타가 없습니다");
                    }
                    if (tbCodeMapper.deleteData(inputDto) == 0) {
                        return new ApiResponse<Void>(false, "삭제 오류");
                    }
                    break;
                default:
                    break;
            }
            log.debug(BizUtils.logInfo("END"));
            return new ApiResponse<Void>(true, "처리성공");
        } catch (Exception e) {
            return new ApiResponse<Void>(false, "처리 중 오류가 발생했습니다.");
        }
    }

    /**
     * @ ID : parseExcelPreview
     * @ NAME : 상세코드 엑셀업로드
     */
    public List<TbCodeDto> parseExcelPreview(MultipartFile file) throws Exception {
        List<TbCodeDto> result = new ArrayList<>();
        Workbook workbook = WorkbookFactory.create(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null) continue;

            TbCodeDto dto = new TbCodeDto();

            dto.setGrpCd    (BizUtils.getCell(row, 1));
            dto.setGrpNm    (BizUtils.getCell(row, 2));
            dto.setCd       (BizUtils.getCell(row, 3));
            dto.setCdNm     (BizUtils.getCell(row, 4));
            dto.setOrd      (BizUtils.parseInt(BizUtils.getCell(row, 5)));
            dto.setRmk      (BizUtils.getCell(row, 6));
            dto.setStat     (BizUtils.getCell(row, 7));
            dto.setCrtId    (BizUtils.getCell(row, 8));

            // 검증 로직
            String error = validateRow(dto);
            if (StringUtil.notNullNorEmpty(error))  dto.setStat(error);

            result.add(dto);
        }
        return result;
    }

    private String validateRow(TbCodeDto dto) {
        if (dto.getGrpCd() == null || dto.getGrpCd().isEmpty()) return "공통코드 누락";
        if (dto.getCd() == null || dto.getCd().isEmpty()) return "상세코드 누락";
        // DB 중복 체크
        int exists = tbCodeMapper.countCode(dto);
        if (exists > 0) return "이미 존재하는 코드";
        return null;  // 정상
    }

    public int saveUploadedExcel(List<TbCodeDto> list) {
        int count = 0;
        for (TbCodeDto dto : list) {
            if(StringUtils.equals(dto.getStat(), "1")) {
                dto.setUpdId(dto.getCrtId());
                tbCodeMapper.insertData(dto);
                count++;
            }
        }
        return count;
    }
 
}

