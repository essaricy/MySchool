package com.myschool.user.dto;

import java.io.Serializable;

/**
 * The Class ChangePasswordDto.
 */
public class ChangePasswordDto implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The user id. */
    private int userId;

    /** The current password. */
    private String currentPassword;

    /** The new password. */
    private String newPassword;

    /** The confirmed password. */
    private String confirmedPassword;

    /**
     * Gets the user id.
     *
     * @return the user id
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets the user id.
     *
     * @param userId the new user id
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Gets the current password.
     *
     * @return the current password
     */
    public String getCurrentPassword() {
        return currentPassword;
    }

    /**
     * Sets the current password.
     *
     * @param currentPassword the new current password
     */
    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    /**
     * Gets the new password.
     *
     * @return the new password
     */
    public String getNewPassword() {
        return newPassword;
    }

    /**
     * Sets the new password.
     *
     * @param newPassword the new new password
     */
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    /**
     * Gets the confirmed password.
     *
     * @return the confirmed password
     */
    public String getConfirmedPassword() {
        return confirmedPassword;
    }

    /**
     * Sets the confirmed password.
     *
     * @param confirmedPassword the new confirmed password
     */
    public void setConfirmedPassword(String confirmedPassword) {
        this.confirmedPassword = confirmedPassword;
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
        retValue.append("ChangePasswordDto ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("confirmedPassword = ").append(this.confirmedPassword).append(SEPARATOR)
            .append("currentPassword = ").append(this.currentPassword).append(SEPARATOR)
            .append("newPassword = ").append(this.newPassword).append(SEPARATOR)
            .append("userId = ").append(this.userId).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}
