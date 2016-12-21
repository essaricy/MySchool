package com.myschool.branch.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.myschool.branch.assembler.RegionDataAssembler;
import com.myschool.branch.dto.RegionDto;
import com.myschool.common.exception.ConnectionException;
import com.myschool.common.exception.DaoException;
import com.myschool.infra.database.agent.DatabaseAgent;

/**
 * The Class RegionDaoImpl.
 */
@Repository
public class RegionDaoImpl implements RegionDao {

    /** The database agent. */
    @Autowired
    private DatabaseAgent databaseAgent;

    /* (non-Javadoc)
     * @see com.myschool.branch.dao.RegionDao#deleteRegion(int)
     */
    @Override
    public boolean deleteRegion(int regionId) throws DaoException {
        return false;
    }

    /* (non-Javadoc)
     * @see com.myschool.branch.dao.RegionDao#get(int)
     */
    @Override
    public RegionDto get(int regionId) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        RegionDto region = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(RegionDaoSql.SELECT_BY_ID);
            preparedStatement.setInt(1, regionId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                region = RegionDataAssembler.create(resultSet);
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
        return region;
    }

    /* (non-Javadoc)
     * @see com.myschool.branch.dao.RegionDao#getAll()
     */
    @Override
    public List<RegionDto> getAll() throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        RegionDto region = null;
        List<RegionDto> regions = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(RegionDaoSql.SELECT_ALL);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                region = RegionDataAssembler.create(resultSet);
                if (regions == null) {
                    regions = new ArrayList<RegionDto>();
                }
                regions.add(region);
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
        return regions;
    }

    /* (non-Javadoc)
     * @see com.myschool.branch.dao.RegionDao#updateRegion(int, com.myschool.branch.dto.RegionDto)
     */
    @Override
    public boolean updateRegion(int regionId, RegionDto regionDto)
            throws DaoException {
        return false;
    }

    /* (non-Javadoc)
     * @see com.myschool.branch.dao.RegionDao#get(java.lang.String)
     */
    @Override
    public RegionDto get(String regionName) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        RegionDto region = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(RegionDaoSql.SELECT_BY_REGION_NAME);
            preparedStatement.setString(1, regionName);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                region = RegionDataAssembler.create(resultSet);
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
        return region;
    }

    /* (non-Javadoc)
     * @see com.myschool.branch.dao.RegionDao#create(java.lang.String)
     */
    @Override
    public int create(String regionName) throws DaoException {
        int nextId = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = databaseAgent.getConnection();
            nextId = databaseAgent.getNextId("REF_REGION", "REGION_ID");
            preparedStatement = connection.prepareStatement(RegionDaoSql.INSERT);
            preparedStatement.setInt(1, nextId);
            preparedStatement.setString(2, regionName);
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
     * @see com.myschool.branch.dao.RegionDao#getByState(int)
     */
    public List<RegionDto> getByState(int stateId) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        RegionDto region = null;
        List<RegionDto> regions = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(RegionDaoSql.SELECT_BY_STATE_ID);
            preparedStatement.setInt(1, stateId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                region = RegionDataAssembler.create(resultSet);
                if (regions == null) {
                    regions = new ArrayList<RegionDto>();
                }
                regions.add(region);
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
        return regions;
    }

}
