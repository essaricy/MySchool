package com.myschool.integration.processor.feature;

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
 * The Class FeatureResourceProcessor.
 */
@Component("FeatureResourceProcessor")
public class FeatureResourceProcessor extends AbstractResourceProcessor {

    /** The Constant LOGGER. */
    private static final Log LOGGER = LogFactory.getLog(FeatureResourceProcessor.class);

    /**
     * Inits the.
     */
    @PostConstruct
    private void init() {
        integrationInbound = integrationProperties.getFile(IntegrationPropertiesConstant.INTEGRATION_SERVER_INBOUND_FEATURE);
        integrationOutbound = integrationProperties.getFile(IntegrationPropertiesConstant.INTEGRATION_SERVER_OUTBOUND_FEATURE);
        pathOnMediaServer = integrationProperties.getProperty(IntegrationPropertiesConstant.MEDIA_SERVER_FEATURE);
        metaFileName = integrationProperties.getProperty(IntegrationPropertiesConstant.FEATURE_DESC);
        integrationInboundMetaFile = new File(integrationInbound, metaFileName);
        integrationOutboundMetaFile = new File(integrationOutbound, metaFileName);

        LOGGER.debug("integrationInbound=" + integrationInbound);
        LOGGER.debug("integrationOutbound=" + integrationOutbound);
        LOGGER.debug("pathOnMedia=" + pathOnMediaServer);
        LOGGER.debug("metaFileName " + metaFileName);
        LOGGER.debug("integrationInboundMetaFile " + integrationInboundMetaFile);
        LOGGER.debug("integrationOutboundMetaFile " + integrationOutboundMetaFile);
    }

    /* (non-Javadoc)
     * @see com.myschool.integration.processor.common.AbstractResourceProcessor#preProcess()
     */
    @Override
    public void preProcess() throws CommandProcessException {
        downloadMetaFile(IntegrationConstant.FEATURE);
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
        deleteResource((ResourceDto) tempUtil.toObject(body), IntegrationConstant.FEATURE);
    }

}
