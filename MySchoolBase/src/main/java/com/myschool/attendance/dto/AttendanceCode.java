package com.myschool.attendance.dto;

/**
 * The Enum AttendanceCode.
 */
public enum AttendanceCode {

    /** The WORKING_DAY. */
    WORKING_DAY("WF"),

    /** The WORKING_HALFDAY. */
    WORKING_HALFDAY("WH"),

    /** The NONWORKING_DAY. */
    NONWORKING_DAY("NF"),

    /** The STATUTORY_HOLIDAY. */
    STATUTORY_HOLIDAY("SF"),

    /** The AN_IMPROMPTU. */
    AN_IMPROMPTU("IF"),

    /** The LEAVE. */
    LEAVE("LF"),

    /** The HALFDAY_LEAVE. */
    HALFDAY_LEAVE("LH"),

    /** The ABSENT. */
    ABSENT("AF"),

    /** The HALFDAY_ABSENT. */
    HALFDAY_ABSENT("AH"),

    /** The PRESENT. */
    PRESENT("PF"),

    /** The HALFDAY_PRESENT. */
    HALFDAY_PRESENT("PH");

    /** The code. */
    private String code;

    /**
     * Instantiates a new attendance code.
     * 
     * @param code the code
     */
    private AttendanceCode(String code) {
        this.code = code;
    }

    /**
     * Gets the code.
     * 
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * Gets the.
     * 
     * @param code the code
     * @return the attendance code
     */
    public static AttendanceCode get(String code) {
        if (code != null) {
            for (AttendanceCode attendanceCode : values()) {
                if (code.equals(attendanceCode.getCode())) {
                    return attendanceCode;
                }
            }
        }
        return null;
    }

    /**
     * Gets the by name.
     * 
     * @param name the name
     * @return the by name
     */
    public static AttendanceCode getByName(String name) {
        if (name != null) {
            for (AttendanceCode attendanceCode : values()) {
                if (name.equals(attendanceCode.toString())) {
                    return attendanceCode;
                }
            }
        }
        return null;
    }

}
