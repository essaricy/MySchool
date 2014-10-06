package com.myschool.student.dao;

import java.util.List;

import com.myschool.common.exception.DaoException;
import com.myschool.student.dto.StudentDocument;

/**
 * The Interface StudentDocumentDao.
 */
public interface StudentDocumentDao {

    /**
     * Gets the.
     * 
     * @param studentDocumentId the student document id
     * @return the student document
     * @throws DaoException the dao exception
     */
    StudentDocument get(int studentDocumentId) throws DaoException;

    /**
     * Gets the.
     * 
     * @param studentId the student id
     * @param documentId the document id
     * @return the student document
     * @throws DaoException the dao exception
     */
    StudentDocument get(int studentId, int documentId) throws DaoException;

    /**
     * Gets the by student.
     * 
     * @param admissionNumber the admission number
     * @return the by student
     * @throws DaoException the dao exception
     */
    List<StudentDocument> getByStudent(String admissionNumber) throws DaoException;

    /**
     * Creates the.
     * 
     * @param studentId the student id
     * @param studentDocument the student document
     * @return the int
     * @throws DaoException the dao exception
     */
    int create(int studentId, StudentDocument studentDocument) throws DaoException;

    /**
     * Update.
     * 
     * @param studentDocumentId the student document id
     * @param studentDocument the student document
     * @return true, if successful
     * @throws DaoException the dao exception
     */
    boolean update(int studentDocumentId, StudentDocument studentDocument) throws DaoException;

    /**
     * Creates the.
     * 
     * @param studentId the student id
     * @param documentsSubmitted the documents submitted
     * @throws DaoException the dao exception
     */
    void create(int studentId, List<StudentDocument> documentsSubmitted) throws DaoException;

    /**
     * Delete.
     * 
     * @param studentDocumentId the student document id
     * @return true, if successful
     * @throws DaoException the dao exception
     */
    boolean delete(int studentDocumentId) throws DaoException;

}
