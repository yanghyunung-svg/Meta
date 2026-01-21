package com.meta.mapper.dbio;

import com.meta.dto.TbTelgmInterfaceBscDto;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
/**
 * @author     : somnus21
 * @file       : TbTelgmInterfaceBscMapper.java
 * @version    : 1.0.0
 * @createDate : 2026-01-21 오후 2:31:22
 * @updateDate : 2026-01-21 오후 2:31:22
 * @desc       : 전문인터페이스기본 tb_telgm_interface_bsc 
 */
@Mapper
public interface TbTelgmInterfaceBscMapper {
    public TbTelgmInterfaceBscDto getData(TbTelgmInterfaceBscDto tbTelgmInterfaceBscDto);
    public TbTelgmInterfaceBscDto getLockData(TbTelgmInterfaceBscDto tbTelgmInterfaceBscDto);
    public int insertData(TbTelgmInterfaceBscDto tbTelgmInterfaceBscDto);
    public int updateData(TbTelgmInterfaceBscDto tbTelgmInterfaceBscDto);
    public int deleteData(TbTelgmInterfaceBscDto tbTelgmInterfaceBscDto);
    public int countData(TbTelgmInterfaceBscDto tbTelgmInterfaceBscDto);
    public List<TbTelgmInterfaceBscDto> getListData(TbTelgmInterfaceBscDto tbTelgmInterfaceBscDto);
}
