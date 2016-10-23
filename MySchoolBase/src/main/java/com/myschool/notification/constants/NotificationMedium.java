package com.myschool.notification.constants;

/**
 * The Enum NotificationMedium.
 */
public enum NotificationMedium {

    /** The EMAIL. */
    EMAIL(1),

    /** The SMS. */
    SMS(2);

    /** The id. */
    private int id;

    /**
     * Instantiates a new notification medium.
     *
     * @param id the id
     */
    private NotificationMedium(int id) {
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
     * @param value the value
     * @return the notification medium
     */
    public static NotificationMedium get(String value) {
        NotificationMedium gotNotificationMedium = null;
        for (NotificationMedium notificationMode : values()) {
            if (notificationMode.toString().equalsIgnoreCase(value)) {
                gotNotificationMedium = notificationMode;
                break;
            }
        }
        return gotNotificationMedium;
    }
}
