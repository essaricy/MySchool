package com.myschool.integration.processor.gallery;

import java.util.List;

import org.apache.camel.Message;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.myschool.application.dto.GalleryDetailDto;
import com.myschool.infra.remote.ftp.exception.FtpException;
import com.myschool.integration.agent.IntegrationImageResource;
import com.myschool.integration.constant.IntegrationConstant;
import com.myschool.integration.exception.CommandExecutionException;
import com.myschool.integration.exception.CommandProcessException;
import com.myschool.integration.processor.common.AbstractDynamicImageProcessor;

@Component("GalleryImageProcessor")
public class GalleryImageProcessor extends AbstractDynamicImageProcessor {

    /** The Constant LOGGER. */
    private static final Log LOGGER = LogFactory.getLog(GalleryImageProcessor.class);

    /* (non-Javadoc)
     * @see com.myschool.integration.processor.common.AbstractDynamicImageProcessor#preProcess()
     */
    @Override
    public void preProcess() throws CommandProcessException {
    }

    /* (non-Javadoc)
     * @see com.myschool.integration.processor.common.AbstractDynamicImageProcessor#postProcess()
     */
    @Override
    public void postProcess() throws CommandProcessException {
    }

    /* (non-Javadoc)
     * @see com.myschool.integration.processor.common.AbstractDynamicImageProcessor#add(org.apache.camel.Message, java.lang.String)
     */
    @Override
    public void add(Message message, String body) throws CommandExecutionException {
        IntegrationImageResource integrationImageResource = null;

        try {
            GalleryDetailDto gallery = validateAndGetGalleryDetail(body, false);
            String galleryName = gallery.getGalleryName();
            integrationImageResource = integrationImageResourceFactory.getImageResource(
                    IntegrationConstant.GALLERY + galleryName, galleryName);
            createDymanicImageResource(IntegrationConstant.GALLERY, integrationImageResource);
        } catch (FtpException ftpException) {
            throw new CommandExecutionException(ftpException.getMessage(), ftpException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.integration.processor.common.AbstractDynamicImageProcessor#update(org.apache.camel.Message, java.lang.String)
     */
    @Override
    public void update(Message message, String body) throws CommandExecutionException {
        //add(message, body);
    }

    /* (non-Javadoc)
     * @see com.myschool.integration.processor.common.AbstractDynamicImageProcessor#delete(org.apache.camel.Message, java.lang.String)
     */
    @Override
    public void delete(Message message, String body) throws CommandExecutionException {
        IntegrationImageResource integrationImageResource = null;

        try {
            GalleryDetailDto gallery = validateAndGetGalleryDetail(body, false);
            String galleryName = gallery.getGalleryName();
            integrationImageResource = integrationImageResourceFactory.getImageResource(
                    IntegrationConstant.GALLERY + galleryName, galleryName);
            deleteDymanicImageResource(IntegrationConstant.GALLERY, integrationImageResource);
        } catch (FtpException ftpException) {
            throw new CommandExecutionException(ftpException.getMessage(), ftpException);
        }
    }

    public void addGalleryItems(Message message, String body) throws CommandExecutionException {
        IntegrationImageResource integrationImageResource = null;

        GalleryDetailDto gallery = validateAndGetGalleryDetail(body, true);
        String galleryName = gallery.getGalleryName();
        List<GalleryDetailDto> galleryItems = gallery.getGalleryItems();

        integrationImageResource = integrationImageResourceFactory.getImageResource(
                IntegrationConstant.GALLERY + galleryName, galleryName);
        
        for (GalleryDetailDto galleryItem : galleryItems) {
            // For each gallery Item, move the file to the corresponding directory.
            if (galleryItem != null) {
                String galleryItemName = galleryItem.getGalleryName();
                if (galleryItemName != null && galleryItemName.trim().length() != 0) {
                    try {
                        addDymanicImage(integrationImageResource, galleryItemName);
                    } catch (CommandExecutionException commandExecutionException) {
                        LOGGER.error("addGalleryItem " + galleryItemName + ". ERROR=" + commandExecutionException.getMessage());
                        System.err.println(commandExecutionException.getMessage());
                    }
                }
            }
        }
    }

    public void deleteGalleryItems(Message message, String body) throws CommandExecutionException {
        IntegrationImageResource integrationImageResource = null;

        GalleryDetailDto gallery = validateAndGetGalleryDetail(body, true);
        String galleryName = gallery.getGalleryName();
        List<GalleryDetailDto> galleryItems = gallery.getGalleryItems();

        integrationImageResource = integrationImageResourceFactory.getImageResource(
                IntegrationConstant.GALLERY + galleryName, galleryName);
        
        for (GalleryDetailDto galleryItem : galleryItems) {
            // For each gallery Item, move the file to the corresponding directory.
            if (galleryItem != null) {
                String galleryItemName = galleryItem.getGalleryName();
                if (galleryItemName != null && galleryItemName.trim().length() != 0) {
                    try {
                        deleteDynamicImage(IntegrationConstant.GALLERY, integrationImageResource, galleryItemName);
                    } catch (CommandExecutionException commandExecutionException) {
                        System.err.println(commandExecutionException.getMessage());
                    }
                }
            }
        }
    }

    private GalleryDetailDto validateAndGetGalleryDetail(String body, boolean validateItems)
            throws CommandExecutionException {
        GalleryDetailDto gallery = (GalleryDetailDto) tempUtil.toObject(body);
        if (gallery == null) {
            throw new CommandExecutionException("Gallery details are not provided");
        }
        String galleryName = gallery.getGalleryName();
        if (galleryName == null || galleryName.trim().length() == 0) {
            throw new CommandExecutionException("Gallery name is not not provided");
        }
        if (validateItems) {
            List<GalleryDetailDto> galleryItems = gallery.getGalleryItems();
            if (galleryItems == null || galleryItems.isEmpty()) {
                throw new CommandExecutionException("There are no Gallery Items to delete");
            }
        }
        return gallery;
    }

}
