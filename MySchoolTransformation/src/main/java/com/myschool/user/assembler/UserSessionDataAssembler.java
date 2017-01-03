package com.myschool.user.assembler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import com.myschool.user.dto.UsageCount;
import com.myschool.user.dto.UserActivity;
import com.myschool.user.dto.UserSession;
import com.quasar.core.util.StringUtil;

/**
 * The Class UserSessionDataAssembler.
 */
public class UserSessionDataAssembler {

	/**
	 * Creates the.
	 *
	 * @param resultSet the result set
	 * @return the user session
	 * @throws SQLException the sQL exception
	 */
	public static UserSession create(ResultSet resultSet) throws SQLException {
		UserSession userSession = new UserSession();
		userSession.setBrowserInformation(resultSet.getString("BROWSER"));
		userSession.setDeviceInformation(resultSet.getString("DEVICE"));
		userSession.setIpAddress(resultSet.getString("IP_ADDRESS"));
		userSession.setSessionId(resultSet.getString("SESSION_ID"));
		Timestamp sessionStartTime = resultSet.getTimestamp("SESSION_START_TIME");
		if (sessionStartTime != null) {
			userSession.setSessionStartTime(new Date(sessionStartTime.getTime()));
		}
		Timestamp sessionEndTime = resultSet.getTimestamp("SESSION_END_TIME");
		if (sessionEndTime != null) {
			userSession.setSessionEndTime(new Date(sessionEndTime.getTime()));
		}
		Object userId = resultSet.getObject("USER_ID");
		if (userId != null) {
			userSession.setUserId(((Long)userId).intValue());
		}
		updateInformation(userSession);
		return userSession;
	}

	/**
	 * Update information.
	 *
	 * @param userSession the user session
	 */
	public static void updateInformation(UserSession userSession) {
		String deviceInformation = userSession.getDeviceInformation();
		String browserInformation = userSession.getBrowserInformation();
		String device = userSession.getDevice();
		String clientOS = userSession.getClientOS();
		String browserName = userSession.getBrowserName();
		StringBuffer buffer = new StringBuffer();

		if (StringUtil.isNullOrBlank(deviceInformation)) {
			// Arrange Device Information
			if (device != null) {
				buffer.append(device);
			}
			buffer.append("/");
			if (clientOS != null) {
				buffer.append(clientOS);
			}
			userSession.setDeviceInformation(buffer.toString());
			buffer.setLength(0);
		} else {
			// Extract Device Information
			if (deviceInformation.indexOf("/") != -1) {
				String[] split = deviceInformation.split("/");
				userSession.setDevice(split[0]);
				userSession.setClientOS(split[0]);
			}
		}

		if (StringUtil.isNullOrBlank(browserInformation)) {
			// Arrange Browser information
			if (browserName != null) {
				buffer.append(browserName);
			}
			buffer.append("/");
			String browserVersion = userSession.getBrowserVersion();
			if (browserVersion != null) {
				buffer.append(browserVersion);
			}
			userSession.setBrowserInformation(buffer.toString());
			buffer.setLength(0);
		} else {
			// Extract Browser Information
			if (browserInformation.indexOf("/") != -1) {
				String[] split = browserInformation.split("/");
				userSession.setBrowserName(split[0]);
				userSession.setBrowserVersion(split[0]);
			}
		}
	}

	/**
	 * Creates the user activity.
	 *
	 * @param requestURI the request uri
	 * @param startTime the start time
	 * @param endTime the end time
	 * @return the user activity
	 */
	public static UserActivity createUserActivity(String requestURI, long startTime, long endTime) {
		UserActivity userActivity = null;
		if (!StringUtil.isNullOrBlank(requestURI)) {
			userActivity = new UserActivity();
			if (requestURI != null && requestURI.indexOf("/") != -1) {
				userActivity.setModule(requestURI.substring(0, requestURI.indexOf("/")));
			}
			userActivity.setRequestedTime(new Date(startTime));
			userActivity.setRequestUrl(requestURI);
			userActivity.setServiceLatency(endTime-startTime);
		}
		return userActivity;
	}

	/**
	 * Creates the usage count.
	 *
	 * @param resultSet the result set
	 * @return the usage count
	 * @throws SQLException the sQL exception
	 */
	public static UsageCount createUsageCount(ResultSet resultSet) throws SQLException {
		UsageCount usageCount = new UsageCount();
		usageCount.setUser(UserDataAssembler.createUser(resultSet));
		usageCount.setUserStatistics(UserDataAssembler.createUserStatistics(resultSet));
		return usageCount;
	}
}
