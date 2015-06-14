package com.myschool.application.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.myschool.application.assembler.ProfileDataAssembler;
import com.myschool.application.dto.MySchoolProfileDto;
import com.myschool.application.dto.OrganizationProfileDto;
import com.myschool.common.exception.ConnectionException;
import com.myschool.common.exception.DaoException;
import com.myschool.common.util.ConversionUtil;
import com.myschool.infra.database.agent.DatabaseAgent;

/**
 * The Class ProfileDaoImpl.
 */
@Repository
public class ProfileDaoImpl implements ProfileDao {

    /** The database agent. */
    @Autowired
    private DatabaseAgent databaseAgent;

    /* (non-Javadoc)
     * @see com.myschool.application.dao.ProfileDao#getOrganizationProfile()
     */
    @Override
    public OrganizationProfileDto getOrganizationProfile() throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        OrganizationProfileDto organizationProfile = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(ProfileDaoSql.SELECT_ORGANIZATION_PROFILE);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                organizationProfile = ProfileDataAssembler.createOrganizationProfile(resultSet);
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
        return organizationProfile;
    }

    /* (non-Javadoc)
     * @see com.myschool.application.dao.ProfileDao#getMySchoolProfile()
     */
    @Override
    public MySchoolProfileDto getMySchoolProfile() throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        MySchoolProfileDto mySchoolProfile = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(ProfileDaoSql.SELECT_MYSCHOOL_PROFILE);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                mySchoolProfile = ProfileDataAssembler.createMySchoolProfile(resultSet);
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
        return mySchoolProfile;
    }

    /* (non-Javadoc)
     * @see com.myschool.application.dao.ProfileDao#update(com.myschool.application.dto.OrganizationProfileDto)
     */
    @Override
    public boolean update(OrganizationProfileDto organizationProfile) throws DaoException {
        boolean updated = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(ProfileDaoSql.UPDATE_ORGANIZATION_PROFILE);
            preparedStatement.setString(1, organizationProfile.getAddress());
            preparedStatement.setString(2, organizationProfile.getPhoneNumber());
            preparedStatement.setString(3, organizationProfile.getFaxNumber());
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
     * @see com.myschool.application.dao.ProfileDao#update(com.myschool.application.dto.MySchoolProfileDto)
     */
    @Override
    public boolean update(MySchoolProfileDto mySchoolProfile) throws DaoException {
        boolean updated = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(ProfileDaoSql.UPDATE_MYSCHOOL_PROFILE);
            preparedStatement.setString(1, ConversionUtil.toYN(mySchoolProfile.isEmailActive()));
            preparedStatement.setString(2, ConversionUtil.toYN(mySchoolProfile.isEmailEmployees()));
            preparedStatement.setString(3, ConversionUtil.toYN(mySchoolProfile.isEmailStudents()));
            preparedStatement.setString(4, ConversionUtil.toYN(mySchoolProfile.isSmsActive()));
            preparedStatement.setString(5, ConversionUtil.toYN(mySchoolProfile.isSmsEmployees()));
            preparedStatement.setString(6, ConversionUtil.toYN(mySchoolProfile.isSmsStudents()));
            preparedStatement.setString(7, ConversionUtil.toYN(mySchoolProfile.isUseMenuIcons()));
            preparedStatement.setString(8, ConversionUtil.toYN(mySchoolProfile.isUseEmployeeSelfSubmit()));
            preparedStatement.setString(9, ConversionUtil.toYN(mySchoolProfile.isUseStudentSelfSubmit()));
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
     * @see com.myschool.application.dao.ProfileDao#updateAyeProgress(java.lang.String)
     */
    @Override
    public boolean updateAyeProgress(String ayeProgressStatus) throws DaoException {
        boolean updated = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(ProfileDaoSql.UPDATE_AYE_STATUS);
            preparedStatement.setString(1, ayeProgressStatus);
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

}
