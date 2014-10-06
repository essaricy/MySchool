package com.myschool.infra.middleware.exception;

/**
 * The Class MessageException.
 */
public class MessageException extends Exception {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new message exception.
     *
     * @param message the message
     * @param throwable the throwable
     */
    public MessageException(String message,
            Throwable throwable) {
        super(message, throwable);
    }

}
