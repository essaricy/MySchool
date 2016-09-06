package com.myschool.storage.dto;

/**
 * The Class StorageItem.
 */
public class StorageItem {

    /** The id. */
    private String id;

    /** The name. */
    private String name;

    /** The created time. */
    private long createdTime;

    /** The modified time. */
    private long modifiedTime;

    /** The size. */
    private long size;

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
     * Gets the created time.
     *
     * @return the createdTime
     */
    public long getCreatedTime() {
        return createdTime;
    }

    /**
     * Sets the created time.
     *
     * @param createdTime the createdTime to set
     */
    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }

    /**
     * Gets the modified time.
     *
     * @return the modifiedTime
     */
    public long getModifiedTime() {
        return modifiedTime;
    }

    /**
     * Sets the modified time.
     *
     * @param modifiedTime the modifiedTime to set
     */
    public void setModifiedTime(long modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    /**
     * Gets the size.
     *
     * @return the size
     */
    public long getSize() {
        return size;
    }

    /**
     * Sets the size.
     *
     * @param size the size to set
     */
    public void setSize(long size) {
        this.size = size;
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
        builder.append("StorageItem [id=").append(id).append(", name=")
                .append(name).append(", createdTime=").append(createdTime)
                .append(", modifiedTime=").append(modifiedTime)
                .append(", size=").append(size).append(", directLink=")
                .append(directLink).append(", thumbnailLink=")
                .append(thumbnailLink).append(", passportLink=")
                .append(passportLink).append("]");
        return builder.toString();
    }

}
