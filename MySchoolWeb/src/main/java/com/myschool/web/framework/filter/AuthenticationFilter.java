package com.myschool.web.framework.filter;

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

import org.apache.log4j.Logger;

import com.myschool.web.application.constants.WebConstants;
import com.myschool.web.framework.controller.ViewDelegationController;

/**
 * The Class AuthenticationFilter.
 */
public class AuthenticationFilter implements Filter {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = Logger.getLogger(AuthenticationFilter.class);

    /** The Constant LAUNCH_LOGIN_HTM. */
    private static final String LAUNCH_LOGIN_HTM = "/launchLogin.htm";

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

        //LOGGER.debug("enter");
        boolean anyExclude = Excludes.isAnyExclude(request);
        if (!anyExclude) {
            HttpSession session = request.getSession();
            if (session == null) {
                //response.sendRedirect(contextPath);
                response.sendRedirect(contextPath + LAUNCH_LOGIN_HTM);
            } else {
                Object userContext = session.getAttribute(WebConstants.USER_CONTEXT);
                if (userContext == null) {
                    if (!requestURI.equals(contextPath + "/")) {
                    	
                        request.setAttribute(ViewDelegationController.ERROR_KEY, "Your login session has expired.");
                    }
                    request.getRequestDispatcher(LAUNCH_LOGIN_HTM).forward(request, response);
                } else {
                    /*String slashedContext = contextPath + "/";
                    String actualRequest = requestURI.substring(requestURI.indexOf(slashedContext) + slashedContext.length(), requestURI.length());
                    // TODO Move this to authorization filter
                    UserAccessDto pageAccessDetails = ModuleDataAssembler.getPageAccessDetails((UserContext) userContext, actualRequest);
                    request.setAttribute("PAGE_ACCESS", pageAccessDetails);*/
                    filterChain.doFilter(request, response);
                }
            }
        } else {
            filterChain.doFilter(request, response);
        }
        //LOGGER.debug("exit");
    }

    /* (non-Javadoc)
     * @see javax.servlet.Filter#destroy()
     */
    @Override
    public void destroy() {
    }

}
