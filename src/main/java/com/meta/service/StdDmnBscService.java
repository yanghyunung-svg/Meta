package com.meta.service;

import ch.qos.logback.core.util.StringUtil;
import com.common.utils.ApiResponse;
import com.common.utils.BizUtils;
import com.meta.dto.TbStdDmnBscDto;
import com.meta.mapper.TbStdDmnBscMapper;
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
        return tbStdDmnBscMapper.getListData(inputDto);
    }

    /**
     * method   : getData
     * desc     : 표준도메인 상세 조회
     */
    public List<TbStdDmnBscDto> getDmnComboData()  {
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
     * method   : manageData
     * desc     : 표준도메인 관리
     */
    public ApiResponse<Void> manageData(TbStdDmnBscDto inputDto)  {
        log.debug(BizUtils.logInfo("START"));
        log.debug(BizUtils.logVoKey(inputDto));
        TbStdDmnBscDto outputDto = tbStdDmnBscMapper.getLockData(inputDto);

        try {
            switch (inputDto.getFunc()) {
                case "I":
                    if (outputDto != null) {
                        return new ApiResponse<Void>(false, "기등록된 데이타가 있습니다.");
                    }
                    if (tbStdDmnBscMapper.insertData(inputDto) == 0) {
                        return new ApiResponse<Void>(false, "등록 오류");
                    }
                    break;
                case "U":
                    if (outputDto == null) {
                        return new ApiResponse<Void>(false, "데이타가 없습니다");
                    }
                    if (tbStdDmnBscMapper.updateData(inputDto) == 0) {
                        return new ApiResponse<Void>(false, "수정 오류");
                    }
                    break;
                case "D":
                    if (outputDto == null) {
                        return new ApiResponse<Void>(false, "데이타가 없습니다");
                    }
                    if (tbStdDmnBscMapper.deleteData(inputDto) == 0) {
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
     * @ NAME : 표준도메인 엑셀업로드
     */
    public List<TbStdDmnBscDto> parseExcelPreview(MultipartFile file) throws Exception {
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
        int exists = tbStdDmnBscMapper.countCode(dto);
        if (exists > 0) return "이미 존재하는 코드";
        return null;  // 정상
    }

    public int saveUploadedExcel(List<TbStdDmnBscDto> list) {
        int count = 0;
        for (TbStdDmnBscDto dto : list) {
            if(StringUtils.equals(dto.getSttsCd(), "0")) {
                dto.setUpdId(dto.getCrtId());
                tbStdDmnBscMapper.insertData(dto);
                count++;
            }
        }
        return count;
    }

}

