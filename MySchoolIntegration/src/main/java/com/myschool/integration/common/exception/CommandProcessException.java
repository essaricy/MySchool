package com.myschool.integration.common.exception;

/**
 * The Class CommandProcessException.
 */
public class CommandProcessException extends Exception {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new command process exception.
     * 
     * @param message the message
     */
    public CommandProcessException(String message) {
        super(message);
    }

    /**
     * Instantiates a new command process exception.
     * 
     * @param message the message
     * @param throwable the throwable
     */
    public CommandProcessException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
