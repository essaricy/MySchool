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

import com.myschool.application.dto.ImageAccessDto;
import com.myschool.common.constants.RecordStatus;
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
import com.myschool.web.employee.constants.EmployeeViewNames;
import com.myschool.web.framework.controller.ViewDelegationController;
import com.myschool.web.framework.util.HttpUtil;

/**
 * The Class EmployeeController.
 */
@Controller
@RequestMapping("employee")
public class EmployeeController {

    /** The employee service. */
    @Autowired
    private EmployeeService employeeService;

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
        map.put(WebConstants.RECORD_STATUS, RecordStatus.VERIFIED.toString());
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
        map.put(WebConstants.RECORD_STATUS, RecordStatus.UNVERIFIED.toString());
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
        employeesJSONList(request, response, RecordStatus.VERIFIED);
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
        employeesJSONList(request, response, RecordStatus.UNVERIFIED);
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
        map.put(WebConstants.RECORD_STATUS, request.getParameter(WebConstants.RECORD_STATUS));
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
     * Gets the next employee.
     *
     * @param request the request
     * @param response the response
     * @return the next employee
     * @throws Exception the exception
     */
    @RequestMapping(value="getNextEmployee")
    public ModelAndView getNextEmployee(HttpServletRequest request,
            HttpServletResponse response) throws Exception  {
    	EmployeeDto nextEmployee = null;
    	Map<String, Object> map = new HashMap<String, Object>();
        String employeeNumber = request.getParameter("EmployeeNumber");
        String type = request.getParameter("Type");
        RecordStatus recordStatus = RecordStatus.get(type);
        if (recordStatus != null) {
        	nextEmployee = employeeService.getNext(employeeNumber, recordStatus);
        }
        if (nextEmployee == null) {
        	nextEmployee = employeeService.get(employeeNumber);
        }
        map.put("Employee", nextEmployee);
        map.put(WebConstants.RECORD_STATUS, request.getParameter(WebConstants.RECORD_STATUS));
        return ViewDelegationController.delegateWholePageView(
                request, EmployeeViewNames.EMPLOYEE_REGISTRATION, map);
    }

    /**
     * Gets the previous employee.
     *
     * @param request the request
     * @param response the response
     * @return the previous employee
     * @throws Exception the exception
     */
    @RequestMapping(value="getPreviousEmployee")
    public ModelAndView getPreviousEmployee(HttpServletRequest request,
            HttpServletResponse response) throws Exception  {
    	EmployeeDto nextEmployee = null;
    	Map<String, Object> map = new HashMap<String, Object>();
        String employeeNumber = request.getParameter("EmployeeNumber");
        String type = request.getParameter("Type");
        RecordStatus recordStatus = RecordStatus.get(type);
        if (recordStatus != null) {
        	nextEmployee = employeeService.getPrevious(employeeNumber, recordStatus);
        }
        if (nextEmployee == null) {
        	nextEmployee = employeeService.get(employeeNumber);
        }
        map.put("Employee", nextEmployee);
        map.put(WebConstants.RECORD_STATUS, request.getParameter(WebConstants.RECORD_STATUS));
        return ViewDelegationController.delegateWholePageView(
                request, EmployeeViewNames.EMPLOYEE_REGISTRATION, map);
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
                        result.setStatusMessage("Employee (" + employeeNumber + ") has been created successfully.");
                    } else {
                        // Update existing employee
                        employeeService.update(employeeId, employee);
                        result.setStatusMessage("Employee (" + employeeNumber + ") has been updated successfully.");
                    }
                    EmployeeDto employeeDto = employeeService.get(employeeNumber);
                    if (employeeDto != null) {
                        result.setSuccessful(true);
                        result.setReferenceNumber(String.valueOf(employeeDto.getEmployeeId()));
                        result.setReference(employeeDto);
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
        searchEmployees(request, response, RecordStatus.VERIFIED);
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
        searchEmployees(request, response, RecordStatus.UNVERIFIED);
        return null;
    }

    /**
     * Search employees.
     * 
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    private ModelAndView searchEmployees(HttpServletRequest request,
            HttpServletResponse response, RecordStatus recordStatus) throws Exception {

        JSONArray data = new JSONArray();
        try {
            String employeeSearchCriteriaValue = request.getParameter("EmployeeSearchCriteria");
            if (!StringUtil.isNullOrBlank(employeeSearchCriteriaValue)) {
                JSONObject employeeSearchCriteria = new JSONObject(employeeSearchCriteriaValue);
                EmployeeSearchCriteriaDto employeeSearchCriteriaDto = EmployeeDataAssembler.createEmployeeSearchCriteriaDto(employeeSearchCriteria);
                employeeSearchCriteriaDto.setRecordStatus(recordStatus);
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

                        ImageAccessDto imageAccess = employee.getImageAccess();
                        if (imageAccess == null) {
                            row.put("");
                            row.put("");
                            row.put("");
                        } else {
                            row.put(imageAccess.getDirectLink());
                            row.put(imageAccess.getPassportLink());
                            row.put(imageAccess.getThumbnailLink());
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
     * @throws Exception the exception
     */
    private void employeesJSONList(HttpServletRequest request,
            HttpServletResponse response, RecordStatus recordStatus) throws Exception {
        JSONArray data = new JSONArray();
        try {
            EmployeeSearchCriteriaDto employeeSearchCriteria = new EmployeeSearchCriteriaDto();
            employeeSearchCriteria.setRecordStatus(recordStatus);
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
