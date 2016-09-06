package com.myschool.infra.remote.ftp.exception;

public class FtpException extends Exception {

    private static final long serialVersionUID = 1L;

    public FtpException(String message) {
        super(message);
    }

    public FtpException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
