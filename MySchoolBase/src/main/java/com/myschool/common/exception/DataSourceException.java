package com.myschool.common.exception;

/**
 * The Class DataSourceException.
 */
public class DataSourceException extends Exception {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new data source exception.
     * 
     * @param message the message
     * @param throwable the throwable
     */
    public DataSourceException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
