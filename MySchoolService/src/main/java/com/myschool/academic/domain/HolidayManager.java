package com.myschool.academic.domain;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.academic.dao.HolidayDao;
import com.myschool.academic.dto.HolidayDto;
import com.myschool.academic.dto.HolidaySearchCriteria;
import com.myschool.common.exception.DaoException;
import com.quasar.core.exception.DataException;
import com.quasar.core.exception.InsufficientInputException;
import com.quasar.core.exception.InvalidDataException;

/**
 * The Class HolidayManager.
 */
@Component
public class HolidayManager {

    /** The holiday dao. */
    @Autowired
    private HolidayDao holidayDao;

    /**
     * Creates the.
     *
     * @param holidayDto the holiday dto
     * @return true, if successful
     * @throws DataException the data exception
     */
    public boolean create(HolidayDto holidayDto) throws DataException {
        boolean created = false;
        try {
            if (holidayDto == null) {
                throw new InsufficientInputException("holidayDto is null");
            }
            String holidayName = holidayDto.getHolidayName();
            if (holidayName == null || holidayName.trim().length() == 0) {
                throw new InsufficientInputException("Holiday Name is a required value.");
            }
            created = holidayDao.create(holidayDto) > 0;
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return created;
    }

    /**
     * Delete.
     *
     * @param holidayId the holiday id
     * @return true, if successful
     * @throws DataException the data exception
     */
    public boolean delete(int holidayId) throws DataException {
        boolean deleted = false;
        try {
            if (holidayId <= 0) {
                throw new InvalidDataException("Invalid Holiday ID.");
            }
            deleted = holidayDao.delete(holidayId);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return deleted;
    }

    /**
     * Gets the.
     *
     * @param holidayId the holiday id
     * @return the holiday dto
     * @throws DataException the data exception
     */
    public HolidayDto get(int holidayId) throws DataException {
        HolidayDto holidayDto = null;
        try {
            if (holidayId <= 0) {
                throw new InvalidDataException("Invalid Holiday ID.");
            }
            holidayDto = holidayDao.get(holidayId);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return holidayDto;
    }

    /**
     * Gets the all.
     *
     * @return the all
     * @throws DataException the data exception
     */
    public List<HolidayDto> getAll() throws DataException {
        List<HolidayDto> holidays = null;
        try {
            holidays = holidayDao.getAll();
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return holidays;
    }

    /**
     * Update.
     *
     * @param holidayId the holiday id
     * @param holidayDto the holiday dto
     * @return true, if successful
     * @throws DataException the data exception
     */
    public boolean update(int holidayId, HolidayDto holidayDto) throws DataException {
        boolean created = false;
        try {
            if (holidayDto == null) {
                throw new InsufficientInputException("holidayDto is null");
            }
            String holidayName = holidayDto.getHolidayName();
            if (holidayName == null || holidayName.trim().length() == 0) {
                throw new InsufficientInputException("Holiday Name is a required value.");
            }
            created = holidayDao.update(holidayId, holidayDto);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return created;
    }

    /**
     * Gets the all.
     * 
     * @param holidaySearchCriteria the holiday search criteria
     * @return the all
     * @throws DataException the data exception
     */
    public List<HolidayDto> getAll(HolidaySearchCriteria holidaySearchCriteria) throws DataException {
        List<HolidayDto> holidays = null;
        try {
            holidays = holidayDao.getAll(holidaySearchCriteria);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return holidays;
    }
}
