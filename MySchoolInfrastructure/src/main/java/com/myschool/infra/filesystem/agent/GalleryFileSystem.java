package com.myschool.infra.filesystem.agent;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.common.exception.FileSystemException;
import com.myschool.infra.filesystem.filefilter.ImageResizingFileFilter;
import com.myschool.infra.filesystem.util.FileUtil;
import com.myschool.infra.image.agent.ImageScalingAgent;
import com.myschool.infra.image.constants.ImageSize;

/**
 * The Class GalleryFileSystem.
 */
@Component
public class GalleryFileSystem extends AbstractSubFileSystem {

    /** The image resizing file filter. */
    @Autowired
    private ImageResizingFileFilter imageResizingFileFilter;

    /** The image scaling agent. */
    @Autowired
    private ImageScalingAgent imageScalingAgent;

    /**
     * Gets the gallery names.
     * 
     * @return the gallery names
     * @throws FileSystemException the file system exception
     */
    public List<String> getGalleryNames() throws FileSystemException {
        return Arrays.asList(getDirectory().list());
    }

    /**
     * Gets the gallery item names.
     * 
     * @param galleryName the gallery name
     * @return the gallery item names
     * @throws FileSystemException the file system exception
     */
    public List<String> getGalleryItemNames(String galleryName) throws FileSystemException {
        List<String> galleryItemNamesList = null;
        File galleryFile = new File(getDirectory(), galleryName);
        if (!galleryFile.exists()) {
            throw new FileSystemException("Gallery ('" + galleryName + "') does not exist.");
        }
        File[] galleryItemNames = galleryFile.listFiles(imageResizingFileFilter);
        if (galleryItemNames != null && galleryItemNames.length != 0) {
            galleryItemNamesList = new ArrayList<String>();
            for (File galleryItemName : galleryItemNames) {
                galleryItemNamesList.add(galleryItemName.getName());
            }
        }
        return galleryItemNamesList;
    }

    /**
     * Gets the latest gallery name.
     * 
     * @return the latest gallery name
     * @throws FileSystemException the file system exception
     */
    public String getLatestGalleryName() throws FileSystemException {
        File latestGallery = FileUtil.getLatestFile(getDirectory().listFiles());
        if (latestGallery != null && latestGallery.isDirectory()) {
            return latestGallery.getName();
        }
        return null;
    }

    /**
     * Gets the gallery item.
     * 
     * @param galleryPath the gallery path
     * @param imageSize the image size
     * @return the gallery item
     * @throws FileSystemException the file system exception
     */
    public File getGalleryItem(String galleryPath, ImageSize imageSize) throws FileSystemException {
        String galleryFileAbsPath = getDirectory().getAbsolutePath() + "/" + galleryPath;
        File galleryFile = new File(galleryFileAbsPath.replaceAll("\\\\", "/"));
        if (galleryFile.exists()) {
            return imageScalingAgent.getImage(galleryFile, imageSize);   
        }
        return null;
    }

}