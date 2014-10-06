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

import com.myschool.clazz.assembler.ClassDataAssembler;
import com.myschool.clazz.dto.ClassDto;
import com.myschool.common.exception.ConnectionException;
import com.myschool.common.exception.DaoException;
import com.myschool.infra.database.agent.DatabaseAgent;

/**
 * The Class ClassDaoImpl.
 */
@Repository
public class ClassDaoImpl implements ClassDao {

    /** The database agent. */
    @Autowired
    private DatabaseAgent databaseAgent;

    /* (non-Javadoc)
     * @see com.myschool.clazz.dao.ClassDao#create(com.myschool.clazz.dto.ClassDto)
     */
    @Override
    public int create(ClassDto classDto) throws DaoException {
        int nextId = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(ClassDaoSql.INSERT);
            nextId = databaseAgent.getNextId("REF_CLASS", "CLASS_ID");
            preparedStatement.setInt(1, nextId);
            preparedStatement.setString(2, classDto.getClassName());
            preparedStatement.setInt(3, classDto.getPromotionOrder());
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
     * @see com.myschool.clazz.dao.ClassDao#delete(int)
     */
    @Override
    public boolean delete(int classId) throws DaoException {
        boolean deleted = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(ClassDaoSql.DELETE);
            preparedStatement.setInt(1, classId);
            deleted = preparedStatement.executeUpdate() > 0;
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
     * @see com.myschool.clazz.dao.ClassDao#get(int)
     */
    @Override
    public ClassDto get(int classId) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        ClassDto classDto = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(ClassDaoSql.SELECT_BY_ID);
            preparedStatement.setInt(1, classId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                classDto = ClassDataAssembler.create(resultSet);
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
        return classDto;
    }

    /* (non-Javadoc)
     * @see com.myschool.clazz.dao.ClassDao#getAll()
     */
    @Override
    public List<ClassDto> getAll() throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        ClassDto classDto = null;
        List<ClassDto> classes = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(ClassDaoSql.SELECT_ALL);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                classDto = ClassDataAssembler.create(resultSet);
                if (classes == null) {
                    classes = new ArrayList<ClassDto>();
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
     * @see com.myschool.clazz.dao.ClassDao#update(int, com.myschool.clazz.dto.ClassDto)
     */
    @Override
    public boolean update(int classId, ClassDto classDto) throws DaoException {
        boolean updated = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(ClassDaoSql.UPDATE);
            preparedStatement.setString(1, classDto.getClassName());
            preparedStatement.setInt(2, classDto.getPromotionOrder());
            preparedStatement.setInt(3, classId);
            updated = preparedStatement.executeUpdate() > 0;
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
     * @see com.myschool.clazz.dao.ClassDao#get(java.lang.String)
     */
    @Override
    public ClassDto get(String className) throws DaoException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        ClassDto classDto = null;

        try {
            connection = databaseAgent.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(ClassDaoSql.SELECT_BY_NAME);
            if (resultSet.next()) {
                classDto = ClassDataAssembler.create(resultSet);
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
        return classDto;
    }

}
