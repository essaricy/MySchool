package com.myschool.exam.service;

import java.util.List;

import com.myschool.application.service.Servicable;
import com.myschool.common.exception.ServiceException;
import com.myschool.exam.dto.SubjectExamDto;

public interface SubjectExamService extends Servicable<SubjectExamDto> {

    /**
     * Gets the subject exams.
     *
     * @param examId the exam id
     * @return the subject exams
     * @throws ServiceException the service exception
     */
    List<SubjectExamDto> getSubjectExams(int examId) throws ServiceException;
}
