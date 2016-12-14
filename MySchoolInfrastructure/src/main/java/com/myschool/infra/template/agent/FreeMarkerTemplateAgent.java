package com.myschool.infra.template.agent;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.common.exception.AgentException;
import com.myschool.common.exception.ConfigurationException;
import com.myschool.infra.application.Agents;
import com.myschool.infra.notification.Message;
import com.myschool.infra.template.exception.MessageGenerationException;
import com.myschool.infra.web.dto.WebProfile;
import com.myschool.infra.webserver.agent.WebServerAgent;
import com.myschool.organization.dto.Organization;
import com.myschool.template.dto.Template;

import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModelException;
import freemarker.template.TemplateNotFoundException;

/**
 * The Class FreeMarkerTemplateAgent.
 */
@Component
public class FreeMarkerTemplateAgent extends TemplateAgent {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(FreeMarkerTemplateAgent.class);

    /** The agents. */
    @Autowired
    private Agents agents;

    /** The configuration. */
    private Configuration configuration;

    /** The web profile. */
    private WebProfile webProfile;

    /* (non-Javadoc)
     * @see com.myschool.infra.agent.Agent#loadConfiguration(java.io.File)
     */
    @Override
    public void loadConfiguration(File configFile) throws ConfigurationException {
        try {
            super.loadConfiguration(configFile);
            configuration = new Configuration(Configuration.VERSION_2_3_25);
            configuration.setDirectoryForTemplateLoading(getTemplateBase());
        } catch (IOException ioException) {
            throw new ConfigurationException(ioException.getMessage(), ioException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.infra.template.agent.TemplateAgent#validate()
     */
    @Override
    public void validate() throws AgentException {
        try {
            WebServerAgent webServerAgent = agents.getWebServerAgent();
            webProfile = webServerAgent.getWebProfile();

            // TODO get this information.
            Organization organization = new Organization();
            organization.setAddress("address");
            organization.setFaxNumber("faxNumber");
            organization.setName("Organization name");
            organization.setPhoneNumber("phoneNumber");
            configuration.setSharedVariable("webProfile", webProfile);
            configuration.setSharedVariable("organization", organization);
            super.validate();
        } catch (TemplateModelException templateModelException) {
            templateModelException.printStackTrace();
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.infra.template.agent.TemplateAgent#generateText(com.myschool.template.dto.Template, com.myschool.infra.notification.Message)
     */
    @Override
    public String generateText(Template template, Message message) throws MessageGenerationException {
        String output = null;
        try {
            LOGGER.info("enter text - " + message.getId());
            LOGGER.info("template - " + message.getId() + " " + template.getId());
            LOGGER.info("message - "  + message.getId() + " " + message);

            freemarker.template.Template fmTemplate = configuration.getTemplate(template.getTemplateRelativePath());
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("message", message);

            StringWriter stringWriter = new StringWriter();
            fmTemplate.process(data, stringWriter);
            output = stringWriter.toString();
        } catch (TemplateNotFoundException templateNotFoundException) {
            throw new MessageGenerationException(templateNotFoundException.getMessage(), templateNotFoundException);
        } catch (MalformedTemplateNameException malformedTemplateNameException) {
            throw new MessageGenerationException(malformedTemplateNameException.getMessage(), malformedTemplateNameException);
        } catch (ParseException parseException) {
            throw new MessageGenerationException(parseException.getMessage(), parseException);
        } catch (IOException ioException) {
            throw new MessageGenerationException(ioException.getMessage(), ioException);
        } catch (TemplateException templateException) {
            throw new MessageGenerationException(templateException.getMessage(), templateException);
        }
        LOGGER.info("exit text - "  + message.getId() + " " + output);
        return output;
    }

    /* (non-Javadoc)
     * @see com.myschool.infra.template.agent.TemplateAgent#generateFile(com.myschool.template.dto.Template, com.myschool.infra.notification.Message, java.io.File)
     */
    @Override
    public void generateFile(Template template, Message message, File output) throws MessageGenerationException {
        Writer writer = null;
        try {
            LOGGER.info("enter file - " + message.getId());
            LOGGER.info("template - " + message.getId() + " " + template.getId());
            LOGGER.info("message - "  + message.getId() + " " + message);

            freemarker.template.Template fmTemplate = configuration.getTemplate(template.getTemplateRelativePath());
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("message", message);

            writer = new FileWriter(output);
            fmTemplate.process(data, writer);
        } catch (TemplateNotFoundException templateNotFoundException) {
            throw new MessageGenerationException(templateNotFoundException.getMessage(), templateNotFoundException);
        } catch (MalformedTemplateNameException malformedTemplateNameException) {
            throw new MessageGenerationException(malformedTemplateNameException.getMessage(), malformedTemplateNameException);
        } catch (ParseException parseException) {
            throw new MessageGenerationException(parseException.getMessage(), parseException);
        } catch (IOException ioException) {
            throw new MessageGenerationException(ioException.getMessage(), ioException);
        } catch (TemplateException templateException) {
            throw new MessageGenerationException(templateException.getMessage(), templateException);
        } finally {
            IOUtils.closeQuietly(writer);
        }
        LOGGER.info("exit file - "  + message.getId() + " " + output.getAbsolutePath());
    }

}
