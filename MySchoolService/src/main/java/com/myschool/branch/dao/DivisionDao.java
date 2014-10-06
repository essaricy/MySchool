package com.myschool.branch.dao;

import java.util.List;

import com.myschool.branch.dto.DivisionDto;
import com.myschool.common.exception.DaoException;

/**
 * The Interface DivisionDao.
 */
public interface DivisionDao {

    /**
     * Creates the.
     * 
     * @param divisionDto the division dto
     * @return the int
     * @throws DaoException the dao exception
     */
    public int create(DivisionDto divisionDto) throws DaoException;

    /**
     * Gets the all.
     *
     * @return the all
     * @throws DaoException the dao exception
     */
    public List<DivisionDto> getAll() throws DaoException;

    /**
     * Gets the.
     *
     * @param divisionId the division id
     * @return the division dto
     * @throws DaoException the dao exception
     */
    public DivisionDto get(int divisionId) throws DaoException;

    /**
     * Update.
     * 
     * @param divisionId the division id
     * @param divisionDto the division dto
     * @return true, if successful
     * @throws DaoException the dao exception
     */
    public boolean update(int divisionId, DivisionDto divisionDto) throws DaoException;

    /**
     * Delete.
     * 
     * @param divisionId the division id
     * @return true, if successful
     * @throws DaoException the dao exception
     */
    public boolean delete(int divisionId) throws DaoException;

    /**
     * Gets the.
     *
     * @param divisionCode the division code
     * @return the division dto
     * @throws DaoException the dao exception
     */
    public DivisionDto get(String divisionCode) throws DaoException;

}
