package com.myschool.branch.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.myschool.branch.assembler.BranchDataAssembler;
import com.myschool.branch.dto.BranchDto;
import com.myschool.common.exception.ConnectionException;
import com.myschool.common.exception.DaoException;
import com.myschool.infra.database.agent.DatabaseAgent;

/**
 * The Class BranchDaoImpl.
 */
@Repository
public class BranchDaoImpl implements BranchDao {

    /** The database agent. */
    @Autowired
    private DatabaseAgent databaseAgent;

    /* (non-Javadoc)
     * @see com.myschool.branch.dao.BranchDao#create(com.myschool.branch.dto.BranchDto)
     */
    @Override
    public int create(BranchDto branchDto) throws DaoException {
        int nextId = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(BranchDaoSql.INSERT);
            nextId = databaseAgent.getNextId("BRANCH", "BRANCH_ID");
            preparedStatement.setInt(1, nextId);
            preparedStatement.setString(2, branchDto.getBranchCode());
            preparedStatement.setString(3, branchDto.getDescription());
            preparedStatement.setString(4, branchDto.getAddress());
            preparedStatement.setInt(5, branchDto.getRegion().getRegionId());
            preparedStatement.setString(6, branchDto.getPhoneNumber());
            preparedStatement.setString(7, branchDto.getEmailId());
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
     * @see com.myschool.branch.dao.BranchDao#delete(int)
     */
    @Override
    public boolean delete(int branchId) throws DaoException {
        boolean deleted = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(BranchDaoSql.DELETE);
            preparedStatement.setInt(1, branchId);
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
     * @see com.myschool.branch.dao.BranchDao#get(int)
     */
    @Override
    public BranchDto get(int branchId) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        BranchDto branch = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(BranchDaoSql.SELECT_BY_ID);
            preparedStatement.setInt(1, branchId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                branch = BranchDataAssembler.create(resultSet);
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
        return branch;
    }

    /* (non-Javadoc)
     * @see com.myschool.branch.dao.BranchDao#getAll()
     */
    @Override
    public List<BranchDto> getAll() throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        BranchDto branch = null;
        List<BranchDto> branches = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(BranchDaoSql.SELECT_ALL);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                branch = BranchDataAssembler.create(resultSet);
                if (branches == null) {
                    branches = new ArrayList<BranchDto>();
                }
                branches.add(branch);
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
        return branches;
    }

    /* (non-Javadoc)
     * @see com.myschool.branch.dao.BranchDao#updateBranch(int, com.myschool.branch.dto.BranchDto)
     */
    @Override
    public boolean updateBranch(int branchId, BranchDto branchDto) throws DaoException {
        boolean updated = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(BranchDaoSql.UPDATE);
            preparedStatement.setString(1, branchDto.getBranchCode());
            preparedStatement.setString(2, branchDto.getDescription());
            preparedStatement.setString(3, branchDto.getAddress());
            preparedStatement.setInt(4, branchDto.getRegion().getRegionId());
            preparedStatement.setString(5, branchDto.getPhoneNumber());
            preparedStatement.setString(6, branchDto.getEmailId());
            preparedStatement.setInt(7, branchId);
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
     * @see com.myschool.branch.dao.BranchDao#get(java.lang.String)
     */
    @Override
    public BranchDto get(String branchCode) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        BranchDto branch = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(BranchDaoSql.SELECT_BY_BRANCH_CODE);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                branch = BranchDataAssembler.create(resultSet);
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
        return branch;
    }

    /* (non-Javadoc)
     * @see com.myschool.branch.dao.BranchDao#getAll(com.myschool.branch.dto.BranchDto)
     */
    @Override
    public List<BranchDto> getAll(BranchDto branchCriteria) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        BranchDto branch = null;
        List<BranchDto> branches = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(BranchDaoSql.getBranchSearchQuery(branchCriteria));
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                branch = BranchDataAssembler.create(resultSet);
                if (branches == null) {
                    branches = new ArrayList<BranchDto>();
                }
                branches.add(branch);
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
        return branches;
    }

    /* (non-Javadoc)
     * @see com.myschool.branch.dao.BranchDao#getByRegion(int)
     */
    @Override
    public List<BranchDto> getByRegion(int regionId) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        BranchDto branch = null;
        List<BranchDto> branches = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(BranchDaoSql.SELECT_BY_REGION_ID);
            preparedStatement.setInt(1, regionId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                branch = BranchDataAssembler.create(resultSet);
                if (branches == null) {
                    branches = new ArrayList<BranchDto>();
                }
                branches.add(branch);
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
        return branches;
    }

}
