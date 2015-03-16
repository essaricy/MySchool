package com.myschool.web.employee.controller;

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
import com.myschool.employee.dto.DesignationDto;
import com.myschool.employee.service.DesignationService;
import com.myschool.web.employee.constants.EmployeeViewNames;
import com.myschool.web.framework.controller.ViewDelegationController;
import com.myschool.web.framework.handler.ViewErrorHandler;
import com.myschool.web.framework.util.HttpUtil;

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
        try {
            List<DesignationDto> designations = designationService.getAll();
            if (designations != null) {
                for(DesignationDto designation : designations) {
                    JSONArray row = new JSONArray();
                    row.put(designation.getDesignationId())
                    .put(designation.getDesignation());
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

        ResultDto result = new ResultDto();

        try {
            DesignationDto designationDto = validateAndGetDesignation(request);
            result.setSuccessful(designationService.create(designationDto));
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

        ResultDto result = new ResultDto();

        try {
            String designationId = request.getParameter("designationId");
            DesignationDto designationDto = validateAndGetDesignation(request);
            result.setSuccessful(designationService.update(Integer.parseInt(designationId), designationDto));
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
            String designationId = request.getParameter("designationId");
            result.setSuccessful(designationService.delete(Integer.parseInt(designationId)));
        } catch (ServiceException serviceException) {
            result.setStatusMessage(serviceException.getMessage());
        } finally {
            HttpUtil.writeAsJson(response, result);
        }
        return null;
    }

}
