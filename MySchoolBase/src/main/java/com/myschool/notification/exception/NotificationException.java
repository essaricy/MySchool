package com.myschool.notification.exception;

/**
 * The Class NotificationException.
 */
public class NotificationException extends Exception {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new notification exception.
     *
     * @param message the message
     */
    public NotificationException(String message) {
        super(message);
    }

    /**
     * Instantiates a new notification exception.
     *
     * @param message the message
     * @param throwable the throwable
     */
    public NotificationException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
