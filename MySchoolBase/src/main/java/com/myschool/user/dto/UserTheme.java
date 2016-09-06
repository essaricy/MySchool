package com.myschool.user.dto;

/**
 * The Class UserTheme.
 */
public class UserTheme {

    /** The name. */
    private String name;

    /** The code. */
    private String code;

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
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
     * Sets the code.
     *
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("UserTheme [name=").append(name).append(", code=")
                .append(code).append("]");
        return builder.toString();
    }

}
