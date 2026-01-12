package com.meta.mapper.dbio;

import com.meta.dto.TbFnstCdInfoDto;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
/**
 * @author     : somnus21
 * @file       : TbFnstCdInfoMapper.java
 * @version    : 1.0.0
 * @createDate : 2026-01-12 오후 2:45:59
 * @updateDate : 2026-01-12 오후 2:45:59
 * @desc       : 금융기관코드정보 TB_FNST_CD_INFO 
 */
@Mapper
public interface TbFnstCdInfoMapper {
    public TbFnstCdInfoDto getData(TbFnstCdInfoDto tbFnstCdInfoDto);
    public TbFnstCdInfoDto getLockData(TbFnstCdInfoDto tbFnstCdInfoDto);
    public int insertData(TbFnstCdInfoDto tbFnstCdInfoDto);
    public int updateData(TbFnstCdInfoDto tbFnstCdInfoDto);
    public int deleteData(TbFnstCdInfoDto tbFnstCdInfoDto);
    public int countData(TbFnstCdInfoDto tbFnstCdInfoDto);
    public List<TbFnstCdInfoDto> getListData(TbFnstCdInfoDto tbFnstCdInfoDto);
}
