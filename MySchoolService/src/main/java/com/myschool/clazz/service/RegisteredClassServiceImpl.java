package com.myschool.clazz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myschool.clazz.domain.RegisteredClassManager;
import com.myschool.clazz.dto.RegisteredClassDto;
import com.myschool.common.exception.ServiceException;
import com.quasar.core.exception.DataException;

/**
 * The Class RegisteredClassServiceImpl.
 */
@Service
public class RegisteredClassServiceImpl implements RegisteredClassService {

    /** The registered class manager. */
    @Autowired
    private RegisteredClassManager registeredClassManager;

    /* (non-Javadoc)
     * @see com.myschool.service.interfaces.ClassService#getBySchool(int)
     */
    @Override
    public List<RegisteredClassDto> getBySchool(int schoolId)
            throws ServiceException {
        List<RegisteredClassDto> classes = null;
        try {
            classes = registeredClassManager.getBySchool(schoolId);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(),
                    dataException);
        }
        return classes;
    }

    /* (non-Javadoc)
     * @see com.myschool.clazz.service.RegisteredClassService#create(com.myschool.clazz.dto.RegisteredClassDto)
     */
    @Override
    public boolean create(RegisteredClassDto registeredClassDto) throws ServiceException {
        boolean created = false;
        try {
            created = registeredClassManager.create(registeredClassDto);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(),
                    dataException);
        }
        return created;
    }

    /* (non-Javadoc)
     * @see com.myschool.clazz.service.RegisteredClassService#update(int, com.myschool.clazz.dto.RegisteredClassDto)
     */
    @Override
    public boolean update(int registeredClassId,
            RegisteredClassDto registeredClassDto) throws ServiceException {
        boolean updated = false;

        try {
            updated = registeredClassManager.update(registeredClassId, registeredClassDto);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(),
                    dataException);
        }
        return updated;
    }

    /* (non-Javadoc)
     * @see com.myschool.clazz.service.RegisteredClassService#delete(int)
     */
    @Override
    public boolean delete(int registeredClassId) throws ServiceException {
        boolean deleted = false;
        try {
            deleted = registeredClassManager.delete(registeredClassId);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(),
                    dataException);
        }
        return deleted;
    }

    /* (non-Javadoc)
     * @see com.myschool.clazz.service.RegisteredClassService#updateBySchool(int, java.util.List)
     */
    @Override
    public void updateBySchool(int schoolId, List<RegisteredClassDto> registeredClasses)
            throws ServiceException {
        try {
            registeredClassManager.updateBySchool(schoolId,registeredClasses);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(),
                    dataException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.clazz.service.RegisteredClassService#getAll()
     */
    @Override
    public List<RegisteredClassDto> getAll() throws ServiceException {
        try {
            return registeredClassManager.getAll();
       } catch (DataException dataException) {
           throw new ServiceException(dataException.getMessage(),
                   dataException);
       }
    }

    /* (non-Javadoc)
     * @see com.myschool.clazz.service.RegisteredClassService#get(int)
     */
    @Override
    public RegisteredClassDto get(int registeredClassId) throws ServiceException {
        try {
            return registeredClassManager.get(registeredClassId);
       } catch (DataException dataException) {
           throw new ServiceException(dataException.getMessage(),
                   dataException);
       }
    }

}
