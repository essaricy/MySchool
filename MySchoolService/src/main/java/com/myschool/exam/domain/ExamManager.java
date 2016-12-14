package com.myschool.exam.domain;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.clazz.dao.RegisteredClassDao;
import com.myschool.clazz.dto.RegisteredClassDto;
import com.myschool.common.exception.DaoException;
import com.myschool.common.exception.DataException;
import com.myschool.exam.dao.ExamDao;
import com.myschool.exam.dao.SubjectExamDao;
import com.myschool.exam.dto.ExamDto;
import com.myschool.exam.dto.SubjectExamDto;

/**
 * The Class ExamManager.
 */
@Component
public class ExamManager {

    /** The exam dao. */
    @Autowired
    private ExamDao examDao;

    @Autowired
    private RegisteredClassDao registeredClassDao;

    /** The subject exam dao. */
    @Autowired
    private SubjectExamDao subjectExamDao;

    /**
     * Gets the by class.
     *
     * @param classId the class id
     * @return the exam names in class
     * @throws DataException the data exception
     */
    public List<ExamDto> getByClass(int classId) throws DataException {
        List<ExamDto> exams = null;
        try {
            exams = examDao.getByClass(classId);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return exams;
    }

    /**
     * Gets the.
     * 
     * @param examId the exam id
     * @return the exam
     * @throws DataException the data exception
     */
    public ExamDto get(int examId) throws DataException {
        ExamDto exam = null;
        try {
            exam = examDao.get(examId);
            if (exam != null) {
                RegisteredClassDto registeredClass = exam.getRegisteredClass();
                exam.setRegisteredClass(registeredClassDao.get(registeredClass.getClassId()));
                exam.setSubjectExams(subjectExamDao.getSubjectExams(examId));
            }
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return exam;
    }

    /**
     * Delete.
     *
     * @param examId the exam id
     * @return true, if successful
     * @throws DataException the data exception
     */
    public boolean delete(int examId) throws DataException {
        try {
            return examDao.delete(examId);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
    }

    /**
     * Update exam.
     * 
     * @param classId the class id
     * @param exam the exam
     * @return true, if successful
     * @throws DataException the data exception
     */
    public boolean updateExam(int classId, ExamDto exam) throws DataException {

        SubjectExamDto tempSubjectExam = null;

        List<SubjectExamDto> addSubjectExams = null;
        List<SubjectExamDto> updateSubjectExams = null;
        List<SubjectExamDto> deleteSubjectExams = null;
        List<SubjectExamDto> existingSubjectExams = null;
        List<SubjectExamDto> subjectExams = null;

        try {
            if (exam != null) {
                int examId = exam.getExamId();
                if (examId == 0 ) {
                    RegisteredClassDto registeredClass = new RegisteredClassDto();
                    registeredClass.setClassId(classId);
                    // We need to create this exam for the subjects mentioned.
                    exam.setRegisteredClass(registeredClass);
                    examId = examDao.create(exam);
                    subjectExamDao.create(examId, exam.getSubjectExams());
                } else {
                    // Get all the subject exams list for this exam.
                    existingSubjectExams = subjectExamDao.getSubjectExams(examId);
                    if (existingSubjectExams == null) {
                        // Not a single subject is present in this exam.
                        // add all the subject exams
                        subjectExamDao.create(examId, exam.getSubjectExams());
                    } else {
                        // Some subject exams already present.
                        // Determine whether to add, update or delete.

                        subjectExams = exam.getSubjectExams();
                        if (subjectExams != null) {
                            for (SubjectExamDto subjectExam : subjectExams) {
                                int subjectExamId = subjectExam.getSubjectExamId();
                                if (subjectExamId > 0) {
                                    // This exam id exists. Update.
                                    // Prepare a list of subject exams to update
                                    if (updateSubjectExams == null) {
                                        updateSubjectExams = new ArrayList<SubjectExamDto>();
                                    }
                                    tempSubjectExam = new SubjectExamDto();
                                    tempSubjectExam.setSubjectExamId(subjectExamId);
                                    tempSubjectExam.setRegisteredSubject(subjectExam.getRegisteredSubject());
                                    tempSubjectExam.setMaximumMarks(subjectExam.getMaximumMarks());
                                    updateSubjectExams.add(tempSubjectExam);
                                } else {
                                    // It is a new exam added now.
                                    // Prepare a list of subject exams to add
                                    if (addSubjectExams == null) {
                                        addSubjectExams = new ArrayList<SubjectExamDto>();
                                    }
                                    tempSubjectExam = new SubjectExamDto();
                                    tempSubjectExam.setRegisteredSubject(subjectExam.getRegisteredSubject());
                                    tempSubjectExam.setMaximumMarks(subjectExam.getMaximumMarks());
                                    addSubjectExams.add(tempSubjectExam);
                                }
                            }
                            // Determine which subject exams to delete.
                            // Exam with this name already exists.
                            for (SubjectExamDto existingSubjectExam : existingSubjectExams) {
                                boolean examDeleted = true;
                                for (SubjectExamDto subjectExam : subjectExams) {
                                    if (existingSubjectExam.getSubjectExamId() == subjectExam.getSubjectExamId()) {
                                        // Subject exam is retained.
                                        examDeleted = false;
                                        break;
                                    }
                                }
                                if (examDeleted) {
                                    // Prepare a list of subject exams to delete
                                    if (deleteSubjectExams == null) {
                                        deleteSubjectExams = new ArrayList<SubjectExamDto>();
                                    }
                                    tempSubjectExam = new SubjectExamDto();
                                    tempSubjectExam.setSubjectExamId(existingSubjectExam.getSubjectExamId());
                                    deleteSubjectExams.add(tempSubjectExam);
                                }
                            }
                            subjectExamDao.create(examId, addSubjectExams);
                            subjectExamDao.update(updateSubjectExams);
                            subjectExamDao.delete(deleteSubjectExams);
                        }
                    
                    }
                }
            }
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return true;
    }

    /**
     * Freeze exam.
     * 
     * @param examId the exam id
     * @throws DataException the data exception
     */
    public void freezeExam(int examId) throws DataException {
        try {
            if (examId == 0) {
                throw new DataException("Excam Not Found.");
            }
            ExamDto exam = get(examId);
            if (exam == null) {
                throw new DataException("Excam Not Found.");
            }
            exam.setExamCompleted(true);
            examDao.update(examId, exam);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
    }

    /**
     * Gets the current academic by class.
     *
     * @param classId the class id
     * @return the current academic by class
     * @throws DataException the data exception
     */
    public List<ExamDto> getCurrentAcademicByClass(int classId) throws DataException {
        List<ExamDto> exams = null;
        try {
            exams = examDao.getExamsInCurrentAcademicByClass(classId);
            if (exams != null && !exams.isEmpty()) {
                for (ExamDto exam : exams) {
                    if (exam != null) {
                        RegisteredClassDto registeredClass = exam.getRegisteredClass();
                        exam.setRegisteredClass(registeredClassDao.get(registeredClass.getClassId()));
                        exam.setSubjectExams(subjectExamDao.getSubjectExams(exam.getExamId()));
                    }
                }
            }
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return exams;
    }

    /**
     * Gets the latest exam.
     * 
     * @param classId the class id
     * @return the latest exam
     * @throws DataException the data exception
     */
    public ExamDto getLatestExam(int classId) throws DataException {
        ExamDto exam = null;
        try {
            exam = examDao.getLatestByClass(classId);
            if (exam != null) {
                RegisteredClassDto registeredClass = exam.getRegisteredClass();
                exam.setRegisteredClass(registeredClassDao.get(registeredClass.getClassId()));
                exam.setSubjectExams(subjectExamDao.getSubjectExams(exam.getExamId()));
            }
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return exam;
    }

}
