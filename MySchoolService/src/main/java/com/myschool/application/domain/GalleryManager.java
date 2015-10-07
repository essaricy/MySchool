package com.myschool.application.domain;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.application.assembler.GalleryDataAssembler;
import com.myschool.application.dto.GalleryDetailDto;
import com.myschool.common.exception.DataException;
import com.myschool.common.exception.FileSystemException;
import com.myschool.common.util.StringUtil;
import com.myschool.infra.filesystem.agent.GalleryFileSystem;
import com.myschool.infra.filesystem.util.FileUtil;
import com.myschool.infra.image.agent.ImageScalingAgent;
import com.myschool.infra.image.constants.ImageSize;

/**
 * The Class GalleryManager.
 */
@Component
public class GalleryManager {

	/** The gallery file system. */
    @Autowired
    private GalleryFileSystem galleryFileSystem;

    /** The image scaling agent. */
    @Autowired
    private ImageScalingAgent imageScalingAgent;

	/**
	 * Gets the gallery details.
	 *
	 * @return the gallery details
	 * @throws DataException the data exception
	 */
	public List<GalleryDetailDto> getAll() throws DataException {
		List<GalleryDetailDto> galleryDetailDtos = null;
        try {
        	// Get the list of galleries
        	List<File> galleryFiles = galleryFileSystem.getGalleryFiles();
    		if (galleryFiles != null && !galleryFiles.isEmpty()) {
    			File pinnedGallery = galleryFileSystem.getPinnedGallery();
        		galleryDetailDtos = new ArrayList<GalleryDetailDto>();
        		// For each gallery
        		for (File galleryFile : galleryFiles) {
        			// Create a gallery detail object
        			GalleryDetailDto galleryDetail = GalleryDataAssembler.createGalleryDetail(galleryFile);
        			// List the item files in the gallery
        			List<File> galleryItemFiles = galleryFileSystem.getGalleryItemFiles(galleryFile);
        			List<GalleryDetailDto> galleryItemDetails = GalleryDataAssembler.createGalleryDetails(galleryItemFiles);
        			galleryDetail.setGalleryItems(galleryItemDetails);
        			galleryDetail.setPinned(pinnedGallery != null && pinnedGallery.getName().equals(galleryFile.getName()));
        			galleryDetailDtos.add(galleryDetail);
    			}
        	}
        } catch (FileSystemException fileSystemException) {
            throw new DataException(fileSystemException.getMessage(), fileSystemException);
        }
		return galleryDetailDtos;
    }

	/**
	 * Gets the gallery detail.
	 *
	 * @param galleryName the gallery name
	 * @return the gallery detail
	 * @throws DataException the data exception
	 */
	public GalleryDetailDto getGalleryDetail(String galleryName) throws DataException {
		GalleryDetailDto galleryDetail;
		try {
			File galleryFile = galleryFileSystem.getGalleryFile(galleryName);
			File pinnedGallery = galleryFileSystem.getPinnedGallery();
			galleryDetail = GalleryDataAssembler.createGalleryDetail(galleryFile);
			if (galleryDetail != null) {
				List<GalleryDetailDto> galleryDetails = GalleryDataAssembler.createGalleryDetails(galleryFileSystem.getGalleryItemFiles(galleryFile));
				galleryDetail.setGalleryItems(galleryDetails);
				galleryDetail.setPinned(pinnedGallery != null && pinnedGallery.getName().equals(galleryFile.getName()));
			}
		} catch (FileSystemException fileSystemException) {
            throw new DataException(fileSystemException.getMessage(), fileSystemException);
        }
		return galleryDetail;
	}

	/**
	 * Gets the latest gallery details.
	 *
	 * @return the latest gallery details
	 * @throws DataException the data exception
	 */
	public GalleryDetailDto getPinned() throws DataException {
		GalleryDetailDto galleryDetail;
		try {
			File latestGallery = galleryFileSystem.getPinnedGallery();
			galleryDetail = GalleryDataAssembler.createGalleryDetail(latestGallery);
			// List the item files in the gallery
			if (galleryDetail != null) {
				List<File> galleryItemFiles = galleryFileSystem.getGalleryItemFiles(latestGallery);
				List<GalleryDetailDto> galleryItemDetails = GalleryDataAssembler.createGalleryDetails(galleryItemFiles);
				galleryDetail.setGalleryItems(galleryItemDetails);
			}
		} catch (FileSystemException fileSystemException) {
            throw new DataException(fileSystemException.getMessage(), fileSystemException);
        }
		return galleryDetail;
	}

    /**
     * Gets the gallery item.
     * 
     * @param galleryName the gallery name
     * @param imageSize the image size
     * @return the gallery item
     * @throws DataException the data exception
     */
    public File getGalleryItemFile(String galleryName, ImageSize imageSize) throws DataException {
        try {
            return galleryFileSystem.getGalleryItem(galleryName, imageSize);
        } catch (FileSystemException fileSystemException) {
            throw new DataException(fileSystemException.getMessage(), fileSystemException);
        }
    }

	/**
	 * Pin gallery.
	 *
	 * @param galleryName the gallery name
	 * @throws DataException the data exception
	 */
	public void pin(String galleryName) throws DataException {
        try {
        	File galleryFile = galleryFileSystem.getGalleryFile(galleryName);
        	if (galleryFile == null) {
        		throw new DataException("Gallery '" + galleryName + "' does not exist");
        	}
        	List<File> galleryItemFiles = galleryFileSystem.getGalleryItemFiles(galleryFile);
        	if (galleryItemFiles == null || galleryItemFiles.isEmpty()) {
        		throw new DataException("Cannot Pin Gallery '" + galleryName + "'. There are no images in this gallery.");
        	}
            galleryFileSystem.pinGallery(galleryFile);
        } catch (FileSystemException fileSystemException) {
            throw new DataException(fileSystemException.getMessage(), fileSystemException);
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
		boolean success=false;
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
        	File galleryFile = galleryFileSystem.createGallery(galleryName);
        	success = (galleryFile != null && galleryFile.exists());
        } catch (FileSystemException fileSystemException) {
            throw new DataException(fileSystemException.getMessage(), fileSystemException);
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
	public boolean update(String oldGalleryName, String newGalleryName) throws DataException {
		boolean success=false;
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
        	// Old gallery name must exist and new gallery name must not exist
        	File oldFile=null, newFile=null;
        	List<GalleryDetailDto> galleryDetails = getAll();
        	if (galleryDetails == null || galleryDetails.isEmpty()) {
        		throw new DataException("There are no galleries present to rename");
        	}
        	for (GalleryDetailDto galleryDetail : galleryDetails) {
        		String galleryName = galleryDetail.getGalleryName();
				if (oldGalleryName.equals(galleryName)) {
					oldFile=galleryDetail.getFile();
				}
				if (newGalleryName.equals(galleryName)) {
					newFile=galleryDetail.getFile();
				}
			}
        	if (oldFile == null) {
        		throw new DataException("Gallery '" + oldGalleryName + "' does not exist");
        	}
        	if (newFile != null) {
        		throw new DataException("A Gallery exists with name '" + newGalleryName+ "'");
        	}
        	success = galleryFileSystem.renameGallery(oldFile, newGalleryName);
        } catch (FileSystemException fileSystemException) {
            throw new DataException(fileSystemException.getMessage(), fileSystemException);
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
		boolean success=false;
        try {
        	if (StringUtil.isNullOrBlank(galleryName)) {
        		throw new DataException("Gallery Name cannot be empty");
        	}
        	galleryName = galleryName.trim();
        	// gallery name must exist
        	File existingFile=null;
        	List<GalleryDetailDto> galleryDetails = getAll();
        	if (galleryDetails == null || galleryDetails.isEmpty()) {
        		throw new DataException("There are no galleries present to delete");
        	}
        	for (GalleryDetailDto galleryDetail : galleryDetails) {
				if (galleryName.equals(galleryDetail.getGalleryName())) {
					existingFile=galleryDetail.getFile();
					break;
				}
			}
        	if (existingFile == null) {
        		throw new DataException("Gallery '" + galleryName + "' does not exist");
        	}
        	success = galleryFileSystem.deleteGallery(existingFile);
        } catch (FileSystemException fileSystemException) {
            throw new DataException(fileSystemException.getMessage(), fileSystemException);
        }
		return success;
    }

	/**
	 * Adds the.
	 *
	 * @param galleryName the gallery name
	 * @param galleryItemDetail the gallery item detail
	 * @return true, if successful
	 * @throws DataException the data exception
	 */
	public boolean add(String galleryName, GalleryDetailDto galleryItemDetail) throws DataException {
		boolean success = false;
        try {
        	if (StringUtil.isNullOrBlank(galleryName)) {
        		throw new DataException("Gallery Name cannot be empty");
        	}
        	if (galleryItemDetail == null) {
        		throw new DataException("Gallery Item information is not present");
        	}
        	String galleryItemName = galleryItemDetail.getGalleryName();
        	if (StringUtil.isNullOrBlank(galleryItemName)) {
        		throw new DataException("Gallery Item Name cannot be empty");
        	}
        	File galleryItemFromFile = galleryItemDetail.getFile();
        	FileUtil.checkFile(galleryItemFromFile, "Gallery Item file is missing", "Gallery Item file does not exist");
        	galleryItemName = galleryItemName.trim();
        	galleryName = galleryName.trim();
        	// gallery name must exist
        	File existingGalleryFile=null;
        	GalleryDetailDto existingGalleryDetail = null;
        	List<GalleryDetailDto> galleryDetails = getAll();
        	if (galleryDetails == null || galleryDetails.isEmpty()) {
        		throw new DataException("There are no galleries present to add images");
        	}
        	for (int index = 0; index < galleryDetails.size(); index++) {
        		existingGalleryDetail = galleryDetails.get(index);
				if (galleryName.equals(existingGalleryDetail.getGalleryName())) {
					existingGalleryFile=existingGalleryDetail.getFile();
					break;
				}
			}
        	if (existingGalleryFile == null) {
        		throw new DataException("Gallery '" + galleryName + "' does not exist");
        	}
        	File galleryItemToFile = new File(existingGalleryFile, galleryItemName);
        	if (galleryItemToFile.exists()) {
        		throw new DataException("Image already added to gallery");
        	}
        	success = galleryFileSystem.addGalleryItem(galleryItemFromFile, galleryItemToFile);
        } catch (FileSystemException fileSystemException) {
            throw new DataException(fileSystemException.getMessage(), fileSystemException);
        }
		return success;
    }

	/**
	 * Adds the.
	 *
	 * @param galleryName the gallery name
	 * @param galleryItemDetails the gallery item details
	 * @return the list
	 * @throws DataException the data exception
	 */
	public List<String> add(String galleryName, List<GalleryDetailDto> galleryItemDetails) throws DataException {
		List<String> result = new ArrayList<String>();

		if (StringUtil.isNullOrBlank(galleryName)) {
			throw new DataException("Gallery Name cannot be empty");
		}
		if (galleryItemDetails == null || galleryItemDetails.isEmpty()) {
			throw new DataException("Gallery Item information is not present");
		}
		// Get the pinned directory to pin again after this operation
		galleryName = galleryName.trim();
		for (GalleryDetailDto galleryDetail : galleryItemDetails) {
			boolean success = false;
			try {
				if (galleryDetail == null) {
					result.add("Gallery Item information is not present");
					continue;
				}
				String galleryItemName = galleryDetail.getGalleryName();
				if (StringUtil.isNullOrBlank(galleryItemName)) {
					result.add("Gallery Item Name cannot be empty");
					continue;
				}
				File galleryItemFromFile = galleryDetail.getFile();
				FileUtil.checkFile(galleryItemFromFile, "Gallery Item file is missing", "Gallery Item file does not exist");
				galleryItemName = galleryItemName.trim();
				
				// gallery name must exist
				File existingFile=null;
				List<GalleryDetailDto> galleryDetails = getAll();
				if (galleryDetails == null || galleryDetails.isEmpty()) {
	        		throw new DataException("There are no galleries present to add images");
	        	}
				for (GalleryDetailDto existingGalleryDetail : galleryDetails) {
					if (galleryName.equals(existingGalleryDetail.getGalleryName())) {
						existingFile=existingGalleryDetail.getFile();
					}
				}
				if (existingFile == null) {
					result.add("Gallery '" + galleryName + "' does not exist");
					continue;
				}
				File galleryItemToFile = new File(existingFile, existingFile.getName());
				if (galleryItemToFile.exists() || !galleryItemToFile.isFile()) {
					result.add("Gallery Item '" + galleryName + "' already exist");
					continue;
				}
				success = galleryFileSystem.addGalleryItem(galleryItemFromFile, galleryItemToFile);
				if (success) {
					result.add("Added Gallery Item '" + galleryName + "'");
				} else {
					result.add("Unable to add Gallery Item '" + galleryName + "'");
				}
			} catch (Exception exception) {
				result.add("Error: " + exception.getMessage());
			}
		}
		return result;
    }

	/**
	 * Delete.
	 *
	 * @param galleryName the gallery name
	 * @param galleryItemName the gallery item name
	 * @return true, if successful
	 * @throws DataException the data exception
	 */
	public boolean delete(String galleryName, String galleryItemName) throws DataException {
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
        	File existingFile=null;
        	List<GalleryDetailDto> galleryDetails = getAll();
        	if (galleryDetails == null || galleryDetails.isEmpty()) {
        		throw new DataException("There are no galleries present to delete images");
        	}
        	for (GalleryDetailDto existingGalleryDetail : galleryDetails) {
				if (galleryName.equals(existingGalleryDetail.getGalleryName())) {
					existingFile=existingGalleryDetail.getFile();
				}
			}
        	if (existingFile == null) {
        		throw new DataException("Gallery '" + galleryName + "' does not exist");
        	}
        	File galleryItemFile = new File(existingFile, existingFile.getName());
        	if (!galleryItemFile.exists() || !galleryItemFile.isFile()) {
        		throw new DataException("Gallery Item '" + galleryName + "' does not exist");
        	}
        	success = galleryFileSystem.deleteGalleryItem(existingFile);
        } catch (FileSystemException fileSystemException) {
            throw new DataException(fileSystemException.getMessage(), fileSystemException);
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
	public List<String> delete(String galleryName, List<String> galleryItemNames) throws DataException {
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
		File existingFile=null;
		List<GalleryDetailDto> galleryDetails = getAll();
		if (galleryDetails == null || galleryDetails.isEmpty()) {
    		throw new DataException("There are no galleries present to delete images");
    	}
		for (GalleryDetailDto existingGalleryDetail : galleryDetails) {
			if (galleryName.equals(existingGalleryDetail.getGalleryName())) {
				existingFile=existingGalleryDetail.getFile();
			}
		}
		if (existingFile == null) {
			throw new DataException("Gallery '" + galleryName + "' does not exist");
		}
		for (String galleryItemName : galleryItemNames) {
			boolean success = false;
			try {
				File galleryItemFile = new File(existingFile, galleryItemName);
				if (!galleryItemFile.exists() || !galleryItemFile.isFile()) {
					result.add("Gallery Item '" + galleryName + "' does not exist");
					continue;
				}
				success = galleryFileSystem.deleteGalleryItem(galleryItemFile);
				if (success) {
					result.add("Deleted Gallery Item '" + galleryItemName + "'");
				} else {
					result.add("Unable to delete Gallery Item '" + galleryItemName + "'");
				}
			} catch (Exception exception) {
				result.add("Error: " + exception.getMessage());
			}
		}
		return result;
    }

}
