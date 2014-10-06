package com.myschool.attendance.dto;

/**
 * The Enum AttendanceCode.
 */
public enum AttendanceCode {

    /** The ABSENT. */
    ABSENT("A"),

    /** The DECLARED_HOLIDAY. */
    DECLARED_HOLIDAY("D"),

    /** The GENERAL_HOLIDAY. */
    GENERAL_HOLIDAY("G"),

    /** The HALF_DAY_LEAVE. */
    HALF_DAY_LEAVE("H"),

    /** The ON_LEAVE. */
    ON_LEAVE("L"),

    /** The PRESENT. */
    PRESENT("P"),

    /** The UNASSIGNED. */
    UNASSIGNED("U"),

    /** The UNACCOUNTED. */
    UNACCOUNTED(null);

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
        return UNACCOUNTED;
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
