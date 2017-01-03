package com.myschool.student.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myschool.common.exception.ServiceException;
import com.myschool.student.domain.StudentDocumentManager;
import com.myschool.student.dto.StudentDocument;
import com.quasar.core.exception.DataException;

/**
 * The Class StudentDocumentServiceImpl.
 */
@Service
public class StudentDocumentServiceImpl implements StudentDocumentService {

    /** The student document manager. */
    @Autowired
    private StudentDocumentManager studentDocumentManager;

    /* (non-Javadoc)
     * @see com.myschool.application.service.Servicable#create(java.lang.Object)
     */
    @Override
    public boolean create(StudentDocument studentDocument) throws ServiceException {
        throw new ServiceException("Use create(admissionNumber, StudentDocument)");
    }

    /* (non-Javadoc)
     * @see com.myschool.student.service.StudentDocumentService#create(java.lang.String, com.myschool.student.dto.StudentDocument)
     */
    @Override
    public boolean create(String admissionNumber, StudentDocument studentDocument) throws ServiceException {
        try {
            return studentDocumentManager.create(admissionNumber, studentDocument);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.application.service.Servicable#getAll()
     */
    @Override
    public List<StudentDocument> getAll() throws ServiceException {
        throw new ServiceException("Use get(studentDocumentId)");
    }

    /* (non-Javadoc)
     * @see com.myschool.application.service.Servicable#get(int)
     */
    @Override
    public StudentDocument get(int studentDocumentId) throws ServiceException {
        try {
            return studentDocumentManager.get(studentDocumentId);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.application.service.Servicable#update(int, java.lang.Object)
     */
    @Override
    public boolean update(int studentDocumentId, StudentDocument studentDocument) throws ServiceException {
        try {
            return studentDocumentManager.update(studentDocumentId, studentDocument);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.application.service.Servicable#delete(int)
     */
    @Override
    public boolean delete(int studentDocumentId) throws ServiceException {
        try {
            return studentDocumentManager.delete(studentDocumentId);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.student.service.StudentDocumentService#validate(com.myschool.student.dto.StudentDocument)
     */
    @Override
    public void validate(StudentDocument studentDocument) throws ServiceException {
        try {
            studentDocumentManager.validate(studentDocument);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.student.service.StudentDocumentService#validate(java.util.List)
     */
    @Override
    public void validate(List<StudentDocument> studentDocuments) throws ServiceException {
        try {
            studentDocumentManager.validate(studentDocuments);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.student.service.StudentDocumentService#getByStudent(java.lang.String)
     */
    @Override
    public List<StudentDocument> getByStudent(String admissionNumber) throws ServiceException {
        try {
            return studentDocumentManager.getByStudent(admissionNumber);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
    }

}
