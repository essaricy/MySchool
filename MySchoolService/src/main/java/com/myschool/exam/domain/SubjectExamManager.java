package com.myschool.exam.domain;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.common.exception.DaoException;
import com.myschool.exam.dao.SubjectExamDao;
import com.myschool.exam.dto.SubjectExamDto;
import com.quasar.core.exception.DataException;

/**
 * The Class SubjectExamManager.
 */
@Component
public class SubjectExamManager {

    /** The subject exam dao. */
    @Autowired
    private SubjectExamDao subjectExamDao;

    /**
     * Gets the subject exams.
     *
     * @param examId the exam id
     * @return the subject exams
     * @throws DataException the data exception
     */
    public List<SubjectExamDto> getSubjectExams(int examId) throws DataException {
        List<SubjectExamDto> subjectExams = null;
        try {
            subjectExams = subjectExamDao.getSubjectExams(examId);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return subjectExams;
    }
}
