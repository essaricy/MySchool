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
import com.myschool.common.util.StringUtil;
import com.myschool.employee.dto.EmploymentStatus;
import com.myschool.employee.service.EmploymentStatusService;
import com.myschool.web.employee.constants.EmployeeViewNames;
import com.myschool.web.framework.controller.ViewDelegationController;
import com.myschool.web.framework.util.HttpUtil;

/**
 * The Class EmploymentStatusController.
 */
@Controller
@RequestMapping("employment")
public class EmploymentStatusController {

    /** The employment status service. */
    @Autowired
    private EmploymentStatusService employmentStatusService;

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
        return ViewDelegationController.delegateWholePageView(request, EmployeeViewNames.VIEW_EMPLOYMENT_STATUS);
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
            List<EmploymentStatus> employmentStatusList = employmentStatusService.getAll();
            if (employmentStatusList != null) {
                for(EmploymentStatus employmentStatus : employmentStatusList) {
                    JSONArray row = new JSONArray();
                    row.put(employmentStatus.getStatusId())
                    .put(employmentStatus.getDescription());
                    data.put(row);
                }
            }
        } finally {
            HttpUtil.wrapAndWriteAsAAData(response, data);
        }
        return null;
    }

    /**
     * Launch.
     * 
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value="launch")
    public ModelAndView launch(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        Map<String, Object> map = new HashMap<String, Object>();
        String employmentStatusId = request.getParameter("employmentStatusId");

        if (!StringUtil.isNullOrBlank(employmentStatusId)) {
            EmploymentStatus employmentStatus = employmentStatusService.get(Integer.parseInt(employmentStatusId));
            map.put("employmentStatus", employmentStatus);
        }
        return ViewDelegationController.delegateModelPageView(request, EmployeeViewNames.MAINTAIN_EMPLOYMENT_STATUS, map);
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
            EmploymentStatus employmentStatus = validateAndGetEmploymentStatus(request);
            result.setSuccessful(employmentStatusService.create(employmentStatus));
            result.setStatusMessage("Employment Status has been created successfully.");
        } catch (DataException dataException) {
            result.setStatusMessage(dataException.getMessage());
        } catch (ServiceException serviceException) {
            result.setStatusMessage(serviceException.getMessage());
        } finally {
            HttpUtil.writeAsJson(response, result);
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

        ResultDto result = new ResultDto();

        try {
            String employmentStatusId = request.getParameter("employmentStatusId");
            if (!StringUtil.isNullOrBlank(employmentStatusId)) {
                EmploymentStatus employmentStatus = validateAndGetEmploymentStatus(request);
                result.setSuccessful(employmentStatusService.update(Integer.parseInt(employmentStatusId), employmentStatus));
                result.setStatusMessage("Employment Status has been updated.");
            }
        } catch (DataException dataException) {
            result.setStatusMessage(dataException.getMessage());
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
            String employmentStatusId = request.getParameter("employmentStatusId");
            result.setSuccessful(employmentStatusService.delete(Integer.parseInt(employmentStatusId)));
            result.setStatusMessage("Employment Status has been deleted successfully.");
        } catch (ServiceException serviceException) {
            result.setStatusMessage(serviceException.getMessage());
        } finally {
            HttpUtil.writeAsJson(response, result);
        }
        return null;
    }

    /**
     * Validate and get employment status.
     * 
     * @param request the request
     * @return the employment status
     * @throws DataException the data exception
     */
    private EmploymentStatus validateAndGetEmploymentStatus(HttpServletRequest request) throws DataException {
        EmploymentStatus employmentStatus = new EmploymentStatus();
        employmentStatus.setDescription(request.getParameter("description"));
        return employmentStatus;
    }

}
