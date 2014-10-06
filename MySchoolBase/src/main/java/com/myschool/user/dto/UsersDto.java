package com.myschool.user.dto;

import java.io.Serializable;

import com.myschool.user.constants.UserType;

/**
 * The Class UsersDto.
 */
public class UsersDto implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The id. */
    private int id;

    /** The user name. */
    private String userName;

    /** The password. */
    private String password;

    /** The ref user id. */
    private int refUserId;

    /** The user type. */
    private UserType userType;

    /** The display name. */
    private String displayName;

    /**
     * Gets the id.
     * 
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the id.
     * 
     * @param id the new id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the user name.
     * 
     * @return the user name
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets the user name.
     * 
     * @param userName the new user name
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Gets the password.
     * 
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password.
     * 
     * @param password the new password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the ref user id.
     *
     * @return the ref user id
     */
    public int getRefUserId() {
        return refUserId;
    }

    /**
     * Sets the ref user id.
     *
     * @param refUserId the new ref user id
     */
    public void setRefUserId(int refUserId) {
        this.refUserId = refUserId;
    }

    /**
     * Gets the display name.
     *
     * @return the display name
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Sets the display name.
     *
     * @param displayName the new display name
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Gets the user type.
     *
     * @return the user type
     */
    public UserType getUserType() {
        return userType;
    }

    /**
     * Sets the user type.
     *
     * @param userType the new user type
     */
    public void setUserType(UserType userType) {
        this.userType = userType;
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
        retValue.append("UsersDto ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("displayName = ").append(this.displayName).append(SEPARATOR)
            .append("id = ").append(this.id).append(SEPARATOR)
            .append("password = ").append(this.password).append(SEPARATOR)
            .append("refUserId = ").append(this.refUserId).append(SEPARATOR)
            .append("userName = ").append(this.userName).append(SEPARATOR)
            .append("userType = ").append(this.userType).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}
