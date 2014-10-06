package com.myschool.middleware.exception;

/**
 * The Class QueueConnectionException.
 */
public class QueueConnectionException extends Exception {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new queue connection exception.
     */
    public QueueConnectionException() {
        super();
    }

    /**
     * Instantiates a new queue connection exception.
     *
     * @param message the message
     */
    public QueueConnectionException(String message) {
        super(message);
    }

    /**
     * Instantiates a new queue connection exception.
     *
     * @param throwable the throwable
     */
    public QueueConnectionException(Throwable throwable) {
        super(throwable);
    }

    /**
     * Instantiates a new queue connection exception.
     *
     * @param message the message
     * @param throwable the throwable
     */
    public QueueConnectionException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
