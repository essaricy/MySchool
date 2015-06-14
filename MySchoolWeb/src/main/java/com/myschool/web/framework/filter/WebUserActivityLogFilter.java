package com.myschool.web.framework.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.myschool.user.service.UserService;

import org.apache.log4j.Logger;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.myschool.common.exception.ServiceException;
import com.myschool.user.assembler.UserSessionDataAssembler;
import com.myschool.user.dto.UserActivity;
import com.myschool.user.dto.UserSession;
import com.myschool.web.application.constants.WebConstants;
import com.myschool.web.framework.util.HttpUtil;

/**
 * The Class WebUserActivityLogFilter.
 */
public class WebUserActivityLogFilter implements Filter {

    //private static final String TRACE_ACTIVITIES = "TRACE_ACTIVITIES";

	/** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(WebUserActivityLogFilter.class);

    /** The user service. */
    private UserService userService;

    /* (non-Javadoc)
     * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    	userService = WebApplicationContextUtils.getRequiredWebApplicationContext(filterConfig.getServletContext()).getBean(UserService.class);
    }

    /* (non-Javadoc)
     * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
            FilterChain filterChain) throws IOException, ServletException {

    	//LOGGER.debug("enter");

    	try {
			if (servletRequest instanceof HttpServletRequest && servletResponse instanceof HttpServletResponse) {
				LOGGER.debug("doFilter()");
				HttpServletRequest request = (HttpServletRequest) servletRequest;
				//HttpServletResponse response = (HttpServletResponse) servletResponse;

				String requestURI = request.getRequestURI();
				String contextPath = request.getContextPath();
				String slashedContext = contextPath + "/";
				String actualRequest = requestURI.substring(requestURI.indexOf(slashedContext) + slashedContext.length(), requestURI.length());
				LOGGER.debug("actualRequest=" + actualRequest);

				HttpSession session = HttpUtil.getExistingSession(request);
				if (session == null) {
					LOGGER.error("############################################### session is null ####################################");
					// The session is being created. just wait for some time.
					//Thread.sleep(1000);
				} else {
					String sessionId = session.getId();
					UserSession userSession = (UserSession) session.getAttribute(WebConstants.USER_SESSION);
					if (userSession == null) {
						// This is the first request with for session. Need to create a user session object bound to session.
						LOGGER.debug("This is a first request with for session. Creating USER_SESSION object and binding to session.");
						userSession = HttpUtil.getUserSession(request);

						userSession.setSessionId(sessionId);
						userSession.setSessionStartTime(Calendar.getInstance().getTime());

						// Create user session record to the database
						if (userService == null) {
							throw new ServiceException("Cannot initialize userService object.");
						}
						// fix for multi-thread scenario
						synchronized (this) {
							userService.create(userSession);
							LOGGER.info("Created user session record for: " + sessionId);
							session.setAttribute(WebConstants.USER_SESSION, userSession);
						}
					}
				}
				// session is established. trace every request now.
				boolean globalOrPublicExclude = Excludes.isGlobalOrPublicExclude(request);
				if (!globalOrPublicExclude) {
					long startTime = System.currentTimeMillis();
					filterChain.doFilter(servletRequest, servletResponse);
					long endTime = System.currentTimeMillis();

					UserSession userSession = (UserSession) session.getAttribute(WebConstants.USER_SESSION);
					if (userSession != null) {
						List<UserActivity> userActivities = userSession.getUserActivities();
						if (userActivities == null) {
							userActivities = new ArrayList<UserActivity>();
							userSession.setUserActivities(userActivities);
						}
						UserActivity userActivity = UserSessionDataAssembler.createUserActivity(actualRequest, startTime, endTime);
						if (userActivity != null) {
							userActivities.add(userActivity);
							LOGGER.debug("User (" + userSession.getSessionId() + ") activity added: " + userActivity);
						}
					}
				} else {
					filterChain.doFilter(servletRequest, servletResponse);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage(), e);
		}
    }

    /* (non-Javadoc)
     * @see javax.servlet.Filter#destroy()
     */
    @Override
    public void destroy() {
    }

}
