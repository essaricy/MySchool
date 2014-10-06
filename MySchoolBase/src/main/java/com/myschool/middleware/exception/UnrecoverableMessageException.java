package com.myschool.middleware.exception;

/**
 * The Class UnrecoverableMessageException.
 */
public class UnrecoverableMessageException extends XmlMessageException {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new unrecoverable message exception.
     */
    public UnrecoverableMessageException() {
        super();
    }

    /**
     * Instantiates a new unrecoverable message exception.
     *
     * @param message the message
     */
    public UnrecoverableMessageException(String message) {
        super(message);
    }

    /**
     * Instantiates a new unrecoverable message exception.
     *
     * @param throwable the throwable
     */
    public UnrecoverableMessageException(Throwable throwable) {
        super(throwable);
    }

    /**
     * Instantiates a new unrecoverable message exception.
     *
     * @param message the message
     * @param throwable the throwable
     */
    public UnrecoverableMessageException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
