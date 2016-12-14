package com.myschool.web.student.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.myschool.student.dto.StudentDto;
import com.myschool.web.application.constants.WebConstants;
import com.myschool.web.framework.controller.ViewDelegationController;
import com.myschool.web.framework.util.HttpUtil;
import com.myschool.web.student.constants.StudentViewNames;

/**
 */
@Controller
@RequestMapping("student-self")
public class StudentSelfController {

    @RequestMapping(value = "admissionDetails")
    public ModelAndView admissionDetails(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        // Get the admission number from the session object.
        HttpSession session = HttpUtil.getExistingSession(request);
        Object studentObject = session.getAttribute(WebConstants.STUDENT);
        if (studentObject != null) {
            map.put(WebConstants.STUDENT, (StudentDto)studentObject);
        }
        return ViewDelegationController.delegateWholePageView(request, StudentViewNames.SELF_VIEW_ADMISSION_DETAILS, map);
    }

}
