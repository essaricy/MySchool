package com.myschool.common.exception;

/**
 * The Class AgentException.
 */
public class AgentException extends Exception {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new agent exception.
     *
     * @param message the message
     */
    public AgentException(String message) {
        super(message);
    }

    /**
     * Instantiates a new agent exception.
     *
     * @param message the message
     * @param throwable the throwable
     */
    public AgentException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
