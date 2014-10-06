package com.myschool.common.exception;

/**
 * The Class SecurityException.
 */
public class SecurityException extends Exception {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new security exception.
     *
     * @param message the message
     * @param throwable the throwable
     */
    public SecurityException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
