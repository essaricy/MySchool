package com.myschool.notification.dto;

import java.io.Serializable;
import java.util.List;

/**
 * The Class NotificationConfig.
 */
public class NotificationConfig implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The base dir. */
    private String baseDir;

    /** The templates base. */
    private String templatesBase;

    /** The samples base. */
    private String samplesBase;

    private List<NotificationTemplate> notificationTemplates;

    /**
     * Gets the base dir.
     *
     * @return the baseDir
     */
    public String getBaseDir() {
        return baseDir;
    }

    /**
     * Sets the base dir.
     *
     * @param baseDir the baseDir to set
     */
    public void setBaseDir(String baseDir) {
        this.baseDir = baseDir;
    }

    /**
     * Gets the templates base.
     *
     * @return the templatesBase
     */
    public String getTemplatesBase() {
        return templatesBase;
    }

    /**
     * Sets the templates base.
     *
     * @param templatesBase the templatesBase to set
     */
    public void setTemplatesBase(String templatesBase) {
        this.templatesBase = templatesBase;
    }

    /**
     * Gets the samples base.
     *
     * @return the samplesBase
     */
    public String getSamplesBase() {
        return samplesBase;
    }

    /**
     * Sets the samples base.
     *
     * @param samplesBase the samplesBase to set
     */
    public void setSamplesBase(String samplesBase) {
        this.samplesBase = samplesBase;
    }

    /**
     * @return the notificationTemplates
     */
    public List<NotificationTemplate> getNotificationTemplates() {
        return notificationTemplates;
    }

    /**
     * @param notificationTemplates the notificationTemplates to set
     */
    public void setNotificationTemplates(
            List<NotificationTemplate> notificationTemplates) {
        this.notificationTemplates = notificationTemplates;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        final int maxLen = 10;
        StringBuilder builder = new StringBuilder();
        builder.append("NotificationConfig [baseDir=").append(baseDir)
                .append(", templatesBase=").append(templatesBase)
                .append(", samplesBase=").append(samplesBase)
                .append(", notificationTemplates=")
                .append(notificationTemplates != null
                        ? notificationTemplates.subList(0,
                                Math.min(notificationTemplates.size(), maxLen))
                        : null)
                .append("]");
        return builder.toString();
    }

}
