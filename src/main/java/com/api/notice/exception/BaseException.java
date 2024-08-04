package com.api.notice.exception;

import com.api.notice.util.message.BaseMessageSource;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BaseException extends RuntimeException {

    private final String message;
    private final HttpStatus httpStatus;

    public BaseException(String messageOrKey) {
        this.message = BaseMessageSource.getMessage(messageOrKey);
        this.httpStatus = HttpStatus.BAD_REQUEST;
    }

    public BaseException(String messageOrKey, String... args) {
        this.message = BaseMessageSource.getMessageWithArgs(messageOrKey, args);
        this.httpStatus = HttpStatus.BAD_REQUEST;
    }

    public BaseException(HttpStatus httpStatus, String messageOrKey) {
        this.message = BaseMessageSource.getMessage(messageOrKey);
        this.httpStatus = httpStatus;
    }

    public BaseException(HttpStatus httpStatus, String messageOrKey, String... args) {
        this.message = BaseMessageSource.getMessageWithArgs(messageOrKey, args);
        this.httpStatus = httpStatus;
    }
}