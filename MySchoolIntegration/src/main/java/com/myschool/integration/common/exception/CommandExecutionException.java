package com.myschool.integration.common.exception;

/**
 * The Class CommandExecutionException.
 */
public class CommandExecutionException extends Exception {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new command execution exception.
     * 
     * @param message the message
     */
    public CommandExecutionException(String message) {
        super(message);
    }

    /**
     * Instantiates a new command execution exception.
     * 
     * @param message the message
     * @param throwable the throwable
     */
    public CommandExecutionException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
