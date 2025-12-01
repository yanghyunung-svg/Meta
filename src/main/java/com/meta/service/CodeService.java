package com.meta.service;

import com.common.utils.ApiResponse;
import com.common.utils.BizUtils;
import com.meta.dto.TbCodeDto;
import com.meta.mapper.TbCodeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/** 
 *@ID       : CodeService
 *@NAME     : 코드기본 Service
 */
@Service
public class CodeService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TbCodeMapper tbCodeMapper;


    /**
     * method   : getListData
     * desc     : 코드기본 목록 조회
     */
    public List<TbCodeDto> getListData(TbCodeDto inputDto)  {
        log.debug(BizUtils.logInfo("START"));
        return tbCodeMapper.getListData(inputDto);
    }

    /**
     * method   : getData
     * desc     : 코드기본 상세 조회
     */
    public TbCodeDto getData(TbCodeDto inputDto)  {
        log.debug(BizUtils.logInfo("START"));
        return tbCodeMapper.getData(inputDto);
    }

    /**
     * method   : insertData
     * desc     : 코드기본 등록
     */
    public ApiResponse<Void> insertData(TbCodeDto inputDto)  {
        log.debug(BizUtils.logInfo("START"));
        log.debug(BizUtils.logVo(inputDto));
        try {
            TbCodeDto outputDto = tbCodeMapper.getData(inputDto);

            if (outputDto != null) {
                return new ApiResponse<Void>(false, "기등록된 데이타가 있습니다. =" + outputDto.getCdNm());
            }

            if (tbCodeMapper.insertData(inputDto) == 0) {
                return new ApiResponse<Void>(false, "등록 오류");
            }

            return new ApiResponse<Void>(true, "등록 성공");

        } catch (Exception e) {
            return new ApiResponse<Void>(false, "등록 처리 중 오류가 발생했습니다.");
        }
    }


    /**
     * method   : updateData
     * desc     : 코드기본 변경
     */
    public ApiResponse<Void> updateData(TbCodeDto inputDto)  {
        log.debug(BizUtils.logInfo("START"));
        log.debug(BizUtils.logVo(inputDto));
        try {
            log.debug(BizUtils.logInfo("SELECT", inputDto.getCd()));
            TbCodeDto outputDto = tbCodeMapper.getData(inputDto);

            if (outputDto == null) {
                return new ApiResponse<Void>(false, "수정할 데이타가 없습니다 =" + outputDto.getCdNm());
            }

            if (tbCodeMapper.updateData(inputDto) == 0) {
                return new ApiResponse<Void>(false, "수정 오류");
            }

            log.debug(BizUtils.logVoKey(outputDto));
            return new ApiResponse<Void>(true, "수정 성공");

        } catch (Exception e) {
            return new ApiResponse<Void>(false, "수정 처리 중 오류가 발생했습니다.");
        }
    }
}

