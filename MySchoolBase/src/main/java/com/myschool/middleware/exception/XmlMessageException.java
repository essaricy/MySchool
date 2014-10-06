package com.myschool.middleware.exception;

/**
 * The Class XmlMessageException.
 */
public class XmlMessageException extends Exception {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new xml message exception.
     */
    public XmlMessageException() {
        super();
    }

    /**
     * Instantiates a new xml message exception.
     *
     * @param message the message
     */
    public XmlMessageException(String message) {
        super(message);
    }

    /**
     * Instantiates a new xml message exception.
     *
     * @param throwable the throwable
     */
    public XmlMessageException(Throwable throwable) {
        super(throwable);
    }

    /**
     * Instantiates a new xml message exception.
     *
     * @param message the message
     * @param throwable the throwable
     */
    public XmlMessageException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
