package com.myschool.clazz.assembler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.myschool.clazz.dto.RegisteredClassDto;
import com.myschool.clazz.dto.RegisteredSubjectDto;
import com.myschool.clazz.dto.SubjectDto;
import com.myschool.school.assembler.SchoolDataAssembler;

/**
 * The Class RegisteredSubjectDataAssembler.
 */
public class RegisteredSubjectDataAssembler {

    /**
     * Creates the registered subject.
     *
     * @param resultSet the result set
     * @return the registered subject dto
     * @throws SQLException the sQL exception
     */
    public static RegisteredSubjectDto createRegisteredSubject(
            ResultSet resultSet) throws SQLException {
        RegisteredSubjectDto registeredSubjectDto = new RegisteredSubjectDto();

        RegisteredClassDto registeredClass = new RegisteredClassDto();
        SubjectDto subject = new SubjectDto();
        subject.setSubjectId(resultSet.getInt("REF_SUBJECT_ID"));
        subject.setSubjectName(resultSet.getString("REF_SUBJECT_NAME"));

        registeredSubjectDto.setRegisteredClass(registeredClass);
        registeredSubjectDto.setSubject(subject);
        registeredSubjectDto.setSubjectId(resultSet.getInt("SUBJECT_ID"));
        return registeredSubjectDto;
    }

    /**
     * Creates the registered subject.
     * 
     * @param resultSet the result set
     * @return the registered subject dto
     * @throws SQLException the sQL exception
     */
    public static RegisteredSubjectDto create(
            ResultSet resultSet) throws SQLException {
        RegisteredSubjectDto registeredSubject = new RegisteredSubjectDto();
        registeredSubject.setSubjectId(resultSet.getInt("SUBJECT_SUBJECT_ID"));
        registeredSubject.setSubject(SubjectDataAssembler.createSubject(resultSet, true));
        RegisteredClassDto registeredClass = RegisteredClassDataAssembler.create(resultSet);
        registeredSubject.setRegisteredClass(registeredClass);
        registeredClass.setSchool(SchoolDataAssembler.create(resultSet, true));
        return registeredSubject;
    }

    /**
     * Creates the.
     * 
     * @param registeredSubjects the registered subjects
     * @return the jSON array
     */
    public static JSONArray create(List<RegisteredSubjectDto> registeredSubjects) {
        JSONArray jsonArray = null;
        if (registeredSubjects != null) {
            jsonArray = new JSONArray();
            for (RegisteredSubjectDto registeredSubject : registeredSubjects) {
                jsonArray.put(create(registeredSubject));
            }
        }
        return jsonArray;
    }

    /**
     * Creates the.
     * 
     * @param registeredSubject the registered subject
     * @return the jSON object
     */
    public static JSONObject create(RegisteredSubjectDto registeredSubject) {
        JSONObject jsonObject = null;
        if (registeredSubject != null) {
            jsonObject = new JSONObject();
            jsonObject.put("RegisteredSubjectId", registeredSubject.getSubjectId());
            jsonObject.put("Subject", SubjectDataAssembler.createSubject(registeredSubject.getSubject()));
            jsonObject.put("RegisteredClass", RegisteredClassDataAssembler.create(registeredSubject.getRegisteredClass()));
        }
        return jsonObject;
    }

}
