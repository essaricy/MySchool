package com.myschool.clazz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myschool.clazz.domain.SubjectManager;
import com.myschool.clazz.dto.SubjectDto;
import com.myschool.common.exception.DataException;
import com.myschool.common.exception.ServiceException;

/**
 * The Class SubjectServiceImpl.
 */
@Service
public class SubjectServiceImpl implements SubjectService {

    /** The subject manager. */
    @Autowired
    private SubjectManager subjectManager;

    /* (non-Javadoc)
     * @see com.myschool.service.interfaces.Servicable#create(java.lang.Object)
     */
    @Override
    public boolean create(SubjectDto subjectDto) throws ServiceException {
        boolean created = false;
        try {
            created = subjectManager.create(subjectDto);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
        return created;
    }

    /* (non-Javadoc)
     * @see com.myschool.service.interfaces.Servicable#delete(int)
     */
    @Override
    public boolean delete(int subjectId) throws ServiceException {
        boolean deleted = false;
        try {
            deleted = subjectManager.delete(subjectId);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
        return deleted;
    }

    /* (non-Javadoc)
     * @see com.myschool.service.interfaces.Servicable#get(int)
     */
    @Override
    public SubjectDto get(int subjectId) throws ServiceException {
        SubjectDto subject = null;
        try {
            subject = subjectManager.get(subjectId);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
        return subject;
    }

    /* (non-Javadoc)
     * @see com.myschool.service.interfaces.Servicable#getAll()
     */
    @Override
    public List<SubjectDto> getAll() throws ServiceException {
        List<SubjectDto> subjects = null;
        try {
            subjects = subjectManager.getAll();
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(),
                    dataException);
        }
        return subjects;
    }

    /* (non-Javadoc)
     * @see com.myschool.service.interfaces.Servicable#update(int, java.lang.Object)
     */
    @Override
    public boolean update(int subjectId, SubjectDto subjectDto)
            throws ServiceException {
        boolean updated = false;
        try {
            updated = subjectManager.update(subjectId, subjectDto);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
        return updated;
    }

    /* (non-Javadoc)
     * @see com.myschool.service.interfaces.SubjectService#updateByClass(int, java.util.List)
     
    @Override
    public void updateByClass(int classId, List<SubjectDto> subjectsList)
            throws ServiceException {
        try {
            subjectManager.updateByClass(classId, subjectsList);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(),
                    dataException);
        }
    }

     (non-Javadoc)
     * @see com.myschool.service.interfaces.SubjectService#getOperatingSubjectsByClass(int)
     
    @Override
    public List<SubjectDto> getOperatingSubjectsByClass(int classId) throws ServiceException {
        List<SubjectDto> subjects = null;
        try {
            subjects = subjectManager.getOperatingSubjectsByClass(classId);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(),
                    dataException);
        }
        return subjects;
    }*/

}
