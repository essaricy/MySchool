package com.quasar.core.exception;

/**
 * The Class DataException.
 */
public class DataException extends Exception {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new data exception.
     * 
     * @param message the message
     * @param throwable the throwable
     */
    public DataException(String message, Throwable throwable) {
        super(message, throwable);
    }

    /**
     * Instantiates a new data exception.
     * 
     * @param message the message
     */
    public DataException(String message) {
        super(message);
    }

}
