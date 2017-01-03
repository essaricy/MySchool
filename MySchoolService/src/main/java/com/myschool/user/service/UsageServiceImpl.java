package com.myschool.user.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myschool.application.dto.NumberNameValueDto;
import com.myschool.common.exception.ServiceException;
import com.myschool.user.constants.UserType;
import com.myschool.user.domain.UsageManager;
import com.myschool.user.dto.UsageCount;
import com.myschool.user.dto.UserSession;
import com.quasar.core.exception.DataException;

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

	/* (non-Javadoc)
	 * @see com.myschool.user.service.UsageService#getUsageCount()
	 */
	@Override
	public List<UsageCount> getUsageCount() throws ServiceException {
		try {
            return usageManager.getUsageCount();
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(),
                    dataException);
        }
	}

	/* (non-Javadoc)
	 * @see com.myschool.user.service.UsageService#getLoginsTrend(java.lang.String)
	 */
	@Override
	public Map<UserType, List<NumberNameValueDto>> getLoginsTrend(
			String trendType) throws ServiceException {
		try {
            return usageManager.getLoginsTrend(trendType);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(),
                    dataException);
        }
	}

}
