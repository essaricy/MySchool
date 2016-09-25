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

import com.myschool.user.assembler.ModuleDataAssembler;
import com.myschool.user.dto.UserAccessDto;
import com.myschool.user.dto.UserContext;
import com.myschool.web.application.constants.WebConstants;
import com.myschool.web.framework.util.HttpUtil;

/**
 * The Class AuthorizationFilter.
 */
public class AuthorizationFilter implements Filter {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = Logger.getLogger(AuthorizationFilter.class);

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

    	//LOGGER.debug("doFilter()");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String requestURI = request.getRequestURI();
        String contextPath = request.getContextPath();

        HttpSession session = HttpUtil.getExistingSession(request);
        //request.setAttribute(WebConstants.MYSCHOOL_PROFILE, session.getAttribute(WebConstants.MYSCHOOL_PROFILE));
    	Object userContext = session.getAttribute(WebConstants.USER_CONTEXT);
    	if (userContext instanceof UserContext) {
    		String slashedContext = contextPath + "/";
    		String actualRequest = requestURI.substring(requestURI.indexOf(slashedContext) + slashedContext.length(), requestURI.length());

    		// TODO Perform real authorization here
    		UserAccessDto pageAccessDetails = ModuleDataAssembler.getPageAccessDetails((UserContext) userContext, actualRequest);
    		LOGGER.debug(WebConstants.PAGE_ACCESS + " = " + pageAccessDetails);
    		request.setAttribute(WebConstants.PAGE_ACCESS, pageAccessDetails);

    		filterChain.doFilter(request, response);
    		
    	} else {
    		filterChain.doFilter(request, response);
    	}
    }

    /* (non-Javadoc)
     * @see javax.servlet.Filter#destroy()
     */
    @Override
    public void destroy() {
    }

}
