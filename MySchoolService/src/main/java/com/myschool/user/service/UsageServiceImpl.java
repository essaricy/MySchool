package com.myschool.user.service;

import java.util.List;

import com.myschool.user.domain.UsageManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myschool.common.exception.DataException;
import com.myschool.common.exception.ServiceException;
import com.myschool.user.dto.UserSession;

/**
 * The Class UsageServiceImpl.
 */
@Service
public class UsageServiceImpl implements UsageService {

	/** The usage manager. */
	@Autowired
	private UsageManager usageManager;

	/* (non-Javadoc)
	 * @see com.myschool.user.service.UsageService#getActiveSessions()
	 */
	@Override
	public List<UserSession> getActiveSessions() throws ServiceException {
		try {
            return usageManager.getActiveSessions();
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(),
                    dataException);
        }
	}

}
