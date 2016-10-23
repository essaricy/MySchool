package com.myschool.infra.notification.agent;

import java.io.File;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.common.dto.Person;
import com.myschool.common.exception.AgentException;
import com.myschool.common.exception.ConfigurationException;
import com.myschool.common.exception.EmailException;
import com.myschool.infra.agent.AbstractAgent;
import com.myschool.infra.application.Agents;
import com.myschool.infra.data.agent.DataGeneratorAgent;
import com.myschool.infra.email.agent.EmailServerAgent;
import com.myschool.infra.notification.reader.NotificationConfigReader;
import com.myschool.infra.template.agent.TemplateAgent;
import com.myschool.infra.template.exception.MessageGenerationException;
import com.myschool.infra.webserver.agent.WebServerAgent;
import com.myschool.notification.dto.NotificationConfig;
import com.myschool.notification.dto.NotificationTemplate;
import com.myschool.notification.exception.NotificationException;
import com.myschool.organization.dto.Organization;

/**
 * The Class NotificationAgent.
 */
@Component
public class NotificationAgent extends AbstractAgent {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(NotificationAgent.class);

    /** The email server agent. */
    @Autowired
    private EmailServerAgent emailServerAgent;

    /** The notification config reader. */
    @Autowired
    private NotificationConfigReader notificationConfigReader;

    /** The agents. */
    @Autowired
    private Agents agents;

    @Autowired
    private TemplateAgent templateAgent;

    @Autowired
    private DataGeneratorAgent dataGeneratorAgent;

    /** The notification config. */
    private NotificationConfig notificationConfig;

    /* (non-Javadoc)
     * @see com.myschool.infra.agent.Agent#loadConfiguration(java.io.File)
     */
    @Override
    public void loadConfiguration(File configFile) throws ConfigurationException {
        // TODO notification related configuration. like mailing lists etc. rss feeds
        // TODO for each type of template there will be a subject. maintain it here
        // TODO from address list configuration
        // TODO have a priority queue here.
    }

    /* (non-Javadoc)
     * @see com.myschool.infra.Agent#validate()
     */
    @Override
    public void validate() throws AgentException {
        try {
            WebServerAgent webServerAgent = agents.getWebServerAgent();
            if (webServerAgent.isWebServer()) {
                emailServerAgent.sendEmail("allstudents.in@gmail.com", "mail2srikanthkumar@gmail.com", "Appserver started", "MySchool Application server has been started.");
            }
        } catch (EmailException e) {
            e.printStackTrace();
        }
    }

    /**
     * Send change password request.
     *
     * @param organizationProfile the organization profile
     * @param emailId the email id
     * @param tokenId the token id
     * @throws NotificationException 
     */
    public void sendChangePasswordRequest(Organization organizationProfile,
            String emailId, String tokenId) throws NotificationException {
        try {
            Person sendTo = new Person();
            sendTo.setEmailId(emailId);
            sendTo.setFirstName("First");
            sendTo.setLastName("Last");
            sendTo.setMiddleName("Middle");

            String output = templateAgent.generateChangePasswordRequest(sendTo);
            emailServerAgent.sendEmail("MySchool", emailId, "Forgot Password?", output);
        } catch (MessageGenerationException messageGenerationException) {
            throw new NotificationException(messageGenerationException.getMessage(), messageGenerationException);
        } catch (EmailException emailException) {
            throw new NotificationException(emailException.getMessage(), emailException);
        }
    }

    /**
     * Gets the template.
     *
     * @param templateName the template name
     * @return the template
     */
    private NotificationTemplate getTemplate(String templateName) {
        List<NotificationTemplate> notificationTemplates = notificationConfig.getNotificationTemplates();
        for (NotificationTemplate notificationTemplate : notificationTemplates) {
            String name = notificationTemplate.getName();
            if (name.equals(templateName)) {
                return notificationTemplate;
            }
        }
        return null;
    }

}
