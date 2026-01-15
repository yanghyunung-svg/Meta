package com.meta.mapper.meta;

import com.meta.dto.TbCodeDto;
import com.meta.dto.TbTelgmDtlBscDto;
import com.meta.dto.TbTelgmKndBscDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author     : somnus21
 * @file       : TelgmMapper.java
 * @version    : 1.0.0
 * @createDate : 2026-01-14 오후 1:15:25
 * @updateDate : 2026-01-14 오후 1:15:25
 * @desc       : 전문
 */
@Mapper
public interface TelgmMapper {
    public List<TbTelgmKndBscDto> getTelgmKndListData(TbTelgmKndBscDto tbTelgmKndBscDto);
    public List<TbTelgmDtlBscDto> getTelgmDtlListData(TbTelgmDtlBscDto tbTelgmDtlBscDto);
    public List<TbCodeDto> getDlngSeCdComboData(TbTelgmKndBscDto tbTelgmKndBscDto);
    public List<TbCodeDto> getTelgmKndCdComboData(TbTelgmKndBscDto tbTelgmKndBscDto);
    public List<TbCodeDto> getTaskSeCdComboData(TbTelgmKndBscDto tbTelgmKndBscDto);
}
