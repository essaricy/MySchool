package com.myschool.web.student.controller;

import java.util.ArrayList;
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

import com.myschool.clazz.dto.RegisteredClassDto;
import com.myschool.common.dto.ResultDto;
import com.myschool.common.exception.ServiceException;
import com.myschool.common.util.StringUtil;
import com.myschool.exam.dto.ExamDto;
import com.myschool.exam.dto.ExamGradeDto;
import com.myschool.exam.dto.StudentExamDto;
import com.myschool.exam.dto.StudentInExamDto;
import com.myschool.exam.dto.SubjectExamDto;
import com.myschool.exam.service.ExamGradeService;
import com.myschool.exam.service.ExamService;
import com.myschool.exam.service.StudentExamService;
import com.myschool.student.dto.StudentDto;
import com.myschool.web.common.util.HttpUtil;
import com.myschool.web.common.util.ViewDelegationController;
import com.myschool.web.exam.constants.ExamViewNames;

/**
 * The Class StudentExamController.
 */
@Controller
@RequestMapping("student-exam")
public class StudentExamController {

    /** The exam service. */
    @Autowired
    private ExamService examService;

    /** The exam grade service. */
    @Autowired
    private ExamGradeService examGradeService;

    /** The student exam service. */
    @Autowired
    private StudentExamService studentExamService;

    /**
     * List exams for marks entry.
     *
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value="viewExamResults")
    public ModelAndView viewExamResults(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        return ViewDelegationController.delegateWholePageView(request, ExamViewNames.VIEW_EXAM_RESULTS);
    }

    /**
     * View students marks sheet.
     * 
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value="viewStudentsMarksSheet")
    public ModelAndView viewStudentsMarksSheet(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        List<SubjectExamDto> subjectExams = null;
        Map<String, Object> map = new HashMap<String, Object>();

        String examId = request.getParameter("ExamId");

        if (!StringUtil.isNullOrBlank(examId)) {
            ExamDto examDetails = examService.get(Integer.parseInt(examId));
            RegisteredClassDto registeredClass = examDetails.getRegisteredClass();
            map.put("ExamDetails", examDetails);

            // Get all the grades
            List<ExamGradeDto> examGrades = examGradeService.getAll();
            map.put("ExamGrades", examGrades);
            // get subject exams in this class.
            subjectExams = examDetails.getSubjectExams();
            map.put("SubjectExams", subjectExams);
            // get students in this class
            List<StudentInExamDto> studentsInExam = studentExamService.getStudentsInExam(examDetails.getExamId(), registeredClass.getClassId());
            map.put("StudentsInExam", studentsInExam);
        }
        return ViewDelegationController.delegateModelPageView(request, ExamViewNames.MAINTAIN_EXAM_RESULTS, map);
    }

    /**
     * Update student exam.
     *
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value="updateStudentsMarks")
    public ModelAndView updateStudentsMarks(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        ResultDto result = new ResultDto();
        result.setSuccessful(ResultDto.FAILURE);
        String studentsMarksData = request.getParameter("StudentsMarksData");
        StudentDto student = null;
        JSONObject jsonStudentMarksData = null;
        StudentInExamDto studentInExam = null;

        try {
            JSONArray jsonStudentsMarksData = new JSONArray(studentsMarksData);
            if (jsonStudentsMarksData != null && jsonStudentsMarksData.length() != 0) {
                List<StudentInExamDto> studentInExams = new ArrayList<StudentInExamDto>();
                for (int index = 0; index < jsonStudentsMarksData.length(); index++) {
                    jsonStudentMarksData = (JSONObject) jsonStudentsMarksData.get(index);
                    if (jsonStudentMarksData != null) {
                        studentInExam = new StudentInExamDto();
                        JSONObject jsonStudentData = jsonStudentMarksData.getJSONObject("StudentData");
                        JSONArray subjectsMarksData = jsonStudentMarksData.getJSONArray("SubjectsMarksData");
                        if (jsonStudentData != null && subjectsMarksData != null && subjectsMarksData.length() != 0) {
                            student = new StudentDto();
                            student.setStudentId(jsonStudentData.getInt("StudentId"));
                            student.setAdmissionNumber(jsonStudentData.getString("AdmissionNumber"));
                            studentInExam.setStudent(student);
                            updateStudentInExam(studentInExam, subjectsMarksData);
                            studentInExams.add(studentInExam);
                        }
                    }
                }
                studentExamService.update(studentInExams);
                result.setSuccessful(ResultDto.SUCCESS);
                result.setStatusMessage("Student Marks have been updated successfully.");
            }
        } catch (NumberFormatException numberFormatException) {
            result.setStatusMessage(numberFormatException.getMessage());
        } catch (ServiceException serviceException) {
            result.setStatusMessage(serviceException.getMessage());
        } finally {
            HttpUtil.writeAsJson(response, result);
        }
        return null;
    }

    /**
     * Update student in exam.
     *
     * @param studentInExam the student in exam
     * @param studentExamsJsonArray the student exams json array
     */
    private static void updateStudentInExam(StudentInExamDto studentInExam,
            JSONArray studentExamsJsonArray) {
        String obtainedMarks = null;
        JSONObject studentExamJsonObject = null;
        StudentExamDto studentExam = new StudentExamDto();
        List<StudentExamDto> studentExams = studentInExam.getStudentExams();
        if (studentExams == null) {
            studentExams = new ArrayList<StudentExamDto>();
        }
        if (studentExamsJsonArray != null) {
            for (int index = 0; index < studentExamsJsonArray.length(); index++) {
                studentExamJsonObject = (JSONObject) studentExamsJsonArray.get(index);
                if (studentExamJsonObject != null) {
                    studentExam = new StudentExamDto();
                    obtainedMarks = studentExamJsonObject.getString("ObtainedMarks");
                    if (obtainedMarks != null && !StringUtil.isEmpty(obtainedMarks)) {
                        studentExam.setObtainedMarks(Integer.parseInt(obtainedMarks));
                    }
                    studentExam.setStudentExamId(studentExamJsonObject.getInt("StudentExamId"));
                    studentExam.setSubjectExamId(studentExamJsonObject.getInt("SubjectExamId"));
                    studentExams.add(studentExam);
                }
            }
        }
        studentInExam.setStudentExams(studentExams);
    }

}
