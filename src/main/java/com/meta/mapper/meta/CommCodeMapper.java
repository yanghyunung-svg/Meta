package com.meta.mapper.meta;

import com.meta.dto.CommCodeDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author     : somnus21
 * @file       : CommCodeMapper.java
 * @version    : 1.0.0
 * @createDate : 2025-11-28 오후 3:11:14
 * @updateDate : 2025-11-28 오후 3:11:14
 * @desc       : 코드기본 tb_code_detl 
 */
@Mapper
public interface CommCodeMapper {
    public List<CommCodeDto> getCommCodeList(CommCodeDto commCodeDto);
}
