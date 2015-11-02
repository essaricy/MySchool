package com.myschool.student.assembler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

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

            student.setImageName(StringUtil.getValue(JsonUtil.getString(studentData, "ImageName")));
            student.setStudentId(Integer.parseInt(StringUtil.getValue(JsonUtil.getString(studentData, "StudentId"))));
            student.setVerified(ConversionUtil.toBoolean(JsonUtil.getString(studentData, "Verified")));
            // Admission Details
            JSONObject admissionDataJsonObject = JsonUtil.getObject(studentData, "AdmissionDetails");
            if (admissionDataJsonObject != null) {
                student.setAdmissionNumber(JsonUtil.getString(admissionDataJsonObject, "AdmissionNumber"));
                student.setRegisteredClassDto(getRegisteredClassDetails(admissionDataJsonObject));
                AdmissionStatus admissionStatus = new AdmissionStatus();
                admissionStatus.setStatusId(JsonUtil.getInt(admissionDataJsonObject, "AdmissionStatusId"));
                student.setAdmissionStatus(admissionStatus);
                student.setDateOfJoining(JsonUtil.getString(admissionDataJsonObject, "DateOfJoining"));
                student.setRemarks(JsonUtil.getString(admissionDataJsonObject, "Remarks"));
            }
            student.setPersonalDetails(getPersonalDetails(JsonUtil.getObject(studentData, "PersonalDetails")));
            student.setFamilyMembers(StudentFamilyDataAssembler.create(JsonUtil.getArray(studentData, "FamilyMemberDetails")));
            student.setDocumentsSubmitted(StudentDocumentDataAssembler.create(JsonUtil.getArray(studentData, "DocumentDetails")));
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
            personalDetailsDto.setFirstName(JsonUtil.getString(personalDetailJsonObject, "FirstName"));
            personalDetailsDto.setMiddleName(JsonUtil.getString(personalDetailJsonObject, "MiddleName"));
            personalDetailsDto.setLastName(JsonUtil.getString(personalDetailJsonObject, "LastName"));
            personalDetailsDto.setGender(JsonUtil.getString(personalDetailJsonObject, "Gender"));
            personalDetailsDto.setDateOfBirth(JsonUtil.getString(personalDetailJsonObject, "DateOfBirth"));
            personalDetailsDto.setReligion(JsonUtil.getString(personalDetailJsonObject, "Religion"));
            personalDetailsDto.setCaste(JsonUtil.getString(personalDetailJsonObject, "Caste"));
            personalDetailsDto.setNationality(JsonUtil.getString(personalDetailJsonObject, "Nationality"));
            personalDetailsDto.setMotherTongue(JsonUtil.getString(personalDetailJsonObject, "MotherTongue"));
            personalDetailsDto.setMobileNumber(JsonUtil.getString(personalDetailJsonObject, "MobileNumber"));
            personalDetailsDto.setBloodGroup(JsonUtil.getString(personalDetailJsonObject, "BloodGroup"));
            personalDetailsDto.setCorrespondenceAddress(JsonUtil.getString(personalDetailJsonObject, "CorrespondenceAddress"));
            personalDetailsDto.setPermanentAddress(JsonUtil.getString(personalDetailJsonObject, "PermanentAddress"));
            personalDetailsDto.setIdentificationMarks(JsonUtil.getString(personalDetailJsonObject, "IdentificationMarks"));
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

            String searchType = JsonUtil.getString(studentSearchCriteria, "SearchType");
            studentSearchCriteriaDto.setBranchId(JsonUtil.getInt(studentSearchCriteria, "Branch"));
            studentSearchCriteriaDto.setDivisionId(JsonUtil.getInt(studentSearchCriteria, "Division"));
            studentSearchCriteriaDto.setSchoolId(JsonUtil.getInt(studentSearchCriteria, "School"));
            studentSearchCriteriaDto.setClassId(JsonUtil.getInt(studentSearchCriteria, "Class"));
            studentSearchCriteriaDto.setMediumId(JsonUtil.getInt(studentSearchCriteria, "Medium"));
            studentSearchCriteriaDto.setSectionId(JsonUtil.getInt(studentSearchCriteria, "Section"));
            studentSearchCriteriaDto.setAdmissionNumber(JsonUtil.getString(studentSearchCriteria, "AdmissionNumber"));
            studentSearchCriteriaDto.setStudentName(JsonUtil.getString(studentSearchCriteria, "StudentName"));

            if (searchType.equalsIgnoreCase("ADVANCED")) {
                studentSearchCriteriaDto.setGender(JsonUtil.getString(studentSearchCriteria, "Gender"));
                studentSearchCriteriaDto.setBloodGroup(JsonUtil.getString(studentSearchCriteria, "BloodGroup"));
                studentSearchCriteriaDto.setDateOfBirthMin(JsonUtil.getString(studentSearchCriteria, "DateOfBirthMin"));
                studentSearchCriteriaDto.setDateOfBirthMax(JsonUtil.getString(studentSearchCriteria, "DateOfBirthMax"));
                studentSearchCriteriaDto.setDateOfJoiningMin(JsonUtil.getString(studentSearchCriteria, "DateOfJoiningMin"));
                studentSearchCriteriaDto.setDateOfJoiningMax(JsonUtil.getString(studentSearchCriteria, "DateOfJoiningMax"));
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
