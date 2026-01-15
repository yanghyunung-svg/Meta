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
        log.debug(BizUtils.logInfo());
        return commCodeMapper.getCommCodeList(inputDto);
    }

    /**
     *@ ID   : getDlngSeCdComboData
     *@ NAME     : 거래구분코드 combo 목록 조회
     */
    public List<TbCodeDto> getTelgmComboData(TbTelgmKndBscDto inputDto)  {
        log.debug(BizUtils.logInfo("START", BizUtils.logVoKey(inputDto)));
        switch(inputDto.getFunc()) {
            case "dlngSeCd":
                return telgmMapper.getDlngSeCdComboData(inputDto);
            case "telgmKndCd":
                return telgmMapper.getTelgmKndCdComboData(inputDto);
            case "taskSeCd":
                return telgmMapper.getTaskSeCdComboData(inputDto);
            default:
                break;
        }
        log.debug(BizUtils.logInfo("END"));
        return null;
    }

    /**
     * method   : insertLoginLog
     * desc     : Login Log insert
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void insertLoginLog(TbLoginLogDto inputDto) {
        log.debug(BizUtils.logInfo());
        tbLoginLogMapper.insertData(inputDto);
    }



}

