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
     * Gets the org image.
     * 
     * @param imageName the image name
     * @param imageSize the image size
     * @return the org image
     * @throws ServiceException the service exception
     */
    File getOrgImage(String imageName, ImageSize imageSize) throws ServiceException;

}
