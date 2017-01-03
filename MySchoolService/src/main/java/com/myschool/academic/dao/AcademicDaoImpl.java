package com.myschool.academic.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.myschool.academic.assembler.AcademicDataAssembler;
import com.myschool.academic.dto.AcademicDto;
import com.myschool.common.exception.ConnectionException;
import com.myschool.common.exception.DaoException;
import com.myschool.infra.database.agent.DatabaseAgent;
import com.quasar.core.util.ConversionUtil;

/**
 * The Class AcademicDaoImpl.
 */
@Repository
public class AcademicDaoImpl implements AcademicDao {

    /** The database agent. */
    @Autowired
    private DatabaseAgent databaseAgent;

    /* (non-Javadoc)
     * @see com.myschool.academic.dao.AcademicDao#create(com.myschool.academic.dto.AcademicDto)
     */
    @Override
    public boolean create(AcademicDto academic) throws DaoException {
        boolean created = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(AcademicDaoSql.INSERT);
            preparedStatement.setString(1, academic.getAcademicYearName());
            preparedStatement.setDate(2, ConversionUtil.fromApplicationDateToStorageDate(academic.getAcademicYearStartDate()));
            preparedStatement.setDate(3, ConversionUtil.fromApplicationDateToStorageDate(academic.getAcademicYearEndDate()));
            created = preparedStatement.executeUpdate() > 0;
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
     * @see com.myschool.academic.dao.AcademicDao#update(com.myschool.academic.dto.AcademicDto)
     */
    @Override
    public boolean update(AcademicDto academic) throws DaoException {
        boolean created = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(AcademicDaoSql.UPDATE);
            preparedStatement.setDate(1, ConversionUtil.fromApplicationDateToStorageDate(academic.getAcademicYearStartDate()));
            preparedStatement.setDate(2, ConversionUtil.fromApplicationDateToStorageDate(academic.getAcademicYearEndDate()));
            preparedStatement.setString(3, academic.getAcademicYearName());
            created = preparedStatement.executeUpdate() > 0;
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
     * @see com.myschool.academic.dao.AcademicDao#getAll()
     */
    @Override
    public List<AcademicDto> getAll() throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        AcademicDto academic = null;
        List<AcademicDto> academics = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(AcademicDaoSql.SELECT_ALL_ORDER_DESC);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                academic = AcademicDataAssembler.create(resultSet);
                if (academics == null) {
                    academics = new ArrayList<AcademicDto>();
                }
                academics.add(academic);
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
        return academics;
    }

    /* (non-Javadoc)
     * @see com.myschool.academic.dao.AcademicDao#get(java.lang.String)
     */
    @Override
    public AcademicDto get(String academicYearName) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        AcademicDto academic = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(AcademicDaoSql.SELECT_BY_ACADEMIC_YEAR_NAME);
            preparedStatement.setString(1, academicYearName);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                academic = AcademicDataAssembler.create(resultSet);
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
        return academic;
    }

    /* (non-Javadoc)
     * @see com.myschool.academic.dao.AcademicDao#getCurrentAcademic()
     */
    @Override
    public AcademicDto getCurrentAcademic() throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        AcademicDto academic = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(AcademicDaoSql.SELECT_CURRENT_ACADEMIC);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                academic = AcademicDataAssembler.create(resultSet);
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
        return academic;
    }

    /* (non-Javadoc)
     * @see com.myschool.academic.dao.AcademicDao#getNextAcademic()
     */
    @Override
    public AcademicDto getNextAcademic() throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        AcademicDto academic = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(AcademicDaoSql.SELECT_NEXT_ACADEMIC);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                academic = AcademicDataAssembler.create(resultSet);
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
        return academic;
    }

    /* (non-Javadoc)
     * @see com.myschool.academic.dao.AcademicDao#delete(java.lang.String)
     */
    @Override
    public boolean delete(String academicYearName) throws DaoException {
        boolean deleted = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(AcademicDaoSql.DELETE);
            preparedStatement.setString(1, academicYearName);
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

}
