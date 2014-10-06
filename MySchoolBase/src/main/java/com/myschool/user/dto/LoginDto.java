package com.myschool.user.dto;

import java.io.Serializable;
import java.util.List;

import com.myschool.user.constants.UserType;

/**
 * The Class LoginDto.
 */
public class LoginDto implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The id. */
    private int id;

    /** The login id. */
    private String loginId;

    /** The password. */
    private String password;

    /** The ref user id. */
    private int refUserId;

    /** The user type. */
    private UserType userType;

    /** The user preference. */
    private UserPreference userPreference;

    /** The user statistics. */
    private UserStatistics userStatistics;

    /** The module access. */
    private List<ModuleAccessDto> moduleAccess;

    /** The user details. */
    private Object userDetails;

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
     * Gets the login id.
     * 
     * @return the login id
     */
    public String getLoginId() {
        return loginId;
    }

    /**
     * Sets the login id.
     * 
     * @param loginId the new login id
     */
    public void setLoginId(String loginId) {
        this.loginId = loginId;
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
     * Gets the user preference.
     *
     * @return the user preference
     */
    public UserPreference getUserPreference() {
        return userPreference;
    }

    /**
     * Sets the user preference.
     *
     * @param userPreference the new user preference
     */
    public void setUserPreference(UserPreference userPreference) {
        this.userPreference = userPreference;
    }

    /**
     * Gets the user statistics.
     *
     * @return the user statistics
     */
    public UserStatistics getUserStatistics() {
        return userStatistics;
    }

    /**
     * Sets the user statistics.
     *
     * @param userStatistics the new user statistics
     */
    public void setUserStatistics(UserStatistics userStatistics) {
        this.userStatistics = userStatistics;
    }

    /**
     * Gets the module access.
     *
     * @return the module access
     */
    public List<ModuleAccessDto> getModuleAccess() {
        return moduleAccess;
    }

    /**
     * Sets the module access.
     *
     * @param moduleAccess the new module access
     */
    public void setModuleAccess(List<ModuleAccessDto> moduleAccess) {
        this.moduleAccess = moduleAccess;
    }

    /**
     * Gets the user details.
     * 
     * @return the user details
     */
    public Object getUserDetails() {
        return userDetails;
    }

    /**
     * Sets the user details.
     * 
     * @param userDetails the new user details
     */
    public void setUserDetails(Object userDetails) {
        this.userDetails = userDetails;
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
        retValue.append("LoginDto ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("id = ").append(this.id).append(SEPARATOR)
            .append("loginId = ").append(this.loginId).append(SEPARATOR)
            .append("password = ").append(this.password).append(SEPARATOR)
            .append("refUserId = ").append(this.refUserId).append(SEPARATOR)
            .append("userType = ").append(this.userType).append(SEPARATOR)
            .append("userPreference = ").append(this.userPreference).append(SEPARATOR)
            .append("userStatistics = ").append(this.userStatistics).append(SEPARATOR)
            .append("moduleAccess = ").append(this.moduleAccess).append(SEPARATOR)
            .append("userDetails = ").append(this.userDetails).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}
