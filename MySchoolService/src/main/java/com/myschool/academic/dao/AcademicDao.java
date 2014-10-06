package com.myschool.academic.dao;

import java.util.List;

import com.myschool.academic.dto.AcademicDto;
import com.myschool.common.exception.DaoException;

/**
 * The Interface AcademicDao.
 */
public interface AcademicDao {

    /**
     * Gets the all.
     *
     * @return the all
     * @throws DaoException the dao exception
     */
    List<AcademicDto> getAll() throws DaoException;

    /**
     * Creates the.
     *
     * @param academic the academic
     * @return true, if successful
     * @throws DaoException the dao exception
     */
    boolean create(AcademicDto academic) throws DaoException;

    /**
     * Update.
     * 
     * @param academic the academic
     * @return true, if successful
     * @throws DaoException the dao exception
     */
    boolean update(AcademicDto academic) throws DaoException;

    /**
     * Gets the current academic.
     *
     * @return the current academic
     * @throws DaoException the dao exception
     */
    AcademicDto getCurrentAcademic() throws DaoException;

    /**
     * Gets the next academic.
     *
     * @return the next academic
     * @throws DaoException the dao exception
     */
    AcademicDto getNextAcademic() throws DaoException;

    /**
     * Delete.
     * 
     * @param academicYearName the academic year name
     * @return true, if successful
     * @throws DaoException the dao exception
     */
    boolean delete(String academicYearName) throws DaoException;

    /**
     * Gets the.
     * 
     * @param academicYearName the academic year name
     * @return the academic dto
     * @throws DaoException the dao exception
     */
    AcademicDto get(String academicYearName) throws DaoException;

}
