package com.myschool.application.service;

import java.io.File;
import java.util.List;

import com.myschool.application.dto.GalleryDetailDto;
import com.myschool.common.exception.ServiceException;
import com.myschool.infra.image.constants.ImageSize;

/**
 * The Interface GalleryService.
 */
public interface GalleryService extends Servicable<GalleryDetailDto> {

	/**
	 * Gets the.
	 *
	 * @param galleryName the gallery name
	 * @return the gallery detail dto
	 * @throws ServiceException the service exception
	 */
	GalleryDetailDto get(String galleryName) throws ServiceException;

	/**
	 * Gets the pinned.
	 *
	 * @return the pinned
	 * @throws ServiceException the service exception
	 */
	GalleryDetailDto getPinned() throws ServiceException;

    /**
     * Gets the gallery item file.
     *
     * @param galleryName the gallery name
     * @param imageSize the image size
     * @return the gallery item file
     * @throws ServiceException the service exception
     */
    File getGalleryItemFile(String galleryName, ImageSize imageSize) throws ServiceException;

    /**
     * Creates the.
     *
     * @param galleryName the gallery name
     * @return true, if successful
     * @throws ServiceException the service exception
     */
    boolean create(String galleryName) throws ServiceException;

    /**
     * Update.
     *
     * @param oldGalleryName the old gallery name
     * @param newGalleryName the new gallery name
     * @return true, if successful
     * @throws ServiceException the service exception
     */
    boolean update(String oldGalleryName, String newGalleryName) throws ServiceException;

    /**
     * Delete.
     *
     * @param galleryName the gallery name
     * @return true, if successful
     * @throws ServiceException the service exception
     */
    boolean delete(String galleryName) throws ServiceException;

    /**
     * Pin.
     *
     * @param galleryName the gallery name
     * @throws ServiceException the service exception
     */
    void pin(String galleryName) throws ServiceException;

    /**
     * Adds the.
     *
     * @param galleryName the gallery name
     * @param galleryDetail the gallery detail
     * @return true, if successful
     * @throws ServiceException the service exception
     */
    boolean add(String galleryName, GalleryDetailDto galleryDetail) throws ServiceException;

    /**
     * Adds the.
     *
     * @param galleryName the gallery name
     * @param galleryDetails the gallery details
     * @return the list
     * @throws ServiceException the service exception
     */
    List<String> add(String galleryName, List<GalleryDetailDto> galleryDetails) throws ServiceException;

    /**
     * Delete.
     *
     * @param galleryName the gallery name
     * @param galleryItemName the gallery item name
     * @return true, if successful
     * @throws ServiceException the service exception
     */
    boolean delete(String galleryName, String galleryItemName) throws ServiceException;

    /**
     * Delete.
     *
     * @param galleryName the gallery name
     * @param galleryItemNames the gallery item names
     * @return the list
     * @throws ServiceException the service exception
     */
    List<String> delete(String galleryName, List<String> galleryItemNames) throws ServiceException;

}
