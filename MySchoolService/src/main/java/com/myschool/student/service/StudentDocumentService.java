package com.myschool.student.service;

import java.util.List;

import com.myschool.application.service.Servicable;
import com.myschool.common.exception.ServiceException;
import com.myschool.student.dto.StudentDocument;

/**
 * The Interface StudentDocumentService.
 */
public interface StudentDocumentService extends Servicable<StudentDocument> {

    /**
     * Gets the by student.
     * 
     * @param admissionNumber the admission number
     * @return the by student
     * @throws ServiceException the service exception
     */
    List<StudentDocument> getByStudent(String admissionNumber) throws ServiceException;

    /**
     * Validate.
     * 
     * @param studentDocument the student document
     * @throws ServiceException the service exception
     */
    void validate(StudentDocument studentDocument) throws ServiceException;

    /**
     * Validate.
     * 
     * @param studentDocuments the student documents
     * @throws ServiceException the service exception
     */
    void validate(List<StudentDocument> studentDocuments) throws ServiceException;

    /**
     * Creates the.
     * 
     * @param admissionNumber the admission number
     * @param studentDocument the student document
     * @return true, if successful
     * @throws ServiceException the service exception
     */
    boolean create(String admissionNumber, StudentDocument studentDocument)
            throws ServiceException;


}
