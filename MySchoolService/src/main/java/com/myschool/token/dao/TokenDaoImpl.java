package com.myschool.token.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.myschool.common.exception.ConnectionException;
import com.myschool.common.exception.DaoException;
import com.myschool.infra.database.agent.DatabaseAgent;
import com.myschool.token.assembler.TokenDataAssembler;
import com.myschool.token.dto.Token;

/**
 * The Class TokenDaoImpl.
 */
@Repository
public class TokenDaoImpl implements TokenDao {

    /** The database agent. */
    @Autowired
    private DatabaseAgent databaseAgent;

    /* (non-Javadoc)
     * @see com.myschool.token.dao.TokenDao#getAll()
     */
    @Override
    public List<Token> getAll() throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        Token token = null;
        List<Token> tokens = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(TokenDaoSql.SELECT_ALL);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                token = TokenDataAssembler.create(resultSet);
                if (tokens == null) {
                    tokens = new ArrayList<Token>();
                }
                tokens.add(token);
            }
        } catch (SQLException sqlException) {
            throw new DaoException(sqlException.getMessage(), sqlException);
        } catch (ConnectionException connectionException) {
            throw new DaoException(connectionException.getMessage(),
                    connectionException);
        } finally {
            try {
                databaseAgent.releaseResources(connection, preparedStatement, resultSet);
            } catch (ConnectionException connectionException) {
                throw new DaoException(connectionException.getMessage(),
                        connectionException);
            }
        }
        return tokens;
    }

    /* (non-Javadoc)
     * @see com.myschool.token.dao.TokenDao#get(java.lang.String)
     */
    @Override
    public Token get(String tokenId) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        Token token = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(TokenDaoSql.SELECT_BY_ID);
            preparedStatement.setString(1, tokenId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                token = TokenDataAssembler.create(resultSet);
            }
        } catch (SQLException sqlException) {
            throw new DaoException(sqlException.getMessage(), sqlException);
        } catch (ConnectionException connectionException) {
            throw new DaoException(connectionException.getMessage(),
                    connectionException);
        } finally {
            try {
                databaseAgent.releaseResources(connection, preparedStatement, resultSet);
            } catch (ConnectionException connectionException) {
                throw new DaoException(connectionException.getMessage(),
                        connectionException);
            }
        }
        return token;
    }

    /* (non-Javadoc)
     * @see com.myschool.token.dao.TokenDao#create(com.myschool.token.dto.Token)
     */
    @Override
    public boolean create(Token token) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = databaseAgent.getConnection();
            System.out.println("TokenDaoSql.INSERT=" + TokenDaoSql.INSERT);
            preparedStatement = connection.prepareStatement(TokenDaoSql.INSERT);
            preparedStatement.setString(1, token.getTokenId());
            preparedStatement.setString(2, token.getConsumedBy());
            preparedStatement.setString(3, token.getNecessity());
            preparedStatement.setDate(4, new Date(System.currentTimeMillis()));
            //preparedStatement.setString(4, token.getDisposedOn());
            preparedStatement.setInt(5, token.getValidity());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException sqlException) {
            throw new DaoException(sqlException.getMessage(), sqlException);
        } catch (ConnectionException connectionException) {
            throw new DaoException(connectionException.getMessage(),
                    connectionException);
        } finally {
            try {
                databaseAgent.releaseResources(connection, preparedStatement, resultSet);
            } catch (ConnectionException connectionException) {
                throw new DaoException(connectionException.getMessage(),
                        connectionException);
            }
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.token.dao.TokenDao#dispose(java.lang.String)
     */
    @Override
    public boolean dispose(String tokenId) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(TokenDaoSql.DISPOSE);
            preparedStatement.setString(1, tokenId);
            preparedStatement.setDate(2, new Date(System.currentTimeMillis()));
        } catch (SQLException sqlException) {
            throw new DaoException(sqlException.getMessage(), sqlException);
        } catch (ConnectionException connectionException) {
            throw new DaoException(connectionException.getMessage(),
                    connectionException);
        } finally {
            try {
                databaseAgent.releaseResources(connection, preparedStatement, resultSet);
            } catch (ConnectionException connectionException) {
                throw new DaoException(connectionException.getMessage(),
                        connectionException);
            }
        }
        return true;
    }

}
