package com.myschool.employee.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.myschool.common.constants.MySchoolConstant;
import com.myschool.common.exception.ConnectionException;
import com.myschool.common.exception.DaoException;
import com.myschool.common.util.ConversionUtil;
import com.myschool.employee.assembler.EmployeeDataAssembler;
import com.myschool.employee.dto.EmployeeDto;
import com.myschool.employee.dto.EmployeeSearchCriteriaDto;
import com.myschool.infra.database.agent.DatabaseAgent;

/**
 * The Class EmployeeDaoImpl.
 */
@Repository
public class EmployeeDaoImpl implements EmployeeDao {

    /** The database agent. */
    @Autowired
    private DatabaseAgent databaseAgent;

    /* (non-Javadoc)
     * @see com.myschool.employee.dao.EmployeeDao#get(java.lang.String)
     */
    public EmployeeDto get(String employeeNumber) throws DaoException {
        EmployeeDto employee = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(EmployeeDaoSql.SELECT_BY_EMPLOYEE_NUMBER);
            preparedStatement.setString(1, employeeNumber);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                employee = EmployeeDataAssembler.createEmployee(resultSet);
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
        return employee;
    }

    /* (non-Javadoc)
     * @see com.myschool.employee.dao.EmployeeDao#update(int, com.myschool.employee.dto.EmployeeDto)
     */
    public boolean update(int employeeId, EmployeeDto employeeDto) throws DaoException {
        boolean employeeUpdated = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            int index = 1;
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(EmployeeDaoSql.UPDATE);
            preparedStatement.setString(index++, employeeDto.getFirstName());
            preparedStatement.setString(index++, employeeDto.getMiddleName());
            preparedStatement.setString(index++, employeeDto.getLastName());
            preparedStatement.setString(index++, employeeDto.getGender());
            preparedStatement.setDate(index++, ConversionUtil.fromApplicationDateToStorageDate(employeeDto.getDateOfBirth()));
            preparedStatement.setString(index++, employeeDto.getBloodGroup());
            preparedStatement.setString(index++, employeeDto.getNationality());
            preparedStatement.setString(index++, employeeDto.getMaritalStatus());
            preparedStatement.setDate(index++, ConversionUtil.fromApplicationDateToStorageDate(
                    employeeDto.getWeddingDay()));
            preparedStatement.setInt(index++, employeeDto.getEmployedAtBranch().getBranchId());
            preparedStatement.setInt(index++, employeeDto.getDesignation().getDesignationId());
            preparedStatement.setInt(index++, employeeDto.getEmploymentStatus().getStatusId());
            preparedStatement.setDate(index++, ConversionUtil.fromApplicationDateToStorageDate(
                    employeeDto.getEmploymentStartDate()));
            preparedStatement.setDate(index++, ConversionUtil.fromApplicationDateToStorageDate(
                    employeeDto.getEmploymentEndDate()));
            int reportingToEmployeeId = employeeDto.getReportingTo().getEmployeeId();
            if (reportingToEmployeeId == 0) {
                preparedStatement.setNull(index++, Types.INTEGER);
            } else {
                preparedStatement.setInt(index++, employeeDto.getReportingTo().getEmployeeId());
            }
            preparedStatement.setString(index++, employeeDto.getRemarks());
            preparedStatement.setString(index++, ConversionUtil.toYN(employeeDto.isVerified()));
            preparedStatement.setInt(index++, employeeId);
            employeeUpdated = preparedStatement.executeUpdate() > 0;
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
        return employeeUpdated;
    }

    /* (non-Javadoc)
     * @see com.myschool.employee.dao.EmployeeDao#create(com.myschool.employee.dto.EmployeeDto)
     */
    public int create(EmployeeDto employeeDto) throws DaoException {
        int employeeId = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            int index = 1;
            employeeId = databaseAgent.getNextId("EMPLOYEE", "EMPLOYEE_ID");
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(EmployeeDaoSql.INSERT);
            preparedStatement.setInt(index++, employeeId);
            preparedStatement.setString(index++, employeeDto.getEmployeeNumber());
            preparedStatement.setString(index++, employeeDto.getFirstName());
            preparedStatement.setString(index++, employeeDto.getMiddleName());
            preparedStatement.setString(index++, employeeDto.getLastName());
            preparedStatement.setString(index++, employeeDto.getGender());
            preparedStatement.setDate(index++, ConversionUtil.fromApplicationDateToStorageDate(
                    employeeDto.getDateOfBirth()));
            preparedStatement.setString(index++, employeeDto.getBloodGroup());
            preparedStatement.setString(index++, employeeDto.getNationality());
            preparedStatement.setString(index++, employeeDto.getMaritalStatus());
            preparedStatement.setDate(index++, ConversionUtil.fromApplicationDateToStorageDate(
                    employeeDto.getWeddingDay()));
            preparedStatement.setInt(index++, employeeDto.getEmployedAtBranch().getBranchId());
            preparedStatement.setInt(index++, employeeDto.getDesignation().getDesignationId());
            preparedStatement.setInt(index++, employeeDto.getEmploymentStatus().getStatusId());
            preparedStatement.setDate(index++, ConversionUtil.fromApplicationDateToStorageDate(
                    employeeDto.getEmploymentStartDate()));
            preparedStatement.setDate(index++, ConversionUtil.fromApplicationDateToStorageDate(
                    employeeDto.getEmploymentEndDate()));
            int reportingToEmployeeId = employeeDto.getReportingTo().getEmployeeId();
            if (reportingToEmployeeId == 0) {
                preparedStatement.setNull(index++, Types.INTEGER);
            } else {
                preparedStatement.setInt(index++, employeeDto.getReportingTo().getEmployeeId());
            }
            preparedStatement.setString(index++, employeeDto.getRemarks());
            preparedStatement.setString(index++, ConversionUtil.toYN(employeeDto.isVerified()));
            if (preparedStatement.executeUpdate() == 0) {
                employeeId = 0;
            }
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
        return employeeId;
    }

    /* (non-Javadoc)
     * @see com.myschool.employee.dao.EmployeeDao#get(int)
     */
    @Override
    public EmployeeDto get(int employeeId) throws DaoException {
        EmployeeDto employee = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(EmployeeDaoSql.SELECT_BY_EMPLOYEE_ID);
            preparedStatement.setInt(1, employeeId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                employee = EmployeeDataAssembler.createEmployee(resultSet);
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
        return employee;
    }

    /* (non-Javadoc)
     * @see com.myschool.employee.dao.EmployeeDao#getLastEmployeeNumber()
     */
    @Override
    public String getLastEmployeeNumber() throws DaoException {
        String employeeNumber = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(EmployeeDaoSql.SELECT_LAST_EMPLOYEE);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                employeeNumber = resultSet.getString("EMPLOYEE_NUMBER");
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
        return employeeNumber;
    }

    /* (non-Javadoc)
     * @see com.myschool.employee.dao.EmployeeDao#getAll(com.myschool.employee.dto.EmployeeSearchCriteriaDto)
     */
    @Override
    public List<EmployeeDto> getAll(
            EmployeeSearchCriteriaDto employeeSearchCriteriaDto) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<EmployeeDto> employeees = null;
        EmployeeDto employee = null;

        try {
            connection = databaseAgent.getConnection();
            String employeeSearchQuery = EmployeeDaoSql.getEmployeeSearchQuery(employeeSearchCriteriaDto);
            preparedStatement = connection.prepareStatement(employeeSearchQuery);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                employee = EmployeeDataAssembler.createEmployee(resultSet);
                if (employeees == null) {
                    employeees = new ArrayList<EmployeeDto>();
                }
                employeees.add(employee);
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
        return employeees;
    }

    /* (non-Javadoc)
     * @see com.myschool.employee.dao.EmployeeDao#delete(java.lang.String)
     */
    @Override
    public boolean delete(String employeeNumber) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(EmployeeDaoSql.DELETE_BY_NUMBER);
            preparedStatement.setString(1, employeeNumber);
            return (preparedStatement.executeUpdate() > 0);
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
	 * @see com.myschool.employee.dao.EmployeeDao#getNextEmployeeNumber(java.lang.String, java.lang.String)
	 */
	@Override
	public String getNextEmployeeNumber(String employeeNumber, String type) throws DaoException {
		String query = null;
        String nextEmployeeNumber = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = databaseAgent.getConnection();
            if (type.equals(MySchoolConstant.VERIFIED)) {
            	query = EmployeeDaoSql.SELECT_NEXT_VERIFIED_EMPLOYEE_NUMBER;
            } else {
            	query = EmployeeDaoSql.SELECT_NEXT_UNVERIFIED_EMPLOYEE_NUMBER;
            }
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, employeeNumber);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
            	nextEmployeeNumber = resultSet.getString("EMPLOYEE_NUMBER");
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
        return nextEmployeeNumber;
    }

	/* (non-Javadoc)
	 * @see com.myschool.employee.dao.EmployeeDao#getPreviousEmployeeNumber(java.lang.String, java.lang.String)
	 */
	@Override
	public String getPreviousEmployeeNumber(String employeeNumber, String type) throws DaoException {
		String query = null;
        String previousEmployeeNumber = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = databaseAgent.getConnection();
            if (type.equals(MySchoolConstant.VERIFIED)) {
            	query = EmployeeDaoSql.SELECT_PREVIOUS_VERIFIED_EMPLOYEE_NUMBER;
            } else {
            	query = EmployeeDaoSql.SELECT_PREVIOUS_UNVERIFIED_EMPLOYEE_NUMBER;
            }
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, employeeNumber);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
            	previousEmployeeNumber = resultSet.getString("EMPLOYEE_NUMBER");
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
        return previousEmployeeNumber;
    }

}
