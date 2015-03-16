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

/**
 * The Class AuthorizationFilter.
 */
public class AuthorizationFilter implements Filter {

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

    	//LOGGER.debug("enter");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String requestURI = request.getRequestURI();
        String contextPath = request.getContextPath();

        boolean anyExclude = Excludes.isAnyExclude(request);
        if (!anyExclude) {
        	HttpSession session = request.getSession();
        	Object userContext = session.getAttribute(WebConstants.USER_CONTEXT);
        	String slashedContext = contextPath + "/";
        	String actualRequest = requestURI.substring(requestURI.indexOf(slashedContext) + slashedContext.length(), requestURI.length());
        	//System.out.println("**** actualRequest " + actualRequest);

        	// Uncomment the below code to restrict access to the unauthorized users.
            /*if (pageAccessDetails == null || !pageAccessDetails.isView()) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            } else {
                request.setAttribute("PAGE_ACCESS", pageAccessDetails);
                filterChain.doFilter(request, response);
            }*/
            filterChain.doFilter(request, response);
            //System.out.println("**** after do filter " + actualRequest);
            UserAccessDto pageAccessDetails = ModuleDataAssembler.getPageAccessDetails((UserContext) userContext, actualRequest);
            request.setAttribute("PAGE_ACCESS", pageAccessDetails);
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
