package com.myschool.web.exam.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.myschool.common.dto.ResultDto;
import com.myschool.common.exception.DataException;
import com.myschool.common.exception.ServiceException;
import com.myschool.common.validator.DataTypeValidator;
import com.myschool.exam.dto.ExamGradeDto;
import com.myschool.exam.service.ExamGradeService;
import com.myschool.web.exam.constants.ExamViewNames;
import com.myschool.web.framework.controller.ViewDelegationController;
import com.myschool.web.framework.handler.ViewErrorHandler;
import com.myschool.web.framework.util.HttpUtil;

@Controller
@RequestMapping("examGrade")
public class ExamGradeController {

    /** The exam grade service. */
    @Autowired
    private ExamGradeService examGradeService;

    /** The resource bundle util. */
    @Autowired
    private ViewErrorHandler viewErrorHandler;

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
        return ViewDelegationController.delegateWholePageView(request, ExamViewNames.VIEW_EXAM_GRADES);
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
        JSONArray data = new JSONArray();
        try {
            List<ExamGradeDto> examGradees = examGradeService.getAll();
            if (examGradees != null) {
                for(ExamGradeDto examGrade : examGradees) {
                    JSONArray row = new JSONArray();
                    row.put(examGrade.getExamGradeId());
                    row.put(examGrade.getGradeName());
                    row.put(examGrade.getQualifyingPercentage());
                    data.put(row);
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
    @RequestMapping(value="launchNew")
    public ModelAndView launchNew(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        return ViewDelegationController.delegateModelPageView(request, ExamViewNames.MAINTAIN_EXAM_GRADE);
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
            ExamGradeDto examGradeDto = validateAndGetExamGrade(request);
            result.setSuccessful(examGradeService.create(examGradeDto));
        } catch (DataException dataException) {
            result.setStatusMessage(viewErrorHandler.getMessage(dataException.getMessage()));
        } catch (ServiceException serviceException) {
            result.setStatusMessage(serviceException.getMessage());
        } finally {
            HttpUtil.writeAsJson(response, result);
        }
        return null;
    }

    /**
     * Validate and get examGrade.
     *
     * @param request the request
     * @return the examGrade dto
     * @throws DataException the data exception
     */
    private ExamGradeDto validateAndGetExamGrade(HttpServletRequest request) throws DataException {
        ExamGradeDto examGradeDto = new ExamGradeDto();

        String gradeName = request.getParameter("gradeName");
        String qualifyingPercentage = request.getParameter("qualifyingPercentage");

        viewErrorHandler.validate(gradeName, "gradeName", DataTypeValidator.ANY_CHARACTER, true);
        viewErrorHandler.validate(qualifyingPercentage, "qualifyingPercentage", DataTypeValidator.INTEGER, true);

        examGradeDto.setGradeName(gradeName);
        examGradeDto.setQualifyingPercentage(Integer.parseInt(qualifyingPercentage));
        return examGradeDto;
    }

    /**
     * Launch update.
     *
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value="launchUpdate")
    public ModelAndView launchUpdate(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        Map<String, Object> map = new HashMap<String, Object>();
        String examGradeId = request.getParameter("examGradeId");

        if (examGradeId != null && examGradeId.trim().length() != 0) {
            ExamGradeDto examGradeDto = examGradeService.get(Integer.parseInt(examGradeId));
            map.put("examGrade", examGradeDto);
        }
        return ViewDelegationController.delegateModelPageView(request, ExamViewNames.MAINTAIN_EXAM_GRADE, map);
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
            String examGradeId = request.getParameter("examGradeId");
            ExamGradeDto examGradeDto = validateAndGetExamGrade(request);
            result.setSuccessful(examGradeService.update(Integer.parseInt(examGradeId), examGradeDto));
        } catch (DataException dataException) {
            result.setStatusMessage(viewErrorHandler.getMessage(dataException.getMessage()));
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
            String examGradeId = request.getParameter("examGradeId");
            result.setSuccessful(examGradeService.delete(Integer.parseInt(examGradeId)));
        } catch (ServiceException serviceException) {
            result.setStatusMessage(serviceException.getMessage());
        } finally {
            HttpUtil.writeAsJson(response, result);
        }
        return null;
    }
}
