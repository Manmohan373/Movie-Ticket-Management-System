package com.example.demo.dto;

import java.util.regex.Pattern;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class IdValidation implements ConstraintValidator<validId,String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if(value == null) {
			return false;
		}
		boolean id = Pattern.matches("^[a-zA-Z]{2}[0-9]{3}$", value);
		return id;
	}

}
