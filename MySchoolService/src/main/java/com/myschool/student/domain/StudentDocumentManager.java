package com.myschool.student.domain;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.common.dto.DocumentDto;
import com.myschool.common.exception.DaoException;
import com.myschool.common.exception.DataException;
import com.myschool.common.exception.ValidationException;
import com.myschool.student.dao.StudentDocumentDao;
import com.myschool.student.dto.StudentDocument;
import com.myschool.student.dto.StudentDto;
import com.myschool.student.validator.StudentDocumentValidator;

/**
 * The Class StudentDocumentManager.
 */
@Component
public class StudentDocumentManager {

    /** The student document dao. */
    @Autowired
    private StudentDocumentDao studentDocumentDao;

    /** The student manager. */
    @Autowired
    private StudentManager studentManager;

    /** The student document validator. */
    @Autowired
    private StudentDocumentValidator studentDocumentValidator;

    /**
     * Gets the.
     * 
     * @param studentDocumentId the student document id
     * @return the student document
     * @throws DataException the data exception
     */
    public StudentDocument get(int studentDocumentId) throws DataException {
        try {
            return studentDocumentDao.get(studentDocumentId);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
    }

    /**
     * Creates the.
     * 
     * @param admissionNumber the admission number
     * @param studentDocument the student document
     * @return true, if successful
     * @throws DataException the data exception
     */
    public boolean create(String admissionNumber, StudentDocument studentDocument) throws DataException {
        try {
            StudentDto student = studentManager.getStudent(admissionNumber);
            if (student == null) {
                throw new DataException("There is no student with admission number '" + admissionNumber + "'");
            }
            studentDocumentValidator.validate(studentDocument);
            return studentDocumentDao.create(student.getStudentId(), studentDocument) > 0;
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        } catch (ValidationException validationException) {
            throw new DataException(validationException.getMessage(), validationException);
        }
    }

    /**
     * Validate.
     * 
     * @param studentDocument the student document
     * @throws DataException the data exception
     */
    public void validate(StudentDocument studentDocument) throws DataException {
        try {
            studentDocumentValidator.validate(studentDocument);
        } catch (ValidationException validationException) {
            throw new DataException(validationException.getMessage(), validationException);
        }
    }

    /**
     * Update.
     * 
     * @param studentDocumentId the student document id
     * @param studentDocument the student document
     * @return true, if successful
     * @throws DataException the data exception
     */
    public boolean update(int studentDocumentId,
            StudentDocument studentDocument) throws DataException {
        try {
            studentDocumentValidator.validate(studentDocumentId, studentDocument);
            return studentDocumentDao.update(studentDocumentId, studentDocument);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        } catch (ValidationException validationException) {
            throw new DataException(validationException.getMessage(), validationException);
        }
    }

    /**
     * Delete.
     * 
     * @param studentDocumentId the student document id
     * @return true, if successful
     * @throws DataException the data exception
     */
    public boolean delete(int studentDocumentId) throws DataException {
        try {
            studentDocumentValidator.validate(studentDocumentId);
            return studentDocumentDao.delete(studentDocumentId);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        } catch (ValidationException validationException) {
            throw new DataException(validationException.getMessage(), validationException);
        }
    }

    /**
     * Validate.
     * 
     * @param studentDocuments the student documents
     * @throws DataException the data exception
     */
    public void validate(List<StudentDocument> studentDocuments) throws DataException {
        DocumentDto document = null;
        StudentDocument studentDocument = null;
        StudentDocument studentDocument2 = null;
        try {
            int documentsSize = (studentDocuments == null) ? 0 : studentDocuments.size();
            for (int index=0; index < documentsSize; index++) {
                studentDocument = studentDocuments.get(index);
                // Check if all the documents are valid or not.
                studentDocumentValidator.validate(studentDocument);
                document = studentDocument.getDocument();
                int documentId = document.getDocumentId();
                // Check if any duplicate documents exist.
                for (int jindex=0; jindex < studentDocuments.size(); jindex++) {
                    if (index != jindex) {
                        studentDocument2 = studentDocuments.get(jindex);
                        if (documentId == studentDocument2.getDocument().getDocumentId()) {
                            throw new DataException("Document '" + document.getName() + "' is already present.");
                        }
                    }
                }
            }
        } catch (ValidationException validationException) {
            throw new DataException(validationException.getMessage(), validationException);
        }
    }

    /**
     * Gets the by student.
     * 
     * @param admissionNumber the admission number
     * @return the by student
     * @throws DataException the data exception
     */
    public List<StudentDocument> getByStudent(String admissionNumber) throws DataException {
        try {
            return studentDocumentDao.getByStudent(admissionNumber);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
    }

}
