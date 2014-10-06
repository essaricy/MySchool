package com.myschool.school.dao;

import java.sql.Connection;
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
import com.myschool.school.assembler.SchoolDataAssembler;
import com.myschool.school.dto.SchoolDto;

/**
 * The Class SchoolDaoImpl.
 */
@Repository
public class SchoolDaoImpl implements SchoolDao {

    /** The database agent. */
    @Autowired
    private DatabaseAgent databaseAgent;

    /* (non-Javadoc)
     * @see com.myschool.school.dao.SchoolDao#create(com.myschool.school.dto.SchoolDto)
     */
    @Override
    public int create(SchoolDto schoolDto) throws DaoException {
        int nextId = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(SchoolDaoSql.INSERT);
            nextId = databaseAgent.getNextId("SCHOOL", "SCHOOL_ID");
            preparedStatement.setInt(1, nextId);
            preparedStatement.setString(2, schoolDto.getSchoolName());
            preparedStatement.setString(3, schoolDto.getAddress());
            preparedStatement.setString(4, schoolDto.getPrimaryPhoneNumber());
            preparedStatement.setString(5, schoolDto.getSecondaryPhoneNumber());
            preparedStatement.setString(6, schoolDto.getMobileNumber());
            preparedStatement.setString(7, schoolDto.getFaxNumber());
            preparedStatement.setString(8, schoolDto.getEmailId());
            preparedStatement.setInt(9, schoolDto.getBranch().getBranchId());
            preparedStatement.setInt(10, schoolDto.getDivision().getDivisionId());

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
     * @see com.myschool.school.dao.SchoolDao#delete(int)
     */
    @Override
    public boolean delete(int schoolId) throws DaoException {
        boolean deleted = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(SchoolDaoSql.DELETE);
            preparedStatement.setInt(1, schoolId);
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
     * @see com.myschool.school.dao.SchoolDao#get(int)
     */
    @Override
    public SchoolDto get(int schoolId) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        SchoolDto school = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(SchoolDaoSql.SELECT_BY_ID);
            preparedStatement.setInt(1, schoolId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                school = SchoolDataAssembler.create(resultSet);
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
        return school;
    }

    /* (non-Javadoc)
     * @see com.myschool.school.dao.SchoolDao#getAll()
     */
    @Override
    public List<SchoolDto> getAll() throws DaoException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        SchoolDto school = null;
        List<SchoolDto> schools = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(SchoolDaoSql.SELECT_ALL);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                school = SchoolDataAssembler.create(resultSet);
                if (schools == null) {
                    schools = new ArrayList<SchoolDto>();
                }
                schools.add(school);
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
        return schools;
    }

    /* (non-Javadoc)
     * @see com.myschool.school.dao.SchoolDao#update(int, com.myschool.school.dto.SchoolDto)
     */
    @Override
    public boolean update(int schoolId, SchoolDto schoolDto) throws DaoException {
        boolean updated = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(SchoolDaoSql.UPDATE);
            preparedStatement.setString(1, schoolDto.getSchoolName());
            preparedStatement.setString(2, schoolDto.getAddress());
            preparedStatement.setString(3, schoolDto.getPrimaryPhoneNumber());
            preparedStatement.setString(4, schoolDto.getSecondaryPhoneNumber());
            preparedStatement.setString(5, schoolDto.getMobileNumber());
            preparedStatement.setString(6, schoolDto.getFaxNumber());
            preparedStatement.setString(7, schoolDto.getEmailId());
            preparedStatement.setInt(8, schoolDto.getBranch().getBranchId());
            preparedStatement.setInt(9, schoolDto.getDivision().getDivisionId());
            preparedStatement.setInt(10, schoolId);
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
     * @see com.myschool.school.dao.SchoolDao#getByBranch(int)
     */
    public List<SchoolDto> getByBranch(int branchId) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        SchoolDto school = null;
        List<SchoolDto> schools = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(SchoolDaoSql.SELECT_BY_BRANCH_ID);
            preparedStatement.setInt(1, branchId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                school = SchoolDataAssembler.create(resultSet);
                if (schools == null) {
                    schools = new ArrayList<SchoolDto>();
                }
                schools.add(school);
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
        return schools;
    }

    /* (non-Javadoc)
     * @see com.myschool.school.dao.SchoolDao#get(com.myschool.school.dto.SchoolDto)
     */
    @Override
    public SchoolDto get(SchoolDto school) throws DaoException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        SchoolDto gotSchool = null;

        try {
            connection = databaseAgent.getConnection();
            String selectSchoolSql = SchoolDaoSql.getSelectSchoolSql(school);
            preparedStatement = connection.prepareStatement(selectSchoolSql);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                gotSchool = SchoolDataAssembler.create(resultSet);
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
        return gotSchool;
    }

    /* (non-Javadoc)
     * @see com.myschool.school.dao.SchoolDao#getAll(com.myschool.school.dto.SchoolDto)
     */
    @Override
    public List<SchoolDto> getAll(SchoolDto schoolDto) throws DaoException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<SchoolDto> schools = null;

        try {
            connection = databaseAgent.getConnection();
            String selectSchoolSql = SchoolDaoSql.getSelectSchoolSql(schoolDto);
            preparedStatement = connection.prepareStatement(selectSchoolSql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                if (schools == null) {
                    schools = new ArrayList<SchoolDto>();
                }
                schools.add(SchoolDataAssembler.create(resultSet));
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
        return schools;
    }

}
