package com.myschool.web.branch.controller;

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

import com.myschool.branch.dto.DivisionDto;
import com.myschool.branch.service.DivisionService;
import com.myschool.common.dto.ResultDto;
import com.myschool.common.exception.DataException;
import com.myschool.common.exception.ServiceException;
import com.myschool.common.validator.DataTypeValidator;
import com.myschool.infra.web.constants.MimeTypes;
import com.myschool.web.branch.constants.BranchViewNames;
import com.myschool.web.common.parser.ResponseParser;
import com.myschool.web.common.util.ViewDelegationController;
import com.myschool.web.common.util.ViewErrorHandler;

/**
 * The Class DivisionController.
 */
@Controller
@RequestMapping("division")
public class DivisionController {

    /** The division service. */
    @Autowired
    private DivisionService divisionService = null;

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
        return ViewDelegationController.delegateWholePageView(request, BranchViewNames.VIEW_DIVISIONS);
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

        List<DivisionDto> divisions = divisionService.getAll();
        if (divisions != null) {
            for(DivisionDto c : divisionService.getAll()){
                JSONArray row = new JSONArray();
                row.put(c.getDivisionId()).put(c.getDivisionCode()).put(c.getDescription());
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
        return ViewDelegationController.delegateModelPageView(request, BranchViewNames.MAINTAIN_DIVISION);
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
            DivisionDto divisionDto = validateAndGetDivision(request);
            resultDto.setSuccessful(divisionService.create(divisionDto));
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
     * Validate and get division.
     *
     * @param request the request
     * @return the division dto
     * @throws DataException the data exception
     */
    private DivisionDto validateAndGetDivision(HttpServletRequest request) throws DataException {
        String divisionCode = request.getParameter("divisionCode");
        String description = request.getParameter("description");

        viewErrorHandler.validate(divisionCode, "divisionCode", DataTypeValidator.NAME, true);
        viewErrorHandler.validate(description, "description", DataTypeValidator.ANY_CHARACTER, true);

        DivisionDto divisionDto = new DivisionDto();
        divisionDto.setDivisionCode(request.getParameter("divisionCode"));
        divisionDto.setDescription(request.getParameter("description"));
        return divisionDto;
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
        String divisionId = request.getParameter("divisionId");

        if (divisionId != null && divisionId.trim().length() != 0) {
            DivisionDto divisionDto = divisionService.get(Integer.parseInt(divisionId));
            map.put("division", divisionDto);
        }
        return ViewDelegationController.delegateModelPageView(request, BranchViewNames.MAINTAIN_DIVISION, map);
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
            String divisionId = request.getParameter("divisionId");
            DivisionDto divisionDto = validateAndGetDivision(request);
            resultDto.setSuccessful(divisionService.update(Integer.parseInt(divisionId), divisionDto));
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

        // This is supposed to be an AJAX call.
        ResultDto resultDto = new ResultDto();
        try {
            String divisionId = request.getParameter("divisionId");
            resultDto.setSuccessful(divisionService.delete(Integer.parseInt(divisionId)));
        } catch (ServiceException serviceException) {
            resultDto.setStatusMessage(serviceException.getMessage());
        } finally {
            ResponseParser.writeResponse(response, resultDto);
        }
        return null;
    }

}