package com.myschool.application.domain;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.application.dao.DocumentDao;
import com.myschool.common.dto.DocumentDto;
import com.myschool.common.dto.DocumentSearchCriteria;
import com.myschool.common.exception.DaoException;
import com.myschool.common.exception.DataException;
import com.myschool.common.exception.ValidationException;
import com.myschool.user.constants.UserType;

/**
 * The Class DocumentManager.
 */
@Component
public class DocumentManager {

    /** The document validator. */
    @Autowired
    private DocumentValidator documentValidator;

    /** The document dao. */
    @Autowired
    private DocumentDao documentDao;

    /**
     * Creates the.
     * 
     * @param document the document
     * @return the int
     * @throws DataException the data exception
     */
    public int create(DocumentDto document) throws DataException {
        try {
            documentValidator.validate(document);
            return documentDao.create(document);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        } catch (ValidationException validationException) {
            throw new DataException(validationException.getMessage(), validationException);
        }
    }

    /**
     * Gets the all.
     * 
     * @return the all
     * @throws DataException the data exception
     */
    public List<DocumentDto> getAll() throws DataException {
        try {
            return documentDao.getAll();
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
    }

    /**
     * Gets the all.
     * 
     * @param userType the user type
     * @return the all
     * @throws DataException the data exception
     */
    public List<DocumentDto> getAll(UserType userType) throws DataException {
        try {
            return documentDao.getAll(userType);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
    }

    /**
     * Gets the.
     * 
     * @param documentId the document id
     * @return the document dto
     * @throws DataException the data exception
     */
    public DocumentDto get(int documentId) throws DataException {
        try {
            return documentDao.get(documentId);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
    }

    /**
     * Update.
     * 
     * @param documentId the document id
     * @param document the document
     * @return true, if successful
     * @throws DataException the data exception
     */
    public boolean update(int documentId, DocumentDto document) throws DataException {
        try {
            documentValidator.validate(document);
            return documentDao.update(documentId, document);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        } catch (ValidationException validationException) {
            throw new DataException(validationException.getMessage(), validationException);
        }
    }

    /**
     * Delete.
     * 
     * @param documentId the document id
     * @return true, if successful
     * @throws DataException the data exception
     */
    public boolean delete(int documentId) throws DataException {
        try {
            return documentDao.delete(documentId);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
    }

    /**
     * Gets the all.
     * 
     * @param documentSearchCriteria the document search criteria
     * @return the all
     * @throws DataException the data exception
     */
    public List<DocumentDto> getAll(
            DocumentSearchCriteria documentSearchCriteria) throws DataException {
        try {
            return documentDao.getAll(documentSearchCriteria);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
    }

}
