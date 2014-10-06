package com.myschool.employee.dao;

import java.util.List;

import com.myschool.common.exception.DaoException;
import com.myschool.employee.dto.EmploymentStatus;

/**
 * The Interface EmploymentStatusDao.
 */
public interface EmploymentStatusDao {

    /**
     * Creates the.
     * 
     * @param employmentStatus the employment status
     * @return the int
     * @throws DaoException the dao exception
     */
    public int create(EmploymentStatus employmentStatus)
            throws DaoException;

    /**
     * Gets the all.
     * 
     * @return the all
     * @throws DaoException the dao exception
     */
    public List<EmploymentStatus> getAll() throws DaoException;

    /**
     * Gets the.
     * 
     * @param employmentStatusId the employment status id
     * @return the employment status
     * @throws DaoException the dao exception
     */
    public EmploymentStatus get(int employmentStatusId) throws DaoException;

    /**
     * Update.
     * 
     * @param employmentStatusId the employment status id
     * @param employmentStatus the employment status
     * @return true, if successful
     * @throws DaoException the dao exception
     */
    public boolean update(int employmentStatusId,
            EmploymentStatus employmentStatus) throws DaoException;

    /**
     * Delete.
     * 
     * @param employmentStatusId the employment status id
     * @return true, if successful
     * @throws DaoException the dao exception
     */
    public boolean delete(int employmentStatusId)
            throws DaoException;

    /**
     * Gets the.
     * 
     * @param description the description
     * @return the employment status
     * @throws DaoException the dao exception
     */
    public EmploymentStatus get(String description) throws DaoException;

}
