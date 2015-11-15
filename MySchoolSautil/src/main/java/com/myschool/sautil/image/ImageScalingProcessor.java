package com.myschool.sautil.image;

import java.io.File;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.common.exception.ConfigurationException;
import com.myschool.common.exception.FileSystemException;
import com.myschool.infra.filesystem.constants.FileExtension;
import com.myschool.infra.filesystem.util.FileUtil;
import com.myschool.infra.image.agent.ImageScalingAgent;
import com.myschool.infra.image.constants.ImageSize;
import com.myschool.sautil.base.StandAloneUtility;

/**
 * The Class ImageScalingProcessor.
 */
@Component
public class ImageScalingProcessor extends StandAloneUtility {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(ImageScalingProcessor.class);

    /** The Constant IMAGE_SIZE. */
    public static final String IMAGE_SIZE = "--image-size";

    /** The image scaling agent. */
    @Autowired
    private ImageScalingAgent imageScalingAgent;

    /* (non-Javadoc)
     * @see com.myschool.sautil.base.StandAloneUtility#validateParameters()
     */
    @Override
    public void validateParameters() throws ConfigurationException {
        try {
            // Validate external directory option
            String extDirOptionValue = executionProperties.getProperty(OPTION_EXT_DIR);
            if (extDirOptionValue == null) {
                throw new ConfigurationException("Must specify '" + OPTION_EXT_DIR + "' option.");
            }
            File extDir = new File(extDirOptionValue);
            FileUtil.checkDirectory(extDir.getAbsolutePath(),
                    "No such directory " + extDirOptionValue,
                    "Cannot access the directory " + extDirOptionValue
                            + " or it is not a directory at all.");
            String imageSizeOptionValue = executionProperties.getProperty(IMAGE_SIZE);
            if (imageSizeOptionValue != null) {
                ImageSize imageSizeType = ImageSize.getImageType(imageSizeOptionValue);
                if (imageSizeType == ImageSize.ORIGINAL) {
                    throw new ConfigurationException("Invalid value for option '" + IMAGE_SIZE + "'.");
                }
            }
        } catch (FileSystemException fileSystemException) {
            throw new ConfigurationException(fileSystemException.getMessage(), fileSystemException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.sautil.base.StandAloneUtility#startProcess()
     */
    @Override
    public void startProcess() throws Exception {
        ImageSize imageSizeTypeOptionValue = null;

        // Get the external directory.
        String extDirOptionValue = executionProperties.getProperty(OPTION_EXT_DIR);
        if (extDirOptionValue == null) {
            throw new ConfigurationException("Must specify '" + OPTION_EXT_DIR + "' option.");
        }
        File extDir = new File(extDirOptionValue);

        // Process Image sizing.
        String imageSizeOptionValue = executionProperties.getProperty(IMAGE_SIZE);
        imageSizeTypeOptionValue = ImageSize.getImageType(imageSizeOptionValue);
        if (imageSizeTypeOptionValue == null) {
            ImageSize[] values = ImageSize.values();
            for (ImageSize imageSizeType : values) {
                resizeImage(extDir, imageSizeType);
            }
        } else {
            resizeImage(extDir, imageSizeTypeOptionValue);
        }
    }

    /**
     * Size image.
     * 
     * @param extDir the ext dir
     * @param imageSize the image size
     */
    private void resizeImage(File extDir, ImageSize imageSize) {
        if (imageSize == null || imageSize == ImageSize.ORIGINAL) {
            return;
        }
        // exclude files that are not  original and process them 
        LOGGER.info("Creating " + imageSize + " images in " + extDir);
        File[] files = imageScalingAgent.getOriginalImages(extDir);
        System.out.println("Creating " + imageSize + " images in " + extDir);
        for (int index = 0; index < files.length; index++) {
            try {
                File file = files[index];
                if (file.isDirectory()) {
                    resizeImage(file, imageSize);
                } else if (file.isFile() && FileExtension.isImage(FileUtil.getExtension(file.getName()))) {
                    if (imageSize == ImageSize.THUMBNAIL) {
                        imageScalingAgent.createThumbnailImage(file);
                    } else if (imageSize == ImageSize.PASSPORT) {
                        imageScalingAgent.createPassportSizeImage(file);
                    }
                }
            } catch (FileSystemException fileSystemException) {
                LOGGER.error(fileSystemException.getMessage(), fileSystemException);
                System.err.println(fileSystemException.getMessage());
            }
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.sautil.base.StandAloneUtility#getUsageText()
     */
    @Override
    public String getUsageText() {
        return null;
    }

}
