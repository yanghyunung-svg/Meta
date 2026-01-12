package com.meta.service;

import com.meta.common.util.BizUtils;
import com.meta.dto.CommCodeDto;
import com.meta.dto.TbLoginLogDto;
import com.meta.mapper.meta.CommCodeMapper;
import com.meta.mapper.dbio.TbLoginLogMapper;
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
     * method   : insertLoginLog
     * desc     : Login Log insert
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void insertLoginLog(TbLoginLogDto inputDto) {
        log.debug(BizUtils.logInfo());
        tbLoginLogMapper.insertData(inputDto);
    }



}

