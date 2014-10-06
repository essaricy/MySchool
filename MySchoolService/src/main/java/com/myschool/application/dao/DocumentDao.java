package com.myschool.application.dao;

import java.util.List;

import com.myschool.common.dto.DocumentDto;
import com.myschool.common.dto.DocumentSearchCriteria;
import com.myschool.common.exception.DaoException;
import com.myschool.user.constants.UserType;

/**
 * The Interface DocumentDao.
 */
public interface DocumentDao {

    /**
     * Gets the all.
     * 
     * @return the all
     * @throws DaoException the dao exception
     */
    List<DocumentDto> getAll() throws DaoException;

    /**
     * Gets the all.
     * 
     * @param userType the user type
     * @return the all
     * @throws DaoException the dao exception
     */
    List<DocumentDto> getAll(UserType userType) throws DaoException;

    /**
     * Gets the.
     * 
     * @param documentId the document id
     * @return the document dto
     * @throws DaoException the dao exception
     */
    DocumentDto get(int documentId) throws DaoException;

    /**
     * Gets the.
     * 
     * @param documentName the document name
     * @return the document dto
     * @throws DaoException the dao exception
     */
    DocumentDto get(String documentName) throws DaoException;

    /**
     * Creates the.
     * 
     * @param document the document
     * @return the int
     * @throws DaoException the dao exception
     */
    int create(DocumentDto document) throws DaoException;

    /**
     * Update.
     * 
     * @param documentId the document id
     * @param document the document
     * @return true, if successful
     * @throws DaoException the dao exception
     */
    boolean update(int documentId, DocumentDto document) throws DaoException;

    /**
     * Delete.
     * 
     * @param documentId the document id
     * @return true, if successful
     * @throws DaoException the dao exception
     */
    boolean delete(int documentId) throws DaoException;

    /**
     * Gets the all.
     * 
     * @param documentSearchCriteria the document search criteria
     * @return the all
     * @throws DaoException the dao exception
     */
    List<DocumentDto> getAll(DocumentSearchCriteria documentSearchCriteria) throws DaoException;

}
