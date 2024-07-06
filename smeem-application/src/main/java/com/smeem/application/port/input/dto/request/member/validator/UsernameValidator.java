package com.smeem.application.port.input.dto.request.member.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Objects;

public class UsernameValidator implements ConstraintValidator<ValidUsername, String> {

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        if (username.isBlank() || username.isEmpty() || username.length() > 10) {
            return false;
        }
        return !(Objects.equals(username.charAt(0),' '));
    }
}
