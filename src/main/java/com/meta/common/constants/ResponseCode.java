package com.meta.common.constants;

import lombok.Getter;

@Getter
public enum ResponseCode {

    // SUCCESS
    SUCCESS("S000", "처리 성공"),

    // VALIDATION
    VALIDATION_FAILED("V001", "입력값이 올바르지 않습니다."),
    PASSWORD_FAILED("V002", "비밀번호 오류"),

    // DATA
    DATA_NOT_FOUND("D001", "데이터가 없습니다."),
    DUPLICATE_DATA("D002", "기등록된 데이터가 있습니다."),
    USER_NOT_FOUND("D003", "사용자가 없습니다."),
    USER_NO_USED("D004", "시스템사용 불가"),

    // DB
    INSERT_FAILED("DB01", "등록 중 오류가 발생했습니다."),
    UPDATE_FAILED("DB02", "수정 중 오류가 발생했습니다."),
    DELETE_FAILED("DB03", "삭제 중 오류가 발생했습니다."),

    // SYSTEM
    SERVER_ERROR("E500", "처리 중 오류가 발생했습니다.");

    private final String code;
    private final String message;

    ResponseCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

}
