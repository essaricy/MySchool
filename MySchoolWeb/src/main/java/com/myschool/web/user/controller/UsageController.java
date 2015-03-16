package com.myschool.web.user.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.myschool.web.framework.controller.ViewDelegationController;
import com.myschool.web.user.constants.UserViewNames;

/**
 * The Class UserController.
 */
@Controller
@RequestMapping("usage")
public class UsageController {

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
        return ViewDelegationController.delegateWholePageView(request, UserViewNames.VIEW_USAGE_STATISTICS);
    }

}