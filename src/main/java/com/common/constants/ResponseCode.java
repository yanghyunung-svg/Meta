package com.common.constants;

public enum ResponseCode {
    SUCCESS("SUCCESS", "요청 성공"),
    DATA_NOT_FOUND("ERROR", "조회할 자료가 없습니다"),
    VALIDATION_FAILED("ERROR", "입력값이 올바르지 않습니다"),
    SERVER_ERROR("ERROR", "서버 오류");

    private final String code;
    private final String message;

    ResponseCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
