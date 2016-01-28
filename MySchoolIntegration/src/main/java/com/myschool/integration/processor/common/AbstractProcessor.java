package com.myschool.integration.processor.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.integration.agent.IntegrationAgent;
import com.myschool.integration.common.util.MediaServerFTPClientProxy;
import com.myschool.integration.common.util.TempUtil;

/**
 * The Class AbstractProcessor.
 */
@Component
public abstract class AbstractProcessor {

    /** The integration agent. */
    @Autowired
    protected IntegrationAgent integrationAgent;

    /** The temp util. */
    @Autowired
    protected TempUtil tempUtil;

    @Autowired
    protected MediaServerFTPClientProxy mediaServerFTPClientProxy;

}
