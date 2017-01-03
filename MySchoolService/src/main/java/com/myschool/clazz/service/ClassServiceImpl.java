package com.myschool.clazz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myschool.clazz.domain.ClassManager;
import com.myschool.clazz.dto.ClassDto;
import com.myschool.common.exception.ServiceException;
import com.quasar.core.exception.DataException;

/**
 * The Class ClassServiceImpl.
 */
@Service
public class ClassServiceImpl implements ClassService {

    /** The class manager. */
    @Autowired
    private ClassManager classManager;

    /* (non-Javadoc)
     * @see com.myschool.service.interfaces.Servicable#create(java.lang.Object)
     */
    @Override
    public boolean create(ClassDto classDto) throws ServiceException {
        boolean created = false;
        try {
            created = classManager.create(classDto);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
        return created;
    }

    /* (non-Javadoc)
     * @see com.myschool.service.interfaces.Servicable#delete(int)
     */
    @Override
    public boolean delete(int classId) throws ServiceException {
        boolean deleted = false;
        try {
            deleted = classManager.delete(classId);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
        return deleted;
    }

    /* (non-Javadoc)
     * @see com.myschool.service.interfaces.Servicable#get(int)
     */
    @Override
    public ClassDto get(int classId) throws ServiceException {
        ClassDto classDto = null;
        try {
            classDto = classManager.get(classId);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
        return classDto;
    }

    /* (non-Javadoc)
     * @see com.myschool.service.interfaces.Servicable#getAll()
     */
    @Override
    public List<ClassDto> getAll() throws ServiceException {
        List<ClassDto> classes = null;
        try {
            classes = classManager.getAll();
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(),
                    dataException);
        }
        return classes;
    }

    /* (non-Javadoc)
     * @see com.myschool.service.interfaces.Servicable#update(int, java.lang.Object)
     */
    @Override
    public boolean update(int classId, ClassDto classDto)
            throws ServiceException {
        boolean updated = false;
        try {
            updated = classManager.update(classId, classDto);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
        return updated;
    }

}
