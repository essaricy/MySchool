package com.myschool.user.dto;

import java.io.Serializable;

/**
 * The Class UsageCount.
 */
public class UsageCount implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The user. */
	private UsersDto user;

	/** The user statistics. */
	private UserStatistics userStatistics;

	/**
	 * Gets the user.
	 *
	 * @return the user
	 */
	public UsersDto getUser() {
		return user;
	}

	/**
	 * Sets the user.
	 *
	 * @param user the new user
	 */
	public void setUser(UsersDto user) {
		this.user = user;
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
	 * Constructs a <code>String</code> with all attributes
	 * in name = value format.
	 *
	 * @return a <code>String</code> representation 
	 * of this object.
	 */
	public String toString() {
	    final String SEPARATOR = ", ";
	    StringBuilder retValue = new StringBuilder();
	    retValue.append("UsageCount ( ")
	        .append(super.toString()).append(SEPARATOR)
	        .append("user = ").append(this.user).append(SEPARATOR)
	        .append("userStatistics = ").append(this.userStatistics).append(SEPARATOR)
	        .append(" )\n");
	    return retValue.toString();
	}

}
