package com.myschool.student.assembler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.myschool.clazz.assembler.ClassDataAssembler;
import com.myschool.clazz.assembler.MediumDataAssembler;
import com.myschool.clazz.assembler.RegisteredClassDataAssembler;
import com.myschool.clazz.assembler.SectionDataAssembler;
import com.myschool.clazz.dto.RegisteredClassDto;
import com.myschool.common.dto.PersonalDetailsDto;
import com.myschool.common.util.ConversionUtil;
import com.myschool.common.util.JsonUtil;
import com.myschool.common.util.StringUtil;
import com.myschool.report.assembler.ReportDataAssembler;
import com.myschool.report.constants.ReportCriteriaTokenConstants;
import com.myschool.report.dto.ReportCriteriaToken;
import com.myschool.school.assembler.SchoolDataAssembler;
import com.myschool.student.dto.AdmissionStatus;
import com.myschool.student.dto.StudentDto;
import com.myschool.student.dto.StudentSearchCriteriaDto;

/**
 * The Class StudentDataAssembler.
 */
public class StudentDataAssembler {

    /**
     * Creates the student.
     *
     * @param student the student
     * @return the jSON object
     */
    public static JSONObject create(StudentDto student) {
        JSONObject jsonObject = null;
        if (student != null) {
            jsonObject = new JSONObject();
            jsonObject.put("StudentId", student.getStudentId());
            //jsonObject.put("LastAcademicYear", student.getLastAcademicYear());
            jsonObject.put("AdmissionNumber", student.getAdmissionNumber());
            jsonObject.put("DateOfJoining", student.getDateOfJoining());
            jsonObject.put("PersonalDetails", createPersonalDetails(student.getPersonalDetails()));
            jsonObject.put("RegisteredClass", RegisteredClassDataAssembler.create(student.getRegisteredClassDto()));
            jsonObject.put("Remarks", student.getRemarks());
        }
        return jsonObject;
    }

    /**
     * Creates the personal details.
     *
     * @param personalDetails the personal details
     * @return the jSON object
     */
    private static JSONObject createPersonalDetails(
            PersonalDetailsDto personalDetails) {
        JSONObject jsonObject = null;
        if (personalDetails != null) {
            jsonObject = new JSONObject();
            jsonObject.put("BloodGroup", personalDetails.getBloodGroup());
            jsonObject.put("Caste", personalDetails.getCaste());
            jsonObject.put("CorrespondenceAddress", personalDetails.getCorrespondenceAddress());
            jsonObject.put("DateOfBirth", personalDetails.getDateOfBirth());
            jsonObject.put("FirstName", personalDetails.getFirstName());
            jsonObject.put("Gender", personalDetails.getGender());
            jsonObject.put("IdentificationMarks", personalDetails.getIdentificationMarks());
            jsonObject.put("LastName", personalDetails.getLastName());
            jsonObject.put("MiddleName", personalDetails.getMiddleName());
            jsonObject.put("MobileNumber", personalDetails.getMobileNumber());
            jsonObject.put("MotherTongue", personalDetails.getMotherTongue());
            jsonObject.put("Nationality", personalDetails.getNationality());
            jsonObject.put("PermanentAddress", personalDetails.getPermanentAddress());
            jsonObject.put("Religion", personalDetails.getReligion());
        }
        return jsonObject;
    }

    /**
     * Gets the student.
     *
     * @param studentData the student data
     * @return the student
     */
    public static StudentDto create(JSONObject studentData) {
        StudentDto student = null;
        if (studentData != null) {
            student = new StudentDto();
            JSONObject personalDetailJsonObject = studentData.getJSONObject("PersonalDetails");
            JSONArray familyMembersDetailObject = studentData.getJSONArray("FamilyMemberDetails");
            JSONObject admissionDataJsonObject = studentData.getJSONObject("AdmissionDetails");
            JSONArray documentDetailsJsonArray = studentData.getJSONArray("DocumentDetails");

            student.setImageName(StringUtil.getValue(studentData.getString("ImageName")));
            student.setStudentId(Integer.parseInt(StringUtil.getValue(studentData.getString("StudentId"))));
            // Admission Details
            student.setAdmissionNumber(admissionDataJsonObject.getString("AdmissionNumber"));
            student.setRegisteredClassDto(getRegisteredClassDetails(admissionDataJsonObject));
            AdmissionStatus admissionStatus = new AdmissionStatus();
            admissionStatus.setStatusId(admissionDataJsonObject.getInt("AdmissionStatusId"));
            student.setAdmissionStatus(admissionStatus);
            student.setDateOfJoining(admissionDataJsonObject.getString("DateOfJoining"));
            student.setRemarks(admissionDataJsonObject.getString("Remarks"));
            student.setVerified(ConversionUtil.toBoolean(studentData.getString("Verified")));
            student.setPersonalDetails(getPersonalDetails(personalDetailJsonObject));
            student.setFamilyMembers(StudentFamilyDataAssembler.create(familyMembersDetailObject));
            student.setDocumentsSubmitted(StudentDocumentDataAssembler.create(documentDetailsJsonArray));
        }
        return student;
    }

    /**
     * Gets the registered class details.
     *
     * @param admissionDataJsonObject the admission data json object
     * @return the registered class details
     */
    private static RegisteredClassDto getRegisteredClassDetails(
            JSONObject admissionDataJsonObject) {
        RegisteredClassDto registeredClassDto = null;
        if (admissionDataJsonObject != null) {
            registeredClassDto = new RegisteredClassDto();
            registeredClassDto.setClassId(Integer.parseInt(admissionDataJsonObject.getString("RegisteredClassId")));
        }
        return registeredClassDto;
    }

    /**
     * Gets the personal details.
     *
     * @param personalDetailJsonObject the personal detail json object
     * @return the personal details
     */
    private static PersonalDetailsDto getPersonalDetails(
            JSONObject personalDetailJsonObject) {
        PersonalDetailsDto personalDetailsDto = null;
        if (personalDetailJsonObject != null) {
            personalDetailsDto = new PersonalDetailsDto();
            personalDetailsDto.setFirstName(personalDetailJsonObject.getString("FirstName"));
            personalDetailsDto.setMiddleName(personalDetailJsonObject.getString("MiddleName"));
            personalDetailsDto.setLastName(personalDetailJsonObject.getString("LastName"));
            personalDetailsDto.setGender(personalDetailJsonObject.getString("Gender"));
            personalDetailsDto.setDateOfBirth(personalDetailJsonObject.getString("DateOfBirth"));
            personalDetailsDto.setReligion(personalDetailJsonObject.getString("Religion"));
            personalDetailsDto.setCaste(personalDetailJsonObject.getString("Caste"));
            personalDetailsDto.setNationality(personalDetailJsonObject.getString("Nationality"));
            personalDetailsDto.setMotherTongue(personalDetailJsonObject.getString("MotherTongue"));
            personalDetailsDto.setMobileNumber(personalDetailJsonObject.getString("MobileNumber"));
            personalDetailsDto.setBloodGroup(personalDetailJsonObject.getString("BloodGroup"));
            personalDetailsDto.setCorrespondenceAddress(personalDetailJsonObject.getString("CorrespondenceAddress"));
            personalDetailsDto.setPermanentAddress(personalDetailJsonObject.getString("PermanentAddress"));
            personalDetailsDto.setIdentificationMarks(personalDetailJsonObject.getString("IdentificationMarks"));
        }
        return personalDetailsDto;
    }

    /**
     * Creates the student search criteria dto.
     *
     * @param studentSearchCriteria the student search criteria
     * @return the student search criteria dto
     */
    public static StudentSearchCriteriaDto createStudentSearchCriteriaDto(JSONObject studentSearchCriteria) {
        StudentSearchCriteriaDto studentSearchCriteriaDto = null;
        if (studentSearchCriteria != null) {
            studentSearchCriteriaDto = new StudentSearchCriteriaDto();

            String searchType = studentSearchCriteria.getString("SearchType");
            studentSearchCriteriaDto.setBranchId(JsonUtil.getIntValue(studentSearchCriteria, "Branch"));
            studentSearchCriteriaDto.setDivisionId(JsonUtil.getIntValue(studentSearchCriteria, "Division"));
            studentSearchCriteriaDto.setSchoolId(JsonUtil.getIntValue(studentSearchCriteria, "School"));
            studentSearchCriteriaDto.setClassId(JsonUtil.getIntValue(studentSearchCriteria, "Class"));
            studentSearchCriteriaDto.setMediumId(JsonUtil.getIntValue(studentSearchCriteria, "Medium"));
            studentSearchCriteriaDto.setSectionId(JsonUtil.getIntValue(studentSearchCriteria, "Section"));
            studentSearchCriteriaDto.setAdmissionNumber(JsonUtil.getStringValue(studentSearchCriteria, "AdmissionNumber"));
            studentSearchCriteriaDto.setStudentName(JsonUtil.getStringValue(studentSearchCriteria, "StudentName"));

            if (searchType.equalsIgnoreCase("ADVANCED")) {
                studentSearchCriteriaDto.setGender(JsonUtil.getStringValue(studentSearchCriteria, "Gender"));
                studentSearchCriteriaDto.setBloodGroup(JsonUtil.getStringValue(studentSearchCriteria, "BloodGroup"));
                studentSearchCriteriaDto.setDateOfBirthMin(JsonUtil.getStringValue(studentSearchCriteria, "DateOfBirthMin"));
                studentSearchCriteriaDto.setDateOfBirthMax(JsonUtil.getStringValue(studentSearchCriteria, "DateOfBirthMax"));
                studentSearchCriteriaDto.setDateOfJoiningMin(JsonUtil.getStringValue(studentSearchCriteria, "DateOfJoiningMin"));
                studentSearchCriteriaDto.setDateOfJoiningMax(JsonUtil.getStringValue(studentSearchCriteria, "DateOfJoiningMax"));
            }
        }
        return studentSearchCriteriaDto;
    }

    /**
     * Creates the student.
     *
     * @param resultSet the result set
     * @return the student dto
     * @throws SQLException the sQL exception
     */
    public static StudentDto create(ResultSet resultSet) throws SQLException {
        StudentDto student = new StudentDto();
        PersonalDetailsDto personalDetails = new PersonalDetailsDto();
        RegisteredClassDto registeredClassDto = new RegisteredClassDto();

        student.setStudentId(resultSet.getInt("STUDENT_ID"));
        student.setAdmissionNumber(resultSet.getString("ADMISSION_NUMBER"));
        student.setDateOfJoining(ConversionUtil.toApplicationDateFromStorageDate(
                resultSet.getString("DATE_OF_JOINING")));
        student.setRemarks(resultSet.getString("REMARKS"));
        student.setAdmissionStatus(AdmissionStatusDataAssembler.create(resultSet, true));
        student.setVerified(ConversionUtil.toBoolean(resultSet.getString("VERIFIED")));

        personalDetails.setFirstName(resultSet.getString("FIRST_NAME"));
        personalDetails.setMiddleName(resultSet.getString("MIDDLE_NAME"));
        personalDetails.setLastName(resultSet.getString("LAST_NAME"));
        personalDetails.setGender(resultSet.getString("GENDER"));
        personalDetails.setDateOfBirth(ConversionUtil.toApplicationDateFromStorageDate(
                resultSet.getString("DATE_OF_BIRTH")));
        personalDetails.setReligion(resultSet.getString("RELIGION"));
        personalDetails.setCaste(resultSet.getString("CASTE"));
        personalDetails.setNationality(resultSet.getString("NATIONALITY"));
        personalDetails.setMotherTongue(resultSet.getString("MOTHER_TONGUE"));
        personalDetails.setPermanentAddress(resultSet.getString("PERMANENT_ADDRESS"));
        personalDetails.setCorrespondenceAddress(resultSet.getString("CORRESPONDENCE_ADDRESS"));
        personalDetails.setMobileNumber(resultSet.getString("MOBILE_NUMBER"));
        personalDetails.setIdentificationMarks(resultSet.getString("IDENTIFICATION_MARKS"));
        personalDetails.setBloodGroup(resultSet.getString("BLOOD_GROUP"));
        student.setPersonalDetails(personalDetails);

        registeredClassDto.setClassId(resultSet.getInt("CLASS_CLASS_ID"));
        registeredClassDto.setClassDto(ClassDataAssembler.create(resultSet, true));
        registeredClassDto.setMedium(MediumDataAssembler.create(resultSet, true));
        registeredClassDto.setSection(SectionDataAssembler.create(resultSet, true));
        registeredClassDto.setSchool(SchoolDataAssembler.create(resultSet, true));
        student.setRegisteredClassDto(registeredClassDto);
        return student;
    }

    /**
     * Creates the.
     * 
     * @param reportCriteriaValues the report criteria values
     * @return the student search criteria dto
     */
    public static StudentSearchCriteriaDto create(
            Map<ReportCriteriaToken, String> reportCriteriaValues) {
        StudentSearchCriteriaDto studentSearchCriteria = new StudentSearchCriteriaDto();
        if (reportCriteriaValues != null && !reportCriteriaValues.isEmpty()) {
            studentSearchCriteria = new StudentSearchCriteriaDto();
            // admission number
            studentSearchCriteria.setAdmissionNumber(ReportDataAssembler.getString(
                    reportCriteriaValues, ReportCriteriaTokenConstants.ADMISSION_NUMBER));
            // blood group
            studentSearchCriteria.setBloodGroup(ReportDataAssembler.getString(
                    reportCriteriaValues, ReportCriteriaTokenConstants.BLOOD_GROUP));
            studentSearchCriteria.setBranchId(ReportDataAssembler.getInt(
                    reportCriteriaValues, ReportCriteriaTokenConstants.BRANCH));
            studentSearchCriteria.setClassId(ReportDataAssembler.getInt(
                    reportCriteriaValues, ReportCriteriaTokenConstants.CLASS));
            // date of birth
            String[] dateOfBirthValues = ReportDataAssembler.getDates(reportCriteriaValues, ReportCriteriaTokenConstants.DATE_OF_BIRTH);
            studentSearchCriteria.setDateOfBirthMin(dateOfBirthValues[0]);
            studentSearchCriteria.setDateOfBirthMax(dateOfBirthValues[1]);
            // date of joining
            String[] dateOfJoiningValues = ReportDataAssembler.getDates(reportCriteriaValues, ReportCriteriaTokenConstants.DATE_OF_JOINING);
            studentSearchCriteria.setDateOfJoiningMin(dateOfJoiningValues[0]);
            studentSearchCriteria.setDateOfJoiningMax(dateOfJoiningValues[1]);
            studentSearchCriteria.setDivisionId(ReportDataAssembler.getInt(
                    reportCriteriaValues, ReportCriteriaTokenConstants.DIVISION));
            studentSearchCriteria.setGender(ReportDataAssembler.getString(
                    reportCriteriaValues, ReportCriteriaTokenConstants.GENDER));
            studentSearchCriteria.setMediumId(ReportDataAssembler.getInt(
                    reportCriteriaValues, ReportCriteriaTokenConstants.MEDIUM));
            studentSearchCriteria.setReligion(ReportDataAssembler.getString(
                    reportCriteriaValues, ReportCriteriaTokenConstants.RELIGION));
            studentSearchCriteria.setSchoolId(ReportDataAssembler.getInt(
                    reportCriteriaValues, ReportCriteriaTokenConstants.SCHOOL));
            studentSearchCriteria.setSectionId(ReportDataAssembler.getInt(
                    reportCriteriaValues, ReportCriteriaTokenConstants.SECTION));
            studentSearchCriteria.setStudentName(ReportDataAssembler.getString(
                    reportCriteriaValues, ReportCriteriaTokenConstants.STUDENT_NAME));
            studentSearchCriteria.setVerifiedStatus(ReportDataAssembler.getString(
                    reportCriteriaValues, ReportCriteriaTokenConstants.VERIFIED_STATUS));
        }
        return studentSearchCriteria;
    }

}
