package com.meta.mapper.dbio;

import com.meta.dto.TbTelgmKndBscDto;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
/**
 * @author     : somnus21
 * @file       : TbTelgmKndBscMapper.java
 * @version    : 1.0.0
 * @createDate : 2026-01-14 오후 1:15:25
 * @updateDate : 2026-01-14 오후 1:15:25
 * @desc       : 전문종류기본 tb_telgm_knd_bsc 
 */
@Mapper
public interface TbTelgmKndBscMapper {
    public TbTelgmKndBscDto getData(TbTelgmKndBscDto tbTelgmKndBscDto);
    public TbTelgmKndBscDto getLockData(TbTelgmKndBscDto tbTelgmKndBscDto);
    public int insertData(TbTelgmKndBscDto tbTelgmKndBscDto);
    public int updateData(TbTelgmKndBscDto tbTelgmKndBscDto);
    public int deleteData(TbTelgmKndBscDto tbTelgmKndBscDto);
    public int countData(TbTelgmKndBscDto tbTelgmKndBscDto);
    public List<TbTelgmKndBscDto> getListData(TbTelgmKndBscDto tbTelgmKndBscDto);
}
