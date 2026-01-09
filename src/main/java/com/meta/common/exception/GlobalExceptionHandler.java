package com.meta.common.exception;

import com.meta.common.constants.ResponseCode;
import com.meta.common.response.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BizException.class)
    public ResponseEntity<ApiResponse<Void>> handleBizException(BizException e) {
        log.warn("✅ Business Exception: {}", e.getMessage());
        return ResponseEntity
                .ok(ApiResponse.fail(e.getResponseCode()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleException(Exception e) {
        log.error("✅ Unhandled Exception occurred", e);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.fail(ResponseCode.SERVER_ERROR));
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ApiResponse<Void>> handleMaxUploadSizeExceeded(MaxUploadSizeExceededException e) {
        log.error("✅ File upload size exceeded", e);
        return ResponseEntity
                .status(HttpStatus.PAYLOAD_TOO_LARGE) // 413
                .body(ApiResponse.fail(ResponseCode.FILE_SIZE_EXCEEDED));
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<Void> handleNoResourceFound(NoResourceFoundException ex,
                                                      HttpServletRequest request) {

        if (request.getRequestURI().equals("/favicon.ico")) {
            // 로그도 남기지 않음
            return ResponseEntity.notFound().build();
        }

        log.error("NoResourceFoundException", ex);
        return ResponseEntity.notFound().build();
    }


}
