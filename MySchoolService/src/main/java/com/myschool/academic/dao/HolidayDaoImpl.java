/*
 * 
 */
package com.myschool.academic.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.myschool.academic.assembler.HolidayDataAssembler;
import com.myschool.academic.dto.HolidayDto;
import com.myschool.academic.dto.HolidaySearchCriteria;
import com.myschool.common.exception.ConnectionException;
import com.myschool.common.exception.DaoException;
import com.myschool.infra.database.agent.DatabaseAgent;
import com.quasar.core.util.ConversionUtil;

/**
 * The Class HolidayDaoImpl.
 */
@Repository
public class HolidayDaoImpl implements HolidayDao {

    /** The database agent. */
    @Autowired
    private DatabaseAgent databaseAgent;

    /* (non-Javadoc)
     * @see com.myschool.academic.dao.HolidayDao#create(com.myschool.academic.dto.HolidayDto)
     */
    @Override
    public int create(HolidayDto holiday) throws DaoException {
        int nextId = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            nextId = databaseAgent.getNextId("HOLIDAYS", "HOLIDAY_ID");
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(HolidayDaoSql.INSERT);
            preparedStatement.setInt(1, nextId);
            preparedStatement.setString(2, holiday.getHolidayName());
            preparedStatement.setDate(3, ConversionUtil.fromApplicationDateToStorageDate(holiday.getStartDate()));
            preparedStatement.setDate(4, ConversionUtil.fromApplicationDateToStorageDate(holiday.getEndDate()));
            if (preparedStatement.executeUpdate() == 0) {
                nextId = 0;
            }
        } catch (SQLException sqlException) {
            throw new DaoException(sqlException.getMessage(), sqlException);
        } catch (ConnectionException connectionException) {
            throw new DaoException(connectionException.getMessage(), connectionException);
        } finally {
            try {
                databaseAgent.releaseResources(connection, preparedStatement);
            } catch (ConnectionException connectionException) {
                throw new DaoException(connectionException.getMessage(),
                        connectionException);
            }
        }
        return nextId;
    }

    /* (non-Javadoc)
     * @see com.myschool.academic.dao.HolidayDao#delete(int)
     */
    @Override
    public boolean delete(int holidayId) throws DaoException {
        boolean deleted = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(HolidayDaoSql.DELETE);
            preparedStatement.setInt(1, holidayId);
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
     * @see com.myschool.academic.dao.HolidayDao#get(int)
     */
    @Override
    public HolidayDto get(int holidayId) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        HolidayDto holidayDto = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(HolidayDaoSql.SELECT_BY_ID);
            preparedStatement.setInt(1, holidayId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                holidayDto = HolidayDataAssembler.createHoliday(resultSet);
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
        return holidayDto;
    }

    /* (non-Javadoc)
     * @see com.myschool.academic.dao.HolidayDao#getAll()
     */
    @Override
    public List<HolidayDto> getAll() throws DaoException {
        List<HolidayDto> holidays = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        HolidayDto holidayDto = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(HolidayDaoSql.SELECT_ALL);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                holidayDto = HolidayDataAssembler.createHoliday(resultSet);
                if (holidays == null){
                    holidays = new ArrayList<HolidayDto>();
                }
                holidays.add(holidayDto);
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
        return holidays;
    }

    /* (non-Javadoc)
     * @see com.myschool.academic.dao.HolidayDao#update(int, com.myschool.academic.dto.HolidayDto)
     */
    @Override
    public boolean update(int holidayId, HolidayDto holiday)
            throws DaoException {
        boolean updated = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(HolidayDaoSql.UPDATE);
            preparedStatement.setString(1, holiday.getHolidayName());
            preparedStatement.setDate(2, ConversionUtil.fromApplicationDateToStorageDate(holiday.getStartDate()));
            preparedStatement.setDate(3, ConversionUtil.fromApplicationDateToStorageDate(holiday.getEndDate()));
            preparedStatement.setInt(4, holidayId);
            updated = preparedStatement.executeUpdate() > 0;
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
     * @see com.myschool.academic.dao.HolidayDao#get(com.myschool.academic.dto.HolidayDto)
     */
    @Override
    public HolidayDto get(HolidayDto holiday) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        HolidayDto holidayDto = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(HolidayDaoSql.SELECT_BY_HOLIDAY);
            preparedStatement.setString(1, holiday.getHolidayName());
            preparedStatement.setDate(2, ConversionUtil.fromApplicationDateToStorageDate(holiday.getStartDate()));
            preparedStatement.setDate(3, ConversionUtil.fromApplicationDateToStorageDate(holiday.getEndDate()));
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                holidayDto = HolidayDataAssembler.createHoliday(resultSet);
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
        return holidayDto;
    }

    /* (non-Javadoc)
     * @see com.myschool.academic.dao.HolidayDao#getAll(com.myschool.academic.dto.HolidaySearchCriteria)
     */
    @Override
    public List<HolidayDto> getAll(HolidaySearchCriteria holidaySearchCriteria)
            throws DaoException {
        List<HolidayDto> holidays = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        HolidayDto holidayDto = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(HolidayDaoSql.getHolidaysSql(holidaySearchCriteria));
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                holidayDto = HolidayDataAssembler.createHoliday(resultSet);
                if (holidays == null){
                    holidays = new ArrayList<HolidayDto>();
                }
                holidays.add(holidayDto);
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
        return holidays;
    }

}
