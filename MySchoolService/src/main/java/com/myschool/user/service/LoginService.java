package com.myschool.user.service;

import com.myschool.common.exception.ServiceException;
import com.myschool.user.dto.LoginDto;

/**
 * The Interface LoginService.
 */
public interface LoginService {

    /**
     * Login.
     *
     * @param login the login
     * @return the login dto
     * @throws ServiceException the service exception
     */
    LoginDto login(LoginDto login) throws ServiceException;

}
