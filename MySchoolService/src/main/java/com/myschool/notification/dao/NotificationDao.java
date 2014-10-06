package com.myschool.notification.dao;

import java.util.List;

import com.myschool.common.exception.DaoException;
import com.myschool.notification.constants.NotificationEndPoint;
import com.myschool.notification.constants.NotificationStatus;
import com.myschool.notification.dto.NotificationCriteriaDto;
import com.myschool.notification.dto.NotificationDetailDto;
import com.myschool.notification.dto.NotificationDto;

/**
 * The Interface NotificationDao.
 */
public interface NotificationDao {

    /**
     * Gets the all.
     *
     * @param notificationEndPoints the notification end points
     * @return the all
     * @throws DaoException the dao exception
     */
    List<NotificationDto> getAll(List<NotificationEndPoint> notificationEndPoints) throws DaoException;

    /**
     * Gets the.
     *
     * @param notificationId the notification id
     * @return the notification dto
     * @throws DaoException the dao exception
     */
    NotificationDto get(int notificationId) throws DaoException;

    /**
     * Creates the notification header.
     * 
     * @param notification the notification
     * @return the int
     * @throws DaoException the dao exception
     */
    int createNotificationHeader(NotificationDto notification) throws DaoException;

    /**
     * Creates the notification detail.
     * 
     * @param headerId the header id
     * @param notifyingIds the notifying ids
     * @return true, if successful
     * @throws DaoException the dao exception
     */
    boolean createNotificationDetail(int headerId, List<Integer> notifyingIds) throws DaoException;

    /**
     * Gets the notification header data.
     *
     * @param notificationStatus the notification status
     * @return the notification header data
     * @throws DaoException the dao exception
     */
    List<NotificationDto> getNotificationHeaderData(NotificationStatus[] notificationStatus)throws DaoException;

    /**
     * Gets the notification detail data.
     *
     * @param headerId the header id
     * @param notificationStatus the notification status
     * @return the notification detail data
     * @throws DaoException the dao exception
     */
    List<NotificationDetailDto> getNotificationDetailData(int headerId, NotificationStatus[] notificationStatus) throws DaoException;

    /**
     * Update notification header status.
     *
     * @param headerId the header id
     * @param status the status
     * @return true, if successful
     * @throws DaoException the dao exception
     */
    boolean updateNotificationHeaderStatus(int headerId, String status) throws DaoException;;

    /**
     * Update notification detail status.
     *
     * @param detailId the detail id
     * @param status the status
     * @return true, if successful
     * @throws DaoException the dao exception
     */
    boolean updateNotificationDetailStatus(int detailId, String status) throws DaoException;

    /**
     * Gets the all.
     * 
     * @param notificationCriteria the notification criteria
     * @return the all
     * @throws DaoException the dao exception
     */
    List<NotificationDto> getAll(NotificationCriteriaDto notificationCriteria) throws DaoException;

}
