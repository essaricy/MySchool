package com.myschool.infra.template.agent;

import java.io.File;
import java.text.MessageFormat;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.common.dto.Person;
import com.myschool.common.exception.AgentException;
import com.myschool.common.exception.ConfigurationException;
import com.myschool.infra.agent.AbstractAgent;
import com.myschool.infra.data.agent.DataGeneratorAgent;
import com.myschool.infra.notification.Message;
import com.myschool.infra.template.exception.MessageGenerationException;
import com.myschool.infra.template.reader.TemplateConfigReader;
import com.myschool.template.dto.Template;
import com.myschool.template.dto.TemplateConfig;
import com.myschool.template.dto.TemplateGroup;
import com.quasar.core.exception.FileSystemException;
import com.quasar.core.util.FileUtil;

/**
 * The Class TemplateAgent.
 */
@Component
public abstract class TemplateAgent extends AbstractAgent {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(TemplateAgent.class);

    /** The Constant MESSAGE_MISSING_BASE. */
    private static final String MESSAGE_MISSING_BASE = "Template base directory does not exist";

    /** The Constant MESSAGE_MISSING_TEMPLATE. */
    private static final String MESSAGE_MISSING_TEMPLATE = "Missing Template {0}/{1}";

    /** The template config reader. */
    @Autowired
    private TemplateConfigReader templateConfigReader;

    @Autowired
    private DataGeneratorAgent dataGeneratorAgent;

    /** The template config. */
    private TemplateConfig templateConfig;

    /* (non-Javadoc)
     * @see com.myschool.infra.agent.Agent#loadConfiguration(java.io.File)
     */
    @Override
    public void loadConfiguration(File configFile)
            throws ConfigurationException {

        LOGGER.info("loading tempalte configuration");
        try {
            templateConfig = templateConfigReader.getNotificationConfig(configFile);
            if (templateConfig == null) {
                throw new ConfigurationException("Templates are not configured");
            }
            String basePath = templateConfig.getBasePath();
            File baseDir = FileUtil.checkDirectory(basePath, MESSAGE_MISSING_BASE, MESSAGE_MISSING_BASE);
            LOGGER.info("Tempaltes base directory=" + baseDir);
            templateConfig.setBaseDir(baseDir);

            List<TemplateGroup> templateGroups = templateConfig.getTemplateGroups();
            if (templateGroups != null && !templateGroups.isEmpty()) {
                for (TemplateGroup templateGroup : templateGroups) {
                    String name = templateGroup.getName();
                    String deliveryMethod = templateGroup.getDeliveryMethod();
                    String usage = templateGroup.getUsage();
                    String exampleFileNamePattern = templateGroup.getExampleFileNamePattern();
                    String templateFileNamePattern = templateGroup.getTemplateFileNamePattern();
                    List<Template> templates = templateGroup.getTemplates();

                    String templateGroupFileName = name + "/" + deliveryMethod.toLowerCase() + "/" + usage.toLowerCase();
                    LOGGER.info("Template Group File Name =" + templateGroupFileName);
                    File templateGroupFile = new File(baseDir, templateGroupFileName);
                    templateGroup.setGroupFile(templateGroupFile);
                    if (templates != null && !templates.isEmpty()) {
                        for (Template template : templates) {
                            String id = template.getId();
                            String exampleFileName = exampleFileNamePattern.replaceAll("\\{id\\}", id);
                            //String missingExample = MessageFormat.format(MESSAGE_MISSING_TEMPLATE, templateGroupFileName, exampleFileName);
                            File exampleFile = new File(templateGroupFile, exampleFileName);
                            template.setExampleName(exampleFileName);
                            template.setExampleRelativePath(templateGroupFileName + "/" + exampleFileName);
                            template.setExampleFile(exampleFile);

                            String templateFileName = templateFileNamePattern.replaceAll("\\{id\\}", id);
                            String missingTemplate = MessageFormat.format(MESSAGE_MISSING_TEMPLATE, templateGroupFileName, templateFileName);
                            File templateFile = FileUtil.checkFile(new File(templateGroupFile, templateFileName), missingTemplate, missingTemplate);
                            template.setTemplateName(templateFileName);
                            template.setTemplateRelativePath(templateGroupFileName + "/" + templateFileName);
                            template.setTemplateFile(templateFile);
                        }
                    }
                }
            }
            LOGGER.info("loaded tempalte configuration");
        } catch (FileSystemException fileSystemException) {
            throw new ConfigurationException(fileSystemException.getMessage(), fileSystemException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.infra.Agent#validate()
     */
    @Override
    public void validate() throws AgentException {
        Message message = new Message();
        message.setId(dataGeneratorAgent.getUniqueId());
        Person sendTo = new Person();
        sendTo.setFirstName(dataGeneratorAgent.getFirstName());
        //sendTo.setMiddleName(dataGeneratorAgent.getMiddleName());
        sendTo.setLastName(dataGeneratorAgent.getLastName());
        sendTo.setEmailId(dataGeneratorAgent.getEmailAddress());
        message.setSendTo(sendTo);
        System.out.println("sendTo=" + sendTo);

        List<TemplateGroup> templateGroups = templateConfig.getTemplateGroups();
        if (templateGroups != null && !templateGroups.isEmpty()) {
            for (TemplateGroup templateGroup : templateGroups) {
                List<Template> templates = templateGroup.getTemplates();
                if (templates != null && !templates.isEmpty()) {
                    for (Template template : templates) {
                        File exampleFile = template.getExampleFile();
                        if (exampleFile == null || !exampleFile.exists()) {
                            try {
                                LOGGER.info("Validating template: " + template.getId());
                                System.out.println("Generating Example for " + template.getId());
                                generateFile(template, message, exampleFile);
                            } catch (MessageGenerationException messageGenerationException) {
                                LOGGER.error(messageGenerationException.getMessage(), messageGenerationException);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Generate text.
     *
     * @param template the template
     * @param message the message
     * @return the string
     * @throws MessageGenerationException the message generation exception
     */
    public abstract String generateText(Template template, Message message) throws MessageGenerationException;

    /**
     * Generate file.
     *
     * @param template the template
     * @param message the message
     * @param output the output
     * @throws MessageGenerationException the message generation exception
     */
    public abstract void generateFile(Template template, Message message, File output) throws MessageGenerationException;

    /**
     * Gets the template base.
     *
     * @return the template base
     */
    protected File getTemplateBase() {
        return templateConfig.getBaseDir();
    }

    /**
     * Gets the template.
     *
     * @param id the id
     * @return the template
     */
    public Template getTemplate(String id) {
        List<TemplateGroup> templateGroups = templateConfig.getTemplateGroups();
        if (templateGroups != null && !templateGroups.isEmpty()) {
            for (TemplateGroup templateGroup : templateGroups) {
                List<Template> templates = templateGroup.getTemplates();
                if (templates != null && !templates.isEmpty()) {
                    for (Template template : templates) {
                        if (id.equals(template.getId())) {
                            return template;
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * Generate change password request.
     *
     * @param sendTo the send to
     * @return the string
     * @throws MessageGenerationException the message generation exception
     */
    /*public String generateChangePasswordRequest(Person sendTo) throws MessageGenerationException {
        Message message = new Message();
        message.setId(UUID.randomUUID().toString());
        message.setSendTo(sendTo);
        return generateText(getTemplate("ChangePasswordRequest"), message);
    }*/

}
