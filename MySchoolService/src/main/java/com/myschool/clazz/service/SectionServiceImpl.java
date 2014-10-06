package com.myschool.clazz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myschool.clazz.domain.SectionManager;
import com.myschool.clazz.dto.SectionDto;
import com.myschool.common.exception.DataException;
import com.myschool.common.exception.ServiceException;

@Service
public class SectionServiceImpl implements SectionService {

    /** The section manager. */
    @Autowired
    private SectionManager sectionManager;

    /* (non-Javadoc)
     * @see com.myschool.service.interfaces.Servicable#create(java.lang.Object)
     */
    @Override
    public boolean create(SectionDto sectionDto) throws ServiceException {
        boolean created = false;
        try {
            created = sectionManager.create(sectionDto);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
        return created;
    }

    /* (non-Javadoc)
     * @see com.myschool.service.interfaces.Servicable#delete(int)
     */
    @Override
    public boolean delete(int sectionId) throws ServiceException {
        boolean deleted = false;
        try {
            deleted = sectionManager.delete(sectionId);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
        return deleted;
    }

    /* (non-Javadoc)
     * @see com.myschool.service.interfaces.Servicable#get(int)
     */
    @Override
    public SectionDto get(int sectionId) throws ServiceException {
        SectionDto sectionDto = null;
        try {
            sectionDto = sectionManager.get(sectionId);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
        return sectionDto;
    }

    /* (non-Javadoc)
     * @see com.myschool.service.interfaces.Servicable#getAll()
     */
    @Override
    public List<SectionDto> getAll() throws ServiceException {
        List<SectionDto> mediumes = null;
        try {
            mediumes = sectionManager.getAll();
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
    public boolean update(int sectionId, SectionDto sectionDto)
            throws ServiceException {
        boolean updated = false;
        try {
            updated = sectionManager.update(sectionId, sectionDto);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
        return updated;
    }

}
