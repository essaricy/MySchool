package com.myschool.graph.constant;

/**
 * The Enum ToDateType.
 */
public enum ToDateType {

    /** The WTD. */
    WTD(7),

    /** The MTD. */
    MTD(31),

    /** The YTD. */
    YTD(365);

    /** The duration. */
    private int duration;

    private ToDateType(int duration) {
        this.duration = duration;
    }

    /**
     * Gets the duration.
     * 
     * @return the duration
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Gets the.
     * 
     * @param toDateTypeValue the to date type value
     * @return the to date type
     */
    public static ToDateType get(String toDateTypeValue) {
        ToDateType gotToDateType = null;
        if (toDateTypeValue != null) {
            for (ToDateType toDateType : values()) {
                if (toDateType.toString().equalsIgnoreCase(toDateTypeValue)) {
                    gotToDateType = toDateType;
                    break;
                }
            }
        }
        return gotToDateType;
    }

}
