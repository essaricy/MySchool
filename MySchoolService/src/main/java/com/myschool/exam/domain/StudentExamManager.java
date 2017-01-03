package com.myschool.exam.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.common.exception.DaoException;
import com.myschool.exam.dto.ExamDto;
import com.myschool.exam.dto.ExamGradeDto;
import com.myschool.exam.dto.StudentExamDto;
import com.myschool.exam.dto.StudentExamsSummaryDto;
import com.myschool.exam.dto.StudentInExamDto;
import com.myschool.exam.dto.SubjectExamDto;
import com.myschool.exim.dao.StudentExamDao;
import com.myschool.student.dao.StudentDao;
import com.myschool.student.dto.StudentDto;
import com.quasar.core.exception.DataException;

/**
 * The Class StudentExamManager.
 */
@Component
public class StudentExamManager {

    /** The exam grade manager. */
    @Autowired
    private ExamGradeManager examGradeManager;

    /** The exam manager. */
    @Autowired
    private ExamManager examManager;

    /** The student exam dao. */
    @Autowired
    private StudentExamDao studentExamDao;

    /** The student dao. */
    @Autowired
    private StudentDao studentDao;

    /**
     * Gets the students in exam.
     *
     * @param examId the exam id
     * @param classId the class id
     * @return the students in exam
     * @throws DataException the data exception
     */
    public List<StudentInExamDto> getStudentsInExam(int examId, int classId) throws DataException {
        List<StudentInExamDto> studentsInExam = null;
        try {
            ExamDto exam = examManager.get(examId);
            if (exam != null) {
                List<SubjectExamDto> subjectExams = exam.getSubjectExams();
                if (subjectExams != null && !subjectExams.isEmpty()) {
                    List<ExamGradeDto> examGrades = examGradeManager.getGrades();
                    studentsInExam = studentExamDao.getStudentsInExam(examId, classId);
                    if (studentsInExam != null) {
                        int totalMarks = getTotalMarks(subjectExams);
                        updateTotalPercentageAndGrade(totalMarks, subjectExams.size(), examGrades, studentsInExam);
                    }
                }
            }
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return studentsInExam;
    }

    /**
     * Update.
     *
     * @param studentInExams the student in exams
     * @throws DataException the data exception
     */
    public void update(List<StudentInExamDto> studentInExams) throws DataException {

        int studentId = 0;
        int studentExamId = 0;
        List<StudentExamDto> studentExams = null;

        try {
            if (studentInExams != null) {
                for (StudentInExamDto studentInExam : studentInExams) {
                    if (studentInExam != null) {
                        studentId = studentInExam.getStudent().getStudentId();
                        studentExams = studentInExam.getStudentExams();
                        if (studentExams != null) {
                            for (StudentExamDto studentExam : studentExams) {
                                if (studentExam != null) {
                                    studentExamId = studentExam.getStudentExamId();
                                    if (studentExamId == 0) {
                                        studentExamDao.create(studentId, studentExam);
                                    } else {
                                        studentExamDao.update(studentExam);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
    }

    /**
     * Gets the total marks.
     * 
     * @param subjectExams the subject exams
     * @return the total marks
     */
    private int getTotalMarks(List<SubjectExamDto> subjectExams) {
        int totalMarks = 0;
        if (subjectExams != null) {
            for (SubjectExamDto subjectExam : subjectExams) {
                if (subjectExam != null) {
                    totalMarks = totalMarks + subjectExam.getMaximumMarks();
                }
            }
        }
        return totalMarks;
    }

    /**
     * Update total percentage and grade.
     * 
     * @param totalMarks the total marks
     * @param numberOfSubjects the number of subjects
     * @param examGrades the exam grades
     * @param studentsInExam the students in exam
     */
    private void updateTotalPercentageAndGrade(int totalMarks,
            int numberOfSubjects, List<ExamGradeDto> examGrades,
            List<StudentInExamDto> studentsInExam) {

        int studentTotalMarks = 0;
        double percentage = 0;
        String grade = null;
        List<StudentExamDto> studentExams = null;

        if (totalMarks !=0 && studentsInExam != null) {
            for (StudentInExamDto studentInExam : studentsInExam) {

                percentage = 0;
                studentTotalMarks = 0;
                grade = null;

                if (studentInExam != null) {
                    studentExams = studentInExam.getStudentExams();
                    if (studentExams != null && !studentExams.isEmpty()) {
                        for (StudentExamDto studentExam : studentExams) {
                            studentTotalMarks = studentTotalMarks + studentExam.getObtainedMarks();
                        }
                    }
                    percentage = ((double)studentTotalMarks * 100)/(double)totalMarks;
                    grade = getGrade(percentage, examGrades);
                }
                studentInExam.setTotalMarks(studentTotalMarks);
                BigDecimal bigDecimal = new BigDecimal(percentage);
                bigDecimal = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
                studentInExam.setPercentage(bigDecimal.doubleValue());
                studentInExam.setGrade(grade);
            }
        }
    }

    /**
     * Gets the grade.
     *
     * @param percentage the percentage
     * @param examGrades the exam grades
     * @return the grade
     */
    private static String getGrade(double percentage, List<ExamGradeDto> examGrades) {
        int lowest = 0;
        int qualifyingPercentage = 0;
        String grade = null;

        if (examGrades != null && !examGrades.isEmpty()) {
            for (ExamGradeDto examGrade : examGrades) {
                if (examGrade != null) {
                    qualifyingPercentage = examGrade.getQualifyingPercentage();
                    if ((percentage - qualifyingPercentage >= 0)
                            && (percentage - lowest >= 0)
                            && percentage - qualifyingPercentage < percentage - lowest) {
                        lowest = qualifyingPercentage;
                    }
                }
            }
            for (ExamGradeDto examGrade : examGrades) {
                if (examGrade != null) {
                    if (lowest == examGrade.getQualifyingPercentage()) {
                        grade = examGrade.getGradeName();
                    }
                }
            }
        }
        return grade;
    }

    /**
     * Gets the student marks.
     * 
     * @param admissionNumber the admission number
     * @return the student marks
     * @throws DataException the data exception
     */
    public StudentExamsSummaryDto getStudentMarks(String admissionNumber) throws DataException {
        StudentExamsSummaryDto studentExamsSummary = null;
        StudentDto student = null;
        List<ExamDto> exams = null;
        List<StudentExamDto> studentExamMarks = null;
        Map<SubjectExamDto, List<StudentExamDto>> studentSubjectExamMarks = null;
        try {
            student = studentDao.get(admissionNumber);
            if (student != null) {
                studentExamsSummary = new StudentExamsSummaryDto();
                exams = examManager.getCurrentAcademicByClass(student.getRegisteredClassDto().getClassId());
                studentExamsSummary.setStudent(student);
                if (exams != null && !exams.isEmpty()) {
                    studentSubjectExamMarks = new HashMap<SubjectExamDto, List<StudentExamDto>>();
                    List<SubjectExamDto> subjectExams = getUniqueSubjects(exams);
                    if (subjectExams != null && !subjectExams.isEmpty()) {
                        for (SubjectExamDto subjectExam : subjectExams) {
                            if (subjectExam != null) {
                                studentExamMarks = studentExamDao.getStudentSubjectMarks(
                                        student.getAdmissionNumber(), subjectExam.getRegisteredSubject().getSubjectId());
                                studentSubjectExamMarks.put(subjectExam, studentExamMarks);
                            }
                        }
                    }
                    studentExamsSummary.setExams(exams);
                    studentExamsSummary.setSubjectExams(subjectExams);
                    studentExamsSummary.setStudentSubjectExamMarks(studentSubjectExamMarks);
                }
            }
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return studentExamsSummary;
    }

    /**
     * Gets the unique subjects.
     * 
     * @param exams the exams
     * @return the unique registered subjects
     */
    private List<SubjectExamDto> getUniqueSubjects(
            List<ExamDto> exams) {
        List<SubjectExamDto> existingSubjectExams = null;
        List<SubjectExamDto> subjectExams = null;
        if (exams != null && !exams.isEmpty()) {
            for (ExamDto exam : exams) {
                if (exam != null) {
                    existingSubjectExams = exam.getSubjectExams();
                    if (existingSubjectExams != null && !existingSubjectExams.isEmpty()) {
                        for (SubjectExamDto existingSubjectExam : existingSubjectExams) {
                            if (existingSubjectExam != null) {
                                if (subjectExams == null) {
                                    subjectExams = new ArrayList<SubjectExamDto>();
                                }
                                if (!contains(subjectExams, existingSubjectExam)) {
                                    subjectExams.add(existingSubjectExam);
                                }
                            }
                        }
                    }
                }
            }
        }
        return subjectExams;
    }

    /**
     * Contains.
     * 
     * @param subjectExams the subject exams
     * @param subjectExam the subject exam
     * @return true, if successful
     */
    private boolean contains(List<SubjectExamDto> subjectExams,
            SubjectExamDto subjectExam) {
        if (subjectExams != null && subjectExam != null) {
            for (SubjectExamDto existingSubjectExam : subjectExams) {
                if (existingSubjectExam != null) {
                    if (existingSubjectExam.getRegisteredSubject().getSubjectId() == subjectExam.getRegisteredSubject().getSubjectId()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
