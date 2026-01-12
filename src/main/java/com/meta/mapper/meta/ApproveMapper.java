package com.meta.mapper.meta;

import com.meta.dto.ApproveDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author     : somnus21
 * @file       : ApproveMapper.java
 * @version    : 1.0.0
 * @createDate : 2025-11-20 오전 10:27:59
 * @updateDate : 2025-11-20 오전 10:27:59
 * @desc       : ApproveMapper
 */
@Mapper
public interface ApproveMapper {
    public List<ApproveDto> getListData(ApproveDto approveDto);
}
