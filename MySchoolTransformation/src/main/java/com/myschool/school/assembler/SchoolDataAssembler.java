package com.myschool.school.assembler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.myschool.branch.assembler.BranchDataAssembler;
import com.myschool.branch.assembler.DivisionDataAssembler;
import com.myschool.report.dto.ReportCriteriaToken;
import com.myschool.school.dto.SchoolDto;

/**
 * The Class SchoolDataAssembler.
 */
public class SchoolDataAssembler {

    /**
     * Creates the school.
     *
     * @param resultSet the result set
     * @return the school dto
     * @throws SQLException the sQL exception
     */
    public static SchoolDto create(ResultSet resultSet) throws SQLException {
        SchoolDto school = null;

        school = new SchoolDto();
        school.setSchoolId(resultSet.getInt("SCHOOL_ID"));
        school.setSchoolName(resultSet.getString("SCHOOL_NAME"));
        school.setAddress(resultSet.getString("ADDRESS"));
        school.setPrimaryPhoneNumber(resultSet.getString("PRIMARY_PHONE_NUMBER"));
        school.setSecondaryPhoneNumber(resultSet.getString("SECONDARY_PHONE_NUMBER"));
        school.setMobileNumber(resultSet.getString("MOBILE_NUMBER"));
        school.setFaxNumber(resultSet.getString("FAX_NUMBER"));
        school.setEmailId(resultSet.getString("EMAIL_ID"));
        school.setMapUrl(resultSet.getString("MAP_URL"));

        school.setBranch(BranchDataAssembler.create(resultSet, true));
        school.setDivision(DivisionDataAssembler.create(resultSet, true));

        return school;
    }

    /**
     * Creates the.
     * 
     * @param resultSet the result set
     * @param aliased the aliased
     * @return the school dto
     * @throws SQLException the sQL exception
     */
    public static SchoolDto create(ResultSet resultSet, boolean aliased) throws SQLException {
        SchoolDto school = new SchoolDto();

        if (aliased) {
            school.setSchoolId(resultSet.getInt("SCHOOL_SCHOOL_ID"));
            school.setSchoolName(resultSet.getString("SCHOOL_SCHOOL_NAME"));
            school.setAddress(resultSet.getString("SCHOOL_ADDRESS"));
            school.setPrimaryPhoneNumber(resultSet.getString("SCHOOL_PRIMARY_PHONE_NUMBER"));
            school.setSecondaryPhoneNumber(resultSet.getString("SCHOOL_SECONDARY_PHONE_NUMBER"));
            school.setMobileNumber(resultSet.getString("SCHOOL_MOBILE_NUMBER"));
            school.setFaxNumber(resultSet.getString("SCHOOL_FAX_NUMBER"));
            school.setEmailId(resultSet.getString("SCHOOL_EMAIL_ID"));
            school.setMapUrl(resultSet.getString("SCHOOL_MAP_URL"));
        } else {
            school.setSchoolId(resultSet.getInt("SCHOOL_ID"));
            school.setSchoolName(resultSet.getString("SCHOOL_NAME"));
            school.setAddress(resultSet.getString("ADDRESS"));
            school.setPrimaryPhoneNumber(resultSet.getString("PRIMARY_PHONE_NUMBER"));
            school.setSecondaryPhoneNumber(resultSet.getString("SECONDARY_PHONE_NUMBER"));
            school.setMobileNumber(resultSet.getString("MOBILE_NUMBER"));
            school.setFaxNumber(resultSet.getString("FAX_NUMBER"));
            school.setEmailId(resultSet.getString("EMAIL_ID"));
            school.setMapUrl(resultSet.getString("MAP_URL"));
        }
        school.setBranch(BranchDataAssembler.create(resultSet, true));
        school.setDivision(DivisionDataAssembler.create(resultSet, true));

        return school;
    }

    /**
     * Creates the.
     * 
     * @param school the school
     * @return the jSON object
     */
    public static JSONObject create(SchoolDto school) {
        JSONObject jsonObject = null;
        if (school != null) {
            jsonObject = new JSONObject();
            jsonObject.put("SchoolId", school.getSchoolId());
            jsonObject.put("Address", school.getAddress());
            jsonObject.put("Branch", BranchDataAssembler.create(school.getBranch()));
            jsonObject.put("Division", DivisionDataAssembler.create(school.getDivision()));
            jsonObject.put("EmailId", school.getEmailId());
            jsonObject.put("FaxNumber", school.getFaxNumber());
            jsonObject.put("MapUrl", school.getMapUrl());
            jsonObject.put("MobileNumber", school.getMobileNumber());
            jsonObject.put("PrimaryPhoneNumber", school.getPrimaryPhoneNumber());
            jsonObject.put("SchoolName", school.getSchoolName());
            jsonObject.put("SecondaryPhoneNumber", school.getSecondaryPhoneNumber());
        }
        return jsonObject;
    }

    /**
     * Creates the.
     * 
     * @param reportCriteriaValues the report criteria values
     * @return the school dto
     */
    public static SchoolDto create(
            Map<ReportCriteriaToken, String> reportCriteriaValues) {
        SchoolDto school = null;
        if (reportCriteriaValues != null && !reportCriteriaValues.isEmpty()) {
            school = new SchoolDto();
            school.setBranch(BranchDataAssembler.create(reportCriteriaValues));
            school.setDivision(DivisionDataAssembler.create(reportCriteriaValues));
        }
        return school;
    }

    /**
     * Creates the.
     * 
     * @param schools the schools
     * @return the jSON array
     */
    public static JSONArray create(List<SchoolDto> schools) {
        JSONArray schoolsData = null;
        if (schools != null) {
            schoolsData = new JSONArray();
            for (SchoolDto school : schools) {
                schoolsData.put(create(school));
            }
        }
        return schoolsData;
    }

}
