package com.myschool.web.branch.controller;

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

import com.myschool.branch.dto.DivisionDto;
import com.myschool.branch.service.DivisionService;
import com.myschool.common.dto.ResultDto;
import com.myschool.common.exception.DataException;
import com.myschool.common.exception.ServiceException;
import com.myschool.common.validator.DataTypeValidator;
import com.myschool.web.branch.constants.BranchViewNames;
import com.myschool.web.framework.controller.ViewDelegationController;
import com.myschool.web.framework.handler.ViewErrorHandler;
import com.myschool.web.framework.util.HttpUtil;

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
        try {
            List<DivisionDto> divisions = divisionService.getAll();
            if (divisions != null) {
                for(DivisionDto c : divisionService.getAll()){
                    JSONArray row = new JSONArray();
                    row.put(c.getDivisionId()).put(c.getDivisionCode()).put(c.getDescription());
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

        ResultDto result = new ResultDto();
        try {
            DivisionDto divisionDto = validateAndGetDivision(request);
            result.setSuccessful(divisionService.create(divisionDto));
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

        ResultDto result = new ResultDto();

        try {
            String divisionId = request.getParameter("divisionId");
            DivisionDto divisionDto = validateAndGetDivision(request);
            result.setSuccessful(divisionService.update(Integer.parseInt(divisionId), divisionDto));
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

        // This is supposed to be an AJAX call.
        ResultDto result = new ResultDto();
        try {
            String divisionId = request.getParameter("divisionId");
            result.setSuccessful(divisionService.delete(Integer.parseInt(divisionId)));
        } catch (ServiceException serviceException) {
            result.setStatusMessage(serviceException.getMessage());
        } finally {
            HttpUtil.writeAsJson(response, result);
        }
        return null;
    }

}