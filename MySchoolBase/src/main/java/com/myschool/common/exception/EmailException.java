package com.myschool.common.exception;

/**
 * The Class EmailException.
 */
public class EmailException extends Exception {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new email exception.
     */
    public EmailException() {
        super();
    }

    /**
     * Instantiates a new email exception.
     * 
     * @param message the message
     */
    public EmailException(String message) {
        super(message);
    }

    /**
     * Instantiates a new email exception.
     * 
     * @param throwable the throwable
     */
    public EmailException(Throwable throwable) {
        super(throwable);
    }

    /**
     * Instantiates a new email exception.
     * 
     * @param message the message
     * @param throwable the throwable
     */
    public EmailException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
