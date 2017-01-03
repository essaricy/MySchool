package com.myschool.application.domain;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.application.assembler.GalleryDataAssembler;
import com.myschool.application.dto.GalleryDetailDto;
import com.myschool.infra.storage.StorageAccessAgent;
import com.myschool.infra.storage.exception.StorageAccessException;
import com.myschool.organization.dao.OrganizationManager;
import com.myschool.organization.dto.OrganizationPreferences;
import com.myschool.storage.dto.StorageItem;
import com.quasar.core.exception.DataException;
import com.quasar.core.exception.FileSystemException;
import com.quasar.core.util.FileUtil;
import com.quasar.core.util.StringUtil;

/**
 * The Class GalleryManager.
 */
@Component
public class GalleryManager {

    @Autowired
    private OrganizationManager organizationManager;

    /** The storage access agent. */
    @Autowired
    private StorageAccessAgent storageAccessAgent;

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
            List<StorageItem> storageItems = storageAccessAgent.GALLERY_STORAGE.getAll();
            OrganizationPreferences preferences = organizationManager.getOrganizationPreferences();
            String pinnedGallery = preferences.getDefaultGallery();
            galleries = GalleryDataAssembler.createGalleryDetails(storageItems, pinnedGallery);
        } catch (StorageAccessException storageAccessException) {
            throw new DataException(storageAccessException.getMessage(), storageAccessException);
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
        OrganizationPreferences preferences = organizationManager.getOrganizationPreferences();
        String pinnedGallery = preferences.getDefaultGallery();
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
            List<StorageItem> storageItems  = storageAccessAgent.GALLERY_STORAGE.getAll();
            if (galleryName == null || storageItems == null || storageItems.isEmpty()) {
                throw new DataException("There are no galleries present to pin.");
            }
            boolean exists = false;
            for (StorageItem storageItem : storageItems) {
                String name = storageItem.getName();
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
            organizationManager.pinGallery(galleryName);
        } catch (StorageAccessException storageAccessException) {
            throw new DataException(storageAccessException.getMessage(), storageAccessException);
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

            List<StorageItem> storageItems = storageAccessAgent.GALLERY_STORAGE.getAll();
            if (storageItems != null && !storageItems.isEmpty()) {
                for (StorageItem storageItem : storageItems) {
                    if (galleryName.equalsIgnoreCase(storageItem.getName())) {
                        throw new DataException("Gallery '" + galleryName + "' already exists");
                    }
                }
            }
            success = storageAccessAgent.GALLERY_STORAGE.addStore(galleryName);
        } catch (StorageAccessException storageAccessException) {
            throw new DataException(storageAccessException.getMessage(), storageAccessException);
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
            OrganizationPreferences preferences = organizationManager.getOrganizationPreferences();
            String pinnedGallery = preferences.getDefaultGallery();
            if (galleryName.equals(pinnedGallery)) {
                organizationManager.pinGallery(null);
            }
            success = storageAccessAgent.GALLERY_STORAGE.delete(galleryName);
        } catch (StorageAccessException storageAccessException) {
            throw new DataException(storageAccessException.getMessage(), storageAccessException);
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
    public GalleryDetailDto add(String galleryName, File galleryItemFile) throws DataException {
        GalleryDetailDto galleryDetail = null;
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

            StorageItem storageItem = storageAccessAgent.GALLERY_STORAGE.addStoreItem(galleryName, galleryItemFile);
            if (storageItem != null) {
                galleryDetail = GalleryDataAssembler.createGalleryDetail(storageItem, null);
                if (galleryItemFile.exists() && galleryItemFile.isFile()) {
                    //TODO: delete file after processing. galleryItemFile.delete();
                }
            }
        } catch (FileSystemException fileSystemException) {
            throw new DataException(fileSystemException.getMessage(), fileSystemException);
        } catch (StorageAccessException storageAccessException) {
            throw new DataException(storageAccessException.getMessage(), storageAccessException);
        }
        return galleryDetail;
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
            success = storageAccessAgent.GALLERY_STORAGE.deleteStoreItem(galleryName, galleryItemName);
        } catch (StorageAccessException storageAccessException) {
            throw new DataException(storageAccessException.getMessage(), storageAccessException);
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
                }
                boolean delete = delete(galleryName, galleryItemName);
                if (delete) {
                    result.add("Gallery Item is deleted.");
                } else {
                    result.add("Failed: Gallery Item is not deleted.");
                }
            } catch (Exception exception) {
                result.add("Error: " + exception.getMessage());
            }
        }
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
            List<StorageItem> galleryStorageItems = storageAccessAgent.GALLERY_STORAGE.getAll(galleryName);
            galleryItems = GalleryDataAssembler.createGalleryDetails(galleryStorageItems, null);
        } catch (StorageAccessException storageAccessException) {
            throw new DataException(storageAccessException.getMessage(), storageAccessException);
        }
        return galleryItems;
    }

}
