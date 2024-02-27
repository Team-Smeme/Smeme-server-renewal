package com.smeem.api.member.controller.dto;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Objects;

public class UsernameValidator implements ConstraintValidator<ValidUsername, String> {

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {

        if (username.isBlank()) {
            return false;
        }

        if (username.isEmpty() || username.length() > 10) {
            return false;
        }

        return !(Objects.equals(username.charAt(0),' '));
    }
}