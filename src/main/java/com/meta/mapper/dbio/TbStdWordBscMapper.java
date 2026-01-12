package com.meta.mapper.dbio;

import com.meta.dto.TbStdWordBscDto;
import com.meta.dto.WordMappingDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author     : somnus21
 * @file       : TbStdWordBscMapper.java
 * @version    : 1.0.0
 * @createDate : 2025-11-20 오전 10:38:58
 * @updateDate : 2025-11-20 오전 10:38:58
 * @desc       : 단어사전 tb_word_dictionary 
 */
@Mapper
public interface TbStdWordBscMapper {
    public TbStdWordBscDto getData(TbStdWordBscDto tbStdWordBscDto);
    public TbStdWordBscDto getDataByName(TbStdWordBscDto tbStdWordBscDto);
    public TbStdWordBscDto getLockData(TbStdWordBscDto tbStdWordBscDto);
    public int insertData(TbStdWordBscDto tbStdWordBscDto);
    public int updateData(TbStdWordBscDto tbStdWordBscDto);
    public int deleteData(TbStdWordBscDto tbStdWordBscDto);
    public List<TbStdWordBscDto> getListData(TbStdWordBscDto tbStdWordBscDto);
    public List<WordMappingDto> getAllData();
    public int countData(TbStdWordBscDto tbStdWordBscDto);
}
