package com.myschool.user.dto;

import java.io.Serializable;
import java.util.List;

import com.myschool.user.constants.UserType;

/**
 * The Class UserContext.
 */
public class UserContext implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The login. */
    private LoginDto login;

    /** The user type. */
    private UserType userType;

    /** The user preference. */
    private UserPreference userPreference;

    /** The user statistics. */
    private UserStatistics userStatistics;

    /** The module access. */
    private List<ModuleAccessDto> moduleAccess;

	/**
	 * Gets the login.
	 *
	 * @return the login
	 */
	public LoginDto getLogin() {
		return login;
	}

	/**
	 * Sets the login.
	 *
	 * @param login the new login
	 */
	public void setLogin(LoginDto login) {
		this.login = login;
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
	 * Constructs a <code>String</code> with all attributes
	 * in name = value format.
	 *
	 * @return a <code>String</code> representation 
	 * of this object.
	 */
	public String toString() {
	    final String SEPARATOR = ", ";
	    StringBuilder retValue = new StringBuilder();
	    retValue.append("UserContext ( ")
	        .append(super.toString()).append(SEPARATOR)
	        .append("login = ").append(this.login).append(SEPARATOR)
	        .append("userType = ").append(this.userType).append(SEPARATOR)
	        .append("userPreference = ").append(this.userPreference).append(SEPARATOR)
	        .append("userStatistics = ").append(this.userStatistics).append(SEPARATOR)
	        .append("moduleAccess = ").append(this.moduleAccess).append(SEPARATOR)
	        .append(" )\n");
	    return retValue.toString();
	}

}
