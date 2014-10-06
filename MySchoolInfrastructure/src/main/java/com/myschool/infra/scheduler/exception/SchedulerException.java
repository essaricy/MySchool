package com.myschool.infra.scheduler.exception;

/**
 * The Class SchedulerException.
 */
public class SchedulerException extends Exception {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new scheduler exception.
     *
     * @param message the message
     * @param throwable the throwable
     */
    public SchedulerException(String message,
            Throwable throwable) {
        super(message, throwable);
    }

}
