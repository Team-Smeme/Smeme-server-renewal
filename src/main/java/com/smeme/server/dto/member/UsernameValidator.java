package com.smeme.server.dto.member;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UsernameValidator implements ConstraintValidator<ValidUsername, String> {

    @Override
    public void initialize(ValidUsername constraintAnnotation) {
    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        if (username == null || username.isEmpty()) {
            return false;
        }

        if (username.length() < 1 || username.length() > 10) {
            return false;
        }

        if (username.charAt(0) == ' ') {
            return false;
        }

        return username.trim().isEmpty();
    }
}