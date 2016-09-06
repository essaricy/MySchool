package com.myschool.integration.agent;

import java.io.File;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.integration.constant.IntegrationConstant;
import com.myschool.integration.constant.IntegrationPropertiesConstant;

/**
 * A factory for creating IntegrationImageResource objects.
 */
@Component
public class IntegrationImageResourceFactory {

    @Autowired
    private IntegrationProperties integrationProperties;

    /** The integration image resources. */
    private Map<String, IntegrationImageResource> integrationImageResources;

    /**
     * Inits the.
     */
    @PostConstruct
    private void init() {
        integrationImageResources = new HashMap<String, IntegrationImageResource>();
    }

    /**
     * Gets the image resource.
     * 
     * @param key the key
     * @param dynamicDirName the dynamic dir name
     * @return the image resource
     */
    public IntegrationImageResource getImageResource(String key, String dynamicDirName) {
        IntegrationImageResource integrationImageResource = null;

        if (key != null) {
            if (integrationImageResources.containsKey(key)) {
                integrationImageResource = integrationImageResources.get(key);
            } else {
                String integrationInboundBase = null;
                String integrationInboundDynamic = null;
                String integrationInboundDynamicPassport = null;
                String integrationInboundDynamicThumbnail = null;
                String integrationOutboundBase = null;
                String integrationOutboundDynamic = null;
                String integrationOutboundDynamicPassport = null;
                String integrationOutboundDynamicThumbnail = null;
                String mediaBase = null;
                String mediaDynamic = null;
                String mediaDynamicPassport = null;
                String mediaDynamicThumbnail = null;

                integrationImageResource = new IntegrationImageResource();
                System.out.println("key " + key);
                if (key.equals(IntegrationConstant.EMPLOYEE_REGISTERED)
                        || key.equals(IntegrationConstant.EMPLOYEE_SELF_SUBMITTED)) {
                    if (dynamicDirName == null) {
                        dynamicDirName = "";
                    }
                    System.out.println("dynamicDirName " + dynamicDirName);
                    integrationInboundBase = integrationProperties.getProperty(
                            IntegrationPropertiesConstant.INTEGRATION_SERVER_INBOUND_EMPLOYEE);
                    integrationInboundDynamic = MessageFormat.format(integrationProperties.getProperty(
                            IntegrationPropertiesConstant.INTEGRATION_SERVER_INBOUND_EMPLOYEE_DYNAMIC),
                            dynamicDirName);
                    integrationInboundDynamicPassport = MessageFormat.format(integrationProperties.getProperty(
                            IntegrationPropertiesConstant.INTEGRATION_SERVER_INBOUND_EMPLOYEE_DYNAMIC_PASSPORT),
                            dynamicDirName);
                    integrationInboundDynamicThumbnail = MessageFormat.format(integrationProperties.getProperty(
                            IntegrationPropertiesConstant.INTEGRATION_SERVER_INBOUND_EMPLOYEE_DYNAMIC_THUMBNAIL),
                            dynamicDirName);

                    integrationOutboundBase = integrationProperties.getProperty(
                            IntegrationPropertiesConstant.INTEGRATION_SERVER_OUTBOUND_EMPLOYEE);
                    integrationOutboundDynamic = MessageFormat.format(integrationProperties.getProperty(
                            IntegrationPropertiesConstant.INTEGRATION_SERVER_OUTBOUND_EMPLOYEE_DYNAMIC),
                            dynamicDirName);
                    integrationOutboundDynamicPassport = MessageFormat.format(integrationProperties.getProperty(
                            IntegrationPropertiesConstant.INTEGRATION_SERVER_OUTBOUND_EMPLOYEE_DYNAMIC_PASSPORT),
                            dynamicDirName);
                    integrationOutboundDynamicThumbnail = MessageFormat.format(integrationProperties.getProperty(
                            IntegrationPropertiesConstant.INTEGRATION_SERVER_OUTBOUND_EMPLOYEE_DYNAMIC_THUMBNAIL),
                            dynamicDirName);

                    mediaBase = integrationProperties.getProperty(
                            IntegrationPropertiesConstant.MEDIA_SERVER_EMPLOYEE);
                    mediaDynamic = MessageFormat.format(integrationProperties.getProperty(
                            IntegrationPropertiesConstant.MEDIA_SERVER_EMPLOYEE_DYNAMIC),
                            dynamicDirName);
                    mediaDynamicPassport = MessageFormat.format(integrationProperties.getProperty(
                            IntegrationPropertiesConstant.MEDIA_SERVER_EMPLOYEE_DYNAMIC_PASSPORT),
                            dynamicDirName);
                    mediaDynamicThumbnail = MessageFormat.format(integrationProperties.getProperty(
                            IntegrationPropertiesConstant.MEDIA_SERVER_EMPLOYEE_DYNAMIC_THUMBNAIL),
                            dynamicDirName);
                } else if (key.equals(IntegrationConstant.STUDENT_REGISTERED)
                        || key.equals(IntegrationConstant.STUDENT_SELF_SUBMITTED)) {
                    if (dynamicDirName == null) {
                        dynamicDirName = "";
                    }
                    System.out.println("dynamicDirName " + dynamicDirName);
                    integrationInboundBase = integrationProperties.getProperty(
                            IntegrationPropertiesConstant.INTEGRATION_SERVER_INBOUND_STUDENT);
                    integrationInboundDynamic = MessageFormat.format(integrationProperties.getProperty(
                            IntegrationPropertiesConstant.INTEGRATION_SERVER_INBOUND_STUDENT_DYNAMIC),
                            dynamicDirName);
                    integrationInboundDynamicPassport = MessageFormat.format(integrationProperties.getProperty(
                            IntegrationPropertiesConstant.INTEGRATION_SERVER_INBOUND_STUDENT_DYNAMIC_PASSPORT),
                            dynamicDirName);
                    integrationInboundDynamicThumbnail = MessageFormat.format(integrationProperties.getProperty(
                            IntegrationPropertiesConstant.INTEGRATION_SERVER_INBOUND_STUDENT_DYNAMIC_THUMBNAIL),
                            dynamicDirName);

                    integrationOutboundBase = integrationProperties.getProperty(
                            IntegrationPropertiesConstant.INTEGRATION_SERVER_OUTBOUND_STUDENT);
                    integrationOutboundDynamic = MessageFormat.format(integrationProperties.getProperty(
                            IntegrationPropertiesConstant.INTEGRATION_SERVER_OUTBOUND_STUDENT_DYNAMIC),
                            dynamicDirName);
                    integrationOutboundDynamicPassport = MessageFormat.format(integrationProperties.getProperty(
                            IntegrationPropertiesConstant.INTEGRATION_SERVER_OUTBOUND_STUDENT_DYNAMIC_PASSPORT),
                            dynamicDirName);
                    integrationOutboundDynamicThumbnail = MessageFormat.format(integrationProperties.getProperty(
                            IntegrationPropertiesConstant.INTEGRATION_SERVER_OUTBOUND_STUDENT_DYNAMIC_THUMBNAIL),
                            dynamicDirName);

                    mediaBase = integrationProperties.getProperty(
                            IntegrationPropertiesConstant.MEDIA_SERVER_STUDENT);
                    mediaDynamic = MessageFormat.format(integrationProperties.getProperty(
                            IntegrationPropertiesConstant.MEDIA_SERVER_STUDENT_DYNAMIC),
                            dynamicDirName);
                    mediaDynamicPassport = MessageFormat.format(integrationProperties.getProperty(
                            IntegrationPropertiesConstant.MEDIA_SERVER_STUDENT_DYNAMIC_PASSPORT),
                            dynamicDirName);
                    mediaDynamicThumbnail = MessageFormat.format(integrationProperties.getProperty(
                            IntegrationPropertiesConstant.MEDIA_SERVER_STUDENT_DYNAMIC_THUMBNAIL),
                            dynamicDirName);
                } else if (key.startsWith(IntegrationConstant.GALLERY)) {
                    if (dynamicDirName == null) {
                        dynamicDirName = "";
                    }
                    System.out.println("dynamicDirName " + dynamicDirName);
                    integrationInboundBase = integrationProperties.getProperty(
                            IntegrationPropertiesConstant.INTEGRATION_SERVER_INBOUND_GALLERY);
                    integrationInboundDynamic = MessageFormat.format(integrationProperties.getProperty(
                            IntegrationPropertiesConstant.INTEGRATION_SERVER_INBOUND_GALLERY_DYNAMIC),
                            dynamicDirName);
                    integrationInboundDynamicPassport = MessageFormat.format(integrationProperties.getProperty(
                            IntegrationPropertiesConstant.INTEGRATION_SERVER_INBOUND_GALLERY_DYNAMIC_PASSPORT),
                            dynamicDirName);
                    integrationInboundDynamicThumbnail = MessageFormat.format(integrationProperties.getProperty(
                            IntegrationPropertiesConstant.INTEGRATION_SERVER_INBOUND_GALLERY_DYNAMIC_THUMBNAIL),
                            dynamicDirName);

                    integrationOutboundBase = integrationProperties.getProperty(
                            IntegrationPropertiesConstant.INTEGRATION_SERVER_OUTBOUND_GALLERY);
                    integrationOutboundDynamic = MessageFormat.format(integrationProperties.getProperty(
                            IntegrationPropertiesConstant.INTEGRATION_SERVER_OUTBOUND_GALLERY_DYNAMIC),
                            dynamicDirName);
                    integrationOutboundDynamicPassport = MessageFormat.format(integrationProperties.getProperty(
                            IntegrationPropertiesConstant.INTEGRATION_SERVER_OUTBOUND_GALLERY_DYNAMIC_PASSPORT),
                            dynamicDirName);
                    integrationOutboundDynamicThumbnail = MessageFormat.format(integrationProperties.getProperty(
                            IntegrationPropertiesConstant.INTEGRATION_SERVER_OUTBOUND_GALLERY_DYNAMIC_THUMBNAIL),
                            dynamicDirName);

                    mediaBase = integrationProperties.getProperty(
                            IntegrationPropertiesConstant.MEDIA_SERVER_GALLERY);
                    mediaDynamic = MessageFormat.format(integrationProperties.getProperty(
                            IntegrationPropertiesConstant.MEDIA_SERVER_GALLERY_DYNAMIC),
                            dynamicDirName);
                    mediaDynamicPassport = MessageFormat.format(integrationProperties.getProperty(
                            IntegrationPropertiesConstant.MEDIA_SERVER_GALLERY_DYNAMIC_PASSPORT),
                            dynamicDirName);
                    mediaDynamicThumbnail = MessageFormat.format(integrationProperties.getProperty(
                            IntegrationPropertiesConstant.MEDIA_SERVER_GALLERY_DYNAMIC_THUMBNAIL),
                            dynamicDirName);
                }
                integrationImageResource.setIntegrationInboundBase(new File(integrationInboundBase));
                integrationImageResource.setIntegrationInboundDynamic(new File(integrationInboundDynamic));
                integrationImageResource.setIntegrationInboundDynamicPassport(new File(integrationInboundDynamicPassport));
                integrationImageResource.setIntegrationInboundDynamicThumbnail(new File(integrationInboundDynamicThumbnail));

                integrationImageResource.setIntegrationOutboundBase(new File(integrationOutboundBase));
                integrationImageResource.setIntegrationOutboundDynamic(new File(integrationOutboundDynamic));
                integrationImageResource.setIntegrationOutboundDynamicPassport(new File(integrationOutboundDynamicPassport));
                integrationImageResource.setIntegrationOutboundDynamicThumbnail(new File(integrationOutboundDynamicThumbnail));
                integrationImageResource.setMediaBase(mediaBase);
                integrationImageResource.setMediaDynamic(mediaDynamic);
                integrationImageResource.setMediaDynamicPassport(mediaDynamicPassport);
                integrationImageResource.setMediaDynamicThumbnail(mediaDynamicThumbnail);
                integrationImageResources.put(key, integrationImageResource);
            }
        }
        return integrationImageResource;
    }

}
