package com.myschool.infra.filesystem.dto;

/**
 * The Enum AbsenceCode.
 */
public enum AbsenceCode {

    /** The CREATE. */
    CREATE,
    
    /** The ERROR. */
    ERROR;

    /**
     * Gets the.
     *
     * @param code the code
     * @return the absence code
     */
    public static AbsenceCode get(String code) {
        if (code != null) {
            for (AbsenceCode absenceCode : values()) {
                if (code.equalsIgnoreCase(absenceCode.toString())) {
                    return absenceCode;
                }
            }
        }
        return null;
    }
}
