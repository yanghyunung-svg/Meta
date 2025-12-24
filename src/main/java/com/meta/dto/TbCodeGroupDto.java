package com.meta.dto;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * @author     : somnus21
 * @file       : TbCodeGroupDto.java
 * @version    : 1.0.0
 * @createDate : 2025-11-28 오후 1:43:47
 * @updateDate : 2025-11-28 오후 1:43:47
 * @desc       : 코드그룹기본 tb_code_group
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TbCodeGroupDto {  
    private String     grpCd;                           // 그룹코드 VARCHAR(50)
    private String     grpNm;                           // 그룹명 VARCHAR(200)
    private String     stat;                            // 상태코드 VARCHAR(1)
    private Integer    ord;                             // 정렬순서 INT
    private String     rmk;                             // 비고 VARCHAR(500)
    private String     crtDttm;                         // 생성일시 DATETIME
    private String     crtId;                           // 생성자ID VARCHAR(50)
    private String     updDttm;                         // 변경일시 DATETIME
    private String     updId;                           // 변경자ID VARCHAR(50)
    private int         exist;      // 0: 사용 가능, 1: 중복
}
