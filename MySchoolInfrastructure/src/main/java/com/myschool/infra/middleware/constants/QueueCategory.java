package com.myschool.infra.middleware.constants;

/**
 * The Enum QueueCategory.
 */
@Deprecated
public enum QueueCategory {

    /** The COMMAND_EXECUTOR. */
    COMMAND_EXECUTOR;

    /**
     * Gets the.
     * 
     * @param category the category
     * @return the queue category
     */
    public static QueueCategory get(String category) {
        if (category != null) {
            for (QueueCategory queueCategory : values()) {
                if (queueCategory.toString().equals(category)) {
                    return queueCategory;
                }
            }
        }
        return null;
    }

}
