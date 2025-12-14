package com.meta.service;

import com.common.utils.ApiResponse;
import com.common.utils.BizUtils;
import com.meta.dto.TbStdDmnBscDto;
import com.meta.mapper.TbStdDmnBscMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *@ ID       : StdDmnBscService
 *@ NAME     : 표준도메인 Service
 */
@Service
public class StdDmnBscService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TbStdDmnBscMapper tbStdDmnBscMapper; 


    /**
     * method   : getListData
     * desc     : 표준도메인 목록 조회
     */
    public List<TbStdDmnBscDto> getListData(TbStdDmnBscDto inputDto)  {
        log.debug(BizUtils.logInfo("START"));
        return tbStdDmnBscMapper.getListData(inputDto);
    }

    /**
     * method   : getData
     * desc     : 표준도메인 상세 조회
     */
    public List<TbStdDmnBscDto> getDmnComboData()  {
        log.debug(BizUtils.logInfo("START"));
        return tbStdDmnBscMapper.getDmnComboData();
    }

    /**
     * method   : getData
     * desc     : 표준도메인 상세 조회
     */
    public TbStdDmnBscDto getData(TbStdDmnBscDto inputDto)  {
        log.debug(BizUtils.logInfo("START"));
        return tbStdDmnBscMapper.getData(inputDto);
    }

    /**
     * method   : insertData
     * desc     : 표준도메인 등록
     */
    public ApiResponse<Void> insertData(TbStdDmnBscDto inputDto)  {
        log.debug(BizUtils.logInfo("START"));
        log.debug(BizUtils.logVo(inputDto));
        try {
            TbStdDmnBscDto outputDto = tbStdDmnBscMapper.getData(inputDto);

            if (outputDto != null) {
                log.debug(BizUtils.logInfo("기등록된 데이타가 있습니다"));
                return new ApiResponse<Void>(false, "기등록된 데이타가 있습니다." + outputDto.getDmnNm());
            }

            if (tbStdDmnBscMapper.insertData(inputDto) == 0) {
                return new ApiResponse<Void>(false, "등록 오류");
            }

            return new ApiResponse<Void>(true, "등록 성공");

        } catch (Exception e) {
            log.debug(BizUtils.logInfo("Exception"));
            return new ApiResponse<Void>(false, "등록 처리 중 오류가 발생했습니다.");
        }
    }

    /**
     * method   : updateData
     * desc     : 표준도메인 변경
     */
    public ApiResponse<Void> updateData(TbStdDmnBscDto inputDto)  {
        log.debug(BizUtils.logInfo("START"));
        log.debug(BizUtils.logVo(inputDto));

        try {
            TbStdDmnBscDto outputDto = tbStdDmnBscMapper.getData(inputDto);

            if (outputDto == null) {
                return new ApiResponse<Void>(false, "수정할 데이타가 없습니다");
            }

            if (tbStdDmnBscMapper.updateData(inputDto) == 0) {
                return new ApiResponse<Void>(false, "수정 오류");
            }

            log.debug(BizUtils.logVoKey(outputDto));
            return new ApiResponse<Void>(true, "수정 성공");

        } catch (Exception e) {
            return new ApiResponse<Void>(false, "수정 처리 중 오류가 발생했습니다.");
        }
    }

}

