package com.myschool.academic.dao;

import java.util.List;

import com.myschool.academic.dto.HolidayDto;
import com.myschool.academic.dto.HolidaySearchCriteria;
import com.myschool.common.exception.DaoException;

/**
 * The Interface HolidayDao.
 */
public interface HolidayDao {

    /**
     * Creates the.
     * 
     * @param holidayDto the holiday dto
     * @return the int
     * @throws DaoException the dao exception
     */
    int create(HolidayDto holidayDto) throws DaoException;

    /**
     * Delete.
     * 
     * @param holidayId the holiday id
     * @return true, if successful
     * @throws DaoException the dao exception
     */
    boolean delete(int holidayId) throws DaoException;

    /**
     * Gets the.
     *
     * @param holidayId the holiday id
     * @return the holiday dto
     * @throws DaoException the dao exception
     */
    HolidayDto get(int holidayId) throws DaoException;

    /**
     * Gets the all.
     *
     * @return the all
     * @throws DaoException the dao exception
     */
    List<HolidayDto> getAll() throws DaoException;

    /**
     * Update.
     * 
     * @param holidayId the holiday id
     * @param holidayDto the holiday dto
     * @return true, if successful
     * @throws DaoException the dao exception
     */
    boolean update(int holidayId, HolidayDto holidayDto) throws DaoException;

    /**
     * Gets the.
     * 
     * @param holiday the holiday
     * @return the holiday dto
     * @throws DaoException the dao exception
     */
    HolidayDto get(HolidayDto holiday) throws DaoException;

    /**
     * Gets the all.
     * 
     * @param holidaySearchCriteria the holiday search criteria
     * @return the all
     * @throws DaoException the dao exception
     */
    List<HolidayDto> getAll(HolidaySearchCriteria holidaySearchCriteria) throws DaoException;

}
