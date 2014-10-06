package com.myschool.notification.constants;

/**
 * The Enum NotificationStatus.
 */
public enum NotificationStatus {

    /** The NEW. */
    NEW,
    
    /** The PARTIAL. */
    PARTIAL,
    
    /** The DELIVERED. */
    DELIVERED,
    
    /** The UNDELIVERED. */
    UNDELIVERED,
    
    /** The ABONDENED. */
    ABONDENED;

    /** The id. */
    private int id;

    /**
     * Gets the id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param id the new id
     */
    public void setId(int id) {
        this.id = id;
    }
    
    public static NotificationStatus get(String notificationStatusValue) {
        NotificationStatus gotNotificationStatus = null;
        for (NotificationStatus notificationStatus : values()) {
            if (notificationStatus.toString().equalsIgnoreCase(notificationStatusValue)) {
                gotNotificationStatus = notificationStatus;
                break;
            }
        }
        return gotNotificationStatus;
    }

}
