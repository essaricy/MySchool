package com.myschool.user.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.myschool.common.exception.DaoException;
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

}
