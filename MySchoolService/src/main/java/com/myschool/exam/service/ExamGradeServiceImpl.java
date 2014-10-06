package com.myschool.exam.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myschool.common.exception.DataException;
import com.myschool.common.exception.ServiceException;
import com.myschool.exam.domain.ExamGradeManager;
import com.myschool.exam.dto.ExamGradeDto;

/**
 * The Class ExamGradeServiceImpl.
 */
@Service
public class ExamGradeServiceImpl implements ExamGradeService {

    /** The exam grade manager. */
    @Autowired
    private ExamGradeManager examGradeManager;

    /* (non-Javadoc)
     * @see com.myschool.service.interfaces.Servicable#create(java.lang.Object)
     */
    @Override
    public boolean create(ExamGradeDto examGrade) throws ServiceException {
        boolean created = false;
        try {
            created = examGradeManager.create(examGrade);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
        return created;
    }

    /* (non-Javadoc)
     * @see com.myschool.service.interfaces.Servicable#delete(int)
     */
    @Override
    public boolean delete(int examGradeId) throws ServiceException {
        boolean deleted = false;
        try {
            deleted = examGradeManager.delete(examGradeId);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
        return deleted;
    }

    /* (non-Javadoc)
     * @see com.myschool.service.interfaces.Servicable#get(int)
     */
    @Override
    public ExamGradeDto get(int examGradeId) throws ServiceException {
        ExamGradeDto examGrade = null;
        try {
            examGrade = examGradeManager.get(examGradeId);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
        return examGrade;
    }

    /* (non-Javadoc)
     * @see com.myschool.service.interfaces.Servicable#getAll()
     */
    @Override
    public List<ExamGradeDto> getAll() throws ServiceException {
        try {
            return examGradeManager.getGrades();
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(),
                    dataException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.service.interfaces.Servicable#update(int, java.lang.Object)
     */
    @Override
    public boolean update(int examGradeId, ExamGradeDto examGrade) throws ServiceException {
        boolean updated = false;
        try {
            updated = examGradeManager.update(examGradeId, examGrade);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
        return updated;
    }

}
