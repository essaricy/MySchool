package com.myschool.web.framework.filter;

import java.io.IOException;
import java.util.ArrayList;
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

import org.apache.log4j.Logger;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.myschool.common.exception.ServiceException;
import com.myschool.user.assembler.UserSessionDataAssembler;
import com.myschool.user.dto.UserActivity;
import com.myschool.user.dto.UserSession;
import com.myschool.user.service.UserService;
import com.myschool.web.application.constants.WebConstants;
import com.myschool.web.framework.util.HttpUtil;

/**
 * The Class WebUserActivityLogFilter.
 */
public class WebUserActivityLogFilter implements Filter {

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
				LOGGER.debug("doFilter(");
				HttpServletRequest request = (HttpServletRequest) servletRequest;
				//HttpServletResponse response = (HttpServletResponse) servletResponse;

				String requestURI = request.getRequestURI();
				String contextPath = request.getContextPath();
				String slashedContext = contextPath + "/";
				String actualRequest = requestURI.substring(requestURI.indexOf(slashedContext) + slashedContext.length(), requestURI.length());
				LOGGER.debug("actualRequest=" + actualRequest);

				HttpSession session = request.getSession(true);

				LOGGER.debug("session.isNew()? " + session.isNew());
				if (session.isNew()) {
					session.setAttribute("TRACE_ACTIVITIES", Boolean.FALSE);
					UserSession userSession = HttpUtil.getUserSession(request);
					LOGGER.debug("1userSession=" + userSession);
					session.setAttribute(WebConstants.USER_SESSION, userSession);
					LOGGER.debug("User session started : " + userSession.getSessionId());
					//filterChain.doFilter(servletRequest, servletResponse);
					// save user session to the database.
					try {
						if (userService != null) {
							userService.create(userSession);
							session.setAttribute("TRACE_ACTIVITIES", Boolean.TRUE);
						}
					} catch (ServiceException serviceException) {
						LOGGER.error("Unable to created user session. Further Activities will not be logged" + serviceException.getMessage(), serviceException);
					}
				}

				// session is established. trace every request now.
				Boolean trace = (Boolean) session.getAttribute("TRACE_ACTIVITIES");
				/*boolean trace = false;
				if (traceBoolean != null) {
					trace = traceBoolean.booleanValue();
				}*/
				boolean globalOrPublicExclude = Excludes.isGlobalOrPublicExclude(request);
				LOGGER.debug("TRACE_ACTIVITIES ? " + trace + ", globalOrPublicExclude ? " + globalOrPublicExclude);
				System.out.println("TRACE_ACTIVITIES ? " + trace + ", globalOrPublicExclude ? " + globalOrPublicExclude);
				if (!globalOrPublicExclude && Boolean.TRUE.equals(trace)) {
					long startTime = System.currentTimeMillis();
					LOGGER.debug("Before forwarding to doFilter");
					filterChain.doFilter(servletRequest, servletResponse);
					LOGGER.debug("after forwarding to doFilter");
					long endTime = System.currentTimeMillis();

					System.out.println("session is null? " + (session==null));
					LOGGER.debug("session is null? " + (session==null));
					UserSession userSession = (UserSession) session.getAttribute(WebConstants.USER_SESSION);
					LOGGER.debug("userSession = " + (userSession==null));
					if (userSession != null) {
						System.out.println("userSession is not null.");
						List<UserActivity> userActivities = userSession.getUserActivities();
						LOGGER.debug("userSession = " + (userActivities==null));
						if (userActivities == null) {
							System.out.println("userActivities is null.");
							userActivities = new ArrayList<UserActivity>();
							userSession.setUserActivities(userActivities);
						}
						UserActivity userActivity = UserSessionDataAssembler.createUserActivity(actualRequest, startTime, endTime);
						LOGGER.debug("userActivity = " + (userActivities==null));
						if (userActivity != null) {
							System.out.println("userActivity is not null.");
							userActivities.add(userActivity);
							LOGGER.debug("User (" + userSession.getSessionId() + ") activity added: " + userActivity);
						}
					}
					//System.out.println("User (" + userSession.getSessionId() + ") activity added: " + userActivity);
				} else {
					LOGGER.debug("in else Before forwarding to doFilter");
					filterChain.doFilter(servletRequest, servletResponse);
					LOGGER.debug("in else after forwarding to doFilter");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage(), e);
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
