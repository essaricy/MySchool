package com.myschool.common.exception;

/**
 * The Class DaoException.
 */
public class DaoException extends Exception {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new dao exception.
     * 
     * @param message the message
     * @param throwable the throwable
     */
    public DaoException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
