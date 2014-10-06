package com.myschool.employee.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.myschool.common.exception.ConnectionException;
import com.myschool.common.exception.DaoException;
import com.myschool.employee.assembler.EmployeeContactDataAssembler;
import com.myschool.employee.constant.EmployeeNotificationTo;
import com.myschool.employee.dto.EmployeeContact;
import com.myschool.infra.database.agent.DatabaseAgent;

/**
 * The Class EmployeeContactDaoImpl.
 */
@Repository
public class EmployeeContactDaoImpl implements EmployeeContactDao {

    /** The database agent. */
    @Autowired
    private DatabaseAgent databaseAgent;

    /* (non-Javadoc)
     * @see com.myschool.employee.dao.EmployeeContactDao#get(int)
     */
    @Override
    public EmployeeContact get(int employeeId) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        EmployeeContact employeeContact = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(EmployeeContactDaoSql.SELECT_BY_EMPLOYEE_ID);
            preparedStatement.setInt(1, employeeId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                employeeContact = EmployeeContactDataAssembler.create(resultSet);
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
        return employeeContact;
    }

    /* (non-Javadoc)
     * @see com.myschool.employee.dao.EmployeeContactDao#create(int, com.myschool.employee.dto.EmployeeContact)
     */
    @Override
    public boolean create(int employeeId, EmployeeContact employeeContact) throws DaoException {
        boolean created = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(EmployeeContactDaoSql.INSERT);
            preparedStatement.setInt(1, employeeId);
            preparedStatement.setString(2, employeeContact.getPermanentAddress());
            preparedStatement.setString(3, employeeContact.getPresentAddress());
            preparedStatement.setString(4, employeeContact.getPersonalMobileNumber());
            preparedStatement.setString(5, employeeContact.getPersonalEmailId());
            preparedStatement.setString(6, employeeContact.getEmergencyContactNumber());
            preparedStatement.setString(7, employeeContact.getEmergencyContactRelationship().getCode());
            preparedStatement.setString(8, employeeContact.getOfficeDeskPhoneNumber());
            preparedStatement.setString(9, employeeContact.getOfficeDeskExtension());
            preparedStatement.setString(10, employeeContact.getOfficeMobileNumber());
            preparedStatement.setString(11, employeeContact.getOfficeEmailId());
            EmployeeNotificationTo emailNotificationTo = employeeContact.getEmailNotificationTo();
            if (emailNotificationTo == null) {
                preparedStatement.setNull(12, Types.VARCHAR);
            } else {
                preparedStatement.setString(12, emailNotificationTo.getNotificationToCode());
            }
            EmployeeNotificationTo smsNotificationTo = employeeContact.getSmsNotificationTo();
            if (smsNotificationTo == null) {
                preparedStatement.setNull(13, Types.VARCHAR);
            } else {
                preparedStatement.setString(13, smsNotificationTo.getNotificationToCode());
            }
            created = (preparedStatement.executeUpdate() > 0);
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
     * @see com.myschool.employee.dao.EmployeeContactDao#update(int, com.myschool.employee.dto.EmployeeContact)
     */
    @Override
    public boolean update(int employeeId, EmployeeContact employeeContact) throws DaoException {
        boolean updated = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(EmployeeContactDaoSql.UPDATE);
            preparedStatement.setString(1, employeeContact.getPermanentAddress());
            preparedStatement.setString(2, employeeContact.getPresentAddress());
            preparedStatement.setString(3, employeeContact.getPersonalMobileNumber());
            preparedStatement.setString(4, employeeContact.getPersonalEmailId());
            preparedStatement.setString(5, employeeContact.getEmergencyContactNumber());
            preparedStatement.setString(6, employeeContact.getEmergencyContactRelationship().getCode());
            preparedStatement.setString(7, employeeContact.getOfficeDeskPhoneNumber());
            preparedStatement.setString(8, employeeContact.getOfficeDeskExtension());
            preparedStatement.setString(9, employeeContact.getOfficeMobileNumber());
            preparedStatement.setString(10, employeeContact.getOfficeEmailId());

            EmployeeNotificationTo emailNotificationTo = employeeContact.getEmailNotificationTo();
            if (emailNotificationTo == null) {
                preparedStatement.setNull(11, Types.VARCHAR);
            } else {
                preparedStatement.setString(11, emailNotificationTo.getNotificationToCode());
            }
            EmployeeNotificationTo smsNotificationTo = employeeContact.getSmsNotificationTo();
            if (smsNotificationTo == null) {
                preparedStatement.setNull(12, Types.VARCHAR);
            } else {
                preparedStatement.setString(12, smsNotificationTo.getNotificationToCode());
            }
            preparedStatement.setInt(13, employeeId);
            updated = (preparedStatement.executeUpdate() > 0);
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

}
