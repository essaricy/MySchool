package com.myschool.user.dto;

import java.io.Serializable;

/**
 * The Class UserTypeDto.
 */
public class UserTypeDto implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The user type id. */
    private int userTypeId;

    /** The description. */
    private String description;

    /**
     * Gets the user type id.
     * 
     * @return the user type id
     */
    public int getUserTypeId() {
        return userTypeId;
    }

    /**
     * Sets the user type id.
     * 
     * @param userTypeId the new user type id
     */
    public void setUserTypeId(int userTypeId) {
        this.userTypeId = userTypeId;
    }

    /**
     * Gets the description.
     * 
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description.
     * 
     * @param description the new description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Constructs a <code>String</code> with all attributes
     * in name = value format.
     *
     * @return a <code>String</code> representation 
     * of this object.
     */
    public String toString() {
        final String SEPARATOR = ", ";
        StringBuilder retValue = new StringBuilder();
        retValue.append("UserTypeDto ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("description = ").append(this.description).append(SEPARATOR)
            .append("userTypeId = ").append(this.userTypeId).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}
