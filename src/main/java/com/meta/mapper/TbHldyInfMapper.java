package com.meta.mapper;

import com.meta.dto.TbHldyInfDto;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

/**
 * @author     : somnus21
 * @file       : TbHldyInfMapper.java
 * @version    : 1.0.0
 * @createDate : 2026-01-07 오후 4:58:18
 * @updateDate : 2026-01-07 오후 4:58:18
 * @desc       : 휴일관리 TB_HLDY_INF 
 */
@Mapper
public interface TbHldyInfMapper {
    public TbHldyInfDto getData(TbHldyInfDto tbHldtInfDto);
    public TbHldyInfDto getLockData(TbHldyInfDto tbHldtInfDto);
    public List<TbHldyInfDto> getListData(TbHldyInfDto tbHldtInfDto);
    public int insertData(TbHldyInfDto tbHldtInfDto);
    public int updateData(TbHldyInfDto tbHldtInfDto);
    public int deleteData(TbHldyInfDto tbHldtInfDto);
    public int countCode(TbHldyInfDto tbHldtInfDto);
}
