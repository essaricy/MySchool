package com.myschool.web.framework.listener;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;
import org.springframework.web.context.support.WebApplicationContextUtils;

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
		HttpSession session = httpSessionEvent.getSession();
		userService = WebApplicationContextUtils.getRequiredWebApplicationContext(session.getServletContext()).getBean(UserService.class);
		System.out.println("sessionCreated() " + userService);

		// Create session record
		/*HttpSession session = httpSessionEvent.getSession();
		UserSession userSession = new UserSession();
		userSession.setSessionId(session.getId());
		userSession.setSessionStartTime(new Date(session.getCreationTime()));
		session.setAttribute(WebConstants.USER_SESSION, userSession);*/
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpSessionListener#sessionDestroyed(javax.servlet.http.HttpSessionEvent)
	 */
	@Override
	public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
		// update session end time to the user session in the database.
		try {
			HttpSession session = httpSessionEvent.getSession();
			UserSession userSession = (UserSession) session.getAttribute(WebConstants.USER_SESSION);
			if (userSession != null) {
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
				System.out.println("%%%% 2 userService " + userService);
				if (userService != null) {
					boolean update = userService.update(userSession);
					System.out.println("Updated user session? " + update);
					if (update) {
						update = userService.createUserActivities(userSession.getSessionId(), userActivities);
						System.out.println("Created user activities? " + update);
					}
				}
			}
			System.out.println("User session ended : " + session.getId());
		} catch (ServiceException serviceException) {
			LOGGER.error("Unable to update WebUserActivityLog. " + serviceException.getMessage(), serviceException);
		}
	}

}
