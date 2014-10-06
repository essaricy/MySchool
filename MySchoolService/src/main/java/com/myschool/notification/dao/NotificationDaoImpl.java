package com.myschool.notification.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.myschool.common.exception.ConnectionException;
import com.myschool.common.exception.DaoException;
import com.myschool.infra.database.agent.DatabaseAgent;
import com.myschool.notification.assembler.NotificationDataAssembler;
import com.myschool.notification.constants.NotificationEndPoint;
import com.myschool.notification.constants.NotificationStatus;
import com.myschool.notification.dto.NotificationCriteriaDto;
import com.myschool.notification.dto.NotificationDetailDto;
import com.myschool.notification.dto.NotificationDto;

/**
 * The Class NotificationDaoImpl.
 */
@Repository
public class NotificationDaoImpl implements NotificationDao {

    /** The database agent. */
    @Autowired
    private DatabaseAgent databaseAgent;

    /* (non-Javadoc)
     * @see com.myschool.notification.dao.NotificationDao#getAll(java.util.List)
     */
    @Override
    public List<NotificationDto> getAll(List<NotificationEndPoint> notificationEndPoints) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        NotificationDto notification = null;
        List<NotificationDto> notifications = null;

        try {
            connection = databaseAgent.getConnection();
            String query = NotificationDaoSql.buildSelectSql(notificationEndPoints);
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                notification = NotificationDataAssembler.createNotification(resultSet);
                if (notifications == null) {
                    notifications = new ArrayList<NotificationDto>();
                }
                notifications.add(notification);
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
        return notifications;
    }

    /* (non-Javadoc)
     * @see com.myschool.notification.dao.NotificationDao#get(int)
     */
    @Override
    public NotificationDto get(int notificationId) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        NotificationDto notification = null;

        try {
            connection = databaseAgent.getConnection();
            preparedStatement = connection.prepareStatement(NotificationDaoSql.SELECT_BY_ID);
            preparedStatement.setInt(1, notificationId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                notification = NotificationDataAssembler.createNotification(resultSet);
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
        return notification;
    }

    /* (non-Javadoc)
     * @see com.myschool.notification.dao.NotificationDao#createNotificationHeader(com.myschool.notification.dto.NotificationDto)
     */
    @Override
    public int createNotificationHeader(NotificationDto notification) throws DaoException {
        int nextId = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = databaseAgent.getConnection();
            String query = NotificationDaoSql.buildNotificationHeaderSql();
            preparedStatement = connection.prepareStatement(query);
            nextId = databaseAgent.getNextId("NOTIFICATION_HEADER", "HEADER_ID");
            preparedStatement.setInt(1, nextId);
            preparedStatement.setInt(2, notification.getNotificationEndPoint().getId());
            preparedStatement.setInt(3, notification.getNotificationMode().getId());
            preparedStatement.setInt(4, notification.getNotificationType().getId());
            preparedStatement.setString(5, NotificationStatus.NEW.toString());
            preparedStatement.setInt(6, 1); // TODO this is a place holder for now.
            preparedStatement.setDate(7, new Date(new java.util.Date().getTime()));
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
     * @see com.myschool.notification.dao.NotificationDao#createNotificationDetail(int, java.util.List)
     */
    @Override
    public boolean createNotificationDetail(int headerId,
            List<Integer> notifyingIds) throws DaoException {
        boolean successful = false;
        int nextId = 0;
        String notificationDetailSql = null;
        Connection connection = null;
        Statement statement = null;

        try {
            if (notifyingIds != null) {
                connection = databaseAgent.getConnection();
                statement = connection.createStatement();

                for (Integer notifyingId : notifyingIds) {
                    if (nextId == 0) {
                        nextId = databaseAgent.getNextId("NOTIFICATION_DETAIL", "DETAIL_ID");
                    } else {
                        nextId++;
                    }
                    notificationDetailSql = NotificationDaoSql.buildNotificationDetailSql(nextId, headerId, notifyingId);
                    statement.addBatch(notificationDetailSql);
                }
                statement.executeBatch();
                successful = true;
            }
        } catch (SQLException sqlException) {
            throw new DaoException(sqlException.getMessage(), sqlException);
        } catch (ConnectionException connectionException) {
            throw new DaoException(connectionException.getMessage(),
                    connectionException);
        } finally {
            try {
                databaseAgent.releaseResources(connection, statement);
            } catch (ConnectionException connectionException) {
                throw new DaoException(connectionException.getMessage(),
                        connectionException);
            }
        }
        return successful;
    }
    
    /* (non-Javadoc)
     * @see com.myschool.notification.dao.NotificationDao#getNotificationHeaderData(com.myschool.notification.dto.NotificationStatus[])
     */
    public List<NotificationDto> getNotificationHeaderData(NotificationStatus[] notificationStatus) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        NotificationDto notification = null;
        List<NotificationDto> notifications = null;

        try {
            connection = databaseAgent.getConnection();
            String query = NotificationDaoSql.buildSelectNotificationHeaderSql(notificationStatus);
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                notification = NotificationDataAssembler.createNotificationHeaderDto(resultSet);
                if (notifications == null) {
                    notifications = new ArrayList<NotificationDto>();
                }
                notifications.add(notification);
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
        return notifications;
    }

    /* (non-Javadoc)
     * @see com.myschool.notification.dao.NotificationDao#getNotificationDetailData(int, com.myschool.notification.dto.NotificationStatus[])
     */
    @Override
    public List<NotificationDetailDto> getNotificationDetailData(int headerId,
            NotificationStatus[] notificationStatus) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        NotificationDetailDto notificationDetail = null;
        List<NotificationDetailDto> notificationDetailList = null;

        try {
            connection = databaseAgent.getConnection();
            String query = NotificationDaoSql.buildSelectNotificationDetailSql(
                    headerId, notificationStatus);
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                notificationDetail = NotificationDataAssembler
                        .createNotificationDetailDto(resultSet);
                if (notificationDetailList == null) {
                    notificationDetailList = new ArrayList<NotificationDetailDto>();
                }
                notificationDetailList.add(notificationDetail);
            }
        } catch (SQLException sqlException) {
            throw new DaoException(sqlException.getMessage(), sqlException);
        } catch (ConnectionException connectionException) {
            throw new DaoException(connectionException.getMessage(),
                    connectionException);
        } finally {
            try {
                databaseAgent.releaseResources(connection, preparedStatement,
                        resultSet);
            } catch (ConnectionException connectionException) {
                throw new DaoException(connectionException.getMessage(),
                        connectionException);
            }
        }
        return notificationDetailList;
    }

    /* (non-Javadoc)
     * @see com.myschool.notification.dao.NotificationDao#updateNotificationHeaderStatus(int, java.lang.String)
     */
    @Override
    public boolean updateNotificationHeaderStatus(int headerId, String status)
            throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        boolean result = false;

        try {
            connection = databaseAgent.getConnection();
            String query = NotificationDaoSql
                    .buildUpdateNotificationHeaderStatusSql(headerId, status);
            preparedStatement = connection.prepareStatement(query);
            result = preparedStatement.execute();
        } catch (SQLException sqlException) {
            throw new DaoException(sqlException.getMessage(), sqlException);
        } catch (ConnectionException connectionException) {
            throw new DaoException(connectionException.getMessage(),
                    connectionException);
        } finally {
            try {
                databaseAgent.releaseResources(connection, preparedStatement,
                        resultSet);
            } catch (ConnectionException connectionException) {
                throw new DaoException(connectionException.getMessage(),
                        connectionException);
            }
        }
        return result;
    }

    /* (non-Javadoc)
     * @see com.myschool.notification.dao.NotificationDao#updateNotificationDetailStatus(int, java.lang.String)
     */
    @Override
    public boolean updateNotificationDetailStatus(int detailId, String status)
            throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        boolean result = false;

        try {
            connection = databaseAgent.getConnection();
            String query = NotificationDaoSql
                    .buildUpdateNotificationDetailStatusSql(detailId, status);
            preparedStatement = connection.prepareStatement(query);
            result = preparedStatement.execute();
        } catch (SQLException sqlException) {
            throw new DaoException(sqlException.getMessage(), sqlException);
        } catch (ConnectionException connectionException) {
            throw new DaoException(connectionException.getMessage(),
                    connectionException);
        } finally {
            try {
                databaseAgent.releaseResources(connection, preparedStatement,
                        resultSet);
            } catch (ConnectionException connectionException) {
                throw new DaoException(connectionException.getMessage(),
                        connectionException);
            }
        }
        return result;
    }

    /* (non-Javadoc)
     * @see com.myschool.notification.dao.NotificationDao#getAll(com.myschool.notification.dto.NotificationCriteriaDto)
     */
    @Override
    public List<NotificationDto> getAll(
            NotificationCriteriaDto notificationCriteria) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        NotificationDto notification = null;
        List<NotificationDto> notifications = null;

        try {
            connection = databaseAgent.getConnection();
            String query = NotificationDaoSql.buildSelectSql(notificationCriteria);
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                notification = NotificationDataAssembler.createNotification(resultSet);
                if (notifications == null) {
                    notifications = new ArrayList<NotificationDto>();
                }
                notifications.add(notification);
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
        return notifications;
    }

}
