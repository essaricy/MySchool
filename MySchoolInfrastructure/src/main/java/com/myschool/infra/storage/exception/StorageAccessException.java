package com.myschool.infra.storage.exception;

/**
 * The Class StorageAccessException.
 */
public class StorageAccessException extends Exception {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new storage access exception.
     */
    public StorageAccessException() {
        super();
    }

    /**
     * Instantiates a new storage access exception.
     *
     * @param message the message
     */
    public StorageAccessException(String message) {
        super(message);
    }

    /**
     * Instantiates a new storage access exception.
     *
     * @param throwable the throwable
     */
    public StorageAccessException(Throwable throwable) {
        super(throwable);
    }

    /**
     * Instantiates a new storage access exception.
     *
     * @param message the message
     * @param throwable the throwable
     */
    public StorageAccessException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
