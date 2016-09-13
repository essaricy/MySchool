package com.myschool.common.constants;

/**
 * The Enum RecordStatus.
 */
public enum RecordStatus {

    /** The verified. */
    VERIFIED, 

    /** The unverified. */
    UNVERIFIED;

    /**
     * Gets the.
     *
     * @param verified the verified
     * @return the record status
     */
    public static RecordStatus get(boolean verified) {
        return (verified) ? VERIFIED : UNVERIFIED;
    }

    /**
     * Gets the.
     *
     * @param type the type
     * @return the record status
     */
    public static RecordStatus get(String type) {
        if (type != null) {
            for (RecordStatus recordStatus : values()) {
                if (type.equalsIgnoreCase(recordStatus.toString())) {
                    return recordStatus;
                }
            }
        }
        return null;
    }

}
