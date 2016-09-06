package com.myschool.storage.dto;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * The Class StorageProviderDto.
 */
public class StorageProviderDto {

    /** The name. */
    private String name;

    /** The appname. */
    private String appname;

    /** The direct link. */
    private String directLink;

    /** The passport link. */
    private String passportLink;

    /** The thumbnail link. */
    private String thumbnailLink;

    /** The params. */
    private Map<String, String> params;

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
     * Gets the appname.
     *
     * @return the appname
     */
    public String getAppname() {
        return appname;
    }

    /**
     * Sets the appname.
     *
     * @param appname the appname to set
     */
    public void setAppname(String appname) {
        this.appname = appname;
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
     * Gets the params.
     *
     * @return the params
     */
    public Map<String, String> getParams() {
        return params;
    }

    /**
     * Sets the params.
     *
     * @param params the params to set
     */
    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        final int maxLen = 10;
        StringBuilder builder = new StringBuilder();
        builder.append("StorageProviderDto [name=").append(name)
                .append(", appname=").append(appname).append(", directLink=")
                .append(directLink).append(", passportLink=")
                .append(passportLink).append(", thumbnailLink=")
                .append(thumbnailLink)
                .append(", params=").append(params != null
                        ? toString(params.entrySet(), maxLen) : null)
                .append("]");
        return builder.toString();
    }

    /**
     * To string.
     *
     * @param collection the collection
     * @param maxLen the max len
     * @return the string
     */
    private String toString(Collection<?> collection, int maxLen) {
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        int i = 0;
        for (Iterator<?> iterator = collection.iterator(); iterator.hasNext()
                && i < maxLen; i++) {
            if (i > 0) {
                builder.append(", ");
            }
            builder.append(iterator.next());
        }
        builder.append("]");
        return builder.toString();
    }

}
