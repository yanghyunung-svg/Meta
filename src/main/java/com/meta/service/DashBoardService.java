package com.meta.service;

import com.common.utils.BizUtils;
import com.meta.dto.DashBoardDto;
import com.meta.mapper.DashBoardMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/** 
 *@ID       : DashBoardDtoService
 *@NAME     : DashBoard Service
 */
@Service
public class DashBoardService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private DashBoardMapper dashBoardMapper;

    /**
     * method   : getListData
     * desc     : DashBoard 목록 조회
     */
    public List<DashBoardDto> getListData(DashBoardDto inputDto)  {
        log.debug(BizUtils.logInfo("START"));
        log.debug(BizUtils.logVoKey(inputDto));
        List<DashBoardDto> outputDto = dashBoardMapper.getListData(inputDto);
        log.debug(BizUtils.logInfo("END"));
        return outputDto;
    }

    /**
     * method   : getData
     * desc     : DashBoard 상세 조회
     */
    public DashBoardDto getData(DashBoardDto inputDto)  {
        log.debug(BizUtils.logInfo("START"));
        DashBoardDto outputDto = dashBoardMapper.getData(inputDto);
        log.debug(BizUtils.logInfo("END"));
        return outputDto;
    }

}

