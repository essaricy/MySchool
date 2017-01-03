package com.myschool.academic.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myschool.academic.domain.AcademicManager;
import com.myschool.academic.dto.AcademicDto;
import com.myschool.common.exception.ServiceException;
import com.quasar.core.exception.DataException;

/**
 * The Class AcademicServiceImpl.
 */
@Service
public class AcademicServiceImpl implements AcademicService {

    /** The academic manager. */
    @Autowired
    private AcademicManager academicManager;

    /* (non-Javadoc)
     * @see com.myschool.service.interfaces.Servicable#create(java.lang.Object)
     */
    @Override
    public boolean create(AcademicDto academic) throws ServiceException {
        boolean created = false;
        try {
            created = academicManager.create(academic);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
        return created;
    }

    /* (non-Javadoc)
     * @see com.myschool.service.interfaces.Servicable#delete(int)
     */
    @Override
    public boolean delete(int academicId) throws ServiceException {
        return false;
    }

    @Override
    public boolean delete(String academicYearName) throws ServiceException {
        boolean deleted = false;
        try {
            deleted = academicManager.delete(academicYearName);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
        return deleted;
    }

    /* (non-Javadoc)
     * @see com.myschool.service.interfaces.Servicable#get(int)
     */
    @Override
    public AcademicDto get(int academicId) throws ServiceException {
        return null;
    }

    /**
     * Gets the.
     *
     * @param academicYearName the academic year name
     * @return the academic dto
     * @throws ServiceException the service exception
     */
    public AcademicDto get(String academicYearName) throws ServiceException {
        AcademicDto academic = null;
        try {
            academic = academicManager.get(academicYearName);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(),
                    dataException);
        }
        return academic;
    }

    /* (non-Javadoc)
     * @see com.myschool.service.interfaces.Servicable#getAll()
     */
    @Override
    public List<AcademicDto> getAll() throws ServiceException {
        List<AcademicDto> academics = null;
        try {
            academics = academicManager.getAll();
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(),
                    dataException);
        }
        return academics;
    }

    /* (non-Javadoc)
     * @see com.myschool.service.interfaces.Servicable#update(int, java.lang.Object)
     */
    @Override
    public boolean update(int id, AcademicDto academic) throws ServiceException {
        return false;
    }

    @Override
    public boolean update(String academicYearName, AcademicDto academic) throws ServiceException {
        boolean updated = false;
        try {
            updated = academicManager.update(academicYearName, academic);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(),
                    dataException);
        }
        return updated;
    }

    /* (non-Javadoc)
     * @see com.myschool.service.interfaces.AcademicService#getCurrentAcademic()
     */
    @Override
    public AcademicDto getCurrentAcademic() throws ServiceException {
        AcademicDto academic = null;
        try {
            academic = academicManager.getCurrentAcademic();
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(),
                    dataException);
        }
        return academic;
    }

    /* (non-Javadoc)
     * @see com.myschool.service.interfaces.AcademicService#getNextAcademic()
     */
    @Override
    public AcademicDto getNextAcademic() throws ServiceException {
        AcademicDto academic = null;
        try {
            academic = academicManager.getNextAcademic();
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(),
                    dataException);
        }
        return academic;
    }

}
