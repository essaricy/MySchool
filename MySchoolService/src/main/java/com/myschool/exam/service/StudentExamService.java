package com.myschool.exam.service;

import java.util.List;

import com.myschool.application.service.Servicable;
import com.myschool.common.exception.ServiceException;
import com.myschool.exam.dto.StudentExamsSummaryDto;
import com.myschool.exam.dto.StudentInExamDto;

/**
 * The Interface StudentExamService.
 */
public interface StudentExamService extends Servicable<StudentInExamDto> {

    /**
     * Gets the students in exam.
     *
     * @param examId the exam id
     * @param classId the class id
     * @return the students in exam
     * @throws ServiceException the service exception
     */
    List<StudentInExamDto> getStudentsInExam(int examId, int classId) throws ServiceException;

    /**
     * Update.
     *
     * @param studentInExams the student in exams
     * @throws ServiceException the service exception
     */
    void update(List<StudentInExamDto> studentInExams) throws ServiceException;

    /**
     * Gets the student marks.
     * 
     * @param admissionNumber the admission number
     * @return the student marks
     * @throws ServiceException the service exception
     */
    StudentExamsSummaryDto getStudentMarks(String admissionNumber) throws ServiceException;

}
