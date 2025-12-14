package com.meta.service;

import com.common.utils.ApiResponse;
import com.common.utils.BizUtils;
import com.meta.dto.TbCodeGroupDto;
import com.meta.mapper.TbCodeGroupMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/** 
 *@ ID       : CodeGroupService
 *@ NAME     : 코드그룹기본 Service
 */
@Service
public class CodeGroupService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TbCodeGroupMapper tbCodeGroupMapper;


    /**
     * method   : getListData
     * desc     : 코드그룹기본 목록 조회
     */
    public List<TbCodeGroupDto> getListData(TbCodeGroupDto inputDto)  {
        log.debug(BizUtils.logInfo("START"));
        return tbCodeGroupMapper.getListData(inputDto);
    }

    /**
     * method   : getData
     * desc     : 코드그룹기본 상세 조회
     */
    public TbCodeGroupDto getData(TbCodeGroupDto inputDto)  {
        log.debug(BizUtils.logInfo("START"));
        return tbCodeGroupMapper.getData(inputDto);
    }

    /**
     * method   : insertData
     * desc     : 코드그룹기본 등록
     */
    public ApiResponse<Void> insertData(TbCodeGroupDto inputDto)  {
        log.debug(BizUtils.logInfo("START"));
        log.debug(BizUtils.logVo(inputDto));
        try {
            TbCodeGroupDto outputDto = tbCodeGroupMapper.getData(inputDto);

            if (outputDto != null) {
                return new ApiResponse<Void>(false, "기등록된 데이타가 있습니다. =" + outputDto.getGrpNm());
            }

            if (tbCodeGroupMapper.insertData(inputDto) == 0) {
                return new ApiResponse<Void>(false, "등록 오류");
            }

            return new ApiResponse<Void>(true, "등록 성공");

        } catch (Exception e) {
            return new ApiResponse<Void>(false, "등록 처리 중 오류가 발생했습니다.");
        }
    }


    /**
     * method   : updateData
     * desc     : 코드그룹기본 변경
     */
    public ApiResponse<Void> updateData(TbCodeGroupDto inputDto)  {
        log.debug(BizUtils.logInfo("START"));
        log.debug(BizUtils.logVo(inputDto));
        try {
            log.debug(BizUtils.logInfo("SELECT", inputDto.getGrpCd()));
            TbCodeGroupDto outputDto = tbCodeGroupMapper.getData(inputDto);

            if (outputDto == null) {
                return new ApiResponse<Void>(false, "수정할 데이타가 없습니다 =" + outputDto.getGrpNm());
            }

            if (tbCodeGroupMapper.updateData(inputDto) == 0) {
                return new ApiResponse<Void>(false, "수정 오류");
            }

            log.debug(BizUtils.logVoKey(outputDto));
            return new ApiResponse<Void>(true, "수정 성공");

        } catch (Exception e) {
            return new ApiResponse<Void>(false, "수정 처리 중 오류가 발생했습니다.");
        }
    }
}

