package com.smeem.common.exception;

public class SmeemException extends RuntimeException {
    private final int statusCode;
    private final String defaultMessage;
    private final String detailMessage;

    public SmeemException(ExceptionCode exceptionCode, String message) {
        this.statusCode = exceptionCode.getStatusCode();
        this.defaultMessage = exceptionCode.getMessage();
        this.detailMessage = message;
    }

    public SmeemException(ExceptionCode exceptionCode) {
        this.statusCode = exceptionCode.getStatusCode();
        this.defaultMessage = exceptionCode.getMessage();
        this.detailMessage = null;
    }
}
