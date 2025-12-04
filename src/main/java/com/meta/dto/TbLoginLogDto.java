package com.meta.dto;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * @author     : somnus21
 * @file       : TbLoginLogDto.java
 * @version    : 1.0.0
 * @createDate : 2025-12-04 오후 3:22:14
 * @updateDate : 2025-12-04 오후 3:22:14
 * @desc       : 로그인 로그 tb_login_log
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TbLoginLogDto {  
    private Integer    logId;                           // LOG_ID BIGINT
    private String     userId;                          // USER_ID VARCHAR(50)
    private String     ipAddr;                          // IP_ADDR VARCHAR(50)
    private String     userAgent;                       // USER_AGENT VARCHAR(300)
    private String     loginResult;                     // LOGIN_RESULT VARCHAR(1)
    private String     failReason;                      // FAIL_REASON VARCHAR(200)
    private String     crtDttm;                         // 생성일시 DATETIME
    private String     startDate;                       // 조회시작일자
    private String     endDate;                         // 조회종료일자
}
