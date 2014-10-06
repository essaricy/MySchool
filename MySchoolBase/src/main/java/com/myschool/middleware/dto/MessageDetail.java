/*
 * 
 */
package com.myschool.middleware.dto;

import java.io.Serializable;

import com.myschool.middleware.constants.MessageEvent;
import com.myschool.notification.constants.NotificationMode;

/**
 * The Class MessageDetail.
 */
public class MessageDetail implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The message id. */
    private String messageId;

    /** The xml content. */
    private String xmlContent;

    /** The email subject. */
    private String emailSubject;

    /** The xsl template base name. */
    private String xslTemplateBaseName;

    /** The message event. */
    private MessageEvent messageEvent;

    /** The notification mode. */
    private NotificationMode notificationMode;

    /** The recipient. */
    private String recipient;

    /** The message tracker. */
    private MessageTracker messageTracker;

    /**
     * Gets the message id.
     *
     * @return the message id
     */
    public String getMessageId() {
        return messageId;
    }

    /**
     * Sets the message id.
     *
     * @param messageId the new message id
     */
    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    /**
     * Gets the xml content.
     *
     * @return the xml content
     */
    public String getXmlContent() {
        return xmlContent;
    }

    /**
     * Sets the xml content.
     *
     * @param xmlContent the new xml content
     */
    public void setXmlContent(String xmlContent) {
        this.xmlContent = xmlContent;
    }

    /**
     * Gets the email subject.
     *
     * @return the email subject
     */
    public String getEmailSubject() {
        return emailSubject;
    }

    /**
     * Sets the email subject.
     *
     * @param emailSubject the new email subject
     */
    public void setEmailSubject(String emailSubject) {
        this.emailSubject = emailSubject;
    }

    /**
     * Gets the xsl template base name.
     *
     * @return the xsl template base name
     */
    public String getXslTemplateBaseName() {
        return xslTemplateBaseName;
    }

    /**
     * Sets the xsl template base name.
     *
     * @param xslTemplateBaseName the new xsl template base name
     */
    public void setXslTemplateBaseName(String xslTemplateBaseName) {
        this.xslTemplateBaseName = xslTemplateBaseName;
    }

    /**
     * Gets the message event.
     *
     * @return the message event
     */
    public MessageEvent getMessageEvent() {
        return messageEvent;
    }

    /**
     * Sets the event name.
     *
     * @param messageEvent the new event name
     */
    public void setEventName(MessageEvent messageEvent) {
        this.messageEvent = messageEvent;
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
     * Gets the recipient.
     *
     * @return the recipient
     */
    public String getRecipient() {
        return recipient;
    }

    /**
     * Sets the recipient.
     *
     * @param recipient the new recipient
     */
    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    /**
     * Sets the message event.
     *
     * @param messageEvent the new message event
     */
    public void setMessageEvent(MessageEvent messageEvent) {
        this.messageEvent = messageEvent;
    }

    /**
     * Gets the message tracker.
     *
     * @return the message tracker
     */
    public MessageTracker getMessageTracker() {
        return messageTracker;
    }

    /**
     * Sets the message tracker.
     *
     * @param messageTracker the new message tracker
     */
    public void setMessageTracker(MessageTracker messageTracker) {
        this.messageTracker = messageTracker;
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
        retValue.append("MessageDetail ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("emailSubject = ").append(this.emailSubject).append(SEPARATOR)
            .append("messageEvent = ").append(this.messageEvent).append(SEPARATOR)
            .append("messageId = ").append(this.messageId).append(SEPARATOR)
            .append("messageTracker = ").append(this.messageTracker).append(SEPARATOR)
            .append("notificationMode = ").append(this.notificationMode).append(SEPARATOR)
            .append("recipient = ").append(this.recipient).append(SEPARATOR)
            .append("xmlContent = ").append(this.xmlContent).append(SEPARATOR)
            .append("xslTemplateBaseName = ").append(this.xslTemplateBaseName).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}
