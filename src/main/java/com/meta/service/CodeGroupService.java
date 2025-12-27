package com.meta.service;

import ch.qos.logback.core.util.StringUtil;
import com.common.utils.ApiResponse;
import com.common.utils.BizUtils;
import com.meta.dto.TbCodeGroupDto;
import com.meta.mapper.TbCodeGroupMapper;
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
 *@ ID       : CodeGroupService
 *@ NAME     : 코드그룹기본 Service
 */
@Service
public class CodeGroupService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TbCodeGroupMapper tbCodeGroupMapper;


    /**
     * method   : getListData
     * desc     : 코드그룹기본 목록 조회
     */
    public List<TbCodeGroupDto> getListData(TbCodeGroupDto inputDto)  {
        log.debug(BizUtils.logInfo("START"));
        return tbCodeGroupMapper.getListData(inputDto);
    }

    /**
     * method   : getData
     * desc     : 코드그룹기본 상세 조회
     */
    public TbCodeGroupDto getData(TbCodeGroupDto inputDto)  {
        log.debug(BizUtils.logInfo("START"));
        return tbCodeGroupMapper.getData(inputDto);
    }

    /**
     * method   : insertData
     * desc     : 코드그룹기본 등록
     */
    public ApiResponse<Void> insertData(TbCodeGroupDto inputDto)  {
        log.debug(BizUtils.logInfo("START"));
        log.debug(BizUtils.logVo(inputDto));
        try {
            TbCodeGroupDto outputDto = tbCodeGroupMapper.getData(inputDto);

            if (outputDto != null) {
                return new ApiResponse<Void>(false, "기등록된 데이타가 있습니다. =" + outputDto.getGrpNm());
            }

            if (tbCodeGroupMapper.insertData(inputDto) == 0) {
                return new ApiResponse<Void>(false, "등록 오류");
            }

            return new ApiResponse<Void>(true, "등록 성공");

        } catch (Exception e) {
            return new ApiResponse<Void>(false, "등록 처리 중 오류가 발생했습니다.");
        }
    }


    /**
     * method   : updateData
     * desc     : 코드그룹기본 변경
     */
    public ApiResponse<Void> updateData(TbCodeGroupDto inputDto)  {
        log.debug(BizUtils.logInfo("START"));
        log.debug(BizUtils.logVo(inputDto));
        try {
            log.debug(BizUtils.logInfo("SELECT", inputDto.getGrpCd()));
            TbCodeGroupDto outputDto = tbCodeGroupMapper.getData(inputDto);

            if (outputDto == null) {
                return new ApiResponse<Void>(false, "수정할 데이타가 없습니다 =" + outputDto.getGrpNm());
            }

            if (tbCodeGroupMapper.updateData(inputDto) == 0) {
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
    public List<TbCodeGroupDto> parseExcelPreview(MultipartFile file) throws Exception {
        log.debug(BizUtils.logInfo("START"));

        List<TbCodeGroupDto> result = new ArrayList<>();
        Workbook workbook = WorkbookFactory.create(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null) continue;

            TbCodeGroupDto dto = new TbCodeGroupDto();

            dto.setGrpCd    (BizUtils.getCell(row, 1));
            dto.setGrpNm    (BizUtils.getCell(row, 2));
            dto.setOrd      (BizUtils.parseInt(BizUtils.getCell(row, 3)));
            dto.setRmk      (BizUtils.getCell(row, 4));
            dto.setStat     (BizUtils.getCell(row, 5));
            dto.setCrtId    (BizUtils.getCell(row, 6));

            // 검증 로직
            String error = validateRow(dto);
            if (StringUtil.notNullNorEmpty(error))  dto.setStat(error);

            result.add(dto);
        }

        log.debug(BizUtils.logInfo("END"));
        return result;
    }

    private String validateRow(TbCodeGroupDto dto) {
        if (dto.getGrpCd() == null || dto.getGrpCd().isEmpty()) return "공통코드 누락";
        if (dto.getGrpNm() == null || dto.getGrpNm().isEmpty()) return "공통코드명 누락";
        // DB 중복 체크
        int exists = tbCodeGroupMapper.countCode(dto);
        if (exists > 0) return "이미 존재하는 코드";
        return null;  // 정상
    }

    public int saveUploadedExcel(List<TbCodeGroupDto> list) {
        log.debug(BizUtils.logInfo("START"));

        int count = 0;
        for (TbCodeGroupDto dto : list) {
            if(StringUtils.equals(dto.getStat(), "1")) {
                dto.setUpdId(dto.getCrtId());
                tbCodeGroupMapper.insertData(dto);
                count++;
            }
        }

        log.debug(BizUtils.logInfo("END", String.valueOf(count)));
        return count;
    }
 
}
