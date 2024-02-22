package com.smeem.api.member.controller.dto;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Objects;

public class UsernameValidator implements ConstraintValidator<ValidUsername, String> {

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {

        // null, 공백으로만 이뤄지는 경우, 빈 값인 경우 ''
        if (username.isBlank()) {
            return false;
        }

        // 길이가 1보다 작거나 10보다 큰 경우
        if (username.isEmpty() || username.length() > 10) {
            return false;
        }

        // 첫 글자가 공백인 경우
        return !(Objects.equals(username.charAt(0),' '));
    }
}