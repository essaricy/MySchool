package com.myschool.common.exception;

/**
 * The Class ConnectionException.
 */
public class ConnectionException extends Exception {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new connection exception.
     *
     * @param message the message
     */
    public ConnectionException(String message) {
        super(message);
    }

    /**
     * Instantiates a new connection exception.
     * 
     * @param message the message
     * @param throwable the throwable
     */
    public ConnectionException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
