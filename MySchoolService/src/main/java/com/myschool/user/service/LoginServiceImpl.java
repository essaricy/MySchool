package com.myschool.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myschool.common.exception.DataException;
import com.myschool.common.exception.ServiceException;
import com.myschool.user.domain.LoginManager;
import com.myschool.user.dto.LoginDto;

/**
 * The Class LoginServiceImpl.
 */
@Service
public class LoginServiceImpl implements LoginService {

    /** The login manager. */
    @Autowired
    private LoginManager loginManager;

    /* (non-Javadoc)
     * @see com.myschool.service.interfaces.LoginService#login(com.myschool.common.dto.LoginDto)
     */
    public LoginDto login(LoginDto login) throws ServiceException {
        LoginDto loginDetails = null;
        try {
            loginDetails = loginManager.login(login);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(),
                    dataException);
        }
        return loginDetails;
    }

}
