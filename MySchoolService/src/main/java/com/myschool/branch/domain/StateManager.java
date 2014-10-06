package com.myschool.branch.domain;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.branch.dao.StateDao;
import com.myschool.branch.dto.StateDto;
import com.myschool.common.exception.DaoException;
import com.myschool.common.exception.DataException;

/**
 * The Class StateManager.
 */
@Component
public class StateManager {

    /** The state dao. */
    @Autowired
    private StateDao stateDao;

    /**
     * Gets the all.
     *
     * @return the all
     * @throws DataException the data exception
     */
    public List<StateDto> getAll() throws DataException {
        List<StateDto> states = null;
        try {
            states = stateDao.getAll();
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return states;
    }

}
