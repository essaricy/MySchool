package com.myschool.web.employee.controller;

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

import com.myschool.application.constants.ApplicationConstants;
import com.myschool.application.service.DocumentService;
import com.myschool.common.dto.ResultDto;
import com.myschool.common.exception.ServiceException;
import com.myschool.common.util.StringUtil;
import com.myschool.employee.assembler.EmployeeDataAssembler;
import com.myschool.employee.dto.DesignationDto;
import com.myschool.employee.dto.EmployeeDto;
import com.myschool.employee.dto.EmployeeSearchCriteriaDto;
import com.myschool.employee.dto.EmploymentStatus;
import com.myschool.employee.service.EmployeeService;
import com.myschool.web.application.constants.WebConstants;
import com.myschool.web.common.util.HttpUtil;
import com.myschool.web.common.util.ViewDelegationController;
import com.myschool.web.common.util.ViewErrorHandler;
import com.myschool.web.employee.constants.EmployeeViewNames;

/**
 * The Class EmployeeController.
 */
@Controller
@RequestMapping("employee")
public class EmployeeController {

    /** The Constant UNVERIFIED. */
    private static final String UNVERIFIED = "UNVERIFIED";

    /** The Constant VERIFIED. */
    private static final String VERIFIED = "VERIFIED";

    /** The Constant SEARCH_MODE. */
    private static final String SEARCH_MODE = "SEARCH_MODE";

    /** The document service. */
    @Autowired
    private DocumentService documentService;

    /** The employee service. */
    @Autowired
    private EmployeeService employeeService;

    /** The view error handler. */
    @Autowired
    private ViewErrorHandler viewErrorHandler;

    /**
     * Launch verified employees search.
     * 
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value = "launchVerifiedEmployeesSearch")
    public ModelAndView launchVerifiedEmployeesSearch(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(SEARCH_MODE, VERIFIED);
        map.put("TITLE", "Search Employees");
        return ViewDelegationController.delegateWholePageView(
                request, EmployeeViewNames.SEARCH_EMPLOYEE, map);
    }

    /**
     * Launch unverified employees search.
     * 
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value = "launchUnverifiedEmployeesSearch")
    public ModelAndView launchUnverifiedEmployeesSearch(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(SEARCH_MODE, UNVERIFIED);
        map.put("TITLE", "Search Employees (Portal)");
        return ViewDelegationController.delegateWholePageView(
                request, EmployeeViewNames.SEARCH_EMPLOYEE, map);
    }

    /**
     * Json list.
     * 
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value="verifiedEmployeesJSONList")
    public ModelAndView verifiedEmployeesJSONList(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        employeesJSONList(request, response, ApplicationConstants.Y);
        return null;
    }

    /**
     * Unverified employees json list.
     * 
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value="unverifiedEmployeesJSONList")
    public ModelAndView unverifiedEmployeesJSONList(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        employeesJSONList(request, response, ApplicationConstants.N);
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
        String employeeNumber = request.getParameter("employeeNumber");
        if (!StringUtil.isNullOrBlank(employeeNumber)) {
            map.put("Employee", employeeService.get(employeeNumber));
        }
        map.put(SEARCH_MODE, request.getParameter(SEARCH_MODE));
        return ViewDelegationController.delegateWholePageView(
                request, EmployeeViewNames.EMPLOYEE_REGISTRATION, map);
    }

    /**
     * View employee.
     * 
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value="viewEmployee")
    public ModelAndView viewEmployee(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        String employeeNumber = request.getParameter("employeeNumber");
        if (!StringUtil.isNullOrBlank(employeeNumber)) {
            map.put("Employee", employeeService.get(employeeNumber));
        }
        map.put(WebConstants.VIEW_ONLY, Boolean.TRUE);
        return ViewDelegationController.delegateModelPageView(
                request, EmployeeViewNames.VIEW_EMPLOYEE, map);
    }

    /**
     * Gets the next employee number.
     *
     * @param request the request
     * @param response the response
     * @return the next employee number
     * @throws Exception the exception
     */
    @RequestMapping(value="getLastEmployeeNumber")
    public ModelAndView getLastEmployeeNumber(HttpServletRequest request,
            HttpServletResponse response) throws Exception  {
        String nextEmployeeNumber = null;
        try {
            nextEmployeeNumber = employeeService.getLastEmployeeNumber();
        } finally {
            JSONObject result = new JSONObject();
            result.put("value", nextEmployeeNumber);
            if (nextEmployeeNumber == null) {
                result.put("successful", Boolean.FALSE);
            } else {
                result.put("successful", Boolean.TRUE);
            }
            HttpUtil.writeJson(response, result);
        }
        return null;
    }

    /**
     * Register employee.
     *
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value="registerEmployee")
    public ModelAndView registerEmployee(HttpServletRequest request,
            HttpServletResponse response) throws Exception  {

        ResultDto result = new ResultDto();
        EmployeeDto employee = null;

        try {
            String employeeDataValue = request.getParameter("EmployeeData");
            if (!StringUtil.isNullOrBlank(employeeDataValue)) {
                JSONObject employeeData = new JSONObject(employeeDataValue);
                employee = EmployeeDataAssembler.create(employeeData);
                if (employee != null) {
                    int employeeId = employee.getEmployeeId();
                    String employeeNumber = employee.getEmployeeNumber();
                    if (employeeId == 0) {
                        // Create a new employee
                        employeeService.create(employee);
                        EmployeeDto employeeDto = employeeService.get(employeeNumber);
                        result.setSuccessful(true);
                        result.setStatusMessage("Employee (" + employeeNumber + ") has been created successfully.");
                        result.setReferenceNumber(String.valueOf(employeeDto.getEmployeeId()));
                    } else {
                        // Update existing employee
                        employeeService.update(employeeId, employee);
                        result.setSuccessful(true);
                        result.setStatusMessage("Employee (" + employeeNumber + ") has been updated successfully.");
                    }
                }
            }
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
     *//*
    @RequestMapping(value="doDelete")
    public ModelAndView doDelete(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        ResultDto result = new ResultDto();
        try {
            String employeeNumber = request.getParameter("employeeNumber");
            result.setSuccessful(employeeService.delete(employeeNumber));
        } catch (ServiceException serviceException) {
            result.setStatusMessage(serviceException.getMessage());
        } finally {
            HttpUtil.writeAsJson(response, result);
        }
        return null;
    }*/

    /**
     * Search employee.
     *
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value="searchVerifiedEmployees")
    public ModelAndView searchVerifiedEmployees(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        searchEmployees(request, response, ApplicationConstants.Y);
        return null;
    }

    /**
     * Search unverified employees.
     * 
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value="searchUnverifiedEmployees")
    public ModelAndView searchUnverifiedEmployees(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        searchEmployees(request, response, ApplicationConstants.N);
        return null;
    }

    /**
     * Search employees.
     * 
     * @param request the request
     * @param response the response
     * @param verifiedStatus the verified status
     * @return the model and view
     * @throws Exception the exception
     */
    private ModelAndView searchEmployees(HttpServletRequest request,
            HttpServletResponse response, String verifiedStatus) throws Exception {

        JSONArray data = new JSONArray();
        try {
            String employeeSearchCriteriaValue = request.getParameter("EmployeeSearchCriteria");
            if (!StringUtil.isNullOrBlank(employeeSearchCriteriaValue)) {
                JSONObject employeeSearchCriteria = new JSONObject(employeeSearchCriteriaValue);
                EmployeeSearchCriteriaDto employeeSearchCriteriaDto = EmployeeDataAssembler.createEmployeeSearchCriteriaDto(employeeSearchCriteria);
                employeeSearchCriteriaDto.setVerifiedStatus(verifiedStatus);
                List<EmployeeDto> employees = employeeService.getAll(employeeSearchCriteriaDto);
                if (employees != null && !employees.isEmpty()) {
                    for (EmployeeDto employee : employees) {
                        JSONArray row = new JSONArray();
                        row.put(employee.getEmployeeId());
                        row.put(employee.getEmployeeNumber());
                        row.put(employee.getFirstName());
                        row.put(employee.getMiddleName());
                        row.put(employee.getLastName());
                        row.put(employee.getGender());
                        row.put(employee.getDateOfBirth());
                        
                        DesignationDto designation = employee.getDesignation();
                        row.put(designation.getDesignation());
                        row.put(employee.getEmployedAtBranch().getBranchCode());
                        row.put(employee.getEmploymentStartDate());
                        EmploymentStatus employmentStatus = employee.getEmploymentStatus();
                        row.put(employmentStatus.getDescription());
                        EmployeeDto reportingTo = employee.getReportingTo();
                        if (reportingTo == null) {
                            row.put("");
                            row.put("");
                            row.put("");
                            row.put("");
                        } else {
                            String employeeNumber = reportingTo.getEmployeeNumber();
                            if (employeeNumber == null) {
                                row.put("");
                            } else {
                                row.put(employeeNumber);
                            }
                            String firstName = reportingTo.getFirstName();
                            if (firstName == null) {
                                row.put("");
                            } else {
                                row.put(firstName);
                            }
                            String middleName = reportingTo.getMiddleName();
                            if (middleName == null) {
                                row.put("");
                            } else {
                                row.put(middleName);
                            }
                            String lastName = reportingTo.getLastName();
                            if (lastName == null) {
                                row.put("");
                            } else {
                                row.put(lastName);
                            }
                        }
                        data.put(row);
                    }
                }
            }
        } finally {
            HttpUtil.wrapAndWriteJson(response, "EmployeesData", data);
        }
        return null;
    }

    /**
     * Employees json list.
     * 
     * @param request the request
     * @param response the response
     * @param verifiedStatus the verified status
     * @throws Exception the exception
     */
    private void employeesJSONList(HttpServletRequest request,
            HttpServletResponse response, String verifiedStatus) throws Exception {
        JSONArray data = new JSONArray();
        try {
            EmployeeSearchCriteriaDto employeeSearchCriteria = new EmployeeSearchCriteriaDto();
            employeeSearchCriteria.setVerifiedStatus(verifiedStatus);
            List<EmployeeDto> employees = employeeService.getAll(employeeSearchCriteria);
            
            if (employees != null) {
                for (EmployeeDto employee : employees) {
                    JSONArray row = new JSONArray();
                    row.put(employee.getEmployeeId());
                    row.put(employee.getEmployeeNumber());
                    row.put(employee.getFirstName());
                    row.put(employee.getMiddleName());
                    row.put(employee.getLastName());
                    data.put(row);
                }
            }
        } finally {
            HttpUtil.wrapAndWriteAsAAData(response, data);
        }
    }

}
