package com.myschool.web.academic.controller;

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

import com.myschool.academic.dto.AcademicDto;
import com.myschool.academic.service.AcademicService;
import com.myschool.common.dto.ResultDto;
import com.myschool.common.exception.DataException;
import com.myschool.common.exception.ServiceException;
import com.myschool.common.util.StringUtil;
import com.myschool.common.validator.DataTypeValidator;
import com.myschool.infra.web.constants.MimeTypes;
import com.myschool.web.academic.constants.AcademicViewNames;
import com.myschool.web.common.parser.ResponseParser;
import com.myschool.web.common.util.ViewDelegationController;
import com.myschool.web.common.util.ViewErrorHandler;

/**
 * The Class BranchController.
 */
@Controller
@RequestMapping("academic")
public class AcademicController {

    /** The academic service. */
    @Autowired
    private AcademicService academicService;

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
        return ViewDelegationController.delegateWholePageView(request, AcademicViewNames.VIEW_ACADEMICS);
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
        List<AcademicDto> academics = academicService.getAll();

        if (academics != null) {
            for(AcademicDto academic : academics){
                JSONArray row = new JSONArray();
                row.put(academic.getAcademicYearName())
                .put(academic.getAcademicYearStartDate())
                .put(academic.getAcademicYearEndDate());
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
        return ViewDelegationController.delegateModelPageView(request, AcademicViewNames.MAINTAIN_ACADEMIC);
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
        String academicYearName = request.getParameter("academicYearName");

        if (!StringUtil.isNullOrBlank(academicYearName)) {
            AcademicDto academic = academicService.get(academicYearName);
            map.put("academic", academic);
        }
        return ViewDelegationController.delegateModelPageView(request, AcademicViewNames.MAINTAIN_ACADEMIC, map);
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
            AcademicDto academic = validateAndGetAcademic(request);
            resultDto.setSuccessful(academicService.create(academic));
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
            AcademicDto academic = validateAndGetAcademic(request);
            resultDto.setSuccessful(academicService.update(academic.getAcademicYearName(), academic));
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
            String academicYearName = request.getParameter("academicYearName");
            resultDto.setSuccessful(academicService.delete(academicYearName));
        } catch (ServiceException serviceException) {
            serviceException.printStackTrace();
            resultDto.setStatusMessage(serviceException.getMessage());
        } finally {
            ResponseParser.writeResponse(response, resultDto);
        }
        return null;
    }

    /**
     * Validate and get academic.
     *
     * @param request the request
     * @return the academic dto
     * @throws DataException the data exception
     */
    private AcademicDto validateAndGetAcademic(HttpServletRequest request) throws DataException {
        AcademicDto academic = new AcademicDto();

        String academicYearName = request.getParameter("academicYearName");
        String academicYearStartDate = request.getParameter("academicYearStartDate");
        String academicYearEndDate = request.getParameter("academicYearEndDate").trim();

        viewErrorHandler.validate(academicYearName, "academicYearName", DataTypeValidator.ANY_CHARACTER, true);
        viewErrorHandler.validate(academicYearStartDate, "academicYearStartDate", DataTypeValidator.DATE, true);
        viewErrorHandler.validate(academicYearEndDate, "academicYearEndDate", DataTypeValidator.DATE, true);

        academic.setAcademicYearName(academicYearName);
        academic.setAcademicYearStartDate(academicYearStartDate);
        academic.setAcademicYearEndDate(academicYearEndDate);
        return academic;
    }

}