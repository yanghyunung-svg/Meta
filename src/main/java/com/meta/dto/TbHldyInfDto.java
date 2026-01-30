package com.meta.dto;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * @author     : somnus21
 * @file       : TbHldyInfDto.java
 * @version    : 1.0.0
 * @createDate : 2026-01-12 오후 2:50:06
 * @updateDate : 2026-01-12 오후 2:50:06
 * @desc       : 휴일관리 TB_HLDY_INF
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TbHldyInfDto {  
    private String     crtrYmd;                         // 기준일자 VARCHAR(8)
    private String     hldyNm;                          // 휴일명 VARCHAR(50)
    private String     hldySeCd;                        // 휴일구분코드 VARCHAR(2)
    private String     dowSeCd;                         // 요일코드 VARCHAR(2)
    private String     rmk;
    private String     crtId;                           // 등록자ID VARCHAR(30)
    private String     crtDttm;                         // 등록일시 DATETIME
    private String     updId;                           // 수정자ID VARCHAR(30)
    private String     updDttm;                         // 수정일시 DATETIME
    private String     func;
    private String     sttsCd;
    private String     inqYear;
    private String     inqMonth;
}
