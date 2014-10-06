package com.myschool.student.dao;

import java.util.List;

import com.myschool.common.exception.DaoException;
import com.myschool.student.dto.AdmissionStatus;

/**
 * The Interface AdmissionStatusDao.
 */
public interface AdmissionStatusDao {

    /**
     * Creates the.
     * 
     * @param admissionStatus the admission status
     * @return the int
     * @throws DaoException the dao exception
     */
    public int create(AdmissionStatus admissionStatus) throws DaoException;

    /**
     * Gets the all.
     * 
     * @return the all
     * @throws DaoException the dao exception
     */
    public List<AdmissionStatus> getAll() throws DaoException;

    /**
     * Gets the.
     * 
     * @param admissionStatusId the admission status id
     * @return the employment status
     * @throws DaoException the dao exception
     */
    public AdmissionStatus get(int admissionStatusId) throws DaoException;

    /**
     * Update.
     * 
     * @param admissionStatusId the admission status id
     * @param admissionStatus the admission status
     * @return true, if successful
     * @throws DaoException the dao exception
     */
    public boolean update(int admissionStatusId, AdmissionStatus admissionStatus)
            throws DaoException;

    /**
     * Delete.
     * 
     * @param admissionStatusId the admission status id
     * @return true, if successful
     * @throws DaoException the dao exception
     */
    public boolean delete(int admissionStatusId) throws DaoException;

    /**
     * Gets the.
     * 
     * @param description the description
     * @return the employment status
     * @throws DaoException the dao exception
     */
    public AdmissionStatus get(String description) throws DaoException;

}
