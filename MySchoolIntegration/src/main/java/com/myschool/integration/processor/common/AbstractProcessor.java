package com.myschool.integration.processor.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.integration.agent.IntegrationProperties;
import com.myschool.integration.ftp.MediaServerFTPClientProxy;
import com.myschool.integration.util.TempUtil;

/**
 * The Class AbstractProcessor.
 */
@Component
public abstract class AbstractProcessor {

    /** The temp util. */
    @Autowired
    protected TempUtil tempUtil;

    @Autowired
    protected IntegrationProperties integrationProperties;

    @Autowired
    protected MediaServerFTPClientProxy mediaServerFTPClientProxy;

}
