package com.myschool.application.domain;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.application.dao.RelationshipDao;
import com.myschool.application.validator.RelationshipValidator;
import com.myschool.common.dto.Relationship;
import com.myschool.common.exception.DaoException;
import com.myschool.common.exception.ValidationException;
import com.quasar.core.exception.DataException;

/**
 * The Class RelationshipManager.
 */
@Component
public class RelationshipManager {

    /** The relationship validator. */
    @Autowired
    private RelationshipValidator relationshipValidator;

    /** The relationship dao. */
    @Autowired
    private RelationshipDao relationshipDao;

    /**
     * Gets the all.
     * 
     * @return the all
     * @throws DataException the data exception
     */
    public List<Relationship> getAll() throws DataException {
        try {
            return relationshipDao.getAll();
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
    }

    /**
     * Gets the.
     * 
     * @param code the code
     * @return the relationship
     * @throws DataException the data exception
     */
    public Relationship get(String code) throws DataException {
        try {
            return relationshipDao.get(code);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
    }

    /**
     * Creates the.
     * 
     * @param relationship the relationship
     * @return true, if successful
     * @throws DataException the data exception
     */
    public boolean create(Relationship relationship) throws DataException {
        try {
            relationshipValidator.validate(relationship);
            String code = relationship.getCode().trim();
            List<Relationship> relasionships = getAll();
            if (relasionships != null && !relasionships.isEmpty()) {
                for (Relationship existingRelationship : relasionships) {
                    String existingCode = existingRelationship.getCode();
                    if (existingCode.equalsIgnoreCase(code)) {
                        throw new ValidationException("Relationship '" + code + "' already exists.");
                    }
                }
            }
            return relationshipDao.create(relationship);
        } catch (ValidationException validationException) {
            throw new DataException(validationException.getMessage(), validationException);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
    }

    /**
     * Update.
     * 
     * @param code the code
     * @param relationship the relationship
     * @return true, if successful
     * @throws DataException the data exception
     */
    public boolean update(String code, Relationship relationship) throws DataException {
        try {
            relationship.setCode(code);
            relationshipValidator.validate(code, relationship);
            return relationshipDao.update(code, relationship.getName());
        } catch (ValidationException validationException) {
            throw new DataException(validationException.getMessage(), validationException);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
    }

    /**
     * Delete.
     * 
     * @param code the code
     * @return true, if successful
     * @throws DataException the data exception
     */
    public boolean delete(String code) throws DataException {
        try {
            return relationshipDao.delete(code);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
    }

}
