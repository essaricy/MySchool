package com.myschool.notification.constants;

/**
 * The Enum NotificationType.
 */
public enum NotificationType {

    REGISTRATION(1, "Registration"),
    MONTHLY_ATTENDANCE_REPORT(2, "Monthly Attendance Report"),
    YEARLY_ATTENDANCE_REPORT(3, "Yearly Attendance Report"),
    EXAM_RESULT(4, "Exam Result"),
    LEAVE_NOTIFICATION(5, "Leave Notification"),
    FEE_SUMMARY(6, "Fee Summary"),
    BIRTHDAY_GREETING(7, "Birthday Greeting"),
    PAYSLIP(8, "Payslip"),
    SERVICE_LETTER(9, "Service Letter"),
    PROMOTION_LETTER(10, "Promotion Letter"),
    DECLARE_HOLIDAYS(11, "Declare Holiday(s)"),
    ACHIEVEMENTS(12, "Achievements"),
    GENERAL_NOTICE(13, "General Notice");

    /** The id. */
    private int id;

    /** The value. */
    private String value;

    /**
     * Instantiates a new notification type.
     *
     * @param value the value
     */
    private NotificationType(int id, String value) {
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
     * Gets the by value.
     *
     * @param notificationTypeValue the notification type value
     * @return the by value
     */
    public static NotificationType getByValue(String notificationTypeValue) {
        NotificationType gotNotificationType = null;
        for (NotificationType notificationType : values()) {
            if (notificationType.getValue().equalsIgnoreCase(notificationTypeValue)) {
                gotNotificationType = notificationType;
                break;
            }
        }
        return gotNotificationType;
    }

    /**
     * Gets the.
     *
     * @param notificationTypeValue the notification type value
     * @return the notification type
     */
    public static NotificationType get(String notificationTypeValue) {
        NotificationType gotNotificationType = null;
        for (NotificationType notificationType : values()) {
            if (notificationType.toString().equalsIgnoreCase(notificationTypeValue)) {
                gotNotificationType = notificationType;
                break;
            }
        }
        return gotNotificationType;
    }

}
