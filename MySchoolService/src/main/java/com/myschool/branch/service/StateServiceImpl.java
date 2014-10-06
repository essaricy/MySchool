package com.myschool.branch.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myschool.branch.domain.StateManager;
import com.myschool.branch.dto.StateDto;
import com.myschool.common.exception.DataException;
import com.myschool.common.exception.ServiceException;

/**
 * The Class StateServiceImpl.
 */
@Service
public class StateServiceImpl implements StateService {

    /** The state manager. */
    @Autowired
    private StateManager stateManager;

    /* (non-Javadoc)
     * @see com.myschool.service.interfaces.Servicable#create(java.lang.Object)
     */
    @Override
    public boolean create(StateDto dto) throws ServiceException {
        return false;
    }

    /* (non-Javadoc)
     * @see com.myschool.service.interfaces.Servicable#getAll()
     */
    @Override
    public List<StateDto> getAll() throws ServiceException {
        List<StateDto> states = null;
        try {
            states = stateManager.getAll();
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(),
                    dataException);
        }
        return states;
    }

    /* (non-Javadoc)
     * @see com.myschool.service.interfaces.Servicable#get(int)
     */
    @Override
    public StateDto get(int id) throws ServiceException {
        return null;
    }

    /* (non-Javadoc)
     * @see com.myschool.service.interfaces.Servicable#update(int, java.lang.Object)
     */
    @Override
    public boolean update(int id, StateDto dto) throws ServiceException {
        return false;
    }

    /* (non-Javadoc)
     * @see com.myschool.service.interfaces.Servicable#delete(int)
     */
    @Override
    public boolean delete(int id) throws ServiceException {
        return false;
    }

}
