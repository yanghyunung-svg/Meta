package com.meta.common.response;

import com.meta.common.constants.ResponseCode;
import lombok.Getter;

@Getter
public class ApiResponse<T> {

    private boolean success;
    private String code;
    private String message;
    private T data;

    private ApiResponse() {
    }

    private ApiResponse(boolean success, ResponseCode responseCode, T data) {
        this.success = success;
        this.code = responseCode.getCode();
        this.message = responseCode.getMessage();
        this.data = data;
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, ResponseCode.SUCCESS, data);
    }

    public static <T> ApiResponse<T> fail(ResponseCode responseCode) {

        if (responseCode == null) {
            responseCode = ResponseCode.SERVER_ERROR;
        }
        return new ApiResponse<>(false, responseCode, null);
    }

    public static <T> ApiResponse<T> fail(ResponseCode responseCode, T data) {
        return new ApiResponse<>(false, responseCode, data);
    }
}
