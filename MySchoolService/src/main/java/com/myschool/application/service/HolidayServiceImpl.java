package com.myschool.application.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myschool.academic.domain.HolidayManager;
import com.myschool.academic.dto.HolidayDto;
import com.myschool.common.exception.DataException;
import com.myschool.common.exception.ServiceException;

@Service
public class HolidayServiceImpl implements HolidayService {

    /** The holiday manager. */
    @Autowired
    private HolidayManager holidayManager;

    /* (non-Javadoc)
     * @see com.myschool.service.interfaces.Servicable#create(java.lang.Object)
     */
    @Override
    public boolean create(HolidayDto holidayDto) throws ServiceException {
        boolean created = false;
        try {
            created = holidayManager.create(holidayDto);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
        return created;
    }

    /* (non-Javadoc)
     * @see com.myschool.service.interfaces.Servicable#delete(int)
     */
    @Override
    public boolean delete(int holidayId) throws ServiceException {
        boolean deleted = false;
        try {
            deleted = holidayManager.delete(holidayId);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
        return deleted;
    }

    /* (non-Javadoc)
     * @see com.myschool.service.interfaces.Servicable#get(int)
     */
    @Override
    public HolidayDto get(int holidayId) throws ServiceException {
        HolidayDto holidayDto = null;
        try {
            holidayDto = holidayManager.get(holidayId);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
        return holidayDto;
    }

    /* (non-Javadoc)
     * @see com.myschool.service.interfaces.Servicable#getAll()
     */
    @Override
    public List<HolidayDto> getAll() throws ServiceException {
        List<HolidayDto> mediumes = null;
        try {
            mediumes = holidayManager.getAll();
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(),
                    dataException);
        }
        return mediumes;
    }

    /* (non-Javadoc)
     * @see com.myschool.service.interfaces.Servicable#update(int, java.lang.Object)
     */
    @Override
    public boolean update(int holidayId, HolidayDto holidayDto)
            throws ServiceException {
        boolean updated = false;
        try {
            updated = holidayManager.update(holidayId, holidayDto);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
        return updated;
    }
}
