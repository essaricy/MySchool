package com.myschool.student.service;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myschool.common.constants.RecordStatus;
import com.myschool.common.exception.DataException;
import com.myschool.common.exception.ServiceException;
import com.myschool.image.constant.ImageSize;
import com.myschool.student.domain.StudentManager;
import com.myschool.student.dto.StudentDto;
import com.myschool.student.dto.StudentPerformaceDto;
import com.myschool.student.dto.StudentSearchCriteriaDto;

/**
 * The Class StudentServiceImpl.
 */
@Service
public class StudentServiceImpl implements StudentService {

    /** The student manager. */
    @Autowired
    private StudentManager studentManager;

    /* (non-Javadoc)
     * @see com.myschool.service.interfaces.Servicable#create(java.lang.Object)
     */
    @Override
    public boolean create(StudentDto studentDto) throws ServiceException {
        try {
            return (studentManager.create(studentDto) > 0);
       } catch (DataException dataException) {
           throw new ServiceException(dataException.getMessage(),
                   dataException);
       }
    }

    /* (non-Javadoc)
     * @see com.myschool.service.interfaces.Servicable#delete(int)
     */
    @Override
    public boolean delete(int studentId) throws ServiceException {
        throw new ServiceException("Deleting a student is not supported. Instead mark the student as Terminated.");
    }

    /* (non-Javadoc)
     * @see com.myschool.service.interfaces.Servicable#get(int)
     */
    @Override
    public StudentDto get(int studentId) throws ServiceException {
        try {
            return studentManager.get(studentId);
       } catch (DataException dataException) {
           throw new ServiceException(dataException.getMessage(),
                   dataException);
       }
    }

    /* (non-Javadoc)
     * @see com.myschool.service.interfaces.Servicable#getAll()
     */
    @Override
    public List<StudentDto> getAll() throws ServiceException {
        try {
            return studentManager.search();
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(),
                    dataException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.service.interfaces.Servicable#update(int, java.lang.Object)
     */
    @Override
    public boolean update(int studentId, StudentDto studentDto)
            throws ServiceException {
        try {
            return studentManager.update(studentId, studentDto);
       } catch (DataException dataException) {
           throw new ServiceException(dataException.getMessage(),
                   dataException);
       }
    }

    /* (non-Javadoc)
     * @see com.myschool.service.interfaces.StudentService#get(java.lang.String)
     */
    @Override
    public StudentDto get(String admissionNumber) throws ServiceException {
        try {
            return studentManager.getStudent(admissionNumber);
       } catch (DataException dataException) {
           throw new ServiceException(dataException.getMessage(),
                   dataException);
       }
    }

    /* (non-Javadoc)
     * @see com.myschool.student.service.StudentService#getNext(java.lang.String, com.myschool.common.constants.RecordStatus)
     */
    @Override
    public StudentDto getNext(String admissionNumber, RecordStatus recordStatus) throws ServiceException {
        try {
            return studentManager.getNext(admissionNumber, recordStatus);
       } catch (DataException dataException) {
           throw new ServiceException(dataException.getMessage(),
                   dataException);
       }
    }

    /* (non-Javadoc)
     * @see com.myschool.student.service.StudentService#getPrevious(java.lang.String, com.myschool.common.constants.RecordStatus)
     */
    @Override
    public StudentDto getPrevious(String admissionNumber, RecordStatus recordStatus) throws ServiceException {
        try {
            return studentManager.getPrevious(admissionNumber, recordStatus);
       } catch (DataException dataException) {
           throw new ServiceException(dataException.getMessage(),
                   dataException);
       }
    }

    /* (non-Javadoc)
     * @see com.myschool.service.interfaces.StudentService#delete(java.lang.String)
     */
    @Override
    public void delete(String admissionNumber) throws ServiceException {
        throw new ServiceException("Deleting a student is not supported. Instead mark the student as Terminated.");
    }

    /* (non-Javadoc)
     * @see com.myschool.service.interfaces.StudentService#getPerformance(java.lang.String)
     */
    @Override
    public List<StudentPerformaceDto> getPerformance(String admissionNumber) throws ServiceException {
        try {
            return studentManager.getPerformance(admissionNumber);
       } catch (DataException dataException) {
           throw new ServiceException(dataException.getMessage(),
                   dataException);
       }
    }

    /* (non-Javadoc)
     * @see com.myschool.service.interfaces.StudentService#unregisterStudent(java.lang.String)
     */
    @Override
    public boolean terminateAdmission(String admissionNumber) throws ServiceException {
        try {
            return studentManager.terminateAdmission(admissionNumber);
       } catch (DataException dataException) {
           throw new ServiceException(dataException.getMessage(),
                   dataException);
       }
    }

    /* (non-Javadoc)
     * @see com.myschool.service.interfaces.StudentService#getCurrentAyStudents(int)
     */
    @Override
    public List<StudentDto> getCurrentAyStudents(int classId)
            throws ServiceException {
        try {
            return studentManager.getCurrentAyStudents(classId);
       } catch (DataException dataException) {
           throw new ServiceException(dataException.getMessage(),
                   dataException);
       }
    }

    /* (non-Javadoc)
     * @see com.myschool.student.service.StudentService#getLastAdmissionNumber()
     */
    @Override
    public String getLastAdmissionNumber() throws ServiceException {
        String admissionNumber = null;
        try {
            admissionNumber = studentManager.getLastAdmissionNumber();
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(),
                    dataException);
        }
        return admissionNumber; 
    }

    /* (non-Javadoc)
     * @see com.myschool.student.service.StudentService#getAll(com.myschool.student.dto.StudentSearchCriteriaDto)
     */
    @Override
    public List<StudentDto> getAll(
            StudentSearchCriteriaDto studentSearchCriteriaDto)
            throws ServiceException {
        try {
            return studentManager.search(studentSearchCriteriaDto);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(),
                    dataException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.student.service.StudentService#getEvanescentImage(java.lang.String, com.myschool.image.constant.ImageSize)
     */
    @Override
    public File getEvanescentImage(String referenceNumber,
            ImageSize imageSize) throws ServiceException {
        try {
            return studentManager.getEvanescentImage(referenceNumber, imageSize);
       } catch (DataException dataException) {
           throw new ServiceException(dataException.getMessage(),
                   dataException);
       }
    }

}
