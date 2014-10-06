package com.myschool.user.domain;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.common.exception.DaoException;
import com.myschool.common.exception.DataException;
import com.myschool.user.dao.UserTypeDaoImpl;
import com.myschool.user.dto.UserTypeDto;
import com.myschool.user.dto.UsersDto;

/**
 * The Class UserTypeManager.
 */
@Component
public class UserTypeManager {

    /** The user type dao. */
    @Autowired
    private UserTypeDaoImpl userTypeDao;

    /**
     * Gets the all.
     *
     * @return the all
     * @throws DataException the data exception
     */
    public List<UserTypeDto> getAll() throws DataException {
        List<UserTypeDto> userTypes = null;
        try {
            userTypes = userTypeDao.getAll();
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return userTypes;
    }

    /**
     * Gets the users.
     *
     * @param userTypeId the user type id
     * @return the users
     * @throws DataException the data exception
     */
    public List<UsersDto> getUsers(int userTypeId) throws DataException {
        List<UsersDto> users = null;
        try {
            users = userTypeDao.getUsers(userTypeId);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return users;
    }

}
