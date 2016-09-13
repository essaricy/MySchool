package com.myschool.student.service;

import java.io.File;
import java.util.List;

import com.myschool.application.service.Servicable;
import com.myschool.common.constants.RecordStatus;
import com.myschool.common.exception.ServiceException;
import com.myschool.image.constant.ImageSize;
import com.myschool.student.dto.StudentDto;
import com.myschool.student.dto.StudentPerformaceDto;
import com.myschool.student.dto.StudentSearchCriteriaDto;

/**
 * The Interface StudentService.
 */
public interface StudentService extends Servicable<StudentDto> {

    /**
     * Gets the student.
     *
     * @param admissionNumber the admission number
     * @return the student
     * @throws ServiceException the service exception
     */
    StudentDto get(String admissionNumber) throws ServiceException;

    /**
     * Gets the next.
     *
     * @param admissionNumber the admission number
     * @param recordStatus the record status
     * @return the next
     * @throws ServiceException the service exception
     */
    StudentDto getNext(String admissionNumber, RecordStatus recordStatus) throws ServiceException;

    /**
     * Gets the previous.
     *
     * @param admissionNumber the admission number
     * @param recordStatus the record status
     * @return the previous
     * @throws ServiceException the service exception
     */
    StudentDto getPrevious(String admissionNumber, RecordStatus recordStatus) throws ServiceException;

    /**
     * Gets the student.
     *
     * @param studentId the student id
     * @return the student
     * @throws ServiceException the service exception
     */
    StudentDto get(int studentId) throws ServiceException;

    /**
     * Delete.
     *
     * @param admissionNumber the admission number
     * @throws ServiceException the service exception
     */
    void delete(String admissionNumber) throws ServiceException;

    /**
     * Gets the performance.
     * 
     * @param admissionNumber the admission number
     * @return the performance
     * @throws ServiceException the service exception
     * @deprecated move this to separate class Gets the performance.
     */
    List<StudentPerformaceDto> getPerformance(String admissionNumber) throws ServiceException;

    /**
     * Terminate admission.
     * 
     * @param admissionNumber the admission number
     * @return true, if successful
     * @throws ServiceException the service exception
     */
    boolean terminateAdmission(String admissionNumber) throws ServiceException;

    /**
     * Gets the current ay students.
     *
     * @param classId the class id
     * @return the current ay students
     * @throws ServiceException the service exception
     */
    List<StudentDto> getCurrentAyStudents(int classId) throws ServiceException;

    /**
     * Gets the last admission number.
     *
     * @return the last admission number
     * @throws ServiceException the service exception
     */
    String getLastAdmissionNumber() throws ServiceException;

    /**
     * Gets the all.
     *
     * @param studentSearchCriteriaDto the student search criteria dto
     * @return the all
     * @throws ServiceException the service exception
     */
    List<StudentDto> getAll(StudentSearchCriteriaDto studentSearchCriteriaDto) throws ServiceException;

    /**
     * Gets the evanescent image.
     *
     * @param referenceNumber the reference number
     * @param imageSize the image size
     * @return the evanescent image
     * @throws ServiceException the service exception
     */
    File getEvanescentImage(String referenceNumber, ImageSize imageSize) throws ServiceException;

}
