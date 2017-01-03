package com.myschool.branch.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myschool.branch.domain.DivisionManager;
import com.myschool.branch.dto.DivisionDto;
import com.myschool.common.exception.ServiceException;
import com.quasar.core.exception.DataException;

/**
 * The Class DivisionServiceImpl.
 */
@Service
public class DivisionServiceImpl implements DivisionService {

    /** The division manager. */
    @Autowired
    private DivisionManager divisionManager;

    /* (non-Javadoc)
     * @see com.myschool.service.interfaces.Servicable#create(java.lang.Object)
     */
    @Override
    public boolean create(DivisionDto divisionDto) throws ServiceException {
        try {
            return divisionManager.create(divisionDto);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(),
                    dataException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.service.interfaces.Servicable#delete(int)
     */
    @Override
    public boolean delete(int divisionId) throws ServiceException {
        try {
            return divisionManager.delete(divisionId);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(),
                    dataException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.service.interfaces.Servicable#get(int)
     */
    @Override
    public DivisionDto get(int divisionId) throws ServiceException {
        DivisionDto division = null;
        try {
            division = divisionManager.get(divisionId);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(),
                    dataException);
        }
        return division;
    }

    /* (non-Javadoc)
     * @see com.myschool.service.interfaces.Servicable#getAll()
     */
    @Override
    public List<DivisionDto> getAll() throws ServiceException {
        List<DivisionDto> divisions = null;
        try {
            divisions = divisionManager.getAll();
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(),
                    dataException);
        }
        return divisions;
    }

    /* (non-Javadoc)
     * @see com.myschool.service.interfaces.Servicable#update(int, java.lang.Object)
     */
    @Override
    public boolean update(int divisionId, DivisionDto divisionDto)
            throws ServiceException {
        try {
            return divisionManager.update(divisionId, divisionDto);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(),
                    dataException);
        }
    }

}
