package com.myschool.infra.cache.exception;

/**
 * The Class CacheException.
 */
public class CacheException extends Exception {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new cache exception.
     *
     * @param message the message
     * @param throwable the throwable
     */
    public CacheException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
