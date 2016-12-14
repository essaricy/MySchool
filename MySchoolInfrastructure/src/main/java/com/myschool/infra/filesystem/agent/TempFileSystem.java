package com.myschool.infra.filesystem.agent;

import java.io.File;

import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.common.exception.FileSystemException;
import com.myschool.common.util.SecureRandomGenerator;
import com.myschool.file.constant.FileExtension;
import com.myschool.file.util.FileUtil;
import com.myschool.filesystem.dto.DirectoryDto;
import com.myschool.image.constant.ImageSize;
import com.myschool.infra.image.agent.ImageScalingAgent;

/**
 * The Class TempFileSystem.
 */
@Component
public class TempFileSystem extends AbstractSubFileSystem {

    /** The image scaling agent. */
    @Autowired
    private ImageScalingAgent imageScalingAgent;

    /** The employee directory. */
    private static File employeeDirectory;

    /** The gallery directory. */
    private static File galleryDirectory;

    /** The import directory. */
    private static File importDirectory;

    /** The report directory. */
    private static File reportDirectory;

    /** The student directory. */
    private static File studentDirectory;

    /** The notification directory. */
    private static File notificationDirectory;

    /** The upload directory. */
    private static File uploadDirectory;

    /* (non-Javadoc)
     * @see com.myschool.infra.filesystem.agent.AbstractSubFileSystem#init(com.myschool.infra.filesystem.dto.DirectoryDto)
     */
    @Override
    public void init(DirectoryDto directory) throws FileSystemException {
        super.init(directory);
        galleryDirectory = getChildDirectoryFile(GALLERY);
        employeeDirectory = getChildDirectoryFile(EMPLOYEE);
        importDirectory = getChildDirectoryFile(IMPORT);
        reportDirectory = getChildDirectoryFile(REPORT);
        studentDirectory = getChildDirectoryFile(STUDENT);
        notificationDirectory = getChildDirectoryFile(NOTIFICATION);
        uploadDirectory = getChildDirectoryFile(UPLOAD);
    }

    /* (non-Javadoc)
     * @see com.myschool.infra.filesystem.agent.FileSystem#destroy()
     */
    @Override
    @PreDestroy
    public void destroy() throws FileSystemException {
        FileUtil.deleteDirectory(getDirectory());
    }

    /**
     * Creates the file.
     * 
     * @param fileName the file name
     * @return the file
     * @throws FileSystemException the file system exception
     */
    public File createFile(String fileName) throws FileSystemException {
        return FileUtil.createFile(getDirectory(), fileName);
    }

    /**
     * Creates the new temp file.
     *
     * @param fileName the file name
     * @return the file
     * @throws FileSystemException the file system exception
     */
    public File createNewTempFile(String fileName) throws FileSystemException {
        String tempFileName = SecureRandomGenerator.getNextString(SecureRandomGenerator.SIZE_64) + "." + FileUtil.getExtension(fileName);
        return FileUtil.createFile(getDirectory(), tempFileName);
    }

    /**
     * Creates the temp file.
     *
     * @param fileName the file name
     * @return the file
     * @throws FileSystemException the file system exception
     */
    public File createTempFile(String fileName) throws FileSystemException {
        return FileUtil.createFile(getDirectory(), fileName);
    }

    /**
     * Creates the gallery temp file.
     *
     * @param fileName the file name
     * @return the file
     * @throws FileSystemException the file system exception
     */
    public File createGalleryTempFile(String fileName) throws FileSystemException {
        return FileUtil.createFile(galleryDirectory, fileName);
    }

    /**
     * Creates the employee image.
     * 
     * @param tempFile the temp file
     * @return the file
     * @throws FileSystemException the file system exception
     */
    public File createEmployeeImage(File tempFile)
            throws FileSystemException {
        File file = new File(employeeDirectory, tempFile.getName());
        FileUtil.moveFile(tempFile, file);
        // Create image resize
        imageScalingAgent.createResizeImages(file);
        return file;
    }

    /**
     * Creates the student image.
     * 
     * @param tempFile the temp file
     * @return the file
     * @throws FileSystemException the file system exception
     */
    public File createStudentImage(File tempFile)
            throws FileSystemException {
        File file = new File(studentDirectory, tempFile.getName());
        FileUtil.moveFile(tempFile, file);
        // Create image resize
        imageScalingAgent.createResizeImages(file);
        return file;
    }

    /**
     * Creates the report file.
     *
     * @param fileName the file name
     * @return the file
     * @throws FileSystemException the file system exception
     */
    public File createReportFile(String fileName) throws FileSystemException {
        return FileUtil.createFile(reportDirectory, fileName);
    }

    /**
     * Creates the report file.
     *
     * @param extension the extension
     * @return the file
     * @throws FileSystemException the file system exception
     */
    private File createReportFile(FileExtension extension) throws FileSystemException {
        StringBuilder fileName = new StringBuilder();
        fileName.append(System.currentTimeMillis());
        fileName.append(FileUtil.FILE_EXTENSION_SEPARATOR);
        fileName.append(extension.toString());
        return FileUtil.createFile(reportDirectory, fileName.toString());
    }

    /**
     * Creates the pdf report file.
     *
     * @return the file
     * @throws FileSystemException the file system exception
     */
    public File createPdfReportFile() throws FileSystemException {
        return createReportFile(FileExtension.PDF);
    }

    /**
     * Creates the notification file.
     * 
     * @param fileName the file name
     * @return the file
     * @throws FileSystemException the file system exception
     */
    public File createNotificationFile(String fileName) throws FileSystemException {
        return FileUtil.createFile(notificationDirectory, fileName);
    }

    /**
     * Gets the employee image.
     * 
     * @param fileName the file name
     * @param imageSize the image size
     * @return the employee image
     * @throws FileSystemException the file system exception
     */
    public File getEmployeeImage(String fileName, ImageSize imageSize)
            throws FileSystemException {
        return getImage(employeeDirectory, fileName, imageSize);
    }

    /**
     * Gets the student image.
     * 
     * @param fileName the file name
     * @param imageSize the image size
     * @return the student image
     * @throws FileSystemException the file system exception
     */
    public File getStudentImage(String fileName, ImageSize imageSize)
            throws FileSystemException {
        return getImage(studentDirectory, fileName, imageSize);
    }

    /**
     * Gets the import file.
     *
     * @param fileName the file name
     * @return the import file
     * @throws FileSystemException the file system exception
     */
    public File getImportFile(String fileName) throws FileSystemException {
        return FileUtil.getUniqueFile(importDirectory, fileName);
    }

    /**
     * Gets the report file.
     *
     * @param fileName the file name
     * @return the report file
     * @throws FileSystemException the file system exception
     */
    public File getReportFile(String fileName) throws FileSystemException {
        return FileUtil.getUniqueFile(reportDirectory, fileName);
    }

    /**
     * Gets the notification file.
     * 
     * @param fileName the file name
     * @return the notification file
     * @throws FileSystemException the file system exception
     */
    public File getNotificationFile(String fileName) throws FileSystemException {
        return FileUtil.getUniqueFile(notificationDirectory, fileName);
    }

    /**
     * Gets the upload directory.
     * 
     * @return the upload directory
     * @throws FileSystemException the file system exception
     */
    public File getUploadDirectory() throws FileSystemException {
        return uploadDirectory;
    }

    /**
     * Gets the upload file.
     * 
     * @param trackerId the tracker id
     * @return the upload file
     * @throws FileSystemException the file system exception
     */
    public File getUploadFile(String trackerId) throws FileSystemException {
        return FileUtil.getUniqueFile(uploadDirectory, trackerId);
    }

    /**
     * Creates the upload file.
     * 
     * @param trackerId the tracker id
     * @param fileName the file name
     * @return the file
     * @throws FileSystemException the file system exception
     */
    public File createUploadFile(String trackerId, String fileName) throws FileSystemException {
        File uploadFile = getUploadFile(trackerId);
        return FileUtil.createFile(uploadFile, fileName);
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
