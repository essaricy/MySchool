package com.myschool.infra.image.dto;

import java.io.Serializable;

/**
 * The Class ImageResizingOptionDto.
 */
public class ImageResizingOptionDto implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The width. */
    private int width;

    /** The height. */
    private int height;

    /** The folder name. */
    private String folderName;

    /**
     * Gets the width.
     *
     * @return the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Sets the width.
     *
     * @param width the new width
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Gets the height.
     *
     * @return the height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Sets the height.
     *
     * @param height the new height
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Gets the folder name.
     * 
     * @return the folder name
     */
    public String getFolderName() {
        return folderName;
    }

    /**
     * Sets the folder name.
     * 
     * @param folderName the new folder name
     */
    public void setFolderName(String folderName) {
        this.folderName = folderName;
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
        retValue.append("ImageResizingOptionDto ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("width = ").append(this.width).append(SEPARATOR)
            .append("height = ").append(this.height).append(SEPARATOR)
            .append("folderName = ").append(this.folderName).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}
