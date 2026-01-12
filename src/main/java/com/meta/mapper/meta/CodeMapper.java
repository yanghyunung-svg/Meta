package com.meta.mapper.meta;

import com.meta.dto.TbCodeDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author     : somnus21
 * @file       : TbCodeMapper.java
 * @version    : 1.0.0
 * @createDate : 2025-11-28 오후 3:11:14
 * @updateDate : 2025-11-28 오후 3:11:14
 * @desc       : 코드기본 tb_code 
 */
@Mapper
public interface CodeMapper {
    public List<TbCodeDto> getGrpData(TbCodeDto tbCodeDto);
}
