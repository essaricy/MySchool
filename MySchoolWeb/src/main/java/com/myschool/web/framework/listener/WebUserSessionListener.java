package com.myschool.web.framework.listener;

import java.text.MessageFormat;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.myschool.acl.constant.SigninSecurityLevel;
import com.myschool.acl.dto.SigninSecurity;
import com.myschool.organization.service.OrganizationService;
import com.myschool.user.constants.UserActivityConstant;
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

    private OrganizationService organizationService;

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
            LOGGER.info(MessageFormat.format(UserActivityConstant.USER_SESSION_CREATED, sessionId));

            // Add the necessary attributes to the session
            initialize(session.getServletContext());

            session.setAttribute(WebConstants.ORGANIZATION, organizationService.getOrganization());
            session.setAttribute(WebConstants.ORGANIZATION_PREFERENCES, organizationService.getOrganizationPreferences());
            session.setAttribute(WebConstants.ORGANIZATION_MANIFEST, organizationService.getOrganizationManifest());

            System.out.println("WebConstants.ORGANIZATION=" + session.getAttribute(WebConstants.ORGANIZATION));
            System.out.println("WebConstants.ORGANIZATION_PREFERENCES=" + session.getAttribute(WebConstants.ORGANIZATION_PREFERENCES));
            System.out.println("WebConstants.ORGANIZATION_MANIFEST=" + session.getAttribute(WebConstants.ORGANIZATION_MANIFEST));

            // Attributes related to restrict multiple login failures.
            // TODO : Currently, the signin security is handled at the session level. Make to user level.
            SigninSecurity signinSecurity = new SigninSecurity();
            signinSecurity.setCurrentSecurityLevel(SigninSecurityLevel.CREDENTIALS);
            session.setAttribute(WebConstants.SIGNIN_SECURITY, signinSecurity);
        } catch (Exception exception) {
            LOGGER.fatal("Unable to create user session " + exception.getMessage(), exception);
        }
    }

    /* (non-Javadoc)
     * @see javax.servlet.http.HttpSessionListener#sessionDestroyed(javax.servlet.http.HttpSessionEvent)
     */
    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        HttpSession session = httpSessionEvent.getSession();
        String sessionId = session.getId();
        LOGGER.info(MessageFormat.format(UserActivityConstant.USER_SESSION_DESTROYED, sessionId));
    }

    /**
     * Initialize.
     *
     * @param servletContext the servlet context
     */
    public void initialize(ServletContext servletContext) {
        try {
            if (organizationService == null) {
                organizationService = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext).getBean(OrganizationService.class);
            }
        } catch (BeansException beansException) {
            LOGGER.fatal("Unable to lookup bean from web application context. " + beansException.getMessage(), beansException);
        } catch (IllegalStateException illegalStateException) {
            LOGGER.fatal("Unable to lookup bean from web application context. " + illegalStateException.getMessage(), illegalStateException);
        }
    }

}
