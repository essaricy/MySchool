package com.myschool.web.student.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.myschool.application.dto.ImageAccessDto;
import com.myschool.branch.dto.BranchDto;
import com.myschool.branch.dto.DivisionDto;
import com.myschool.clazz.dto.ClassDto;
import com.myschool.clazz.dto.MediumDto;
import com.myschool.clazz.dto.RegisteredClassDto;
import com.myschool.clazz.dto.SectionDto;
import com.myschool.common.constants.RecordStatus;
import com.myschool.common.dto.PersonalDetailsDto;
import com.myschool.common.dto.ResultDto;
import com.myschool.common.exception.ServiceException;
import com.myschool.common.util.StringUtil;
import com.myschool.school.dto.SchoolDto;
import com.myschool.student.assembler.StudentDataAssembler;
import com.myschool.student.dto.StudentDto;
import com.myschool.student.dto.StudentSearchCriteriaDto;
import com.myschool.student.service.StudentService;
import com.myschool.web.application.constants.WebConstants;
import com.myschool.web.framework.controller.ViewDelegationController;
import com.myschool.web.framework.util.HttpUtil;
import com.myschool.web.student.constants.StudentViewNames;

/**
 * The Class StudentController.
 */
@Controller
@RequestMapping("student")
public class StudentController {

    /** The student service. */
    @Autowired
    private StudentService studentService;

    /**
     * Launch verified students search.
     * 
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value = "launchVerifiedStudentsSearch")
    public ModelAndView launchVerifiedStudentsSearch(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(WebConstants.RECORD_STATUS, RecordStatus.VERIFIED.toString());
        map.put("TITLE", "Search Students");
        return ViewDelegationController.delegateWholePageView(
                request, StudentViewNames.SEARCH_STUDENT, map);
    }

    /**
     * Launch unverified students search.
     * 
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value = "launchUnverifiedStudentsSearch")
    public ModelAndView launchUnverifiedStudentsSearch(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(WebConstants.RECORD_STATUS, RecordStatus.UNVERIFIED.toString());
        map.put("TITLE", "Search Students (Portal)");
        return ViewDelegationController.delegateWholePageView(
                request, StudentViewNames.SEARCH_STUDENT, map);
    }

    /**
     * Launch.
     * 
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value="launch")
    public ModelAndView launch(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        String admissionNumber = request.getParameter("AdmissionNumber");
        if (!StringUtil.isNullOrBlank(admissionNumber)) {
            map.put("Student", studentService.get(admissionNumber));
        }
        map.put(WebConstants.RECORD_STATUS, request.getParameter(WebConstants.RECORD_STATUS));
        return ViewDelegationController.delegateWholePageView(
                request, StudentViewNames.STUDENT_REGISTRATION, map);
    }

    /**
     * Search verified students.
     * 
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
   @RequestMapping(value="searchVerifiedStudents")
   public ModelAndView searchVerifiedStudents(HttpServletRequest request,
           HttpServletResponse response) throws Exception {
       searchStudents(request, response, RecordStatus.VERIFIED);
       return null;
   }

   /**
     * Search unverified students.
     * 
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
   @RequestMapping(value="searchUnverifiedStudents")
   public ModelAndView searchUnverifiedStudents(HttpServletRequest request,
           HttpServletResponse response) throws Exception {
       searchStudents(request, response, RecordStatus.UNVERIFIED);
       return null;
   }

    /**
     * View student.
     * 
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value="viewStudent")
    public ModelAndView viewStudent(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        String admissionNumber = request.getParameter("admissionNumber");
        if (!StringUtil.isNullOrBlank(admissionNumber)) {
            map.put("Student", studentService.get(admissionNumber));
        }
        map.put(WebConstants.VIEW_ONLY, Boolean.TRUE);
        return ViewDelegationController.delegateModelPageView(
                request, StudentViewNames.VIEW_STUDENT, map);
    }

    /**
     * Gets the last admission number.
     * 
     * @param request the request
     * @param response the response
     * @return the last admission number
     * @throws Exception the exception
     */
    @RequestMapping(value="getLastAdmissionNumber")
    public ModelAndView getLastAdmissionNumber(HttpServletRequest request,
            HttpServletResponse response) throws Exception  {

        String nextAdmissionNumber = null;
        try {
            nextAdmissionNumber = studentService.getLastAdmissionNumber();
        } finally {
            JSONObject result = new JSONObject();
            if (nextAdmissionNumber == null) {
                result.put("successful", Boolean.FALSE);
            } else {
                result.put("successful", Boolean.TRUE);
            }
            result.put("value", nextAdmissionNumber);
            HttpUtil.writeJson(response, result);
        }
        return null;
    }

    /**
     * Gets the next student.
     *
     * @param request the request
     * @param response the response
     * @return the next student
     * @throws Exception the exception
     */
    @RequestMapping(value="getNextStudent")
    public ModelAndView getNextStudent(HttpServletRequest request,
            HttpServletResponse response) throws Exception  {
    	StudentDto nextStudent = null;
    	Map<String, Object> map = new HashMap<String, Object>();
        String admissionNumber = request.getParameter("AdmissionNumber");
        String type = request.getParameter("Type");
        RecordStatus recordStatus = RecordStatus.get(type);
        if (recordStatus != null) {
        	nextStudent = studentService.getNext(admissionNumber, recordStatus);
        }
        if (nextStudent == null) {
        	nextStudent = studentService.get(admissionNumber);
        }
        map.put("Student", nextStudent);
        map.put(WebConstants.RECORD_STATUS, request.getParameter(WebConstants.RECORD_STATUS));
        return ViewDelegationController.delegateWholePageView(
                request, StudentViewNames.STUDENT_REGISTRATION, map);
    }

    /**
     * Gets the previous student.
     *
     * @param request the request
     * @param response the response
     * @return the previous student
     * @throws Exception the exception
     */
    @RequestMapping(value="getPreviousStudent")
    public ModelAndView getPreviousStudent(HttpServletRequest request,
            HttpServletResponse response) throws Exception  {
    	StudentDto nextStudent = null;
    	Map<String, Object> map = new HashMap<String, Object>();
        String admissionNumber = request.getParameter("AdmissionNumber");
        String type = request.getParameter("Type");
        RecordStatus recordStatus = RecordStatus.get(type);
        if (recordStatus != null) {
        	nextStudent = studentService.getPrevious(admissionNumber, recordStatus);
        }
        if (nextStudent == null) {
        	nextStudent = studentService.get(admissionNumber);
        }
        map.put("Student", nextStudent);
        map.put(WebConstants.RECORD_STATUS, request.getParameter(WebConstants.RECORD_STATUS));
        return ViewDelegationController.delegateWholePageView(
                request, StudentViewNames.STUDENT_REGISTRATION, map);
    }

    /**
     * Register student.
     * 
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value="registerStudent")
    public ModelAndView registerStudent(HttpServletRequest request,
            HttpServletResponse response) throws Exception  {

        ResultDto result = new ResultDto();
        StudentDto student = null;

        try {
            String studentDataValue = request.getParameter("StudentData");
            if (!StringUtil.isNullOrBlank(studentDataValue)) {
                JSONObject studentData = new JSONObject(studentDataValue);
                student = StudentDataAssembler.create(studentData);
                System.out.println("student=" + student);
                if (student != null) {
                    int studentId = student.getStudentId();
                    String admissionNumber = student.getAdmissionNumber();
                    if (studentId == 0) {
                        // Create a new Student
                        studentService.create(student);
                        result.setStatusMessage("Student (" + admissionNumber + ") has been created successfully.");
                    } else {
                        // Update existing Student
                        studentService.update(studentId, student);
                        result.setStatusMessage("Student (" + admissionNumber + ") has been updated successfully.");
                    }
                    StudentDto studentDto = studentService.get(admissionNumber);
                    System.out.println("registered student=" + studentDto);
                    if (studentDto != null) {
                        result.setSuccessful(true);
                        result.setReferenceNumber(String.valueOf(studentDto.getStudentId()));
                        result.setReference(studentDto);
                    }
                }
            }
        } catch (ServiceException serviceException) {
            result.setStatusMessage(serviceException.getMessage());
        } finally {
            HttpUtil.writeAsJson(response, result);
        }
        return null;
    }

    /**
     * Search students.
     *
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    private ModelAndView searchStudents(HttpServletRequest request,
            HttpServletResponse response, RecordStatus recordStatus) throws Exception {

        JSONArray data = new JSONArray();
        try {
            String studentSearchCriteriaValue = request.getParameter("StudentSearchCriteria");

            if (!StringUtil.isNullOrBlank(studentSearchCriteriaValue)) {
                JSONObject studentSearchCriteria = new JSONObject(studentSearchCriteriaValue);
                StudentSearchCriteriaDto studentSearchCriteriaDto = StudentDataAssembler.createStudentSearchCriteriaDto(studentSearchCriteria);
                studentSearchCriteriaDto.setRecordStatus(recordStatus);
                List<StudentDto> students = studentService.getAll(studentSearchCriteriaDto);
                if (students != null && !students.isEmpty()) {
                    for (StudentDto student : students) {
                        JSONArray row = new JSONArray();
                        row.put(student.getStudentId());
                        row.put(student.getAdmissionNumber());

                        PersonalDetailsDto personalDetails = student.getPersonalDetails();
                        RegisteredClassDto registeredClassDto = student.getRegisteredClassDto();
                        ClassDto classDto = registeredClassDto.getClassDto();
                        MediumDto medium = registeredClassDto.getMedium();
                        SchoolDto school = registeredClassDto.getSchool();
                        SectionDto section = registeredClassDto.getSection();
                        BranchDto branch = school.getBranch();
                        DivisionDto division = school.getDivision();

                        row.put(personalDetails.getFirstName());
                        row.put(personalDetails.getMiddleName());
                        row.put(personalDetails.getLastName());
                        row.put(personalDetails.getGender());
                        row.put(personalDetails.getDateOfBirth());
                        row.put(personalDetails.getBloodGroup());
                        row.put(personalDetails.getNationality());
                        row.put(personalDetails.getReligion()); // 10
                        row.put(personalDetails.getCaste());
                        row.put(personalDetails.getMotherTongue());
                        row.put(personalDetails.getIdentificationMarks());
                        row.put(personalDetails.getMobileNumber());
                        row.put(personalDetails.getCorrespondenceAddress()); //15
                        row.put(personalDetails.getPermanentAddress());

                        row.put(registeredClassDto.getClassId());
                        row.put(branch.getBranchId());
                        row.put(branch.getBranchCode());
                        row.put(division.getDivisionId()); // 20
                        row.put(division.getDivisionCode());
                        row.put(school.getSchoolId());
                        row.put(school.getSchoolName());
                        row.put(classDto.getClassId());
                        row.put(classDto.getClassName()); // 25
                        row.put(medium.getMediumId());
                        row.put(medium.getDescription());
                        row.put(section.getSectionId());
                        row.put(section.getSectionName());

                        row.put(student.getDateOfJoining());  // 30

                        ImageAccessDto imageAccess = student.getImageAccess();
                        if (imageAccess == null) {
                            row.put("");
                            row.put("");
                            row.put("");
                        } else {
                            row.put(imageAccess.getDirectLink());
                            row.put(imageAccess.getPassportLink());
                            row.put(imageAccess.getThumbnailLink());
                        }
                        data.put(row);
                    }
                }
            }
        } finally {
            HttpUtil.wrapAndWriteJson(response, "StudentsData", data);
        }
        return null;
    }

}
