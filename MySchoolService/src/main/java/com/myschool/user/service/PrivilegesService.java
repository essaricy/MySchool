package com.myschool.user.service;

import java.util.List;

import com.myschool.application.dto.FunctionDto;
import com.myschool.common.exception.ServiceException;
import com.myschool.user.dto.ModuleAccessDto;

/**
 * The Interface PrivilegesService.
 */
public interface PrivilegesService {

    /**
     * Gets the default privileges.
     *
     * @param userTypeId the user type id
     * @return the default privileges
     * @throws ServiceException the service exception
     */
    public List<ModuleAccessDto> getDefaultPrivileges(int userTypeId) throws ServiceException;

    /**
     * Gets the all functions.
     *
     * @return the all functions
     * @throws ServiceException the service exception
     */
    public List<FunctionDto> getAllFunctions() throws ServiceException;

    /**
     * Save default privileges.
     *
     * @param userTypeId the user type id
     * @param moduleAccessList the module access list
     * @return true, if successful
     * @throws ServiceException the service exception
     */
    public boolean saveDefaultPrivileges(int userTypeId,
            List<ModuleAccessDto> moduleAccessList) throws ServiceException;

    /**
     * Gets the user privileges.
     * 
     * @param userId the user id
     * @param userTypeId the user type id
     * @return the user privileges
     * @throws ServiceException the service exception
     */
    public List<ModuleAccessDto> getUserPrivileges(int userId, int userTypeId) throws ServiceException;

    /**
     * Save user privileges.
     *
     * @param userId the user id
     * @param moduleAccessList the module access list
     * @return true, if successful
     * @throws ServiceException the service exception
     */
    public boolean saveUserPrivileges(int userId,
            List<ModuleAccessDto> moduleAccessList) throws ServiceException;

    /**
     * Delete user privileges.
     *
     * @param userId the user id
     * @throws ServiceException the service exception
     */
    public void deleteUserPrivileges(int userId) throws ServiceException;

    /**
     * Copy user privileges.
     * 
     * @param copyFrom the copy from
     * @param copyTo the copy to
     * @throws ServiceException the service exception
     */
    public void copyUserPrivileges(Integer copyFrom, List<Integer> copyTo) throws ServiceException;

}
