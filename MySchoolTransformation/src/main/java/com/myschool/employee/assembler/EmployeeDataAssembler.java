package com.myschool.employee.assembler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import org.json.JSONObject;

import com.myschool.branch.assembler.BranchDataAssembler;
import com.myschool.branch.dto.BranchDto;
import com.myschool.common.assembler.ImageDataAssembler;
import com.myschool.common.constants.RecordStatus;
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

            employeeSearchCriteriaDto.setEmployeeNumber(JsonUtil.getString(employeeSearchCriteria, "EmployeeNumber"));
            employeeSearchCriteriaDto.setEmployeeName(JsonUtil.getString(employeeSearchCriteria, "EmployeeName"));
            employeeSearchCriteriaDto.setDesignationId(JsonUtil.getInt(employeeSearchCriteria, "Designation"));
            employeeSearchCriteriaDto.setReportingToEmployeeNumber(JsonUtil.getString(employeeSearchCriteria, "ReportingTo"));
            employeeSearchCriteriaDto.setEmployedAtBranchCode(JsonUtil.getString(employeeSearchCriteria, "EmployedAt"));

            if (searchType.equalsIgnoreCase("ADVANCED")) {
                employeeSearchCriteriaDto.setEmploymentStatusId(JsonUtil.getInt(employeeSearchCriteria, "EmploymentStatus"));
                employeeSearchCriteriaDto.setGender(JsonUtil.getString(employeeSearchCriteria, "Gender"));
                employeeSearchCriteriaDto.setBloodGroup(JsonUtil.getString(employeeSearchCriteria, "BloodGroup"));
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
            employee.setEmployeeId(JsonUtil.getInt(employeeData, "EmployeeId"));
            employee.setEmployeeNumber(StringUtil.getValue(JsonUtil.getString(employeeData, "EmployeeNumber")));
            employee.setImageName(StringUtil.getValue(JsonUtil.getString(employeeData, "ImageName")));
            employee.setFirstName(StringUtil.getValue(JsonUtil.getString(employeeData, "FirstName")));
            employee.setMiddleName(StringUtil.getValue(JsonUtil.getString(employeeData, "MiddleName")));
            employee.setLastName(StringUtil.getValue(JsonUtil.getString(employeeData, "LastName")));
            employee.setGender(StringUtil.getValue(JsonUtil.getString(employeeData, "Gender")));
            employee.setDateOfBirth(StringUtil.getValue(JsonUtil.getString(employeeData, "DateOfBirth")));
            employee.setBloodGroup(StringUtil.getValue(JsonUtil.getString(employeeData, "BloodGroup")));
            employee.setNationality(StringUtil.getValue(JsonUtil.getString(employeeData, "Nationality")));
            employee.setMaritalStatus(StringUtil.getValue(JsonUtil.getString(employeeData, "MaritalStatus")));
            employee.setWeddingDay(StringUtil.getValue(JsonUtil.getString(employeeData, "WeddingDay")));
            employee.setEmploymentStartDate(StringUtil.getValue(JsonUtil.getString(employeeData, "EmploymentStartDate")));
            employee.setEmploymentEndDate(StringUtil.getValue(JsonUtil.getString(employeeData, "EmploymentEndDate")));
            //employee.setVerified(ConversionUtil.toBoolean(JsonUtil.getString(employeeData, "Verified")));
            employee.setVerify(ConversionUtil.toBoolean(JsonUtil.getString(employeeData, "Verify")));
            // load reporting to
            EmployeeDto reportingTo = new EmployeeDto();
            reportingTo.setEmployeeId(JsonUtil.getInt(employeeData, "ReportingTo"));
            employee.setReportingTo(reportingTo);
            employee.setRemarks(StringUtil.getValue(JsonUtil.getString(employeeData, "Remarks")));

            BranchDto branch = new BranchDto();
            branch.setBranchId(JsonUtil.getInt(employeeData, "EmployedAtBranch"));
            employee.setEmployedAtBranch(branch);

            DesignationDto designation = new DesignationDto();
            designation.setDesignationId(JsonUtil.getInt(employeeData, "DesignationId"));
            employee.setDesignation(designation);

            EmploymentStatus employmentStatus = new EmploymentStatus();
            employmentStatus.setStatusId(JsonUtil.getInt(employeeData, "EmploymentStatusId"));
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
            String verifiedStatus = ReportDataAssembler.getString(reportCriteriaValues, ReportCriteriaTokenConstants.VERIFIED_STATUS);
            System.out.println("ReportDataAssembler.getString(reportCriteriaValues, ReportCriteriaTokenConstants.VERIFIED_STATUS)=" + verifiedStatus);
            if (ConversionUtil.toBoolean(verifiedStatus)) {
                employeeSearchCriteria.setRecordStatus(RecordStatus.VERIFIED);
            } else {
                employeeSearchCriteria.setRecordStatus(RecordStatus.UNVERIFIED);
            }
            System.out.println("employeeSearchCriteria.getRecordStatus()=" + employeeSearchCriteria.getRecordStatus());
        }
        return employeeSearchCriteria;
    }

    /**
     * Creates the.
     *
     * @param employee the employee
     * @return the JSON object
     */
    public static JSONObject create(EmployeeDto employee) {
        JSONObject jsonObject = null;
        if (employee != null) {
            jsonObject = new JSONObject();
            jsonObject.put("EmployeeId", employee.getEmployeeId());
            jsonObject.put("EmployeeNumber", employee.getEmployeeNumber());
            jsonObject.put("ImageAccess", ImageDataAssembler.create(employee.getImageAccess()));
            
        }
        return jsonObject;
    }

}
