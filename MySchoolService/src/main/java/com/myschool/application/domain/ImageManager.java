package com.myschool.application.domain;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.application.dto.FeatureDto;
import com.myschool.common.exception.DataException;
import com.myschool.common.exception.FileSystemException;
import com.myschool.infra.application.Agents;
import com.myschool.infra.application.ApplicationLoader;
import com.myschool.infra.cache.agent.InMemoryCacheAgent;
import com.myschool.infra.filesystem.agent.ImageFileSystem;
import com.myschool.infra.filesystem.agent.TempFileSystem;
import com.myschool.infra.image.constants.ImageSize;

/**
 * The Class ImageManager.
 */
@Component
public class ImageManager {

    /** The Constant LOGO. */
    private static final String LOGO = "LOGO";

    /** The Constant NO_IMAGE. */
    private static final String NO_IMAGE = "NO_IMAGE";

    /** The agents. */
    @Autowired
    private Agents agents;

    /** The image file system. */
    @Autowired
    private ImageFileSystem imageFileSystem;

    /** The temp file system. */
    @Autowired
    private TempFileSystem tempFileSystem;

    /** The application loader. */
    @Autowired
    private ApplicationLoader applicationLoader;

    /** The in memory cache agent. */
    @Autowired
    private InMemoryCacheAgent inMemoryCacheAgent;

    /**
     * Gets the logo.
     * 
     * @return the logo
     * @throws DataException the data exception
     */
    public File getLogo() throws DataException {
        File logo = null;
        try {
            logo = (File) inMemoryCacheAgent.getEntry(LOGO);
            if (logo == null) {
                logo = imageFileSystem.getOrgImage(LOGO, ImageSize.ORIGINAL);
                inMemoryCacheAgent.putEntry(LOGO, logo);
            }
        } catch (FileSystemException fileSystemException) {
            throw new DataException(fileSystemException.getMessage(), fileSystemException);
        }
        return logo;
    }

    /**
     * Gets the no image.
     * 
     * @return the no image
     * @throws DataException the data exception
     */
    public File getNoImage() throws DataException {
        File noImage = null;
        try {
            noImage = (File) inMemoryCacheAgent.getEntry(NO_IMAGE);
            if (noImage == null) {
                noImage = imageFileSystem.getOrgImage(NO_IMAGE, ImageSize.ORIGINAL);
                inMemoryCacheAgent.putEntry(NO_IMAGE, noImage);
            }
        } catch (FileSystemException fileSystemException) {
            throw new DataException(fileSystemException.getMessage(), fileSystemException);
        }
        return noImage;
    }

    /**
     * Gets the features.
     * 
     * @return the features
     */
    public List<FeatureDto> getFeatures() {
        return applicationLoader.getFeatures();
    }

    /**
     * Gets the student image.
     * 
     * @param admissionNumber the admission number
     * @param imageSize the image size
     * @return the student image
     * @throws DataException the data exception
     */
    public File getStudentImage(String admissionNumber,
            ImageSize imageSize) throws DataException {
        File studentImage = null;
        try {
            studentImage = imageFileSystem.getStudentImage(admissionNumber, imageSize);
            // Not found, then search in employee tmp file system.
            if (studentImage == null) {
                studentImage = tempFileSystem.getStudentImage(admissionNumber, imageSize);
            }
            // Still not found then Return no image file.
            if (studentImage == null) {
                studentImage = getNoImage();
            }
            if (studentImage == null) {
                studentImage = getNoImage();
            }
        } catch (FileSystemException fileSystemException) {
            throw new DataException(fileSystemException.getMessage(), fileSystemException);
        }
        return studentImage;
    }

    /**
     * Gets the org image.
     * 
     * @param imageName the image name
     * @param imageSize the image size
     * @return the org image
     * @throws DataException the data exception
     */
    public File getOrgImage(String imageName, ImageSize imageSize) throws DataException {
        File orgImage = null;
        try {
            orgImage = (File) inMemoryCacheAgent.getEntry(imageName);
            // Try to get the image from cache
            if (orgImage == null) {
                // If org image is not found then put no-image instead.
                orgImage = imageFileSystem.getOrgImage(imageName, imageSize);
                // Still not found then Return no image file.
                if (orgImage == null) {
                    orgImage = getNoImage();
                }
                inMemoryCacheAgent.putEntry(imageName, orgImage);
            }
            
        } catch (FileSystemException fileSystemException) {
            throw new DataException(fileSystemException.getMessage(), fileSystemException);
        }
        return orgImage;
    }

    /**
     * Gets the employee image.
     * 
     * @param employeeNumber the employee number
     * @param imageSize the image size
     * @return the employee image
     * @throws DataException the data exception
     */
    public File getEmployeeImage(String employeeNumber,
            ImageSize imageSize) throws DataException {
        File employeeImage = null;
        try {
            if (imageSize == null) {
                throw new DataException("Must specify the file size to retrieve image.");
            }
            // Search in the employee file server
            employeeImage = imageFileSystem.getEmployeeImage(employeeNumber, imageSize);
            // Not found, then search in employee tmp file system.
            if (employeeImage == null) {
                employeeImage = tempFileSystem.getEmployeeImage(employeeNumber, imageSize);
            }
            // Still not found then Return no image file.
            if (employeeImage == null) {
                employeeImage = getNoImage();
            }
        } catch (FileSystemException fileSystemException) {
            throw new DataException(fileSystemException.getMessage(), fileSystemException);
        }
        return employeeImage;
    }

}
