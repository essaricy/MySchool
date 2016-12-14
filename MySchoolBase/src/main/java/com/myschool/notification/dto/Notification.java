package com.myschool.notification.dto;

import java.io.Serializable;

import com.myschool.template.dto.Template;

/**
 * The Class Notification.
 */
public class Notification implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The id. */
    private String id;

    /** The title. */
    private String title;

    /** The validity. */
    private long validity;

    /** The template. */
    private Template template;

    /**
     * Gets the id.
     *
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title.
     *
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the validity.
     *
     * @return the validity
     */
    public long getValidity() {
        return validity;
    }

    /**
     * Sets the validity.
     *
     * @param validity the validity to set
     */
    public void setValidity(long validity) {
        this.validity = validity;
    }

    /**
     * Gets the template.
     *
     * @return the template
     */
    public Template getTemplate() {
        return template;
    }

    /**
     * Sets the template.
     *
     * @param template the template to set
     */
    public void setTemplate(Template template) {
        this.template = template;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Notification [id=").append(id).append(", title=")
                .append(title).append(", validity=").append(validity)
                .append(", template=").append(template).append("]");
        return builder.toString();
    }

}
