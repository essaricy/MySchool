package com.myschool.user.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * The Class UserSession.
 */
public class UserSession implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The session id. */
	private String sessionId;

	/** The user id. */
	private int userId;

	/** The session start time. */
	private Date sessionStartTime;

	/** The session end time. */
	private Date sessionEndTime;

	/** The ip address. */
	private String ipAddress;

	/** The browser name. */
	private String browserName;

	/** The browser version. */
	private String browserVersion;

	/** The device. */
	private String device;

	/** The client os. */
	private String clientOS;

	/** The device information. */
	private String deviceInformation;

	/** The browser information. */
	private String browserInformation;

	/** The user activities. */
	private List<UserActivity> userActivities;

	/**
	 * Gets the session id.
	 *
	 * @return the session id
	 */
	public String getSessionId() {
		return sessionId;
	}

	/**
	 * Sets the session id.
	 *
	 * @param sessionId the new session id
	 */
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

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
	 * Gets the session start time.
	 *
	 * @return the session start time
	 */
	public Date getSessionStartTime() {
		return sessionStartTime;
	}

	/**
	 * Sets the session start time.
	 *
	 * @param sessionStartTime the new session start time
	 */
	public void setSessionStartTime(Date sessionStartTime) {
		this.sessionStartTime = sessionStartTime;
	}

	/**
	 * Gets the session end time.
	 *
	 * @return the session end time
	 */
	public Date getSessionEndTime() {
		return sessionEndTime;
	}

	/**
	 * Sets the session end time.
	 *
	 * @param sessionEndTime the new session end time
	 */
	public void setSessionEndTime(Date sessionEndTime) {
		this.sessionEndTime = sessionEndTime;
	}

	/**
	 * Gets the ip address.
	 *
	 * @return the ip address
	 */
	public String getIpAddress() {
		return ipAddress;
	}

	/**
	 * Sets the ip address.
	 *
	 * @param ipAddress the new ip address
	 */
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	/**
	 * Gets the browser name.
	 *
	 * @return the browser name
	 */
	public String getBrowserName() {
		return browserName;
	}

	/**
	 * Sets the browser name.
	 *
	 * @param browserName the new browser name
	 */
	public void setBrowserName(String browserName) {
		this.browserName = browserName;
	}

	/**
	 * Gets the browser version.
	 *
	 * @return the browser version
	 */
	public String getBrowserVersion() {
		return browserVersion;
	}

	/**
	 * Sets the browser version.
	 *
	 * @param browserVersion the new browser version
	 */
	public void setBrowserVersion(String browserVersion) {
		this.browserVersion = browserVersion;
	}

	/**
	 * Gets the device.
	 *
	 * @return the device
	 */
	public String getDevice() {
		return device;
	}

	/**
	 * Sets the device.
	 *
	 * @param device the new device
	 */
	public void setDevice(String device) {
		this.device = device;
	}

	/**
	 * Gets the client os.
	 *
	 * @return the client os
	 */
	public String getClientOS() {
		return clientOS;
	}

	/**
	 * Sets the client os.
	 *
	 * @param clientOS the new client os
	 */
	public void setClientOS(String clientOS) {
		this.clientOS = clientOS;
	}

	/**
	 * Gets the device information.
	 *
	 * @return the device information
	 */
	public String getDeviceInformation() {
		return deviceInformation;
	}

	/**
	 * Sets the device information.
	 *
	 * @param deviceInformation the new device information
	 */
	public void setDeviceInformation(String deviceInformation) {
		this.deviceInformation = deviceInformation;
	}

	/**
	 * Gets the browser information.
	 *
	 * @return the browser information
	 */
	public String getBrowserInformation() {
		return browserInformation;
	}

	/**
	 * Sets the browser information.
	 *
	 * @param browserInformation the new browser information
	 */
	public void setBrowserInformation(String browserInformation) {
		this.browserInformation = browserInformation;
	}

	/**
	 * Gets the user activities.
	 *
	 * @return the user activities
	 */
	public List<UserActivity> getUserActivities() {
		return userActivities;
	}

	/**
	 * Sets the user activities.
	 *
	 * @param userActivities the new user activities
	 */
	public void setUserActivities(List<UserActivity> userActivities) {
		this.userActivities = userActivities;
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
	    retValue.append("UserSession ( ")
	        .append(super.toString()).append(SEPARATOR)
	        .append("sessionId = ").append(this.sessionId).append(SEPARATOR)
	        .append("userId = ").append(this.userId).append(SEPARATOR)
	        .append("sessionStartTime = ").append(this.sessionStartTime).append(SEPARATOR)
	        .append("sessionEndTime = ").append(this.sessionEndTime).append(SEPARATOR)
	        .append("ipAddress = ").append(this.ipAddress).append(SEPARATOR)
	        .append("browserName = ").append(this.browserName).append(SEPARATOR)
	        .append("browserVersion = ").append(this.browserVersion).append(SEPARATOR)
	        .append("device = ").append(this.device).append(SEPARATOR)
	        .append("clientOS = ").append(this.clientOS).append(SEPARATOR)
	        .append("deviceInformation = ").append(this.deviceInformation).append(SEPARATOR)
	        .append("browserInformation = ").append(this.browserInformation).append(SEPARATOR)
	        .append("userActivities = ").append(this.userActivities).append(SEPARATOR)
	        .append(" )\n");
	    return retValue.toString();
	}

}
