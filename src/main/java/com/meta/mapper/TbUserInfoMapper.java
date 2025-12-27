package com.meta.mapper;

import com.meta.dto.TbStdDmnBscDto;
import com.meta.dto.TbUserInfoDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author     : somnus21
 * @file       : TbUserInfoMapper.java
 * @version    : 1.0.0
 * @createDate : 2025-11-26 오후 3:41:46
 * @updateDate : 2025-11-26 오후 3:41:46
 * @desc       : 사용자기본 tb_user 
 */
@Mapper
public interface TbUserInfoMapper {
    public TbUserInfoDto getData(TbUserInfoDto tbUserDto);
    public TbUserInfoDto getLockData(TbUserInfoDto tbUserDto);
    public int insertData(TbUserInfoDto tbUserDto);
    public int updateData(TbUserInfoDto tbUserDto);
    public int deleteData(TbUserInfoDto tbUserDto);
    public int countCode(TbUserInfoDto tbUserDto);
    public List<TbUserInfoDto> getListData(TbUserInfoDto tbUserDto);
}
