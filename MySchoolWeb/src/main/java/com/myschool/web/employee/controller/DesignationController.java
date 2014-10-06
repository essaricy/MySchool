package com.myschool.web.employee.controller;

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

import com.myschool.common.dto.ResultDto;
import com.myschool.common.exception.DataException;
import com.myschool.common.exception.ServiceException;
import com.myschool.common.validator.DataTypeValidator;
import com.myschool.employee.dto.DesignationDto;
import com.myschool.employee.service.DesignationService;
import com.myschool.infra.web.constants.MimeTypes;
import com.myschool.web.common.parser.ResponseParser;
import com.myschool.web.common.util.ViewDelegationController;
import com.myschool.web.common.util.ViewErrorHandler;
import com.myschool.web.employee.constants.EmployeeViewNames;

/**
 * The Class DesignationController.
 */
@Controller
@RequestMapping("designation")
public class DesignationController {

    /** The designation service. */
    @Autowired
    private DesignationService designationService;

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
        return ViewDelegationController.delegateWholePageView(request, EmployeeViewNames.VIEW_DESIGNATIONS);
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

        List<DesignationDto> designations = designationService.getAll();
        if (designations != null) {
            for(DesignationDto designation : designations) {
                JSONArray row = new JSONArray();
                row.put(designation.getDesignationId())
                .put(designation.getDesignation());
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
        return ViewDelegationController.delegateModelPageView(request, EmployeeViewNames.MAINTAIN_DESIGNATION);
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
        String designationId = request.getParameter("designationId");

        if (designationId != null && designationId.trim().length() != 0) {
            DesignationDto designation = designationService.get(Integer.parseInt(designationId));
            map.put("designation", designation);
        }
        return ViewDelegationController.delegateModelPageView(request, EmployeeViewNames.MAINTAIN_DESIGNATION, map);
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
            DesignationDto designationDto = validateAndGetDesignation(request);
            resultDto.setSuccessful(designationService.create(designationDto));
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
     * Validate and get designation.
     *
     * @param request the request
     * @return the designation dto
     * @throws DataException the data exception
     */
    private DesignationDto validateAndGetDesignation(HttpServletRequest request) throws DataException {
        DesignationDto designationDto = new DesignationDto();

        String designation = request.getParameter("designation");
        viewErrorHandler.validate(designation, "designation", DataTypeValidator.ANY_CHARACTER, true);
        designationDto.setDesignation(designation);
        return designationDto;
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
            String designationId = request.getParameter("designationId");
            DesignationDto designationDto = validateAndGetDesignation(request);
            resultDto.setSuccessful(designationService.update(Integer.parseInt(designationId), designationDto));
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
            String designationId = request.getParameter("designationId");
            resultDto.setSuccessful(designationService.delete(Integer.parseInt(designationId)));
        } catch (ServiceException serviceException) {
            serviceException.printStackTrace();
            resultDto.setStatusMessage(serviceException.getMessage());
        } finally {
            ResponseParser.writeResponse(response, resultDto);
        }
        return null;
    }

}
