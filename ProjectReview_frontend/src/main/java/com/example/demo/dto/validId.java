package com.example.demo.dto;

import java.lang.annotation.Documented;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = IdValidation.class)
public @interface validId {
	public String message() default "Id must be valid e.g AB001";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
