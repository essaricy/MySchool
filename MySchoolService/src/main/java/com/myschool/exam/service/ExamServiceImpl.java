package com.myschool.exam.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myschool.common.exception.ServiceException;
import com.myschool.exam.domain.ExamManager;
import com.myschool.exam.dto.ExamDto;
import com.quasar.core.exception.DataException;

/**
 * The Class ExamServiceImpl.
 */
@Service
public class ExamServiceImpl implements ExamService {

    /** The exam manager. */
    @Autowired
    private ExamManager examManager;

    /* (non-Javadoc)
     * @see com.myschool.service.interfaces.ExamService#getByClass(int)
     */
    @Override
    public List<ExamDto> getByClass(int classId)
            throws ServiceException {
        try {
            return examManager.getByClass(classId);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(),
                    dataException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.service.interfaces.Servicable#delete(int)
     */
    @Override
    public boolean delete(int examId) throws ServiceException {
        try {
            return examManager.delete(examId);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(),
                    dataException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.service.interfaces.Servicable#get(int)
     */
    @Override
    public ExamDto get(int examId) throws ServiceException {
        try {
            return examManager.get(examId);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(),
                    dataException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.service.interfaces.Servicable#getAll()
     */
    @Override
    public List<ExamDto> getAll() throws ServiceException {
        return null;
    }

    /* (non-Javadoc)
     * @see com.myschool.service.interfaces.Servicable#update(int, java.lang.Object)
     */
    @Override
    public boolean update(int id, ExamDto dto) throws ServiceException {
        return false;
    }

    /* (non-Javadoc)
     * @see com.myschool.service.interfaces.ExamService#updateExam(int, com.myschool.common.dto.ExamDto)
     */
    @Override
    public boolean updateExam(int classId, ExamDto exam) throws ServiceException {
        try {
            return examManager.updateExam(classId, exam);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(),
                    dataException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.service.interfaces.Servicable#create(java.lang.Object)
     */
    @Override
    public boolean create(ExamDto dto) throws ServiceException {
        return false;
    }

    /* (non-Javadoc)
     * @see com.myschool.exam.service.ExamService#freezeExam(int)
     */
    @Override
    public void freezeExam(int examId) throws ServiceException {
        try {
            examManager.freezeExam(examId);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(),
                    dataException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.service.interfaces.ExamService#getCurrentAcademicByClass(int)
     */
    @Override
    public List<ExamDto> getCurrentAcademicByClass(int classId) throws ServiceException {
        try {
            return examManager.getCurrentAcademicByClass(classId);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(),
                    dataException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.exam.service.ExamService#getLatestExam(int)
     */
    @Override
    public ExamDto getLatestExam(int classId) throws ServiceException {
        try {
            return examManager.getLatestExam(classId);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(),
                    dataException);
        }
    }

}
