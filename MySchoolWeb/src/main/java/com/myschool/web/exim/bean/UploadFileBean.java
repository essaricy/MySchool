package com.myschool.web.exim.bean;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

/**
 * The Class UploadFileBean.
 */
public class UploadFileBean implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The content id. */
    private String contentId;

    /** The content. */
    private MultipartFile content;

    private String module;

    /**
     * Gets the module.
     *
     * @return the module
     */
    public String getModule() {
        return module;
    }

    /**
     * Sets the module.
     *
     * @param module the new module
     */
    public void setModule(String module) {
        this.module = module;
    }

    /**
     * Gets the content id.
     *
     * @return the content id
     */
    public String getContentId() {
        return contentId;
    }

    /**
     * Sets the content id.
     *
     * @param contentId the new content id
     */
    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    /**
     * Gets the content.
     *
     * @return the content
     */
    public MultipartFile getContent() {
        return content;
    }

    /**
     * Sets the content.
     *
     * @param content the new content
     */
    public void setContent(MultipartFile content) {
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
        retValue.append("UploadFileBean ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("content = ").append(this.content).append(SEPARATOR)
            .append("contentId = ").append(this.contentId).append(SEPARATOR)
            .append("module = ").append(this.module).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}
