package com.myschool.clazz.dao;

import java.util.List;

import com.myschool.clazz.dto.RegisteredSubjectDto;
import com.myschool.common.exception.DaoException;

/**
 * The Interface RegisteredSubjectDao.
 */
public interface RegisteredSubjectDao {

    /**
     * Gets the by class.
     *
     * @param classId the class id
     * @return the by class
     * @throws DaoException the dao exception
     */
    List<RegisteredSubjectDto> getByClass(int classId) throws DaoException;

    /**
     * Gets the.
     *
     * @param registeredSubjectId the registered subject id
     * @return the registered subject dto
     * @throws DaoException the dao exception
     */
    RegisteredSubjectDto get(int registeredSubjectId) throws DaoException;

    /**
     * Gets the.
     *
     * @param classId the class id
     * @param subjectId the subject id
     * @return the registered subject dto
     * @throws DaoException the dao exception
     */
    RegisteredSubjectDto get(int classId, int subjectId) throws DaoException;

    /**
     * Creates the.
     *
     * @param registeredSubject the registered subject
     * @return true, if successful
     * @throws DaoException the dao exception
     */
    boolean create(RegisteredSubjectDto registeredSubject) throws DaoException;

    /**
     * Update.
     *
     * @param registeredSubjectId the registered subject id
     * @param registeredSubject the registered subject
     * @return true, if successful
     * @throws DaoException the dao exception
     */
    boolean update(int registeredSubjectId, RegisteredSubjectDto registeredSubject) throws DaoException;

    /**
     * Delete.
     *
     * @param classId the class id
     * @param subjectId the subject id
     * @return true, if successful
     * @throws DaoException the dao exception
     */
    boolean delete(int classId, int subjectId) throws DaoException;

    /**
     * Delete.
     *
     * @param registeredSubjectId the registered subject id
     * @return true, if successful
     * @throws DaoException the dao exception
     */
    boolean delete(int registeredSubjectId) throws DaoException;

}
