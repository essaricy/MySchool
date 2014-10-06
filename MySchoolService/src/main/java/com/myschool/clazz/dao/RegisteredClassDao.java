package com.myschool.clazz.dao;

import java.util.List;

import com.myschool.clazz.dto.RegisteredClassDto;
import com.myschool.common.exception.DaoException;

/**
 * The Interface RegisteredClassDao.
 */
public interface RegisteredClassDao {

    /**
     * Gets the all.
     * 
     * @return the operating classes
     * @throws DaoException the dao exception
     */
    public List<RegisteredClassDto> getAll() throws DaoException;

    /**
     * Gets the.
     * 
     * @param registeredClassId the registered class id
     * @return the registered class dto
     * @throws DaoException the dao exception
     */
    public RegisteredClassDto get(int registeredClassId) throws DaoException;

    /**
     * Gets the by school.
     *
     * @param schoolId the school id
     * @return the by school
     * @throws DaoException the dao exception
     */
    List<RegisteredClassDto> getBySchool(int schoolId) throws DaoException;

    /**
     * Creates the.
     * 
     * @param schoolId the school id
     * @param registeredClass the registered class
     * @return true, if successful
     * @throws DaoException the dao exception
     */
    boolean createBySchool(int schoolId, RegisteredClassDto registeredClass) throws DaoException;

    /**
     * Update.
     * 
     * @param registeredClassId the registered class id
     * @param registeredClass the registered class
     * @return true, if successful
     * @throws DaoException the dao exception
     */
    boolean update(int registeredClassId, RegisteredClassDto registeredClass) throws DaoException;

    /**
     * Delete.
     * 
     * @param registeredClassId the registered class id
     * @return true, if successful
     * @throws DaoException the dao exception
     */
    boolean delete(int registeredClassId) throws DaoException;

    /**
     * Gets the by student.
     *
     * @param admissionNumber the admission number
     * @return the by student
     * @throws DaoException the dao exception
     */
    public RegisteredClassDto getByStudent(String admissionNumber) throws DaoException;

    /**
     * Gets the.
     * 
     * @param registeredClassDto the registered class dto
     * @return the registered class dto
     * @throws DaoException the dao exception
     */
    public RegisteredClassDto get(RegisteredClassDto registeredClassDto) throws DaoException;

}
