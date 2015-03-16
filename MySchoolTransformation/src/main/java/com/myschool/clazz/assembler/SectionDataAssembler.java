package com.myschool.clazz.assembler;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONObject;

import com.myschool.clazz.dto.SectionDto;

/**
 * The Class SectionDataAssembler.
 */
public class SectionDataAssembler {

    /**
     * Creates the.
     * 
     * @param resultSet the result set
     * @return the section dto
     * @throws SQLException the sQL exception
     */
    public static SectionDto create(ResultSet resultSet) throws SQLException {
        return create(resultSet, false);
    }

    /**
     * Creates the.
     * 
     * @param resultSet the result set
     * @param aliased the aliased
     * @return the section dto
     * @throws SQLException the sQL exception
     */
    public static SectionDto create(ResultSet resultSet, boolean aliased) throws SQLException {
        SectionDto section = new SectionDto();
        if (aliased) {
            section.setSectionId(resultSet.getInt("REF_SECTION_SECTION_ID"));
            section.setSectionName(resultSet.getString("REF_SECTION_SECTION_NAME"));
        } else {
            section.setSectionId(resultSet.getInt("SECTION_ID"));
            section.setSectionName(resultSet.getString("SECTION_NAME"));
        }
        return section;
    }

    /**
     * Creates the.
     * 
     * @param section the section
     * @return the jSON object
     */
    public static JSONObject create(SectionDto section) {
        JSONObject jsonObject = null;
        if (section != null) {
            jsonObject = new JSONObject();
            jsonObject.put("SectionId", section.getSectionId());
            jsonObject.put("SectionName", section.getSectionName());
        }
        return jsonObject;
    }

}
