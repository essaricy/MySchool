package com.myschool.exam.service;

import java.util.List;

import com.myschool.application.service.Servicable;
import com.myschool.common.exception.ServiceException;
import com.myschool.exam.dto.ExamDto;

/**
 * The Interface ExamService.
 */
public interface ExamService extends Servicable<ExamDto> {

    /**
     * Gets the by class.
     *
     * @param classId the class id
     * @return the exam names in class
     * @throws ServiceException the service exception
     */
    List<ExamDto> getByClass(int classId) throws ServiceException;

    /**
     * Update exam.
     *
     * @param classId the class id
     * @param exam the exam
     * @return true, if successful
     * @throws ServiceException the service exception
     */
    boolean updateExam(int classId, ExamDto exam) throws ServiceException;

    /**
     * Freeze exam.
     * 
     * @param examId the exam id
     * @throws ServiceException the service exception
     */
    void freezeExam(int examId) throws ServiceException;

    /**
     * Gets the current academic by class.
     *
     * @param classId the class id
     * @return the current academic by class
     * @throws ServiceException the service exception
     */
    List<ExamDto> getCurrentAcademicByClass(int classId) throws ServiceException;

    /**
     * Gets the latest exam.
     * 
     * @param classId the class id
     * @return the latest exam
     * @throws ServiceException the service exception
     */
    ExamDto getLatestExam(int classId) throws ServiceException;

}
