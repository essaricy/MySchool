package com.myschool.web.exim.bean;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

/**
 * The Class UploadDataFileBean.
 */
public class UploadDataFileBean implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The multi upload id. */
    private String multiUploadId;

    /** The upload name. */
    private String uploadName;

    /** The upload file. */
    private MultipartFile uploadFile;

    /**
     * Gets the multi upload id.
     * 
     * @return the multi upload id
     */
    public String getMultiUploadId() {
        return multiUploadId;
    }

    /**
     * Sets the multi upload id.
     * 
     * @param multiUploadId the new multi upload id
     */
    public void setMultiUploadId(String multiUploadId) {
        this.multiUploadId = multiUploadId;
    }

    /**
     * Gets the upload name.
     * 
     * @return the upload name
     */
    public String getUploadName() {
        return uploadName;
    }

    /**
     * Sets the upload name.
     * 
     * @param uploadName the new upload name
     */
    public void setUploadName(String uploadName) {
        this.uploadName = uploadName;
    }

    /**
     * Gets the upload file.
     * 
     * @return the upload file
     */
    public MultipartFile getUploadFile() {
        return uploadFile;
    }

    /**
     * Sets the upload file.
     * 
     * @param uploadFile the new upload file
     */
    public void setUploadFile(MultipartFile uploadFile) {
        this.uploadFile = uploadFile;
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
        retValue.append("UploadDataFileBean ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("multiUploadId = ").append(this.multiUploadId).append(SEPARATOR)
            .append("uploadName = ").append(this.uploadName).append(SEPARATOR)
            .append("uploadFile = ").append(this.uploadFile).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}
