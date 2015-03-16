package com.myschool.web.framework.filter;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 * The Class LoggingFilter.
 */
public class LoggingFilter implements Filter {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = Logger.getLogger(LoggingFilter.class);

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
    	if (servletRequest instanceof HttpServletRequest && servletResponse instanceof HttpServletResponse) {
    		HttpServletRequest request = (HttpServletRequest) servletRequest;
    		//HttpServletResponse response = (HttpServletResponse) servletResponse;

    		String requestURI = request.getRequestURI();

    		boolean publicExclude = Excludes.isPublicExclude(request);
    		if (!publicExclude) {
    	        LOGGER.debug("Requested: " + requestURI);
    	        Enumeration enumeration = request.getParameterNames();
    	        while (enumeration.hasMoreElements()) {
    	            Object nextElement = enumeration.nextElement();
    	            LOGGER.debug(nextElement + " = " + request.getParameter((String)nextElement));
    	        }
    		}
    	}
    	//System.out.println("LoggingFilter before filter");
    	filterChain.doFilter(servletRequest, servletResponse);
    	//System.out.println("LoggingFilter after filter");
    	//LOGGER.debug("exit");
    }

    /* (non-Javadoc)
     * @see javax.servlet.Filter#destroy()
     */
    @Override
    public void destroy() {
    }

}
