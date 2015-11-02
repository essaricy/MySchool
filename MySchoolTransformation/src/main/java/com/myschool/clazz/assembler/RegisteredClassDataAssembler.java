package com.myschool.clazz.assembler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.myschool.clazz.dto.RegisteredClassDto;
import com.myschool.school.assembler.SchoolDataAssembler;

/**
 * The Class RegisteredClassDataAssembler.
 */
public class RegisteredClassDataAssembler {

    /**
     * Creates the.
     * 
     * @param registeredClass the registered class
     * @return the jSON object
     */
    public static JSONObject create(RegisteredClassDto registeredClass) {
        JSONObject jsonObject = null;
        if (registeredClass != null) {
            jsonObject = new JSONObject();
            jsonObject.put("ClassId", registeredClass.getClassId());
            jsonObject.put("Class", ClassDataAssembler.create(registeredClass.getClassDto()));
            jsonObject.put("Medium", MediumDataAssembler.create(registeredClass.getMedium()));
            jsonObject.put("Section", SectionDataAssembler.create(registeredClass.getSection()));
            jsonObject.put("School", SchoolDataAssembler.create(registeredClass.getSchool()));
        }
        return jsonObject;
    }

    /**
     * Creates the.
     * 
     * @param resultSet the result set
     * @return the registered class dto
     * @throws SQLException the sQL exception
     */
    public static RegisteredClassDto create(ResultSet resultSet) throws SQLException {
        RegisteredClassDto registeredClass = new RegisteredClassDto();
        registeredClass.setClassDto(ClassDataAssembler.create(resultSet, true));
        registeredClass.setClassId(resultSet.getInt("CLASS_ID"));
        registeredClass.setMedium(MediumDataAssembler.create(resultSet, true));
        registeredClass.setSchool(SchoolDataAssembler.create(resultSet, true));
        registeredClass.setSection(SectionDataAssembler.create(resultSet, true));
        return registeredClass;
    }

    /**
     * Creates the.
     * 
     * @param schools the schools
     * @return the jSON array
     */
    public static JSONArray create(List<RegisteredClassDto> schools) {
        JSONArray classesData = null;
        if (schools != null) {
            classesData = new JSONArray();
            for (RegisteredClassDto clazz : schools) {
                classesData.put(create(clazz));
            }
        }
        return classesData;
    }

}
