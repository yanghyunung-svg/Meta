package com.meta.dto;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * @author     : somnus21
 * @file       : TbFnncJntCdInfoDto.java
 * @version    : 1.0.0
 * @createDate : 2026-01-09 오후 1:09:05
 * @updateDate : 2026-01-09 오후 1:09:05
 * @desc       : 금융공동코드정보 TB_FNNC_JNT_CD_INFO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TbFnncJntCdInfoDto {
    private String     func;
    private String     jntCd;                           // 공동코드 VARCHAR(7)
    private String     rprsFnstCd;                      // 대표기관코드 VARCHAR(3)
    private String     fnstCd;                          // 기관코드 VARCHAR(3)
    private String     fnstNm;                          // 기관명 VARCHAR(100)
    private String     storNm;                          // 점포명 VARCHAR(100)
    private String     telno;                           // 전화번호 VARCHAR(20)
    private String     fxno;                            // 팩스번호 VARCHAR(20)
    private String     zip;                             // 우편번호 VARCHAR(20)
    private String     addr;                            // 주소 VARCHAR(255)
    private String     se;                              // 구분 VARCHAR(255)
    private String     clsgMngBrnch;                    // 폐쇄관리지점 VARCHAR(10)
    private String     sttsCd;                          // 상태코드 VARCHAR(2)
    private String     crtId;                           // 등록자ID VARCHAR(30)
    private String     crtDttm;                         // 등록일시 DATETIME
    private String     updId;                           // 수정자ID VARCHAR(30)
    private String     updDttm;                         // 수정일시 DATETIME
}
