package com.myschool.infra.web.dto;

import java.io.Serializable;

/**
 * The Class WebResource.
 */
public class WebResource implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The name. */
    private String name;

    /** The link url. */
    private String linkUrl;

    /** The image url. */
    private String imageUrl;

    /** The passport url. */
    private String passportUrl;

    /** The thumb url. */
    private String thumbUrl;

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
     * Gets the link url.
     *
     * @return the linkUrl
     */
    public String getLinkUrl() {
        return linkUrl;
    }

    /**
     * Sets the link url.
     *
     * @param linkUrl the linkUrl to set
     */
    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    /**
     * Gets the image url.
     *
     * @return the imageUrl
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * Sets the image url.
     *
     * @param imageUrl the imageUrl to set
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    /**
     * Gets the passport url.
     *
     * @return the passportUrl
     */
    public String getPassportUrl() {
        return passportUrl;
    }

    /**
     * Sets the passport url.
     *
     * @param passportUrl the passportUrl to set
     */
    public void setPassportUrl(String passportUrl) {
        this.passportUrl = passportUrl;
    }

    /**
     * Gets the thumb url.
     *
     * @return the thumbUrl
     */
    public String getThumbUrl() {
        return thumbUrl;
    }

    /**
     * Sets the thumb url.
     *
     * @param thumbUrl the thumbUrl to set
     */
    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("WebResource [name=").append(name).append(", linkUrl=")
                .append(linkUrl).append(", imageUrl=").append(imageUrl)
                .append(", passportUrl=").append(passportUrl)
                .append(", thumbUrl=").append(thumbUrl).append("]");
        return builder.toString();
    }

}
