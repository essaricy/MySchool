package com.myschool.token.domain;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.common.exception.DaoException;
import com.myschool.infra.data.agent.DataGeneratorAgent;
import com.myschool.token.dao.TokenDao;
import com.myschool.token.dto.Token;
import com.quasar.core.exception.DataException;
import com.quasar.core.util.StringUtil;

/**
 * The Class TokenManager.
 */
@Component
public class TokenManager {

    @Autowired
    private DataGeneratorAgent dataGeneratorAgent;

    /** The token dao. */
    @Autowired
    private TokenDao tokenDao;

    /**
     * Gets the all.
     *
     * @return the all
     * @throws DataException the data exception
     */
    public List<Token> getAll() throws DataException {
        List<Token> tokens = null;
        try {
            tokens = tokenDao.getAll();
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return tokens;
    }

    /**
     * Gets the.
     *
     * @param tokenId the token id
     * @return the token
     * @throws DataException the data exception
     */
    public Token get(String tokenId) throws DataException {
        Token token = null;
        try {
            token = tokenDao.get(tokenId);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return token;
    }

    /**
     * Creates the.
     *
     * @param token the token
     * @return true, if successful
     * @throws DataException the data exception
     */
    public boolean create(Token token) throws DataException {
        try {
            String consumedBy = token.getConsumedBy();
            System.out.println("consumedBy=" + consumedBy);
            if (StringUtil.isNullOrBlank(consumedBy)) {
                throw new DataException("Requested by email id is required to generate token");
            }
            // TODO: Check if this email id is associated to an account or not.
            String necessity = token.getNecessity();
            System.out.println("necessity=" + necessity);
            if (StringUtil.isNullOrBlank(necessity)) {
                throw new DataException("Reason for generating the token must be specified");
            }
            token.setDisposedOn(null);
            token.setTokenId(dataGeneratorAgent.getUniqueId());
            //token.setValidity(validity);
            return tokenDao.create(token);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
    }

    /**
     * Dispose.
     *
     * @param tokenId the token id
     * @return true, if successful
     * @throws DataException the data exception
     */
    public boolean dispose(String tokenId) throws DataException {
        try {
            if (StringUtil.isNullOrBlank(tokenId)) {
                throw new DataException("invalid token");
            }
            return tokenDao.dispose(tokenId);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
    }

}
