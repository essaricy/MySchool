package com.myschool.organization.assembler;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.myschool.organization.dto.Organization;
import com.myschool.organization.dto.OrganizationManifest;
import com.myschool.organization.dto.OrganizationPreferences;
import com.quasar.core.util.ConversionUtil;

/**
 * The Class OrganizationDataAssembler.
 */
public class OrganizationDataAssembler {

    /**
     * Creates the.
     *
     * @param resultSet the result set
     * @return the organization
     * @throws SQLException the SQL exception
     */
    public static Organization create(ResultSet resultSet) throws SQLException {
        Organization organization = new Organization();
        organization.setId(resultSet.getInt("ID"));
        organization.setName(resultSet.getString("NAME"));
        organization.setAddress(resultSet.getString("ADDRESS"));
        organization.setPhoneNumber(resultSet.getString("PHONE_NUMBER"));
        organization.setFaxNumber(resultSet.getString("FAX_NUMBER"));
        return organization;
    }

    /**
     * Creates the preferences.
     *
     * @param resultSet the result set
     * @return the organization preferences
     * @throws SQLException the SQL exception
     */
    public static OrganizationPreferences createPreferences(
            ResultSet resultSet) throws SQLException {
        OrganizationPreferences organizationPreferences = new OrganizationPreferences();
        organizationPreferences.setId(resultSet.getInt("ORGANIZATION_ID"));
        organizationPreferences.setDefaultGallery(resultSet.getString("PINNED_GALLERY"));
        organizationPreferences.setDefaultTheme(resultSet.getString("DEFAULT_THEME"));

        organizationPreferences.setEmailActive(ConversionUtil.toBoolean(resultSet.getString("NOTIF_USE_EMAILS")));
        organizationPreferences.setEmailEmployees(ConversionUtil.toBoolean(resultSet.getString("NOTIF_EMAIL_EMPLOYEES")));
        organizationPreferences.setEmailStudents(ConversionUtil.toBoolean(resultSet.getString("NOTIF_EMAIL_STUDENTS")));
        organizationPreferences.setSmsActive(ConversionUtil.toBoolean(resultSet.getString("NOTIF_USE_TEXT")));
        organizationPreferences.setSmsEmployees(ConversionUtil.toBoolean(resultSet.getString("NOTIF_TEXT_EMPLOYEES")));
        organizationPreferences.setSmsStudents(ConversionUtil.toBoolean(resultSet.getString("NOTIF_TEXT_STUDENTS")));
        organizationPreferences.setUseEmployeeSelfSubmit(ConversionUtil.toBoolean(resultSet.getString("USE_EMPLOYEE_SELF_SUBMIT")));
        organizationPreferences.setUseStudentSelfSubmit(ConversionUtil.toBoolean(resultSet.getString("USE_STUDENT_SELF_SUBMIT")));
        organizationPreferences.setUseMenuIcons(ConversionUtil.toBoolean(resultSet.getString("USE_MENU_ICONS")));
        return organizationPreferences;
    }

    /**
     * Creates the manifest.
     *
     * @param resultSet the result set
     * @return the organization manifest
     * @throws SQLException the SQL exception
     */
    public static OrganizationManifest createManifest(ResultSet resultSet) throws SQLException {
        OrganizationManifest organizationManifest = new OrganizationManifest();
        organizationManifest.setId(resultSet.getInt("ORGANIZATION_ID"));
        organizationManifest.setAcademicYearName(resultSet.getString("CURRENT_AY_NAME"));
        organizationManifest.setMapURL(resultSet.getString("MAP_URL"));
        organizationManifest.setAyeInProgress(ConversionUtil.toBoolean(resultSet.getString("AYE_IN_PROGRESS")));
        return organizationManifest;
    }

}
