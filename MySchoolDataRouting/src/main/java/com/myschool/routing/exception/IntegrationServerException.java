package com.myschool.routing.exception;

public class IntegrationServerException extends Exception {

    private static final long serialVersionUID = 1L;

    public IntegrationServerException(String message) {
        super(message);
    }

    public IntegrationServerException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
