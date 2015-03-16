package com.myschool.user.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.application.assembler.StatisticsDataAssembler;
import com.myschool.application.dto.DateValueDto;
import com.myschool.common.exception.DaoException;
import com.myschool.common.exception.DataException;
import com.myschool.common.exception.InvalidDataException;
import com.myschool.common.util.ConversionUtil;
import com.myschool.common.util.Encryptor;
import com.myschool.common.util.PasswordUtil;
import com.myschool.common.util.StringUtil;
import com.myschool.employee.dao.EmployeeDao;
import com.myschool.employee.dto.EmployeeDto;
import com.myschool.graph.constant.ToDateType;
import com.myschool.student.dao.StudentDao;
import com.myschool.student.dto.StudentDto;
import com.myschool.user.assembler.UserDataAssembler;
import com.myschool.user.assembler.UserSessionDataAssembler;
import com.myschool.user.constants.UserType;
import com.myschool.user.dao.UserDao;
import com.myschool.user.dto.ChangePasswordDto;
import com.myschool.user.dto.UserActivity;
import com.myschool.user.dto.UserPreference;
import com.myschool.user.dto.UserSession;
import com.myschool.user.dto.UsersDto;

/**
 * The Class UserManager.
 */
@Component
public class UserManager {

    /** The user dao. */
    @Autowired
    private UserDao userDao;

    /** The employee dao. */
    @Autowired
    private EmployeeDao employeeDao;

    /** The student dao. */
    @Autowired
    private StudentDao studentDao;

    /**
     * Creates the user.
     *
     * @param student the student
     * @return the int
     * @throws DataException the data exception
     */
    public int createUser(StudentDto student) throws DataException {
        try {
            return createUser(UserDataAssembler.createUser(student));
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
    }

    /**
     * Creates the user.
     *
     * @param employee the employee
     * @return the int
     * @throws DataException the data exception
     */
    public int createUser(EmployeeDto employee) throws DataException {
        try {
            return createUser(UserDataAssembler.createUser(employee));
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
    }

    /**
     * Creates the user.
     *
     * @param user the user
     * @return the int
     * @throws DaoException the dao exception
     */
    private int createUser(UsersDto user) throws DaoException {
        int userId = userDao.createUser(user);
        /*if (userId > 0) {
            // create User preferences
            userDao.createUserPreferences(userId);
            // create user statistics
            userDao.createUserStatistics(userId);
        }*/
        return userId;
    }

    /**
     * Restore password.
     *
     * @param student the student
     * @return true, if successful
     * @throws DataException the data exception
     */
    public boolean restorePassword(StudentDto student) throws DataException {
        String initialPassword = PasswordUtil.getInitialPassword(student);
        try {
            return userDao.restorePassword(UserType.STUDENT, student.getStudentId(), initialPassword);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
    }

    /**
     * Restore password.
     *
     * @param employee the employee
     * @return true, if successful
     * @throws DataException the data exception
     */
    public boolean restorePassword(EmployeeDto employee) throws DataException {
        String initialPassword = PasswordUtil.getInitialPassword(employee);
        try {
            return userDao.restorePassword(UserType.EMPLOYEE, employee.getEmployeeId(), initialPassword);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
    }

    /**
     * Change password.
     *
     * @param changePassword the change password
     * @return true, if successful
     * @throws DataException the data exception
     */
    public boolean changePassword(ChangePasswordDto changePassword) throws DataException {
        try {
            int userId = changePassword.getUserId();
            if (userId <=0 ) {
                throw new InvalidDataException("Invalid User ID");
            }
            String currentPassword = changePassword.getCurrentPassword();
            String newPassword = changePassword.getNewPassword();
            String confirmedPassword = changePassword.getConfirmedPassword();

            UsersDto usersDto = userDao.getUser(userId);
            if (usersDto == null) {
                throw new InvalidDataException("Invalid User ID");
            }
            String password = usersDto.getPassword();
            if (!password.equals(Encryptor.getInstance().encrypt(currentPassword))) {
                throw new InvalidDataException("The current password you provided does not match with our records.");
            }
            if (!newPassword.equals(confirmedPassword)) {
                throw new InvalidDataException("New password and Confirmed Password do not match.");
            }
            checkIfUserActive(usersDto);
            return userDao.changePassword(userId, Encryptor.getInstance().encrypt(newPassword));
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
    }

    /**
     * Check if user active.
     * 
     * @param usersDto the users dto
     * @throws DataException the data exception
     * @throws DaoException the dao exception
     */
    private void checkIfUserActive(UsersDto usersDto) throws DataException, DaoException {
        UserType userType = usersDto.getUserType();
        if (userType == UserType.EMPLOYEE) {
            EmployeeDto employee = employeeDao.get(usersDto.getRefUserId());
            if (employee == null) {
                throw new DataException("Cannot change the password. The employee information does not exist.");
            }
            if (!employee.isVerified()) {
                throw new DataException("Cannot change the password. Employee profile is not verified.");
            }
            String employmentEndDateValue = employee.getEmploymentEndDate();
            if (employmentEndDateValue != null) {
                Date employmentEndDate = ConversionUtil.fromApplicationDate(employmentEndDateValue);
                if (employmentEndDate.before(new Date())) {
                    throw new DataException("Cannot change the password. Employee has been terminated.");
                }
            }
        } else if (userType == UserType.STUDENT) {
            StudentDto student = studentDao.get(usersDto.getRefUserId());
            if (student == null) {
                throw new DataException("Cannot change the password. The Student information does not exist.");
            }
            if (!student.isVerified()) {
                throw new DataException("Cannot change the password. Student profile is not verified.");
            }
        }
    }

    /**
     * Change preferences.
     *
     * @param userPreference the user preference
     * @return true, if successful
     * @throws DataException the data exception
     */
    public boolean changePreferences(UserPreference userPreference) throws DataException {
        try {
            int userId = userPreference.getUserId();
            if (userId <=0 ) {
                throw new InvalidDataException("Invalid User ID");
            }

            UsersDto usersDto = userDao.getUser(userId);
            if (usersDto == null) {
                throw new InvalidDataException("Invalid User ID");
            }
            checkIfUserActive(usersDto);
            return userDao.changePreferences(userPreference);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
    }

    /**
     * Gets the user.
     * 
     * @param userType the user type
     * @param employeeId the employee id
     * @return the user
     * @throws DataException the data exception
     */
    public UsersDto getUser(UserType userType, int employeeId) throws DataException {
        try {
            return userDao.getUser(userType, employeeId);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
    }

    /**
     * Gets the logins to date.
     * 
     * @param toDateType the to date type
     * @return the logins to date
     * @throws DataException the data exception
     */
    public Map<UserType, List<DateValueDto>> getLoginsToDate(
            ToDateType toDateType) throws DataException {
        List<DateValueDto> dateValues = null;
        Map<UserType, List<DateValueDto>> map = new HashMap<UserType, List<DateValueDto>>();
        try {
            // get the type of users.
            // for each user get the statistics. 
            for (UserType userType : UserType.values()) {
                dateValues = userDao.getLoginsToDate(userType, toDateType);
                if (dateValues == null) {
                    dateValues = new ArrayList<DateValueDto>();
                }
                StatisticsDataAssembler.fillEmptyDates(dateValues, toDateType);
                map.put(userType, dateValues);
            }
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return map;
    }

	/**
	 * Creates the.
	 *
	 * @param userSession the user session
	 * @return the int
	 * @throws DataException the data exception
	 */
	public int create(UserSession userSession) throws DataException {
		try {
			validateUserSession(userSession);
			return userDao.create(userSession);
		} catch (DaoException daoException) {
			throw new DataException(daoException.getMessage(), daoException);
		}
	}

	/**
	 * Update.
	 *
	 * @param userSession the user session
	 * @return true, if successful
	 * @throws DataException the data exception
	 */
	public boolean update(UserSession userSession) throws DataException {
		boolean updated = false;
		try {
			validateUserSession(userSession);
			String sessionId = userSession.getSessionId();
			UserSession existingSession = userDao.getUserSession(sessionId);
			if (existingSession == null) {
				throw new DataException("User Session " + sessionId + " is not created.");
			}
			if (existingSession.getSessionEndTime() != null) {
				throw new DataException("User Session " + sessionId + " is already closed. Cannot update now.");
			}
			int userId = userSession.getUserId();
			// check if the user id is present in the database or not.
			if (userId != 0) {
				UsersDto user = userDao.getUser(userId);
				if (user == null) {
					throw new DataException("User with ID " + userId + " not found.");
				}
			}
			updated = userDao.update(userSession);
		} catch (DaoException daoException) {
			throw new DataException(daoException.getMessage(), daoException);
		}
		return updated;
	}

	public boolean createUserActivities(String sessionId,
			List<UserActivity> userActivities) throws DataException {
		boolean updated = false;
		try {
			//String sessionId = userSession.getSessionId();
			UserSession existingSession = userDao.getUserSession(sessionId);
			if (existingSession == null) {
				throw new DataException("User Session " + sessionId + " is not created.");
			}
			if (userActivities != null && !userActivities.isEmpty()) {
				// Update user activities
				updated = userDao.create(sessionId, userActivities);
			}
		} catch (DaoException daoException) {
			throw new DataException(daoException.getMessage(), daoException);
		}
		return updated;
	}

	/**
	 * Validate user session.
	 *
	 * @param userSession the user session
	 * @throws DataException the data exception
	 */
	private void validateUserSession(UserSession userSession) throws DataException {
		if (userSession == null) {
			throw new DataException("UserSession object is null");
		}
		String sessionId = userSession.getSessionId();
		if (StringUtil.isNullOrBlank(sessionId)) {
			throw new DataException("Session ID is null");
		}
		Date sessionStartTime = userSession.getSessionStartTime();
		if (sessionStartTime == null) {
			throw new DataException("Session start time is not provided");
		}
		String browserName = userSession.getBrowserName();
		if (StringUtil.isNullOrBlank(browserName)) {
			throw new DataException("Browser Information is not provided");
		}
		String device = userSession.getDevice();
		if (StringUtil.isNullOrBlank(device)) {
			throw new DataException("Device Information is not provided");
		}
		String ipAddress = userSession.getIpAddress();
		if (StringUtil.isNullOrBlank(ipAddress)) {
			throw new DataException("IP Address is not provided");
		}
		UserSessionDataAssembler.updateInformation(userSession);
	}

}
