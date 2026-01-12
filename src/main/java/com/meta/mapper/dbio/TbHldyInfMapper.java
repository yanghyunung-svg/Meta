package com.meta.mapper.dbio;

import com.meta.dto.TbHldyInfDto;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
/**
 * @author     : somnus21
 * @file       : TbHldyInfMapper.java
 * @version    : 1.0.0
 * @createDate : 2026-01-12 오후 2:50:06
 * @updateDate : 2026-01-12 오후 2:50:06
 * @desc       : 휴일관리 TB_HLDY_INF 
 */
@Mapper
public interface TbHldyInfMapper {
    public TbHldyInfDto getData(TbHldyInfDto tbHldyInfDto);
    public TbHldyInfDto getLockData(TbHldyInfDto tbHldyInfDto);
    public int insertData(TbHldyInfDto tbHldyInfDto);
    public int updateData(TbHldyInfDto tbHldyInfDto);
    public int deleteData(TbHldyInfDto tbHldyInfDto);
    public int countData(TbHldyInfDto tbHldyInfDto);
    public List<TbHldyInfDto> getListData(TbHldyInfDto tbHldyInfDto);
}
