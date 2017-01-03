package com.myschool.exam.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myschool.common.exception.ServiceException;
import com.myschool.exam.domain.StudentExamManager;
import com.myschool.exam.dto.StudentExamsSummaryDto;
import com.myschool.exam.dto.StudentInExamDto;
import com.quasar.core.exception.DataException;

/**
 * The Class StudentExamServiceImpl.
 */
@Service
public class StudentExamServiceImpl implements StudentExamService {

    /** The student exam manager. */
    @Autowired
    private StudentExamManager studentExamManager;

    /* (non-Javadoc)
     * @see com.myschool.service.interfaces.StudentExamService#getStudentsInExam(int, int)
     */
    @Override
    public List<StudentInExamDto> getStudentsInExam(int examId, int classId)
            throws ServiceException {
        List<StudentInExamDto> studentExams = null;
        try {
            studentExams = studentExamManager.getStudentsInExam(examId, classId);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(),
                    dataException);
        }
        return studentExams;
    }

    /* (non-Javadoc)
     * @see com.myschool.service.interfaces.Servicable#create(java.lang.Object)
     */
    @Override
    public boolean create(StudentInExamDto dto) throws ServiceException {
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
    public StudentInExamDto get(int id) throws ServiceException {
        return null;
    }

    /* (non-Javadoc)
     * @see com.myschool.service.interfaces.Servicable#getAll()
     */
    @Override
    public List<StudentInExamDto> getAll() throws ServiceException {
        return null;
    }

    /* (non-Javadoc)
     * @see com.myschool.service.interfaces.Servicable#update(int, java.lang.Object)
     */
    @Override
    public boolean update(int id, StudentInExamDto dto) throws ServiceException {
        return false;
    }

    /* (non-Javadoc)
     * @see com.myschool.service.interfaces.StudentExamService#update(java.util.List)
     */
    @Override
    public void update(List<StudentInExamDto> studentInExams) throws ServiceException {
        try {
            studentExamManager.update(studentInExams);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(),
                    dataException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.exam.service.StudentExamService#getStudentMarks(java.lang.String)
     */
    @Override
    public StudentExamsSummaryDto getStudentMarks(String admissionNumber) throws ServiceException {
        try {
            return studentExamManager.getStudentMarks(admissionNumber);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(),
                    dataException);
        }
    }

}
