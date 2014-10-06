package com.myschool.exim.dao;

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
import com.myschool.exim.assembler.UploadTrackerDataAssembler;
import com.myschool.exim.constants.UploadStatus;
import com.myschool.exim.dto.UploadFileTrackerDto;
import com.myschool.exim.dto.UploadRecordTrackerDto;
import com.myschool.exim.dto.UploadTrackerDto;
import com.myschool.infra.database.agent.DatabaseAgent;

/**
 * The Class UploadDaoImpl.
 */
@Repository
public class UploadDaoImpl implements UploadDao {

    /** The database agent. */
    @Autowired
    private DatabaseAgent databaseAgent;

    /* (non-Javadoc)
     * @see com.myschool.exim.dao.UploadDao#createUploadTracker(int)
     */
    @Override
    public int createUploadTracker(int userId) throws DaoException {
        int nextId = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = databaseAgent.getConnection();
            String query = UploadDaoSql.buildInsertUploadTrackerSql();
            preparedStatement = connection.prepareStatement(query);
            nextId = databaseAgent.getNextId("UPLOAD_TRACKER", "TRACKER_ID");
            preparedStatement.setInt(1, nextId);
            preparedStatement.setInt(2, UploadStatus.NOT_STARTED.getStatusId());
            preparedStatement.setInt(3, userId);
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
     * @see com.myschool.exim.dao.UploadDao#updateUploadTracker(int, com.myschool.exim.dto.UploadTrackerDto)
     */
    @Override
    public boolean updateUploadTracker(int trackerId,
            UploadTrackerDto uploadTracker) throws DaoException {
        boolean updated = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = databaseAgent.getConnection();
            String query = UploadDaoSql.buildUpdateUploadTrackerSql();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, uploadTracker.getUploadStatus().getStatusId());
            preparedStatement.setInt(2, uploadTracker.getProcessedFiles());
            preparedStatement.setInt(3, uploadTracker.getTotalFiles());
            preparedStatement.setInt(4, trackerId);
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
     * @see com.myschool.exim.dao.UploadDao#getUploadTracker(int)
     */
    @Override
    public UploadTrackerDto getUploadTracker(int trackerId) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        UploadTrackerDto uploadTracker = null;

        try {
            connection = databaseAgent.getConnection();
            String query = UploadDaoSql.buildSelectUploadTrackerSql(trackerId);
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                uploadTracker = UploadTrackerDataAssembler.createUploadTracker(resultSet);
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
        return uploadTracker;
    }

    /* (non-Javadoc)
     * @see com.myschool.exim.dao.UploadDao#getUploadTrackers()
     */
    @Override
    public List<UploadTrackerDto> getUploadTrackers() throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<UploadTrackerDto> uploadTrackers = null;

        try {
            connection = databaseAgent.getConnection();
            String query = UploadDaoSql.buildSelectUploadTrackersSql();
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                if (uploadTrackers == null) {
                    uploadTrackers = new ArrayList<UploadTrackerDto>();
                }
                uploadTrackers.add(UploadTrackerDataAssembler.createUploadTracker(resultSet));
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
        return uploadTrackers;
    }

    /* (non-Javadoc)
     * @see com.myschool.exim.dao.UploadDao#getUploadTrackers(int)
     */
    @Override
    public List<UploadTrackerDto> getUploadTrackers(int userId) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<UploadTrackerDto> uploadTrackers = null;

        try {
            connection = databaseAgent.getConnection();
            String query = UploadDaoSql.buildSelectUploadTrackersSql(userId);
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                if (uploadTrackers == null) {
                    uploadTrackers = new ArrayList<UploadTrackerDto>();
                }
                uploadTrackers.add(UploadTrackerDataAssembler.createUploadTracker(resultSet));
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
        return uploadTrackers;
    }

    /* (non-Javadoc)
     * @see com.myschool.exim.dao.UploadDao#createUploadFileTracker(int, com.myschool.exim.dto.UploadFileTrackerDto)
     */
    @Override
    public int createUploadFileTracker(int trackerId,
            UploadFileTrackerDto uploadFileTracker) throws DaoException {
        int nextId = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = databaseAgent.getConnection();
            String query = UploadDaoSql.buildInsertUploadFileTrackerSql();
            preparedStatement = connection.prepareStatement(query);
            nextId = databaseAgent.getNextId("UPLOAD_FILE_TRACKER", "UPLOAD_FILE_ID");
            preparedStatement.setInt(1, nextId);
            preparedStatement.setInt(2, trackerId);
            preparedStatement.setString(3, uploadFileTracker.getUploadType());
            preparedStatement.setString(4, uploadFileTracker.getFileName());
            preparedStatement.setInt(5, UploadStatus.NOT_STARTED.getStatusId());
            preparedStatement.setInt(6, uploadFileTracker.getProcessedRecords());
            preparedStatement.setInt(7, uploadFileTracker.getTotalRecords());
            preparedStatement.setString(8, uploadFileTracker.getRemarks());
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
     * @see com.myschool.exim.dao.UploadDao#updateUploadFileTracker(int, com.myschool.exim.dto.UploadFileTrackerDto)
     */
    @Override
    public boolean updateUploadFileTracker(int fileTrackerId,
            UploadFileTrackerDto uploadFileTracker) throws DaoException {
        boolean updated = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = databaseAgent.getConnection();
            String query = UploadDaoSql.buildUpdateUploadFileTrackerSql();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, uploadFileTracker.getUploadStatus().getStatusId());
            preparedStatement.setTimestamp(2, uploadFileTracker.getUploadStartTime());
            preparedStatement.setTimestamp(3, uploadFileTracker.getUploadEndTime());
            preparedStatement.setInt(4, uploadFileTracker.getProcessedRecords());
            preparedStatement.setInt(5, uploadFileTracker.getTotalRecords());
            preparedStatement.setString(6, uploadFileTracker.getRemarks());
            preparedStatement.setInt(7, fileTrackerId);
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
     * @see com.myschool.exim.dao.UploadDao#getUploadFileTracker(int)
     */
    @Override
    public UploadFileTrackerDto getUploadFileTracker(int fileTrackerId)
            throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        UploadFileTrackerDto uploadFileTracker = null;

        try {
            connection = databaseAgent.getConnection();
            String query = UploadDaoSql.buildSelectUploadFileTrackerSql(fileTrackerId);
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                uploadFileTracker = UploadTrackerDataAssembler.createUploadFileTracker(resultSet);
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
        return uploadFileTracker;
    }

    /* (non-Javadoc)
     * @see com.myschool.exim.dao.UploadDao#createUploadRecordTracker(int, com.myschool.exim.dto.UploadRecordTrackerDto)
     */
    @Override
    public int createUploadRecordTracker(int fileTrackerId,
            UploadRecordTrackerDto uploadRecordTracker) throws DaoException {
        int nextId = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = databaseAgent.getConnection();
            String query = UploadDaoSql.buildInsertUploadRecordTrackerSql();
            preparedStatement = connection.prepareStatement(query);
            nextId = databaseAgent.getNextId("UPLOAD_RECORD_TRACKER", "UPLOAD_RECORD_ID");
            preparedStatement.setInt(1, nextId);
            preparedStatement.setInt(2, fileTrackerId);
            preparedStatement.setInt(3, uploadRecordTracker.getRecordNumber());
            preparedStatement.setString(4, uploadRecordTracker.getRecordData());
            preparedStatement.setInt(5, uploadRecordTracker.getUploadStatus().getStatusId());
            preparedStatement.setString(6, uploadRecordTracker.getRemarks());
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
     * @see com.myschool.exim.dao.UploadDao#updateUploadRecordTracker(int, com.myschool.exim.dto.UploadRecordTrackerDto)
     */
    @Override
    public boolean updateUploadRecordTracker(int recordTrackerId,
            UploadRecordTrackerDto uploadRecordTracker) throws DaoException {

        boolean updated = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = databaseAgent.getConnection();
            String query = UploadDaoSql.buildUpdateUploadRecordTrackerSql();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, uploadRecordTracker.getUploadStatus().getStatusId());
            preparedStatement.setString(2, uploadRecordTracker.getRemarks());
            preparedStatement.setInt(3, recordTrackerId);
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
     * @see com.myschool.exim.dao.UploadDao#getUploadRecordTracker(int)
     */
    @Override
    public UploadRecordTrackerDto getUploadRecordTracker(int recordTrackerId)
            throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        UploadRecordTrackerDto uploadRecordTracker = null;

        try {
            connection = databaseAgent.getConnection();
            String query = UploadDaoSql.buildSelectUploadRecordTrackerSql(recordTrackerId);
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                uploadRecordTracker = UploadTrackerDataAssembler.createUploadRecordTracker(resultSet);
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
        return uploadRecordTracker;
    }

    /* (non-Javadoc)
     * @see com.myschool.exim.dao.UploadDao#getUploadFileTrackerByTracker(int)
     */
    @Override
    public List<UploadFileTrackerDto> getUploadFileTrackerByTracker(
            int trackerId) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<UploadFileTrackerDto> uploadFileTrackers = null;

        try {
            connection = databaseAgent.getConnection();
            String query = UploadDaoSql.buildSelectUploadFileTrackersSql(trackerId);
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                if (uploadFileTrackers == null) {
                    uploadFileTrackers = new ArrayList<UploadFileTrackerDto>();
                }
                uploadFileTrackers.add(UploadTrackerDataAssembler.createUploadFileTracker(resultSet));
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
        return uploadFileTrackers;
    }

    /* (non-Javadoc)
     * @see com.myschool.exim.dao.UploadDao#getUploadRecordTrackerByFileTracker(int)
     */
    @Override
    public List<UploadRecordTrackerDto> getUploadRecordTrackerByFileTracker(
            int fileTrackerId) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<UploadRecordTrackerDto> uploadRecordTrackers = null;

        try {
            connection = databaseAgent.getConnection();
            String query = UploadDaoSql.buildSelectUploadRecordTrackersSql(fileTrackerId);
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                if (uploadRecordTrackers == null) {
                    uploadRecordTrackers = new ArrayList<UploadRecordTrackerDto>();
                }
                uploadRecordTrackers.add(UploadTrackerDataAssembler.createUploadRecordTracker(resultSet));
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
        return uploadRecordTrackers;
    }

}
