package com.myschool.infra.filesystem.agent;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.common.exception.FileSystemException;
import com.myschool.infra.filesystem.dto.DirectoryDto;
import com.myschool.infra.filesystem.util.FileUtil;
import com.myschool.infra.image.agent.ImageScalingAgent;
import com.myschool.infra.image.constants.ImageSize;

/**
 * The Class ImageFileSystem.
 */
@Component
public class ImageFileSystem extends AbstractSubFileSystem {

    /** The employee directory. */
    private static File employeeDirectory;

    /** The student directory. */
    private static File studentDirectory;

    /** The org directory. */
    private static File orgDirectory;

    /** The image scaling agent. */
    @Autowired
    private ImageScalingAgent imageScalingAgent;

    /* (non-Javadoc)
     * @see com.myschool.infra.filesystem.agent.AbstractSubFileSystem#init(com.myschool.infra.filesystem.dto.DirectoryDto)
     */
    @Override
    public void init(DirectoryDto directory) throws FileSystemException {
        super.init(directory);
        employeeDirectory = getChildDirectoryFile(EMPLOYEE);
        studentDirectory = getChildDirectoryFile(STUDENT);
        orgDirectory = getChildDirectoryFile(ORGANIZATION);
    }

    /**
     * Creates the employee image.
     * 
     * @param employeeNumber the employee number
     * @param imageFile the image file
     * @throws FileSystemException the file system exception
     */
    public void createEmployeeImage(String employeeNumber, File imageFile)
            throws FileSystemException {
        createImage(employeeDirectory, employeeNumber, imageFile);
    }

    /**
     * Creates the student image.
     * 
     * @param admissionNumber the admission number
     * @param imageFile the image file
     * @throws FileSystemException the file system exception
     */
    public void createStudentImage(String admissionNumber, File imageFile)
            throws FileSystemException {
        createImage(studentDirectory, admissionNumber, imageFile);
    }

    /**
     * Gets the employee image.
     * 
     * @param employeeNumber the employee number
     * @param imageSize the image size
     * @return the employee image
     * @throws FileSystemException the file system exception
     */
    public File getEmployeeImage(String employeeNumber,
            ImageSize imageSize) throws FileSystemException {
        return getImage(employeeDirectory, employeeNumber, imageSize);
    }

    /**
     * Gets the student image.
     * 
     * @param admissionNumber the admission number
     * @param imageSize the image size
     * @return the student image
     * @throws FileSystemException the file system exception
     */
    public File getStudentImage(String admissionNumber,
            ImageSize imageSize) throws FileSystemException {
        return getImage(studentDirectory, admissionNumber, imageSize);
    }

    /**
     * Gets the org image.
     * 
     * @param imageName the image name
     * @param imageSize the image size
     * @return the org image
     * @throws FileSystemException the file system exception
     */
    public File getOrgImage(String imageName,
            ImageSize imageSize) throws FileSystemException {
        return getImage(orgDirectory, imageName, imageSize);
    }

    /**
     * Creates the image.
     * 
     * @param directory the directory
     * @param newImageName the new image name
     * @param oldImageFile the old image file
     * @throws FileSystemException the file system exception
     */
    // TODO do not create resize images instantly. instead have a monitor on the folders and do whenever a file is added or deleted.
    public void createImage(File directory, String newImageName, File oldImageFile)
            throws FileSystemException {
        File newImageFile = new File(directory, newImageName + "." + FileUtil.getExtension(oldImageFile));
        FileUtil.moveFile(oldImageFile, newImageFile);
        // generate resized images.
        imageScalingAgent.createResizeImages(newImageFile);
    }

    /**
     * Gets the image.
     * 
     * @param directory the directory
     * @param fileName the file name
     * @param imageSize the image size
     * @return the image
     * @throws FileSystemException the file system exception
     */
    private File getImage(File directory, String fileName, ImageSize imageSize)
            throws FileSystemException {
        File parentDirectory = directory;
        if (imageSize == ImageSize.THUMBNAIL) {
            parentDirectory = new File(parentDirectory, ImageSize.THUMBNAIL.toString());
        } else if (imageSize == ImageSize.PASSPORT) {
            parentDirectory = new File(parentDirectory, ImageSize.PASSPORT.toString());
        }
        return FileUtil.getUniqueFile(parentDirectory, fileName);
    }

}
