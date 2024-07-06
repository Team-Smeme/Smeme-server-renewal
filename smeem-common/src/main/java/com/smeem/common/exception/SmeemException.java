package com.smeem.common.exception;

import jakarta.validation.constraints.NotNull;

public class SmeemException extends RuntimeException {
    @NotNull public final int statusCode;
    @NotNull public final String defaultMessage;
    public final String detailMessage;

    public SmeemException(ExceptionCode exceptionCode) {
        this.statusCode = exceptionCode.getStatusCode();
        this.defaultMessage = exceptionCode.getMessage();
        this.detailMessage = "";
    }

    public SmeemException(ExceptionCode exceptionCode, String message) {
        this.statusCode = exceptionCode.getStatusCode();
        this.defaultMessage = exceptionCode.getMessage();
        this.detailMessage = message;
    }
}
