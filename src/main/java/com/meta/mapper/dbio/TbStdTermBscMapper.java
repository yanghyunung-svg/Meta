package com.meta.mapper.dbio;

import com.meta.dto.TbStdTermBscDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author     : somnus21
 * @file       : TbStdTermBscMapper.java
 * @version    : 1.0.0
 * @createDate : 2025-11-20 오전 10:27:59
 * @updateDate : 2025-11-20 오전 10:27:59
 * @desc       : 용어사전 tb_term_dictionary 
 */
@Mapper
public interface TbStdTermBscMapper {
    public TbStdTermBscDto getData(TbStdTermBscDto tbStdTermBscDto);
    public TbStdTermBscDto getLockData(TbStdTermBscDto tbStdTermBscDto);
    public List<TbStdTermBscDto> getListData(TbStdTermBscDto tbStdTermBscDto);
    public int insertData(TbStdTermBscDto tbStdTermBscDto);
    public int updateData(TbStdTermBscDto tbStdTermBscDto);
    public int deleteData(TbStdTermBscDto tbStdTermBscDto);
    public int countData(TbStdTermBscDto tbStdTermBscDto);
}
