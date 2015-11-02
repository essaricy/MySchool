package com.myschool.web.attendance.controller;

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

import com.myschool.academic.dto.AcademicDto;
import com.myschool.attendance.assembler.AttendanceDataAssembler;
import com.myschool.attendance.assembler.AttendanceProfileDataAssembler;
import com.myschool.attendance.constants.AttendanceConstant;
import com.myschool.attendance.dto.AttendanceCodeDto;
import com.myschool.attendance.dto.AttendanceProfileDto;
import com.myschool.attendance.service.AttendanceProfileService;
import com.myschool.attendance.service.AttendanceService;
import com.myschool.common.dto.ResultDto;
import com.myschool.common.util.StringUtil;
import com.myschool.web.attendance.constants.AttendanceViewNames;
import com.myschool.web.framework.controller.ViewDelegationController;
import com.myschool.web.framework.util.HttpUtil;

/**
 * The Class AttendanceController.
 */
@Controller
@RequestMapping(value="attendance")
public class AttendanceController {

    /** The attendance service. */
    @Autowired
    private AttendanceService attendanceService;

    /** The attendance profile service. */
    @Autowired
    private AttendanceProfileService attendanceProfileService;

    /**
     * List.
     * 
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value="list")
    public ModelAndView list(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        return ViewDelegationController.delegateWholePageView(request, AttendanceViewNames.VIEW_ATTENDANCE_PROFILES);
    }

    /**
     * Json list.
     * 
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value="jsonList")
    public ModelAndView jsonList(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        System.out.println("jsonList()");
        JSONArray data = new JSONArray();
        try {
            List<AttendanceProfileDto> attendanceProfiles = attendanceProfileService.getAll();
            if (attendanceProfiles != null) {
                for (AttendanceProfileDto attendanceProfile : attendanceProfiles) {
                    JSONArray row = new JSONArray();
                    row.put(attendanceProfile.getProfileId());
                    row.put(attendanceProfile.getProfileName());
                    AcademicDto academic = attendanceProfile.getEffectiveAcademic();
                    if (academic == null) {
                        row.put("");
                    } else {
                        row.put(academic.getAcademicYearName());
                    }
                    data.put(row);
                }
            }
        } finally {
            HttpUtil.wrapAndWriteAsAAData(response, data);
        }
        return null;
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
        String attendanceProfileIdVal = request.getParameter(AttendanceConstant.ATTENDANCE_PROFILE_ID);
        System.out.println("launch(" + attendanceProfileIdVal + ")");
        if (!StringUtil.isNullOrBlank(attendanceProfileIdVal)) {
            AttendanceProfileDto attendanceProfile = attendanceProfileService.get(Integer.parseInt(attendanceProfileIdVal));
            map.put(AttendanceConstant.ATTENDANCE_PROFILE, attendanceProfile);
        }
        return ViewDelegationController.delegateModelPageView(request, AttendanceViewNames.MAINTAIN_ATTENDANCE_PROFILE, map);
    }

    /**
     * Json list attendance codes.
     * 
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value="jsonListAttendanceCodes")
    public ModelAndView jsonListAttendanceCodes(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        JSONObject jsonResponse = new JSONObject();
        try {
            String type = request.getParameter("Type");
            System.out.println("jsonListAttendanceCodes type=" + type);
            List<AttendanceCodeDto> attendanceCodes = attendanceService.getAttendanceCodes(type);
            JSONArray jsonArray = AttendanceDataAssembler.create(attendanceCodes);
            jsonResponse.put(AttendanceConstant.ATTENDANCE_CODES, jsonArray);
        } finally {
            HttpUtil.writeJson(response, jsonResponse);
        }
        return null;
    }

    /**
     * Json get attendance profile.
     * 
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value="jsonGetAttendanceProfile")
    public ModelAndView jsonGetAttendanceProfile(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        JSONObject jsonResponse = new JSONObject();
        AttendanceProfileDto attendanceProfile = null;
        try {
            String attendanceProfileIdVal = request.getParameter(AttendanceConstant.ATTENDANCE_PROFILE_ID);
            String academicYearNameVal = request.getParameter(AttendanceConstant.ACADEMIC_YEAR_NAME);
            System.out.println("jsonGetAttendanceProfile(" + attendanceProfileIdVal + ", " + academicYearNameVal + ")");
            if (!StringUtil.isNullOrBlank(attendanceProfileIdVal)) {
                attendanceProfile = attendanceProfileService.getInDetail(Integer.parseInt(attendanceProfileIdVal));
            } else if (!StringUtil.isNullOrBlank(academicYearNameVal)) {
                attendanceProfile = attendanceProfileService.getBlank(academicYearNameVal);
            } else {
                attendanceProfile = attendanceProfileService.getBlank();
            }
            jsonResponse.put(AttendanceConstant.ATTENDANCE_PROFILE, AttendanceProfileDataAssembler.create(attendanceProfile));
        } finally {
            HttpUtil.writeJson(response, jsonResponse);
        }
        return null;
    }

    /**
     * Do create.
     * 
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value="doCreate")
    public ModelAndView doCreate(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        ResultDto result = new ResultDto();
        try {
            String attendanceProfileData = request.getParameter(AttendanceConstant.ATTENDANCE_PROFILE);
            System.out.println("doCreate() " + attendanceProfileData);
            if (!StringUtil.isNullOrBlank(attendanceProfileData)) {
                AttendanceProfileDto attendanceProfile = AttendanceProfileDataAssembler.create(attendanceProfileData);
                result.setSuccessful(attendanceProfileService.create(attendanceProfile));
                result.setStatusMessage("Attendance Profile has been created succesfully.");
            }
        } catch (Exception exception) {
            result.setStatusMessage(exception.getMessage());
        } finally {
            HttpUtil.writeAsJson(response, result);
        }
        return null;
    }

    /**
     * Do update.
     * 
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value="doUpdate")
    public ModelAndView doUpdate(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        ResultDto result = new ResultDto();
        try {
            String attendanceProfileData = request.getParameter(AttendanceConstant.ATTENDANCE_PROFILE);
            System.out.println("doUpdate() " + attendanceProfileData);
            if (!StringUtil.isNullOrBlank(attendanceProfileData)) {
                AttendanceProfileDto attendanceProfile = AttendanceProfileDataAssembler.create(attendanceProfileData);
                if (attendanceProfile != null) {
                    int profileId = attendanceProfile.getProfileId();
                    result.setSuccessful(attendanceProfileService.update(profileId, attendanceProfile));
                    result.setStatusMessage("Attendance Profile has been updated succesfully.");
                }
            }
        } catch (Exception exception) {
            result.setStatusMessage(exception.getMessage());
        } finally {
            HttpUtil.writeAsJson(response, result);
        }
        return null;
    }

    /**
     * Do delete.
     * 
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value="doDelete")
    public ModelAndView doDelete(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        ResultDto result = new ResultDto();
        try {
            String attendanceProfileId = request.getParameter(AttendanceConstant.ATTENDANCE_PROFILE_ID);
            System.out.println("doDelete() " + attendanceProfileId);
            if (!StringUtil.isNullOrBlank(attendanceProfileId)) {
                result.setSuccessful(attendanceProfileService.delete(Integer.parseInt(attendanceProfileId)));
                result.setStatusMessage("Attendance Profile has been deleted succesfully.");
            }
        } catch (Exception exception) {
            result.setStatusMessage(exception.getMessage());
        } finally {
            HttpUtil.writeAsJson(response, result);
        }
        return null;
    }

    /**
     * Json save attendance profile.
     * 
     * @param request the request
     * @param response the response
     * @return the model and view
     *//*
    @RequestMapping(value="jsonSaveAttendanceProfile")
    public ModelAndView jsonSaveAttendanceProfile(HttpServletRequest request,
            HttpServletResponse response) {
        ResultDto result = new ResultDto();
        AttendanceProfileDto attendanceProfile = null;

        try {
            System.out.println(">>>>>>>>>>>>> jsonSaveAttendanceProfile()");
            String attendanceProfileDataValue = request.getParameter("AttendanceProfileData");
            if (!StringUtil.isNullOrBlank(attendanceProfileDataValue)) {
                JSONObject attendanceProfileData = new JSONObject(attendanceProfileDataValue);
                attendanceProfile = AttendanceDataAssembler.create(attendanceProfileData);
                AttendanceProfileDataAssembler.debugAttendanceProfile(attendanceProfile);
                if (attendanceProfile != null) {
                    int attendanceProfileId = attendanceProfile.getProfileId();
                    String profileName = attendanceProfile.getProfileName();
                    if (attendanceProfileId == 0) {
                        // Create a new attendance profile
                        attendanceProfileService.create(attendanceProfile);
                        result.setSuccessful(true);
                        result.setStatusMessage("Attendance Profile (" + profileName + ") has been created successfully.");
                    } else {
                        // Update existing attendance profile
                        attendanceProfileService.update(attendanceProfileId, attendanceProfile);
                        result.setSuccessful(true);
                        result.setStatusMessage("Attendance Profile (" + profileName + ") has been updated successfully.");
                    }
                }
            }
        } catch (ServiceException serviceException) {
            result.setStatusMessage(serviceException.getMessage());
        } catch (Exception exception) {
            result.setStatusMessage(exception.getMessage());
        } finally {
            try {
                HttpUtil.writeAsJson(response, result);
            } catch (Exception exception) {
            }
        }
        return null;
    }*/

    /**
     * Student attendance.
     *
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     *//*
    @RequestMapping(value="studentAttendance")
    public ModelAndView studentAttendance(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        return ViewDelegationController.delegateWholePageView(request, AttendanceViewNames.VIEW_STUDENT_ATTENDANCE);
    }

    *//**
     * Launch student attendance entry sheet.
     *
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     *//*
    @RequestMapping(value="launchStudentAttendanceEntrySheet")
    public ModelAndView launchStudentAttendanceEntrySheet(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("classId", request.getParameter("classId"));
        return ViewDelegationController.delegateModelPageView(request, AttendanceViewNames.MAINTAIN_STUDENT_ATTENDANCE, map);
    }

    *//**
     * Gets the current.
     *
     * @param request the request
     * @param response the response
     * @return the current
     * @throws Exception the exception
     *//*
    @RequestMapping(value="getCurrent")
    public ModelAndView getCurrent(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("currentDate", ConversionUtil.toApplicationDate(new Date().getTime()));
        response.setContentType(MimeTypes.APPLICATION_JSON);

        return null;
    }

    *//**
     * Gets the reference attendance.
     *
     * @param request the request
     * @param response the response
     * @return the reference attendance
     * @throws Exception the exception
     *//*
    @RequestMapping(value="getReferenceAttendance")
    public ModelAndView getReferenceAttendance(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        JSONObject jsonResponse = new JSONObject();

        String outputData = null;
        AttendanceDto attendance = null;

        String classId = request.getParameter("classId");
        String date = request.getParameter("date");
        String month = request.getParameter("month");
        String year = request.getParameter("year");
        String output = request.getParameter("output");


        if (!StringUtil.isNullOrBlank(classId)) {
            AttendanceCriteria attendanceCriteria = AttendanceAssembler.createAttendanceCriteria(classId, date, month, year);

            attendance = attendanceService.getReferenceAttendance(attendanceCriteria);
            OutputFormat outputFormat = OutputFormat.getOutputFormat(output);

            if (outputFormat == OutputFormat.JSON) {
                Object json = AttendanceAssembler.getJson(attendance);
                jsonResponse.put("attendanceData", json);
                response.setContentType(MimeTypes.APPLICATION_JSON);
                outputData = jsonResponse.toString();
            }
        }
        return null;
    }

    *//**
     * Gets the class attendance.
     *
     * @param request the request
     * @param response the response
     * @return the class attendance
     * @throws Exception the exception
     *//*
    @RequestMapping(value="getClassAttendance")
    public ModelAndView getClassAttendance(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        JSONObject jsonResponse = new JSONObject();
        JSONArray jsonStudentAttendances = null;

        String outputData = null;

        String classId = request.getParameter("classId");
        String date = request.getParameter("date");
        String month = request.getParameter("month");
        String year = request.getParameter("year");
        String output = request.getParameter("output");

        if (!StringUtil.isNullOrBlank(classId)) {
            AttendanceCriteria attendanceCriteria = AttendanceAssembler.createAttendanceCriteria(classId, date, month, year);
            List<StudentAttendanceDto> studentAttendances = attendanceService.getStudentAttendances(Integer.parseInt(classId), attendanceCriteria);
            if (studentAttendances != null && !studentAttendances.isEmpty()) {
                jsonStudentAttendances = new JSONArray();
                for (StudentAttendanceDto studentAttendance : studentAttendances) {
                    JSONObject jsonStudentAttendance = AttendanceAssembler.getJson(studentAttendance);
                    jsonStudentAttendances.put(jsonStudentAttendance);
                }
            }
        }

        OutputFormat outputFormat = OutputFormat.getOutputFormat(output);
        if (outputFormat == OutputFormat.JSON) {
            jsonResponse.put("studentAttendances", jsonStudentAttendances);
            response.setContentType(MimeTypes.APPLICATION_JSON);
            outputData = jsonResponse.toString();
        }
        return null;
    }

    *//**
     * Update student attendance.
     *
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     *//*
    @RequestMapping(value="updateStudentAttendance")
    public ModelAndView updateStudentAttendance(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        ResultDto result = new ResultDto();

        try {
            JSONObject attendanceData = new JSONObject(request.getParameter("attendanceData"));

            ReferenceAttendanceDto referenceAttendance = AttendanceAssembler.getReferenceAttendance(attendanceData);
            List<StudentAttendanceDto> studentsAttendance = AttendanceAssembler.getStudentsAttendance(attendanceData);
            boolean updated = attendanceService.update(referenceAttendance, studentsAttendance);
            if (updated) {
                result.setSuccessful(ResultDto.SUCCESS);
                result.setStatusMessage("Students attendances have been updated successfully.");
            }
        } catch (ServiceException serviceException) {
            result.setStatusMessage(serviceException.getMessage());
        } finally {
            HttpUtil.writeAsJson(response, result);
        }
        return null;
    }*/

}
