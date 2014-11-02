package com.myschool.web.application.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.myschool.academic.service.AcademicService;
import com.myschool.application.dto.MySchoolProfileDto;
import com.myschool.application.dto.OrganizationProfileDto;
import com.myschool.application.service.ProfileService;
import com.myschool.common.dto.ResultDto;
import com.myschool.common.exception.DataException;
import com.myschool.common.exception.ServiceException;
import com.myschool.common.util.ConversionUtil;
import com.myschool.web.application.constants.ApplicationViewNames;
import com.myschool.web.application.constants.WebConstants;
import com.myschool.web.common.util.HttpUtil;
import com.myschool.web.common.util.ViewDelegationController;
import com.myschool.web.common.util.ViewErrorHandler;

/**
 * The Class BranchController.
 */
@Controller
@RequestMapping("profile")
public class ProfileController {

    /** The profile service. */
    @Autowired
    private ProfileService profileService;

    /** The academic service. */
    @Autowired
    private AcademicService academicService;

    /** The view error handler. */
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
    @RequestMapping(value = "list")
    public ModelAndView showOrg(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        // get Profile details
        map.put("OrganizationProfile", profileService.getOrganizationProfile());
        map.put("MySchoolProfile", profileService.getMySchoolProfile());
        return ViewDelegationController.delegateWholePageView(request, ApplicationViewNames.MANAGE_PROFILE, map);
    }

    /**
     * Update organization profile.
     * 
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value = "updateOrganizationProfile")
    public ModelAndView updateOrganizationProfile(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        ResultDto result = new ResultDto();
        try {
            OrganizationProfileDto organizationProfile = validateAndGetOrganizationProfile(request);
            result.setSuccessful(profileService.update(organizationProfile));
            result.setStatusMessage("Organization Profile has been updated successfully.");
        } catch (ServiceException serviceException) {
            result.setStatusMessage(serviceException.getMessage());
        } finally {
            HttpUtil.writeAsJson(response, result);
        }
        return null;
    }

    /**
     * Update my school profile.
     * 
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value = "updateMySchoolProfile")
    public ModelAndView updateMySchoolProfile(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        ResultDto result = new ResultDto();
        try {
            MySchoolProfileDto mySchoolProfile = validateAndGetMySchoolProfile(request);
            result.setSuccessful(profileService.update(mySchoolProfile));
            result.setStatusMessage("MySchool Profile has been updated successfully.");

            HttpSession session = request.getSession();
            session.setAttribute(WebConstants.MYSCHOOL_PROFILE, mySchoolProfile);
        } catch (ServiceException serviceException) {
            result.setStatusMessage(serviceException.getMessage());
        } finally {
            HttpUtil.writeAsJson(response, result);
        }
        return null;
    }


    /**
     * Validate and get organization profile.
     * 
     * @param request the request
     * @return the organization profile dto
     * @throws DataException the data exception
     */
    private OrganizationProfileDto validateAndGetOrganizationProfile(
            HttpServletRequest request) throws DataException {
        OrganizationProfileDto organizationProfile = new OrganizationProfileDto();
        organizationProfile.setAddress(request.getParameter("address"));
        organizationProfile.setPhoneNumber(request.getParameter("phoneNumber"));
        organizationProfile.setFaxNumber(request.getParameter("faxNumber"));
        return organizationProfile;
    }

    /**
     * Validate and get my school profile.
     * 
     * @param request the request
     * @return the my school profile dto
     * @throws DataException the data exception
     */
    private MySchoolProfileDto validateAndGetMySchoolProfile(
            HttpServletRequest request) throws DataException {
        MySchoolProfileDto mySchoolProfile = new MySchoolProfileDto();
        mySchoolProfile.setAyeInProgress(ConversionUtil.toBoolean(request.getParameter("ayeInProgress")));
        mySchoolProfile.setEmailActive(ConversionUtil.toBoolean(request.getParameter("emailActive")));
        mySchoolProfile.setEmailEmployees(ConversionUtil.toBoolean(request.getParameter("emailEmployees")));
        mySchoolProfile.setEmailStudents(ConversionUtil.toBoolean(request.getParameter("emailStudents")));
        mySchoolProfile.setSmsActive(ConversionUtil.toBoolean(request.getParameter("smsActive")));
        mySchoolProfile.setSmsEmployees(ConversionUtil.toBoolean(request.getParameter("smsEmployees")));
        mySchoolProfile.setSmsStudents(ConversionUtil.toBoolean(request.getParameter("smsStudents")));
        mySchoolProfile.setUseMenuIcons(ConversionUtil.toBoolean(request.getParameter("useMenuIcons")));
        return mySchoolProfile;
    }

}
