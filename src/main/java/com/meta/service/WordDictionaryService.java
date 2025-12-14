package com.meta.service;

import com.common.utils.ApiResponse;
import com.common.utils.BizUtils;
import com.meta.dto.TbWordDictionaryDto;
import com.meta.mapper.TbWordDictionaryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/** 
 *@ ID       : WordDictionaryService
 *@ NAME     : 단어사전 Service
 */
@Service
public class WordDictionaryService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TbWordDictionaryMapper tbWordDictionaryMapper;


    /**
     * method   : getListData
     * desc     : 단어사전 목록 조회
     */
    public List<TbWordDictionaryDto> getListData(TbWordDictionaryDto inputDto)  {
        log.debug(BizUtils.logInfo("START"));
        return tbWordDictionaryMapper.getListData(inputDto);
    }

    /**
     * method   : getData
     * desc     : 단어사전 상세 조회
     */
    public TbWordDictionaryDto getData(TbWordDictionaryDto inputDto)  {
        log.debug(BizUtils.logInfo("START", BizUtils.logVo(inputDto)));
        TbWordDictionaryDto outputDto = tbWordDictionaryMapper.getData(inputDto);
        log.debug(BizUtils.logInfo("END", BizUtils.logVo(outputDto)));
        return outputDto;
    }
    /**
     * method   : getDataByName
     * desc     : 단어사전 단어명 상세 조회
     */
    public TbWordDictionaryDto getDataByName(TbWordDictionaryDto inputDto)  {
        log.debug(BizUtils.logInfo("START", BizUtils.logVo(inputDto)));
        TbWordDictionaryDto outputDto = tbWordDictionaryMapper.getDataByName(inputDto);
        log.debug(BizUtils.logInfo("END", BizUtils.logVo(outputDto)));
        return outputDto;
    }

    /**
     * method   : insertData
     * desc     : 단어사전 등록
     */
    public ApiResponse<Void> insertData(TbWordDictionaryDto inputDto)  {
        log.debug(BizUtils.logInfo("START"));
        log.debug(BizUtils.logVo(inputDto));
        try {
            TbWordDictionaryDto outputDto = tbWordDictionaryMapper.getDataByName(inputDto);

            if (outputDto != null) {
                return new ApiResponse<Void>(false, "기등록된 데이타가 있습니다." + outputDto.getEngAbbrNm());
            }

            if (tbWordDictionaryMapper.insertData(inputDto) == 0) {
                return new ApiResponse<Void>(false, "등록 오류");
            }

            return new ApiResponse<Void>(true, "등록 성공");

        } catch (Exception e) {
            return new ApiResponse<Void>(false, "등록 처리 중 오류가 발생했습니다.");
        }
    }


    /**
     * method   : updateData
     * desc     : 단어사전 변경
     */
    public ApiResponse<Void> updateData(TbWordDictionaryDto inputDto)  {
        log.debug(BizUtils.logInfo("START"));
        log.debug(BizUtils.logVo(inputDto));

        try {
            TbWordDictionaryDto outputDto = tbWordDictionaryMapper.getData(inputDto);

            if (outputDto == null) {
                return new ApiResponse<Void>(false, "수정할 데이타가 없습니다");
            }

            if (tbWordDictionaryMapper.updateData(inputDto) == 0) {
                return new ApiResponse<Void>(false, "수정 오류");
            }

            log.debug(BizUtils.logVoKey(outputDto));
            return new ApiResponse<Void>(true, "수정 성공");

        } catch (Exception e) {
            return new ApiResponse<Void>(false, "수정 처리 중 오류가 발생했습니다.");
        }
    }
}

