package com.myschool.organization.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.myschool.common.exception.ConnectionException;
import com.myschool.common.exception.DaoException;
import com.myschool.common.util.ConversionUtil;
import com.myschool.infra.database.agent.DatabaseAgent;
import com.myschool.organization.assembler.OrganizationDataAssembler;
import com.myschool.organization.dto.Organization;
import com.myschool.organization.dto.OrganizationManifest;
import com.myschool.organization.dto.OrganizationPreferences;

/**
 * The Class OrganizationDaoImpl.
 */
@Repository
public class OrganizationDaoImpl implements OrganizationDao {

    /** The database agent. */
    @Autowired
    private DatabaseAgent databaseAgent;

    /* (non-Javadoc)
     * @see com.myschool.organization.dao.OrganizationDao#getOrganization()
     */
    @Override
    public Organization getOrganization() throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        Organization organization = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(OrganizationDaoSql.SELECT);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                organization = OrganizationDataAssembler.create(resultSet);
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
        return organization;
    }

    /* (non-Javadoc)
     * @see com.myschool.organization.dao.OrganizationDao#update(com.myschool.organization.dto.Organization)
     */
    @Override
    public boolean update(Organization organization) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(OrganizationDaoSql.UPDATE);
            preparedStatement.setString(1, organization.getAddress());
            preparedStatement.setString(2, organization.getPhoneNumber());
            preparedStatement.setString(3, organization.getFaxNumber());
            return preparedStatement.executeUpdate() > 0;
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
     * @see com.myschool.organization.dao.OrganizationDao#getOrganizationPreferences()
     */
    @Override
    public OrganizationPreferences getOrganizationPreferences() throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        OrganizationPreferences organizationPreferences = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(OrganizationDaoSql.SELECT_PREFERENCES);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                organizationPreferences = OrganizationDataAssembler.createPreferences(resultSet);
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
        return organizationPreferences;
    }

    /* (non-Javadoc)
     * @see com.myschool.organization.dao.OrganizationDao#updateNotificationSettings(com.myschool.organization.dto.OrganizationPreferences)
     */
    @Override
    public boolean updateNotificationSettings(
            OrganizationPreferences organizationPreferences)
                    throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(OrganizationDaoSql.UPDATE_NOTIFICATION_SETTINGS);
            preparedStatement.setString(1, ConversionUtil.toYN(organizationPreferences.isEmailActive()));
            preparedStatement.setString(2, ConversionUtil.toYN(organizationPreferences.isEmailEmployees()));
            preparedStatement.setString(3, ConversionUtil.toYN(organizationPreferences.isEmailStudents()));
            preparedStatement.setString(4, ConversionUtil.toYN(organizationPreferences.isSmsActive()));
            preparedStatement.setString(5, ConversionUtil.toYN(organizationPreferences.isSmsEmployees()));
            preparedStatement.setString(6, ConversionUtil.toYN(organizationPreferences.isSmsStudents()));
            return preparedStatement.executeUpdate() > 0;
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
     * @see com.myschool.organization.dao.OrganizationDao#updateDisplaySettings(com.myschool.organization.dto.OrganizationPreferences)
     */
    @Override
    public boolean updateDisplaySettings(
            OrganizationPreferences organizationPreferences)
                    throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(OrganizationDaoSql.UPDATE_DISPLAY_SETTINGS);
            preparedStatement.setString(1, ConversionUtil.toYN(organizationPreferences.isUseMenuIcons()));
            preparedStatement.setString(2, organizationPreferences.getDefaultTheme());
            preparedStatement.setString(3, organizationPreferences.getDefaultGallery());
            return preparedStatement.executeUpdate() > 0;
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
     * @see com.myschool.organization.dao.OrganizationDao#updateSelfServiceSettings(com.myschool.organization.dto.OrganizationPreferences)
     */
    @Override
    public boolean updateSelfServiceSettings(
            OrganizationPreferences organizationPreferences)
                    throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(OrganizationDaoSql.UPDATE_SELF_SERVICE_SETTINGS);
            preparedStatement.setString(1, ConversionUtil.toYN(organizationPreferences.isUseEmployeeSelfSubmit()));
            preparedStatement.setString(2, ConversionUtil.toYN(organizationPreferences.isUseStudentSelfSubmit()));
            return preparedStatement.executeUpdate() > 0;
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
     * @see com.myschool.organization.dao.OrganizationDao#updateDefaultTheme(java.lang.String)
     */
    @Override
    public boolean updateDefaultTheme(String themename) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(OrganizationDaoSql.UPDATE_DEFAULT_THEME);
            preparedStatement.setString(1, themename);
            return preparedStatement.executeUpdate() > 0;
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
     * @see com.myschool.organization.dao.OrganizationDao#pinGallery(java.lang.String)
     */
    @Override
    public boolean pinGallery(String gallery) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(OrganizationDaoSql.UPDATE_DEFAULT_GALLERY);
            preparedStatement.setString(1, gallery);
            return preparedStatement.executeUpdate() > 0;
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
     * @see com.myschool.organization.dao.OrganizationDao#getOrganizationManifest()
     */
    @Override
    public OrganizationManifest getOrganizationManifest() throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        OrganizationManifest organizationManifest = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(OrganizationDaoSql.SELECT_MANIFEST);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                organizationManifest = OrganizationDataAssembler.createManifest(resultSet);
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
        return organizationManifest;
    }

    /* (non-Javadoc)
     * @see com.myschool.organization.dao.OrganizationDao#rollAcademicYear()
     */
    @Override
    public boolean rollAcademicYear() throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(OrganizationDaoSql.ROLL_ACADEMIC_YEAR);
            return preparedStatement.executeUpdate() > 0;
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
     * @see com.myschool.organization.dao.OrganizationDao#setAcademicYearEndProcess(boolean)
     */
    @Override
    public boolean setAcademicYearEndProcess(boolean onOff)
            throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(OrganizationDaoSql.UPDATE_AYE_PROCESS);
            preparedStatement.setString(1, ConversionUtil.toYN(onOff));
            return preparedStatement.executeUpdate() > 0;
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

}
