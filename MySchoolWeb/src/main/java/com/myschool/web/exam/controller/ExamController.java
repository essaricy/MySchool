package com.myschool.web.exam.controller;

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

import com.myschool.clazz.dto.RegisteredSubjectDto;
import com.myschool.clazz.service.RegisteredClassService;
import com.myschool.clazz.service.RegisteredSubjectService;
import com.myschool.common.dto.ResultDto;
import com.myschool.common.exception.ServiceException;
import com.myschool.common.util.StringUtil;
import com.myschool.exam.dto.ExamDto;
import com.myschool.exam.dto.SubjectExamDto;
import com.myschool.exam.service.ExamService;
import com.myschool.web.exam.constants.ExamViewNames;
import com.myschool.web.framework.controller.ViewDelegationController;
import com.myschool.web.framework.util.HttpUtil;

/**
 * The Class ExamController.
 */
@Controller
@RequestMapping("exam")
public class ExamController {

    @Autowired
    private RegisteredClassService registeredClassService;

    /** The registered subject service. */
    @Autowired
    private RegisteredSubjectService registeredSubjectService;

    /** The exam service. */
    @Autowired
    private ExamService examService;

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
        return ViewDelegationController.delegateWholePageView(request, ExamViewNames.VIEW_EXAMS);
    }

    /**
     * Json list by class.
     *
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value="jsonListByClass")
    public ModelAndView jsonListByClass(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        JSONArray data = new JSONArray();
        try {
            String classId = request.getParameter("classId");
            if (classId != null && !StringUtil.isEmpty(classId)) {
                List<ExamDto> byClass = examService.getByClass(Integer.parseInt(classId));
                if (byClass != null) {
                    for(ExamDto exam: byClass){
                        if (exam != null) {
                            JSONArray row = new JSONArray();
                            row.put(exam.getExamId());
                            row.put(exam.getExamName());
                            row.put(exam.getExamDate());
                            row.put(exam.isExamCompleted());
                            data.put(row);
                        }
                    }
                }
            }
        } finally {
            HttpUtil.wrapAndWriteAsAAData(response, data);
        }
        return null;
    }

    /**
     * Launch new.
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
        result.setSuccessful(ResultDto.FAILURE);
        String updatedExamsData = request.getParameter("updatedExamsData");

        ExamDto exam = new ExamDto();
        RegisteredSubjectDto registeredSubject = null;
        SubjectExamDto subjectExam = null;
        List<SubjectExamDto> subjectExams = null;

        try {
            JSONObject jsonObject = new JSONObject(updatedExamsData);
            String classId = (String) jsonObject.get("classId");
            String examId = (String) jsonObject.get("examId");
            if (examId != null && !StringUtil.isEmpty(examId)) {
                exam.setExamId(Integer.parseInt(examId));
            }
            String examName = (String) jsonObject.get("examName");
            exam.setExamName(examName);
            String examDate = (String) jsonObject.get("examDate");
            exam.setExamDate(examDate);

            JSONArray subjectExamsData = jsonObject.getJSONArray("subjectExamsData");
            if (subjectExamsData != null) {
                for (int index = 0; index < subjectExamsData.length(); index++) {
                    if (subjectExams == null) {
                        subjectExams = new ArrayList<SubjectExamDto>();
                    }
                    JSONObject subjectExamObject = (JSONObject) subjectExamsData.get(index);
                    if (subjectExamObject != null) {
                        subjectExam = new SubjectExamDto();
                        subjectExam.setSubjectExamId(subjectExamObject.getInt("subjectExamId"));
                        registeredSubject = new RegisteredSubjectDto();
                        registeredSubject.setSubjectId(subjectExamObject.getInt("registeredSubjectId"));
                        subjectExam.setRegisteredSubject(registeredSubject);
                        subjectExam.setMaximumMarks(subjectExamObject.getInt("maximumMarks"));
                        subjectExams.add(subjectExam);
                    }
                }
            }
            exam.setSubjectExams(subjectExams);
            result.setSuccessful(examService.updateExam(Integer.parseInt(classId), exam)); 
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
            String examId = request.getParameter("examId");
            result.setSuccessful(examService.delete(Integer.parseInt(examId)));
        } catch (ServiceException serviceException) {
            result.setStatusMessage(serviceException.getMessage());
        } finally {
            HttpUtil.writeAsJson(response, result);
        }
        return null;
    }

    /**
     * Launch update.
     *
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value="launchExam")
    public ModelAndView launchExam(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        Map<String, Object> map = new HashMap<String, Object>();

        String classId = request.getParameter("classId");
        if (classId != null && !StringUtil.isEmpty(classId)) {
            List<RegisteredSubjectDto> registeredSubjects = registeredSubjectService.getByClass(Integer.parseInt(classId));
            map.put("classId", classId);
            map.put("classDetails", registeredClassService.get(Integer.parseInt(classId)));
            map.put("registeredSubjects", registeredSubjects);

            String examId = request.getParameter("examId");
            if (examId != null && examId.trim().length() != 0) {
                ExamDto examDetails = examService.get(Integer.parseInt(examId));
                map.put("examDetails", examDetails);
            }
        }
        return ViewDelegationController.delegateModelPageView(request, ExamViewNames.MAINTAIN_EXAM, map);
    }

    /**
     *
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value="freezeExam")
    public ModelAndView freezeExam(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        ResultDto result = new ResultDto();

        String examId = request.getParameter("examId");
        //String classId = request.getParameter("classId");

        try {
            examService.freezeExam(Integer.parseInt(examId)/*, Integer.parseInt(classId)*/);
            result.setSuccessful(ResultDto.SUCCESS);
            result.setStatusMessage("Exam has been successfully marked as completed.");
        } catch (NumberFormatException numberFormatException) {
            result.setStatusMessage(numberFormatException.getMessage());
        } catch (ServiceException serviceException) {
            result.setStatusMessage(serviceException.getMessage());
        } finally {
            HttpUtil.writeAsJson(response, result);
        }
        return null;
    }

}
