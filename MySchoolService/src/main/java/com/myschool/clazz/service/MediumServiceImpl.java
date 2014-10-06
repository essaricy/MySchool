package com.myschool.clazz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myschool.clazz.domain.MediumManager;
import com.myschool.clazz.dto.MediumDto;
import com.myschool.common.exception.DataException;
import com.myschool.common.exception.ServiceException;

/**
 * The Class MediumServiceImpl.
 */
@Service
public class MediumServiceImpl implements MediumService {

    /** The medium manager. */
    @Autowired
    private MediumManager mediumManager;

    /* (non-Javadoc)
     * @see com.myschool.service.interfaces.Servicable#create(java.lang.Object)
     */
    @Override
    public boolean create(MediumDto mediumDto) throws ServiceException {
        boolean created = false;
        try {
            created = mediumManager.create(mediumDto);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
        return created;
    }

    /* (non-Javadoc)
     * @see com.myschool.service.interfaces.Servicable#delete(int)
     */
    @Override
    public boolean delete(int mediumId) throws ServiceException {
        boolean deleted = false;
        try {
            deleted = mediumManager.delete(mediumId);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
        return deleted;
    }

    /* (non-Javadoc)
     * @see com.myschool.service.interfaces.Servicable#get(int)
     */
    @Override
    public MediumDto get(int mediumId) throws ServiceException {
        MediumDto medium = null;
        try {
            medium = mediumManager.get(mediumId);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
        return medium;
    }

    /* (non-Javadoc)
     * @see com.myschool.service.interfaces.Servicable#getAll()
     */
    @Override
    public List<MediumDto> getAll() throws ServiceException {
        List<MediumDto> mediums = null;
        try {
            mediums = mediumManager.getAll();
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(),
                    dataException);
        }
        return mediums;
    }

    /* (non-Javadoc)
     * @see com.myschool.service.interfaces.Servicable#update(int, java.lang.Object)
     */
    @Override
    public boolean update(int mediumId, MediumDto mediumDto)
            throws ServiceException {
        boolean updated = false;
        try {
            updated = mediumManager.update(mediumId, mediumDto);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
        return updated;
    }

}
