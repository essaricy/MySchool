package com.myschool.infra.web.dto;

import java.io.Serializable;

/**
 * The Class WebContent.
 */
public class WebContent implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The content link. */
    private String contentLink;

    /** The original image link. */
    private String originalImageLink;

    /** The passport image link. */
    private String passportImageLink;

    /** The thumbnail image link. */
    private String thumbnailImageLink;

    /**
     * Gets the content link.
     *
     * @return the contentLink
     */
    public String getContentLink() {
        return contentLink;
    }

    /**
     * Sets the content link.
     *
     * @param contentLink the contentLink to set
     */
    public void setContentLink(String contentLink) {
        this.contentLink = contentLink;
    }

    /**
     * Gets the original image link.
     *
     * @return the originalImageLink
     */
    public String getOriginalImageLink() {
        return originalImageLink;
    }

    /**
     * Sets the original image link.
     *
     * @param originalImageLink the originalImageLink to set
     */
    public void setOriginalImageLink(String originalImageLink) {
        this.originalImageLink = originalImageLink;
    }

    /**
     * Gets the passport image link.
     *
     * @return the passportImageLink
     */
    public String getPassportImageLink() {
        return passportImageLink;
    }

    /**
     * Sets the passport image link.
     *
     * @param passportImageLink the passportImageLink to set
     */
    public void setPassportImageLink(String passportImageLink) {
        this.passportImageLink = passportImageLink;
    }

    /**
     * Gets the thumbnail image link.
     *
     * @return the thumbnailImageLink
     */
    public String getThumbnailImageLink() {
        return thumbnailImageLink;
    }

    /**
     * Sets the thumbnail image link.
     *
     * @param thumbnailImageLink the thumbnailImageLink to set
     */
    public void setThumbnailImageLink(String thumbnailImageLink) {
        this.thumbnailImageLink = thumbnailImageLink;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("WebContent [contentLink=").append(contentLink)
                .append(", originalImageLink=").append(originalImageLink)
                .append(", passportImageLink=").append(passportImageLink)
                .append(", thumbnailImageLink=").append(thumbnailImageLink)
                .append("]");
        return builder.toString();
    }

}
