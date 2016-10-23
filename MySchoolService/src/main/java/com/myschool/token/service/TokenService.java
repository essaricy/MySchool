package com.myschool.token.service;

import com.myschool.application.service.Servicable;
import com.myschool.common.exception.ServiceException;
import com.myschool.token.dto.Token;

/**
 * The Interface TokenService.
 */
public interface TokenService extends Servicable<Token> {

    /**
     * Gets the.
     *
     * @param tokenId the token id
     * @return the token
     * @throws ServiceException the service exception
     */
    Token get(String tokenId) throws ServiceException;

    /**
     * Dispose.
     *
     * @param tokenId the token id
     * @return true, if successful
     * @throws ServiceException the service exception
     */
    boolean dispose(String tokenId) throws ServiceException;

}
