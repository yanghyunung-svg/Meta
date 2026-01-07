package com.meta.common.exception;

import com.meta.common.constants.ResponseCode;
import lombok.Getter;

@Getter
public class BizException extends RuntimeException {
    private final ResponseCode responseCode;
    public BizException(ResponseCode responseCode) {
        super(responseCode.getMessage());
        this.responseCode = responseCode;
    }
}
