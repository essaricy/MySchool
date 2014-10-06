package com.myschool.notification.constants;

/**
 * The Enum NotificationEndPoint.
 */
public enum NotificationEndPoint {

    /** The STUDENT. */
    STUDENT(1, "Student"),

    /** The EMPLOYEE. */
    EMPLOYEE(2, "Employee"),

    /** The NOTICE_BOARD. */
    NOTICE_BOARD(3, "Notice Board");

    /** The id. */
    private int id;

    /** The value. */
    private String value;

    /**
     * Instantiates a new notification end point.
     *
     * @param id the id
     * @param value the value
     */
    private NotificationEndPoint(int id, String value) {
        this.id = id;
        this.value = value;
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
     * Gets the value.
     *
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * Gets the.
     *
     * @param endPointName the end point name
     * @return the notification end point
     */
    public static NotificationEndPoint get(String endPointName) {
        NotificationEndPoint gotNotificationEndPoint = null;
        for (NotificationEndPoint notificationEndPoint : values()) {
            if (notificationEndPoint.toString().equalsIgnoreCase(endPointName)) {
                gotNotificationEndPoint = notificationEndPoint;
                break;
            }
        }
        return gotNotificationEndPoint;
    }

    /**
     * Gets the by value.
     *
     * @param endPointName the end point name
     * @return the by value
     */
    public static NotificationEndPoint getByValue(String endPointName) {
        NotificationEndPoint gotNotificationEndPoint = null;
        for (NotificationEndPoint notificationEndPoint : values()) {
            if (notificationEndPoint.getValue().equalsIgnoreCase(endPointName)) {
                gotNotificationEndPoint = notificationEndPoint;
                break;
            }
        }
        return gotNotificationEndPoint;
    }

}
