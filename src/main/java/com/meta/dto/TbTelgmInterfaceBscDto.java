package com.meta.dto;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * @author     : somnus21
 * @file       : TbTelgmInterfaceBscDto.java
 * @version    : 1.0.0
 * @createDate : 2026-01-21 오후 2:31:22
 * @updateDate : 2026-01-21 오후 2:31:22
 * @desc       : 전문인터페이스기본 tb_telgm_interface_bsc
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TbTelgmInterfaceBscDto {  
    private String     ifId;                            // 인터페이스ID VARCHAR(20)
    private String     instCd;                          // 기관코드 VARCHAR(4)
    private String     taskSeCd;                        // 업무구분코드 VARCHAR(3)
    private String     hndlEstblSeCd;                   // 취급개설구분코드 VARCHAR(1)
    private String     telgmTypeCd;                     // 전문유형코드 VARCHAR(3)
    private String     telgmKndCd;                      // 전문종별코드 VARCHAR(4)
    private String     dlngSeCd;                        // 거래구분코드 VARCHAR(6)
    private String     telgmNm;                         // 전문명 VARCHAR(100)
    private String     telgmExpln;                      // 전문설명 TEXT
    private String     sttsCd;                          // 상태코드 VARCHAR(1)
    private String     crtDttm;                         // 생성일시 DATETIME
    private String     crtId;                           // 생성자 VARCHAR(50)
    private String     updDttm;                         // 수정일시 DATETIME
    private String     updId;                           // 수정자 VARCHAR(50)
    private String     func;
    private String     instNm;                          // 기관명
    private String     taskSeNm;                        // 업무구분명
}
