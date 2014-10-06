package com.myschool.web.student.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.myschool.application.service.HolidayService;
import com.myschool.student.dto.StudentDto;
import com.myschool.student.service.StudentService;
import com.myschool.user.service.LoginService;
import com.myschool.web.application.constants.WebConstants;
import com.myschool.web.common.util.ViewDelegationController;
import com.myschool.web.common.util.ViewErrorHandler;
import com.myschool.web.student.constants.StudentViewNames;

/**
 */
@Controller
@RequestMapping("student-self")
public class StudentSelfController {

    /** The student service. */
    @Autowired
    private StudentService studentService;

    /** The login service. */
    @Autowired
    private LoginService loginService;

    /** The holiday service. */
    @Autowired
    private HolidayService holidayService;

    /** The view error handler. */
    @Autowired
    private ViewErrorHandler viewErrorHandler;

    @RequestMapping(value = "admissionDetails")
    public ModelAndView admissionDetails(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        // Get the admission number from the session object.
        HttpSession session = request.getSession();
        Object studentObject = session.getAttribute(WebConstants.STUDENT);
        if (studentObject != null) {
            map.put(WebConstants.STUDENT, (StudentDto)studentObject);
        }
        return ViewDelegationController.delegateWholePageView(request, StudentViewNames.SELF_VIEW_ADMISSION_DETAILS, map);
    }

}
