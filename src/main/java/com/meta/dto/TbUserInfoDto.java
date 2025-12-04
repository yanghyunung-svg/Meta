package com.meta.dto;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * @author     : somnus21
 * @file       : TbUserInfoDto.java
 * @version    : 1.0.0
 * @createDate : 2025-11-26 오후 3:44:43
 * @updateDate : 2025-11-26 오후 3:44:43
 * @desc       : 사용자기본 tb_user
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TbUserInfoDto {
    private String     userId;                          // 사용자ID VARCHAR(50)
    private String     userNm;                          // 사용자이름 VARCHAR(200)
    private String     password;                        // 비밀번호 VARCHAR(255)
    private String     email;                           // 이메일 VARCHAR(200)
    private String     phone;                           // 전화번호 VARCHAR(50)
    private String     role;                            // 권한 VARCHAR(50)
    private String     useYn;                           // 사용여부 CHAR(1)
    private String     crtDttm;                         // 생성일시 DATETIME
    private String     crtId;                           // 생성자ID VARCHAR(50)
    private String     updDttm;                         // 변경일시 DATETIME
    private String     updId;                           // 변경자ID VARCHAR(50)

    private String     ipAddr;
    private String     userAgent;
}
