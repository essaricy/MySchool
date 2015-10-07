package com.myschool.application.service;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myschool.application.domain.ImageManager;
import com.myschool.application.dto.FeatureDto;
import com.myschool.common.exception.DataException;
import com.myschool.common.exception.ServiceException;
import com.myschool.infra.image.constants.ImageSize;

/**
 * The Class ImageServiceImpl.
 */
@Service
public class ImageServiceImpl implements ImageService {

    /** The image manager. */
    @Autowired
    private ImageManager imageManager;

    /* (non-Javadoc)
     * @see com.myschool.application.service.ImageService#getLogo()
     */
    @Override
    public File getLogo() throws ServiceException {
        try {
            return imageManager.getLogo();
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.application.service.ImageService#getNoImage()
     */
    @Override
    public File getNoImage() throws ServiceException {
        try {
            return imageManager.getNoImage();
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.application.service.ImageService#getFeatures()
     */
    @Override
    public List<FeatureDto> getFeatures() {
        List<FeatureDto> features = null;
        features = imageManager.getFeatures();
        return features;
    }

    /* (non-Javadoc)
     * @see com.myschool.application.service.ImageService#getStudentImage(java.lang.String, com.myschool.infra.image.constants.ImageSize)
     */
    @Override
    public File getStudentImage(String admissionNumber, ImageSize imageSize) throws ServiceException {
        try {
            return imageManager.getStudentImage(admissionNumber, imageSize);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.application.service.ImageService#getOrgImage(java.lang.String, com.myschool.infra.image.constants.ImageSize)
     */
    @Override
    public File getOrgImage(String imageName, ImageSize imageSize) throws ServiceException {
        try {
            return imageManager.getOrgImage(imageName, imageSize);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.application.service.ImageService#getEmployeeImage(java.lang.String, com.myschool.infra.image.constants.ImageSize)
     */
    @Override
    public File getEmployeeImage(String employeeNumber, ImageSize imageSize) throws ServiceException {
        try {
            return imageManager.getEmployeeImage(employeeNumber, imageSize);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
    }

}
