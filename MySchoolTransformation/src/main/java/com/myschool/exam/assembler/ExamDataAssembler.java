package com.myschool.exam.assembler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.myschool.clazz.assembler.RegisteredClassDataAssembler;
import com.myschool.clazz.assembler.RegisteredSubjectDataAssembler;
import com.myschool.clazz.dto.RegisteredClassDto;
import com.myschool.common.util.ConversionUtil;
import com.myschool.exam.dto.ExamDto;
import com.myschool.exam.dto.SubjectExamDto;

/**
 * The Class ExamDataAssembler.
 */
public class ExamDataAssembler {

    /**
     * Creates the.
     * 
     * @param resultSet the result set
     * @return the exam dto
     * @throws SQLException the sQL exception
     */
    public static ExamDto create(ResultSet resultSet) throws SQLException {
        ExamDto exam = new ExamDto();
        exam.setExamId(resultSet.getInt("EXAM_ID"));
        RegisteredClassDto registeredClass = new RegisteredClassDto();
        registeredClass.setClassId(resultSet.getInt("CLASS_ID"));
        exam.setRegisteredClass(registeredClass);
        exam.setExamName(resultSet.getString("EXAM_NAME"));
        exam.setExamDate(ConversionUtil.toApplicationDateFromStorageDate(
                resultSet.getString("EXAM_DATE")));
        exam.setExamCompleted(ConversionUtil.toBoolean(resultSet.getString("EXAM_COMPLETED")));
        return exam;
    }

    /**
     * Creates the.
     * 
     * @param exam the exam
     * @return the jSON object
     */
    public static JSONObject create(ExamDto exam) {
        JSONObject jsonObject = null;
        if (exam != null) {
            jsonObject = new JSONObject();
            jsonObject.put("ExamId", exam.getExamId());
            jsonObject.put("ExamDate", exam.getExamDate());
            jsonObject.put("ExamName", exam.getExamName());
            jsonObject.put("ExamCompleted", exam.isExamCompleted());
            jsonObject.put("RegisteredClass", RegisteredClassDataAssembler.create(exam.getRegisteredClass()));
            jsonObject.put("SubjectExams", createSubjectExams(exam.getSubjectExams()));
        }
        return jsonObject;
    }

    /**
     * Creates the subject exams.
     * 
     * @param subjectExams the subject exams
     * @return the jSON array
     */
    private static JSONArray createSubjectExams(List<SubjectExamDto> subjectExams) {
        JSONArray jsonArray = null;
        if (subjectExams != null) {
            jsonArray = new JSONArray();
            for (SubjectExamDto subjectExam : subjectExams) {
                jsonArray.put(createSubjectExam(subjectExam));
            }
        }
        return jsonArray;
    }

    /**
     * Creates the subject exam.
     * 
     * @param subjectExam the subject exam
     * @return the jSON object
     */
    private static JSONObject createSubjectExam(SubjectExamDto subjectExam) {
        JSONObject jsonObject = null;
        if (subjectExam != null) {
            jsonObject = new JSONObject();
            jsonObject.put("MaximumMarks", subjectExam.getMaximumMarks());
            jsonObject.put("SubjectExamId", subjectExam.getSubjectExamId());
            jsonObject.put("RegisteredSubject", RegisteredSubjectDataAssembler.create(subjectExam.getRegisteredSubject()));
        }
        return jsonObject;
    }

    /**
     * Creates the.
     * 
     * @param exams the exams
     * @return the jSON array
     */
    public static JSONArray create(List<ExamDto> exams) {
        JSONArray jsonArray = null;
        if (exams != null && !exams.isEmpty()) {
            jsonArray = new JSONArray();
            for (ExamDto exam : exams) {
                jsonArray.put(create(exam));
            }
        }
        return jsonArray;
    }

}
