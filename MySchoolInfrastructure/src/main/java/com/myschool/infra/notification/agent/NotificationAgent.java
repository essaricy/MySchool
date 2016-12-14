package com.myschool.infra.notification.agent;

import java.io.File;
import java.util.List;

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
import com.myschool.infra.notification.Message;
import com.myschool.infra.notification.constants.NotificationConstants;
import com.myschool.infra.notification.reader.NotificationConfigReader;
import com.myschool.infra.template.agent.TemplateAgent;
import com.myschool.infra.template.exception.MessageGenerationException;
import com.myschool.infra.webserver.agent.WebServerAgent;
import com.myschool.notification.dto.Notification;
import com.myschool.notification.dto.NotificationConfig;
import com.myschool.notification.exception.NotificationException;
import com.myschool.organization.dto.Organization;
import com.myschool.template.dto.Template;
import com.myschool.token.dto.Token;

/**
 * The Class NotificationAgent.
 */
@Component
public class NotificationAgent extends AbstractAgent {

    /** The Constant LOGGER. */
    //private static final Logger LOGGER = Logger.getLogger(NotificationAgent.class);

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
        
        notificationConfig = notificationConfigReader.getNotificationConfig(configFile);
        System.out.println("notificationConfig=" + notificationConfig);
        List<Notification> notifications = notificationConfig.getNotifications();

        if (notifications != null && !notifications.isEmpty()) {
            for (Notification notification : notifications) {
                String id = notification.getId();
                Template template = templateAgent.getTemplate(id);
                notification.setTemplate(template);
            }
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.infra.Agent#validate()
     */
    @Override
    public void validate() throws AgentException {
        try {
            WebServerAgent webServerAgent = agents.getWebServerAgent();
            if (webServerAgent.isWebServer()) {
                Token token = new Token();
                // TODO send to "Application Support" group
                token.setConsumedBy("srikanthkumar.ragi@gmail.com");
                token.setNecessity(NotificationConstants.APPSERVER_STARTUP);
                token.setTokenId(dataGeneratorAgent.getUniqueId());
                sendNotification(null, null, token);
            }
        } catch (NotificationException notificationException) {
            throw new AgentException(notificationException.getMessage(), notificationException);
        }
    }

    public void sendNotification(Organization organization, Person sendTo,
            Token token) throws NotificationException {
        try {
            String emailId = token.getConsumedBy();

            Message message = new Message();
            message.setId(token.getTokenId());
            message.setSendTo(sendTo);

            Notification notification = getNotification(token.getNecessity());
            String output = templateAgent.generateText(notification.getTemplate(), message);

            emailServerAgent.sendEmail(emailId, notification.getTitle(), output);
        } catch (MessageGenerationException messageGenerationException) {
            throw new NotificationException(messageGenerationException.getMessage(), messageGenerationException);
        } catch (EmailException emailException) {
            throw new NotificationException(emailException.getMessage(), emailException);
        }
    }

    private Notification getNotification(String id) {
        List<Notification> notifications = notificationConfig.getNotifications();
        for (Notification notification : notifications) {
            if (id.equals(notification.getId())) {
                return notification;
            }
        }
        return null;
    }

    public Token getTokenDetails(String id, String emailId) {
        Token token = null;
        Notification notification = getNotification(id);
        if (notification != null) {
            token = new Token();
            token.setConsumedBy(emailId);
            token.setNecessity(notification.getId());
            token.setValidity(notification.getValidity());
        }
        return null;
    }

}
