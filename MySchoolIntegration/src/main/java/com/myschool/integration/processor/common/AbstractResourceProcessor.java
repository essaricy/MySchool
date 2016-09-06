package com.myschool.integration.processor.common;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.camel.Message;
import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.myschool.application.dto.ResourceDto;
import com.myschool.common.exception.FileSystemException;
import com.myschool.file.util.FileUtil;
import com.myschool.infra.remote.ftp.exception.FtpException;
import com.myschool.integration.exception.CommandExecutionException;
import com.myschool.integration.exception.CommandProcessException;

/**
 * The Class AbstractFileCommandProcessor.
 */
@Component
public abstract class AbstractResourceProcessor extends AbstractProcessor {

    /** The Constant LOGGER. */
    private static final Log LOGGER = LogFactory.getLog(AbstractResourceProcessor.class);

    /** The integration inbound. */
    protected File integrationInbound;

    /** The integration outbound. */
    protected File integrationOutbound;

    /** The integration inbound meta file. */
    protected File integrationInboundMetaFile;

    /** The integration outbound meta file. */
    protected File integrationOutboundMetaFile;

    /** The path on media server. */
    protected String pathOnMediaServer;

    /** The meta file name. */
    protected String metaFileName;

    /**
     * Pre process.
     * 
     * @throws CommandProcessException the command process exception
     */
    public abstract void preProcess() throws CommandProcessException;

    /**
     * Post process.
     * 
     * @throws CommandProcessException the command process exception
     */
    public abstract void postProcess() throws CommandProcessException;

    /**
     * Adds the.
     * 
     * @param message the message
     * @param body the body
     * @throws CommandExecutionException the command execution exception
     */
    public abstract void add(Message message, String body) throws CommandExecutionException;

    /**
     * Update.
     * 
     * @param message the message
     * @param body the body
     * @throws CommandExecutionException the command execution exception
     */
    public abstract void update(Message message, String body) throws CommandExecutionException;

    /**
     * Delete.
     * 
     * @param message the message
     * @param body the body
     * @throws CommandExecutionException the command execution exception
     */
    public abstract void delete(Message message, String body) throws CommandExecutionException;

    /**
     * Download meta file.
     * @param mediaType 
     * 
     * @throws CommandProcessException the command process exception
     */
    protected void downloadMetaFile(String mediaType) throws CommandProcessException {
        try {
            if (integrationInboundMetaFile != null && !integrationInboundMetaFile.exists()) {
                String fileContent = mediaServerFTPClientProxy.getFileContent(mediaType, pathOnMediaServer, metaFileName);
                LOGGER.debug("MetaFle before processing=" + fileContent);
                if (fileContent == null || fileContent.trim().length() == 0) {
                    fileContent = tempUtil.toXml(new ArrayList<ResourceDto>(), ResourceDto.class);
                }
                FileUtils.write(integrationInboundMetaFile, fileContent);
            }
        } catch (FtpException ftpException) {
            throw new CommandProcessException(ftpException.getMessage(), ftpException);
        } catch (IOException ioException) {
            throw new CommandProcessException(ioException.getMessage(), ioException);
        }
    }

    /**
     * Move meta file.
     * 
     * @throws CommandProcessException the command process exception
     */
    public void moveMetaFile() throws CommandProcessException {
        try {
            if (integrationInboundMetaFile == null || !integrationInboundMetaFile.exists()) {
                throw new CommandProcessException("Missing meta file " + integrationInboundMetaFile);
            }
            String fileContent = FileUtils.readFileToString(integrationInboundMetaFile);
            LOGGER.debug("Meta file after processing=" + fileContent);
            integrationInboundMetaFile.renameTo(integrationOutboundMetaFile);
            System.out.println("Moved to outbound.");
        } catch (IOException ioException) {
            throw new CommandProcessException(ioException.getMessage(), ioException);
        }
    }

    /**
     * Adds the resource.
     * 
     * @param resource the resource
     * @throws CommandExecutionException the command execution exception
     */
    public void addResource(ResourceDto resource) throws CommandExecutionException {
        ResourceDto existingResource = null;
        try {
            String resourceName = resource.getName();

            File resourceFile = FileUtil.getMandatoryFile(integrationInbound, resourceName);
            LOGGER.info("Adding ResourceFile=" + resourceFile);
            List<ResourceDto> existingResources = getResources();
            int resourceIndex = getResourceIndex(existingResources, resourceName);
            if (resourceIndex >= 0) {
                existingResource = existingResources.get(resourceIndex);
            }
            if (existingResource != null) {
                throw new CommandExecutionException("Resource " + resourceName + "  already exists.");
            }
            File resourceOutboundFile = new File(integrationOutbound, resourceName);
            resourceFile.renameTo(resourceOutboundFile);
            // add to the list
            existingResources.add(resource);
            LOGGER.info("Added ResourceFile=" + resourceFile);
            updateMetaFile(existingResources);
        } catch (IOException ioException) {
            throw new CommandExecutionException(ioException.getMessage(), ioException);
        } catch (FileSystemException fileSystemException) {
            throw new CommandExecutionException(fileSystemException.getMessage(), fileSystemException);
        }
    }

    /**
     * Update resource.
     * 
     * @param resourceToProcess the resource to process
     * @throws CommandExecutionException the command execution exception
     */
    public void updateResource(ResourceDto resourceToProcess) throws CommandExecutionException {
        ResourceDto existingResource = null;
        try {
            String resourceName = resourceToProcess.getName();

            File resourceFile = FileUtil.getFileCheckExists(integrationInbound, resourceName);
            LOGGER.info("Updating ResourceFile=" + resourceFile);
            List<ResourceDto> existingResources = getResources();
            int resourceIndex = getResourceIndex(existingResources, resourceName);
            if (resourceIndex >= 0) {
                existingResource = existingResources.get(resourceIndex);
                existingResource.setShortDescription(resourceToProcess.getShortDescription());
                existingResource.setLongDescription(resourceToProcess.getLongDescription());
            }
            if (resourceFile == null) {
                LOGGER.info("Resource file is not found. will update the descriptions for resource " + resourceName);
            } else {
                File updatedResourceFile = new File(integrationOutbound, resourceName);
                resourceFile.renameTo(updatedResourceFile);
                LOGGER.info("Updated ResourceFile=" + resourceFile);
            }
            updateMetaFile(existingResources);
        } catch (IOException ioException) {
            throw new CommandExecutionException(ioException.getMessage(), ioException);
        }
    }

    /**
     * Delete resource.
     * 
     * @param resource the resource
     * @param mediaType 
     * @throws CommandExecutionException the command execution exception
     */
    public void deleteResource(ResourceDto resource, String mediaType) throws CommandExecutionException {
        ResourceDto existingResource = null;
        try {
            String resourceName = resource.getName();
            LOGGER.info("Deleting ResourceFile=" + resourceName);
            List<ResourceDto> existingResources = getResources();
            int resourceIndex = getResourceIndex(existingResources, resourceName);
            if (resourceIndex >= 0) {
                existingResource = existingResources.get(resourceIndex);
            }
            if (existingResource == null) {
                throw new CommandExecutionException("Unable to delete resource. Brochure " + resourceName + " does not exist.");
            }
            mediaServerFTPClientProxy.deleteFile(mediaType, pathOnMediaServer, resourceName);
            LOGGER.info("Deleted ResourceFile=" + resourceName);
            existingResources.remove(resourceIndex);
            updateMetaFile(existingResources);
        } catch (IOException ioException) {
            throw new CommandExecutionException(ioException.getMessage(), ioException);
        } catch (FtpException ftpException) {
            throw new CommandExecutionException(ftpException.getMessage(), ftpException);
        }
    }

    /**
     * Gets the resource index.
     * 
     * @param existingResources the existing resources
     * @param resourceName the resource name
     * @return the resource index
     */
    protected int getResourceIndex(List<ResourceDto> existingResources, String resourceName) {
        if (resourceName != null && existingResources != null && !existingResources.isEmpty()) {
            for (int index = 0; index < existingResources.size(); index++) {
                ResourceDto existingResource = existingResources.get(index);
                if (existingResource != null && resourceName.equalsIgnoreCase(existingResource.getName())) {
                    return index;
                }
            }
        }
        return -1;
    }

    /**
     * Update meta file.
     * 
     * @param brochures the brochures
     * @throws IOException Signals that an I/O exception has occurred.
     */
    protected void updateMetaFile(List<ResourceDto> brochures) throws IOException {
        String resourcesXml = tempUtil.toXml(brochures, ResourceDto.class);
        FileUtils.write(integrationInboundMetaFile, resourcesXml);
    }

    /**
     * Gets the resources.
     * 
     * @return the resources
     * @throws IOException Signals that an I/O exception has occurred.
     */
    protected List<ResourceDto> getResources() throws IOException {
        System.out.println("integrationInboundMetaFile " + integrationInboundMetaFile);
        String resourcesXml = FileUtils.readFileToString(integrationInboundMetaFile);
        System.out.println("resourcesXml " + resourcesXml);
        List<ResourceDto> existingResources = tempUtil.toObject(resourcesXml, ResourceDto.class);
        return existingResources;
    }

}
