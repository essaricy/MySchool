package com.myschool.employee.assembler;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONObject;

import com.myschool.application.assembler.RelationshipDataAssembler;
import com.myschool.common.dto.Relationship;
import com.myschool.common.util.JsonUtil;
import com.myschool.common.util.StringUtil;
import com.myschool.employee.constant.EmployeeNotificationTo;
import com.myschool.employee.dto.EmployeeContact;

/**
 * The Class EmployeeContactDataAssembler.
 */
public class EmployeeContactDataAssembler {

    /**
     * Creates the.
     * 
     * @param resultSet the result set
     * @return the employee contact
     * @throws SQLException the sQL exception
     */
    public static EmployeeContact create(ResultSet resultSet) throws SQLException {
        EmployeeContact employeeContact = new EmployeeContact();
        employeeContact.setEmergencyContactNumber(resultSet.getString("EMERGENCY_CONTACT_NUMBER"));
        employeeContact.setOfficeDeskExtension(resultSet.getString("OFFICE_DESK_EXTENSION"));
        employeeContact.setOfficeDeskPhoneNumber(resultSet.getString("OFFICE_DESK_PHONE_NUMBER"));
        employeeContact.setOfficeEmailId(resultSet.getString("OFFICE_EMAIL_ID"));
        employeeContact.setOfficeMobileNumber(resultSet.getString("OFFICE_MOBILE_NUMBER"));
        employeeContact.setPermanentAddress(resultSet.getString("PERMANENT_ADDRESS"));
        employeeContact.setPersonalEmailId(resultSet.getString("PERSONAL_EMAIL_ID"));
        employeeContact.setPersonalMobileNumber(resultSet.getString("PERSONAL_MOBILE_NUMBER"));
        employeeContact.setPresentAddress(resultSet.getString("PRESENT_ADDRESS"));
        employeeContact.setEmergencyContactRelationship(RelationshipDataAssembler.create(resultSet, true));
        employeeContact.setEmailNotificationTo(
                EmployeeNotificationTo.getByCode(resultSet.getString("EMAIL_NOTIFICATION_TO")));
        employeeContact.setSmsNotificationTo(
                EmployeeNotificationTo.getByCode(resultSet.getString("SMS_NOTIFICATION_TO")));
        return employeeContact;
    }

    /**
     * Creates the employee contact.
     * 
     * @param employeeContactData the employee contact data
     * @return the employee contact
     */
    public static EmployeeContact createEmployeeContact(JSONObject employeeContactData) {
        EmployeeContact employeeContact = null;
        if (employeeContactData != null) {
            employeeContact = new EmployeeContact();
            employeeContact.setEmergencyContactNumber(StringUtil.getValue(
                    JsonUtil.getString(employeeContactData, "EmergencyContactNumber")));
            employeeContact.setOfficeDeskExtension(StringUtil.getValue(
                    JsonUtil.getString(employeeContactData, "OfficeDeskExtension")));
            employeeContact.setOfficeDeskPhoneNumber(StringUtil.getValue(
                    JsonUtil.getString(employeeContactData, "OfficeDeskPhoneNumber")));
            employeeContact.setOfficeEmailId(StringUtil.getValue(
                    JsonUtil.getString(employeeContactData, "OfficeEmailId")));
            employeeContact.setOfficeMobileNumber(StringUtil.getValue(
                    JsonUtil.getString(employeeContactData, "OfficeMobileNumber")));
            employeeContact.setPermanentAddress(StringUtil.getValue(
                    JsonUtil.getString(employeeContactData, "PermanentAddress")));
            employeeContact.setPersonalEmailId(StringUtil.getValue(
                    JsonUtil.getString(employeeContactData, "PersonalEmailId")));
            employeeContact.setPersonalMobileNumber(StringUtil.getValue(
                    JsonUtil.getString(employeeContactData, "PersonalMobileNumber")));
            employeeContact.setPresentAddress(StringUtil.getValue(
                    JsonUtil.getString(employeeContactData, "PresentAddress")));
            Relationship emergencyContactRelationship = new Relationship();
            emergencyContactRelationship.setCode(
                    JsonUtil.getString(employeeContactData, "EmergencyContactRelationshipCode"));
            emergencyContactRelationship.setName(
                    JsonUtil.getString(employeeContactData, "EmergencyContactRelationshipName"));
            employeeContact.setEmergencyContactRelationship(emergencyContactRelationship);
            employeeContact.setEmailNotificationTo(
                    EmployeeNotificationTo.getByCode(JsonUtil.getString(employeeContactData, "EmailNotificationTo")));
            employeeContact.setSmsNotificationTo(
                    EmployeeNotificationTo.getByCode(JsonUtil.getString(employeeContactData, "SmsNotificationTo")));
        }
        return employeeContact;
    }

}
