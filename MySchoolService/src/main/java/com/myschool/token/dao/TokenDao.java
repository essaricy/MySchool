package com.myschool.token.dao;

import java.util.List;

import com.myschool.common.exception.DaoException;
import com.myschool.token.dto.Token;

/**
 * The Interface TokenDao.
 */
public interface TokenDao {

    /**
     * Gets the all.
     *
     * @return the all
     * @throws DaoException the dao exception
     */
    List<Token> getAll() throws DaoException;

    /**
     * Gets the.
     *
     * @param tokenId the token id
     * @return the token
     * @throws DaoException the dao exception
     */
    Token get(String tokenId) throws DaoException;

    /**
     * Creates the.
     *
     * @param token the token
     * @return true, if successful
     * @throws DaoException the dao exception
     */
    boolean create(Token token) throws DaoException;

    /**
     * Dispose.
     *
     * @param tokenId the token id
     * @return true, if successful
     * @throws DaoException the dao exception
     */
    boolean dispose(String tokenId) throws DaoException;

}
