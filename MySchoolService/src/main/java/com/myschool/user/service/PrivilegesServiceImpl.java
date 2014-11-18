package com.myschool.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myschool.application.dto.FunctionDto;
import com.myschool.common.exception.DataException;
import com.myschool.common.exception.ServiceException;
import com.myschool.user.domain.PrivilegesManager;
import com.myschool.user.dto.ModuleAccessDto;

/**
 * The Class PrivilegesServiceImpl.
 */
@Service
public class PrivilegesServiceImpl implements PrivilegesService {

    /** The privileges manager. */
    @Autowired
    private PrivilegesManager privilegesManager;

    /* (non-Javadoc)
     * @see com.myschool.service.interfaces.PrivilegesService#getDefaultPrivileges(int)
     */
    @Override
    public List<ModuleAccessDto> getDefaultPrivileges(int userTypeId) throws ServiceException {
        List<ModuleAccessDto> defaultPrivileges = null;
        try {
            defaultPrivileges = privilegesManager.getDefaultPrivileges(userTypeId);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(),
                    dataException);
        }
        return defaultPrivileges;
    }

    /* (non-Javadoc)
     * @see com.myschool.service.interfaces.PrivilegesService#getAllFunctions()
     */
    @Override
    public List<FunctionDto> getAllFunctions() throws ServiceException {
        List<FunctionDto> allFunctions = null;
        try {
            allFunctions = privilegesManager.getAllFunctions();
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(),
                    dataException);
        }
        return allFunctions;
    }

    /* (non-Javadoc)
     * @see com.myschool.service.interfaces.PrivilegesService#saveDefaultPrivileges(int, java.util.List)
     */
    @Override
    public boolean saveDefaultPrivileges(int userTypeId,
            List<ModuleAccessDto> moduleAccessList) throws ServiceException {
        boolean status = false;
        try {
            status = privilegesManager.saveDefaultPrivileges(userTypeId, moduleAccessList);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(),
                    dataException);
        }
        return status;
    }

    /* (non-Javadoc)
     * @see com.myschool.user.service.PrivilegesService#getUserPrivileges(int, int)
     */
    @Override
    public List<ModuleAccessDto> getUserPrivileges(int userId, int userTypeId)
            throws ServiceException {
        List<ModuleAccessDto> userPrivileges = null;
        try {
            userPrivileges = privilegesManager.getUserPrivileges(userId, userTypeId);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(),
                    dataException);
        }
        return userPrivileges;
    }

    /* (non-Javadoc)
     * @see com.myschool.service.interfaces.PrivilegesService#saveUserPrivileges(int, java.util.List)
     */
    @Override
    public boolean saveUserPrivileges(int userId,
            List<ModuleAccessDto> moduleAccessList) throws ServiceException {
        boolean status = false;
        try {
            status = privilegesManager.saveUserPrivileges(userId, moduleAccessList);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(),
                    dataException);
        }
        return status;
    }

    /* (non-Javadoc)
     * @see com.myschool.service.interfaces.PrivilegesService#deleteUserPrivileges(int)
     */
    @Override
    public void deleteUserPrivileges(int userId) throws ServiceException {
        try {
            privilegesManager.deleteUserPrivileges(userId);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(),
                    dataException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.user.service.PrivilegesService#copyUserPrivileges(java.lang.Integer, java.util.List)
     */
    @Override
    public void copyUserPrivileges(Integer copyFromUserId, List<Integer> copyToUserIds) throws ServiceException {
        try {
            privilegesManager.copyUserPrivileges(copyFromUserId, copyToUserIds);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(),
                    dataException);
        }
    }

}
