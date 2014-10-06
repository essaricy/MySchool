package com.myschool.clazz.assembler;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONObject;

import com.myschool.clazz.dto.ClassDto;
import com.myschool.clazz.dto.MediumDto;
import com.myschool.clazz.dto.RegisteredClassDto;
import com.myschool.clazz.dto.SectionDto;
import com.myschool.school.dto.SchoolDto;

/**
 * The Class ClassDataAssembler.
 */
public class ClassDataAssembler {

    /**
     * Creates the.
     * 
     * @param resultSet the result set
     * @return the class dto
     * @throws SQLException the sQL exception
     */
    public static ClassDto create(ResultSet resultSet) throws SQLException {
        return create(resultSet, false);
    }

    /**
     * Creates the class.
     * 
     * @param resultSet the result set
     * @param aliased the aliased
     * @return the class dto
     * @throws SQLException the sQL exception
     */
    public static ClassDto create(ResultSet resultSet, boolean aliased) throws SQLException {
        ClassDto classDto = new ClassDto();
        if (aliased) {
            classDto.setClassId(resultSet.getInt("REF_CLASS_CLASS_ID"));
            classDto.setClassName(resultSet.getString("REF_CLASS_CLASS_NAME"));
        } else {
            classDto.setClassId(resultSet.getInt("CLASS_ID"));
            classDto.setClassName(resultSet.getString("CLASS_NAME"));
            classDto.setPromotionOrder(resultSet.getInt("PROMOTION_ORDER"));
        }
        return classDto;
    }

    /**
     * Creates the registered class.
     *
     * @param resultSet the result set
     * @return the registered class dto
     * @throws SQLException the sQL exception
     */
    public static RegisteredClassDto createRegisteredClass(ResultSet resultSet) throws SQLException {
        ClassDto classDto = new ClassDto();
        MediumDto medium = new MediumDto();
        SectionDto section = new SectionDto();
        SchoolDto school = new SchoolDto();
        RegisteredClassDto registeredClassDto = new RegisteredClassDto();

        classDto.setClassId(resultSet.getInt("REF_CLASS_ID"));
        medium.setMediumId(resultSet.getInt("REF_MEDIUM_ID"));
        section.setSectionId(resultSet.getInt("REF_SECTION_ID"));
        school.setSchoolId(resultSet.getInt("SCHOOL_ID"));

        registeredClassDto.setClassId(resultSet.getInt("CLASS_ID"));
        registeredClassDto.setClassDto(classDto);
        registeredClassDto.setMedium(medium);
        registeredClassDto.setSection(section);
        registeredClassDto.setSchool(school);
        return registeredClassDto;
    }

    /**
     * Creates the.
     * 
     * @param classDto the class dto
     * @return the jSON object
     */
    public static JSONObject create(ClassDto classDto) {
        JSONObject jsonObject = null;
        if (classDto != null) {
            jsonObject = new JSONObject();
            jsonObject.put("ClassId", classDto.getClassId());
            jsonObject.put("ClassName", classDto.getClassName());
            jsonObject.put("PromotionOrder", classDto.getPromotionOrder());
        }
        return jsonObject;
    }

}
