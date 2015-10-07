package com.myschool.application.service;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myschool.application.domain.GalleryManager;
import com.myschool.application.dto.GalleryDetailDto;
import com.myschool.common.exception.DataException;
import com.myschool.common.exception.ServiceException;
import com.myschool.infra.image.constants.ImageSize;

/**
 * The Class GalleryServiceImpl.
 */
@Service
public class GalleryServiceImpl implements GalleryService {

	/** The gallery manager. */
	@Autowired
	private GalleryManager galleryManager;

	/* (non-Javadoc)
	 * @see com.myschool.application.service.Servicable#create(java.lang.Object)
	 */
	@Override
	public boolean create(GalleryDetailDto galleryDetail) throws ServiceException {
		throw new ServiceException("Use create(galleryName) instead");
	}

	/* (non-Javadoc)
	 * @see com.myschool.application.service.Servicable#getAll()
	 */
	@Override
	public List<GalleryDetailDto> getAll() throws ServiceException {
        try {
            return galleryManager.getAll();
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
    }

	/* (non-Javadoc)
	 * @see com.myschool.application.service.Servicable#get(int)
	 */
	@Override
	public GalleryDetailDto get(int id) throws ServiceException {
		throw new ServiceException("Use get(galleryName) instead");
	}

	/* (non-Javadoc)
	 * @see com.myschool.application.service.Servicable#update(int, java.lang.Object)
	 */
	@Override
	public boolean update(int id, GalleryDetailDto galleryDetail) throws ServiceException {
		throw new ServiceException("Use update(galleryName, galleryDetail) instead");
	}

	/* (non-Javadoc)
	 * @see com.myschool.application.service.Servicable#delete(int)
	 */
	@Override
	public boolean delete(int id) throws ServiceException {
		throw new ServiceException("Use delete(galleryName) instead");
	}

	/* (non-Javadoc)
	 * @see com.myschool.application.service.GalleryService#get(java.lang.String)
	 */
	@Override
	public GalleryDetailDto get(String galleryName) throws ServiceException {
        try {
            return galleryManager.getGalleryDetail(galleryName);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
    }

	/* (non-Javadoc)
	 * @see com.myschool.application.service.GalleryService#getPinned()
	 */
	@Override
	public GalleryDetailDto getPinned() throws ServiceException {
        try {
            return galleryManager.getPinned();
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
    }

	/* (non-Javadoc)
	 * @see com.myschool.application.service.GalleryService#getGalleryItemFile(java.lang.String, com.myschool.infra.image.constants.ImageSize)
	 */
	@Override
	public File getGalleryItemFile(String galleryName, ImageSize imageSize)
			throws ServiceException {
        try {
            return galleryManager.getGalleryItemFile(galleryName, imageSize);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
    }

	/* (non-Javadoc)
	 * @see com.myschool.application.service.GalleryService#create(java.lang.String)
	 */
	@Override
	public boolean create(String galleryName) throws ServiceException {
        try {
            return galleryManager.create(galleryName);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
    }

	/* (non-Javadoc)
	 * @see com.myschool.application.service.GalleryService#update(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean update(String oldGalleryName, String newGalleryName) throws ServiceException {
        try {
            return galleryManager.update(oldGalleryName, newGalleryName);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
    }

	/* (non-Javadoc)
	 * @see com.myschool.application.service.GalleryService#delete(java.lang.String)
	 */
	@Override
	public boolean delete(String galleryName) throws ServiceException {
        try {
            return galleryManager.delete(galleryName);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
    }

	/* (non-Javadoc)
	 * @see com.myschool.application.service.GalleryService#pin(java.lang.String)
	 */
	@Override
	public void pin(String galleryName) throws ServiceException {
        try {
        	galleryManager.pin(galleryName);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
    }

	/* (non-Javadoc)
	 * @see com.myschool.application.service.GalleryService#add(java.lang.String, com.myschool.application.dto.GalleryDetailDto)
	 */
	@Override
	public boolean add(String galleryName, GalleryDetailDto galleryDetail) throws ServiceException {
        try {
            return galleryManager.add(galleryName, galleryDetail);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
    }

	/* (non-Javadoc)
	 * @see com.myschool.application.service.GalleryService#add(java.lang.String, java.util.List)
	 */
	@Override
	public List<String> add(String galleryName, List<GalleryDetailDto> galleryDetails) throws ServiceException {
        try {
            return galleryManager.add(galleryName, galleryDetails);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
    }

	/* (non-Javadoc)
	 * @see com.myschool.application.service.GalleryService#delete(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean delete(String galleryName, String galleryItemName) throws ServiceException {
        try {
            return galleryManager.delete(galleryName, galleryItemName);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
    }

	/* (non-Javadoc)
	 * @see com.myschool.application.service.GalleryService#delete(java.lang.String, java.util.List)
	 */
	@Override
	public List<String> delete(String galleryName, List<String> galleryItemNames) throws ServiceException {
        try {
            return galleryManager.delete(galleryName, galleryItemNames);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
    }

}
