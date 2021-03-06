package com.myschool.web.student.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.myschool.common.dto.ResultDto;
import com.myschool.common.exception.ServiceException;
import com.myschool.infra.captcha.agent.CaptchaAgent;
import com.myschool.student.assembler.StudentDataAssembler;
import com.myschool.student.dto.StudentDto;
import com.myschool.student.service.StudentService;
import com.myschool.web.application.constants.PortalViewNames;
import com.myschool.web.application.constants.WebConstants;
import com.myschool.web.framework.controller.ViewDelegationController;
import com.myschool.web.framework.util.HttpUtil;
import com.quasar.core.util.StringUtil;

/**
 */
@Controller
@RequestMapping("portal-student")
public class StudentPortalController {

    @Autowired
    private StudentAttributesController studentAttributesController;

    @Autowired
    private StudentService studentService;

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
                request, PortalViewNames.STUDENT_SELF_SUBMIT);
    }

    /**
     * 
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value="submitStudent")
    public ModelAndView submitStudent(HttpServletRequest request,
            HttpServletResponse response) throws Exception  {

        ResultDto result = new ResultDto();
        StudentDto student = null;

        try {
            String studentDataValue = request.getParameter("StudentData");
            if (!StringUtil.isNullOrBlank(studentDataValue)) {
                JSONObject studentData = new JSONObject(studentDataValue);
                student = StudentDataAssembler.create(studentData);
                if (student != null) {
                    student.setVerified(false);
                    String admissionNumber = student.getAdmissionNumber();

                    // Verify CAPTCHA
                    String userCaptchaResponse = request.getParameter(WebConstants.CAPTCHA_RESPONSE);
                    boolean valid = captchaAgent.isValid(userCaptchaResponse);
                    System.out.println("valid? " + valid);
                    if (!valid) {
                        throw new ServiceException("Form is not submitted. CAPTCHA moderated.");
                    }

                    // Create a new student
                    studentService.create(student);
                    StudentDto studentDto = studentService.get(admissionNumber);
                    result.setSuccessful(true);
                    result.setStatusMessage("Thank you for using Student Self-Submit Service. "
                            + "Your informaton has been successfully submitted and you will be notified through email when it is approved.<br/>"
                            + "For any queries, please visit the corresponding branch.");
                    result.setReferenceNumber(String.valueOf(studentDto.getStudentId()));
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
                studentAttributesController.doDelete(request, response);
            }
        }
        return null;
    }
}
