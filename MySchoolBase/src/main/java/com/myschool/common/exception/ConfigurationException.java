package com.myschool.common.exception;

/**
 * The Class ConfigurationException.
 */
public class ConfigurationException extends Exception {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new configuration exception.
     */
    public ConfigurationException() {
        super();
    }

    /**
     * Instantiates a new configuration exception.
     * 
     * @param message the message
     */
    public ConfigurationException(String message) {
        super(message);
    }

    /**
     * Instantiates a new configuration exception.
     * 
     * @param throwable the throwable
     */
    public ConfigurationException(Throwable throwable) {
        super(throwable);
    }

    /**
     * Instantiates a new configuration exception.
     * 
     * @param message the message
     * @param throwable the throwable
     */
    public ConfigurationException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
