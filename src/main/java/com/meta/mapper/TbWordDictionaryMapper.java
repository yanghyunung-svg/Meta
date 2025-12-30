package com.meta.mapper;

import com.meta.dto.TbWordDictionaryDto;
import com.meta.dto.WordMappingDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author     : somnus21
 * @file       : TbWordDictionaryMapper.java
 * @version    : 1.0.0
 * @createDate : 2025-11-20 오전 10:38:58
 * @updateDate : 2025-11-20 오전 10:38:58
 * @desc       : 단어사전 tb_word_dictionary 
 */
@Mapper
public interface TbWordDictionaryMapper {
    public TbWordDictionaryDto getData(TbWordDictionaryDto tbWordDictionaryDto);
    public TbWordDictionaryDto getDataByName(TbWordDictionaryDto tbWordDictionaryDto);
    public TbWordDictionaryDto getLockData(TbWordDictionaryDto tbWordDictionaryDto);
    public int insertData(TbWordDictionaryDto tbWordDictionaryDto);
    public int updateData(TbWordDictionaryDto tbWordDictionaryDto);
    public int deleteData(TbWordDictionaryDto tbWordDictionaryDto);
    public List<TbWordDictionaryDto> getListData(TbWordDictionaryDto tbWordDictionaryDto);
    public List<WordMappingDto> getAllData();
    public int countCode(TbWordDictionaryDto tbWordDictionaryDto);
}
