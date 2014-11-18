package com.myschool.user.dao;

import java.util.List;

import com.myschool.application.dto.FunctionDto;
import com.myschool.common.exception.DaoException;
import com.myschool.user.dto.ModuleAccessDto;
import com.myschool.user.dto.UserAccessDto;

/**
 * The Interface PrivilegesDao.
 */
public interface PrivilegesDao {

    /**
     * Gets the default privileges.
     *
     * @param userTypeId the user type id
     * @return the default privileges
     * @throws DaoException the dao exception
     */
    List<UserAccessDto> getDefaultPrivileges(int userTypeId)
            throws DaoException;

    /**
     * Gets the all functions.
     *
     * @return the all functions
     * @throws DaoException the dao exception
     */
    List<FunctionDto> getAllFunctions() throws DaoException;

    /**
     * Save default privileges.
     *
     * @param userTypeId the user type id
     * @param moduleAccessList the module access list
     * @return true, if successful
     * @throws DaoException the dao exception
     */
    boolean saveDefaultPrivileges(int userTypeId,
            List<ModuleAccessDto> moduleAccessList) throws DaoException;

    /**
     * Delete default privileges.
     *
     * @param userTypeId the user type id
     * @throws DaoException the dao exception
     */
    void deleteDefaultPrivileges(int userTypeId) throws DaoException;

    /**
     * Gets the user privileges.
     *
     * @param userId the user id
     * @return the user privileges
     * @throws DaoException the dao exception
     */
    List<UserAccessDto> getUserPrivileges(int userId) throws DaoException;

    /**
     * Delete user privileges.
     *
     * @param userId the user id
     * @throws DaoException the dao exception
     */
    void deleteUserPrivileges(int userId) throws DaoException;

    /**
     * Save user privileges.
     *
     * @param userId the user id
     * @param moduleAccessList the module access list
     * @return true, if successful
     * @throws DaoException the dao exception
     */
    boolean saveUserPrivileges(int userId,
            List<ModuleAccessDto> moduleAccessList) throws DaoException;

    /**
     * Copy user privileges.
     * 
     * @param copyFromUserId the copy from user id
     * @param copyToUserIds the copy to user ids
     * @throws DaoException the dao exception
     */
    void copyUserPrivileges(Integer copyFromUserId, List<Integer> copyToUserIds) throws DaoException;

}
