package com.myschool.middleware.exception;

/**
 * The Class RecoverableMessageException.
 */
public class RecoverableMessageException extends XmlMessageException {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new email exception.
     */
    public RecoverableMessageException() {
        super();
    }

    /**
     * Instantiates a new recoverable message exception.
     *
     * @param message the message
     */
    public RecoverableMessageException(String message) {
        super(message);
    }

    /**
     * Instantiates a new recoverable message exception.
     *
     * @param throwable the throwable
     */
    public RecoverableMessageException(Throwable throwable) {
        super(throwable);
    }

    /**
     * Instantiates a new recoverable message exception.
     *
     * @param message the message
     * @param throwable the throwable
     */
    public RecoverableMessageException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
