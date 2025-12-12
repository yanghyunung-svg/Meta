package com.meta.mapper;

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
public interface TbCodeMapper {
    public TbCodeDto getData(TbCodeDto tbCodeDto);
    public TbCodeDto getLockData(TbCodeDto tbCodeDto);
    public int insertData(TbCodeDto tbCodeDto);
    public int updateData(TbCodeDto tbCodeDto);
    public int deleteData(TbCodeDto tbCodeDto);
    public List<TbCodeDto> getListData(TbCodeDto tbCodeDto);
    public List<TbCodeDto> getAllData(TbCodeDto tbCodeDto);
    public int countCode(TbCodeDto tbCodeDto);
}
