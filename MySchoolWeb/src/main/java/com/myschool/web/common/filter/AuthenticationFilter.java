package com.myschool.web.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.myschool.user.assembler.ModuleDataAssembler;
import com.myschool.user.dto.UserAccessDto;
import com.myschool.user.dto.UserContext;
import com.myschool.web.application.constants.WebConstants;
import com.myschool.web.common.util.ViewDelegationController;

/**
 * The Class AuthenticationFilter.
 */
public class AuthenticationFilter implements Filter {

    /** The GLOBAL_EXCLUDES. */
    private static String[] GLOBAL_EXCLUDES = {
        // Common excludes
        "/themes", "/scripts", "/image", "/images", "/widgets", "/jcaptcha",
        // login excludes
        "/login.htm", "/logout.htm", "/launchLogin.htm",
    };

    /** The PUBLIC_EXCLUDES. */
    private static String[] PUBLIC_EXCLUDES = {
        // screens that login does not require
        "/noticeBoard", "/findus", "/download", "/portal",
        "/announcement",
        // Issue related excludes
        "/issue/launchIssue.htm", "/issue/doCreate.htm",
        // Portal excludes
        "/portal-employee",
        "/portal-student"
    };

    /** The FUNCTIONAL_URL. */
    private static String[] FUNCTIONAL_URL = {
        "admission-status/jsonList.htm",
        // Branch related excludes
        "/branch/jsonList.htm",
        // Class related excludes
        "/class/lookupClass.htm",
        "/school/jsonList.htm",
        "/jsonListBySchool.htm",
        "/registeredSubject/jsonList.htm",
        // designation related excludes
        "/designation/jsonList.htm",
        "/document/jsonList.htm",
        // Relationship Excludes
        "/relationship/jsonList.htm",
        // Employee related excludes
        "/employment/jsonList.htm",
        "/employee/verifiedEmployeesJSONList.htm",
        "/employee-attribute/launch.htm",
        "/employee-attribute/doValidate.htm",
        "/portal-employee/doDelete.htm",
        // Student related excludes
        "/student-attribute/launch.htm",
        "/student-attribute/doValidate.htm",
        "/portal-student/doDelete.htm",
        // Upload image excludes
        "/upload/uploadImage.htm",
        /*
        "/class/jsonListBySchool.htm",
        // Student related excludes
        "/student/launchParent.htm", "/student/validateFamilyMember.htm", "/student/launchSibling.htm",*/
        // Issue related excludes
        //"/issue/launchIssue.htm", "/issue/doCreate.htm"
    };

    /* (non-Javadoc)
     * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    /* (non-Javadoc)
     * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
            FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String requestURI = request.getRequestURI();
        String contextPath = request.getContextPath();

        if (!exclude(contextPath, requestURI)) {
            // User has used a bookmark. Bookmarks are not supported.
            HttpSession session = request.getSession();
            if (session == null) {
                //response.sendRedirect(contextPath);
                response.sendRedirect(contextPath + "/launchLogin.htm");
            } else {
                Object userContext = session.getAttribute(WebConstants.USER_CONTEXT);
                if (userContext == null) {
                    if (!requestURI.equals(contextPath + "/")) {
                        request.setAttribute(ViewDelegationController.ERROR_KEY, "Your login session has expired.");
                    }
                    request.getRequestDispatcher("/launchLogin.htm").forward(request, response);
                } else {
                    String slashedContext = contextPath + "/";
                    String actualRequest = requestURI.substring(requestURI.indexOf(slashedContext) + slashedContext.length(), requestURI.length());
                    // TODO Move this to authorization filter
                    UserAccessDto pageAccessDetails = ModuleDataAssembler.getPageAccessDetails((UserContext) userContext, actualRequest);
                    request.setAttribute("PAGE_ACCESS", pageAccessDetails);
                    filterChain.doFilter(request, response);
                    // Uncomment the below code to restrict access to the unauthorized users.
                    /*if (pageAccessDetails == null || !pageAccessDetails.isView()) {
                        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                    } else {
                        request.setAttribute("PAGE_ACCESS", pageAccessDetails);
                        filterChain.doFilter(request, response);
                    }*/
                }
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }

    /**
     * Exclude.
     *
     * @param contextPath the context path
     * @param requestURI the request uri
     * @return true, if successful
     */
    private static boolean exclude(String contextPath, String requestURI) {
        boolean exclude = false;
        String actualRequest = requestURI.substring(requestURI.indexOf(contextPath) + contextPath.length(), requestURI.length());
        for (String excludeRequest : GLOBAL_EXCLUDES) {
            if (actualRequest.equals(excludeRequest) || actualRequest.indexOf(excludeRequest) != -1) {
                exclude = true;
                break;
            }
        }
        if (!exclude) {
            for (String excludeRequest : PUBLIC_EXCLUDES) {
                if (actualRequest.equals(excludeRequest) || actualRequest.indexOf(excludeRequest) != -1) {
                    exclude = true;
                    break;
                }
            }
        }
        if (!exclude) {
            for (String excludeRequest : FUNCTIONAL_URL) {
                if (actualRequest.equals(excludeRequest) || actualRequest.indexOf(excludeRequest) != -1) {
                    exclude = true;
                    break;
                }
            }
        }
        return exclude;
    }

    /* (non-Javadoc)
     * @see javax.servlet.Filter#destroy()
     */
    @Override
    public void destroy() {
    }

}
