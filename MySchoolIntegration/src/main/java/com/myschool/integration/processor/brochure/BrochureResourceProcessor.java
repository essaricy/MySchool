package com.myschool.integration.processor.brochure;

import java.io.File;

import javax.annotation.PostConstruct;

import org.apache.camel.Message;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.myschool.application.dto.ResourceDto;
import com.myschool.integration.constant.IntegrationConstant;
import com.myschool.integration.constant.IntegrationPropertiesConstant;
import com.myschool.integration.exception.CommandExecutionException;
import com.myschool.integration.exception.CommandProcessException;
import com.myschool.integration.processor.common.AbstractResourceProcessor;

/**
 * The Class BrochureResourceProcessor.
 */
@Component("BrochureResourceProcessor")
public class BrochureResourceProcessor extends AbstractResourceProcessor {

    /** The Constant LOGGER. */
    private static final Log LOGGER = LogFactory.getLog(BrochureResourceProcessor.class);

    /**
     * Inits the.
     */
    @PostConstruct
    private void init() {
        integrationInbound = integrationProperties.getFile(IntegrationPropertiesConstant.INTEGRATION_SERVER_INBOUND_BROCHURE);
        integrationOutbound = integrationProperties.getFile(IntegrationPropertiesConstant.INTEGRATION_SERVER_OUTBOUND_BROCHURE);
        pathOnMediaServer = integrationProperties.getProperty(IntegrationPropertiesConstant.MEDIA_SERVER_BROCHURE);
        metaFileName = integrationProperties.getProperty(IntegrationPropertiesConstant.BROCHURE_DESC);
        integrationInboundMetaFile = new File(integrationInbound, metaFileName);
        integrationOutboundMetaFile = new File(integrationOutbound, metaFileName);

        LOGGER.info("integrationInbound=" + integrationInbound);
        LOGGER.info("integrationOutbound=" + integrationOutbound);
        LOGGER.info("pathOnMedia=" + pathOnMediaServer);
        LOGGER.info("metaFileName " + metaFileName);
        LOGGER.info("integrationInboundMetaFile " + integrationInboundMetaFile);
        LOGGER.info("integrationOutboundMetaFile " + integrationOutboundMetaFile);
    }

    /* (non-Javadoc)
     * @see com.myschool.integration.processor.common.AbstractResourceProcessor#preProcess()
     */
    @Override
    public void preProcess() throws CommandProcessException {
        downloadMetaFile(IntegrationConstant.BROCHURE);
    }

    /* (non-Javadoc)
     * @see com.myschool.integration.processor.common.AbstractResourceProcessor#postProcess()
     */
    @Override
    public void postProcess() throws CommandProcessException {
        moveMetaFile();
    }

    /* (non-Javadoc)
     * @see com.myschool.integration.processor.common.AbstractResourceProcessor#add(org.apache.camel.Message, java.lang.String)
     */
    @Override
    public void add(Message message, String body) throws CommandExecutionException {
        addResource((ResourceDto) tempUtil.toObject(body));
    }

    /* (non-Javadoc)
     * @see com.myschool.integration.processor.common.AbstractResourceProcessor#update(org.apache.camel.Message, java.lang.String)
     */
    @Override
    public void update(Message message, String body) throws CommandExecutionException {
        updateResource((ResourceDto) tempUtil.toObject(body));
    }

    /* (non-Javadoc)
     * @see com.myschool.integration.processor.common.AbstractResourceProcessor#delete(org.apache.camel.Message, java.lang.String)
     */
    @Override
    public void delete(Message message, String body) throws CommandExecutionException {
        deleteResource((ResourceDto) tempUtil.toObject(body), IntegrationConstant.BROCHURE);
    }

}
