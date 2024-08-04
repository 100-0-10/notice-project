package com.api.notice.util.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = {EnumValidator.class})
@Target({ METHOD, FIELD, PARAMETER })
@Retention(RUNTIME)
public @interface NotDateTime {
    String pattern() default "errors.date.format";

    String message() default "errors.required";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
