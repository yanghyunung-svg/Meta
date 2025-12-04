package com.meta.dto;

import lombok.Data;

/**
 * @author     : somnus21
 * @file       : WordMappingDto.java
 * @version    : 1.0.0
 * @createDate : 2025-11-20 오전 11:00:52
 * @updateDate : 2025-11-20 오전 11:00:52
 * @desc       : 단어사전 tb_word_dictionary
 */
@Data
public class WordMappingDto {
    private String     korNm;                           // 한글명
    private String     engNm;                           // 영문명
}
