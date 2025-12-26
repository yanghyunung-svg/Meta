package com.meta.service;

import com.common.utils.BizUtils;
import com.meta.dto.CommCodeDto;
import com.meta.mapper.CommCodeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


    /**
     *@ ID   : getListData
     *@ NAME     : 코드기본 목록 조회
     */
    public List<CommCodeDto> getCommCodeSearch(CommCodeDto inputDto)  {
        log.debug(BizUtils.logInfo("START"));
        log.debug(BizUtils.logVoKey(inputDto));
        return commCodeMapper.getCommCodeList(inputDto);
    }
}

