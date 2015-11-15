package com.myschool.application.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myschool.application.domain.ImageManager;
import com.myschool.application.dto.FeatureDto;
import com.myschool.common.exception.DataException;
import com.myschool.common.exception.ServiceException;

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
     
    @Override
    public File getLogo() throws ServiceException {
        try {
            return imageManager.getLogo();
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
    }

     (non-Javadoc)
     * @see com.myschool.application.service.ImageService#getNoImage()
     
    @Override
    public File getNoImage() throws ServiceException {
        try {
            return imageManager.getNoImage();
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
    }*/

    /* (non-Javadoc)
     * @see com.myschool.application.service.ImageService#getFeatures()
     */
    @Override
    public List<FeatureDto> getFeatures() throws ServiceException {
        List<FeatureDto> features = null;
        try {
            features = imageManager.getFeatures();
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
        return features;
    }

}
