package com.myschool.report.dao;

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
import com.myschool.report.assembler.ReportDataAssembler;
import com.myschool.report.dto.ReportCriteriaToken;
import com.myschool.report.dto.ReportDto;

/**
 * The Class ReportDaoImpl.
 */
@Repository
public class ReportDaoImpl implements ReportDao {

    /** The database agent. */
    @Autowired
    private DatabaseAgent databaseAgent;

    /* (non-Javadoc)
     * @see com.myschool.report.dao.ReportDao#getAll()
     */
    @Override
    public List<ReportDto> getAll() throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<ReportDto> reports = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(ReportDaoSql.SELECT_ALL);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                if (reports == null) {
                    reports = new ArrayList<ReportDto>();
                }
                reports.add(ReportDataAssembler.create(resultSet));
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
        return reports;
    }

    /* (non-Javadoc)
     * @see com.myschool.report.dao.ReportDao#get(java.lang.String)
     */
    @Override
    public ReportDto get(String reportKey) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        ReportDto report = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(ReportDaoSql.SELECT_BY_REPORT_KEY);
            preparedStatement.setString(1, reportKey);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                report = ReportDataAssembler.create(resultSet);
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
        return report;
    }

    /* (non-Javadoc)
     * @see com.myschool.report.dao.ReportDao#getReportCriteriaTokens(java.lang.String)
     */
    @Override
    public List<ReportCriteriaToken> getReportCriteriaTokens(String reportKey)
            throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<ReportCriteriaToken> reportCriteriaTokens = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(ReportDaoSql.SELECT_REPORT_CRITERIA_BY_KEY);
            preparedStatement.setString(1, reportKey);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                if (reportCriteriaTokens == null) {
                    reportCriteriaTokens = new ArrayList<ReportCriteriaToken>();
                }
                reportCriteriaTokens.add(ReportDataAssembler.createCriteriaToken(resultSet));
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
        return reportCriteriaTokens;
    }

}
