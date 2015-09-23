package com.myschool.web.framework.filter;

import java.io.IOException;
import java.text.MessageFormat;

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

import com.myschool.user.constants.UserActivityConstant;
import com.myschool.web.framework.util.HttpUtil;

/**
 * The Class WebUserActivityLogFilter.
 */
public class WebUserActivityLogFilter implements Filter {

	/** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(WebUserActivityLogFilter.class);

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
    	if (servletRequest instanceof HttpServletRequest && servletResponse instanceof HttpServletResponse) {
    		HttpServletRequest request = (HttpServletRequest) servletRequest;
    		HttpServletResponse response = (HttpServletResponse) servletResponse;

    		String requestURI = request.getRequestURI();
    		String contextPath = request.getContextPath();
    		String slashedContext = contextPath + "/";
    		String actualRequest = requestURI.substring(requestURI.indexOf(slashedContext) + slashedContext.length(), requestURI.length());
    		
    		HttpSession session = HttpUtil.getExistingSession(request);
    		String sessionId = null;
    		if (session != null) {
    			sessionId = session.getId();
    		}
    		long startTime = System.currentTimeMillis();
    		long endTime = 0;
    		String error = null;
    		try {
    			// Pass it to the next filter to complete the request
    			filterChain.doFilter(request, response);
    		} catch (Exception exception) {
    			error = exception.getMessage();
    		} finally {
    			endTime = System.currentTimeMillis();
    			if (error == null) {
    				LOGGER.info(MessageFormat.format(UserActivityConstant.USER_ACTIVITY_SUCCESS, sessionId, actualRequest, (endTime - startTime)));
    			} else {
    				LOGGER.info(MessageFormat.format(UserActivityConstant.USER_ACTIVITY_FAILED, sessionId, actualRequest, error));
    			}
    		}
    	} else {
    		filterChain.doFilter(servletRequest, servletResponse);
    	}
    }

    /* (non-Javadoc)
     * @see javax.servlet.Filter#destroy()
     */
    @Override
    public void destroy() {
    }

}
