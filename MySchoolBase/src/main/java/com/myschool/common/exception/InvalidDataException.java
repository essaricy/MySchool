package com.myschool.common.exception;


/**
 * The Class InvalidDataException.
 */
public class InvalidDataException extends DataException {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new invalid data exception.
     * 
     * @param message the message
     */
    public InvalidDataException(String message) {
        super(message);
    }

    /**
     * Instantiates a new invalid data exception.
     *
     * @param throwable the throwable
     */
    public InvalidDataException(Throwable throwable) {
        super(null, throwable);
    }

}
