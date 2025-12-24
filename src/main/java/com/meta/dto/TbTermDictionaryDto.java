package com.meta.dto;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * @author     : somnus21
 * @file       : TbTermDictionaryDto.java
 * @version    : 1.0.0
 * @createDate : 2025-11-20 오전 10:59:53
 * @updateDate : 2025-11-20 오전 10:59:53
 * @desc       : 용어사전 tb_term_dictionary
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TbTermDictionaryDto {
    private String     trmNm;                           // 용어명 VARCHAR(100)
    private String     engNm;                           // 영문명 VARCHAR(100)
    private String     trmExpln;                        // 용어설명 TEXT
    private String     dmnNm;                           // 도메인명 VARCHAR(100)
    private String     stat;                            // 상태 0=등록 1=사용 9=미사용
    private String     crtDttm;                         // 생성일시 DATETIME
    private String     crtId;                           // 생성자ID VARCHAR(50)
    private String     updDttm;                         // 변경일시 DATETIME
    private String     updId;                           // 변경자ID VARCHAR(50)
    private int         exist;      // 0: 사용 가능, 1: 중복
}
