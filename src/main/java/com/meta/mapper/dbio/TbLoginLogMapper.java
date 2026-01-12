package com.meta.mapper.dbio;

import com.meta.dto.TbLoginLogDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author     : somnus21
 * @file       : TbLoginLogMapper.java
 * @version    : 1.0.0
 * @createDate : 2025-12-04 오후 3:22:14
 * @updateDate : 2025-12-04 오후 3:22:14
 * @desc       : 로그인 로그 tb_login_log 
 */
@Mapper
public interface TbLoginLogMapper {
    public TbLoginLogDto getData(TbLoginLogDto tbLoginLogDto);
    public int insertData(TbLoginLogDto tbLoginLogDto);
    public List<TbLoginLogDto> getListData(TbLoginLogDto tbLoginLogDto);
}
