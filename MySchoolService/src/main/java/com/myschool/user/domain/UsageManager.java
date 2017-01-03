package com.myschool.user.domain;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.application.dto.NumberNameValueDto;
import com.myschool.common.exception.DaoException;
import com.myschool.user.assembler.UsageDataAssembler;
import com.myschool.user.constants.UserType;
import com.myschool.user.dao.UsageDao;
import com.myschool.user.dto.UsageCount;
import com.myschool.user.dto.UserSession;
import com.quasar.core.exception.DataException;
import com.quasar.core.util.StringUtil;

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

	/**
	 * Gets the usage count.
	 *
	 * @return the usage count
	 * @throws DataException the data exception
	 */
	public List<UsageCount> getUsageCount() throws DataException {
        try {
            return usageDao.getUsageCount();
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
	}

	/**
	 * Gets the logins trend.
	 *
	 * @param trendType the trend type
	 * @return the logins trend
	 * @throws DataException the data exception
	 */
	public Map<UserType, List<NumberNameValueDto>> getLoginsTrend(
			String trendType) throws DataException {
        Map<UserType, List<NumberNameValueDto>> nameNumberValuesByUserType = null;
        try {
        	if (StringUtil.isNullOrBlank(trendType)) {
        		throw new DataException("No value specified for trend type");
        	}
        	System.out.println("trendType " + trendType);
        	int type = 0;
        	if (trendType.equals("MonthOfYear")) {
        		type = Calendar.MONTH;
        	} else if (trendType.equals("DayOfYear")) {
        		type = Calendar.DAY_OF_YEAR;
        	} else if (trendType.equals("DayOfWeek")) {
        		type = Calendar.DAY_OF_WEEK;
        	} else if (trendType.equals("HourOfDay")) {
        		type = Calendar.HOUR_OF_DAY;
        	} else if (trendType.equals("DayOfMonth")) {
        		type = Calendar.DAY_OF_MONTH;
        	} else {
        		throw new DataException("Invalid value specified for trend type");
        	}
        	nameNumberValuesByUserType = new HashMap<UserType, List<NumberNameValueDto>>();
        	for (UserType userType : UserType.values()) {
            	nameNumberValuesByUserType.put(userType, UsageDataAssembler.fill(usageDao.getLoginsTrend(userType, type), type));
			}
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
		return nameNumberValuesByUserType;
	}

}
