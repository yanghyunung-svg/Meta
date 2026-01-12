package com.meta.dto;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * @author     : somnus21
 * @file       : TbFnstCdInfoDto.java
 * @version    : 1.0.0
 * @createDate : 2026-01-12 오후 2:45:59
 * @updateDate : 2026-01-12 오후 2:45:59
 * @desc       : 금융기관코드정보 TB_FNST_CD_INFO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TbFnstCdInfoDto {  
    private String     fnstCd;                          // 금융기관코드 VARCHAR(3)
    private String     rprsFnstCd;                      // 대표금융기관코드 VARCHAR(3)
    private String     swiftCd;                         // SWIFT코드 VARCHAR(11)
    private String     fnstNm;                          // 금융기관명 VARCHAR(100)
    private String     fnstEngNm;                       // 금융기관영문명 VARCHAR(100)
    private String     aplcnYmd;                        // 적용일자 VARCHAR(8)
    private String     sttsCd;                          // 상태코드 VARCHAR(02)
    private String     crtId;                           // 등록자ID VARCHAR(30)
    private String     crtDttm;                         // 등록일시 DATETIME
    private String     updId;                           // 수정자ID VARCHAR(30)
    private String     updDttm;                         // 수정일시 DATETIME
    private String     func;
}
