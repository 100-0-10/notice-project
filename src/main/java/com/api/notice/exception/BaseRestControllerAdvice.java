package com.api.notice.exception;

import com.api.notice.util.response.BaseResponse;
import io.micrometer.common.util.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

@RestControllerAdvice
public class BaseRestControllerAdvice {

    /**
     * DTO @Valid 유효성 체크시 예외처리 핸들러
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    private ResponseEntity<?> validationExceptionHandler(MethodArgumentNotValidException e) {
        FieldError error = e.getBindingResult().getFieldError();
        BaseResponse baseResponse = BaseResponse.ofMessage("errors.required.parameter");
        if (error != null) {
            if (StringUtils.isNotEmpty(error.getDefaultMessage())) {
                switch (Objects.requireNonNull(error.getCode())) {
                    case "Size" -> {
                        baseResponse = BaseResponse.ofMessage("errors.required.size", error.getDefaultMessage());
                    }
                    default -> {
                        baseResponse = BaseResponse.ofMessage("errors.required", error.getDefaultMessage());
                    }
                }
            }
        }

        return ResponseEntity.badRequest().body(baseResponse);
    }

}
