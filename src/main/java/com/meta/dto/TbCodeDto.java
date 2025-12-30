package com.meta.dto;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * @author     : somnus21
 * @file       : TbCodeDto.java
 * @version    : 1.0.0
 * @createDate : 2025-11-28 오후 3:11:14
 * @updateDate : 2025-11-28 오후 3:11:14
 * @desc       : 코드기본 tb_code
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TbCodeDto {  
  private String     grpCd;                             // 그룹코드 VARCHAR(50)
  private String     grpNm;                            // 그룹코드명 VARCHAR(50)
  private String     cd;                              // 상세코드 VARCHAR(50)
  private String     cdNm;                            // 상세코드명 VARCHAR(200)
  private String     stat;                           // 상태코드 VARCHAR(1)
  private Integer    ord;                             // 정렬순서 INT
  private String     rmk;                             // 비고 VARCHAR(500)
  private String     crtDttm;                         // 생성일시 DATETIME
  private String     crtId;                           // 생성자ID VARCHAR(50)
  private String     updDttm;                         // 변경일시 DATETIME
  private String     updId;                           // 변경자ID VARCHAR(50)
    private String    func;
}
