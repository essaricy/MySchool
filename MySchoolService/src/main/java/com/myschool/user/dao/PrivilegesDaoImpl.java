package com.myschool.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.myschool.application.dto.FunctionDto;
import com.myschool.common.exception.ConnectionException;
import com.myschool.common.exception.DaoException;
import com.myschool.common.util.ConversionUtil;
import com.myschool.infra.database.agent.DatabaseAgent;
import com.myschool.user.assembler.ModuleDataAssembler;
import com.myschool.user.assembler.UserDataAssembler;
import com.myschool.user.dto.FunctionAccessDto;
import com.myschool.user.dto.ModuleAccessDto;
import com.myschool.user.dto.UserAccessDto;

/**
 * The Class PrivilegesDaoImpl.
 */
@Repository
public class PrivilegesDaoImpl implements PrivilegesDao {

    /** The database agent. */
    @Autowired
    private DatabaseAgent databaseAgent;

    /* (non-Javadoc)
     * @see com.myschool.user.dao.PrivilegesDao#getDefaultPrivileges(int)
     */
    @Override
    public List<UserAccessDto> getDefaultPrivileges(int userTypeId) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        UserAccessDto defaultUserAccess = null;
        List<UserAccessDto> defaultUserAccesses = null;

        try {
            connection = databaseAgent.getConnection();
            String query = PrivilegesDaoSql.buildDefaultPrivilegesSql(true);
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, userTypeId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                defaultUserAccess = UserDataAssembler.createUserAccess(resultSet);
                if (defaultUserAccesses == null) {
                    defaultUserAccesses = new ArrayList<UserAccessDto>();
                }
                defaultUserAccesses.add(defaultUserAccess);
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
        return defaultUserAccesses;
    }

    /* (non-Javadoc)
     * @see com.myschool.user.dao.PrivilegesDao#getAllFunctions()
     */
    @Override
    public List<FunctionDto> getAllFunctions() throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        FunctionDto function = null;
        List<FunctionDto> allFunctions = null;

        try {
            connection = databaseAgent.getConnection();
            String query = PrivilegesDaoSql.buildSelectFunctionsSql();
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                function = ModuleDataAssembler.createFunction(resultSet, true);
                if (allFunctions == null) {
                    allFunctions = new ArrayList<FunctionDto>();
                }
                allFunctions.add(function);
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
        return allFunctions;
    }

    /* (non-Javadoc)
     * @see com.myschool.user.dao.PrivilegesDao#saveDefaultPrivileges(int, java.util.List)
     */
    @Override
    public boolean saveDefaultPrivileges(int userTypeId,
            List<ModuleAccessDto> moduleAccessList) throws DaoException {

        boolean saved = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<FunctionAccessDto> functionAccessList = null;

        try {
            connection = databaseAgent.getConnection();
            String query = PrivilegesDaoSql.buildCreateDefaultPrivilegesSql();
            preparedStatement = connection.prepareStatement(query);

            for (ModuleAccessDto moduleAccess : moduleAccessList) {
                functionAccessList = moduleAccess.getFunctionAccess();
                if (functionAccessList != null) {
                    for (FunctionAccessDto functionAccess : functionAccessList) {
                        if (functionAccess != null) {
                            preparedStatement.setInt(1, userTypeId);
                            preparedStatement.setInt(2, functionAccess.getFunctionId());
                            preparedStatement.setString(3, ConversionUtil.toYN(functionAccess.isView()));
                            preparedStatement.setString(4, ConversionUtil.toYN(functionAccess.isCreate()));
                            preparedStatement.setString(5, ConversionUtil.toYN(functionAccess.isUpdate()));
                            preparedStatement.setString(6, ConversionUtil.toYN(functionAccess.isDelete()));
                            preparedStatement.addBatch();
                        }
                    }
                }
            }
            preparedStatement.executeBatch();
            saved = true;
        } catch (SQLException sqlException) {
            throw new DaoException(sqlException.getMessage(), sqlException);
        } catch (ConnectionException connectionException) {
            throw new DaoException(connectionException.getMessage(),
                    connectionException);
        } finally {
            try {
                databaseAgent.releaseResources(connection, preparedStatement);
            } catch (ConnectionException connectionException) {
                throw new DaoException(connectionException.getMessage(),
                        connectionException);
            }
        }
        return saved;
    }

    /* (non-Javadoc)
     * @see com.myschool.user.dao.PrivilegesDao#deleteDefaultPrivileges(int)
     */
    @Override
    public void deleteDefaultPrivileges(int userTypeId) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = databaseAgent.getConnection();
            String query = PrivilegesDaoSql.buildDeleteDefaultPrivilegesSql();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, userTypeId);
            preparedStatement.executeUpdate();
        } catch (SQLException sqlException) {
            throw new DaoException(sqlException.getMessage(), sqlException);
        } catch (ConnectionException connectionException) {
            throw new DaoException(connectionException.getMessage(),
                    connectionException);
        } finally {
            try {
                databaseAgent.releaseResources(connection, preparedStatement);
            } catch (ConnectionException connectionException) {
                throw new DaoException(connectionException.getMessage(),
                        connectionException);
            }
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.user.dao.PrivilegesDao#getUserPrivileges(int)
     */
    @Override
    public List<UserAccessDto> getUserPrivileges(int userId) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        UserAccessDto defaultUserAccess = null;
        List<UserAccessDto> defaultUserAccesses = null;

        try {
            connection = databaseAgent.getConnection();
            String query = PrivilegesDaoSql.buildUserPrivilegesSql(true);
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, userId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                defaultUserAccess = UserDataAssembler.createUserAccess(resultSet);
                if (defaultUserAccesses == null) {
                    defaultUserAccesses = new ArrayList<UserAccessDto>();
                }
                defaultUserAccesses.add(defaultUserAccess);
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
        return defaultUserAccesses;
    }

    /* (non-Javadoc)
     * @see com.myschool.user.dao.PrivilegesDao#deleteUserPrivileges(int)
     */
    @Override
    public void deleteUserPrivileges(int userId) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = databaseAgent.getConnection();
            String query = PrivilegesDaoSql.buildDeleteUserPrivilegesSql();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, userId);
            preparedStatement.executeUpdate();
        } catch (SQLException sqlException) {
            throw new DaoException(sqlException.getMessage(), sqlException);
        } catch (ConnectionException connectionException) {
            throw new DaoException(connectionException.getMessage(),
                    connectionException);
        } finally {
            try {
                databaseAgent.releaseResources(connection, preparedStatement);
            } catch (ConnectionException connectionException) {
                throw new DaoException(connectionException.getMessage(),
                        connectionException);
            }
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.user.dao.PrivilegesDao#saveUserPrivileges(int, java.util.List)
     */
    @Override
    public boolean saveUserPrivileges(int userId,
            List<ModuleAccessDto> moduleAccessList) throws DaoException {

        boolean saved = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<FunctionAccessDto> functionAccessList = null;

        try {
            connection = databaseAgent.getConnection();
            String query = PrivilegesDaoSql.buildCreateUserPrivilegesSql();
            preparedStatement = connection.prepareStatement(query);

            for (ModuleAccessDto moduleAccess : moduleAccessList) {
                functionAccessList = moduleAccess.getFunctionAccess();
                if (functionAccessList != null) {
                    for (FunctionAccessDto functionAccess : functionAccessList) {
                        if (functionAccess != null) {
                            preparedStatement.setInt(1, userId);
                            preparedStatement.setInt(2, functionAccess.getFunctionId());
                            preparedStatement.setString(3, ConversionUtil.toYN(functionAccess.isView()));
                            preparedStatement.setString(4, ConversionUtil.toYN(functionAccess.isCreate()));
                            preparedStatement.setString(5, ConversionUtil.toYN(functionAccess.isUpdate()));
                            preparedStatement.setString(6, ConversionUtil.toYN(functionAccess.isDelete()));
                            preparedStatement.addBatch();
                        }
                    }
                }
            }
            preparedStatement.executeBatch();
            saved = true;
        } catch (SQLException sqlException) {
            throw new DaoException(sqlException.getMessage(), sqlException);
        } catch (ConnectionException connectionException) {
            throw new DaoException(connectionException.getMessage(),
                    connectionException);
        } finally {
            try {
                databaseAgent.releaseResources(connection, preparedStatement);
            } catch (ConnectionException connectionException) {
                throw new DaoException(connectionException.getMessage(),
                        connectionException);
            }
        }
        return saved;
    }

    /* (non-Javadoc)
     * @see com.myschool.user.dao.PrivilegesDao#copyUserPrivileges(java.lang.Integer, java.util.List)
     */
    @Override
    public void copyUserPrivileges(Integer copyFromUserId,
            List<Integer> copyToUserIds) throws DaoException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = databaseAgent.getConnection();
            String query = PrivilegesDaoSql.getCopyPrivilegesSql(copyFromUserId, copyToUserIds);
            System.out.println("query " + query);
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
        } catch (SQLException sqlException) {
            throw new DaoException(sqlException.getMessage(), sqlException);
        } catch (ConnectionException connectionException) {
            throw new DaoException(connectionException.getMessage(),
                    connectionException);
        } finally {
            try {
                databaseAgent.releaseResources(connection, statement, resultSet);
            } catch (ConnectionException connectionException) {
                throw new DaoException(connectionException.getMessage(),
                        connectionException);
            }
        }
    }

}
