package com.myschool.download.dto;

import java.io.Serializable;

/**
 * The Class BrochureDto.
 */
public class BrochureDto implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The brochure name. */
    private String brochureName;

    /** The brochure Type. */
    private String brochureType;

    /** The url. */
    private String url;

    /**
     * Gets the brochure name.
     *
     * @return the brochure name
     */
    public String getBrochureName() {
        return brochureName;
    }

    /**
     * Sets the brochure name.
     *
     * @param brochureName the new brochure name
     */
    public void setBrochureName(String brochureName) {
        this.brochureName = brochureName;
    }

    /**
     * Gets the brochure type.
     *
     * @return the brochure type
     */
    public String getBrochureType() {
        return brochureType;
    }

    /**
     * Sets the brochure type.
     *
     * @param brochureType the new brochure type
     */
    public void setBrochureType(String brochureType) {
        this.brochureType = brochureType;
    }

    /**
     * Gets the url.
     * 
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * Sets the url.
     * 
     * @param url the new url
     */
    public void setUrl(String url) {
        this.url = url;
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
        retValue.append("BrochureDto ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("brochureName = ").append(this.brochureName).append(SEPARATOR)
            .append("brochureType = ").append(this.brochureType).append(SEPARATOR)
            .append("url = ").append(this.url).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}
