package com.myschool.branch.dao;

import java.util.List;

import com.myschool.branch.dto.StateDto;
import com.myschool.common.exception.DaoException;

/**
 * The Interface StateDao.
 */
public interface StateDao {

    /**
     * Creates the.
     *
     * @param stateName the state name
     * @return the int
     * @throws DaoException the dao exception
     */
    int create(String stateName) throws DaoException;

    /**
     * Gets the.
     *
     * @param stateName the state name
     * @return the state dto
     * @throws DaoException the dao exception
     */
    StateDto get(String stateName) throws DaoException;

    /**
     * Gets the all.
     *
     * @return the all
     * @throws DaoException the dao exception
     */
    List<StateDto> getAll() throws DaoException;

}
