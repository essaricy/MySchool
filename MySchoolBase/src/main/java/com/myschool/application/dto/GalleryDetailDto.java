package com.myschool.application.dto;

import java.io.File;
import java.util.List;

/**
 * The Class GalleryDetailDto.
 */
public class GalleryDetailDto {

	/** The gallery name. */
	private String galleryName;

	/** The gallery items. */
	private List<GalleryDetailDto> galleryItems;

	/** The last modified. */
	private String lastModified;

	/** The size. */
	private String size;

	/** The pinned. */
	private boolean pinned;

	/** The file. */
	private File file;

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
	 * Gets the last modified.
	 *
	 * @return the last modified
	 */
	public String getLastModified() {
		return lastModified;
	}

	/**
	 * Sets the last modified.
	 *
	 * @param lastModified the new last modified
	 */
	public void setLastModified(String lastModified) {
		this.lastModified = lastModified;
	}

	/**
	 * Gets the size.
	 *
	 * @return the size
	 */
	public String getSize() {
		return size;
	}

	/**
	 * Sets the size.
	 *
	 * @param size the new size
	 */
	public void setSize(String size) {
		this.size = size;
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
	 * Gets the file.
	 *
	 * @return the file
	 */
	public File getFile() {
		return file;
	}

	/**
	 * Sets the file.
	 *
	 * @param file the new file
	 */
	public void setFile(File file) {
		this.file = file;
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
	        .append("galleryItems = ").append(this.galleryItems).append(SEPARATOR)
	        .append("lastModified = ").append(this.lastModified).append(SEPARATOR)
	        .append("size = ").append(this.size).append(SEPARATOR)
	        .append("pinned = ").append(this.pinned).append(SEPARATOR)
	        .append("file = ").append(this.file).append(SEPARATOR)
	        .append(" )\n");
	    return retValue.toString();
	}

}
