package com.myschool.user.dao;

import com.myschool.common.exception.DaoException;
import com.myschool.user.dto.LoginDto;

/**
 * The Interface LoginDao.
 */
public interface LoginDao {

    /**
     * Gets the login details.
     *
     * @param login the login
     * @return the login details
     * @throws DaoException the dao exception
     */
    LoginDto getLoginDetails(LoginDto login) throws DaoException;

}
