package com.myschool.infra.filesystem.agent;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.common.exception.FileSystemException;
import com.myschool.infra.filesystem.filefilter.GalleryPinFileFilter;
import com.myschool.infra.filesystem.filefilter.ImageResizingFileFilter;
import com.myschool.infra.filesystem.util.FileUtil;
import com.myschool.infra.image.agent.ImageScalingAgent;
import com.myschool.infra.image.constants.ImageSize;

/**
 * The Class GalleryFileSystem.
 */
@Component
public class GalleryFileSystem extends AbstractSubFileSystem {

	/** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(GalleryFileSystem.class);

    /** The image resizing file filter. */
    @Autowired
    private ImageResizingFileFilter imageResizingFileFilter;

    /** The gallery pin file filter. */
    @Autowired
    private GalleryPinFileFilter galleryPinFileFilter;

    /** The image scaling agent. */
    @Autowired
    private ImageScalingAgent imageScalingAgent;

    /**
     * Inits the.
     */
    @PostConstruct
    private void init() {
    	File pinnedGallery = null;
    	try {
    		LOGGER.info("Looking for pin in galleries");
    		System.out.println("Looking for pin in galleries");
    		pinnedGallery = getPinnedGallery();
			// If pin file is not present in any of the galleries, then pin the latest gallery
			if (pinnedGallery == null) {
				LOGGER.info("No gallery is pinned. Pinning the latest gallery file.");
				System.out.println("No gallery is pinned. Pinning the latest gallery file.");
				pinLatestGallery();
			} else {
				LOGGER.info("Pinned gallery=" + pinnedGallery.getName());
				System.out.println("Pinned gallery=" + pinnedGallery.getName());
			}
		} catch (FileSystemException fileSystemException) {
			LOGGER.error("Error looking for and creating pin. ERROR=" + fileSystemException.getMessage(), fileSystemException);
		}
    }

    /**
     * Gets the gallery files.
     *
     * @return the gallery files
     * @throws FileSystemException the file system exception
     */
    public List<File> getGalleryFiles() throws FileSystemException {
    	File[] galleryFiles = getDirectory().listFiles();
		return (galleryFiles == null) ? null : Arrays.asList(galleryFiles);
    }

    /**
     * Gets the gallery file.
     *
     * @param galleryName the gallery name
     * @return the gallery file
     * @throws FileSystemException the file system exception
     */
    public File getGalleryFile(String galleryName) throws FileSystemException {
    	if (galleryName != null) {
    		List<File> galleryFiles = getGalleryFiles();
    		if (galleryFiles != null && !galleryFiles.isEmpty()) {
    			for (File galleryFile : galleryFiles) {
    				if (galleryName.trim().equals(galleryFile.getName())) {
    					return galleryFile;
    				}
    			}
    		}
    	}
    	return null;
    }

    /**
     * Gets the gallery item files.
     *
     * @param galleryFile the gallery file
     * @return the gallery item files
     * @throws FileSystemException the file system exception
     */
    public List<File> getGalleryItemFiles(File galleryFile) throws FileSystemException {
        if (galleryFile == null || !galleryFile.exists() || !galleryFile.isDirectory()) {
            throw new FileSystemException("Gallery ('" + galleryFile.getName() + "') does not exist.");
        }
        File[] galleryItemNames = galleryFile.listFiles(imageResizingFileFilter);
        return (galleryItemNames == null) ? null : Arrays.asList(galleryItemNames);
    }

    /**
     * Gets the pinned gallery.
     *
     * @return the pinned gallery
     * @throws FileSystemException the file system exception
     */
    public File getPinnedGallery() throws FileSystemException {
    	File[] galleryFiles = getDirectory().listFiles();
    	if (galleryFiles != null && galleryFiles.length != 0) {
    		for (File galleryFile : galleryFiles) {
    			if (galleryFile != null) {
    				File[] pinFiles = galleryFile.listFiles(galleryPinFileFilter);
    				if (pinFiles != null && pinFiles.length != 0) {
    					return galleryFile;
    				}
    			}
    		}
    	}
        return null;
    }

    /**
     * Pin latest gallery.
     *
     * @return the file
     * @throws FileSystemException the file system exception
     */
    private File pinLatestGallery() throws FileSystemException {
		try {
			File latestGallery = FileUtil.getLatestFile(getDirectory().listFiles());
			if (latestGallery != null && latestGallery.isDirectory()) {
				File pin = galleryPinFileFilter.getPin(latestGallery);
				LOGGER.info("Pinned the latest gallery=" + latestGallery.getName());
				System.out.println("Pinned the latest gallery=" + latestGallery.getName());
				pin.createNewFile();
				return pin;
			}
		} catch (IOException ioException) {
			throw new FileSystemException(ioException.getMessage(), ioException);
		}
		return null;
	}
    /**
     * Gets the gallery item.
     * 
     * @param galleryName the gallery name
     * @param imageSize the image size
     * @return the gallery item
     * @throws FileSystemException the file system exception
     */
    public File getGalleryItem(String galleryName, ImageSize imageSize) throws FileSystemException {
        String galleryFileAbsPath = getDirectory().getAbsolutePath() + "/" + galleryName;
        File galleryFile = new File(galleryFileAbsPath.replaceAll("\\\\", "/"));
        if (galleryFile.exists()) {
            return imageScalingAgent.getImage(galleryFile, imageSize);
        }
        return null;
    }

    /**
     * Pin gallery.
     *
     * @param gallery the gallery
     * @throws FileSystemException the file system exception
     */
    public void pinGallery(File gallery) throws FileSystemException {
    	try {
			if (gallery != null && gallery.exists() && gallery.isDirectory()) {
				// Look for existing pins in the galleries. If found then delete them.
				File[] galleryFiles = getDirectory().listFiles();
				if (galleryFiles != null && galleryFiles.length != 0) {
					for (File galleryFile : galleryFiles) {
						if (galleryFile != null) {
							File[] pinFiles = galleryFile.listFiles(galleryPinFileFilter);
							if (pinFiles != null && pinFiles.length != 0) {
								for (File pinFile : pinFiles) {
									if (pinFile != null) {
										pinFile.delete();
										LOGGER.debug("Deleted pin=" + pinFile);
										System.out.println("Deleted pin=" + pinFile);
									}
								}
							}
						}
					}
				}
				File pinFile = galleryPinFileFilter.getPin(gallery);
				boolean pinned = pinFile.createNewFile();
				if (pinned) {
					LOGGER.info("Pinned gallery=" + gallery.getName());
					System.out.println("Pinned gallery=" + gallery.getName());
				} else {
					throw new FileSystemException("Unable to pin gallery '" + gallery.getName() + "'");
				}
			}
		} catch (IOException ioException) {
			throw new FileSystemException(ioException.getMessage(), ioException);
		}
    }

	/**
	 * Creates the gallery.
	 *
	 * @param galleryName the gallery name
	 * @return the file
	 * @throws FileSystemException the file system exception
	 */
	public File createGallery(String galleryName) throws FileSystemException {
    	// Create the gallery directory
        return FileUtil.createDirectory(getDirectory(), galleryName);
	}

	/**
	 * Rename gallery.
	 *
	 * @param oldFile the old file
	 * @param newGalleryName the new gallery name
	 * @return true, if successful
	 * @throws FileSystemException the file system exception
	 */
	public boolean renameGallery(File oldFile, String newGalleryName) throws FileSystemException {
    	// Rename the gallery name
        return FileUtil.rename(oldFile, newGalleryName);
	}

	/**
	 * Delete gallery.
	 *
	 * @param gallery the existing file
	 * @return true, if successful
	 * @throws FileSystemException the file system exception
	 */
	public boolean deleteGallery(File gallery) throws FileSystemException {
		try {
			boolean pinLatest = false;
			// Check if the deleting gallery has pinned
			if (gallery != null) {
				File[] pins = gallery.listFiles(galleryPinFileFilter);
				pinLatest = (pins != null && pins.length != 0);
			}
			// delete the gallery
			FileUtils.deleteDirectory(gallery);
			// Pin the latest directory
			if (pinLatest) {
				LOGGER.info("Gallery '" + gallery.getName() + "' is deleted. This gallery has been pinned.");
				System.out.println("Gallery '" + gallery.getName() + "' is deleted. This gallery has been pinned.");
				pinLatestGallery();
			}
			return true;
		} catch (IOException ioException) {
			throw new FileSystemException(ioException.getMessage(), ioException);
		}
	}

	/**
	 * Adds the gallery item.
	 *
	 * @param galleryItemFromFile the gallery item from file
	 * @param galleryItemToFile the gallery item to file
	 * @return true, if successful
	 * @throws FileSystemException the file system exception
	 */
	public boolean addGalleryItem(File galleryItemFromFile, File galleryItemToFile) throws FileSystemException {
    	// add the gallery name
    	FileUtil.moveFile(galleryItemFromFile, galleryItemToFile);
    	// Create THUMBNAIL and PASSPORT images
    	imageScalingAgent.createResizeImages(galleryItemToFile);
        return true;
	}

	/**
	 * Delete gallery item.
	 *
	 * @param galleryItemFile the gallery item file
	 * @return true, if successful
	 * @throws FileSystemException the file system exception
	 */
	public boolean deleteGalleryItem(File galleryItemFile) throws FileSystemException {
		boolean deleted = false;
    	// delete the gallery item
    	deleted = galleryItemFile.delete();
    	// Remove THUMBNAIL and PASSPORT images
    	imageScalingAgent.removeResizeImages(galleryItemFile);
        return deleted;
	}

}