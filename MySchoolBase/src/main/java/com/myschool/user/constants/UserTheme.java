package com.myschool.user.constants;

/**
 * The Enum UserTheme.
 */
public enum UserTheme {

    /** The BLACK. */
    BLACK,

    /** The BLUE. */
    BLUE,

    /** The CHOCOLATE. */
    CHOCOLATE,

    /** The DARK_GRAY. */
    DARK_GRAY,

    /** The GREEN. */
    GREEN,

    /** The LIGHT_  BLUE. */
    LIGHT_BLUE,

    /** The ORANGE. */
    ORANGE,

    /** The RED. */
    RED,

    /** The YELLOW. */
    YELLOW;

    /**
     * Gets the.
     * 
     * @param value the value
     * @return the user theme
     */
    public static UserTheme get(String value) {
        if (value != null) {
            for (UserTheme userTheme : values()) {
                if (value.equalsIgnoreCase(userTheme.toString())) {
                    return userTheme;
                }
            }
        }
        return null;
    }

}
