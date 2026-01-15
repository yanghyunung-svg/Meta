package com.meta.mapper.dbio;

import com.meta.dto.TbTelgmDtlBscDto;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
/**
 * @author     : somnus21
 * @file       : TbTelgmDtlBscMapper.java
 * @version    : 1.0.0
 * @createDate : 2026-01-14 오전 11:06:28
 * @updateDate : 2026-01-14 오전 11:06:28
 * @desc       : 전문상세기본 tb_telgm_dtl_bsc 
 */
@Mapper
public interface TbTelgmDtlBscMapper {
    public TbTelgmDtlBscDto getData(TbTelgmDtlBscDto tbTelgmDtlBscDto);
    public TbTelgmDtlBscDto getLockData(TbTelgmDtlBscDto tbTelgmDtlBscDto);
    public int insertData(TbTelgmDtlBscDto tbTelgmDtlBscDto);
    public int updateData(TbTelgmDtlBscDto tbTelgmDtlBscDto);
    public int deleteData(TbTelgmDtlBscDto tbTelgmDtlBscDto);
    public int countData(TbTelgmDtlBscDto tbTelgmDtlBscDto);
    public List<TbTelgmDtlBscDto> getListData(TbTelgmDtlBscDto tbTelgmDtlBscDto);
}
