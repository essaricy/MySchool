package com.myschool.notification.constants;

/**
 * The Enum NotificationMode.
 */
public enum NotificationMode {

    /** The EMAIL. */
    EMAIL(1),

    /** The SMS. */
    SMS(2);

    /** The id. */
    private int id;

    /**
     * Instantiates a new notification mode.
     * 
     * @param id the id
     */
    private NotificationMode(int id) {
        this.id = id;
    }

    /**
     * Gets the id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the.
     *
     * @param notificationModeValue the notification mode value
     * @return the notification mode
     */
    public static NotificationMode get(String notificationModeValue) {
        NotificationMode gotNotificationMode = null;
        for (NotificationMode notificationMode : values()) {
            if (notificationMode.toString().equalsIgnoreCase(notificationModeValue)) {
                gotNotificationMode = notificationMode;
                break;
            }
        }
        return gotNotificationMode;
    }
}
