package com.myschool.web.organization.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.myschool.common.dto.ResultDto;
import com.myschool.common.exception.DataException;
import com.myschool.common.exception.ServiceException;
import com.myschool.common.util.ConversionUtil;
import com.myschool.organization.dto.Organization;
import com.myschool.organization.dto.OrganizationManifest;
import com.myschool.organization.dto.OrganizationPreferences;
import com.myschool.organization.service.OrganizationService;
import com.myschool.web.application.constants.OrganizationViewNames;
import com.myschool.web.application.constants.WebConstants;
import com.myschool.web.framework.controller.ViewDelegationController;
import com.myschool.web.framework.util.HttpUtil;

/**
 * The Class OrganizationController.
 */
@Controller
@RequestMapping("organization")
public class OrganizationController {

    /** The organization service. */
    @Autowired
    private OrganizationService organizationService;

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

        // Get organization details.
        Organization organization = organizationService.getOrganization();
        OrganizationPreferences organizationPreferences = organizationService.getOrganizationPreferences();
        OrganizationManifest organizationManifest = organizationService.getOrganizationManifest();

        // Put them to the map for view
        map.put("Organization", organization);
        map.put("OrganizationPreferences", organizationPreferences);
        map.put("OrganizationManifest", organizationManifest);

        // As the data is already retrieved, update the session variable for accuracy and to address concurrency a bit.
        HttpSession session = HttpUtil.getExistingSession(request);
        session.setAttribute(WebConstants.ORGANIZATION, organization);
        session.setAttribute(WebConstants.ORGANIZATION_PREFERENCES, organizationPreferences);
        session.setAttribute(WebConstants.ORGANIZATION_MANIFEST, organizationManifest);

        return ViewDelegationController.delegateWholePageView(request, OrganizationViewNames.MANAGE_ORGANIZATION, map);
    }

    /**
     * Update.
     *
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value = "update")
    public ModelAndView update(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        ResultDto result = new ResultDto();
        try {
            Organization organization = validateAndGetOrganization(request);
            boolean update = organizationService.update(organization);
            if (update) {
                result.setSuccessful(update);
                result.setStatusMessage("Organization details has been updated successfully.");
                // Update session variable.
                HttpSession session = HttpUtil.getExistingSession(request);
                session.setAttribute(WebConstants.ORGANIZATION, organization);
            }
        } catch (ServiceException serviceException) {
            result.setStatusMessage(serviceException.getMessage());
        } finally {
            HttpUtil.writeAsJson(response, result);
        }
        return null;
    }

    /**
     * Update notification preferences.
     *
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value = "updateNotificationPreferences")
    public ModelAndView updateNotificationPreferences(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        ResultDto result = new ResultDto();
        try {
            OrganizationPreferences organizationPreferences = validateAndGetOrganizationPreferences(request);
            boolean updated = organizationService.updateNotificationSettings(organizationPreferences);
            if (updated) {
                result.setStatusMessage("Notification Preferences have been updated successfully");
                HttpSession session = HttpUtil.getExistingSession(request);
                session.setAttribute(WebConstants.ORGANIZATION_PREFERENCES, organizationPreferences);
            }
        } catch (ServiceException serviceException) {
            result.setStatusMessage(serviceException.getMessage());
        } finally {
            HttpUtil.writeAsJson(response, result);
        }
        return null;
    }

    /**
     * Update display preferences.
     *
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value = "updateDisplayPreferences")
    public ModelAndView updateDisplayPreferences(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        ResultDto result = new ResultDto();
        try {
            OrganizationPreferences organizationPreferences = validateAndGetOrganizationPreferences(request);
            boolean updated = organizationService.updateDisplaySettings(organizationPreferences);
            if (updated) {
                result.setStatusMessage("Display Preferences have been updated successfully");
                HttpSession session = HttpUtil.getExistingSession(request);
                session.setAttribute(WebConstants.ORGANIZATION_PREFERENCES, organizationPreferences);
            }
        } catch (ServiceException serviceException) {
            result.setStatusMessage(serviceException.getMessage());
        } finally {
            HttpUtil.writeAsJson(response, result);
        }
        return null;
    }

    /**
     * Update self submit preferences.
     *
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value = "updateSelfSubmitPreferences")
    public ModelAndView updateSelfSubmitPreferences(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        ResultDto result = new ResultDto();
        try {
            OrganizationPreferences organizationPreferences = validateAndGetOrganizationPreferences(request);
            boolean updated = organizationService.updateSelfServiceSettings(organizationPreferences);
            if (updated) {
                result.setStatusMessage("Self-Submit-Service Preferences have been updated successfully");
                HttpSession session = HttpUtil.getExistingSession(request);
                session.setAttribute(WebConstants.ORGANIZATION_PREFERENCES, organizationPreferences);
            }
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
    private Organization validateAndGetOrganization(
            HttpServletRequest request) throws DataException {
        Organization organization = new Organization();
        organization.setAddress(request.getParameter("Address"));
        organization.setPhoneNumber(request.getParameter("PhoneNumber"));
        organization.setFaxNumber(request.getParameter("FaxNumber"));
        return organization;
    }

    /**
     * Validate and get organization preferences.
     *
     * @param request the request
     * @return the organization preferences
     * @throws DataException the data exception
     */
    private OrganizationPreferences validateAndGetOrganizationPreferences(
            HttpServletRequest request) throws DataException {
        OrganizationPreferences organizationPreferences = new OrganizationPreferences();
        // Notification preferences
        organizationPreferences.setEmailActive(ConversionUtil.toBoolean(request.getParameter("EmailActive")));
        organizationPreferences.setEmailEmployees(ConversionUtil.toBoolean(request.getParameter("EmailEmployees")));
        organizationPreferences.setEmailStudents(ConversionUtil.toBoolean(request.getParameter("EmailStudents")));
        organizationPreferences.setSmsActive(ConversionUtil.toBoolean(request.getParameter("SMSActive")));
        organizationPreferences.setSmsEmployees(ConversionUtil.toBoolean(request.getParameter("SMSEmployees")));
        organizationPreferences.setSmsStudents(ConversionUtil.toBoolean(request.getParameter("SMSStudents")));
        // Display Preferences
        organizationPreferences.setUseMenuIcons(ConversionUtil.toBoolean(request.getParameter("UseMenuIcons")));
        organizationPreferences.setDefaultTheme(request.getParameter("DefaultTheme"));
        organizationPreferences.setDefaultGallery(request.getParameter("DefaultGallery"));
        // Self submit controls
        organizationPreferences.setUseEmployeeSelfSubmit(ConversionUtil.toBoolean(request.getParameter("UseEmployeeSelfSubmit")));
        organizationPreferences.setUseStudentSelfSubmit(ConversionUtil.toBoolean(request.getParameter("UseStudentSelfSubmit")));
        return organizationPreferences;
    }

}
