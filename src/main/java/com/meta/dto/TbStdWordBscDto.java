package com.meta.dto;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * @author     : somnus21
 * @file       : TbStdWordBscDto.java
 * @version    : 1.0.0
 * @createDate : 2025-11-20 오전 11:00:52
 * @updateDate : 2025-11-20 오전 11:00:52
 * @desc       : 단어사전 tb_word_dictionary
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TbStdWordBscDto {  
    private Long       id;                              // ID INT
    private String     wordNm;                          // 단어명 VARCHAR(100)
    private String     engAbbrNm;                       // 영문약어명 VARCHAR(100)
    private String     engNm;                           // 영문명 VARCHAR(100)
    private String     expln;                           // 설명 TEXT
    private String     sttsCd;                            // 상태
    private String     crtDttm;                         // 생성일시 DATETIME
    private String     crtId;                           // 생성자ID VARCHAR(50)
    private String     updDttm;                         // 변경일시 DATETIME
    private String     updId;                           // 변경자ID VARCHAR(50)
    private String     func;
}
