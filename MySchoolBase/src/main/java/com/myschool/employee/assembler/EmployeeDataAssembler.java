package com.myschool.employee.assembler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import org.json.JSONObject;

import com.myschool.branch.assembler.BranchDataAssembler;
import com.myschool.branch.dto.BranchDto;
import com.myschool.common.util.ConversionUtil;
import com.myschool.common.util.JsonUtil;
import com.myschool.common.util.StringUtil;
import com.myschool.employee.dto.DesignationDto;
import com.myschool.employee.dto.EmployeeDto;
import com.myschool.employee.dto.EmployeeSearchCriteriaDto;
import com.myschool.employee.dto.EmploymentStatus;
import com.myschool.report.assembler.ReportDataAssembler;
import com.myschool.report.constants.ReportCriteriaTokenConstants;
import com.myschool.report.dto.ReportCriteriaToken;

/**
 * The Class EmployeeDataAssembler.
 */
public class EmployeeDataAssembler {

    /**
     * Creates the employee.
     *
     * @param resultSet the result set
     * @return the employee dto
     * @throws SQLException the sQL exception
     */
    public static EmployeeDto createEmployee(ResultSet resultSet) throws SQLException {
        EmployeeDto employee = new EmployeeDto();
        employee.setEmployeeId(resultSet.getInt("EMPLOYEE_ID"));
        employee.setEmployeeNumber(resultSet.getString("EMPLOYEE_NUMBER"));
        employee.setFirstName(resultSet.getString("FIRST_NAME"));
        employee.setMiddleName(resultSet.getString("MIDDLE_NAME"));
        employee.setLastName(resultSet.getString("LAST_NAME"));
        employee.setGender(resultSet.getString("GENDER"));
        employee.setDateOfBirth(ConversionUtil.toApplicationDateFromStorageDate(
                resultSet.getString("DATE_OF_BIRTH")));
        employee.setBloodGroup(resultSet.getString("BLOOD_GROUP"));
        employee.setNationality(resultSet.getString("NATIONALITY"));
        employee.setMaritalStatus(resultSet.getString("MARITAL_STATUS"));
        employee.setWeddingDay(resultSet.getString("WEDDING_DAY"));
        employee.setEmploymentStartDate(ConversionUtil.toApplicationDateFromStorageDate(
                resultSet.getString("EMPLOYMENT_START_DATE")));
        employee.setEmploymentEndDate(ConversionUtil.toApplicationDateFromStorageDate(
                resultSet.getString("EMPLOYMENT_END_DATE")));
        // load reporting to
        EmployeeDto reportingTo = new EmployeeDto();
        reportingTo.setEmployeeId(resultSet.getInt("REPORTING_TO"));
        employee.setReportingTo(reportingTo);
        employee.setRemarks(resultSet.getString("REMARKS"));
        employee.setVerified(ConversionUtil.toBoolean(resultSet.getString("VERIFIED")));

        employee.setEmployedAtBranch(BranchDataAssembler.create(resultSet, true));
        employee.setDesignation(DesignationDataAssembler.create(resultSet, true));
        employee.setEmploymentStatus(EmploymentStatusDataAssembler.create(resultSet, true));
        return employee;
    }

    /**
     * Creates the employee search criteria dto.
     * 
     * @param employeeSearchCriteria the employee search criteria
     * @return the employee search criteria dto
     */
    public static EmployeeSearchCriteriaDto createEmployeeSearchCriteriaDto(
            JSONObject employeeSearchCriteria) {
        EmployeeSearchCriteriaDto employeeSearchCriteriaDto = null;
        if (employeeSearchCriteria != null) {
            employeeSearchCriteriaDto = new EmployeeSearchCriteriaDto();

            String searchType = employeeSearchCriteria.getString("SearchType");

            employeeSearchCriteriaDto.setEmployeeNumber(JsonUtil.getStringValue(employeeSearchCriteria, "EmployeeNumber"));
            employeeSearchCriteriaDto.setEmployeeName(JsonUtil.getStringValue(employeeSearchCriteria, "EmployeeName"));
            employeeSearchCriteriaDto.setDesignationId(JsonUtil.getIntValue(employeeSearchCriteria, "Designation"));
            employeeSearchCriteriaDto.setReportingToEmployeeNumber(JsonUtil.getStringValue(employeeSearchCriteria, "ReportingTo"));
            employeeSearchCriteriaDto.setEmployedAtBranchCode(JsonUtil.getStringValue(employeeSearchCriteria, "EmployedAt"));

            if (searchType.equalsIgnoreCase("ADVANCED")) {
                employeeSearchCriteriaDto.setEmploymentStatusId(JsonUtil.getIntValue(employeeSearchCriteria, "EmploymentStatus"));
                employeeSearchCriteriaDto.setGender(JsonUtil.getStringValue(employeeSearchCriteria, "Gender"));
                employeeSearchCriteriaDto.setBloodGroup(JsonUtil.getStringValue(employeeSearchCriteria, "BloodGroup"));
                employeeSearchCriteriaDto.setExperienceInMonthsMin(JsonUtil.getIntValue(employeeSearchCriteria, "ExperienceMin", -1));
                employeeSearchCriteriaDto.setExperienceInMonthsMax(JsonUtil.getIntValue(employeeSearchCriteria, "ExperienceMax", -1));
            }
        }
        return employeeSearchCriteriaDto;
    }

    /**
     * Creates the.
     * 
     * @param employeeData the employee data
     * @return the employee dto
     */
    public static EmployeeDto create(JSONObject employeeData) {
        EmployeeDto employee = null;
        if (employeeData != null) {
            employee = new EmployeeDto();
            String employeeId = employeeData.getString("EmployeeId");
            if (!StringUtil.isNullOrBlank(employeeId)) {
                employee.setEmployeeId(Integer.parseInt(employeeId.trim()));
            }
            employee.setEmployeeNumber(StringUtil.getValue(employeeData.getString("EmployeeNumber")));
            employee.setImageName(StringUtil.getValue(employeeData.getString("ImageName")));
            employee.setFirstName(StringUtil.getValue(employeeData.getString("FirstName")));
            employee.setMiddleName(StringUtil.getValue(employeeData.getString("MiddleName")));
            employee.setLastName(StringUtil.getValue(employeeData.getString("LastName")));
            employee.setGender(StringUtil.getValue(employeeData.getString("Gender")));
            employee.setDateOfBirth(StringUtil.getValue(employeeData.getString("DateOfBirth")));
            employee.setBloodGroup(StringUtil.getValue(employeeData.getString("BloodGroup")));
            employee.setNationality(StringUtil.getValue(employeeData.getString("Nationality")));
            employee.setMaritalStatus(StringUtil.getValue(employeeData.getString("MaritalStatus")));
            employee.setWeddingDay(StringUtil.getValue(employeeData.getString("WeddingDay")));
            employee.setEmploymentStartDate(StringUtil.getValue(employeeData.getString("EmploymentStartDate")));
            employee.setEmploymentEndDate(StringUtil.getValue(employeeData.getString("EmploymentEndDate")));
            employee.setVerified(ConversionUtil.toBoolean(employeeData.getString("Verified")));
            // load reporting to
            EmployeeDto reportingTo = new EmployeeDto();
            reportingTo.setEmployeeId(employeeData.getInt("ReportingTo"));
            employee.setReportingTo(reportingTo);
            employee.setRemarks(StringUtil.getValue(employeeData.getString("Remarks")));

            BranchDto branch = new BranchDto();
            branch.setBranchId(employeeData.getInt("EmployedAtBranch"));
            employee.setEmployedAtBranch(branch);

            DesignationDto designation = new DesignationDto();
            designation.setDesignationId(employeeData.getInt("DesignationId"));
            employee.setDesignation(designation);

            EmploymentStatus employmentStatus = new EmploymentStatus();
            employmentStatus.setStatusId(employeeData.getInt("EmploymentStatusId"));
            employee.setEmploymentStatus(employmentStatus);
            // Create employee contact
            employee.setEmployeeContact(EmployeeContactDataAssembler.createEmployeeContact(
                    employeeData.getJSONObject("EmployeeContact")));
            employee.setEmployeeDocuments(EmployeeDocumentDataAssembler.create(
                    employeeData.getJSONArray("EmployeeDocuments")));
            employee.setEmployeeEducations(EmployeeEducationDataAssembler.create(
                    employeeData.getJSONArray("EmployeeEducations")));
            employee.setEmployeeExperiences(EmployeeExperienceDataAssembler.create(
                    employeeData.getJSONArray("EmployeeExperiences")));
            employee.setEmployeePromotions(EmployeePromotionDataAssembler.create(
                    employeeData.getJSONArray("EmployeePromotions")));
            employee.setEmployeeSubjects(EmployeeSubjectDataAssembler.create(
                    employeeData.getJSONArray("EmployeeTeachingSubjects")));
        }
        return employee;
    }

    /**
     * Creates the.
     * 
     * @param reportCriteriaValues the report criteria values
     * @return the employee search criteria dto
     */
    public static EmployeeSearchCriteriaDto create(
            Map<ReportCriteriaToken, String> reportCriteriaValues) {
        EmployeeSearchCriteriaDto employeeSearchCriteria = null;
        if (reportCriteriaValues != null) {
            employeeSearchCriteria = new EmployeeSearchCriteriaDto();
            // blood group
            employeeSearchCriteria.setBloodGroup(ReportDataAssembler.getString(
                    reportCriteriaValues, ReportCriteriaTokenConstants.BLOOD_GROUP));
            // DOB criteria
            String[] dateOfBirthValues = ReportDataAssembler.getDates(reportCriteriaValues, ReportCriteriaTokenConstants.DATE_OF_BIRTH);
            employeeSearchCriteria.setDateOfBirthMin(dateOfBirthValues[0]);
            employeeSearchCriteria.setDateOfBirthMax(dateOfBirthValues[1]);
            // designation
            employeeSearchCriteria.setDesignationId(ReportDataAssembler.getInt(reportCriteriaValues, ReportCriteriaTokenConstants.DESIGNATION));
            // Branch
            employeeSearchCriteria.setEmployedAtBranchCode(ReportDataAssembler.getString(
                    reportCriteriaValues, ReportCriteriaTokenConstants.EMPLOYED_AT));
            // name
            employeeSearchCriteria.setEmployeeName(ReportDataAssembler.getString(
                    reportCriteriaValues, ReportCriteriaTokenConstants.EMPLOYEE_NAME));
            // employee number
            employeeSearchCriteria.setEmployeeNumber(ReportDataAssembler.getString(
                    reportCriteriaValues, ReportCriteriaTokenConstants.EMPLOYEE_NUMBER));
            // employment start date
            String[] employmentDateValues = ReportDataAssembler.getDates(reportCriteriaValues, ReportCriteriaTokenConstants.EMPLOYMENT_DATE);
            employeeSearchCriteria.setEmploymentStartDateMin(employmentDateValues[0]);
            employeeSearchCriteria.setEmploymentStartDateMax(employmentDateValues[1]);
            // designation id
            employeeSearchCriteria.setEmploymentStatusId(ReportDataAssembler.getInt(reportCriteriaValues, ReportCriteriaTokenConstants.EMPLOYMENT_STATUS));
            // Experience
            int[] experienceInMonthValues = ReportDataAssembler.getInts(reportCriteriaValues, ReportCriteriaTokenConstants.EXPERIENCE, -1);
            employeeSearchCriteria.setExperienceInMonthsMin(experienceInMonthValues[0]);
            employeeSearchCriteria.setExperienceInMonthsMax(experienceInMonthValues[1]);
            // gender
            employeeSearchCriteria.setGender(ReportDataAssembler.getString(
                    reportCriteriaValues, ReportCriteriaTokenConstants.GENDER));
            employeeSearchCriteria.setReportingToEmployeeNumber(ReportDataAssembler.getString(
                    reportCriteriaValues, ReportCriteriaTokenConstants.REPORTING_TO));
            employeeSearchCriteria.setVerifiedStatus(ReportDataAssembler.getString(
                    reportCriteriaValues, ReportCriteriaTokenConstants.VERIFIED_STATUS));
        }
        return employeeSearchCriteria;
    }

}
