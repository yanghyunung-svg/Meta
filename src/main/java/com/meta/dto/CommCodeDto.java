package com.meta.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author     : somnus21
 * @file       : CommCodeDto.java
 * @version    : 1.0.0
 * @createDate : 2025-11-28 오후 3:11:14
 * @updateDate : 2025-11-28 오후 3:11:14
 * @desc       : 공통코드 통합검색 DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommCodeDto {
    private String     se;
    private String     seNm;
    private String     grpCd;
  private String     grpNm;                             // 그룹코드명 VARCHAR(50)
  private String     cd;                                // 상세코드 VARCHAR(50)
  private String     cdNm;                              // 상세코드명 VARCHAR(200)
  private String     stat;                              // 상태코드 VARCHAR(1)
  private String     rmk;                               // 비고 VARCHAR(500)
}
