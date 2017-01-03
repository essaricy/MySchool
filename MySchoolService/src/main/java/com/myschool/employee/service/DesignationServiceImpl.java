package com.myschool.employee.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myschool.common.exception.ServiceException;
import com.myschool.employee.domain.DesignationManager;
import com.myschool.employee.dto.DesignationDto;
import com.quasar.core.exception.DataException;

/**
 * The Class DesignationServiceImpl.
 */
@Service
public class DesignationServiceImpl implements DesignationService {

    /** The designation manager. */
    @Autowired
    private DesignationManager designationManager;

    /* (non-Javadoc)
     * @see com.myschool.service.interfaces.Servicable#create(java.lang.Object)
     */
    @Override
    public boolean create(DesignationDto designation) throws ServiceException {
        boolean created = false;
        try {
            created = designationManager.create(designation);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
        return created;
    }

    /* (non-Javadoc)
     * @see com.myschool.service.interfaces.Servicable#delete(int)
     */
    @Override
    public boolean delete(int designationId) throws ServiceException {
        boolean deleted = false;
        try {
            deleted = designationManager.delete(designationId);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
        return deleted;
    }

    /* (non-Javadoc)
     * @see com.myschool.service.interfaces.Servicable#get(int)
     */
    @Override
    public DesignationDto get(int designationId) throws ServiceException {
        try {
            return designationManager.get(designationId);
       } catch (DataException dataException) {
           throw new ServiceException(dataException.getMessage(),
                   dataException);
       }
    }

    /* (non-Javadoc)
     * @see com.myschool.service.interfaces.Servicable#getAll()
     */
    @Override
    public List<DesignationDto> getAll() throws ServiceException {
        try {
            return designationManager.getAll();
       } catch (DataException dataException) {
           throw new ServiceException(dataException.getMessage(),
                   dataException);
       }
    }

    /* (non-Javadoc)
     * @see com.myschool.service.interfaces.Servicable#update(int, java.lang.Object)
     */
    @Override
    public boolean update(int designationId, DesignationDto designation) throws ServiceException {
        boolean updated = false;
        try {
            updated = designationManager.update(designationId, designation);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
        return updated;
    }

}
