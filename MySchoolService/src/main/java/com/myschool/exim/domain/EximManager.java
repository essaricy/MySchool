package com.myschool.exim.domain;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.common.exception.DaoException;
import com.myschool.common.exception.DataException;
import com.myschool.exim.dao.EximDao;
import com.myschool.exim.dto.EximDto;

/**
 * The Class EximManager.
 */
@Component
public class EximManager {

    /** The exim dao. */
    @Autowired
    private EximDao eximDao;

    /**
     * Gets the all.
     *
     * @return the all
     * @throws DataException the data exception
     */
    public List<EximDto> getAll() throws DataException {
        List<EximDto> exims = null;
        try {
            exims = eximDao.getAll();
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return exims;
    }

    /**
     * Gets the all imports.
     *
     * @return the all imports
     * @throws DataException the data exception
     */
    public List<EximDto> getAllImports() throws DataException {
        List<EximDto> imports = null;
        try {
            imports = eximDao.getAllImports();
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return imports;
    }

    /**
     * Gets the all exports.
     *
     * @return the all exports
     * @throws DataException the data exception
     */
    public List<EximDto> getAllExports() throws DataException {
        List<EximDto> exports = null;
        try {
            exports = eximDao.getAllExports();
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return exports;
    }
}
