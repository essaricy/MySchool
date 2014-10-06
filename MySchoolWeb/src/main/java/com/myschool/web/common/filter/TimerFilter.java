package com.myschool.web.common.filter;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

/**
 * The Class TimerFilter.
 */
public class TimerFilter implements Filter {

    private static final Logger LOGGER = Logger.getLogger(TimerFilter.class);

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
        long start = System.currentTimeMillis();
        LOGGER.debug("============================ REQUEST START ============================ ");
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        LOGGER.debug("Requested: " + httpServletRequest.getRequestURI());
        Enumeration enumeration = httpServletRequest.getParameterNames();
        while (enumeration.hasMoreElements()) {
            Object nextElement = enumeration.nextElement();
            LOGGER.debug("#: " + nextElement + " = " + httpServletRequest.getParameter((String)nextElement));
        }
        filterChain.doFilter(servletRequest, servletResponse);
        long end = System.currentTimeMillis();
        LOGGER.debug("============================ REQUEST END ============================ ");
        LOGGER.debug("TIME TAKEN TO COMPLETE YOUR REQUEST IS: " + (end-start) + " ms.");
    }

    /* (non-Javadoc)
     * @see javax.servlet.Filter#destroy()
     */
    @Override
    public void destroy() {
    }

}
