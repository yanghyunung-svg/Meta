package com.meta.mapper;

import com.meta.dto.TbCodeGroupDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author     : somnus21
 * @file       : TbCodeGroupMapper.java
 * @version    : 1.0.0
 * @createDate : 2025-11-28 오후 1:43:47
 * @updateDate : 2025-11-28 오후 1:43:47
 * @desc       : 코드그룹기본 tb_code_group 
 */
@Mapper
public interface TbCodeGroupMapper {
    public TbCodeGroupDto getData(TbCodeGroupDto tbCodeGroupDto);
    public TbCodeGroupDto getLockData(TbCodeGroupDto tbCodeGroupDto);
    public int insertData(TbCodeGroupDto tbCodeGroupDto);
    public int updateData(TbCodeGroupDto tbCodeGroupDto);
    public int deleteData(TbCodeGroupDto tbCodeGroupDto);
    public List<TbCodeGroupDto> getListData(TbCodeGroupDto tbCodeGroupDto);
    public int countCode(TbCodeGroupDto tbCodeGroupDto);
}
