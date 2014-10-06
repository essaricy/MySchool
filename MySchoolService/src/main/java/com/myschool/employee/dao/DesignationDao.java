package com.myschool.employee.dao;

import java.util.List;

import com.myschool.common.exception.DaoException;
import com.myschool.employee.dto.DesignationDto;

/**
 * The Interface DesignationDao.
 */
public interface DesignationDao {

    /**
     * Gets the all.
     *
     * @return the all
     * @throws DaoException the dao exception
     */
    List<DesignationDto> getAll() throws DaoException;

    /**
     * Creates the.
     * 
     * @param designation the designation
     * @return true, if successful
     * @throws DaoException the dao exception
     */
    boolean create(DesignationDto designation) throws DaoException;

    /**
     * Gets the.
     *
     * @param designationId the designation id
     * @return the designation dto
     * @throws DaoException the dao exception
     */
    DesignationDto get(int designationId) throws DaoException;

    /**
     * Delete.
     *
     * @param designationId the designation id
     * @return true, if successful
     * @throws DaoException the dao exception
     */
    boolean delete(int designationId) throws DaoException;

    /**
     * Update.
     *
     * @param designationId the designation id
     * @param designation the designation
     * @return true, if successful
     * @throws DaoException the dao exception
     */
    boolean update(int designationId, DesignationDto designation) throws DaoException;

}
