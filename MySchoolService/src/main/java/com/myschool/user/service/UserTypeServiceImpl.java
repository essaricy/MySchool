package com.myschool.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myschool.common.exception.DataException;
import com.myschool.common.exception.ServiceException;
import com.myschool.user.domain.UserTypeManager;
import com.myschool.user.dto.UserTypeDto;
import com.myschool.user.dto.UsersDto;

/**
 * The Class UserTypeServiceImpl.
 */
@Service
public class UserTypeServiceImpl implements UserTypeService {

    /** The user type manager. */
    @Autowired
    private UserTypeManager userTypeManager;

    /* (non-Javadoc)
     * @see com.myschool.service.interfaces.Servicable#create(java.lang.Object)
     */
    @Override
    public boolean create(UserTypeDto dto) throws ServiceException {
        return false;
    }

    /* (non-Javadoc)
     * @see com.myschool.service.interfaces.Servicable#delete(int)
     */
    @Override
    public boolean delete(int id) throws ServiceException {
        return false;
    }

    /* (non-Javadoc)
     * @see com.myschool.service.interfaces.Servicable#get(int)
     */
    @Override
    public UserTypeDto get(int id) throws ServiceException {
        return null;
    }

    /* (non-Javadoc)
     * @see com.myschool.service.interfaces.Servicable#getAll()
     */
    @Override
    public List<UserTypeDto> getAll() throws ServiceException {
        List<UserTypeDto> userTypes = null;
        try {
            userTypes = userTypeManager.getAll();
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(),
                    dataException);
        }
        return userTypes;
    }

    /* (non-Javadoc)
     * @see com.myschool.service.interfaces.Servicable#update(int, java.lang.Object)
     */
    @Override
    public boolean update(int id, UserTypeDto dto) throws ServiceException {
        return false;
    }

    /* (non-Javadoc)
     * @see com.myschool.service.interfaces.UserTypeService#getUsers(int)
     */
    @Override
    public List<UsersDto> getUsers(int userTypeId) throws ServiceException {
        List<UsersDto> users = null;
        try {
            users = userTypeManager.getUsers(userTypeId);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(),
                    dataException);
        }
        return users;
    }

}
