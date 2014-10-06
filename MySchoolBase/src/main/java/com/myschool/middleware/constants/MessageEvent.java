package com.myschool.middleware.constants;

/**
 * The Enum MessageEvent.
 */
public enum MessageEvent {
    
    STUDENT_REGISTRATION ("Student Registration"),

    /** The EXA m_ result. */
    EXAM_RESULT ("Exam Result");

    /** The description. */
    private String description;

    /**
     * Instantiates a new message event.
     *
     * @param description the description
     */
    MessageEvent(String description) {
        this.description = description;
    }

    /**
     * Gets the description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }


    /**
     * Gets the message event.
     *
     * @param description the description
     * @return the message event
     */
    public static MessageEvent getMessageEvent(String description) {
        MessageEvent gotMessageEvent = null;

        if (description != null) {
            MessageEvent[] values = MessageEvent.values();
            for (MessageEvent messageEvent : values) {
                if (description.equals(messageEvent.getDescription())) {
                    gotMessageEvent = messageEvent;
                    break;
                }
            }
        }
        return gotMessageEvent;
    }

}
