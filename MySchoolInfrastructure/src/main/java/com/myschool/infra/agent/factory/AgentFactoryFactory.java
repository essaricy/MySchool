package com.myschool.infra.agent.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.infra.application.constants.AgentConstants;
import com.myschool.infra.cache.factory.CacheAgentFactory;
import com.myschool.infra.captcha.factory.CaptchaAgentFactory;
import com.myschool.infra.data.factory.DataGeneratorAgentFactory;
import com.myschool.infra.database.factory.DatabaseAgentFactory;
import com.myschool.infra.email.factory.EmailServerAgentFactory;
import com.myschool.infra.filesystem.factory.FileSystemAgentFactory;
import com.myschool.infra.graph.factory.GraphAgentFactory;
import com.myschool.infra.image.factory.ImageScalingAgentFactory;
import com.myschool.infra.notification.factory.NotificationAgentFactory;
import com.myschool.infra.ojo.factory.OjoAgentFactory;
import com.myschool.infra.oxo.factory.OxoAgentFactory;
import com.myschool.infra.report.factory.ReportAgentFactory;
import com.myschool.infra.scheduler.agent.SchedulerAgentFactory;
import com.myschool.infra.sms.factory.SmsServerAgentFactory;
import com.myschool.infra.storage.factory.StorageAccessAgentFactory;
import com.myschool.infra.template.factory.TemplateAgentFactory;
import com.myschool.infra.webserver.factory.WebserverAgentFactory;

/**
 * A factory for creating AgentFactory objects.
 */
@Component
public class AgentFactoryFactory {

    /** The captcha agent factory. */
    @Autowired
    private CaptchaAgentFactory captchaAgentFactory;

    /** The cache agent factory. */
    @Autowired
    private CacheAgentFactory cacheAgentFactory;

    /** The database agent factory. */
    @Autowired
    private DatabaseAgentFactory databaseAgentFactory;

    /** The email server agent factory. */
    @Autowired
    private EmailServerAgentFactory emailServerAgentFactory;

    /** The file system agent factory. */
    @Autowired
    private FileSystemAgentFactory fileSystemAgentFactory;

    /** The graph agent factory. */
    @Autowired
    private GraphAgentFactory graphAgentFactory;

    /** The ojo agent factory. */
    @Autowired
    private OjoAgentFactory ojoAgentFactory;

    /** The oxo agent factory. */
    @Autowired
    private OxoAgentFactory oxoAgentFactory;

    /** The report agent factory. */
    @Autowired
    private ReportAgentFactory reportAgentFactory;

    /** The scheduler agent factory. */
    @Autowired
    private SchedulerAgentFactory schedulerAgentFactory;

    /** The sms server agent factory. */
    @Autowired
    private SmsServerAgentFactory smsServerAgentFactory;

    /** The image scaling agent factory. */
    @Autowired
    private ImageScalingAgentFactory imageScalingAgentFactory;

    /** The webserver agent factory. */
    @Autowired
    private WebserverAgentFactory webserverAgentFactory;

    /** The storage access agent factory. */
    @Autowired
    private StorageAccessAgentFactory storageAccessAgentFactory;

    @Autowired
    private NotificationAgentFactory notificationAgentFactory;

    @Autowired
    private TemplateAgentFactory templateAgentFactory;

    @Autowired
    private DataGeneratorAgentFactory dataGeneratorAgentFactory;

    /**
     * Gets the agent factory.
     *
     * @param agentKeyName the agent key name
     * @return the agent factory
     */
    public AgentFactory getAgentFactory(String agentKeyName) {
        AgentFactory agentFactory = null;
        if (agentKeyName != null) {
            if (agentKeyName.equals(AgentConstants.IN_CACHE)) {
                agentFactory = cacheAgentFactory;
            } else if (agentKeyName.equals(AgentConstants.OUT_CACHE)) {
                agentFactory = cacheAgentFactory;
            } else if (agentKeyName.equals(AgentConstants.DATABASE)) {
                agentFactory = databaseAgentFactory;
            } else if (agentKeyName.equals(AgentConstants.EMAIL)) {
                agentFactory = emailServerAgentFactory;
            } else if (agentKeyName.equals(AgentConstants.FILESYSTEM)) {
                agentFactory = fileSystemAgentFactory;
            } else if (agentKeyName.equals(AgentConstants.GRAPHS)) {
                agentFactory = graphAgentFactory;
            } else if (agentKeyName.equals(AgentConstants.OJO)) {
                agentFactory = ojoAgentFactory;
            } else if (agentKeyName.equals(AgentConstants.OXO)) {
                agentFactory = oxoAgentFactory;
            } else if (agentKeyName.equals(AgentConstants.REPORT)) {
                agentFactory = reportAgentFactory;
            } else if (agentKeyName.equals(AgentConstants.WEBSERVER)) {
                agentFactory = webserverAgentFactory;
            } else if (agentKeyName.equals(AgentConstants.CAPTCHA)) {
                agentFactory = captchaAgentFactory;
            } else if (agentKeyName.equals(AgentConstants.IMAGE_SCALING)) {
                agentFactory = imageScalingAgentFactory;
            } else if (agentKeyName.equals(AgentConstants.SCHEDULER)) {
                agentFactory = schedulerAgentFactory;
            } else if (agentKeyName.equals(AgentConstants.SMS)) {
                agentFactory = smsServerAgentFactory;
            } else if (agentKeyName.equals(AgentConstants.STORAGE_SERVER)) {
                agentFactory = storageAccessAgentFactory;
            } else if (agentKeyName.equals(AgentConstants.NOTIFICATION)) {
                agentFactory = notificationAgentFactory;
            } else if (agentKeyName.equals(AgentConstants.TEMPLATE_ENGINE)) {
                agentFactory = templateAgentFactory;
            } else if (agentKeyName.equals(AgentConstants.DATA_GENERATOR)) {
                agentFactory = dataGeneratorAgentFactory;
            }
        }
        return agentFactory;
    }

}
