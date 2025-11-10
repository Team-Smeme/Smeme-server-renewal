package com.smeem.http.security;

import com.smeem.common.exception.ExceptionCode;
import com.smeem.common.exception.SmeemException;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.security.Principal;

public class MemberIdArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(MemberId.class) && parameter.getParameterType().equals(long.class);
    }

    @Override
    public Object resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory
    ) {
        Principal principal = webRequest.getUserPrincipal();

        if (principal == null || principal.getName() == null) {
            throw new SmeemException(ExceptionCode.UNAUTHORIZED);
        }

        try {
            return Long.parseLong(principal.getName());
        } catch (NumberFormatException e) {
            throw new SmeemException(ExceptionCode.UNAUTHORIZED);
        }
    }
}
