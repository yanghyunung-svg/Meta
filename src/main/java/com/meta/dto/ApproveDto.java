package com.meta.dto;

import lombok.Data;

/**
 * @author     : somnus21
 * @file       : ApproveDto.java
 * @version    : 1.0.0
 * @createDate : 2025-11-20 오전 10:59:53
 * @updateDate : 2025-11-20 오전 10:59:53
 * @desc       : DashBoardDto
 */
@Data
public class ApproveDto {
    private String     se;                              // 구분
    private String     seNm;                            // 구분명
    private Long       id;                              // ID
    private String     korNm;                           // 한글명
    private String     engNm;                           // 영문명
    private String     rmk;                           // 설명
    private String     sttsCd;                            // 상태 0=등록 1=사용 9=미사용
    private String     crtDttm;                         // 생성일시 DATETIME
    private String     crtId;                           // 생성자ID
    private String     userId;                          // 등록자ID
}
