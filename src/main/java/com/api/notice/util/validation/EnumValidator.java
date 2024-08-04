package com.api.notice.util.validation;

import io.micrometer.common.util.StringUtils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EnumValidator implements ConstraintValidator<NotDateTime, String> {

    private String pattern;

    @Override
    public void initialize(NotDateTime constraintAnnotation) {
        this.pattern = constraintAnnotation.pattern();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (StringUtils.isEmpty(value)) {
            return false;
        }

        isParsedCheck(value);

        return true;
    }

    private void isParsedCheck(String value) {
        LocalDateTime.parse(value, DateTimeFormatter.ofPattern(pattern));
    }
}
