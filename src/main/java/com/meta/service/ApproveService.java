package com.meta.service;

import com.common.utils.BizUtils;
import com.meta.dto.ApproveDto;
import com.meta.dto.TbTermDictionaryDto;
import com.meta.dto.TbWordDictionaryDto;
import com.meta.mapper.ApproveMapper;
import com.meta.mapper.TbTermDictionaryMapper;
import com.meta.mapper.TbWordDictionaryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/** 
 *@ID       : ApproveService
 *@NAME     : 승인 Service
 */
@Service
public class ApproveService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ApproveMapper approveMapper;
    @Autowired
    private TbWordDictionaryMapper tbWordDictionaryMapper;
    @Autowired
    private TbTermDictionaryMapper tbTermDictionaryMapper;

    /**
     * @ID   : getListData
     * @NAME     : 승인 목록 조회
     */
    public List<ApproveDto> getListData(ApproveDto inputDto)  {
        log.debug(BizUtils.logInfo("START", BizUtils.logVoKey(inputDto)));
        List<ApproveDto> outputDto = approveMapper.getListData(inputDto);
        log.debug(BizUtils.logInfo("END"));
        return outputDto;
    }
    /**
     * @ID   : getListData
     * @NAME     : 승인 목록 조회
     */
    public ApproveDto prcsAprvDsctn(ApproveDto inputDto)  {
        log.debug(BizUtils.logInfo("START", BizUtils.logVoKey(inputDto)));
        ApproveDto outputDto = new ApproveDto();
        switch (inputDto.getSe()) {
        case "1":
            TbTermDictionaryDto termIn = new TbTermDictionaryDto();
            termIn.setId(inputDto.getId());
            TbTermDictionaryDto termOut = tbTermDictionaryMapper.getLockData(termIn);
            termOut.setStat("1");
            termOut.setUpdId(inputDto.getUserId());
            tbTermDictionaryMapper.updateData(termOut);
            break;
        case "2":
            TbWordDictionaryDto wordIn = new TbWordDictionaryDto();
            wordIn.setId(inputDto.getId());
            TbWordDictionaryDto wordOut = tbWordDictionaryMapper.getLockData(wordIn);
            wordOut.setStat("1");
            wordOut.setUpdId(inputDto.getUserId());
            tbWordDictionaryMapper.updateData(wordOut);
            break;
        default:
            break;
        }
        log.debug(BizUtils.logInfo("END"));
        return outputDto;
    }

}

