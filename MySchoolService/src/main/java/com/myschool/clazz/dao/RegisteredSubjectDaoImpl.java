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

import com.myschool.clazz.assembler.RegisteredSubjectDataAssembler;
import com.myschool.clazz.dto.RegisteredSubjectDto;
import com.myschool.clazz.dto.SubjectDto;
import com.myschool.common.exception.ConnectionException;
import com.myschool.common.exception.DaoException;
import com.myschool.infra.database.agent.DatabaseAgent;

/**
 * The Class RegisteredSubjectDaoImpl.
 */
@Repository
public class RegisteredSubjectDaoImpl implements RegisteredSubjectDao {

    /** The database agent. */
    @Autowired
    private DatabaseAgent databaseAgent;

    /* (non-Javadoc)
     * @see com.myschool.clazz.dao.RegisteredSubjectDao#getByClass(int)
     */
    @Override
    public List<RegisteredSubjectDto> getByClass(int classId) throws DaoException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<RegisteredSubjectDto> registeredSubjects = null;
        RegisteredSubjectDto registeredSubject = null;

        try {
            connection = databaseAgent.getConnection();
            String query = RegisteredSubjectDaoSql.getSubjectsByClassQuery();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, classId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                registeredSubject = RegisteredSubjectDataAssembler.createRegisteredSubject(resultSet);
                if (registeredSubjects == null) {
                    registeredSubjects = new ArrayList<RegisteredSubjectDto>();
                }
                registeredSubjects.add(registeredSubject);
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
        return registeredSubjects;
    }

    /* (non-Javadoc)
     * @see com.myschool.clazz.dao.RegisteredSubjectDao#create(int, com.myschool.clazz.dto.RegisteredSubjectDto)
     */
    public boolean create(RegisteredSubjectDto registeredSubject) throws DaoException {
        boolean created = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = databaseAgent.getConnection();
            String query = RegisteredSubjectDaoSql.getInsertRegisteredSubjectQuery();
            preparedStatement = connection.prepareStatement(query);
            SubjectDto subject = registeredSubject.getSubject();
            preparedStatement.setInt(1, databaseAgent.getNextId("SUBJECT", "SUBJECT_ID"));
            preparedStatement.setInt(2, subject.getSubjectId());
            preparedStatement.setInt(3, registeredSubject.getRegisteredClass().getClassId());
            created = (preparedStatement.executeUpdate() > 0) ? true : false;
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
        return created;
    }

    /* (non-Javadoc)
     * @see com.myschool.clazz.dao.RegisteredSubjectDao#deleteRegisteredSubject(int, int)
     */
    public boolean delete(int classId, int subjectId) throws DaoException {
        boolean deleted = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = databaseAgent.getConnection();
            String query = RegisteredSubjectDaoSql.getDeleteRegisteredSubjectByClassQuery(classId, subjectId);
            preparedStatement = connection.prepareStatement(query);
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
     * @see com.myschool.clazz.dao.RegisteredSubjectDao#delete(int)
     */
    @Override
    public boolean delete(int registeredSubjectId) throws DaoException {
        boolean deleted = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = databaseAgent.getConnection();
            String query = RegisteredSubjectDaoSql.getDeleteRegisteredSubjectQuery(registeredSubjectId);
            preparedStatement = connection.prepareStatement(query);
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
     * @see com.myschool.clazz.dao.RegisteredSubjectDao#get(int)
     */
    @Override
    public RegisteredSubjectDto get(int registeredSubjectId) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        RegisteredSubjectDto gotRegisteredSubject = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(RegisteredSubjectDaoSql.SELECT_BY_ID);
            preparedStatement.setInt(1, registeredSubjectId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                gotRegisteredSubject = RegisteredSubjectDataAssembler.create(resultSet);
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
        return gotRegisteredSubject;
    }

    /* (non-Javadoc)
     * @see com.myschool.clazz.dao.RegisteredSubjectDao#update(int, com.myschool.clazz.dto.RegisteredSubjectDto)
     */
    @Override
    public boolean update(int registeredSubjectId,
            RegisteredSubjectDto registeredSubject) throws DaoException {
        boolean updated = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            String updateRegisteredSubjectQuery = RegisteredSubjectDaoSql.getUpdateRegisteredSubjectQuery();
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(updateRegisteredSubjectQuery);
            preparedStatement.setInt(1, registeredSubject.getSubject().getSubjectId());
            preparedStatement.setInt(2, registeredSubject.getRegisteredClass().getClassId());
            preparedStatement.setInt(3, registeredSubjectId);
            updated = (preparedStatement.executeUpdate() > 0) ? true : false;
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
        return updated;
    }

    /* (non-Javadoc)
     * @see com.myschool.clazz.dao.RegisteredSubjectDao#get(int, int)
     */
    @Override
    public RegisteredSubjectDto get(int classId, int subjectId)
            throws DaoException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        RegisteredSubjectDto gotRegisteredSubject = null;

        try {
            String selectRegisteredSubjectSql = RegisteredSubjectDaoSql.getSelectRegisteredSubjectSql(classId, subjectId);
            connection = databaseAgent.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(selectRegisteredSubjectSql);
            if (resultSet.next()) {
                gotRegisteredSubject = RegisteredSubjectDataAssembler.createRegisteredSubject(resultSet);
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
        return gotRegisteredSubject;
    }

}
