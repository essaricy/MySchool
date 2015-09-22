package com.myschool.user.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.myschool.application.dto.NumberNameValueDto;
import com.myschool.common.exception.DaoException;
import com.myschool.user.constants.UserType;
import com.myschool.user.dto.UsageCount;
import com.myschool.user.dto.UserSession;

/**
 * The Interface UsageDao.
 */
@Repository
public interface UsageDao {

	/**
	 * Gets the active sessions.
	 *
	 * @return the active sessions
	 * @throws DaoException the dao exception
	 */
	List<UserSession> getActiveSessions() throws DaoException;

	/**
	 * Gets the usage count.
	 *
	 * @return the usage count
	 * @throws DaoException the dao exception
	 */
	List<UsageCount> getUsageCount() throws DaoException;

	/**
	 * Gets the logins trend.
	 *
	 * @param userType the user type
	 * @param type the type
	 * @return the logins trend
	 * @throws DaoException the dao exception
	 */
	List<NumberNameValueDto> getLoginsTrend(UserType userType, int type) throws DaoException;

}
