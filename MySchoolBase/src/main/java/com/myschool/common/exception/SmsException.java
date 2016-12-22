package com.myschool.common.exception;

/**
 * The Class SmsException.
 */
public class SmsException extends Exception {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new sms exception.
     */
    public SmsException() {
        super();
    }

    /**
     * Instantiates a new sms exception.
     *
     * @param message the message
     */
    public SmsException(String message) {
        super(message);
    }

    /**
     * Instantiates a new sms exception.
     *
     * @param throwable the throwable
     */
    public SmsException(Throwable throwable) {
        super(throwable);
    }

    /**
     * Instantiates a new sms exception.
     *
     * @param message the message
     * @param throwable the throwable
     */
    public SmsException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
