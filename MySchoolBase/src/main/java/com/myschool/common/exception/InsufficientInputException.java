package com.myschool.common.exception;

/**
 * The Class InsufficientInputException.
 */
public class InsufficientInputException extends DataException {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new insufficient input exception.
     * 
     * @param message the message
     */
    public InsufficientInputException(String message) {
        super(message);
    }

}
