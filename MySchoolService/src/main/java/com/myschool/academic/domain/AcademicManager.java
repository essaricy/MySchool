package com.myschool.academic.domain;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.academic.dao.AcademicDao;
import com.myschool.academic.dto.AcademicDto;
import com.myschool.common.exception.DaoException;
import com.myschool.common.exception.DataException;
import com.myschool.common.exception.InsufficientInputException;
import com.myschool.common.exception.InvalidDataException;
import com.myschool.common.util.DateUtil;

/**
 * The Class AcademicManager.
 */
@Component
public class AcademicManager {

    /** The academic dao. */
    @Autowired
    private AcademicDao academicDao;

    /**
     * Gets the all.
     *
     * @return the all
     * @throws DataException the data exception
     */
    public List<AcademicDto> getAll() throws DataException {
        List<AcademicDto> academics = null;
        try {
            academics = academicDao.getAll();
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return academics;
    }

    /**
     * Creates the.
     *
     * @param academic the academic
     * @return true, if successful
     * @throws DataException the data exception
     */
    public boolean create(AcademicDto academic) throws DataException {
        boolean created = false;
        String academicYearName = null;
        String academicYearStartDate = null;
        String academicYearEndDate = null;
        String existingAcademicYearName = null;

        try {
            if (academic == null) {
                throw new InsufficientInputException("Academic is null");
            }
            academicYearName = academic.getAcademicYearName();
            academicYearStartDate = academic.getAcademicYearStartDate();
            academicYearEndDate = academic.getAcademicYearEndDate();

            // check whether this academic is already present or not.
            List<AcademicDto> academics = academicDao.getAll();
            if (academics != null) {
                for (AcademicDto existingAcademic : academics) {
                    existingAcademicYearName = existingAcademic.getAcademicYearName();
                    if (existingAcademicYearName.equals(academicYearName)) {
                        throw new InvalidDataException("Academic with name " + academicYearName + " already exists.");
                    }
                    // Check if the date clashes
                    DateUtil.checkDateOverlap(academicYearStartDate, academicYearEndDate,
                            existingAcademic.getAcademicYearStartDate(), existingAcademic.getAcademicYearEndDate());
                }
            }
            /*academic.setAcademicYearStartDate(DataTypeConversionUtil.toStringDate(
                    academic.getAcademicYearStartDate(), FormatUtil.dd_MM_yyyy, FormatUtil.yyyy_MM_dd));
            academic.setAcademicYearEndDate(DataTypeConversionUtil.toStringDate(
                    academic.getAcademicYearEndDate(), FormatUtil.dd_MM_yyyy, FormatUtil.yyyy_MM_dd));*/
            created = academicDao.create(academic);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return created;
    }

    /**
     * Gets the current academic.
     *
     * @return the current academic
     * @throws DataException the data exception
     */
    public AcademicDto getCurrentAcademic() throws DataException {
        AcademicDto academic = null;
        try {
            academic = academicDao.getCurrentAcademic();
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return academic;
    }

    /**
     * Gets the next academic.
     *
     * @return the next academic
     * @throws DataException the data exception
     */
    public AcademicDto getNextAcademic() throws DataException {
        AcademicDto academic = null;
        try {
            academic = academicDao.getNextAcademic();
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return academic;
    }

    /**
     * Update.
     * 
     * @param academicYearName the academic year name
     * @param academic the academic
     * @return true, if successful
     * @throws DataException the data exception
     */
    public boolean update(String academicYearName, AcademicDto academic) throws DataException {
        boolean updated = false;
        String academicYearStartDate = null;
        String academicYearEndDate = null;
        String existingAcademicYearName = null;

        try {
            if (academic == null) {
                throw new InsufficientInputException("Academic is null");
            }
            academicYearStartDate = academic.getAcademicYearStartDate();
            academicYearEndDate = academic.getAcademicYearEndDate();

            // check whether this academic is already present or not.
            List<AcademicDto> academics = academicDao.getAll();
            if (academics != null) {
                boolean isExisting = false;
                for (AcademicDto existingAcademic : academics) {
                    existingAcademicYearName = existingAcademic.getAcademicYearName();
                    if (academicYearName.equalsIgnoreCase(existingAcademicYearName)) {
                        isExisting = true;
                        break;
                    }
                }
                if (isExisting) {
                    for (AcademicDto existingAcademic : academics) {
                        existingAcademicYearName = existingAcademic.getAcademicYearName();
                        if (!academicYearName.equalsIgnoreCase(existingAcademicYearName)) {
                            // Check if the date clashes
                            DateUtil.checkDateOverlap(academicYearStartDate, academicYearEndDate,
                                    existingAcademic.getAcademicYearStartDate(), existingAcademic.getAcademicYearEndDate());
                        }
                    }
                } else {
                    throw new InvalidDataException("Academic with name " + academicYearName + " not found.");
                }
            }
            academic.setAcademicYearName(academicYearName);
            /*academic.setAcademicYearStartDate(DataTypeConversionUtil.toStringDate(
                    academic.getAcademicYearStartDate(), FormatUtil.dd_MM_yyyy, FormatUtil.yyyy_MM_dd));
            academic.setAcademicYearEndDate(DataTypeConversionUtil.toStringDate(
                    academic.getAcademicYearEndDate(), FormatUtil.dd_MM_yyyy, FormatUtil.yyyy_MM_dd));*/
            updated = academicDao.update(academic);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return updated;
    }

    /**
     * Delete.
     * 
     * @param academicYearName the academic year name
     * @return true, if successful
     * @throws DataException the data exception
     */
    public boolean delete(String academicYearName) throws DataException {
        boolean deleted = false;
        String existingAcademicYearName = null;

        try {
            if (academicYearName == null) {
                throw new InsufficientInputException("AcademicYearName is null");
            }

            // check whether this academic is already present or not.
            List<AcademicDto> academics = academicDao.getAll();
            if (academics != null) {
                boolean isExisting = false;
                for (AcademicDto existingAcademic : academics) {
                    existingAcademicYearName = existingAcademic.getAcademicYearName();
                    if (academicYearName.equalsIgnoreCase(existingAcademicYearName)) {
                        isExisting = true;
                        break;
                    }
                }
                if (!isExisting) {
                    throw new InvalidDataException("Academic with name " + academicYearName + " not found.");
                }
            }
            deleted = academicDao.delete(academicYearName);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return deleted;
    }

    /**
     * Gets the.
     * 
     * @param academicYearName the academic year name
     * @return the academic dto
     * @throws DataException the data exception
     */
    public AcademicDto get(String academicYearName) throws DataException {
        AcademicDto academic = null;
        try {
            academic = academicDao.get(academicYearName);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return academic;
    }

}
