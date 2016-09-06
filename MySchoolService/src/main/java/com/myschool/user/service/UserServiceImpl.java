package com.myschool.user.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myschool.application.dto.DateValueDto;
import com.myschool.common.exception.DataException;
import com.myschool.common.exception.ServiceException;
import com.myschool.employee.dto.EmployeeDto;
import com.myschool.graph.constant.ToDateType;
import com.myschool.student.dto.StudentDto;
import com.myschool.user.constants.UserType;
import com.myschool.user.domain.UserManager;
import com.myschool.user.dto.ChangePasswordDto;
import com.myschool.user.dto.UserActivity;
import com.myschool.user.dto.UserPreference;
import com.myschool.user.dto.UserSession;
import com.myschool.user.dto.UserTheme;
import com.myschool.user.dto.UsersDto;

/**
 * The Class UserServiceImpl.
 */
@Service
public class UserServiceImpl implements UserService {

    /** The user manager. */
    @Autowired
    private UserManager userManager;

    /* (non-Javadoc)
     * @see com.myschool.application.service.Servicable#create(java.lang.Object)
     */
    @Override
    public boolean create(UsersDto usersDto) throws ServiceException {
        return false;
    }

    /* (non-Javadoc)
     * @see com.myschool.user.service.UserService#create(com.myschool.student.dto.StudentDto)
     */
    public int create(StudentDto student) throws ServiceException {
        try {
            return userManager.createUser(student);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(),
                    dataException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.user.service.UserService#create(com.myschool.employee.dto.EmployeeDto)
     */
    @Override
    public int create(EmployeeDto employee) throws ServiceException {
        try {
            return userManager.createUser(employee);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(),
                    dataException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.application.service.Servicable#delete(int)
     */
    @Override
    public boolean delete(int userId) throws ServiceException {
        return false;
    }

    /* (non-Javadoc)
     * @see com.myschool.application.service.Servicable#get(int)
     */
    @Override
    public UsersDto get(int userId) throws ServiceException {
        return null;
    }

    /* (non-Javadoc)
     * @see com.myschool.application.service.Servicable#getAll()
     */
    @Override
    public List<UsersDto> getAll() throws ServiceException {
        return null;
    }

    /* (non-Javadoc)
     * @see com.myschool.application.service.Servicable#update(int, java.lang.Object)
     */
    @Override
    public boolean update(int userId, UsersDto usersDto)
            throws ServiceException {
        return false;
    }

    /* (non-Javadoc)
     * @see com.myschool.user.service.UserService#restorePassword(com.myschool.student.dto.StudentDto)
     */
    @Override
    public boolean restorePassword(StudentDto student) throws ServiceException {
        boolean passwordRestored = false;
        try {
            passwordRestored = userManager.restorePassword(student);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
        return passwordRestored;
    }

    /* (non-Javadoc)
     * @see com.myschool.user.service.UserService#restorePassword(com.myschool.employee.dto.EmployeeDto)
     */
    @Override
    public boolean restorePassword(EmployeeDto employee) throws ServiceException {
        boolean passwordRestored = false;
        try {
            passwordRestored = userManager.restorePassword(employee);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
        return passwordRestored;
    }

    /* (non-Javadoc)
     * @see com.myschool.user.service.UserService#changePassword(com.myschool.user.dto.ChangePasswordDto)
     */
    @Override
    public boolean changePassword(ChangePasswordDto changePassword) throws ServiceException {
        boolean passwordChanged = false;
        try {
            passwordChanged = userManager.changePassword(changePassword);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
        return passwordChanged;
    }

    /* (non-Javadoc)
     * @see com.myschool.user.service.UserService#changePreferences(com.myschool.user.dto.UserPreference)
     */
    @Override
    public boolean changePreferences(UserPreference userPreference) throws ServiceException {
        boolean preferencesChanged = false;
        try {
            preferencesChanged = userManager.changePreferences(userPreference);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
        return preferencesChanged;
    }

    /* (non-Javadoc)
     * @see com.myschool.user.service.UserService#getLoginsToDate(com.myschool.graph.constant.ToDateType)
     */
    @Override
    public Map<UserType, List<DateValueDto>> getLoginsToDate(ToDateType toDateType)
            throws ServiceException {
        Map<UserType, List<DateValueDto>> map = null;
        try {
            map = userManager.getLoginsToDate(toDateType);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
        return map;
    }

	/* (non-Javadoc)
	 * @see com.myschool.user.service.UserService#create(com.myschool.user.dto.UserSession)
	 */
	@Override
	public int create(UserSession userSession) throws ServiceException {
        try {
            return userManager.create(userSession);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(),
                    dataException);
        }
    }

	/* (non-Javadoc)
	 * @see com.myschool.user.service.UserService#update(com.myschool.user.dto.UserSession)
	 */
	@Override
	public boolean update(UserSession userSession) throws ServiceException {
        try {
            return userManager.update(userSession);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(),
                    dataException);
        }
    }

	/* (non-Javadoc)
	 * @see com.myschool.user.service.UserService#createUserActivities(java.lang.String, java.util.List)
	 */
	@Override
	public boolean createUserActivities(String sessionId,
			List<UserActivity> userActivities) throws ServiceException {
		try {
            return userManager.createUserActivities(sessionId, userActivities);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(),
                    dataException);
        }
	}

    /* (non-Javadoc)
     * @see com.myschool.user.service.UserService#getThemes()
     */
    @Override
    public List<UserTheme> getThemes() throws ServiceException {
        try {
            return userManager.getThemes();
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(),
                    dataException);
        }
    }

}
