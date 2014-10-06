package com.myschool.user.dto;

import java.io.Serializable;

import com.myschool.user.constants.UserTheme;

/**
 * The Class UserPreference.
 */
public class UserPreference implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The user id. */
    private int userId;

    /** The allow ads. */
    private boolean allowAds;

    /** The records per page. */
    private int recordsPerPage;

    /** The user theme. */
    private UserTheme userTheme;

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
     * Checks if is allow ads.
     *
     * @return true, if is allow ads
     */
    public boolean isAllowAds() {
        return allowAds;
    }

    /**
     * Sets the allow ads.
     *
     * @param allowAds the new allow ads
     */
    public void setAllowAds(boolean allowAds) {
        this.allowAds = allowAds;
    }

    /**
     * Gets the records per page.
     *
     * @return the records per page
     */
    public int getRecordsPerPage() {
        return recordsPerPage;
    }

    /**
     * Sets the records per page.
     *
     * @param recordsPerPage the new records per page
     */
    public void setRecordsPerPage(int recordsPerPage) {
        this.recordsPerPage = recordsPerPage;
    }

    /**
     * Gets the user theme.
     * 
     * @return the user theme
     */
    public UserTheme getUserTheme() {
        return userTheme;
    }

    /**
     * Sets the user theme.
     * 
     * @param userTheme the new user theme
     */
    public void setUserTheme(UserTheme userTheme) {
        this.userTheme = userTheme;
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
        retValue.append("UserPreference ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("userId = ").append(this.userId).append(SEPARATOR)
            .append("allowAds = ").append(this.allowAds).append(SEPARATOR)
            .append("recordsPerPage = ").append(this.recordsPerPage).append(SEPARATOR)
            .append("userTheme = ").append(this.userTheme).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}
