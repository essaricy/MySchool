package com.myschool.clazz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myschool.clazz.domain.RegisteredSubjectManager;
import com.myschool.clazz.dto.RegisteredSubjectDto;
import com.myschool.common.exception.DataException;
import com.myschool.common.exception.ServiceException;

/**
 * The Class RegisteredSubjectServiceImpl.
 */
@Service
public class RegisteredSubjectServiceImpl implements RegisteredSubjectService {

    /** The registered subject manager. */
    @Autowired
    private RegisteredSubjectManager registeredSubjectManager;

    /* (non-Javadoc)
     * @see com.myschool.clazz.service.RegisteredSubjectService#getByClass(int)
     */
    @Override
    public List<RegisteredSubjectDto> getByClass(int classId) throws ServiceException {
        List<RegisteredSubjectDto> subjects = null;
        try {
            subjects = registeredSubjectManager.getByClass(classId);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(),
                    dataException);
        }
        return subjects;
    }

    /* (non-Javadoc)
     * @see com.myschool.application.service.Servicable#getAll()
     */
    @Override
    public List<RegisteredSubjectDto> getAll() throws ServiceException {
        return null;
    }

    /* (non-Javadoc)
     * @see com.myschool.application.service.Servicable#get(int)
     */
    @Override
    public RegisteredSubjectDto get(int registeredSubjectId) throws ServiceException {
        RegisteredSubjectDto registeredSubject = null;
        try {
            registeredSubject = registeredSubjectManager.get(registeredSubjectId);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(),
                    dataException);
        }
        return registeredSubject;
    }

    /* (non-Javadoc)
     * @see com.myschool.application.service.Servicable#update(int, java.lang.Object)
     */
    @Override
    public boolean update(int registeredSubjectId, RegisteredSubjectDto registeredSubject)
            throws ServiceException {
        boolean update = false;
        try {
            update = registeredSubjectManager.update(registeredSubjectId, registeredSubject);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(),
                    dataException);
        }
        return update;
    }

    /* (non-Javadoc)
     * @see com.myschool.application.service.Servicable#delete(int)
     */
    @Override
    public boolean delete(int registeredSubjectId) throws ServiceException {
        boolean deleted = false;
        try {
            deleted = registeredSubjectManager.delete(registeredSubjectId);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(),
                    dataException);
        }
        return deleted;
    }

    /* (non-Javadoc)
     * @see com.myschool.application.service.Servicable#create(java.lang.Object)
     */
    @Override
    public boolean create(RegisteredSubjectDto registeredSubject) throws ServiceException {
        boolean created = false;
        try {
            created = registeredSubjectManager.create(registeredSubject);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(),
                    dataException);
        }
        return created;
    }

}
