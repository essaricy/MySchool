package com.myschool.user.dao;

import java.util.List;

import com.myschool.application.dto.DateValueDto;
import com.myschool.common.exception.DaoException;
import com.myschool.graph.constant.ToDateType;
import com.myschool.user.constants.UserType;
import com.myschool.user.dto.UserPreference;
import com.myschool.user.dto.UserStatistics;
import com.myschool.user.dto.UsersDto;

/**
 * The Interface UserDao.
 */
public interface UserDao {

    /**
     * Creates the user.
     *
     * @param user the user
     * @return the int
     * @throws DaoException the dao exception
     */
    int createUser(UsersDto user) throws DaoException;

    /**
     * Restore password.
     *
     * @param userType the user type
     * @param refUserId the ref user id
     * @param initialPassword the initial password
     * @return true, if successful
     * @throws DaoException the dao exception
     */
    boolean restorePassword(UserType userType, int refUserId,
            String initialPassword) throws DaoException;

    /**
     * Update user statistics.
     *
     * @param userId the user id
     * @throws DaoException the dao exception
     */
    void updateUserStatistics(int userId) throws DaoException;

    /**
     * Gets the user preferences.
     *
     * @param userId the user id
     * @return the user preferences
     * @throws DaoException the dao exception
     */
    UserPreference getUserPreferences(int userId) throws DaoException;

    /**
     * Gets the user statistics.
     *
     * @param userId the user id
     * @return the user statistics
     * @throws DaoException the dao exception
     */
    UserStatistics getUserStatistics(int userId) throws DaoException;

    /**
     * Gets the user.
     *
     * @param userId the user id
     * @return the user
     * @throws DaoException the dao exception
     */
    UsersDto getUser(int userId) throws DaoException;

    /**
     * Change password.
     *
     * @param userId the user id
     * @param newPassword the new password
     * @return true, if successful
     * @throws DaoException the dao exception
     */
    boolean changePassword(int userId, String newPassword) throws DaoException;

    /**
     * Change preferences.
     *
     * @param userPreference the user preference
     * @return true, if successful
     * @throws DaoException the dao exception
     */
    boolean changePreferences(UserPreference userPreference) throws DaoException;

    /**
     * Gets the user.
     * 
     * @param userType the user type
     * @param employeeId the employee id
     * @return the user
     * @throws DaoException the dao exception
     */
    UsersDto getUser(UserType userType, int employeeId) throws DaoException;

    /**
     * Gets the logins to date.
     * 
     * @param userType the user type
     * @param toDateType the to date type
     * @return the logins to date
     * @throws DaoException the dao exception
     */
    List<DateValueDto> getLoginsToDate(UserType userType, ToDateType toDateType) throws DaoException;

}
