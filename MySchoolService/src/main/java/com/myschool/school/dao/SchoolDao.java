package com.myschool.school.dao;

import java.util.List;

import com.myschool.common.exception.DaoException;
import com.myschool.school.dto.SchoolDto;

/**
 * The Interface BranchDao.
 */
public interface SchoolDao {

    /**
     * Creates the.
     * 
     * @param schoolDto the school dto
     * @return the int
     * @throws DaoException the dao exception
     */
    public int create(SchoolDto schoolDto) throws DaoException;

    /**
     * Gets the all.
     *
     * @return the all
     * @throws DaoException the dao exception
     */
    public List<SchoolDto> getAll() throws DaoException;

    /**
     * Gets the.
     *
     * @param schoolId the school id
     * @return the school dto
     * @throws DaoException the dao exception
     */
    public SchoolDto get(int schoolId) throws DaoException;

    /**
     * Update.
     * 
     * @param schoolId the school id
     * @param schoolDto the school dto
     * @return true, if successful
     * @throws DaoException the dao exception
     */
    public boolean update(int schoolId, SchoolDto schoolDto) throws DaoException;

    /**
     * Delete.
     * 
     * @param schoolId the school id
     * @return true, if successful
     * @throws DaoException the dao exception
     */
    public boolean delete(int schoolId) throws DaoException;

    /**
     * Gets the by branch.
     *
     * @param branchId the branch id
     * @return the by branch
     * @throws DaoException the dao exception
     */
    public List<SchoolDto> getByBranch(int branchId) throws DaoException;

    /**
     * Gets the.
     * 
     * @param schoolDto the school dto
     * @return the school dto
     * @throws DaoException the dao exception
     */
    public SchoolDto get(SchoolDto schoolDto) throws DaoException;

    /**
     * Gets the all.
     * 
     * @param schoolDto the school dto
     * @return the all
     * @throws DaoException the dao exception
     */
    public List<SchoolDto> getAll(SchoolDto schoolDto) throws DaoException;

}
