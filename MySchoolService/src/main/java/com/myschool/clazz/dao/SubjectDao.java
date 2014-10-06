package com.myschool.clazz.dao;

import java.util.List;

import com.myschool.clazz.dto.SubjectDto;
import com.myschool.common.exception.DaoException;

/**
 * The Interface SubjectDao.
 */
public interface SubjectDao {

    /**
     * Creates the subject.
     *
     * @param subjectDto the subject dto
     * @return the int
     * @throws DaoException the dao exception
     */
    int createSubject(SubjectDto subjectDto) throws DaoException;

    /**
     * Gets the all.
     *
     * @return the all
     * @throws DaoException the dao exception
     */
    List<SubjectDto> getAll() throws DaoException;

    /**
     * Gets the.
     *
     * @param subjectId the subject id
     * @return the subject dto
     * @throws DaoException the dao exception
     */
    SubjectDto get(int subjectId) throws DaoException;

    /**
     * Update subject.
     *
     * @param subjectId the subject id
     * @param subjectDto the subject dto
     * @return true, if successful
     * @throws DaoException the dao exception
     */
    boolean updateSubject(int subjectId, SubjectDto subjectDto) throws DaoException;

    /**
     * Delete subject.
     *
     * @param subjectId the subject id
     * @return true, if successful
     * @throws DaoException the dao exception
     */
    boolean deleteSubject(int subjectId) throws DaoException;

    /**
     * Gets the.
     *
     * @param subjectName the subject name
     * @return the subject dto
     * @throws DaoException the dao exception
     */
    SubjectDto get(String subjectName) throws DaoException;

    /**
     * Creates the.
     *
     * @param subjectName the subject name
     * @return the int
     * @throws DaoException the dao exception
     */
    int create(String subjectName) throws DaoException;

}
