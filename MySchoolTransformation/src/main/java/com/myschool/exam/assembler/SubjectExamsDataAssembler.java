package com.myschool.exam.assembler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.myschool.clazz.assembler.RegisteredSubjectDataAssembler;
import com.myschool.clazz.dto.RegisteredSubjectDto;
import com.myschool.clazz.dto.SubjectDto;
import com.myschool.exam.dto.SubjectExamDto;

/**
 * The Class SubjectExamsDataAssembler.
 */
public class SubjectExamsDataAssembler {

    /**
     * Creates the subject exam.
     *
     * @param resultSet the result set
     * @return the subject exam dto
     * @throws SQLException the sQL exception
     */
    public static SubjectExamDto createSubjectExam(ResultSet resultSet) throws SQLException {
        SubjectExamDto subjectExam = new SubjectExamDto();
        subjectExam.setSubjectExamId(resultSet.getInt("SUBJECT_EXAM_ID"));

        RegisteredSubjectDto registeredSubject = new RegisteredSubjectDto();
        registeredSubject.setSubjectId(resultSet.getInt("SUBJECT_ID"));

        SubjectDto subject = new SubjectDto();
        subject.setSubjectId(resultSet.getInt("REF_SUBJECT_SUBJECT_ID"));
        subject.setSubjectName(resultSet.getString("REF_SUBJECT_SUBJECT_NAME"));

        registeredSubject.setSubject(subject);
        subjectExam.setRegisteredSubject(registeredSubject);
        subjectExam.setMaximumMarks(resultSet.getInt("MAXIMUM_MARKS"));
        return subjectExam;
    }

    /**
     * Creates the subjects in exams.
     *
     * @param subjectExams the subject exams
     * @return the subjects in exams
     */
    public static JSONArray createSubjectsInExams(List<SubjectExamDto> subjectExams) {
        SubjectExamDto subjectExam = null;
        JSONArray subjectsInExamsJsonArray = null;
        if (subjectExams != null) {
            subjectsInExamsJsonArray = new JSONArray();
            for (int index = 0; index < subjectExams.size(); index++) {
                subjectExam = subjectExams.get(index);
                if (subjectExam != null) {
                    subjectsInExamsJsonArray.put(createSubjectsInExam(subjectExam));
                }
            }
        }
        return subjectsInExamsJsonArray;
    }

    /**
     * Creates the subjects in exam.
     *
     * @param subjectExam the subject exam
     * @return the subjects in exam
     */
    public static JSONObject createSubjectsInExam(SubjectExamDto subjectExam) {
        JSONObject jsonObject = null;
        if (subjectExam != null) {
            jsonObject = new JSONObject();
            jsonObject.put("SubjectExamId", subjectExam.getSubjectExamId());
            jsonObject.put("MaximumMarks", subjectExam.getMaximumMarks());
            jsonObject.put("RegisteredSubject", RegisteredSubjectDataAssembler.create(subjectExam.getRegisteredSubject()));
        }
        return jsonObject;
    }

}
