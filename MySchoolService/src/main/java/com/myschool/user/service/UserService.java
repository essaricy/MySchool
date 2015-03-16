package com.myschool.user.service;

import java.util.List;
import java.util.Map;

import com.myschool.application.dto.DateValueDto;
import com.myschool.application.service.Servicable;
import com.myschool.common.exception.ServiceException;
import com.myschool.employee.dto.EmployeeDto;
import com.myschool.graph.constant.ToDateType;
import com.myschool.student.dto.StudentDto;
import com.myschool.user.constants.UserType;
import com.myschool.user.dto.ChangePasswordDto;
import com.myschool.user.dto.UserActivity;
import com.myschool.user.dto.UserPreference;
import com.myschool.user.dto.UserSession;
import com.myschool.user.dto.UsersDto;

/**
 * The Interface UserService.
 */
public interface UserService extends Servicable<UsersDto> {

    /**
     * Creates the user.
     *
     * @param student the existing student
     * @return the int
     * @throws ServiceException the service exception
     */
    int create(StudentDto student) throws ServiceException;

    /**
     * Creates the user.
     *
     * @param employee the employee
     * @return the int
     * @throws ServiceException the service exception
     */
    int create(EmployeeDto employee) throws ServiceException;

    /**
     * Restore password.
     *
     * @param student the student
     * @return true, if successful
     * @throws ServiceException the service exception
     */
    boolean restorePassword(StudentDto student) throws ServiceException;

    /**
     * Restore password.
     *
     * @param employee the employee
     * @return true, if successful
     * @throws ServiceException the service exception
     */
    boolean restorePassword(EmployeeDto employee) throws ServiceException;

    /**
     * Change password.
     *
     * @param changePassword the change password
     * @return true, if successful
     * @throws ServiceException the service exception
     */
    boolean changePassword(ChangePasswordDto changePassword) throws ServiceException;

    /**
     * Change preferences.
     *
     * @param userPreference the user preference
     * @return true, if successful
     * @throws ServiceException the service exception
     */
    boolean changePreferences(UserPreference userPreference) throws ServiceException;

    /**
     * Gets the logins to date.
     * 
     * @param toDateType the to date type
     * @return the logins to date
     * @throws ServiceException the service exception
     */
    Map<UserType, List<DateValueDto>> getLoginsToDate(ToDateType toDateType) throws ServiceException;

    /**
     * Creates the.
     *
     * @param userSession the user session
     * @return the int
     * @throws ServiceException the service exception
     */
    int create(UserSession userSession) throws ServiceException;

    /**
     * Update.
     *
     * @param userSession the user session
     * @return true, if successful
     * @throws ServiceException the service exception
     */
    boolean update(UserSession userSession) throws ServiceException;

    /**
     * Creates the user activities.
     *
     * @param sessionId the session id
     * @param userActivities the user activities
     * @return true, if successful
     * @throws ServiceException the service exception
     */
    boolean createUserActivities(String sessionId, List<UserActivity> userActivities) throws ServiceException;

}
