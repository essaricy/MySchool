package com.myschool.application.assembler;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.myschool.application.dto.MySchoolProfileDto;
import com.myschool.application.dto.OrganizationProfileDto;
import com.myschool.common.util.ConversionUtil;

/**
 * The Class ProfileDataAssembler.
 */
public class ProfileDataAssembler {

    /**
     * Creates the organization profile.
     * 
     * @param resultSet the result set
     * @return the organization profile dto
     * @throws SQLException the sQL exception
     */
    public static OrganizationProfileDto createOrganizationProfile(ResultSet resultSet) throws SQLException {
        OrganizationProfileDto organizationProfile = new OrganizationProfileDto();
        organizationProfile.setOrganizationName(resultSet.getString("ORGANIZATION_NAME"));
        organizationProfile.setCurrentAcademicYear(resultSet.getString("CURRENT_AY_NAME"));
        organizationProfile.setAddress(resultSet.getString("ADDRESS"));
        organizationProfile.setPhoneNumber(resultSet.getString("PHONE_NUMBER"));
        organizationProfile.setFaxNumber(resultSet.getString("FAX_NUMBER"));
        return organizationProfile;
    }

    /**
     * Creates the my school.
     * 
     * @param resultSet the result set
     * @return the my school profile dto
     * @throws SQLException the sQL exception
     */
    public static MySchoolProfileDto createMySchoolProfile(ResultSet resultSet) throws SQLException {
        MySchoolProfileDto mySchoolProfile = new MySchoolProfileDto();
        mySchoolProfile.setAyeInProgress(
                ConversionUtil.toBoolean(resultSet.getString("AYE_IN_PROGRESS")));
        mySchoolProfile.setEmailActive(
                ConversionUtil.toBoolean(resultSet.getString("EMAIL_ACTIVE")));
        mySchoolProfile.setEmailEmployees(
                ConversionUtil.toBoolean(resultSet.getString("EMAIL_EMPLOYEES")));
        mySchoolProfile.setEmailStudents(
                ConversionUtil.toBoolean(resultSet.getString("EMAIL_STUDENTS")));
        mySchoolProfile.setSmsActive(
                ConversionUtil.toBoolean(resultSet.getString("SMS_ACTIVE")));
        mySchoolProfile.setSmsEmployees(
                ConversionUtil.toBoolean(resultSet.getString("SMS_EMPLOYEES")));
        mySchoolProfile.setSmsStudents(
                ConversionUtil.toBoolean(resultSet.getString("SMS_STUDENTS")));
        mySchoolProfile.setUseMenuIcons(
                ConversionUtil.toBoolean(resultSet.getString("USE_MENU_ICONS")));
        mySchoolProfile.setUseEmployeeSelfSubmit(
                ConversionUtil.toBoolean(resultSet.getString("USE_EMPLOYEE_SELF_SUBMIT")));
        mySchoolProfile.setUseStudentSelfSubmit(
                ConversionUtil.toBoolean(resultSet.getString("USE_STUDENT_SELF_SUBMIT")));
        mySchoolProfile.setPinnedGallery(resultSet.getString("PINNED_GALLERY"));
        return mySchoolProfile;
    }

}
