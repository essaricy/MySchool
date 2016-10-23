package com.myschool.notification.dto;

import java.io.Serializable;

import com.myschool.notification.constants.NotificationMedium;

/**
 * The Class NotificationTemplate.
 */
public class NotificationTemplate implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The name. */
    private String name;

    /** The category. */
    private String category;

    /** The medium. */
    private NotificationMedium medium;

    /** The template. */
    private String template;

    /** The sample. */
    private String sample;

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the category.
     *
     * @return the category
     */
    public String getCategory() {
        return category;
    }

    /**
     * Sets the category.
     *
     * @param category the category to set
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * Gets the medium.
     *
     * @return the medium
     */
    public NotificationMedium getMedium() {
        return medium;
    }

    /**
     * Sets the medium.
     *
     * @param medium the medium to set
     */
    public void setMedium(NotificationMedium medium) {
        this.medium = medium;
    }

    /**
     * Gets the template.
     *
     * @return the template
     */
    public String getTemplate() {
        return template;
    }

    /**
     * Sets the template.
     *
     * @param template the template to set
     */
    public void setTemplate(String template) {
        this.template = template;
    }

    /**
     * Gets the sample.
     *
     * @return the sample
     */
    public String getSample() {
        return sample;
    }

    /**
     * Sets the sample.
     *
     * @param sample the sample to set
     */
    public void setSample(String sample) {
        this.sample = sample;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("NotificationTemplate [name=").append(name)
                .append(", category=").append(category).append(", medium=")
                .append(medium).append(", template=").append(template)
                .append(", sample=").append(sample).append("]");
        return builder.toString();
    }

}
