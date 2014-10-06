package com.myschool.exam.assembler;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.myschool.exam.dto.ExamGradeDto;

/**
 * The Class ExamGradeDataAssembler.
 */
public class ExamGradeDataAssembler {

    /**
     * Creates the exam grade.
     *
     * @param resultSet the result set
     * @return the exam grade dto
     * @throws SQLException the sQL exception
     */
    public static ExamGradeDto createExamGrade(ResultSet resultSet) throws SQLException {
        ExamGradeDto examGrade = new ExamGradeDto();
        examGrade.setExamGradeId(resultSet.getInt("EXAM_GRADE_ID"));
        examGrade.setGradeName(resultSet.getString("GRADE_NAME"));
        examGrade.setQualifyingPercentage(resultSet.getInt("PERCENTAGE"));
        return examGrade;
    }

}
