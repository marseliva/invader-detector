package com.example.demo.invadersdetector.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = StringLengthValidator.class)
@Documented
public @interface AllStringsSameLength {
    String message() default "All rows must have same length";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
