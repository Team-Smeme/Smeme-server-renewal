package com.smeme.server.dto.member;

import com.smeme.server.util.message.ErrorMessage;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UsernameValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidUsername {

    String message() default "닉네임 제한사항에 부합하지 않습니다.";
}