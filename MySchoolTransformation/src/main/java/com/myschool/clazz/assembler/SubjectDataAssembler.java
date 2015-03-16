package com.myschool.clazz.assembler;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONObject;

import com.myschool.clazz.dto.RegisteredClassDto;
import com.myschool.clazz.dto.RegisteredSubjectDto;
import com.myschool.clazz.dto.SubjectDto;

/**
 * The Class SubjectDataAssembler.
 */
public class SubjectDataAssembler {

    /**
     * Creates the subject.
     *
     * @param resultSet the result set
     * @return the subject dto
     * @throws SQLException the sQL exception
     */
    public static SubjectDto createSubject(ResultSet resultSet, boolean aliased)
            throws SQLException {
        SubjectDto subject = new SubjectDto();
        if (aliased) {
            subject.setSubjectId(resultSet.getInt("REF_SUBJECT_SUBJECT_ID"));
            subject.setSubjectName(resultSet.getString("REF_SUBJECT_SUBJECT_NAME"));
        } else {
            subject.setSubjectId(resultSet.getInt("SUBJECT_ID"));
            subject.setSubjectName(resultSet.getString("SUBJECT_NAME"));
        }
        return subject;
    }

    /**
     * Creates the registered subject.
     *
     * @param resultSet the result set
     * @return the registered subject dto
     * @throws SQLException the sQL exception
     */
    public static RegisteredSubjectDto createRegisteredSubject(
            ResultSet resultSet) throws SQLException {
        RegisteredSubjectDto registeredSubject = new RegisteredSubjectDto();
        registeredSubject.setSubjectId(resultSet.getInt("SUBJECT_ID"));
        SubjectDto subject = new SubjectDto();
        subject.setSubjectId(resultSet.getInt("REF_SUBJECT_ID"));
        registeredSubject.setSubject(subject );
        RegisteredClassDto registeredClass = new RegisteredClassDto();
        registeredClass.setClassId(resultSet.getInt("CLASS_ID"));
        registeredSubject.setRegisteredClass(registeredClass);
        return registeredSubject;
    }

    /**
     * Creates the subject.
     *
     * @param subject the subject
     * @return the jSON object
     */
    public static JSONObject createSubject(SubjectDto subject) {
        JSONObject jsonObject = null;
        if (subject != null) {
            jsonObject = new JSONObject();
            jsonObject.put("SubjectId", subject.getSubjectId());
            jsonObject.put("SubjectName", subject.getSubjectName());
        }
        return jsonObject;
    }

}
