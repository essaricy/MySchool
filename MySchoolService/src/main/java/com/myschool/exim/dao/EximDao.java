package com.myschool.exim.dao;

import java.util.List;

import com.myschool.common.exception.DaoException;
import com.myschool.exim.dto.EximDto;

/**
 * The Interface ImportDao.
 */
public interface EximDao {

    /**
     * Gets the all.
     *
     * @return the all
     * @throws DaoException the dao exception
     */
    List<EximDto> getAll() throws DaoException;

    /**
     * Gets the all imports.
     *
     * @return the all imports
     * @throws DaoException the dao exception
     */
    List<EximDto> getAllImports() throws DaoException;

    /**
     * Gets the all exports.
     *
     * @return the all exports
     * @throws DaoException the dao exception
     */
    List<EximDto> getAllExports() throws DaoException;

}
