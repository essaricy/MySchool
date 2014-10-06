package com.myschool.infra.report.exception;

/**
 * The Class ReportException.
 */
public class ReportException extends Exception {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new report exception.
     * 
     * @param message the message
     */
    public ReportException(String message) {
        super(message);
    }

    /**
     * Instantiates a new report exception.
     *
     * @param message the message
     * @param throwable the throwable
     */
    public ReportException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
