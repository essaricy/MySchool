package com.myschool.infra.media.exception;

/**
 * The Class ResourceException.
 */
public class ResourceException extends Exception {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new resource exception.
     * 
     * @param message the message
     */
    public ResourceException(String message) {
        super(message);
    }

    /**
     * Instantiates a new resource exception.
     * 
     * @param message the message
     * @param throwable the throwable
     */
    public ResourceException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
