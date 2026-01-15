package com.meta.dto;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * @author     : somnus21
 * @file       : TbTelgmDtlBscDto.java
 * @version    : 1.0.0
 * @createDate : 2026-01-14 오전 11:06:28
 * @updateDate : 2026-01-14 오전 11:06:28
 * @desc       : 전문상세기본 tb_telgm_dtl_bsc
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TbTelgmDtlBscDto {  
    private String     instCd;                          // 기관코드 VARCHAR(4)
    private String     taskSeCd;                        // 업무구분코드 VARCHAR(3)
    private String     telgmKndCd;                      // 전문종별코드 VARCHAR(4)
    private String     dlngSeCd;                        // 거래구분코드 VARCHAR(6)
    private Integer    artclSeq;                        // 항목순서 INT
    private String     artclNm;                         // 항목명 VARCHAR(100)
    private String     artclEngNm;                      // 항목영문명 VARCHAR(100)
    private String     artclAtrb;                       // 항목속성 VARCHAR(500)
    private Integer    artclLen;                        // 항목길이 INT
    private String     artclBscVl;                      // 항목기본값 VARCHAR(100)
    private String     artclTypeCd;                     // 항목유형코드 1=FIELD 2=ARRAY VARCHAR(100)
    private Integer    artclDpth;                       // 항목깊이 1,2 INT
    private String     reptField;                       // 반복FIELD VARCHAR(100)
    private Integer    reptFixNmtm;                     // 반복고정횟수 INT
    private String     sttsCd;                          // 상태코드 VARCHAR(1)
    private String     crtDttm;                         // 생성일시 DATETIME
    private String     crtId;                           // 생성자 VARCHAR(50)
    private String     updDttm;                         // 수정일시 DATETIME
    private String     updId;                           // 수정자 VARCHAR(50)
    private String     func;
}
