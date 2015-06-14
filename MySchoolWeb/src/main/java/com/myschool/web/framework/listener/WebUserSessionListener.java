package com.myschool.web.framework.listener;

import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.myschool.application.dto.MySchoolProfileDto;
import com.myschool.application.dto.OrganizationProfileDto;
import com.myschool.application.service.ProfileService;
import com.myschool.common.exception.ServiceException;
import com.myschool.user.dto.UserActivity;
import com.myschool.user.dto.UserSession;
import com.myschool.user.service.UserService;
import com.myschool.web.application.constants.WebConstants;

/**
 * The listener interface for receiving webUserSession events.
 * The class that is interested in processing a webUserSession
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addWebUserSessionListener<code> method. When
 * the webUserSession event occurs, that object's appropriate
 * method is invoked.
 *
 * @see WebUserSessionEvent
 */
public class WebUserSessionListener implements HttpSessionListener {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = Logger.getLogger(WebUserSessionListener.class);

	/** The user service. */
	private UserService userService;

	/** The profile service. */
	private ProfileService profileService;

	/**
	 * Instantiates a new web user session listener.
	 */
	public WebUserSessionListener() {
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpSessionListener#sessionCreated(javax.servlet.http.HttpSessionEvent)
	 */
	@Override
	public void sessionCreated(HttpSessionEvent httpSessionEvent) {
		try {
			HttpSession session = httpSessionEvent.getSession();
			String sessionId = session.getId();
			initialize(session.getServletContext());
			LOGGER.debug("Session Created: " + sessionId);

			OrganizationProfileDto organizationProfile = profileService.getOrganizationProfile();
	        MySchoolProfileDto mySchoolProfile = profileService.getMySchoolProfile();
			session.setAttribute(WebConstants.ORGANIZATION_PROFILE, organizationProfile);
	        session.setAttribute(WebConstants.MYSCHOOL_PROFILE, mySchoolProfile);
	        LOGGER.debug("Added ORGANIZATION_PROFILE and MYSCHOOL_PROFILE to session: " + sessionId);
		} catch (Exception exception) {
			LOGGER.error("Unable to create user session. " + exception.getMessage(), exception);
		}
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpSessionListener#sessionDestroyed(javax.servlet.http.HttpSessionEvent)
	 */
	@Override
	public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
		// update session end time to the user session in the database.
		try {
			HttpSession session = httpSessionEvent.getSession();
			String sessionId = session.getId();
			UserSession userSession = (UserSession) session.getAttribute(WebConstants.USER_SESSION);
			if (userSession != null) {
				// The session can be destroyed when a user ends the session or container ends the session.
				// User ends the session when he logs out from the application.
				// Container ends the session when the session is idle for maxInactiveInterval.
				// So, instead of updating session end time to the current time, update it to the timestamp of last request.
				List<UserActivity> userActivities = userSession.getUserActivities();
				if (userActivities == null || userActivities.isEmpty()) {
					userSession.setSessionEndTime(new Date(session.getLastAccessedTime()));
				} else {
					UserActivity userActivity = userActivities.get(0);
					if (userActivity == null || userActivity.getRequestedTime() == null) {
						userSession.setSessionEndTime(new Date(session.getLastAccessedTime()));
					} else {
						userSession.setSessionEndTime(userActivity.getRequestedTime());
					}
				}
				// update all the user activities to the database.
				initialize(session.getServletContext());
				if (userService == null) {
					throw new ServiceException("Cannot initialize userService object.");
				}
				boolean update = userService.update(userSession);
				if (!update) {
					throw new ServiceException("Unable to update user session. UserSession=" + userSession);
				}
				update = userService.createUserActivities(userSession.getSessionId(), userActivities);
				if (!update) {
					throw new ServiceException("Unable to update user activities. UserSession=" + userSession);
				}
				LOGGER.debug("User session and activities have been updated successfully for session " + sessionId);
			}
			LOGGER.debug("Session Destroyed: " + sessionId);
		} catch (ServiceException serviceException) {
			LOGGER.error("Unable to update WebUserActivityLog. " + serviceException.getMessage(), serviceException);
		}
	}

	/**
	 * Initialize.
	 *
	 * @param servletContext the servlet context
	 */
	public void initialize(ServletContext servletContext) {
		try {
			if (userService == null) {
				userService = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext).getBean(UserService.class);
			}
			if (profileService == null) {
				profileService = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext).getBean(ProfileService.class);
			}
		} catch (BeansException beansException) {
			LOGGER.error("Unable to lookup bean from web application context. " + beansException.getMessage(), beansException);
		} catch (IllegalStateException illegalStateException) {
			LOGGER.error("Unable to lookup bean from web application context. " + illegalStateException.getMessage(), illegalStateException);
		}
	}

}
