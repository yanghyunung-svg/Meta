package com.meta.mapper;

import com.meta.dto.TbFnncJntCdInfoDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author     : somnus21
 * @file       : TbFnncJntCdInfoMapper.java
 * @version    : 1.0.0
 * @createDate : 2026-01-09 오후 1:09:05
 * @updateDate : 2026-01-09 오후 1:09:05
 * @desc       : 금융공동코드정보 TB_FNNC_JNT_CD_INFO 
 */
@Mapper
public interface TbFnncJntCdInfoMapper {
    public TbFnncJntCdInfoDto getData(TbFnncJntCdInfoDto tbFnncJntCdInfoDto);
    public TbFnncJntCdInfoDto getLockData(TbFnncJntCdInfoDto tbFnncJntCdInfoDto);
    public List<TbFnncJntCdInfoDto> getListData(TbFnncJntCdInfoDto tbFnncJntCdInfoDto);
    public int insertData(TbFnncJntCdInfoDto tbFnncJntCdInfoDto);
    public int updateData(TbFnncJntCdInfoDto tbFnncJntCdInfoDto);
    public int deleteData(TbFnncJntCdInfoDto tbFnncJntCdInfoDto);
    public int countCode(TbFnncJntCdInfoDto tbFnncJntCdInfoDto);
}
