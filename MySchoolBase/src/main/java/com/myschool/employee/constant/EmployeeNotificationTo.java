package com.myschool.employee.constant;

/**
 * The Enum EmployeeNotificationTo.
 */
public enum EmployeeNotificationTo {

    /** The PERSONAL. */
    PERSONAL("P"),
    
    /** The WORK. */
    WORK("W");

    /** The notification to code. */
    private String notificationToCode;

    /**
     * Instantiates a new employee notification to.
     * 
     * @param notificationToCode the notification to code
     */
    private EmployeeNotificationTo(String notificationToCode) {
        this.notificationToCode = notificationToCode;
    }

    /**
     * Gets the notification to code.
     * 
     * @return the notification to code
     */
    public String getNotificationToCode() {
        return notificationToCode;
    }

    /**
     * Gets the by code.
     * 
     * @param notificationToCode the notification to code
     * @return the by code
     */
    public static EmployeeNotificationTo getByCode(String notificationToCode) {
        if (notificationToCode != null) {
            for (EmployeeNotificationTo employeeNotificationTo : values()) {
                if (employeeNotificationTo.getNotificationToCode().equalsIgnoreCase(notificationToCode)) {
                    return employeeNotificationTo;
                }
            }
        }
        return null;
    }
}
