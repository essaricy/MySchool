package com.myschool.application.dto;

import java.util.List;


/**
 * The Class GalleryDetailDto.
 */
public class GalleryDetailDto {

    /** The gallery name. */
    private String galleryName;

    /** The pinned. */
    private boolean pinned;

    /** The url. */
    private String url;

    /** The passport url. */
    private String passportUrl;

    /** The thumbnail url. */
    private String thumbnailUrl;

    /** The gallery items. */
    private List<GalleryDetailDto> galleryItems;

    /**
     * Gets the gallery name.
     * 
     * @return the gallery name
     */
    public String getGalleryName() {
        return galleryName;
    }

    /**
     * Sets the gallery name.
     * 
     * @param galleryName the new gallery name
     */
    public void setGalleryName(String galleryName) {
        this.galleryName = galleryName;
    }

    /**
     * Checks if is pinned.
     * 
     * @return true, if is pinned
     */
    public boolean isPinned() {
        return pinned;
    }

    /**
     * Sets the pinned.
     * 
     * @param pinned the new pinned
     */
    public void setPinned(boolean pinned) {
        this.pinned = pinned;
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
     * Gets the gallery items.
     * 
     * @return the gallery items
     */
    public List<GalleryDetailDto> getGalleryItems() {
        return galleryItems;
    }

    /**
     * Sets the gallery items.
     * 
     * @param galleryItems the new gallery items
     */
    public void setGalleryItems(List<GalleryDetailDto> galleryItems) {
        this.galleryItems = galleryItems;
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
        retValue.append("GalleryDetailDto ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("galleryName = ").append(this.galleryName).append(SEPARATOR)
            .append("pinned = ").append(this.pinned).append(SEPARATOR)
            .append("url = ").append(this.url).append(SEPARATOR)
            .append("passportUrl = ").append(this.passportUrl).append(SEPARATOR)
            .append("thumbnailUrl = ").append(this.thumbnailUrl).append(SEPARATOR)
            .append("galleryItems = ").append(this.galleryItems).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}
