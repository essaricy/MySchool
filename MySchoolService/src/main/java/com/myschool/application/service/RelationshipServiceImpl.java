package com.myschool.application.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myschool.application.domain.RelationshipManager;
import com.myschool.common.dto.Relationship;
import com.myschool.common.exception.DataException;
import com.myschool.common.exception.ServiceException;

/**
 * The Class RelationshipServiceImpl.
 */
@Service
public class RelationshipServiceImpl implements RelationshipService {

    /** The relationship manager. */
    @Autowired
    private RelationshipManager relationshipManager;

    /* (non-Javadoc)
     * @see com.myschool.application.service.Servicable#getAll()
     */
    @Override
    public List<Relationship> getAll() throws ServiceException {
        try {
            return relationshipManager.getAll();
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.application.service.RelationshipService#get(java.lang.String)
     */
    @Override
    public Relationship get(String code) throws ServiceException {
        try {
            return relationshipManager.get(code);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.application.service.Servicable#create(java.lang.Object)
     */
    @Override
    public boolean create(Relationship relationship) throws ServiceException {
        try {
            return relationshipManager.create(relationship);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.application.service.Servicable#get(int)
     */
    @Override
    public Relationship get(int id) throws ServiceException {
        throw new ServiceException("Use get(code) instead.");
    }

    /* (non-Javadoc)
     * @see com.myschool.application.service.Servicable#update(int, java.lang.Object)
     */
    @Override
    public boolean update(int id, Relationship relationship) throws ServiceException {
        throw new ServiceException("Use update(code, Relationship) instead.");
    }

    /* (non-Javadoc)
     * @see com.myschool.application.service.RelationshipService#update(java.lang.String, com.myschool.common.dto.Relationship)
     */
    @Override
    public boolean update(String code, Relationship relationship) throws ServiceException {
        try {
            return relationshipManager.update(code, relationship);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.application.service.Servicable#delete(int)
     */
    @Override
    public boolean delete(int id) throws ServiceException {
        throw new ServiceException("Use delete(code) instead.");
    }

    /* (non-Javadoc)
     * @see com.myschool.application.service.RelationshipService#delete(java.lang.String)
     */
    @Override
    public boolean delete(String code) throws ServiceException {
        try {
            return relationshipManager.delete(code);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
    }

}
