package com.myschool.integration.common.exception;


public class MediaServerException extends Exception {

    private static final long serialVersionUID = 1L;

    public MediaServerException(String message) {
        super(message);
    }

    public MediaServerException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
