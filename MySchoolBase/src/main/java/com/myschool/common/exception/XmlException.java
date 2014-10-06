package com.myschool.common.exception;

/**
 * The Class XmlException.
 */
public class XmlException extends Exception {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new xml exception.
     */
    public XmlException() {
        super();
    }

    /**
     * Instantiates a new xml exception.
     *
     * @param message the message
     */
    public XmlException(String message) {
        super(message);
    }

    /**
     * Instantiates a new xml exception.
     *
     * @param throwable the throwable
     */
    public XmlException(Throwable throwable) {
        super(throwable);
    }

    /**
     * Instantiates a new xml exception.
     *
     * @param message the message
     * @param throwable the throwable
     */
    public XmlException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
