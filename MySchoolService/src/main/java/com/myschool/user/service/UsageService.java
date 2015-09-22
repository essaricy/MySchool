package com.myschool.user.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.myschool.application.dto.NumberNameValueDto;
import com.myschool.common.exception.ServiceException;
import com.myschool.user.constants.UserType;
import com.myschool.user.dto.UsageCount;
import com.myschool.user.dto.UserSession;

/**
 * The Interface UsageService.
 */
@Service
public interface UsageService {

	/**
	 * Gets the active sessions.
	 *
	 * @return the active sessions
	 * @throws ServiceException the service exception
	 */
	List<UserSession> getActiveSessions() throws ServiceException;

	/**
	 * Gets the usage count.
	 *
	 * @return the usage count
	 * @throws ServiceException the service exception
	 */
	List<UsageCount> getUsageCount() throws ServiceException;

	/**
	 * Gets the logins trend.
	 *
	 * @param trendType the trend type
	 * @return the logins trend
	 * @throws ServiceException the service exception
	 */
	Map<UserType, List<NumberNameValueDto>> getLoginsTrend(String trendType) throws ServiceException;

}
