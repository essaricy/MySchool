package com.myschool.academic.dao;

import com.myschool.common.exception.DaoException;

/**
 * The Interface AyeProcessDao.
 */
public interface AyeProcessDao {

    /**
     * Sets the next academic year.
     *
     * @param academicYearName the academic year name
     * @return true, if successful
     * @throws DaoException the dao exception
     */
    boolean setNextAcademicYear(String academicYearName) throws DaoException;

}
