package com.myschool.user.domain;

import java.util.List;

import com.myschool.user.dao.UsageDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.common.exception.DaoException;
import com.myschool.common.exception.DataException;
import com.myschool.user.dto.UserSession;

/**
 * The Class UsageManager.
 */
@Component
public class UsageManager {

	/** The usage dao. */
	@Autowired
	private UsageDao usageDao;

	/**
	 * Gets the active sessions.
	 *
	 * @return the active sessions
	 * @throws DataException the data exception
	 */
	public List<UserSession> getActiveSessions() throws DataException {
        try {
            return usageDao.getActiveSessions();
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
	}

}
