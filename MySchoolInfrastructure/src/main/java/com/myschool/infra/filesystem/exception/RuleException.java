package com.myschool.infra.filesystem.exception;


/**
 * The Class RuleException.
 */
public class RuleException extends Exception {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new rule exception.
     * 
     * @param message the message
     */
    public RuleException(String message) {
        super(message);
    }

    /**
     * Instantiates a new rule exception.
     *
     * @param message the message
     * @param throwable the throwable
     */
    public RuleException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
