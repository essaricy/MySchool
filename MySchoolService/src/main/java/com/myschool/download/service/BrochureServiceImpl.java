package com.myschool.download.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myschool.common.exception.DataException;
import com.myschool.common.exception.ServiceException;
import com.myschool.download.domain.BrochureManager;
import com.myschool.download.dto.BrochureDto;

/**
 * The Class BrochureServiceImpl.
 */
@Service
public class BrochureServiceImpl implements BrochureService {

    /** The brochure manager. */
    @Autowired
    private BrochureManager brochureManager;

    /* (non-Javadoc)
     * @see com.myschool.application.service.Servicable#create(java.lang.Object)
     */
    @Override
    public boolean create(BrochureDto brochure) throws ServiceException {
        return false;
    }

    /* (non-Javadoc)
     * @see com.myschool.application.service.Servicable#getAll()
     */
    @Override
    public List<BrochureDto> getAll() throws ServiceException {
        try {
            return brochureManager.getAll();
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.application.service.Servicable#get(int)
     */
    @Override
    public BrochureDto get(int id) throws ServiceException {
        return null;
    }

    /* (non-Javadoc)
     * @see com.myschool.application.service.Servicable#update(int, java.lang.Object)
     */
    @Override
    public boolean update(int id, BrochureDto brochure) throws ServiceException {
        return false;
    }

    /* (non-Javadoc)
     * @see com.myschool.application.service.Servicable#delete(int)
     */
    @Override
    public boolean delete(int id) throws ServiceException {
        return false;
    }

}
