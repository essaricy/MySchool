package com.myschool.common.exception;

/**
 * The Class FileSystemException.
 */
public class FileSystemException extends Exception {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new file system exception.
     *
     * @param message the message
     */
    public FileSystemException(String message) {
        super(message);
    }

    /**
     * Instantiates a new file system exception.
     *
     * @param message the message
     * @param throwable the throwable
     */
    public FileSystemException(String message, Throwable throwable) {
        super(message, throwable);
    }


}
