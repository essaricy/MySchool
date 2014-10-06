package com.myschool.clazz.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.myschool.clazz.assembler.RegisteredClassDataAssembler;
import com.myschool.clazz.dto.RegisteredClassDto;
import com.myschool.common.exception.ConnectionException;
import com.myschool.common.exception.DaoException;
import com.myschool.infra.database.agent.DatabaseAgent;

/**
 * The Class RegisteredClassDaoImpl.
 */
@Repository
public class RegisteredClassDaoImpl implements RegisteredClassDao {

    /** The database agent. */
    @Autowired
    private DatabaseAgent databaseAgent;

    /* (non-Javadoc)
     * @see com.myschool.clazz.dao.RegisteredClassDao#getBySchool(int)
     */
    public List<RegisteredClassDto> getBySchool(int schoolId) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        RegisteredClassDto classDto = null;
        List<RegisteredClassDto> classes = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(RegisteredClassDaoSql.SELECT_BY_SCHOOL_ID);
            preparedStatement.setInt(1, schoolId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                classDto = RegisteredClassDataAssembler.create(resultSet);
                if (classes == null) {
                    classes = new ArrayList<RegisteredClassDto>();
                }
                classes.add(classDto);
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
        return classes;
    }

    /* (non-Javadoc)
     * @see com.myschool.clazz.dao.RegisteredClassDao#createBySchool(int, com.myschool.clazz.dto.RegisteredClassDto)
     */
    public boolean createBySchool(int schoolId, RegisteredClassDto updatedClass) throws DaoException {
        boolean created = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(RegisteredClassDaoSql.INSERT);
            preparedStatement.setInt(1, databaseAgent.getNextId("CLASS", "CLASS_ID"));
            preparedStatement.setInt(2, schoolId);
            preparedStatement.setInt(3, updatedClass.getClassDto().getClassId());
            preparedStatement.setInt(4, updatedClass.getMedium().getMediumId());
            preparedStatement.setInt(5, updatedClass.getSection().getSectionId());
            created = preparedStatement.executeUpdate() > 0;
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
        return created;
    }

    /* (non-Javadoc)
     * @see com.myschool.clazz.dao.RegisteredClassDao#update(int, com.myschool.clazz.dto.RegisteredClassDto)
     */
    public boolean update(int registeredClassId, RegisteredClassDto updatedClass)
            throws DaoException {
        boolean updated = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(RegisteredClassDaoSql.UPDATE);
            preparedStatement.setInt(1, updatedClass.getClassDto().getClassId());
            preparedStatement.setInt(2, updatedClass.getMedium().getMediumId());
            preparedStatement.setInt(3, updatedClass.getSection().getSectionId());
            preparedStatement.setInt(4, registeredClassId);
            updated = (preparedStatement.executeUpdate() > 0) ? true : false;
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
        return updated;
    }

    /* (non-Javadoc)
     * @see com.myschool.clazz.dao.RegisteredClassDao#delete(int)
     */
    public boolean delete(int registeredClassId) throws DaoException {
        boolean deleted = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(RegisteredClassDaoSql.DELETE);
            preparedStatement.setInt(1, registeredClassId);
            deleted = (preparedStatement.executeUpdate() > 0) ? true : false;
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
        return deleted;
    }

    /* (non-Javadoc)
     * @see com.myschool.clazz.dao.RegisteredClassDao#getAll()
     */
    public List<RegisteredClassDto> getAll() throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        RegisteredClassDto registeredClass = null;
        List<RegisteredClassDto> registeredClasses = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(RegisteredClassDaoSql.SELECT_ALL);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                registeredClass = RegisteredClassDataAssembler.create(resultSet);
                if (registeredClasses == null) {
                    registeredClasses = new ArrayList<RegisteredClassDto>();
                }
                registeredClasses.add(registeredClass);
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
        return registeredClasses;
    }

    /* (non-Javadoc)
     * @see com.myschool.clazz.dao.RegisteredClassDao#get(int)
     */
    @Override
    public RegisteredClassDto get(int registeredClassId) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        RegisteredClassDto operatingClass = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(RegisteredClassDaoSql.SELECT_BY_ID);
            preparedStatement.setInt(1, registeredClassId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                operatingClass = RegisteredClassDataAssembler.create(resultSet);
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
        return operatingClass;
    }

    /* (non-Javadoc)
     * @see com.myschool.clazz.dao.RegisteredClassDao#getByStudent(java.lang.String)
     */
    @Override
    public RegisteredClassDto getByStudent(String admissionNumber) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        RegisteredClassDto operatingClass = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(RegisteredClassDaoSql.SELECT_BY_ADMISSION_NUMBER);
            preparedStatement.setString(1, admissionNumber);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                operatingClass = RegisteredClassDataAssembler.create(resultSet);
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
        return operatingClass;
    }

    /* (non-Javadoc)
     * @see com.myschool.clazz.dao.RegisteredClassDao#get(com.myschool.clazz.dto.RegisteredClassDto)
     */
    public RegisteredClassDto get(RegisteredClassDto registeredClass) throws DaoException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        RegisteredClassDto gotRegisteredClass = null;

        try {
            connection = databaseAgent.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(RegisteredClassDaoSql.getSearchRegisteredClassesQuery(registeredClass));
            if (resultSet.next()) {
                gotRegisteredClass = RegisteredClassDataAssembler.create(resultSet);
            }
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
        return gotRegisteredClass;
    }

}
