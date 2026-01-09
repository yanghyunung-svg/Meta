package com.meta.service;

import ch.qos.logback.core.util.StringUtil;
import com.meta.common.constants.BizConstants;
import com.meta.common.constants.ResponseCode;
import com.meta.common.exception.BizException;
import com.meta.common.util.BizUtils;
import com.meta.dto.TbFnncJntCdInfoDto;
import com.meta.mapper.TbFnncJntCdInfoMapper;
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
 *@ NAME     : 금융공동코드 Service
 */
@Service
public class JntCdService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TbFnncJntCdInfoMapper jntCdMapper;

    /**
     * method   : getListData
     * desc     : 금융공동코드 목록 조회
     */
    public List<TbFnncJntCdInfoDto> getListData(TbFnncJntCdInfoDto inputDto)  {
        log.debug(BizUtils.logInfo(BizUtils.logVoKey(inputDto)));
        return jntCdMapper.getListData(inputDto);
    }

    /**
     * method   : getData
     * desc     : 금융공동코드 상세 조회
     */
    public TbFnncJntCdInfoDto getData(TbFnncJntCdInfoDto inputDto)  {
        log.debug(BizUtils.logInfo(BizUtils.logVoKey(inputDto)));
        return jntCdMapper.getData(inputDto);
    }

    /**
     * method   : manageData
     * desc     : 금융공동코드 관리
     */
    public void manageData(TbFnncJntCdInfoDto inputDto)  {
        log.debug(BizUtils.logInfo("START"));
        log.debug(BizUtils.logVoKey(inputDto));
        TbFnncJntCdInfoDto outputDto = jntCdMapper.getLockData(inputDto);

        switch (inputDto.getFunc()) {
            case BizConstants.FUNC_SE.INS:
                if (outputDto != null) {
                    throw new BizException(ResponseCode.DUPLICATE_DATA);
                }
                if (jntCdMapper.insertData(inputDto) == 0) {
                    throw new BizException(ResponseCode.INSERT_FAILED);
                }
                break;
            case BizConstants.FUNC_SE.UPD:
                if (outputDto == null) {
                    throw new BizException(ResponseCode.DATA_NOT_FOUND);
                }
                if (jntCdMapper.updateData(inputDto) == 0) {
                    throw new BizException(ResponseCode.UPDATE_FAILED);
                }
                break;
            case BizConstants.FUNC_SE.DEL:
                if (outputDto == null) {
                    throw new BizException(ResponseCode.DATA_NOT_FOUND);
                }
                if (jntCdMapper.deleteData(inputDto) == 0) {
                    throw new BizException(ResponseCode.DELETE_FAILED);
                }
                break;
            default:
                throw new BizException(ResponseCode.VALIDATION_FAILED);
        }
        log.debug(BizUtils.logInfo("END"));
    }

    public List<TbFnncJntCdInfoDto> uploadPreview(MultipartFile file) throws Exception {
        List<TbFnncJntCdInfoDto> result = new ArrayList<>();

        Workbook workbook = WorkbookFactory.create(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null) continue;

            TbFnncJntCdInfoDto dto = new TbFnncJntCdInfoDto();

            dto.setJntCd(BizUtils.getCell(row, 0));
            dto.setRprsFnstCd(BizUtils.getCell(row, 1));
            dto.setFnstCd(BizUtils.getCell(row, 2));
            dto.setFnstNm(BizUtils.getCell(row, 3));
            dto.setStorNm(BizUtils.getCell(row, 4));
            dto.setTelno(BizUtils.getCell(row, 5));
            dto.setFxno(BizUtils.getCell(row, 6));
            dto.setZip(BizUtils.getCell(row, 7));
            dto.setAddr(BizUtils.getCell(row, 8));
            dto.setSe(BizUtils.getCell(row, 9));
            dto.setClsgMngBrnch(BizUtils.getCell(row, 10));
            dto.setSttsCd("1");

            // 검증 로직
            String error = validateRow(dto);
            if (StringUtil.notNullNorEmpty(error))  dto.setSttsCd(error);

            result.add(dto);
        }
        return result;
    }


    private String validateRow(TbFnncJntCdInfoDto dto) {
        if (dto.getJntCd() == null || dto.getJntCd().isEmpty()) return "금융공동코드 누락";
        if (dto.getFnstCd() == null || dto.getFnstCd().isEmpty()) return "기관코드 누락";
        if (dto.getRprsFnstCd() == null || dto.getRprsFnstCd().isEmpty()) return "대표기관코드 누락";
        if (dto.getFnstNm() == null || dto.getFnstNm().isEmpty()) return "금융기관명 누락";
        if (dto.getStorNm() == null || dto.getStorNm().isEmpty()) return "점포명 누락";
        if (dto.getTelno() == null || dto.getTelno().isEmpty()) return "전화번호 누락";

        // DB 중복 체크
        int exists = jntCdMapper.countCode(dto);
        if (exists > 0) return "이미 존재하는 코드";
        return null;  // 정상
    }

    public int saveUploaded(List<TbFnncJntCdInfoDto> list)  {
        int count = 0;
        for (TbFnncJntCdInfoDto dto : list) {
            if (StringUtils.equals(dto.getSttsCd(), "1")) {
                int exists = jntCdMapper.countCode(dto);
                if (exists == 0) {
                    dto.setUpdId(dto.getCrtId());
                    jntCdMapper.insertData(dto);
                    count++;
                }
            }
        }
        return count;
    } 
}

