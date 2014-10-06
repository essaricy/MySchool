package com.myschool.application.service;

import com.myschool.common.dto.Relationship;
import com.myschool.common.exception.ServiceException;

/**
 * The Interface RelationshipService.
 */
public interface RelationshipService extends Servicable<Relationship> {

    /**
     * Gets the.
     * 
     * @param code the code
     * @return the relationship
     * @throws ServiceException the service exception
     */
    Relationship get(String code) throws ServiceException;

    /**
     * Update.
     * 
     * @param code the code
     * @param relationship the relationship
     * @return true, if successful
     * @throws ServiceException the service exception
     */
    boolean update(String code, Relationship relationship) throws ServiceException;

    /**
     * Delete.
     * 
     * @param code the code
     * @return true, if successful
     * @throws ServiceException the service exception
     */
    boolean delete(String code) throws ServiceException;

}
