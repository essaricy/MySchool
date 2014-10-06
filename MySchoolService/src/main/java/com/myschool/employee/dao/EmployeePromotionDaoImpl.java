package com.myschool.employee.dao;

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
import com.myschool.common.util.ConversionUtil;
import com.myschool.employee.assembler.EmployeePromotionDataAssembler;
import com.myschool.employee.dto.EmployeePromotion;
import com.myschool.infra.database.agent.DatabaseAgent;

/**
 * The Class EmployeePromotionDaoImpl.
 */
@Repository
public class EmployeePromotionDaoImpl implements EmployeePromotionDao {

    /** The database agent. */
    @Autowired
    private DatabaseAgent databaseAgent;

    /* (non-Javadoc)
     * @see com.myschool.employee.dao.EmployeePromotionDao#get(int)
     */
    @Override
    public EmployeePromotion get(int employeePromotionId) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        EmployeePromotion employeePromotion = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(EmployeePromotionDaoSql.SELECT_BY_ID);
            preparedStatement.setInt(1, employeePromotionId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                employeePromotion = EmployeePromotionDataAssembler.create(resultSet);
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
        return employeePromotion;
    }

    /* (non-Javadoc)
     * @see com.myschool.employee.dao.EmployeePromotionDao#getByEmployee(int)
     */
    @Override
    public List<EmployeePromotion> getByEmployee(int employeeId)
            throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<EmployeePromotion> employeePromotions = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(EmployeePromotionDaoSql.SELECT_BY_EMPLOYEE_ID);
            preparedStatement.setInt(1, employeeId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                if (employeePromotions == null) {
                    employeePromotions = new ArrayList<EmployeePromotion>();
                }
                employeePromotions.add(EmployeePromotionDataAssembler.create(resultSet));
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
        return employeePromotions;
    }

    /* (non-Javadoc)
     * @see com.myschool.employee.dao.EmployeePromotionDao#create(int, com.myschool.employee.dto.EmployeePromotion)
     */
    @Override
    public int create(int employeeId,
            EmployeePromotion employeePromotion) throws DaoException {
        int nextId = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(EmployeePromotionDaoSql.INSERT);
            nextId = databaseAgent.getNextId("EMPLOYEE_PROMOTION", "PROMOTION_ID");
            preparedStatement.setInt(1, nextId);
            preparedStatement.setInt(2, employeeId);
            preparedStatement.setInt(3, employeePromotion.getPriorDesignation().getDesignationId());
            preparedStatement.setInt(4, employeePromotion.getCurrentDesignation().getDesignationId());
            preparedStatement.setDate(5, ConversionUtil.fromApplicationDateToStorageDate(
                    employeePromotion.getEffectiveFrom()));
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
     * @see com.myschool.employee.dao.EmployeePromotionDao#create(int, java.util.List)
     */
    @Override
    public void create(int employeeId,
            List<EmployeePromotion> employeePromotions) throws DaoException {
        int nextId = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            if (employeePromotions != null && !employeePromotions.isEmpty()) {
                connection = databaseAgent.getConnection();
                preparedStatement = connection.prepareStatement(EmployeePromotionDaoSql.INSERT);

                for (EmployeePromotion employeePromotion : employeePromotions) {
                    if (nextId == 0) {
                        nextId = databaseAgent.getNextId("EMPLOYEE_PROMOTION", "PROMOTION_ID");
                    } else {
                        nextId++;
                    }
                    preparedStatement.setInt(1, nextId);
                    preparedStatement.setInt(2, employeeId);
                    preparedStatement.setInt(3, employeePromotion.getPriorDesignation().getDesignationId());
                    preparedStatement.setInt(4, employeePromotion.getCurrentDesignation().getDesignationId());
                    preparedStatement.setDate(5, ConversionUtil.fromApplicationDateToStorageDate(
                            employeePromotion.getEffectiveFrom()));
                    preparedStatement.addBatch();
                }
                preparedStatement.executeBatch();
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
    }

    /* (non-Javadoc)
     * @see com.myschool.employee.dao.EmployeePromotionDao#update(int, com.myschool.employee.dto.EmployeePromotion)
     */
    @Override
    public boolean update(int employeePromotionId,
            EmployeePromotion employeePromotion) throws DaoException {
        boolean updated = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(EmployeePromotionDaoSql.UPDATE);
            preparedStatement.setInt(1, employeePromotion.getPriorDesignation().getDesignationId());
            preparedStatement.setInt(2, employeePromotion.getCurrentDesignation().getDesignationId());
            preparedStatement.setDate(3, ConversionUtil.fromApplicationDateToStorageDate(
                    employeePromotion.getEffectiveFrom()));
            preparedStatement.setInt(4, employeePromotionId);
            updated = (preparedStatement.executeUpdate() > 0);
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
        return updated;
    }

    /* (non-Javadoc)
     * @see com.myschool.employee.dao.EmployeePromotionDao#get(int, com.myschool.employee.dto.EmployeePromotion)
     */
    @Override
    public EmployeePromotion get(int employeeId,
            EmployeePromotion employeePromotion) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        EmployeePromotion existingEmployeePromotion = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(EmployeePromotionDaoSql.SELECT_BY_PROMOTION);
            preparedStatement.setInt(1, employeeId);
            preparedStatement.setInt(2, employeePromotion.getPriorDesignation().getDesignationId());
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                existingEmployeePromotion = EmployeePromotionDataAssembler.create(resultSet);
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
        return existingEmployeePromotion;
    }

    /* (non-Javadoc)
     * @see com.myschool.employee.dao.EmployeePromotionDao#delete(int)
     */
    @Override
    public boolean delete(int promotionId) throws DaoException {
        boolean deleted = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(EmployeePromotionDaoSql.DELETE);
            preparedStatement.setInt(1, promotionId);
            deleted = (preparedStatement.executeUpdate() > 0);
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
        return deleted;
    }

    /* (non-Javadoc)
     * @see com.myschool.employee.dao.EmployeePromotionDao#getByEmployee(java.lang.String)
     */
    @Override
    public List<EmployeePromotion> getByEmployee(String employeeNumber) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<EmployeePromotion> employeePromotions = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(EmployeePromotionDaoSql.SELECT_BY_EMPLOYEE_NUMBER);
            preparedStatement.setString(1, employeeNumber);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                if (employeePromotions == null) {
                    employeePromotions = new ArrayList<EmployeePromotion>();
                }
                employeePromotions.add(EmployeePromotionDataAssembler.create(resultSet));
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
        return employeePromotions;
    }

    /* (non-Javadoc)
     * @see com.myschool.employee.dao.EmployeePromotionDao#update(java.util.List)
     */
    @Override
    public void update(List<EmployeePromotion> employeePromotions) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            if (employeePromotions != null && !employeePromotions.isEmpty()) {
                connection = databaseAgent.getConnection();
                preparedStatement = connection.prepareStatement(EmployeePromotionDaoSql.UPDATE);

                for (EmployeePromotion employeePromotion : employeePromotions) {
                    if (employeePromotion == null || employeePromotion.getPromotionId() == 0) {
                        continue;
                    }
                    preparedStatement.setInt(1, employeePromotion.getPriorDesignation().getDesignationId());
                    preparedStatement.setInt(2, employeePromotion.getCurrentDesignation().getDesignationId());
                    preparedStatement.setDate(3, ConversionUtil.fromApplicationDateToStorageDate(
                            employeePromotion.getEffectiveFrom()));
                    preparedStatement.setInt(4, employeePromotion.getPromotionId());
                    preparedStatement.addBatch();
                }
                preparedStatement.executeBatch();
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
    }

}
