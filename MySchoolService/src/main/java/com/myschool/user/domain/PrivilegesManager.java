package com.myschool.user.domain;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.application.dto.FunctionDto;
import com.myschool.common.exception.DaoException;
import com.myschool.common.exception.DataException;
import com.myschool.user.assembler.ModuleDataAssembler;
import com.myschool.user.dao.PrivilegesDao;
import com.myschool.user.dto.ModuleAccessDto;
import com.myschool.user.dto.UserAccessDto;

/**
 * The Class PrivilegesManager.
 */
@Component
public class PrivilegesManager {

    /** The privileges dao. */
    @Autowired
    private PrivilegesDao privilegesDao;

    /**
     * Gets the default privileges.
     * 
     * @param userTypeId the user type id
     * @return the default privileges
     * @throws DataException the data exception
     */
    public List<ModuleAccessDto> getDefaultPrivileges(int userTypeId) throws DataException {
        List<ModuleAccessDto> privileges = null;
        try {
            List<FunctionDto> allFunctions = getAllFunctions();
            List<UserAccessDto> userAccessList = privilegesDao.getDefaultPrivileges(userTypeId);
            privileges = ModuleDataAssembler.getPrivileges(userAccessList, allFunctions);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return privileges;
    }

    /**
     * Gets the all functions.
     *
     * @return the all functions
     * @throws DataException the data exception
     */
    public List<FunctionDto> getAllFunctions() throws DataException {
        List<FunctionDto> allFunctions = null;
        try {
            allFunctions = privilegesDao.getAllFunctions();
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return allFunctions;
    }

    /**
     * Save default privileges.
     *
     * @param userTypeId the user type id
     * @param moduleAccessList the module access list
     * @return true, if successful
     * @throws DataException the data exception
     */
    public boolean saveDefaultPrivileges(int userTypeId,
            List<ModuleAccessDto> moduleAccessList) throws DataException {
        boolean status = false;
        try {
            privilegesDao.deleteDefaultPrivileges(userTypeId);
            status = privilegesDao.saveDefaultPrivileges(userTypeId, moduleAccessList);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return status;
    }

    /**
     * Gets the user privileges.
     * 
     * @param userId the user id
     * @param userTypeId the user type id
     * @return the user privileges
     * @throws DataException the data exception
     */
    public List<ModuleAccessDto> getUserPrivileges(int userId, int userTypeId) throws DataException {
        List<ModuleAccessDto> userPrivileges = null;
        List<UserAccessDto> userAccessList = null;
        try {
            List<FunctionDto> allFunctions = getAllFunctions();
            userAccessList = privilegesDao.getUserPrivileges(userId);
            if (userAccessList == null) {
                userAccessList = privilegesDao.getDefaultPrivileges(userTypeId);
            }
            userPrivileges = ModuleDataAssembler.getPrivileges(userAccessList, allFunctions);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return userPrivileges;
    }

    /**
     * Save user privileges.
     *
     * @param userId the user id
     * @param moduleAccessList the module access list
     * @return true, if successful
     * @throws DataException the data exception
     */
    public boolean saveUserPrivileges(int userId,
            List<ModuleAccessDto> moduleAccessList) throws DataException {
        boolean status = false;
        try {
            privilegesDao.deleteUserPrivileges(userId);
            status = privilegesDao.saveUserPrivileges(userId, moduleAccessList);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return status;
    }

    /**
     * Delete user privileges.
     *
     * @param userId the user id
     * @throws DataException the data exception
     */
    public void deleteUserPrivileges(int userId) throws DataException {
        try {
            privilegesDao.deleteUserPrivileges(userId);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
    }

    /**
     * Copy user privileges.
     * 
     * @param copyFromUserId the copy from user id
     * @param copyToUserIds the copy to user ids
     * @throws DataException the data exception
     */
    public void copyUserPrivileges(Integer copyFromUserId,
            List<Integer> copyToUserIds) throws DataException {
        try {
            System.out.println("copyFromUserId " + copyFromUserId);
            System.out.println("copyToUserIds " + copyToUserIds);
            if (copyFromUserId == null || copyFromUserId == 0) {
                throw new DataException("'Copy From User ID' is not given.");
            }
            if (copyToUserIds == null || copyToUserIds.isEmpty()) {
                throw new DataException("'Copy To User IDs' are not given.");
            }
            for (Integer copyToUserId : copyToUserIds) {
                if (copyToUserId == null || copyToUserId == 0) {
                    throw new DataException("'Copy To User ID' cannot be null.");
                }
                if (copyToUserId == copyFromUserId) {
                    throw new DataException("'Copy From User ID' and 'Copy To User ID' cannot same.");
                }
            }
            privilegesDao.copyUserPrivileges(copyFromUserId, copyToUserIds);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
    }

}
