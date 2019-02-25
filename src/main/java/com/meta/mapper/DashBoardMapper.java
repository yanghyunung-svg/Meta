package com.meta.mapper;

import com.meta.dto.DashBoardDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author     : somnus21
 * @file       : DashBoardMapper.java
 * @version    : 1.0.0
 * @createDate : 2025-11-20 오전 10:27:59
 * @updateDate : 2025-11-20 오전 10:27:59
 * @desc       : DashBoard Mapper
 */
@Mapper
public interface DashBoardMapper {
    public DashBoardDto getData(DashBoardDto dashBoardDto);
    public List<DashBoardDto> getListData(DashBoardDto dashBoardDto);
}
