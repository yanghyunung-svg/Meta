package com.meta.service;

import com.common.utils.ApiResponse;
import com.common.utils.BizUtils;
import com.meta.dto.TbTermDictionaryDto;
import com.meta.mapper.TbTermDictionaryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/** 
 *@ID       : TermDictionaryService
 *@NAME     : 용어사전 Service
 */
@Service
public class TermDictionaryService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TbTermDictionaryMapper tbTermDictionaryMapper;


    /**
     * method   : getListData
     * desc     : 용어사전 목록 조회
     */
    public List<TbTermDictionaryDto> getListData(TbTermDictionaryDto inputDto)  {
        log.debug(BizUtils.logInfo("START"));
        return tbTermDictionaryMapper.getListData(inputDto);
    }

    /**
     * method   : getData
     * desc     : 용어사전 상세 조회
     */
    public TbTermDictionaryDto getData(TbTermDictionaryDto inputDto)  {
        log.debug(BizUtils.logInfo("START"));
        return tbTermDictionaryMapper.getData(inputDto);
    }
    /**
     * method   : insertData
     * desc     : 용어사전 등록
     */
    public ApiResponse insertData(TbTermDictionaryDto inputDto)  {
        log.debug(BizUtils.logInfo("START"));
        log.debug(BizUtils.logVo(inputDto));
        try {
            log.debug(BizUtils.logInfo("SELECT", inputDto.getTrmNm()));
            TbTermDictionaryDto outputDto = tbTermDictionaryMapper.getDataByName(inputDto);

            if (outputDto != null) {
                log.debug(BizUtils.logInfo("기등록된 데이타가 있습니다"));
                return new ApiResponse(false, "기등록된 데이타가 있습니다." + outputDto.getEngNm());
            }

            if (tbTermDictionaryMapper.insertData(inputDto) == 0) {
                log.debug(BizUtils.logInfo("등록오류"));
                return new ApiResponse(false, "등록 오류");
            }

            log.debug(BizUtils.logVoKey(outputDto));
            return new ApiResponse(true, "등록 성공", outputDto);

        } catch (Exception e) {
            log.debug(BizUtils.logInfo("Exception"));
            return new ApiResponse(false, "등록 처리 중 오류가 발생했습니다.");
        }
    }
}

