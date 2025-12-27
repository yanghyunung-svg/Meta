package com.meta.mapper;

import com.meta.dto.TbStdDmnBscDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author     : somnus21
 * @file       : TbStdDmnBscMapper.java
 * @version    : 1.0.0
 * @createDate : 2025-12-10 오후 2:33:57
 * @updateDate : 2025-12-10 오후 2:33:57
 * @desc       : 표준도메인기본 TB_STD_DMN_BSC 
 */
@Mapper
public interface TbStdDmnBscMapper {
    public TbStdDmnBscDto getData(TbStdDmnBscDto tbStdDmnBscDto);
    public TbStdDmnBscDto getLockData(TbStdDmnBscDto tbStdDmnBscDto);
    public int insertData(TbStdDmnBscDto tbStdDmnBscDto);
    public int updateData(TbStdDmnBscDto tbStdDmnBscDto);
    public int deleteData(TbStdDmnBscDto tbStdDmnBscDto);
    public int countCode(TbStdDmnBscDto tbStdDmnBscDto);
    public List<TbStdDmnBscDto> getListData(TbStdDmnBscDto tbStdDmnBscDto);
    public List<TbStdDmnBscDto> getDmnComboData();
}
