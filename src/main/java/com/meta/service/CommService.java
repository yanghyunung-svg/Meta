package com.meta.service;

import com.meta.common.util.BizUtils;
import com.meta.dto.CommCodeDto;
import com.meta.dto.TbCodeDto;
import com.meta.dto.TbLoginLogDto;
import com.meta.dto.TbTelgmKndBscDto;
import com.meta.mapper.dbio.TbLoginLogMapper;
import com.meta.mapper.meta.CommCodeMapper;
import com.meta.mapper.meta.TelgmMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/** 
 *@ ID       : CodeService
 *@ NAME     : 코드기본 Service
 */
@Service
public class CommService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private CommCodeMapper commCodeMapper;
    @Autowired
    private TelgmMapper telgmMapper;
    @Autowired
    private TbLoginLogMapper tbLoginLogMapper;

    /**
     *@ ID   : getListData
     *@ NAME     : 코드기본 목록 조회
     */
    public List<CommCodeDto> getCommCodeSearch(CommCodeDto inputDto)  {
        log.debug(BizUtils.logInfo("START", BizUtils.logVoKey(inputDto)));
        return commCodeMapper.getCommCodeList(inputDto);
    }

    /**
     *@ ID   : getTelgmComboData
     *@ NAME     : 거래구분코드 combo 목록 조회
     */
    public List<TbCodeDto> getTelgmComboData(TbTelgmKndBscDto inputDto)  {
        log.debug(BizUtils.logInfo("START", BizUtils.logVoKey(inputDto)));
        List<TbCodeDto> outputDto = new ArrayList<TbCodeDto>();
        switch(inputDto.getFunc()) {
            case "taskSeCd":        // 업무구분
                outputDto = telgmMapper.getTaskSeCdComboData(inputDto);
                break;
            case "telgmKndCd":   // 전체전문종별
                outputDto = telgmMapper.getAllTelgmKndCdComboData(inputDto);
                break;
            case "reqTelgmKndCd":   // 요청전문종별
                outputDto = telgmMapper.getReqTelgmKndCdComboData(inputDto);
                break;
            case "rspnsTelgmKndCd": // 응답전문종별
                outputDto = telgmMapper.getRspnsTelgmKndCdComboData(inputDto);
                break;
            case "dlngSeCd":        // 거래구분코드
                outputDto = telgmMapper.getDlngSeCdComboData(inputDto);
                break;
            default:
                break;
        }
        log.debug("END result={}", outputDto);
        log.debug(BizUtils.logInfo("END"));
        return outputDto;
    }

    /**
     * method   : insertLoginLog
     * desc     : Login Log insert
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void insertLoginLog(TbLoginLogDto inputDto) {
        log.debug(BizUtils.logInfo("START", BizUtils.logVoKey(inputDto)));
        tbLoginLogMapper.insertData(inputDto);
    }



}

