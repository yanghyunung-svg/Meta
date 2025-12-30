package com.meta.dto;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * @author     : somnus21
 * @file       : TbStdDmnBscDto.java
 * @version    : 1.0.0
 * @createDate : 2025-12-10 오후 2:33:57
 * @updateDate : 2025-12-10 오후 2:33:57
 * @desc       : 표준도메인기본 TB_STD_DMN_BSC
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TbStdDmnBscDto {  
    private String     dmnNm;                           // 도메인명 VARCHAR(100)
    private String     dmnClsfNm;                       // 도메인분류명 VARCHAR(100)
    private String     dmnEngNm;                        // 도메인영문명 VARCHAR(100)
    private String     dmnAtrb;                         // 도메인속성 VARCHAR(100)
    private String     sttsCd;                          // 상태코드 VARCHAR(1)
    private String     sttsNm;                          // 상태코드명
    private String     crtDttm;                         // 생성일시 DATETIME
    private String     crtId;                           // 생성자ID VARCHAR(50)
    private String     updDttm;                         // 변경일시 DATETIME
    private String     updId;                           // 변경자ID VARCHAR(50)
    private String     func;
}
