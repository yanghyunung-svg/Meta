package com.common.utils;

import com.meta.dto.TbUserInfoDto;
import lombok.Data;

/**
 * @author     : somnus21
 * @file       : LoginResult.java
 */
@Data
public class LoginResult {
    private boolean success;
    private String message;
    private TbUserInfoDto data; // 로그인 성공 시 사용자 정보

    public LoginResult() {}

    public LoginResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public LoginResult(boolean success, String message, TbUserInfoDto data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }
}
