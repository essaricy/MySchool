package com.myschool.token.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myschool.common.exception.ServiceException;
import com.myschool.token.domain.TokenManager;
import com.myschool.token.dto.Token;
import com.quasar.core.exception.DataException;

/**
 * The Class TokenServiceImpl.
 */
@Service
public class TokenServiceImpl implements TokenService {

    /** The token manager. */
    @Autowired
    private TokenManager tokenManager;

    /* (non-Javadoc)
     * @see com.myschool.application.service.Servicable#create(java.lang.Object)
     */
    @Override
    public boolean create(Token token) throws ServiceException {
        try {
            return tokenManager.create(token);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
    }

    /*
     * (non-Javadoc)
     * @see com.myschool.service.interfaces.Servicable#delete(int)
     */
    @Override
    public boolean delete(int tokenId) throws ServiceException {
        return false;
    }

    /*
     * (non-Javadoc)
     * @see com.myschool.service.interfaces.Servicable#get(int)
     */
    @Override
    public Token get(int tokenId) throws ServiceException {
        return null;
    }

    /* (non-Javadoc)
     * @see com.myschool.token.service.TokenService#get(java.lang.String)
     */
    @Override
    public Token get(String tokenId) throws ServiceException {
        Token token = null;
        try {
            token = tokenManager.get(tokenId);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
        return token;
    }

    /*
     * (non-Javadoc)
     * @see com.myschool.service.interfaces.Servicable#getAll()
     */
    @Override
    public List<Token> getAll() throws ServiceException {
        List<Token> tokens = null;
        try {
            tokens = tokenManager.getAll();
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(),
                    dataException);
        }
        return tokens;
    }

    /*
     * (non-Javadoc)
     * @see com.myschool.service.interfaces.Servicable#update(int,
     * java.lang.Object)
     */
    @Override
    public boolean update(int tokenId, Token token)
            throws ServiceException {
        return false;
    }

    /* (non-Javadoc)
     * @see com.myschool.token.service.TokenService#dispose(java.lang.String)
     */
    @Override
    public boolean dispose(String tokenId) throws ServiceException {
        boolean disposed = false;
        try {
            disposed = tokenManager.dispose(tokenId);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
        return disposed;
    }

}
