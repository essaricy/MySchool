package com.myschool.common.exception;

/**
 * The Class ValidationException.
 */
public class ValidationException extends Exception {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new validation exception.
     *
     * @param message the message
     */
    public ValidationException(String message) {
        super(message);
    }

    /**
     * Instantiates a new validation exception.
     *
     * @param message the message
     * @param throwable the throwable
     */
    public ValidationException(String message, Throwable throwable) {
        super(message, throwable);
    }

    /**
     * Instantiates a new validation exception.
     *
     * @param throwable the throwable
     */
    public ValidationException(Throwable throwable) {
        super(throwable);
    }

}
