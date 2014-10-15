package com.myschool.web.clazz.controller;

import java.io.PrintWriter;
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

import com.myschool.branch.service.BranchService;
import com.myschool.clazz.dto.SubjectDto;
import com.myschool.clazz.service.ClassService;
import com.myschool.clazz.service.SubjectService;
import com.myschool.common.dto.ResultDto;
import com.myschool.common.exception.DataException;
import com.myschool.common.exception.ServiceException;
import com.myschool.common.validator.DataTypeValidator;
import com.myschool.infra.web.constants.MimeTypes;
import com.myschool.school.service.SchoolService;
import com.myschool.web.clazz.constants.ClazzViewNames;
import com.myschool.web.common.parser.ResponseParser;
import com.myschool.web.common.util.ViewDelegationController;
import com.myschool.web.common.util.ViewErrorHandler;

/**
 * The Class SubjectController.
 */
@Controller
@RequestMapping("subject")
public class SubjectController {

    /** The subject service. */
    @Autowired
    private SubjectService subjectService;

    /** The school service. */
    @Autowired
    private SchoolService schoolService;

    /** The class service. */
    @Autowired
    private ClassService classService;

    /** The branch service. */
    @Autowired
    private BranchService branchService;

    /** The view error handler. */
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
        return ViewDelegationController.delegateWholePageView(request, ClazzViewNames.VIEW_SUBJECTS);
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
        JSONObject jsonResponse = new JSONObject();

        List<SubjectDto> subjects = subjectService.getAll();
        if (subjects != null) {
            for(SubjectDto subject : subjects) {
                JSONArray row = new JSONArray();
                row.put(subject.getSubjectId());
                row.put(subject.getSubjectName());
                data.put(row);
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
        return ViewDelegationController.delegateModelPageView(request, ClazzViewNames.MAINTAIN_SUBJECTS);
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

        ResultDto resultDto = new ResultDto();

        try {
            SubjectDto subjectDto = validateAndGetSubject(request);
            resultDto.setSuccessful(subjectService.create(subjectDto));
        } catch (DataException dataException) {
            resultDto.setStatusMessage(viewErrorHandler.getMessage(dataException.getMessage()));
        } catch (ServiceException serviceException) {
            resultDto.setStatusMessage(serviceException.getMessage());
        } finally {
            ResponseParser.writeResponse(response, resultDto);
        }
        return null;
    }

    /**
     * Validate and get subject.
     *
     * @param request the request
     * @return the subject dto
     * @throws DataException the data exception
     */
    private SubjectDto validateAndGetSubject(HttpServletRequest request) throws DataException {
        SubjectDto subject = new SubjectDto();
        String subjectName = request.getParameter("subjectName");
        viewErrorHandler.validate(subjectName, "subjectName", DataTypeValidator.ANY_CHARACTER, true);
        subject.setSubjectName(subjectName);
        return subject;
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
        String subjectId = request.getParameter("subjectId");

        if (subjectId != null && subjectId.trim().length() != 0) {
            SubjectDto subjectDto = subjectService.get(Integer.parseInt(subjectId));
            map.put("subject", subjectDto);
        }
        return ViewDelegationController.delegateModelPageView(request, ClazzViewNames.MAINTAIN_SUBJECTS, map);
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

        ResultDto resultDto = new ResultDto();

        try {
            String subjectId = request.getParameter("subjectId");
            SubjectDto subjectDto = validateAndGetSubject(request);
            resultDto.setSuccessful(subjectService.update(Integer.parseInt(subjectId), subjectDto));
        } catch (DataException dataException) {
            resultDto.setStatusMessage(viewErrorHandler.getMessage(dataException.getMessage()));
        } catch (ServiceException serviceException) {
            resultDto.setStatusMessage(serviceException.getMessage());
        } finally {
            ResponseParser.writeResponse(response, resultDto);
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

        ResultDto resultDto = new ResultDto();
        try {
            String subjectId = request.getParameter("subjectId");
            resultDto.setSuccessful(subjectService.delete(Integer.parseInt(subjectId)));
        } catch (ServiceException serviceException) {
            serviceException.printStackTrace();
            resultDto.setStatusMessage(serviceException.getMessage());
        } finally {
            ResponseParser.writeResponse(response, resultDto);
        }
        return null;
    }

}