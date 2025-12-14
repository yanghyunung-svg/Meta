package com.meta.service;

import com.common.utils.BizUtils;
import com.meta.dto.ApproveDto;
import com.meta.mapper.ApproveMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/** 
 *@ ID       : ApproveDtoService
 *@ NAME     : DashBoard Service
 */
@Service
public class DashBoardService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ApproveMapper approveMapper;

    /**
     * method   : getListData
     * desc     : DashBoard 목록 조회
     */
    public List<ApproveDto> getListData(ApproveDto inputDto)  {
        log.debug(BizUtils.logInfo("START"));
        log.debug(BizUtils.logVoKey(inputDto));
        List<ApproveDto> outputDto = approveMapper.getListData(inputDto);
        log.debug(BizUtils.logInfo("END"));
        return outputDto;
    }


}

