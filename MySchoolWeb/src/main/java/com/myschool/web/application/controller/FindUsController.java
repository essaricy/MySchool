package com.myschool.web.application.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.myschool.application.service.ProfileService;
import com.myschool.branch.service.BranchService;
import com.myschool.school.service.SchoolService;
import com.myschool.web.application.constants.ApplicationViewNames;
import com.myschool.web.framework.controller.ViewDelegationController;

/**
 */
@Controller
@RequestMapping("findus")
public class FindUsController {

    /** The profile service. */
    @Autowired
    private ProfileService profileService;

    /** The branch service. */
    @Autowired
    private BranchService branchService;

    /** The school service. */
    @Autowired
    private SchoolService schoolService;

    /**
     * Contact us.
     *
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value="contactUs")
    public ModelAndView contactUs(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("organization", profileService.getOrganizationProfile());
        map.put("branches", branchService.getAll());
        map.put("schools", schoolService.getAll());
        return ViewDelegationController.delegateWholePageView(request, ApplicationViewNames.VIEW_CONTACT_INFO, map);
    }

    /**
     * Locate us.
     *
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value="locateUs")
    public ModelAndView locateUs(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("organization", profileService.getOrganizationProfile());
        map.put("branches", branchService.getAll());
        map.put("schools", schoolService.getAll());
        return ViewDelegationController.delegateWholePageView(request, ApplicationViewNames.VIEW_LOCATE_US, map);
    }

}
