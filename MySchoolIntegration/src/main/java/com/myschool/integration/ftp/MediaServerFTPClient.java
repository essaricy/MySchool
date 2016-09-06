package com.myschool.integration.ftp;

import java.util.Properties;

import com.myschool.common.util.PropertiesUtil;
import com.myschool.infra.remote.ftp.client.MySchoolFTPClient;
import com.myschool.integration.constant.IntegrationPropertiesConstant;

/**
 * The Class MediaServerFTPClient.
 */
class MediaServerFTPClient extends MySchoolFTPClient {

    /** The properties. */
    private Properties properties;

    public MediaServerFTPClient(String name, Properties properties) {
        super(name);
        this.properties=properties;
    }

    @Override
    protected String getHost() {
        return properties.getProperty(IntegrationPropertiesConstant.MEDIA_SERVER_HOST);
    }

    @Override
    protected int getPort() {
        return PropertiesUtil.getInt(properties, IntegrationPropertiesConstant.MEDIA_SERVER_FTP_PORT);
    }

    @Override
    protected String getUserName() {
        return properties.getProperty(IntegrationPropertiesConstant.MEDIA_SERVER_FTP_USER);
    }

    @Override
    protected String getPassword() {
        return properties.getProperty(IntegrationPropertiesConstant.MEDIA_SERVER_FTP_PWD);
    }

    @Override
    protected long getRetryMinDelay() {
        return PropertiesUtil.getLong(properties, IntegrationPropertiesConstant.MEDIA_SERVER_RETRY_MIN_DELAY);
    }

    @Override
    protected long getRetryMaxDelay() {
        return PropertiesUtil.getLong(properties, IntegrationPropertiesConstant.MEDIA_SERVER_RETRY_MAX_DELAY);
    }

}