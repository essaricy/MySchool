package com.myschool.school.domain;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.common.exception.DaoException;
import com.myschool.common.exception.DataException;
import com.myschool.common.exception.InsufficientInputException;
import com.myschool.common.exception.InvalidDataException;
import com.myschool.school.dao.SchoolDao;
import com.myschool.school.dto.SchoolDto;

/**
 * The Class BranchManagerImpl.
 */
@Component
public class SchoolManager {

    /** The school dao. */
    @Autowired
    private SchoolDao schoolDao;

    /**
     * Creates the.
     *
     * @param schoolDto the school dto
     * @return true, if successful
     * @throws DataException the data exception
     */
    public boolean create(SchoolDto schoolDto) throws DataException {
        boolean created = false;
        try {
            if (schoolDto == null) {
                throw new InsufficientInputException("schoolDto is null");
            }
            String schoolName = schoolDto.getSchoolName();
            if (schoolName == null || schoolName.trim().length() == 0) {
                throw new InsufficientInputException("School Name is a required value.");
            }            
            String address = schoolDto.getAddress();
            if (address == null || address.trim().length() == 0) {
                throw new InsufficientInputException("school address is a required value.");
            }
            String primaryPhoneNumber = schoolDto.getPrimaryPhoneNumber();
            if (primaryPhoneNumber == null || primaryPhoneNumber.trim().length() == 0) {
                throw new InsufficientInputException("Primary Phone Number is a required value.");
            }
            String mobileNumber = schoolDto.getMobileNumber();
            if (mobileNumber == null || mobileNumber.trim().length() == 0) {
                throw new InsufficientInputException("Mobile Number is a required value.");
            }
            // Email ID, Secondary Phone Number and Fax Number is not mandatory
            created = (schoolDao.create(schoolDto) > 0) ? true : false;
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return created;
    }

    /**
     * Delete.
     *
     * @param schoolId the school id
     * @return true, if successful
     * @throws DataException the data exception
     */
    public boolean delete(int schoolId) throws DataException {
        boolean deleted = false;
        try {
            if (schoolId <= 0) {
                throw new InvalidDataException("Invalid school ID.");
            }
            deleted = schoolDao.delete(schoolId);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return deleted;
    }

    /**
     * Gets the.
     *
     * @param schoolId the school id
     * @return the school dto
     * @throws DataException the data exception
     */
    public SchoolDto get(int schoolId) throws DataException {
        SchoolDto school = null;
        try {
            if (schoolId <= 0) {
                throw new InvalidDataException("Invalid school ID.");
            }
            school = schoolDao.get(schoolId);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return school;
    }

    /**
     * Gets the all.
     *
     * @return the all
     * @throws DataException the data exception
     */
    public List<SchoolDto> getAll() throws DataException {
        List<SchoolDto> schools = null;
        try {
            schools = schoolDao.getAll();
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return schools;
    }

    /**
     * Update.
     *
     * @param schoolId the school id
     * @param schoolDto the school dto
     * @return true, if successful
     * @throws DataException the data exception
     */
    public boolean update(int schoolId, SchoolDto schoolDto) throws DataException {
        boolean created = false;
        try {
            if (schoolDto == null) {
                throw new InsufficientInputException("schoolDto is null");
            }
            if (schoolId <= 0) {
                throw new InvalidDataException("Invalid school ID.");
            }
            String schoolName = schoolDto.getSchoolName();
            if (schoolName == null || schoolName.trim().length() == 0) {
                throw new InsufficientInputException("school name is a required value.");
            }            
            String address = schoolDto.getAddress();
            if (address == null || address.trim().length() == 0) {
                throw new InsufficientInputException("school address is a required value.");
            }
            String primaryPhoneNumber = schoolDto.getPrimaryPhoneNumber();
            if (primaryPhoneNumber == null || primaryPhoneNumber.trim().length() == 0) {
                throw new InsufficientInputException("Primary Phone Number is a required value.");
            }
            String mobileNumber = schoolDto.getMobileNumber();
            if (mobileNumber == null || mobileNumber.trim().length() == 0) {
                throw new InsufficientInputException("mobile Number is a required value.");
            }
            // Email ID, Secondary Phone Number and Fax Number is not mandatory
            created = schoolDao.update(schoolId, schoolDto);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return created;
    }

    /**
     * Gets the by branch.
     *
     * @param branchId the branch id
     * @return the by branch
     * @throws DataException the data exception
     */
    public List<SchoolDto> getByBranch(int branchId) throws DataException {
        List<SchoolDto> schools = null;
        try {
            schools = schoolDao.getByBranch(branchId);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return schools;
    }

    /**
     * Gets the all.
     * 
     * @param schoolDto the school dto
     * @return the all
     * @throws DataException the data exception
     */
    public List<SchoolDto> getAll(SchoolDto schoolDto) throws DataException {
        List<SchoolDto> schools = null;
        try {
            schools = schoolDao.getAll(schoolDto);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return schools;
    }
}
