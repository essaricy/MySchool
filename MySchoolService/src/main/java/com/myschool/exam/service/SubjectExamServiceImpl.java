package com.myschool.exam.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myschool.common.exception.DataException;
import com.myschool.common.exception.ServiceException;
import com.myschool.exam.domain.SubjectExamManager;
import com.myschool.exam.dto.SubjectExamDto;

/**
 * The Class SubjectExamServiceImpl.
 */
@Service
public class SubjectExamServiceImpl implements SubjectExamService {

    /** The subject exam manager. */
    @Autowired
    private SubjectExamManager subjectExamManager;

    /* (non-Javadoc)
     * @see com.myschool.service.interfaces.Servicable#create(java.lang.Object)
     */
    @Override
    public boolean create(SubjectExamDto dto) throws ServiceException {
        return false;
    }

    /* (non-Javadoc)
     * @see com.myschool.service.interfaces.Servicable#delete(int)
     */
    @Override
    public boolean delete(int id) throws ServiceException {
        return false;
    }

    /* (non-Javadoc)
     * @see com.myschool.service.interfaces.Servicable#get(int)
     */
    @Override
    public SubjectExamDto get(int id) throws ServiceException {
        return null;
    }

    /* (non-Javadoc)
     * @see com.myschool.service.interfaces.Servicable#getAll()
     */
    @Override
    public List<SubjectExamDto> getAll() throws ServiceException {
        return null;
    }

    /* (non-Javadoc)
     * @see com.myschool.service.interfaces.Servicable#update(int, java.lang.Object)
     */
    @Override
    public boolean update(int id, SubjectExamDto dto) throws ServiceException {
        return false;
    }

    /* (non-Javadoc)
     * @see com.myschool.service.interfaces.SubjectExamService#getSubjectExams(int)
     */
    @Override
    public List<SubjectExamDto> getSubjectExams(int examId)
            throws ServiceException {
        List<SubjectExamDto> subjectExams = null;
        try {
            subjectExams = subjectExamManager.getSubjectExams(examId);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(),
                    dataException);
        }
        return subjectExams;
    }

}
