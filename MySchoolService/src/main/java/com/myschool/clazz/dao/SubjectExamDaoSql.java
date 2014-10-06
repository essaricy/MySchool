package com.myschool.clazz.dao;

import com.myschool.exam.dto.SubjectExamDto;

/**
 * The Class SubjectExamDaoSql.
 */
public class SubjectExamDaoSql {

    /**
     * Gets the insert subject exam sql.
     *
     * @param nextId the next id
     * @param examId the exam id
     * @param subjectExam the subject exam
     * @return the insert subject exam sql
     */
    public static String getInsertSubjectExamSql(int nextId, int examId,
            SubjectExamDto subjectExam) {
        StringBuilder builder = new StringBuilder();
        builder.append("INSERT INTO SUBJECT_EXAM (");
        builder.append("SUBJECT_EXAM_ID, ");
        builder.append("EXAM_ID, ");
        builder.append("SUBJECT_ID, ");
        builder.append("MAXIMUM_MARKS) ");
        builder.append("VALUES (");
        builder.append(nextId).append(", ");
        builder.append(examId).append(", ");
        builder.append(subjectExam.getRegisteredSubject().getSubjectId()).append(", ");
        builder.append(subjectExam.getMaximumMarks()).append(") ");
        return builder.toString();
    }

    /**
     * Gets the delete exams sql.
     *
     * @param subjectExamId the subject exam id
     * @return the delete exams sql
     */
    public static String getDeleteExamsSql(int subjectExamId) {
        StringBuilder builder = new StringBuilder();
        builder.append("DELETE FROM SUBJECT_EXAM ");
        builder.append("WHERE ");
        builder.append("SUBJECT_EXAM_ID = ").append(subjectExamId);
        return builder.toString();
    }

    /**
     * Gets the update exams sql.
     *
     * @param subjectExam the subject exam
     * @return the update exams sql
     */
    public static String getUpdateExamsSql(SubjectExamDto subjectExam) {
        StringBuilder builder = new StringBuilder();
        builder.append("UPDATE SUBJECT_EXAM ");
        builder.append("SET ");
        builder.append("SUBJECT_ID = ");
        builder.append(subjectExam.getRegisteredSubject().getSubjectId()).append(", ");
        builder.append("MAXIMUM_MARKS = ");
        builder.append(subjectExam.getMaximumMarks()).append(" ");
        builder.append("WHERE ");
        builder.append("SUBJECT_EXAM_ID = ");
        builder.append(subjectExam.getSubjectExamId());
        return builder.toString();
    }

    /**
     * Gets the subject exams sql.
     *
     * @param examId the exam id
     * @return the subject exams sql
     */
    public static String getSubjectExamsSql(int examId) {
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT "); 
        builder.append("CLASS.CLASS_ID AS CLASS_CLASS_ID, "); 
        builder.append("REF_CLASS.CLASS_ID AS REF_CLASS_CLASS_ID, "); 
        builder.append("REF_CLASS.CLASS_NAME AS REF_CLASS_CLASS_NAME, ");
        builder.append("SUBJECT.SUBJECT_ID AS SUBJECT_ID, ");
        builder.append("REF_SUBJECT.SUBJECT_ID AS REF_SUBJECT_SUBJECT_ID, ");
        builder.append("REF_SUBJECT.SUBJECT_NAME AS REF_SUBJECT_SUBJECT_NAME, "); 
        builder.append("REF_MEDIUM.MEDIUM_ID AS REF_MEDIUM_MEDIUM_ID, ");
        builder.append("REF_MEDIUM.DESCRIPTION AS REF_MEDIUM_DESCRIPTION, "); 
        builder.append("REF_SECTION.SECTION_ID AS REF_SECTION_SECTION_ID, ");
        builder.append("REF_SECTION.SECTION_NAME AS REF_SECTION_SECTION_NAME, ");
        builder.append("SUBJECT_EXAM.SUBJECT_EXAM_ID, ");
        builder.append("SUBJECT_EXAM.MAXIMUM_MARKS "); 
        builder.append("FROM SUBJECT_EXAM ");
        builder.append("INNER JOIN SUBJECT ");
        builder.append("ON SUBJECT.SUBJECT_ID = SUBJECT_EXAM.SUBJECT_ID "); 
        builder.append("INNER JOIN REF_SUBJECT ");
        builder.append("ON REF_SUBJECT.SUBJECT_ID = SUBJECT.REF_SUBJECT_ID "); 
        builder.append("INNER JOIN CLASS ");
        builder.append("ON CLASS.CLASS_ID = SUBJECT.CLASS_ID "); 
        builder.append("INNER JOIN REF_CLASS ");
        builder.append("ON REF_CLASS.CLASS_ID = CLASS.REF_CLASS_ID "); 
        builder.append("INNER JOIN REF_MEDIUM ");
        builder.append("ON REF_MEDIUM.MEDIUM_ID = CLASS.REF_MEDIUM_ID "); 
        builder.append("INNER JOIN REF_SECTION ");
        builder.append("ON REF_SECTION.SECTION_ID = CLASS.REF_SECTION_ID "); 
        builder.append("WHERE ");
        builder.append("SUBJECT_EXAM.EXAM_ID = ").append(examId);
        return builder.toString();
    }

    /**
     * Gets the subject exams sql.
     * 
     * @param examId the exam id
     * @param registeredSubjectId the registered subject id
     * @return the subject exams sql
     */
    public static String getSubjectExamsSql(int examId, int registeredSubjectId) {
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT "); 
        builder.append("CLASS.CLASS_ID AS CLASS_CLASS_ID, "); 
        builder.append("REF_CLASS.CLASS_ID AS REF_CLASS_CLASS_ID, "); 
        builder.append("REF_CLASS.CLASS_NAME AS REF_CLASS_CLASS_NAME, ");
        builder.append("REF_SUBJECT.SUBJECT_ID AS REF_SUBJECT_SUBJECT_ID, ");
        builder.append("REF_SUBJECT.SUBJECT_NAME AS REF_SUBJECT_SUBJECT_NAME, "); 
        builder.append("REF_MEDIUM.MEDIUM_ID AS REF_MEDIUM_MEDIUM_ID, ");
        builder.append("REF_MEDIUM.DESCRIPTION AS REF_MEDIUM_DESCRIPTION, "); 
        builder.append("REF_SECTION.SECTION_ID AS REF_SECTION_SECTION_ID, ");
        builder.append("REF_SECTION.SECTION_NAME AS REF_SECTION_SECTION_NAME, ");
        builder.append("SUBJECT_EXAM.SUBJECT_EXAM_ID, ");
        builder.append("SUBJECT_EXAM.SUBJECT_ID, ");
        builder.append("SUBJECT_EXAM.MAXIMUM_MARKS "); 
        builder.append("FROM SUBJECT_EXAM ");
        builder.append("INNER JOIN SUBJECT ");
        builder.append("ON SUBJECT.SUBJECT_ID = SUBJECT_EXAM.SUBJECT_ID "); 
        builder.append("INNER JOIN REF_SUBJECT ");
        builder.append("ON REF_SUBJECT.SUBJECT_ID = SUBJECT.REF_SUBJECT_ID "); 
        builder.append("INNER JOIN CLASS ");
        builder.append("ON CLASS.CLASS_ID = SUBJECT.CLASS_ID "); 
        builder.append("INNER JOIN REF_CLASS ");
        builder.append("ON REF_CLASS.CLASS_ID = CLASS.REF_CLASS_ID "); 
        builder.append("INNER JOIN REF_MEDIUM ");
        builder.append("ON REF_MEDIUM.MEDIUM_ID = CLASS.REF_MEDIUM_ID "); 
        builder.append("INNER JOIN REF_SECTION ");
        builder.append("ON REF_SECTION.SECTION_ID = CLASS.REF_SECTION_ID "); 
        builder.append("WHERE ");
        builder.append("SUBJECT_EXAM.EXAM_ID = ").append(examId);
        builder.append(" AND SUBJECT_EXAM.SUBJECT_ID = ").append(registeredSubjectId);
        return builder.toString();
    }

    /**
     * Gets the insert subject exam sql.
     * 
     * @param nextId the next id
     * @param examId the exam id
     * @param registeredSubjectId the registered subject id
     * @param maximumMarks the maximum marks
     * @return the insert subject exam sql
     */
    public static String getInsertSubjectExamSql(int nextId, int examId,
            int registeredSubjectId, int maximumMarks) {
        StringBuilder builder = new StringBuilder();
        builder.append("INSERT INTO SUBJECT_EXAM (");
        builder.append("SUBJECT_EXAM_ID, ");
        builder.append("EXAM_ID, ");
        builder.append("SUBJECT_ID, ");
        builder.append("MAXIMUM_MARKS) ");
        builder.append("VALUES (");
        builder.append(nextId).append(", ");
        builder.append(examId).append(", ");
        builder.append(registeredSubjectId).append(", ");
        builder.append(maximumMarks).append(") ");
        return builder.toString();
    }

    /**
     * Gets the update subject exam sql.
     * 
     * @param subjectExamId the subject exam id
     * @param examId the exam id
     * @param registeredSubjectId the registered subject id
     * @param maximumMarks the maximum marks
     * @return the update subject exam sql
     */
    public static String getUpdateSubjectExamSql(int subjectExamId, int examId,
            int registeredSubjectId, int maximumMarks) {
        StringBuilder builder = new StringBuilder();
        builder.append("UPDATE SUBJECT_EXAM ");
        builder.append("SET ");
        builder.append("EXAM_ID = ");
        builder.append(examId).append(", ");
        builder.append("SUBJECT_ID = ");
        builder.append(registeredSubjectId).append(", ");
        builder.append("MAXIMUM_MARKS = ");
        builder.append(maximumMarks).append(" ");
        builder.append("WHERE ");
        builder.append("SUBJECT_EXAM_ID = ");
        builder.append(subjectExamId);
        return builder.toString();
    }

}
