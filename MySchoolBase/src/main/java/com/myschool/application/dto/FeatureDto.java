package com.myschool.application.dto;

import java.io.Serializable;

/**
 * The Class FeatureDto.
 */
public class FeatureDto implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The image path. */
    private String imagePath;

    /** The name. */
    private String name;

    /** The description. */
    private String description;

    /** The passport url. */
    private String passportUrl;

    /** The thumbnail url. */
    private String thumbnailUrl;

    /**
     * Gets the image path.
     *
     * @return the image path
     */
    public String getImagePath() {
        return imagePath;
    }

    /**
     * Sets the image path.
     *
     * @param imagePath the new image path
     */
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

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
     * @param name the new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description.
     *
     * @param description the new description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the passport url.
     * 
     * @return the passport url
     */
    public String getPassportUrl() {
        return passportUrl;
    }

    /**
     * Sets the passport url.
     * 
     * @param passportUrl the new passport url
     */
    public void setPassportUrl(String passportUrl) {
        this.passportUrl = passportUrl;
    }

    /**
     * Gets the thumbnail url.
     * 
     * @return the thumbnail url
     */
    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    /**
     * Sets the thumbnail url.
     * 
     * @param thumbnailUrl the new thumbnail url
     */
    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
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
        retValue.append("FeatureDto ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("imagePath = ").append(this.imagePath).append(SEPARATOR)
            .append("name = ").append(this.name).append(SEPARATOR)
            .append("description = ").append(this.description).append(SEPARATOR)
            .append("passportUrl = ").append(this.passportUrl).append(SEPARATOR)
            .append("thumbnailUrl = ").append(this.thumbnailUrl).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}
