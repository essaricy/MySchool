package com.myschool.application.dao;

import java.util.List;

import com.myschool.common.dto.Relationship;
import com.myschool.common.exception.DaoException;

/**
 * The Interface RelationshipDao.
 */
public interface RelationshipDao {

    /**
     * Gets the all.
     * 
     * @return the all
     * @throws DaoException the dao exception
     */
    List<Relationship> getAll() throws DaoException;

    /**
     * Gets the.
     * 
     * @param relationshipCode the relationship code
     * @return the relationship
     * @throws DaoException the dao exception
     */
    Relationship get(String relationshipCode) throws DaoException;

    /**
     * Gets the by name.
     * 
     * @param relationshipName the relationship name
     * @return the by name
     * @throws DaoException the dao exception
     */
    Relationship getByName(String relationshipName) throws DaoException;

    /**
     * Creates the.
     * 
     * @param relationship the relationship
     * @return true, if successful
     * @throws DaoException the dao exception
     */
    boolean create(Relationship relationship) throws DaoException;

    /**
     * Update.
     * 
     * @param relationshipCode the relationship code
     * @param relationshipName the relationship name
     * @return true, if successful
     * @throws DaoException the dao exception
     */
    boolean update(String relationshipCode, String relationshipName) throws DaoException;

    /**
     * Delete.
     * 
     * @param relationshipCode the relationship code
     * @return true, if successful
     * @throws DaoException the dao exception
     */
    boolean delete(String relationshipCode) throws DaoException;

}
