package com.myschool.infra.media.dto;

import java.io.Serializable;
import java.util.List;

/**
 * The Class ResourceConfigDto.
 */
public class ResourceConfigDto implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The id. */
    private String id;

    /** The url. */
    private String url;

    /** The name. */
    private String name;

    /** The excludes. */
    private List<String> excludes;

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
     * @param id the new id
     */
    public void setId(String id) {
        this.id = id;
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
     * Gets the excludes.
     * 
     * @return the excludes
     */
    public List<String> getExcludes() {
        return excludes;
    }

    /**
     * Sets the excludes.
     * 
     * @param excludes the new excludes
     */
    public void setExcludes(List<String> excludes) {
        this.excludes = excludes;
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
        retValue.append("ResourceConfigDto ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("id = ").append(this.id).append(SEPARATOR)
            .append("url = ").append(this.url).append(SEPARATOR)
            .append("name = ").append(this.name).append(SEPARATOR)
            .append("excludes = ").append(this.excludes).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}
