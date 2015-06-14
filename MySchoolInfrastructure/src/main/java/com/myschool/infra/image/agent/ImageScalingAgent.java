package com.myschool.infra.image.agent;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.common.exception.AgentException;
import com.myschool.common.exception.ConfigurationException;
import com.myschool.common.exception.FileSystemException;
import com.myschool.common.util.PropertiesUtil;
import com.myschool.infra.agent.AbstractAgent;
import com.myschool.infra.filesystem.filefilter.ImageResizingFileFilter;
import com.myschool.infra.image.constants.ImageScalingConstants;
import com.myschool.infra.image.constants.ImageSize;
import com.myschool.infra.image.dto.ImageResizingOptionDto;

/**
 * The Class ImageScalingAgent.
 */
@Component
public abstract class ImageScalingAgent extends AbstractAgent {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(ImageScalingAgent.class);

    /** The image resizing file filter. */
    @Autowired
    private ImageResizingFileFilter imageResizingFileFilter;

    /** The thumbnail image resizing option. */
    private ImageResizingOptionDto thumbnailImageResizingOption;

    /** The passport image resizing option. */
    private ImageResizingOptionDto passportImageResizingOption;

    /* (non-Javadoc)
     * @see com.myschool.infra.agent.Agent#loadConfiguration(java.io.File)
     */
    @Override
    public void loadConfiguration(File configFile)
            throws ConfigurationException {
        try {
            properties = PropertiesUtil.loadProperties(configFile);
            // prepare thumbnail resize options
            thumbnailImageResizingOption = new ImageResizingOptionDto();
            thumbnailImageResizingOption.setHeight(Integer.parseInt(properties.getProperty(ImageScalingConstants.THUMBNAIL_HEIGHT)));
            thumbnailImageResizingOption.setFolderName(properties.getProperty(ImageScalingConstants.THUMBNAIL_FOLDER_NAME));
            thumbnailImageResizingOption.setWidth(Integer.parseInt(properties.getProperty(ImageScalingConstants.THUMBNAIL_WIDTH)));
            // prepare passport size photo resize options
            passportImageResizingOption = new ImageResizingOptionDto();
            passportImageResizingOption.setHeight(Integer.parseInt(properties.getProperty(ImageScalingConstants.PASSPORT_HEIGHT)));
            passportImageResizingOption.setFolderName(properties.getProperty(ImageScalingConstants.PASSPORT_FOLDER_NAME));
            passportImageResizingOption.setWidth(Integer.parseInt(properties.getProperty(ImageScalingConstants.PASSPORT_WIDTH)));
        } catch (FileSystemException fileSystemException) {
            throw new ConfigurationException(fileSystemException.getMessage(), fileSystemException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.infra.Agent#validate()
     */
    @Override
    public void validate() throws AgentException {
        // No validation planned for thie agent.
    }

    /**
     * Resize image.
     * 
     * @param file the file
     * @param resizedFile the resized file
     * @param imageResizeType the image resize type
     * @param imageResizingOption the image resizing option
     * @throws IOException Signals that an I/O exception has occurred.
     */
    protected abstract void resizeImage(File file, File resizedFile,
            ImageSize imageResizeType,
            ImageResizingOptionDto imageResizingOption) throws IOException;

    /**
     * Creates the resize images.
     * 
     * @param file the file
     * @throws FileSystemException the file system exception
     */
    public void createResizeImages(File file) throws FileSystemException {
        createThumbnailImage(file);
        createPassportSizeImage(file);
    }

    /**
     * Creates the thumbnail image.
     * 
     * @param file the file
     * @return the file
     * @throws FileSystemException the file system exception
     */
    public File createThumbnailImage(File file) throws FileSystemException {
        return resizeImage(file, ImageSize.THUMBNAIL);
    }

    /**
     * Creates the passport size image.
     * 
     * @param file the file
     * @return the file
     * @throws FileSystemException the file system exception
     */
    public File createPassportSizeImage(File file) throws FileSystemException {
        return resizeImage(file, ImageSize.PASSPORT);
    }

    /**
     * Resize image.
     * 
     * @param file the file
     * @param imageResizeType the image resize type
     * @return the file
     * @throws FileSystemException the file system exception
     */
    private File resizeImage(File file, ImageSize imageResizeType) throws FileSystemException {
        File resizedFile = null;
        File parentDirectory = null;
        File imageResizeDirectory = null;
        try {
            String absolutePath = file.getAbsolutePath();
            boolean isResizedFile = false;
            if (imageResizeType == ImageSize.THUMBNAIL) {
                isResizedFile = isThumbnailImage(file);
            } else if (imageResizeType == ImageSize.PASSPORT) {
                isResizedFile = isPassportSizeImage(file);
            }

            if (!isResizedFile) {
                LOGGER.debug("Creating " + imageResizeType + " for the file: " + absolutePath);

                // Check if thumb directory exists or not. If does not exist then create one
                parentDirectory = file.getParentFile();
                if (imageResizeType == ImageSize.THUMBNAIL) {
                    imageResizeDirectory = new File(parentDirectory, thumbnailImageResizingOption.getFolderName());
                } else if (imageResizeType == ImageSize.PASSPORT) {
                    imageResizeDirectory = new File(parentDirectory, passportImageResizingOption.getFolderName());
                }
                if (!imageResizeDirectory.exists()) {
                    imageResizeDirectory.mkdir();
                }
                // Create thumb file.
                resizedFile = new File(imageResizeDirectory, file.getName());
                LOGGER.debug(imageResizeType + " file name will be " + resizedFile.getAbsolutePath());
                if (imageResizeType == ImageSize.THUMBNAIL) {
                    resizeImage(file, resizedFile, imageResizeType, thumbnailImageResizingOption);
                } else if (imageResizeType == ImageSize.PASSPORT) {
                    resizeImage(file, resizedFile, imageResizeType, passportImageResizingOption);
                }
            }
        } catch (IOException ioException) {
            throw new FileSystemException(ioException.getMessage(), ioException);
        }
        return resizedFile;
    }

    /**
     * Checks if is thumbnail image.
     * 
     * @param file the file
     * @return true, if is thumbnail image
     */
    public boolean isThumbnailImage(File file) {
        return file.getName().equalsIgnoreCase(thumbnailImageResizingOption.getFolderName());
    }

    /**
     * Checks if is passport size image.
     * 
     * @param file the file
     * @return true, if is passport size image
     */
    public boolean isPassportSizeImage(File file) {
        return file.getName().equalsIgnoreCase(passportImageResizingOption.getFolderName());
    }

    /**
     * Gets the original images.
     * 
     * @param imageDirectory the image directory
     * @return the original images
     */
    public File[] getOriginalImages(File imageDirectory) {
        if (imageDirectory != null) {
        	// TODO sort by file creation time in to make it intact.
            return imageDirectory.listFiles(imageResizingFileFilter);
        }
        return null;
    }

    /**
     * Gets the image.
     * 
     * @param file the file
     * @param imageResizeType the image resize type
     * @return the image
     */
    public File getImage(File file, ImageSize imageResizeType) {
        File image = null;
        File imageResizingDir = null;
        if (file != null) {
            File parentFile = file.getParentFile();
            if (imageResizeType == ImageSize.THUMBNAIL) {
                imageResizingDir = new File(parentFile, thumbnailImageResizingOption.getFolderName());
                image = new File(imageResizingDir, file.getName());
            } else if (imageResizeType == ImageSize.PASSPORT) {
                imageResizingDir = new File(parentFile, passportImageResizingOption.getFolderName());
                image = new File(imageResizingDir, file.getName());
            } else if (imageResizeType == ImageSize.ORIGINAL) {
                image = file;
            }
        }
        return image;
    }

}
