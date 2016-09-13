package com.myschool.application.dto;

import java.io.Serializable;

/**
 * The Class ImageAccessDto.
 */
public class ImageAccessDto implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The id. */
    private String id;

    /** The name. */
    private String name;

    /** The direct link. */
    private String directLink;

    /** The thumbnail link. */
    private String thumbnailLink;

    /** The passport link. */
    private String passportLink;

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
     * Gets the direct link.
     *
     * @return the directLink
     */
    public String getDirectLink() {
        return directLink;
    }

    /**
     * Sets the direct link.
     *
     * @param directLink the directLink to set
     */
    public void setDirectLink(String directLink) {
        this.directLink = directLink;
    }

    /**
     * Gets the thumbnail link.
     *
     * @return the thumbnailLink
     */
    public String getThumbnailLink() {
        return thumbnailLink;
    }

    /**
     * Sets the thumbnail link.
     *
     * @param thumbnailLink the thumbnailLink to set
     */
    public void setThumbnailLink(String thumbnailLink) {
        this.thumbnailLink = thumbnailLink;
    }

    /**
     * Gets the passport link.
     *
     * @return the passportLink
     */
    public String getPassportLink() {
        return passportLink;
    }

    /**
     * Sets the passport link.
     *
     * @param passportLink the passportLink to set
     */
    public void setPassportLink(String passportLink) {
        this.passportLink = passportLink;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ImageAccessDto [id=").append(id).append(", name=")
                .append(name).append(", directLink=").append(directLink)
                .append(", thumbnailLink=").append(thumbnailLink)
                .append(", passportLink=").append(passportLink).append("]");
        return builder.toString();
    }

}
