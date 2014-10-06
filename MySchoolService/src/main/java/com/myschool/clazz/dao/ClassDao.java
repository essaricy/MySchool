package com.myschool.clazz.dao;

import java.util.List;

import com.myschool.clazz.dto.ClassDto;
import com.myschool.common.exception.DaoException;

/**
 * The Interface ClassDao.
 */
public interface ClassDao {

    /**
     *
     * @param classDto the class dto
     * @return the int
     * @throws DaoException the dao exception
     */
    public int create(ClassDto classDto) throws DaoException;

    /**
     * Gets the all.
     *
     * @return the all
     * @throws DaoException the dao exception
     */
    public List<ClassDto> getAll() throws DaoException;

    /**
     * Gets the.
     *
     * @param classId the class id
     * @return the class dto
     * @throws DaoException the dao exception
     */
    public ClassDto get(int classId) throws DaoException;

    /**
     *
     * @param classId the class id
     * @param classDto the class dto
     * @return true, if successful
     * @throws DaoException the dao exception
     */
    public boolean update(int classId, ClassDto classDto) throws DaoException;

    /**
     *
     * @param classId the class id
     * @return true, if successful
     * @throws DaoException the dao exception
     */
    public boolean delete(int classId) throws DaoException;

    /**
     * Gets the.
     *
     * @param className the class name
     * @return the class dto
     * @throws DaoException the dao exception
     */
    public ClassDto get(String className) throws DaoException;

}
