package com.myschool.sautil.xml;

import java.io.File;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.application.service.ProfileService;
import com.myschool.common.exception.ConfigurationException;
import com.myschool.infra.application.ApplicationLoader;
import com.myschool.infra.filesystem.agent.NotificationTemplatesFileSystem;
import com.myschool.infra.filesystem.util.FileUtil;
import com.myschool.infra.middleware.dto.MessageDto;
import com.myschool.infra.oxo.agent.OxoAgent;
import com.myschool.notification.constants.NotificationEndPoint;
import com.myschool.notification.constants.NotificationMode;
import com.myschool.notification.constants.NotificationType;
import com.myschool.notification.dto.NotificationTemplateDto;
import com.myschool.sautil.base.StandAloneUtility;
import com.myschool.test.notification.NotificationTestDataFactory;

@Component
public class NotificationTestXmlGenerator extends StandAloneUtility {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(NotificationTestXmlGenerator.class);

    @Autowired
    private ApplicationLoader applicationLoader;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private NotificationTemplatesFileSystem notificationTemplatesFileSystem;

    @Autowired
    private OxoAgent oxoAgent;

    /* (non-Javadoc)
     * @see com.myschool.sautil.base.StandAloneUtility#validateParameters()
     */
    public void validateParameters() throws ConfigurationException {
    }

    /* (non-Javadoc)
     * @see com.myschool.sautil.base.StandAloneUtility#startProcess()
     */
    @Override
    public void startProcess() throws Exception {
        LOGGER.info("Starting generating Notification Test XML documents.");
        List<NotificationTemplateDto> notificationTemplates = notificationTemplatesFileSystem.getNotificationTemplates();
        if (notificationTemplates != null && !notificationTemplates.isEmpty()) {
            MessageDto message = new MessageDto();
            message.setOrganizationProfile(profileService.getOrganizationProfile());
            message.setToPerson(NotificationTestDataFactory.getToPerson());
            message.setMySchool(applicationLoader.getMySchool());

            for (NotificationTemplateDto notificationTemplate : notificationTemplates) {
                NotificationEndPoint notificationEndPoint = notificationTemplate.getNotificationEndPoint();
                NotificationMode notificationMode = notificationTemplate.getNotificationMode();
                NotificationType notificationType = notificationTemplate.getNotificationType();
                File testFile = notificationTemplate.getTestFile();

                if (testFile == null) {
                    System.err.println("There is no test file for "
                                + notificationEndPoint + ", " + notificationMode + ", "
                                + notificationType + ". File location=" + testFile);
                    LOGGER.warn("There is no test file for "
                            + notificationEndPoint + ", " + notificationMode + ", "
                            + notificationType + ". File location=" + testFile);
                } else {
                    message.setContent(NotificationTestDataFactory.getObject(notificationEndPoint, notificationMode, notificationType));
                    message.setNotificationEndPoint(notificationEndPoint);
                    message.setNotificationMode(notificationMode);
                    message.setNotificationType(notificationType);
                    String xmlContent = oxoAgent.toXml(message);
                    if (xmlContent == null) {
                        System.err.println("NO XML content was generated for "
                                + notificationEndPoint + ", " + notificationMode + ", "
                                + notificationType + ". File location=" + testFile);
                        LOGGER.warn("There is no test file for "
                                + notificationEndPoint + ", " + notificationMode + ", "
                                + notificationType + ". File location=" + testFile);
                    } else {
                        FileUtil.writeToFile(testFile, xmlContent.getBytes());
                        System.out.println("Test file has been successfully created for "
                                + notificationEndPoint + ", " + notificationMode + ", "
                                + notificationType + ". File location=" + testFile);
                        LOGGER.info("Test file has been successfully created for "
                                + notificationEndPoint + ", " + notificationMode + ", "
                                + notificationType + ". File location=" + testFile);
                    }
                }
            }
        }
        LOGGER.info("Completed generating Test XML files for all notification templates.");
    }

    /* (non-Javadoc)
     * @see com.myschool.sautil.base.StandAloneUtility#getUsageText()
     */
    @Override
    public String getUsageText() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("Usage: ").append(this.getClass().getName()).append(" [OPTIONS] \n");
        buffer.append("Valid Options are\n");
        buffer.append(String.format(PADDING_WIDTH, OPTION_HELP)).append("For Help\n");
        return buffer.toString();
    }

}
