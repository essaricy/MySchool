package com.myschool.application.domain;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.application.assembler.GalleryDataAssembler;
import com.myschool.application.dto.GalleryDetailDto;
import com.myschool.application.dto.MySchoolProfileDto;
import com.myschool.application.dto.ResourceDto;
import com.myschool.common.exception.DataException;
import com.myschool.common.util.StringUtil;
import com.myschool.infra.filesystem.util.FileUtil;
import com.myschool.infra.image.agent.ImageScalingAgent;
import com.myschool.infra.media.agent.MediaServerAgent;
import com.myschool.infra.media.exception.ResourceException;
import com.myschool.infra.middleware.agent.OutboundMessageAgent;

/**
 * The Class GalleryManager.
 */
@Component
public class GalleryManager {

    /** The media server agent. */
    @Autowired
    private MediaServerAgent mediaServerAgent;

    /** The image scaling agent. */
    @Autowired
    private ImageScalingAgent imageScalingAgent;

    /** The profile manager. */
    @Autowired
    private ProfileManager profileManager;

    /** The outbound message agent. */
    @Autowired
    private OutboundMessageAgent outboundMessageAgent;

    /**
     * Gets the gallery details.
     * 
     * @return the gallery details
     * @throws DataException the data exception
     */
    public List<GalleryDetailDto> getAll() throws DataException {
        List<GalleryDetailDto> galleries = null;
        try {
            // Get the list of galleries
            List<ResourceDto> galleryResources = mediaServerAgent.getGallery();
            MySchoolProfileDto myschoolProfile = profileManager.getMyschoolProfile();
            String pinnedGallery = myschoolProfile.getPinnedGallery();
            galleries = GalleryDataAssembler.createGalleryDetails(galleryResources, pinnedGallery);
        } catch (ResourceException resourceException) {
            throw new DataException(resourceException.getMessage(), resourceException);
        }
        return galleries;
    }

    /**
     * Gets the all in detail.
     * 
     * @return the all in detail
     * @throws DataException the data exception
     */
    public List<GalleryDetailDto> getAllInDetail() throws DataException {
        List<GalleryDetailDto> galleries = getAll();
        if (galleries != null && !galleries.isEmpty()) {
            for (GalleryDetailDto gallery : galleries) {
                gallery.setGalleryItems(getGalleryItems(gallery.getGalleryName()));
            }
        }
        return galleries;
    }

    /**
     * Gets the gallery.
     * 
     * @param galleryName the gallery name
     * @return the gallery
     * @throws DataException the data exception
     */
    public GalleryDetailDto getGallery(String galleryName) throws DataException {
        GalleryDetailDto gallery = new GalleryDetailDto();
        gallery.setGalleryName(galleryName);
        gallery.setGalleryItems(getGalleryItems(galleryName));
        return gallery;
    }

    /**
     * Gets the pinned.
     * 
     * @return the pinned
     * @throws DataException the data exception
     */
    public GalleryDetailDto getPinned() throws DataException {
        MySchoolProfileDto myschoolProfile = profileManager.getMyschoolProfile();
        String pinnedGallery = myschoolProfile.getPinnedGallery();
        return getGallery(pinnedGallery);
    }

    /**
     * Pin gallery.
     * 
     * @param galleryName the gallery name
     * @throws DataException the data exception
     */
    public void pin(String galleryName) throws DataException {
        try {
            List<ResourceDto> galleryResources = mediaServerAgent.getGallery();
            if (galleryName == null || galleryResources == null || galleryResources.isEmpty()) {
                throw new DataException("There are no galleries present to pin.");
            }
            boolean exists = false;
            for (ResourceDto galleryResource : galleryResources) {
                String name = galleryResource.getName();
                if (galleryName.equals(name)) {
                    exists = true;
                    break;
                }
            }
            if (!exists) {
                throw new DataException("Gallery '" + galleryName + "' does not exist");
            }
            List<GalleryDetailDto> galleryItems = getGalleryItems(galleryName);
            if (galleryItems == null || galleryItems.isEmpty()) {
                throw new DataException("Cannot Pin Gallery '" + galleryName
                        + "'. There are no images in this gallery.");
            }
            profileManager.pinGallery(galleryName);
        } catch (ResourceException resourceException) {
            throw new DataException(resourceException.getMessage(), resourceException);
        }
    }

    /**
     * Creates the.
     * 
     * @param galleryName the gallery name
     * @return true, if successful
     * @throws DataException the data exception
     */
    public boolean create(String galleryName) throws DataException {
        boolean success = false;
        try {
            if (StringUtil.isNullOrBlank(galleryName)) {
                throw new DataException("Gallery Name cannot be empty");
            }
            galleryName = galleryName.trim();
            List<GalleryDetailDto> galleryDetails = getAll();
            if (galleryDetails != null && !galleryDetails.isEmpty()) {
                for (GalleryDetailDto galleryDetail : galleryDetails) {
                    if (galleryName.equalsIgnoreCase(galleryDetail.getGalleryName())) {
                        throw new DataException("Gallery '" + galleryName + "' already exists");
                    }
                }
            }
            // Send Command
            outboundMessageAgent.sendMessage("Create Gallery: " + galleryName);
        } catch (Exception fileSystemException) {
            throw new DataException(fileSystemException.getMessage(),
                    fileSystemException);
        }
        return success;
    }

    /**
     * Update.
     * 
     * @param oldGalleryName the old gallery name
     * @param newGalleryName the new gallery name
     * @return true, if successful
     * @throws DataException the data exception
     */
    public boolean update(String oldGalleryName, String newGalleryName)
            throws DataException {
        boolean success = false;
        try {
            if (StringUtil.isNullOrBlank(oldGalleryName)) {
                throw new DataException("Old Gallery Name cannot be empty");
            }
            if (StringUtil.isNullOrBlank(newGalleryName)) {
                throw new DataException("New Gallery Name cannot be empty");
            }
            oldGalleryName = oldGalleryName.trim();
            newGalleryName = newGalleryName.trim();
            if (oldGalleryName.equalsIgnoreCase(newGalleryName)) {
                throw new DataException("New Gallery Name is same as the old gallery name");
            }
            List<GalleryDetailDto> galleryDetails = getAll();
            if (galleryDetails == null || galleryDetails.isEmpty()) {
                throw new DataException(
                        "There are no galleries present to rename");
            }
            // Old gallery name must exist and new gallery name must not exist
            boolean galleryExists = false, newGalleryPresent = false;
            for (GalleryDetailDto galleryDetail : galleryDetails) {
                String galleryName = galleryDetail.getGalleryName();
                if (oldGalleryName.equals(galleryName)) {
                    galleryExists = true;
                }
                if (newGalleryName.equals(galleryName)) {
                    newGalleryPresent = true;
                }
            }
            if (!galleryExists) {
                throw new DataException("Gallery '" + oldGalleryName + "' does not exist");
            }
            if (newGalleryPresent) {
                throw new DataException("A Gallery exists with name '" + newGalleryName + "'");
            }
            // Send Command
            outboundMessageAgent.sendMessage("Update Gallery Name from: " + oldGalleryName + " to " + newGalleryName);
            success = true;
        } catch (Exception fileSystemException) {
            throw new DataException(fileSystemException.getMessage(),
                    fileSystemException);
        }
        return success;
    }

    /**
     * Delete.
     * 
     * @param galleryName the gallery name
     * @return true, if successful
     * @throws DataException the data exception
     */
    public boolean delete(String galleryName) throws DataException {
        boolean success = false;
        try {
            if (StringUtil.isNullOrBlank(galleryName)) {
                throw new DataException("Gallery Name cannot be empty");
            }
            galleryName = galleryName.trim();
            // gallery name must exist
            List<GalleryDetailDto> galleryDetails = getAll();
            if (galleryDetails == null || galleryDetails.isEmpty()) {
                throw new DataException("There are no galleries present to delete");
            }
            boolean exists = false;
            for (GalleryDetailDto galleryDetail : galleryDetails) {
                if (galleryName.equals(galleryDetail.getGalleryName())) {
                    exists = true;
                    break;
                }
            }
            if (!exists) {
                throw new DataException("Gallery '" + galleryName + "' does not exist");
            }
            // Remove pin if its a pinned gallery
            MySchoolProfileDto myschoolProfile = profileManager.getMyschoolProfile();
            String pinnedGallery = myschoolProfile.getPinnedGallery();
            if (galleryName.equals(pinnedGallery)) {
                profileManager.pinGallery(null);
            }
            // Send Command
            outboundMessageAgent.sendMessage("Delete Gallery: " + galleryName);
            success = true;
        } catch (Exception fileSystemException) {
            throw new DataException(fileSystemException.getMessage(),
                    fileSystemException);
        }
        return success;
    }

    /**
     * Adds the.
     * 
     * @param galleryName the gallery name
     * @param galleryItemFile the gallery item file
     * @return true, if successful
     * @throws DataException the data exception
     */
    public boolean add(String galleryName, File galleryItemFile) throws DataException {
        boolean success = false;
        try {
            if (StringUtil.isNullOrBlank(galleryName)) {
                throw new DataException("Gallery Name cannot be empty");
            }
            if (galleryItemFile == null) {
                throw new DataException("Could not add file as it is missing.");
            }
            String galleryItemName = galleryItemFile.getName();
            if (StringUtil.isNullOrBlank(galleryItemName)) {
                throw new DataException("Gallery Item Name cannot be empty");
            }
            FileUtil.checkFile(galleryItemFile, "Gallery Item file is missing", "Gallery Item file does not exist");
            galleryName = galleryName.trim();
            galleryItemName = galleryItemName.trim();

            // gallery name must exist
            GalleryDetailDto existingGalleryDetail = null;
            List<GalleryDetailDto> galleries = getAll();
            if (galleries == null || galleries.isEmpty()) {
                throw new DataException("There are no galleries present to add images");
            }
            for (int index = 0; index < galleries.size(); index++) {
                existingGalleryDetail = galleries.get(index);
                if (galleryName.equals(existingGalleryDetail.getGalleryName())) {
                    break;
                }
            }
            if (existingGalleryDetail == null) {
                throw new DataException("Gallery '" + galleryName + "' does not exist");
            }
            // Send Command
            outboundMessageAgent.sendMessage("Add Image " + galleryItemName + " to Gallery: " + galleryName);
            success = true;
        } catch (Exception fileSystemException) {
            throw new DataException(fileSystemException.getMessage(),
                    fileSystemException);
        }
        return success;
    }

    /**
     * Delete.
     * 
     * @param galleryName the gallery name
     * @param galleryItemName the gallery item name
     * @return true, if successful
     * @throws DataException the data exception
     */
    public boolean delete(String galleryName, String galleryItemName)
            throws DataException {
        boolean success = false;
        try {
            if (StringUtil.isNullOrBlank(galleryName)) {
                throw new DataException("Gallery Name cannot be empty");
            }
            if (StringUtil.isNullOrBlank(galleryItemName)) {
                throw new DataException("Gallery Item name is not present");
            }
            galleryItemName = galleryItemName.trim();
            galleryName = galleryName.trim();

            // gallery name must exist
            List<GalleryDetailDto> galleryDetails = getAllInDetail();
            if (galleryDetails == null || galleryDetails.isEmpty()) {
                throw new DataException("There are no galleries present to delete images");
            }
            boolean galleryExists = false;
            for (GalleryDetailDto existingGalleryDetail : galleryDetails) {
                if (galleryName.equals(existingGalleryDetail.getGalleryName())) {
                    galleryExists = true;
                    break;
                }
            }
            if (!galleryExists) {
                throw new DataException("Gallery '" + galleryName + "' does not exist");
            }
            // Send Command
            outboundMessageAgent.sendMessage("Delete Image " + galleryItemName + " from Gallery: " + galleryName);
            success = true;
        } catch (Exception fileSystemException) {
            throw new DataException(fileSystemException.getMessage(),
                    fileSystemException);
        }
        return success;
    }

    /**
     * Delete.
     * 
     * @param galleryName the gallery name
     * @param galleryItemNames the gallery item names
     * @return the list
     * @throws DataException the data exception
     */
    public List<String> delete(String galleryName, List<String> galleryItemNames)
            throws DataException {
        List<String> result = new ArrayList<String>();

        if (StringUtil.isNullOrBlank(galleryName)) {
            throw new DataException("Gallery Name cannot be empty");
        }
        if (galleryItemNames == null || galleryItemNames.isEmpty()) {
            throw new DataException("Gallery Item information is not present");
        }
        // Get the pinned directory to pin again after this operation
        galleryName = galleryName.trim();
        // gallery name must exist
        List<GalleryDetailDto> galleryDetails = getAll();
        if (galleryDetails == null || galleryDetails.isEmpty()) {
            throw new DataException("There are no galleries present to delete images");
        }
        boolean galleryExists = false;
        for (GalleryDetailDto existingGalleryDetail : galleryDetails) {
            if (galleryName.equals(existingGalleryDetail.getGalleryName())) {
                galleryExists = true;
                break;
            }
        }
        if (!galleryExists) {
            throw new DataException("Gallery '" + galleryName + "' does not exist");
        }

        for (String galleryItemName : galleryItemNames) {
            try {
                if (StringUtil.isNullOrBlank(galleryItemName)) {
                    throw new DataException("Gallery Item Name is not present.");
                } else {
                    result.add("Gallery Item will be deleted.");
                }
            } catch (Exception exception) {
                result.add("Error: " + exception.getMessage());
            }
        }
        // Send Command
        outboundMessageAgent.sendMessage("Delete Images " + galleryItemNames + " from Gallery: " + galleryName);
        return result;
    }

    /**
     * Gets the gallery items.
     * 
     * @param galleryName the gallery name
     * @return the gallery items
     * @throws DataException the data exception
     */
    private List<GalleryDetailDto> getGalleryItems(String galleryName) throws DataException {
        List<GalleryDetailDto> galleryItems = null;
        try {
            List<ResourceDto> galleryItemResources = mediaServerAgent.getGalleryItems(galleryName);
            galleryItems = GalleryDataAssembler.createGalleryDetails(galleryItemResources, null);
        } catch (ResourceException resourceException) {
            throw new DataException(resourceException.getMessage(), resourceException);
        }
        return galleryItems;
    }

}
