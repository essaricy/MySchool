package com.myschool.notification.dao;

import java.util.ArrayList;
import java.util.List;

import com.myschool.common.util.DatabaseUtil;
import com.myschool.common.util.StringUtil;
import com.myschool.notification.constants.NotificationEndPoint;
import com.myschool.notification.constants.NotificationStatus;
import com.myschool.notification.dto.NotificationCriteriaDto;

/**
 * The Class NotificationDaoSql.
 */
public class NotificationDaoSql {

    /** The Constant SELECT_ALL. */
    public static final String SELECT_ALL;

    /** The Constant SELECT_BY_ID. */
    public static final String SELECT_BY_ID;

    static {
        StringBuffer buffer = new StringBuffer();
        buffer.append("SELECT "); 
        buffer.append("NOTIFICATION_TEMPLATE.TEMPLATE_ID, ");
        buffer.append("NOTIFICATION_TEMPLATE.END_POINT_ID, ");
        buffer.append("NOTIFICATION_TEMPLATE.MODE_ID, ");
        buffer.append("NOTIFICATION_TEMPLATE.TYPE_ID, ");
        buffer.append("NOTIFICATION_END_POINT.END_POINT_NAME, ");
        buffer.append("NOTIFICATION_MODE.MODE_NAME, ");
        buffer.append("NOTIFICATION_TYPE.TYPE_NAME ");
        buffer.append("FROM NOTIFICATION_TEMPLATE ");
        buffer.append("INNER JOIN NOTIFICATION_END_POINT ");
        buffer.append("ON NOTIFICATION_END_POINT.END_POINT_ID = NOTIFICATION_TEMPLATE.END_POINT_ID ");
        buffer.append("INNER JOIN NOTIFICATION_MODE ");
        buffer.append("ON NOTIFICATION_MODE.MODE_ID = NOTIFICATION_TEMPLATE.MODE_ID ");
        buffer.append("INNER JOIN NOTIFICATION_TYPE ");
        buffer.append("ON NOTIFICATION_TYPE.TYPE_ID = NOTIFICATION_TEMPLATE.TYPE_ID ");
        SELECT_ALL = buffer.toString();
        buffer.setLength(0);

        buffer.append(SELECT_ALL);
        buffer.append("WHERE NOTIFICATION_TEMPLATE.TEMPLATE_ID=?");
        SELECT_BY_ID = buffer.toString();
        buffer.setLength(0);
    }

    /**
     * Builds the select sql.
     *
     * @param notificationEndPoints the notification end points
     * @return the string
     */
    public static String buildSelectSql(List<NotificationEndPoint> notificationEndPoints) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(SELECT_ALL);
        if (notificationEndPoints != null && !notificationEndPoints.isEmpty()) {
            List<Object> objects = new ArrayList<Object>();
            for (NotificationEndPoint notificationEndPoint : notificationEndPoints) {
                objects.add(notificationEndPoint.getValue());
            }
            buffer.append("WHERE NOTIFICATION_END_POINT.END_POINT_NAME IN (").append(DatabaseUtil.buildWhereInClause(objects)).append(")");
        }
        buffer.append(" ORDER BY NOTIFICATION_END_POINT.END_POINT_NAME, NOTIFICATION_MODE.MODE_NAME, NOTIFICATION_TYPE.TYPE_NAME ");
        return buffer.toString();
    }


    /**
     * Builds the notification header sql.
     *
     * @return the string
     */
    public static String buildNotificationHeaderSql() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("INSERT INTO NOTIFICATION_HEADER ( ");
        buffer.append("HEADER_ID, END_POINT_ID, MODE_ID, TYPE_ID, STATUS_CODE, REQUESTED_USER_ID, REQUESTED_DATE) VALUES (?, ?, ?, ?, ?, ?, ?) "); 
        return buffer.toString();
    }

    /**
     * Builds the notification detail sql.
     *
     * @param detailId the detail id
     * @param headerId the header id
     * @param notifyingId the notifying id
     * @return the string
     */
    public static String buildNotificationDetailSql(int detailId, int headerId,
            Integer notifyingId) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("INSERT INTO NOTIFICATION_DETAIL ( ");
        buffer.append("DETAIL_ID, HEADER_ID, SEND_TO_ID, STATUS_CODE) VALUES (");
        buffer.append(detailId).append(", ");
        buffer.append(headerId).append(", ");
        buffer.append(notifyingId).append(", ");
        buffer.append("'").append(NotificationStatus.NEW.toString()).append("') ");
        return buffer.toString();
    }

	/**
     * Builds the select notification header sql.
     * 
     * @param notificationStatus the notification status
     * @return the string
     */
	public static String buildSelectNotificationHeaderSql(NotificationStatus[] notificationStatus) {
		StringBuffer buffer = new StringBuffer();
        buffer.append("SELECT *,");
        buffer.append("NOTIFICATION_END_POINT.END_POINT_NAME, ");
        buffer.append("NOTIFICATION_MODE.MODE_NAME, ");
        buffer.append("NOTIFICATION_TYPE.TYPE_NAME ");
        buffer.append("FROM  NOTIFICATION_HEADER "); 
        buffer.append("INNER JOIN NOTIFICATION_END_POINT ");
        buffer.append("ON NOTIFICATION_END_POINT.END_POINT_ID = NOTIFICATION_HEADER.END_POINT_ID ");
        buffer.append("INNER JOIN NOTIFICATION_MODE ");
        buffer.append("ON NOTIFICATION_MODE.MODE_ID = NOTIFICATION_HEADER.MODE_ID ");
        buffer.append("INNER JOIN NOTIFICATION_TYPE ");
        buffer.append("ON NOTIFICATION_TYPE.TYPE_ID = NOTIFICATION_HEADER.TYPE_ID ");
        if (notificationStatus != null) {
			buffer.append("WHERE STATUS_CODE IN ( ").append(
					getStatusCode(notificationStatus)).append(")");
		}
       
        return buffer.toString();
	}

	/**
     * Gets the status code.
     * 
     * @param notificationStatus the notification status
     * @return the status code
     */
	private static StringBuffer getStatusCode(NotificationStatus[] notificationStatus) {
		boolean firstValue=true;
		StringBuffer statusCodes = new StringBuffer(50);
		for (int index=0;index<notificationStatus.length;index++){
			if(firstValue){
				statusCodes.append("'").append(notificationStatus[index]).append("'");
				firstValue=false;
			} else{
				statusCodes.append(",'").append(notificationStatus[index]).append("'");
			}
		}
		return statusCodes;
	}

	/**
     * Builds the select notification detail sql.
     * 
     * @param headerId the header id
     * @param notificationStatus the notification status
     * @return the string
     */
	public static String buildSelectNotificationDetailSql(int headerId, NotificationStatus[] notificationStatus) {
		StringBuffer buffer = new StringBuffer();
        buffer.append("SELECT * FROM  NOTIFICATION_DETAIL "); 
        buffer.append("WHERE HEADER_ID = ").append(headerId);
        if (notificationStatus != null) {
			buffer.append(" AND STATUS_CODE IN ( ").append(
					getStatusCode(notificationStatus)).append(")");
		}
        return buffer.toString();
	}

	/**
     * Builds the update notification header status sql.
     * 
     * @param headerId the header id
     * @param status the status
     * @return the string
     */
	public static String buildUpdateNotificationHeaderStatusSql(int headerId,
			String status) {
		StringBuffer buffer = new StringBuffer();
        buffer.append("UPDATE NOTIFICATION_HEADER "); 
        buffer.append("SET STATUS_CODE = '").append(status); 
        buffer.append("' WHERE HEADER_ID = "+headerId);
        return buffer.toString();
	}

	/**
     * Builds the update notification detail status sql.
     * 
     * @param detailId the detail id
     * @param status the status
     * @return the string
     */
	public static String buildUpdateNotificationDetailStatusSql(int detailId,
			String status) {
		StringBuffer buffer = new StringBuffer();
        buffer.append("UPDATE NOTIFICATION_DETAIL "); 
        buffer.append("SET STATUS_CODE = '").append(status); 
        buffer.append("' WHERE DETAIL_ID = "+detailId);
        
        return buffer.toString();
	}

    /**
     * Builds the select sql.
     * 
     * @param notificationCriteria the notification criteria
     * @return the string
     */
    public static String buildSelectSql(
            NotificationCriteriaDto notificationCriteria) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(SELECT_ALL);
        if (notificationCriteria != null) {
            String endPoint = notificationCriteria.getEndPoint();
            String mode = notificationCriteria.getMode();
            String type = notificationCriteria.getType();
            if (!StringUtil.isNullOrBlank(endPoint) || !StringUtil.isNullOrBlank(mode) || !StringUtil.isNullOrBlank(type)) {
                boolean whereAdded = false;
                buffer.append("WHERE ");
                if (!StringUtil.isNullOrBlank(endPoint)) {
                    buffer.append("NOTIFICATION_TEMPLATE.END_POINT_ID=").append(endPoint);
                    whereAdded = true;
                }
                if (!StringUtil.isNullOrBlank(mode)) {
                    if (whereAdded) {
                        buffer.append(" AND ");
                        whereAdded = true;
                    }
                    buffer.append("NOTIFICATION_TEMPLATE.MODE_ID=").append(mode);
                }
                if (!StringUtil.isNullOrBlank(type)) {
                    if (whereAdded) {
                        buffer.append(" AND ");
                        whereAdded = true;
                    }
                    buffer.append("NOTIFICATION_TEMPLATE.TYPE_ID=").append(type);
                }
            }
        }
        return buffer.toString();
    }

}
