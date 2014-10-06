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

import com.myschool.clazz.assembler.SubjectDataAssembler;
import com.myschool.clazz.dto.SubjectDto;
import com.myschool.common.exception.ConnectionException;
import com.myschool.common.exception.DaoException;
import com.myschool.infra.database.agent.DatabaseAgent;

/**
 * The Class SubjectDaoImpl.
 */
@Repository
public class SubjectDaoImpl implements SubjectDao {

    /** The database agent. */
    @Autowired
    private DatabaseAgent databaseAgent;

    /* (non-Javadoc)
     * @see com.myschool.clazz.dao.SubjectDao#createSubject(com.myschool.clazz.dto.SubjectDto)
     */
    @Override
    public int createSubject(SubjectDto subjectDto) throws DaoException {
        int nextId = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            String insertSubjectSql = SubjectDaoSql.getInsertSubjectSql();
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(insertSubjectSql);
            nextId = databaseAgent.getNextId("REF_SUBJECT", "SUBJECT_ID");
            preparedStatement.setInt(1, nextId);
            preparedStatement.setString(2, subjectDto.getSubjectName());
            if (preparedStatement.executeUpdate() == 0) {
                nextId = 0;
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
        return nextId;
    }

    /* (non-Javadoc)
     * @see com.myschool.clazz.dao.SubjectDao#deleteSubject(int)
     */
    @Override
    public boolean deleteSubject(int subjectId) throws DaoException {
        boolean deleted = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = databaseAgent.getConnection();
            String query = SubjectDaoSql.getDeleteSubjectQuery();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, subjectId);
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
     * @see com.myschool.clazz.dao.SubjectDao#get(int)
     */
    @Override
    public SubjectDto get(int subjectId) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        SubjectDto subject = null;

        try {
            connection = databaseAgent.getConnection();
            String query =SubjectDaoSql.getSubjectsQuery(true);
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, subjectId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
            	subject = SubjectDataAssembler.createSubject(resultSet, false);
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
        return subject;
    }

    /* (non-Javadoc)
     * @see com.myschool.clazz.dao.SubjectDao#getAll()
     */
    @Override
    public List<SubjectDto> getAll() throws DaoException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<SubjectDto> subjects = null;
        SubjectDto subject = null;

        try {
            connection = databaseAgent.getConnection();
            String query = SubjectDaoSql.getSubjectsQuery(false);
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
            	subject = SubjectDataAssembler.createSubject(resultSet, false);
                if (subjects == null) {
                	subjects = new ArrayList<SubjectDto>();
                }
                subjects.add(subject);
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
        return subjects;
    }

    /* (non-Javadoc)
     * @see com.myschool.clazz.dao.SubjectDao#updateSubject(int, com.myschool.clazz.dto.SubjectDto)
     */
    @Override
    public boolean updateSubject(int subjectId, SubjectDto subjectDto)
            throws DaoException {
        boolean updated = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = databaseAgent.getConnection();
            String query = SubjectDaoSql.getUpdateSubjectQuery();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, subjectDto.getSubjectName());
            preparedStatement.setInt(2, subjectId);
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
     * @see com.myschool.clazz.dao.SubjectDao#get(java.lang.String)
     */
    @Override
    public SubjectDto get(String subjectName) throws DaoException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        SubjectDto subject = null;

        try {
            String selectSubjectSql = SubjectDaoSql.getSelectSubjectSql(subjectName);
            connection = databaseAgent.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(selectSubjectSql);
            if (resultSet.next()) {
                subject = SubjectDataAssembler.createSubject(resultSet, false);
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
        return subject;
    }

    /* (non-Javadoc)
     * @see com.myschool.clazz.dao.SubjectDao#create(java.lang.String)
     */
    @Override
    public int create(String subjectName) throws DaoException {
        SubjectDto subject = new SubjectDto();
        subject.setSubjectName(subjectName);
        return createSubject(subject);
    }

}
