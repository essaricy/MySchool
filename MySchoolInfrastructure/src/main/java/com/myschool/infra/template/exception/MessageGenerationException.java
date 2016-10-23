package com.myschool.infra.template.exception;

/**
 * The Class MessageGenerationException.
 */
public class MessageGenerationException extends Exception {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new message generation exception.
     *
     * @param message the message
     */
    public MessageGenerationException(String message) {
        super(message);
    }

    /**
     * Instantiates a new message generation exception.
     *
     * @param message the message
     * @param throwable the throwable
     */
    public MessageGenerationException(String message,
            Throwable throwable) {
        super(message, throwable);
    }

}
