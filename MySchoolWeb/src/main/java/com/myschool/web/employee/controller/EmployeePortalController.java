package com.myschool.web.employee.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.myschool.common.dto.ResultDto;
import com.myschool.common.exception.ServiceException;
import com.myschool.common.util.StringUtil;
import com.myschool.employee.assembler.EmployeeDataAssembler;
import com.myschool.employee.dto.EmployeeDto;
import com.myschool.employee.service.EmployeeService;
import com.myschool.infra.captcha.agent.CaptchaAgent;
import com.myschool.web.application.constants.PortalViewNames;
import com.myschool.web.application.constants.WebConstants;
import com.myschool.web.framework.controller.ViewDelegationController;
import com.myschool.web.framework.util.HttpUtil;

/**
 * The Class EmployeePortalController.
 */
@Controller
@RequestMapping("portal-employee")
public class EmployeePortalController {

    /** The employee attributes controller. */
    @Autowired
    private EmployeeAttributesController employeeAttributesController;

    /** The employee service. */
    @Autowired
    private EmployeeService employeeService;

    /** The captcha agent. */
    @Autowired
    private CaptchaAgent captchaAgent;

    /**
     * Launch self submit.
     * 
     * @param request the request
     * @param response the response
     * @return the model and view
     */
    @RequestMapping(value="launchSelfSubmit")
    public ModelAndView launchSelfSubmit(HttpServletRequest request,
            HttpServletResponse response) {
        return ViewDelegationController.delegateWholePageView(
                request, PortalViewNames.EMPLOYEE_SELF_SUBMIT);
    }

    /**
     * Submit employee.
     * 
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value="submitEmployee")
    public ModelAndView submitEmployee(HttpServletRequest request,
            HttpServletResponse response) throws Exception  {

        ResultDto result = new ResultDto();
        EmployeeDto employee = null;

        try {
            // Verify CAPTCHA
            String userCaptchaResponse = request.getParameter(WebConstants.CAPTCHA_RESPONSE);
            boolean valid = captchaAgent.isValid(userCaptchaResponse);
            System.out.println("valid? " + valid);
            if (!valid) {
                throw new ServiceException("Form is not submitted. CAPTCHA moderated.");
            }

            String employeeDataValue = request.getParameter("EmployeeData");
            if (!StringUtil.isNullOrBlank(employeeDataValue)) {
                JSONObject employeeData = new JSONObject(employeeDataValue);
                employee = EmployeeDataAssembler.create(employeeData);
                employee.setVerified(false);
                if (employee != null) {
                    String employeeNumber = employee.getEmployeeNumber();
                    // Create a new employee
                    employeeService.create(employee);
                    EmployeeDto employeeDto = employeeService.get(employeeNumber);
                    result.setSuccessful(true);
                    result.setStatusMessage("Thank you for using Employee Self-Submit Service. "
                            + "Your informaton has been successfully submitted and you will be notified through email when it is approved.<br/>"
                            + "For any queries, please visit the corresponding branch.");
                    result.setReferenceNumber(String.valueOf(employeeDto.getEmployeeId()));
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
     */
    @RequestMapping(value="doDelete")
    public ModelAndView doDelete(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        String attribute = request.getParameter("attribute");
        String attributeId = request.getParameter("attributeId");
        if (attribute != null && !StringUtil.isEmpty(attribute)
                && attributeId != null && !StringUtil.isEmpty(attributeId)) {
            if (Integer.parseInt(attributeId) == 0) {
                employeeAttributesController.doDelete(request, response);
            }
        }
        return null;
    }
}
