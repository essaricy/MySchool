package com.myschool.web.student.controller;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.myschool.academic.assembler.HolidayDataAssembler;
import com.myschool.academic.dto.HolidayDto;
import com.myschool.application.service.HolidayService;
import com.myschool.clazz.dto.RegisteredClassDto;
import com.myschool.common.dto.PersonalDetailsDto;
import com.myschool.common.util.StringUtil;
import com.myschool.common.validator.DataTypeValidator;
import com.myschool.exam.assembler.ExamDataAssembler;
import com.myschool.exam.assembler.StudentExamDataAssembler;
import com.myschool.exam.dto.ExamDto;
import com.myschool.exam.dto.StudentExamsSummaryDto;
import com.myschool.exam.dto.StudentInExamDto;
import com.myschool.exam.service.ExamGradeService;
import com.myschool.exam.service.ExamService;
import com.myschool.exam.service.StudentExamService;
import com.myschool.graph.assembler.ChartDataAssembler;
import com.myschool.graph.dto.LineChartDto;
import com.myschool.infra.web.constants.MimeTypes;
import com.myschool.student.dto.StudentDto;
import com.myschool.student.service.StudentService;
import com.myschool.web.application.constants.WebConstants;

/**
 * The Class StudentDashboardController.
 */
@Controller
@RequestMapping("student-dashboard")
public class StudentDashboardController {

    /** The Constant CHART_DATA. */
    private static final String CHART_DATA = "CHART_DATA";

    /** The Constant STUDENT_EXAM_SUMMARY_LINE_CHART_DATA. */
    private static final String STUDENT_EXAM_SUMMARY_LINE_CHART_DATA = "STUDENT_EXAM_SUMMARY_LINE_CHART_DATA";

    /** The holiday service. */
    @Autowired
    private HolidayService holidayService;

    /** The exam service. */
    @Autowired
    private ExamService examService;

    /** The exam grade service. */
    @Autowired
    private ExamGradeService examGradeService;

    /** The student exam service. */
    @Autowired
    private StudentExamService studentExamService;

    /** The student service. */
    @Autowired
    private StudentService studentService;

    /**
     * Private announcements.
     * 
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value="privateAnnouncements")
    public ModelAndView privateAnnouncements(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        JSONArray data = new JSONArray();
        data.put("[Private] New Branch has been created.");
        data.put("[Private] Schools in Hyderabad will be closed on Monday.");
        data.put("[Private] Science Exhibition to be held in bangalore in December.");
        data.put("[Private] Third quarterly exams to begin from December 10th.");
        data.put("[Private] Employees submit their pending leave requests by this weekend.");
        data.put("[Private] MySchool selected as the school of the award for the year 2013.");
        response.setContentType(MimeTypes.APPLICATION_JSON);
        PrintWriter writer = response.getWriter();
        writer.print(data.toString());
        writer.close();
        return null;
    }

    /**
     * Json private announcements.
     * 
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value="monthlyAttendanceSummary")
    public ModelAndView jsonPrivateAnnouncements(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        JSONObject jsonObject = new JSONObject();
        List<HolidayDto> holidays = holidayService.getAll();
        jsonObject.put("DeclaredHolidays", HolidayDataAssembler.create(holidays));
        // TODO add leaves, absents
        response.setContentType(MimeTypes.APPLICATION_JSON);
        PrintWriter writer = response.getWriter();
        writer.print(jsonObject.toString());
        writer.close();
        return null;
    }

    /**
     * Gets the latest exam.
     * 
     * @param request the request
     * @param response the response
     * @return the latest exam result
     * @throws Exception the exception
     */
    @RequestMapping(value="getLatestExam")
    public ModelAndView getLatestExam(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        ExamDto latestExam = null;
        JSONObject jsonResponse = new JSONObject();
        HttpSession session = request.getSession();
        StudentDto student = (StudentDto) session.getAttribute(WebConstants.STUDENT);
        if (student != null) {
            RegisteredClassDto registeredClass = student.getRegisteredClassDto();
            if (registeredClass != null && registeredClass.getClassId() != 0) {
                latestExam = examService.getLatestExam(registeredClass.getClassId());
            }
        }
        jsonResponse.put("ExamDetails", ExamDataAssembler.create(latestExam));
        response.setContentType(MimeTypes.APPLICATION_JSON);
        PrintWriter writer = response.getWriter();
        writer.print(jsonResponse.toString());
        writer.close();
        return null;
    }

    /**
     * Json latest exam result.
     * 
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value="jsonLatestExamResult")
    public ModelAndView jsonLatestExamResult(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        ExamDto latestExam = null;
        JSONArray data = new JSONArray();
        JSONObject jsonResponse = new JSONObject();
        HttpSession session = request.getSession();
        StudentDto student = (StudentDto) session.getAttribute(WebConstants.STUDENT);
        PersonalDetailsDto personalDetails = null;
        if (student != null) {
            RegisteredClassDto registeredClass = student.getRegisteredClassDto();
            if (registeredClass != null && registeredClass.getClassId() != 0) {
                latestExam = examService.getLatestExam(registeredClass.getClassId());
                if (latestExam != null) {
                    List<StudentInExamDto> studentsInExam = studentExamService.getStudentsInExam(latestExam.getExamId(), registeredClass.getClassId());
                    if (studentsInExam != null && !studentsInExam.isEmpty()) {
                        for (StudentInExamDto studentInExam : studentsInExam) {
                            student = studentInExam.getStudent();
                            personalDetails = student.getPersonalDetails();
                            JSONArray row = new JSONArray();
                            row.put(student.getAdmissionNumber());
                            row.put(personalDetails.getFirstName());
                            row.put(personalDetails.getMiddleName());
                            row.put(personalDetails.getLastName());
                            row.put(studentInExam.getTotalMarks());
                            row.put(studentInExam.getPercentage());
                            row.put(studentInExam.getGrade());
                            data.put(row);
                        }
                    }
                }
            }
        }
        jsonResponse.put(DataTypeValidator.AA_DATA, data);
        response.setContentType(MimeTypes.APPLICATION_JSON);
        PrintWriter writer = response.getWriter();
        writer.print(jsonResponse.toString());
        writer.close();
        return null;
    }

    /**
     * Student exam summary.
     * 
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value="studentExamSummary")
    public ModelAndView studentExamSummary(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        JSONObject jsonResponse = new JSONObject();
        HttpSession session = request.getSession();

        JSONObject jsonObject = (JSONObject) session.getAttribute(STUDENT_EXAM_SUMMARY_LINE_CHART_DATA);
        if (jsonObject == null) {
            StudentDto student = (StudentDto) session.getAttribute(WebConstants.STUDENT);
            if (student != null) {
                String admissionNumber = student.getAdmissionNumber();
                if (!StringUtil.isNullOrBlank(admissionNumber)) {
                    StudentExamsSummaryDto studentExamsSummary = studentExamService.getStudentMarks(admissionNumber);
                    LineChartDto lineChart = StudentExamDataAssembler.create(studentExamsSummary);
                    jsonObject = ChartDataAssembler.create(lineChart);
                    session.setAttribute(STUDENT_EXAM_SUMMARY_LINE_CHART_DATA, jsonObject);
                }
            }
        }
        jsonResponse.put(CHART_DATA, jsonObject);
        response.setContentType(MimeTypes.APPLICATION_JSON);
        PrintWriter writer = response.getWriter();
        writer.print(jsonResponse.toString());
        writer.close();
        return null;
    }

}
