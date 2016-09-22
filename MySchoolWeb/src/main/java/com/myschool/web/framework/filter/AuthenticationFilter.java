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

import com.myschool.web.application.constants.WebConstants;
import com.myschool.web.framework.util.HttpUtil;

/**
 * The Class AuthenticationFilter.
 */
public class AuthenticationFilter implements Filter {

	/** The Constant LOGGER. */
	//private static final Logger LOGGER = Logger.getLogger(AuthenticationFilter.class);

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

        boolean anyExclude = Excludes.isAnyExclude(request);
        if (!anyExclude) {
            HttpSession session = HttpUtil.getExistingSession(request);
            if (session == null) {
                response.sendRedirect(contextPath + WebConstants.PUBLIC_DASHBOARD);
            } else {
                Object userContext = session.getAttribute(WebConstants.USER_CONTEXT);
                if (userContext == null) {
                    if (!requestURI.equals(contextPath + "/")) {
                        request.setAttribute(WebConstants.MESSAGE, "Your session has expired.");
                    }
                    request.getRequestDispatcher(WebConstants.PUBLIC_DASHBOARD).forward(request, response);
                } else {
                    filterChain.doFilter(request, response);
                }
            }
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
