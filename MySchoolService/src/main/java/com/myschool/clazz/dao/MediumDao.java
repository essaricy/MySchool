package com.myschool.clazz.dao;

import java.util.List;

import com.myschool.clazz.dto.MediumDto;
import com.myschool.common.exception.DaoException;

/**
 * The Interface MediumDao.
 */
public interface MediumDao {

    /**
     * Creates the medium.
     *
     * @param mediumDto the medium dto
     * @return the int
     * @throws DaoException the dao exception
     */
    public int createMedium(MediumDto mediumDto) throws DaoException;

    /**
     * Gets the all.
     *
     * @return the all
     * @throws DaoException the dao exception
     */
    public List<MediumDto> getAll() throws DaoException;

    /**
     * Gets the.
     *
     * @param mediumId the medium id
     * @return the medium dto
     * @throws DaoException the dao exception
     */
    public MediumDto get(int mediumId) throws DaoException;

    /**
     * Update medium.
     *
     * @param mediumId the medium id
     * @param mediumDto the medium dto
     * @return true, if successful
     * @throws DaoException the dao exception
     */
    public boolean updateMedium(int mediumId, MediumDto mediumDto) throws DaoException;

    /**
     * Delete medium.
     *
     * @param mediumId the medium id
     * @return true, if successful
     * @throws DaoException the dao exception
     */
    public boolean deleteMedium(int mediumId) throws DaoException;

    /**
     * Gets the.
     *
     * @param mediumName the medium name
     * @return the medium dto
     * @throws DaoException the dao exception
     */
    public MediumDto get(String mediumName) throws DaoException;

    /**
     * Creates the.
     *
     * @param mediumName the medium name
     * @return the int
     * @throws DaoException the dao exception
     */
    public int create(String mediumName) throws DaoException;
}
