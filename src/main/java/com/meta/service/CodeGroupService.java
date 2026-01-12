package com.meta.service;

import ch.qos.logback.core.util.StringUtil;
import com.meta.common.constants.BizConstants;
import com.meta.common.constants.ResponseCode;
import com.meta.common.exception.BizException;
import com.meta.common.util.BizUtils;
import com.meta.dto.TbCodeGroupDto;
import com.meta.mapper.dbio.TbCodeGroupMapper;
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
        log.debug(BizUtils.logInfo());
        return tbCodeGroupMapper.getListData(inputDto);
    }

    /**
     * method   : getData
     * desc     : 코드그룹기본 상세 조회
     */
    public TbCodeGroupDto getData(TbCodeGroupDto inputDto)  {
        log.debug(BizUtils.logInfo());
        return tbCodeGroupMapper.getData(inputDto);
    }

    /**
     * method   : manageData
     * desc     : 단어사전 관리
     */
    public void manageData(TbCodeGroupDto inputDto)  {
        log.debug(BizUtils.logInfo("START"));
        log.debug(BizUtils.logVoKey(inputDto));
        TbCodeGroupDto outputDto = tbCodeGroupMapper.getLockData(inputDto);

        switch (inputDto.getFunc()) {
            case BizConstants.FUNC_SE.INS:
                if (outputDto  != null) {
                    throw new BizException(ResponseCode.DUPLICATE_DATA);
                }
                if (tbCodeGroupMapper.insertData(inputDto) == 0) {
                    throw new BizException(ResponseCode.INSERT_FAILED);
                }
                break;
            case BizConstants.FUNC_SE.UPD:
                if (outputDto == null) {
                    throw new BizException(ResponseCode.DATA_NOT_FOUND);
                }
                if (tbCodeGroupMapper.updateData(inputDto) == 0) {
                    throw new BizException(ResponseCode.UPDATE_FAILED);
                }
                break;
            case BizConstants.FUNC_SE.DEL:
                if (outputDto == null) {
                    throw new BizException(ResponseCode.DATA_NOT_FOUND);
                }
                if (tbCodeGroupMapper.deleteData(inputDto) == 0) {
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
     * @ NAME : 코드그룹 엑셀업로드
     */
    public List<TbCodeGroupDto> parsePreview(MultipartFile file) throws Exception {
        log.debug(BizUtils.logInfo());
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
            dto.setSttsCd     (BizUtils.getCell(row, 5));
            dto.setCrtId    (BizUtils.getCell(row, 6));

            // 검증 로직
            String error = validateRow(dto);
            if (StringUtil.notNullNorEmpty(error))  dto.setSttsCd(error);

            result.add(dto);
        }

        log.debug(BizUtils.logInfo("END"));
        return result;
    }

    private String validateRow(TbCodeGroupDto dto) {
        if (dto.getGrpCd() == null || dto.getGrpCd().isEmpty()) return "공통코드 누락";
        if (dto.getGrpNm() == null || dto.getGrpNm().isEmpty()) return "공통코드명 누락";
        // DB 중복 체크
        int exists = tbCodeGroupMapper.countData(dto);
        if (exists > 0) return "이미 존재하는 코드";
        return null;  // 정상
    }

    public int saveUploaded(List<TbCodeGroupDto> list) {
        log.debug(BizUtils.logInfo());
        int count = 0;
        for (TbCodeGroupDto dto : list) {
            if(StringUtils.equals(dto.getSttsCd(), "1")) {
                dto.setUpdId(dto.getCrtId());
                tbCodeGroupMapper.insertData(dto);
                count++;
            }
        }
        return count;
    }
 
}
