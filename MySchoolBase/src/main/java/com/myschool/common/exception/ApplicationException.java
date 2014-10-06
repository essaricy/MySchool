package com.myschool.common.exception;

/**
 * The Class ApplicationException.
 */
public class ApplicationException extends Exception {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /**
     * Instantiates a new application exception.
     *
     * @param message the message
     */
    public ApplicationException(String message) {
        super(message);
    }

    /**
     * Instantiates a new application exception.
     *
     * @param message the message
     * @param throwable the throwable
     */
    public ApplicationException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
