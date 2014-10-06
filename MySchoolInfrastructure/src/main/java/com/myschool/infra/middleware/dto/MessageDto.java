package com.myschool.infra.middleware.dto;

import java.io.Serializable;
import java.util.List;

import com.myschool.application.dto.OrganizationProfileDto;
import com.myschool.common.dto.Person;
import com.myschool.infra.application.dto.MySchoolDto;
import com.myschool.notification.constants.NotificationEndPoint;
import com.myschool.notification.constants.NotificationMode;
import com.myschool.notification.constants.NotificationType;

/**
 * The Class MessageDto.
 */
public class MessageDto implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The notification end point. */
    private NotificationEndPoint notificationEndPoint;
    
    /** The notification mode. */
    private NotificationMode notificationMode;

    /** The notification type. */
    private NotificationType notificationType;

    /** The to person. */
    private Person toPerson;

    /** The copy to. */
    private List<Person> copyTo;

    /** The my school. */
    private MySchoolDto mySchool;

    /** The organization profile. */
    private OrganizationProfileDto organizationProfile;

    /** The content. */
    private Object content;

    /**
     * Gets the notification end point.
     * 
     * @return the notification end point
     */
    public NotificationEndPoint getNotificationEndPoint() {
        return notificationEndPoint;
    }

    /**
     * Sets the notification end point.
     * 
     * @param notificationEndPoint the new notification end point
     */
    public void setNotificationEndPoint(NotificationEndPoint notificationEndPoint) {
        this.notificationEndPoint = notificationEndPoint;
    }

    /**
     * Gets the notification mode.
     * 
     * @return the notification mode
     */
    public NotificationMode getNotificationMode() {
        return notificationMode;
    }

    /**
     * Sets the notification mode.
     * 
     * @param notificationMode the new notification mode
     */
    public void setNotificationMode(NotificationMode notificationMode) {
        this.notificationMode = notificationMode;
    }

    /**
     * Gets the notification type.
     *
     * @return the notification type
     */
    public NotificationType getNotificationType() {
        return notificationType;
    }

    /**
     * Sets the notification type.
     *
     * @param notificationType the new notification type
     */
    public void setNotificationType(NotificationType notificationType) {
        this.notificationType = notificationType;
    }

    /**
     * Gets the to person.
     *
     * @return the to person
     */
    public Person getToPerson() {
        return toPerson;
    }

    /**
     * Sets the to person.
     *
     * @param toPerson the new to person
     */
    public void setToPerson(Person toPerson) {
        this.toPerson = toPerson;
    }

    /**
     * Gets the copy to.
     *
     * @return the copy to
     */
    public List<Person> getCopyTo() {
        return copyTo;
    }

    /**
     * Sets the copy to.
     *
     * @param copyTo the new copy to
     */
    public void setCopyTo(List<Person> copyTo) {
        this.copyTo = copyTo;
    }

    /**
     * Gets the my school.
     * 
     * @return the my school
     */
    public MySchoolDto getMySchool() {
        return mySchool;
    }

    /**
     * Sets the my school.
     * 
     * @param mySchool the new my school
     */
    public void setMySchool(MySchoolDto mySchool) {
        this.mySchool = mySchool;
    }

    /**
     * Gets the organization profile.
     *
     * @return the organization profile
     */
    public OrganizationProfileDto getOrganizationProfile() {
        return organizationProfile;
    }

    /**
     * Sets the organization profile.
     *
     * @param organizationProfile the new organization profile
     */
    public void setOrganizationProfile(OrganizationProfileDto organizationProfile) {
        this.organizationProfile = organizationProfile;
    }

    /**
     * Gets the content.
     *
     * @return the content
     */
    public Object getContent() {
        return content;
    }

    /**
     * Sets the content.
     *
     * @param content the new content
     */
    public void setContent(Object content) {
        this.content = content;
    }

    /**
     * Constructs a <code>String</code> with all attributes
     * in name = value format.
     *
     * @return a <code>String</code> representation 
     * of this object.
     */
    public String toString() {
        final String SEPARATOR = ", ";
        StringBuilder retValue = new StringBuilder();
        retValue.append("MessageDto ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("notificationEndPoint = ").append(this.notificationEndPoint).append(SEPARATOR)
            .append("notificationMode = ").append(this.notificationMode).append(SEPARATOR)
            .append("notificationType = ").append(this.notificationType).append(SEPARATOR)
            .append("toPerson = ").append(this.toPerson).append(SEPARATOR)
            .append("copyTo = ").append(this.copyTo).append(SEPARATOR)
            .append("mySchool = ").append(this.mySchool).append(SEPARATOR)
            .append("organizationProfile = ").append(this.organizationProfile).append(SEPARATOR)
            .append("content = ").append(this.content).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}
