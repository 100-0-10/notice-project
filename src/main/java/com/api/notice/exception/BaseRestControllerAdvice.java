package com.api.notice.exception;

import com.api.notice.util.response.BaseResponse;
import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.format.DateTimeParseException;
import java.util.Objects;

@Slf4j
@RestControllerAdvice
public class BaseRestControllerAdvice {

    /**
     * 기본 예외처리 핸들러
     */
    @ExceptionHandler(value = BaseException.class)
    private ResponseEntity<?> baseExceptionHandler(BaseException e) {
        log.error("Base Exception - ", e);
        return ResponseEntity.status(e.getHttpStatus().value()).body(BaseResponse.ofMessage(e.getMessage()));
    }

    /**
     * DTO @Valid 유효성 체크시 예외처리 핸들러
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    private ResponseEntity<?> validationExceptionHandler(MethodArgumentNotValidException e) {
        FieldError error = e.getBindingResult().getFieldError();
        BaseResponse baseResponse = BaseResponse.ofMessage("errors.required.parameter");

        if (error != null) {
            String message = error.getDefaultMessage();
            if (StringUtils.isNotEmpty(message)) {
                switch (Objects.requireNonNull(error.getCode())) {
                    default -> baseResponse = BaseResponse.ofMessage("errors.required", message);
                }
            }
        }

        return ResponseEntity.badRequest().body(baseResponse);
    }

    /**
     * 날짜 변환 예외처리 핸들러
     */
    @ExceptionHandler(value = DateTimeParseException.class)
    private ResponseEntity<?> dateParseExceptionHandler(DateTimeParseException e) {
        log.error("parse Exception - ", e);
        return ResponseEntity.badRequest().body(BaseResponse.ofMessage("errors.date.format", e.getParsedString()));
    }

}
