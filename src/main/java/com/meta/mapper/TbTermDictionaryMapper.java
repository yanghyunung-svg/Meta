package com.meta.mapper;

import com.meta.dto.TbTermDictionaryDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author     : somnus21
 * @file       : TbTermDictionaryMapper.java
 * @version    : 1.0.0
 * @createDate : 2025-11-20 오전 10:27:59
 * @updateDate : 2025-11-20 오전 10:27:59
 * @desc       : 용어사전 tb_term_dictionary 
 */
@Mapper
public interface TbTermDictionaryMapper {
    public TbTermDictionaryDto getData(TbTermDictionaryDto tbTermDictionaryDto);
    public TbTermDictionaryDto getLockData(TbTermDictionaryDto tbTermDictionaryDto);
    public List<TbTermDictionaryDto> getListData(TbTermDictionaryDto tbTermDictionaryDto);
    public int insertData(TbTermDictionaryDto tbTermDictionaryDto);
    public int updateData(TbTermDictionaryDto tbTermDictionaryDto);
    public int countCode(TbTermDictionaryDto tbTermDictionaryDto);
}
