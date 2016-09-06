package com.myschool.integration.processor.common;

import java.io.File;

import org.apache.camel.Message;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.common.exception.FileSystemException;
import com.myschool.file.util.FileUtil;
import com.myschool.image.constant.ImageSize;
import com.myschool.infra.remote.ftp.exception.FtpException;
import com.myschool.integration.agent.IntegrationImageResource;
import com.myschool.integration.agent.IntegrationImageResourceFactory;
import com.myschool.integration.exception.CommandExecutionException;
import com.myschool.integration.exception.CommandProcessException;

/**
 * The Class AbstractDynamicImageProcessor.
 */
@Component
public abstract class AbstractDynamicImageProcessor extends AbstractProcessor {

    /** The Constant LOGGER. */
    private static final Log LOGGER = LogFactory.getLog(AbstractDynamicImageProcessor.class);

    /** The integration image resource factory. */
    @Autowired
    protected IntegrationImageResourceFactory integrationImageResourceFactory;

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

    protected void createDymanicImageResource(String mediaType,
            IntegrationImageResource integrationImageResource)
            throws CommandExecutionException, FtpException {
        // Create media in inbound
        createDymanicImageResource(integrationImageResource.getIntegrationInboundDynamic());
        createDymanicImageResource(integrationImageResource.getIntegrationInboundDynamicPassport());
        createDymanicImageResource(integrationImageResource.getIntegrationInboundDynamicThumbnail());
        // Create media in outbound
        createDymanicImageResource(integrationImageResource.getIntegrationOutboundDynamic());
        createDymanicImageResource(integrationImageResource.getIntegrationOutboundDynamicPassport());
        createDymanicImageResource(integrationImageResource.getIntegrationOutboundDynamicThumbnail());
        // Create media in media server
        mediaServerFTPClientProxy.createDirectory(mediaType, integrationImageResource.getMediaDynamic());
        mediaServerFTPClientProxy.createDirectory(mediaType, integrationImageResource.getMediaDynamicPassport());
        mediaServerFTPClientProxy.createDirectory(mediaType, integrationImageResource.getMediaDynamicThumbnail());
    }

    protected void deleteDymanicImageResource(String mediaType,
            IntegrationImageResource integrationImageResource)
            throws CommandExecutionException, FtpException {
        // delete media in inbound
        deleteLocalImageResource(integrationImageResource.getIntegrationInboundDynamicPassport());
        deleteLocalImageResource(integrationImageResource.getIntegrationInboundDynamicThumbnail());
        deleteLocalImageResource(integrationImageResource.getIntegrationInboundDynamic());
        // delete media in outbound
        deleteLocalImageResource(integrationImageResource.getIntegrationOutboundDynamicPassport());
        deleteLocalImageResource(integrationImageResource.getIntegrationOutboundDynamicThumbnail());
        deleteLocalImageResource(integrationImageResource.getIntegrationOutboundDynamic());
        // delete media in media server
        mediaServerFTPClientProxy.deleteDirectory(mediaType, integrationImageResource.getMediaDynamic());
    }

    /**
     * 
     * @param integrationImageResource the integration image resource
     * @param imageName the image name
     * @throws CommandExecutionException the command execution exception
     */
    protected void addDymanicImage(IntegrationImageResource integrationImageResource,
            String imageName) throws CommandExecutionException {
        addDymanicImage(integrationImageResource, null, imageName);
    }

    /**
     * 
     * @param integrationImageResource the integration image resource
     * @param identity the identity
     * @param imageName the image name
     * @throws CommandExecutionException the command execution exception
     */
    protected void addDymanicImage(
            IntegrationImageResource integrationImageResource, String identity,
            String imageName) throws CommandExecutionException {

        try {
            if (integrationImageResource == null) {
                throw new CommandExecutionException("Could not find a proper integrationImageResource for processing.");
            }
            File integrationInboundBase = integrationImageResource.getIntegrationInboundBase();
            File integrationInboundDynamic = integrationImageResource.getIntegrationInboundDynamic();
            File integrationInboundDynamicPassport = integrationImageResource.getIntegrationInboundDynamicPassport();
            File integrationInboundDynamicThumbnail = integrationImageResource.getIntegrationInboundDynamicThumbnail();

            File integrationOutboundDynamic = integrationImageResource.getIntegrationOutboundDynamic();
            File integrationOutboundDynamicPassport = integrationImageResource.getIntegrationOutboundDynamicPassport();
            File integrationOutboundDynamicThumbnail = integrationImageResource.getIntegrationOutboundDynamicThumbnail();

            // Create the dynamic directories if already does not exist
            createDymanicImageResource(integrationInboundDynamicPassport);
            createDymanicImageResource(integrationInboundDynamicThumbnail);
            createDymanicImageResource(integrationOutboundDynamicPassport);
            createDymanicImageResource(integrationOutboundDynamicThumbnail);
            // Pick up the image to process from the inbound base directory
            File imageToProcess = new File(integrationInboundBase, imageName);
            String imagePath = imageToProcess.getAbsolutePath();
            LOGGER.info("Processing Image: " + imagePath);
            if (!imageToProcess.exists() || !imageToProcess.isFile() || !imageToProcess.canWrite()) {
                throw new CommandExecutionException("Image does not exists of does not have privileges to process: " + imagePath);
            }
            // Move the image from inbound base directory to inbound dynamic directory.
            String extension = FileUtil.getExtension(imageToProcess);
            String newFileName = null;
            if (identity == null) {
                newFileName = imageName;
            } else {
                if (extension == null || extension.trim().length() == 0) {
                    newFileName = identity;
                } else {
                    newFileName = identity + "." + extension;
                }
            }
            File identityAssignedImage = new File(integrationInboundDynamic, newFileName);
            if (identityAssignedImage.exists()) {
                identityAssignedImage.delete();
                Thread.sleep(1000);
            }
            LOGGER.info("identityAssignedImage=" + identityAssignedImage.getAbsolutePath());
            boolean moved = imageToProcess.renameTo(identityAssignedImage);
            if (!moved) {
                throw new CommandExecutionException("Unable to move image from "
                        + imageToProcess.getAbsolutePath() + " to " + identityAssignedImage.getAbsolutePath());
            }

            // Generate scaled images for the given image in inbound dynamic directory
            File passportImage = tempUtil.resizeImage(identityAssignedImage, ImageSize.PASSPORT);
            LOGGER.info("passportImage=" + passportImage.getAbsolutePath());
            File thumbnailImage = tempUtil.resizeImage(identityAssignedImage, ImageSize.THUMBNAIL);
            LOGGER.info("thumbnailImage=" + thumbnailImage.getAbsolutePath());

            // Move the original image and scaled images to the outbound dynamic directory
            File outboundDynamicImage = new File(integrationOutboundDynamic, newFileName);
            File outboundDynamicPassport = new File(integrationOutboundDynamicPassport, newFileName);
            File outboundDynamicThumbnail = new File(integrationOutboundDynamicThumbnail, newFileName);

            identityAssignedImage.renameTo(outboundDynamicImage);
            passportImage.renameTo(outboundDynamicPassport);
            thumbnailImage.renameTo(outboundDynamicThumbnail);
            LOGGER.info("All Complegted");
        } catch (FileSystemException fileSystemException) {
            throw new CommandExecutionException(fileSystemException.getMessage(), fileSystemException);
        } catch (InterruptedException interruptedException) {
            throw new CommandExecutionException(interruptedException.getMessage(), interruptedException);
        }
    }

    /**
     * Delete dynamic image.
     * 
     * @param integrationImageResource the integration image resource
     * @param identity the identity
     * @throws CommandExecutionException the command execution exception
     */
    protected void deleteDynamicImage(String mediaType,
            IntegrationImageResource integrationImageResource, String identity)
            throws CommandExecutionException {

        String mediaDynamic = integrationImageResource.getMediaDynamic();
        String mediaDynamicPassport = integrationImageResource.getMediaDynamicPassport();
        String mediaDynamicThumbnail = integrationImageResource.getMediaDynamicThumbnail();
        try {

            String originalImage = mediaServerFTPClientProxy.getFileName(mediaType, mediaDynamic, identity);
            mediaServerFTPClientProxy.deleteFile(mediaType, mediaDynamic, originalImage);

            String passportImage = mediaServerFTPClientProxy.getFileName(mediaType, mediaDynamicPassport, identity);
            mediaServerFTPClientProxy.deleteFile(mediaType, mediaDynamicPassport, passportImage);

            String thumbnailImage = mediaServerFTPClientProxy.getFileName(mediaType, mediaDynamicThumbnail, identity);
            mediaServerFTPClientProxy.deleteFile(mediaType, mediaDynamicThumbnail, thumbnailImage);
        } catch (FtpException ftpException) {
            throw new CommandExecutionException(ftpException.getMessage(), ftpException);
        }
    }

    protected void moveDynamicImage(String mediaType, IntegrationImageResource fromImageResource,
            IntegrationImageResource toImageResource, String identity)
            throws CommandExecutionException {

        File toIntegrationInboundBase = toImageResource.getIntegrationInboundBase();

        String mediaDynamic = fromImageResource.getMediaDynamic();
        String mediaDynamicPassport = fromImageResource.getMediaDynamicPassport();
        String mediaDynamicThumbnail = fromImageResource.getMediaDynamicThumbnail();

        try {
            LOGGER.debug("identity = " + identity);
            String fileName = mediaServerFTPClientProxy.getFileName(mediaType, mediaDynamic, identity);
            LOGGER.debug("fileName = " + fileName);
            if (fileName == null) {
                throw new FtpException("Unable to find remote file: " + mediaDynamic + "/" + fileName);
            }

            // Move the file from remote to local directory
            downloadFile(mediaType, toIntegrationInboundBase, mediaDynamic, fileName);
            LOGGER.debug("File " + mediaDynamic + "/" + fileName + " has been downloaded.");
            mediaServerFTPClientProxy.deleteFile(mediaType, mediaDynamic, fileName);
            LOGGER.debug("Deleted file: " + mediaDynamic + "/" + fileName);
            mediaServerFTPClientProxy.deleteFile(mediaType, mediaDynamicPassport, fileName);
            LOGGER.debug("Deleted file: " + mediaDynamicPassport + "/" + fileName);
            mediaServerFTPClientProxy.deleteFile(mediaType, mediaDynamicThumbnail, fileName);
            LOGGER.debug("Deleted file: " + mediaDynamicThumbnail + "/" + fileName);
            addDymanicImage(toImageResource, fileName);
        } catch (FtpException ftpException) {
            throw new CommandExecutionException(ftpException.getMessage(), ftpException);
        }
    }

    private void downloadFile(String mediaType, File downloadDirectory, String remoteDirectory,
            String remoteFileName) throws FtpException {
        if (!downloadDirectory.exists()) {
            boolean success = downloadDirectory.mkdirs();
            if (!success) {
                throw new FtpException("Source directory does not exist and unable to create: " + downloadDirectory.getAbsolutePath());
            }
        }
        File srcFile = new File(downloadDirectory, remoteFileName);
        LOGGER.debug("downloading file = " + remoteDirectory + "/" + remoteFileName);
        mediaServerFTPClientProxy.downloadFile(mediaType, srcFile, remoteDirectory, remoteFileName);
        if (!srcFile.exists()) {
            throw new FtpException("File is not downloaded : " + srcFile.getAbsolutePath());
        }
        LOGGER.debug("downloaded file = " + srcFile.getAbsolutePath());
    }

    private void createDymanicImageResource(File file) throws CommandExecutionException {
        if (!file.exists()) {
            boolean mkdirs = file.mkdirs();
            if (!mkdirs) {
                throw new CommandExecutionException("Cannot create: " + file.getAbsolutePath());
            }
        }
    }

    private void deleteLocalImageResource(File file) throws CommandExecutionException {
        if (file.exists()) {
            boolean deleted = file.delete();
            if (!deleted) {
                throw new CommandExecutionException("Cannot delete: " + file.getAbsolutePath());
            }
        }
    }

}
