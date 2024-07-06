package com.smeem.common.exception;

public class SmeemException extends RuntimeException {
    public final int statusCode;
    public final String defaultMessage;
    public final String detailMessage;

    public SmeemException(ExceptionCode exceptionCode, String message) {
        this.statusCode = exceptionCode.getStatusCode();
        this.defaultMessage = exceptionCode.getMessage();
        this.detailMessage = message;
    }

    public SmeemException(ExceptionCode exceptionCode) {
        this.statusCode = exceptionCode.getStatusCode();
        this.defaultMessage = exceptionCode.getMessage();
        this.detailMessage = "";
    }

    public String getMessage() {
        return defaultMessage + detailMessage;
    }
}
