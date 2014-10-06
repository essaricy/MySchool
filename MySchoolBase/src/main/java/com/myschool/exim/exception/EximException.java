package com.myschool.exim.exception;

/**
 * The Class EximException.
 */
public class EximException extends Exception {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new exim exception.
     *
     * @param message the message
     */
    public EximException(String message) {
        super(message);
    }

    /**
     * Instantiates a new exim exception.
     *
     * @param message the message
     * @param throwable the throwable
     */
    public EximException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
