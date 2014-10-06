package com.myschool.infra.application.dto;

import java.io.File;
import java.io.Serializable;

/**
 * The Class MySchoolDto.
 */
public class MySchoolDto implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The my school name. */
    private String mySchoolName;

    /** The base dir. */
    private File baseDir;

    /** The web url. */
    private String webUrl;

    /**
     * Gets the my school name.
     * 
     * @return the my school name
     */
    public String getMySchoolName() {
        return mySchoolName;
    }

    /**
     * Sets the my school name.
     * 
     * @param mySchoolName the new my school name
     */
    public void setMySchoolName(String mySchoolName) {
        this.mySchoolName = mySchoolName;
    }

    /**
     * Gets the base dir.
     * 
     * @return the base dir
     */
    public File getBaseDir() {
        return baseDir;
    }

    /**
     * Sets the base dir.
     * 
     * @param baseDir the new base dir
     */
    public void setBaseDir(File baseDir) {
        this.baseDir = baseDir;
    }

    /**
     * Gets the web url.
     * 
     * @return the web url
     */
    public String getWebUrl() {
        return webUrl;
    }

    /**
     * Sets the web url.
     * 
     * @param webUrl the new web url
     */
    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
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
        retValue.append("MySchoolDto ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("mySchoolName = ").append(this.mySchoolName).append(SEPARATOR)
            .append("baseDir = ").append(this.baseDir).append(SEPARATOR)
            .append("webUrl = ").append(this.webUrl).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}
