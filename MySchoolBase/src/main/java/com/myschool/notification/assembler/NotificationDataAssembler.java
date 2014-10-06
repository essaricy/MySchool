package com.myschool.notification.assembler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.myschool.notification.constants.NotificationEndPoint;
import com.myschool.notification.constants.NotificationMode;
import com.myschool.notification.constants.NotificationType;
import com.myschool.notification.dto.NotificationCriteriaDto;
import com.myschool.notification.dto.NotificationDetailDto;
import com.myschool.notification.dto.NotificationDto;
import com.myschool.notification.dto.NotificationTemplateDto;

/**
 * The Class NotificationDataAssembler.
 */
public class NotificationDataAssembler {

    /**
     * Creates the notification.
     *
     * @param resultSet the result set
     * @return the notification dto
     * @throws SQLException the sQL exception
     */
    public static NotificationDto createNotification(ResultSet resultSet) throws SQLException {
        NotificationDto notificationDto = new NotificationDto();
        notificationDto.setNotificationId(resultSet.getInt("TEMPLATE_ID"));

        NotificationEndPoint endPoint = NotificationEndPoint.getByValue(resultSet.getString("END_POINT_NAME"));
        //endPoint.setId(resultSet.getInt("END_POINT_ID"));
        notificationDto.setNotificationEndPoint(endPoint);

        NotificationMode mode = NotificationMode.get(resultSet.getString("MODE_NAME"));
        //mode.setId(resultSet.getInt("MODE_ID"));
        notificationDto.setNotificationMode(mode);

        NotificationType type = NotificationType.getByValue(resultSet.getString("TYPE_NAME"));
        //type.setId(resultSet.getInt("TYPE_ID"));
        notificationDto.setNotificationType(type);

        return notificationDto;
    }

    /**
     * Creates the notification.
     *
     * @param notificationEndPoint the notification end point
     * @param notificationMode the notification mode
     * @param notificationType the notification type
     * @param notifyingList the notifying list
     * @return the notification dto
     */
    public static NotificationDto createNotification(NotificationEndPoint notificationEndPoint,
            NotificationMode notificationMode, NotificationType notificationType,
            List<Integer> notifyingList) {
        NotificationDto notificationDto = new NotificationDto();
        notificationDto.setNotificationEndPoint(notificationEndPoint);
        notificationDto.setNotificationMode(notificationMode);
        notificationDto.setNotificationType(notificationType);
        notificationDto.setNotifyingIds(notifyingList);
        return notificationDto;
    }

    /**
     * Creates the notification.
     *
     * @param notificationEndPoint the notification end point
     * @param notificationMode the notification mode
     * @param notificationType the notification type
     * @param studentId the student id
     * @return the notification dto
     */
    public static NotificationDto createNotification(NotificationEndPoint notificationEndPoint,
            NotificationMode notificationMode, NotificationType notificationType,
            int studentId) {
        List<Integer> notifyingList = new ArrayList<Integer>();
        notifyingList.add(studentId);
        return createNotification(notificationEndPoint, notificationMode, notificationType, notifyingList);
    }

    /**
     * Gets the defined notification template.
     *
     * @param definedNotifications the defined notifications
     * @param notification the notification
     * @return the defined notification template
     */
    public static NotificationTemplateDto getDefinedNotificationTemplate(
            List<NotificationTemplateDto> definedNotifications,
            NotificationDto notification) {
        NotificationTemplateDto notificationTemplate = null;
        if (definedNotifications != null && !definedNotifications.isEmpty()) {
            for (NotificationTemplateDto notificationTemplateDto : definedNotifications) {
                if (notificationTemplateDto != null) {
                    if (notificationTemplateDto.getNotificationEndPoint() == notification.getNotificationEndPoint()
                            && notificationTemplateDto.getNotificationMode() == notification.getNotificationMode()
                            && notificationTemplateDto.getNotificationType() == notification.getNotificationType()) {
                        notificationTemplate = notificationTemplateDto;
                        break;
                    }
                }
            }
        }
        return notificationTemplate;
    }

    /**
     * Creates the notification header dto.
     *
     * @param resultSet the result set
     * @return the notification dto
     * @throws SQLException the sQL exception
     */
    public static NotificationDto createNotificationHeaderDto(
            ResultSet resultSet) throws SQLException {
        NotificationDto notificationDto = new NotificationDto();
        notificationDto.setNotificationId(resultSet.getInt("HEADER_ID"));

        NotificationEndPoint endPoint = NotificationEndPoint.getByValue(resultSet.getString("END_POINT_NAME"));
        //endPoint.setId(resultSet.getInt("END_POINT_ID"));
        notificationDto.setNotificationEndPoint(endPoint);

        NotificationMode mode = NotificationMode.get(resultSet.getString("MODE_NAME"));
        //mode.setId(resultSet.getInt("MODE_ID"));
        notificationDto.setNotificationMode(mode);

        NotificationType type = NotificationType.getByValue(resultSet.getString("TYPE_NAME"));
        //type.setId(resultSet.getInt("TYPE_ID"));
        notificationDto.setNotificationType(type);

        
        return notificationDto;
    }

    /**
     * Creates the notification detail dto.
     *
     * @param resultSet the result set
     * @return the notification detail dto
     * @throws SQLException the sQL exception
     */
    public static NotificationDetailDto createNotificationDetailDto(
            ResultSet resultSet) throws SQLException{
        NotificationDetailDto notificationDetailDto = new NotificationDetailDto();
        notificationDetailDto.setNotificationId(resultSet.getInt("HEADER_ID"));
        notificationDetailDto.setNotificationDetailSendTo(resultSet.getInt("SEND_TO_ID"));
        // Set notificationStatus
        return notificationDetailDto;
   }

    /**
     * Creates the notification.
     * 
     * @param notificationCriteriaValue the notification criteria value
     * @return the notification criteria dto
     */
    public static NotificationCriteriaDto createNotification(JSONObject notificationCriteriaValue) {
        NotificationCriteriaDto notificationCriteria = null;
        if (notificationCriteriaValue != null) {
            notificationCriteria = new NotificationCriteriaDto();
            notificationCriteria.setEndPoint(notificationCriteriaValue.getString("EndPoint"));
            notificationCriteria.setMode(notificationCriteriaValue.getString("Mode"));
            notificationCriteria.setStatus(notificationCriteriaValue.getString("Status"));
            notificationCriteria.setType(notificationCriteriaValue.getString("Type"));
            notificationCriteria.setRequestDateMin(notificationCriteriaValue.getString("DateMin"));
            notificationCriteria.setRequestDateMax(notificationCriteriaValue.getString("DateMax"));
        }
        return notificationCriteria;
    }

}
