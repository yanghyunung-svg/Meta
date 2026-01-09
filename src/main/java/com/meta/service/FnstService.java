package com.meta.service;

import ch.qos.logback.core.util.StringUtil;
import com.meta.common.constants.BizConstants;
import com.meta.common.constants.ResponseCode;
import com.meta.common.exception.BizException;
import com.meta.common.util.BizUtils;
import com.meta.dto.TbFnstCdInfoDto;
import com.meta.mapper.TbFnstCdInfoMapper;
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
 *@ ID       : FnstService
 *@ NAME     : 금융기관코드 Service
 */
@Service
public class FnstService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TbFnstCdInfoMapper fnstMapper;

    /**
     * method   : getListData
     * desc     : 금융기관코드 목록 조회
     */
    public List<TbFnstCdInfoDto> getListData(TbFnstCdInfoDto inputDto)  {
        return fnstMapper.getListData(inputDto);
    }

    /**
     * method   : getData
     * desc     : 금융기관코드 상세 조회
     */
    public TbFnstCdInfoDto getData(TbFnstCdInfoDto inputDto)  {
        return fnstMapper.getData(inputDto);
    }

    /**
     * method   : manageData
     * desc     : 금융기관코드 관리
     */
    public void manageData(TbFnstCdInfoDto inputDto)  {
        log.debug(BizUtils.logInfo("START"));
        log.debug(BizUtils.logVoKey(inputDto));
        TbFnstCdInfoDto outputDto = fnstMapper.getLockData(inputDto);

        switch (inputDto.getFunc()) {
            case BizConstants.FUNC_SE.INS:
                if (outputDto != null) {
                    throw new BizException(ResponseCode.DUPLICATE_DATA);
                }
                if (fnstMapper.insertData(inputDto) == 0) {
                    throw new BizException(ResponseCode.INSERT_FAILED);
                }
                break;
            case BizConstants.FUNC_SE.UPD:
                if (outputDto == null) {
                    throw new BizException(ResponseCode.DATA_NOT_FOUND);
                }
                if (fnstMapper.updateData(inputDto) == 0) {
                    throw new BizException(ResponseCode.UPDATE_FAILED);
                }
                break;
            case BizConstants.FUNC_SE.DEL:
                if (outputDto == null) {
                    throw new BizException(ResponseCode.DATA_NOT_FOUND);
                }
                if (fnstMapper.deleteData(inputDto) == 0) {
                    throw new BizException(ResponseCode.DELETE_FAILED);
                }
                break;
            default:
                throw new BizException(ResponseCode.VALIDATION_FAILED);
        }
        log.debug(BizUtils.logInfo("END"));
    }

    public List<TbFnstCdInfoDto> uploadPreview(MultipartFile file) throws Exception {
        List<TbFnstCdInfoDto> result = new ArrayList<>();

        Workbook workbook = WorkbookFactory.create(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null) continue;

            TbFnstCdInfoDto dto = new TbFnstCdInfoDto();

            dto.setFnstCd(BizUtils.getCell(row, 0));
            dto.setRprsFnstCd(BizUtils.getCell(row, 1));
            dto.setSwiftCd(BizUtils.getCell(row, 2));
            dto.setFnstNm(BizUtils.getCell(row, 3));
            dto.setFnstEngNm(BizUtils.getCell(row, 4));
            dto.setAplcnYmd(BizUtils.getCell(row, 5));
            dto.setSttsCd("1");

            // 검증 로직
            String error = validateRow(dto);
            if (StringUtil.notNullNorEmpty(error))  dto.setSttsCd(error);

            result.add(dto);
        }
        return result;
    }


    private String validateRow(TbFnstCdInfoDto dto) {
        if (dto.getFnstCd() == null || dto.getFnstCd().isEmpty()) return "금융기관코드 누락";
        if (dto.getRprsFnstCd() == null || dto.getRprsFnstCd().isEmpty()) return "대표금융기관코드 누락";
        if (dto.getFnstNm() == null || dto.getFnstNm().isEmpty()) return "금융기관명 누락";
        if (dto.getFnstEngNm() == null || dto.getFnstEngNm().isEmpty()) return "금융기관영문명 누락";
        if (dto.getAplcnYmd() == null || dto.getAplcnYmd().isEmpty()) return "적용일자 누락";

        // DB 중복 체크
        int exists = fnstMapper.countCode(dto);
        if (exists > 0) return "이미 존재하는 코드";
        return null;  // 정상
    }

    public int saveUploaded(List<TbFnstCdInfoDto> list)  {
        int count = 0;
        for (TbFnstCdInfoDto dto : list) {
            if (StringUtils.equals(dto.getSttsCd(), "1")) {
                int exists = fnstMapper.countCode(dto);
                if (exists == 0) {
                    dto.setUpdId(dto.getCrtId());
                    fnstMapper.insertData(dto);
                    count++;
                }
            }
        }
        return count;
    } 
}

