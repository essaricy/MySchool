package com.myschool.user.constants;

/**
 * The Enum UserType.
 */
public enum UserType {

    /** The ADMIN. */
    ADMIN {
        @Override
        public int getUserTypeValue() {
            return 1;
        }

        @Override
        public String getPrefix() {
            return "";
        }
    },

    /** The EMPLOYEE. */
    EMPLOYEE {
        @Override
        public int getUserTypeValue() {
            return 2;
        }

        @Override
        public String getPrefix() {
            return "E";
        }
    },

    /** The STUDENT. */
    STUDENT {
        @Override
        public int getUserTypeValue() {
            return 3;
        }

        @Override
        public String getPrefix() {
            return "S";
        }
    }; 

    /**
     * User type value.
     *
     * @return the int
     */
    public abstract int getUserTypeValue();

    /**
     * Gets the prefix.
     *
     * @return the prefix
     */
    public abstract String getPrefix();

    /**
     * Gets the.
     *
     * @param userTypeName the user type name
     * @return the user type
     */
    public static UserType get(String userTypeName) {
        UserType gotUserType = null;
        if (userTypeName != null) {
            for (UserType userType : values()) {
                if (userType.toString().equalsIgnoreCase(userTypeName)) {
                    gotUserType = userType;
                    break;
                }
            }
        }
        return gotUserType;
    }

    /**
     * Gets the by value.
     *
     * @param userTypeValue the user type value
     * @return the by value
     */
    public static UserType getByValue(int userTypeValue) {
        UserType gotUserType = null;
        for (UserType userType : values()) {
            if (userType.getUserTypeValue() == userTypeValue) {
                gotUserType = userType;
                break;
            }
        }
        return gotUserType;
    }

}
