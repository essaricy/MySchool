package com.myschool.application.service;

import java.io.File;
import java.util.List;

import com.myschool.application.dto.FeatureDto;
import com.myschool.common.exception.ServiceException;
import com.myschool.infra.image.constants.ImageSize;

/**
 * The Interface ImageService.
 */
public interface ImageService {

    /**
     * Gets the logo.
     * 
     * @return the logo
     * @throws ServiceException the service exception
     */
    File getLogo() throws ServiceException;

    /**
     * Gets the no image.
     * 
     * @return the no image
     * @throws ServiceException the service exception
     */
    File getNoImage() throws ServiceException;

    /**
     * Gets the features.
     * 
     * @return the features
     */
    List<FeatureDto> getFeatures();

    /**
     * Gets the student image.
     * 
     * @param admissionNumber the admission number
     * @param imageSize the image size
     * @return the student image
     * @throws ServiceException the service exception
     */
    File getStudentImage(String admissionNumber, ImageSize imageSize) throws ServiceException;

    /**
     * Gets the employee image.
     * 
     * @param employeeNumber the employee number
     * @param imageSize the image size
     * @return the employee image
     * @throws ServiceException the service exception
     */
    File getEmployeeImage(String employeeNumber, ImageSize imageSize) throws ServiceException;

    /**
     * Gets the gallery names.
     * 
     * @return the gallery names
     * @throws ServiceException the service exception
     */
    List<String> getGalleryNames() throws ServiceException;

    /**
     * Gets the gallery item names.
     * 
     * @param galleryName the gallery name
     * @return the gallery item names
     * @throws ServiceException the service exception
     */
    List<String> getGalleryItemNames(String galleryName) throws ServiceException;

    /**
     * Gets the latest gallery name.
     * 
     * @return the latest gallery name
     * @throws ServiceException the service exception
     */
    String getLatestGalleryName() throws ServiceException;

    /**
     * Gets the gallery item.
     * 
     * @param galleryName the gallery name
     * @param imageSize the image size
     * @return the gallery item
     * @throws ServiceException the service exception
     */
    File getGalleryItem(String galleryName, ImageSize imageSize) throws ServiceException;

    /**
     * Gets the org image.
     * 
     * @param imageName the image name
     * @param imageSize the image size
     * @return the org image
     * @throws ServiceException the service exception
     */
    File getOrgImage(String imageName, ImageSize imageSize) throws ServiceException;

    /**
     * Mark as latest.
     * 
     * @param galleryName the gallery name
     * @throws ServiceException the service exception
     */
    void markAsLatest(String galleryName) throws ServiceException;

}
