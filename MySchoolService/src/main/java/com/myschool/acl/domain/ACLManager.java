package com.myschool.acl.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.common.dto.Person;
import com.myschool.infra.notification.agent.NotificationAgent;
import com.myschool.infra.notification.constants.NotificationConstants;
import com.myschool.notification.exception.NotificationException;
import com.myschool.organization.dao.OrganizationManager;
import com.myschool.organization.dto.Organization;
import com.myschool.token.domain.TokenManager;
import com.myschool.token.dto.Token;
import com.quasar.core.exception.DataException;
import com.quasar.core.util.StringUtil;

/**
 * The Class ACLManager.
 */
@Component
public class ACLManager {

    /** The token manager. */
    @Autowired
    private TokenManager tokenManager;

    /** The notification agent. */
    @Autowired
    private NotificationAgent notificationAgent;

    /** The organization manager. */
    @Autowired
    private OrganizationManager organizationManager;

    /**
     * Process change password request.
     *
     * @param emailId the email id
     * @return true, if successful
     * @throws DataException the data exception
     */
    public boolean processChangePasswordRequest(String emailId) throws DataException {
        try {
            // Validate email id
            if (StringUtil.isNullOrBlank(emailId)) {
                throw new DataException("EmailId is required");
            }
            // TODO check if the email id is linked to any of the account
            Token token = notificationAgent.getTokenDetails(NotificationConstants.CHANGE_PASSWORD_REQUEST, emailId);
            boolean create = tokenManager.create(token);
            if (!create) {
                throw new DataException("Unable to create token now.");
            }
            Organization organization = organizationManager.getOrganization();
            Person sendTo = new Person();
            sendTo.setEmailId(emailId);
            sendTo.setFirstName("First");
            sendTo.setLastName("Last");
            sendTo.setMiddleName("Middle");

            notificationAgent.sendNotification(organization, sendTo, token);
        } catch (NotificationException notificationException) {
            throw new DataException(notificationException.getMessage(), notificationException);
        }
        return true;
    }

}
