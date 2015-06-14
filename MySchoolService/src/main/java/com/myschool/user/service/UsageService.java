package com.myschool.user.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.myschool.common.exception.ServiceException;
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

}
