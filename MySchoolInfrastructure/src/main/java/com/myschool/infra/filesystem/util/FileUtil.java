package com.myschool.infra.filesystem.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.myschool.common.exception.FileSystemException;
import com.myschool.common.util.ResourceUtil;
import com.myschool.file.constant.FileExtension;
import com.myschool.file.filter.FileNameFilter;

/**
 * The Class FileUtil.
 */
public class FileUtil {

    /** The Constant FILE_EXTENSION_SEPARATOR. */
    public static final String FILE_EXTENSION_SEPARATOR = ".";

    /**
     * Gets the extension.
     *
     * @param fileName the file name
     * @return the extension
     */
    public static String getExtension(String fileName) {
        String extension = null;
        if (fileName != null) {
        	if (fileName.indexOf(FILE_EXTENSION_SEPARATOR) > 0) {
        		extension = fileName.substring(fileName.lastIndexOf(FILE_EXTENSION_SEPARATOR) + 1, fileName.length());
        	}
        }
        return extension;
    }

    /**
     * Gets the extension.
     * 
     * @param file the file
     * @return the extension
     */
    public static String getExtension(File file) {
        String extension = null;
        if (file != null) {
            String fileName = file.getName();
            if (fileName != null) {
                extension = fileName.substring(fileName.lastIndexOf(FILE_EXTENSION_SEPARATOR) + 1, fileName.length());
            }
        }
        return extension;
    }

    /**
     * Gets the file name.
     *
     * @param fileFullName the file full name
     * @return the file name
     */
    public static String getFileName(String fileFullName) {
        String fileName = null;
        if (fileFullName != null) {
            fileName = fileFullName.substring(0, fileFullName.lastIndexOf(FILE_EXTENSION_SEPARATOR));
        }
        return fileName;
    }

    /**
     * Gets the file name with extension.
     *
     * @param fileName the file name
     * @param fileExtension the file extension
     * @return the file name with extension
     */
    public static String getFileNameWithExtension(String fileName, FileExtension fileExtension) {
        if (fileName != null) {
            return fileName + "." + fileExtension.getFileExtension();
        }
        return null;
    }

    /**
     * Checks if is importable.
     *
     * @param fileName the file name
     * @return true, if is importable
     */
    public static boolean isImportable(String fileName) {
        boolean importable = false;
        String extension = getExtension(fileName);
        if (extension != null && extension.equalsIgnoreCase(FileExtension.XLS.toString())) {
            importable = true;
        }
        return importable;
    }

    /**
     * Creates the directory.
     *
     * @param directoryName the directory name
     */
    public static void createDirectory(String directoryName) {
        File directory = new File(directoryName);
        if (!directory.exists()) {
            directory.mkdir();
        }
    }

    /**
     * Creates the directory.
     *
     * @param directory the directory
     */
    public static void createDirectory(File directory) {
        if (!directory.exists()) {
            directory.mkdir();
        }
    }

    /**
     * Creates the directory.
     *
     * @param parentDirectory the parent directory
     * @param directoryName the directory name
     * @return the file
     */
    public static File createDirectory(File parentDirectory, String directoryName) {
        File directory = new File(parentDirectory, directoryName);
        if (!directory.exists()) {
            directory.mkdir();
        }
        return directory;
    }

    /**
     * Gets the unique file.
     *
     * @param parentDirectory the parent directory
     * @param fileName the file name
     * @return the file
     * @throws FileSystemException the file system exception
     */
    public static File getUniqueFile(File parentDirectory, String fileName) throws FileSystemException {
        long lastModified = 0;
        File gotFile = null;
        File file = null;
        String message = "No such directory: " + parentDirectory;
        checkDirectory(parentDirectory, message, message);

        FileNameFilter fileNameFilter = FileNameFilter.getInstance();
        fileNameFilter.setFileNameToFilter(fileName);
        File[] listFiles = parentDirectory.listFiles(fileNameFilter);
        if (listFiles != null) {
            for (int index=0; index<listFiles.length; index++) {
                file = listFiles[index];
                if (fileName.equalsIgnoreCase(file.getName())) {
                    gotFile = file;
                    break;
                }
                String fileNameWOExt = null;
                if (file.isFile()) {
                    fileNameWOExt = getFileName(file.getName());
                } else {
                    fileNameWOExt = file.getName();
                }
                if (file != null && fileName.equalsIgnoreCase(fileNameWOExt)
                        && file.lastModified() > lastModified) {
                    lastModified = file.lastModified();
                    gotFile = file;
                }
            }
        }
        return gotFile;
    }

    /**
     * Creates the directory.
     *
     * @param parentDirectory the parent directory
     * @param childDirectory the child directory
     */
    public static void createDirectory(String parentDirectory, String childDirectory) {
        File directory = new File(parentDirectory, childDirectory);
        if (!directory.exists()) {
            directory.mkdir();
        }
    }

    /**
     * Creates the file.
     *
     * @param file the file
     * @param bytes the bytes
     * @throws FileSystemException the file system exception
     */
    public static void writeToFile(File file, byte[] bytes) throws FileSystemException {
        FileOutputStream fileOutputStream = null;
        
        try {
            if (bytes != null && bytes.length > 0) {
                fileOutputStream = new FileOutputStream(file);
                fileOutputStream.write(bytes);
                fileOutputStream.flush();
            }
        } catch (FileNotFoundException fileNotFoundException) {
            throw new FileSystemException(fileNotFoundException.getMessage(), fileNotFoundException);
        } catch (IOException ioException) {
            throw new FileSystemException(ioException.getMessage(), ioException);
        } finally {
            ResourceUtil.releaseResource(fileOutputStream);
        }
    }

    /**
     * Write to file.
     *
     * @param file the file
     * @param content the content
     * @throws FileSystemException the file system exception
     */
    public static void writeToFile(File file, String content) throws FileSystemException {
        FileOutputStream fileOutputStream = null;
        try {
            if (content != null) {
                writeToFile(file, content.getBytes());
            }
        } finally {
            ResourceUtil.releaseResource(fileOutputStream);
        }
    }

    /**
     * Move file.
     *
     * @param fromFile the from file
     * @param toFile the to file
     * @throws FileSystemException the file system exception
     */
    public static void moveFile(File fromFile, File toFile) throws FileSystemException {
        int read = 0;
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;

        try {
            if (fromFile != null && fromFile.exists()) {
                fileInputStream = new FileInputStream(fromFile);
                fileOutputStream = new FileOutputStream(toFile);
                while ((read = fileInputStream.read()) != -1) {
                    fileOutputStream.write(read);
                }
            }
        } catch (FileNotFoundException fileNotFoundException) {
            throw new FileSystemException(fileNotFoundException.getMessage(), fileNotFoundException);
        } catch (IOException ioException) {
            throw new FileSystemException(ioException.getMessage(), ioException);
        } finally {
            ResourceUtil.releaseResource(fileOutputStream);
            ResourceUtil.releaseResource(fileInputStream);
        }
        fromFile.delete();
    }

    /**
     * Delete directory.
     *
     * @param directory the directory
     */
    public static void deleteDirectory(String directory) {
        if (directory != null) {
            deleteDirectory(new File(directory));
        }
    }

    /**
     * Delete directory.
     *
     * @param directory the directory
     */
    public static void deleteDirectory(File directory) {
        if (directory.exists()) {
            directory.delete();
        }
    }

    /**
     * Check directory.
     *
     * @param fileName the file name
     * @param missingMessage the missing message
     * @param invalidMessage the invalid message
     * @return the file
     * @throws FileSystemException the file system exception
     */
    public static File checkDirectory(String fileName, String missingMessage, String invalidMessage) throws FileSystemException {
        return checkFile(fileName, false, missingMessage, invalidMessage);
    }

    /**
     * Check directory.
     *
     * @param file the file
     * @param missingMessage the missing message
     * @param invalidMessage the invalid message
     * @return the file
     * @throws FileSystemException the file system exception
     */
    public static File checkDirectory(File file, String missingMessage, String invalidMessage) throws FileSystemException {
        if (file == null) {
            throw new FileSystemException(missingMessage);
        }
        return checkFile(file.getAbsolutePath(), false, missingMessage, invalidMessage);
    }

    /**
     * Check file.
     *
     * @param fileName the file name
     * @param missingMessage the missing message
     * @param invalidMessage the invalid message
     * @return the file
     * @throws FileSystemException the file system exception
     */
    public static File checkFile(String fileName, String missingMessage, String invalidMessage) throws FileSystemException {
        return checkFile(fileName, true, missingMessage, invalidMessage);
    }

    /**
     * Check file.
     *
     * @param file the file
     * @param missingMessage the missing message
     * @param invalidMessage the invalid message
     * @return the file
     * @throws FileSystemException the file system exception
     */
    public static File checkFile(File file, String missingMessage, String invalidMessage) throws FileSystemException {
        if (file == null) {
            throw new FileSystemException(missingMessage);
        }
        return checkFile(file.getAbsolutePath(), true, missingMessage, invalidMessage);
    }

    /**
     * Check file.
     *
     * @param fileName the file name
     * @param isFile the is file
     * @param missingMessage the missing message
     * @param invalidMessage the invalid message
     * @return the file
     * @throws FileSystemException the file system exception
     */
    public static File checkFile(String fileName, boolean isFile, String missingMessage, String invalidMessage) throws FileSystemException {
        if (fileName == null) {
            throw new FileSystemException(missingMessage);
        }
        File file = new File(fileName);
        boolean validFileType = false;
        if (isFile && file.isFile()) {
            validFileType = true;
        } else if (file.isDirectory()) {
            validFileType = true;
        }
        if (!file.exists() || !validFileType || !file.canRead()) {
            throw new FileSystemException(invalidMessage);
        }
        return file;
    }

    /**
     * Creates the file.
     *
     * @param directory the directory
     * @param fileName the file name
     * @param fileContent the file content
     * @return the file
     * @throws FileSystemException the file system exception
     */
    public static File createFile(File directory, String fileName,
            String fileContent) throws FileSystemException {
        File fileToCreate;
        try {
            fileToCreate = new File(directory, fileName);
            fileToCreate.createNewFile();
            if (fileContent != null) {
                writeToFile(fileToCreate, fileContent.getBytes());
            }
        } catch (IOException ioException) {
            throw new FileSystemException(ioException.getMessage(), ioException);
        }
        return fileToCreate;
    }

    /**
     * Creates the file.
     *
     * @param directory the directory
     * @param fileName the file name
     * @return the file
     * @throws FileSystemException the file system exception
     */
    public static File createFile(File directory, String fileName) throws FileSystemException {
        File fileToCreate;
        try {
            fileToCreate = new File(directory, fileName);
            fileToCreate.createNewFile();
        } catch (IOException ioException) {
            throw new FileSystemException(ioException.getMessage(), ioException);
        }
        return fileToCreate;
    }

    /**
     * Gets the file content.
     *
     * @param file the template file
     * @return the file content
     * @throws FileSystemException the file system exception
     */
    public static String getFileContent(File file) throws FileSystemException {
        byte[] buffer = new byte[(int) file.length()];
        StringBuffer content = new StringBuffer();
        FileInputStream fileInputStream = null;

        try {
            if (file != null) {
                fileInputStream = new FileInputStream(file);
                fileInputStream.read(buffer);
                content.append(new String(buffer));
            }
        } catch (IOException ioException) {
            throw new FileSystemException(ioException.getMessage(), ioException);
        } finally {
            ResourceUtil.releaseResource(fileInputStream);
        }
        return content.toString();
    }

    /**
     * Write from stream.
     * 
     * @param inputStream the input stream
     * @param outputFile the output file
     * @throws FileSystemException the file system exception
     */
    public static void writeFromStream(InputStream inputStream, File outputFile)
            throws FileSystemException {
        byte[] buffer = new byte[1024];
        FileOutputStream fileOutputStream = null;

        try {
            if (inputStream != null) {
                fileOutputStream = new FileOutputStream(outputFile, false);
                while (inputStream.available() > 0) {
                    inputStream.read(buffer);
                    fileOutputStream.write(buffer);
                }
                fileOutputStream.flush();
            }
        } catch (IOException ioException) {
            throw new FileSystemException(ioException.getMessage(), ioException);
        } finally {
            ResourceUtil.releaseResource(inputStream);
            ResourceUtil.releaseResource(fileOutputStream);
        }
    }

    /**
     * Gets the renamed file.
     *
     * @param file the file
     * @param newName the new name
     * @return the renamed file
     * @throws FileSystemException the file system exception
     */
    public static File getRenamedFile(File file, String newName) throws FileSystemException {
        String invalidMessage = "Invalid file.";
        checkFile(file, invalidMessage, invalidMessage);

        String filePath = file.getAbsolutePath();
        StringBuffer buffer = new StringBuffer(file.getAbsolutePath());
        int lastSeparatorIndex = 0;
        if (filePath.lastIndexOf(File.separator) != -1) {
            lastSeparatorIndex = filePath.lastIndexOf(File.separator) + 1;
        }
        buffer.append(filePath.substring(0, filePath.lastIndexOf(lastSeparatorIndex)));
        buffer.append(newName);
        buffer.append(filePath.substring(filePath.lastIndexOf("."), filePath.length()));
        return new File(buffer.toString());
    }

    /**
     * Gets the prefixed file.
     *
     * @param file the file
     * @param prefix the prefix
     * @return the prefixed file
     * @throws FileSystemException the file system exception
     */
    public static File getPrefixedFile(File file, String prefix) throws FileSystemException {
        String invalidMessage = "No such file.";
        checkFile(file, invalidMessage, invalidMessage);
        String imageFilePath = file.getAbsolutePath();
        StringBuffer buffer = new StringBuffer(file.getAbsolutePath());
        int lastSeparatorIndex = 0;
        if (imageFilePath.lastIndexOf(File.separator) != -1) {
            lastSeparatorIndex = imageFilePath.lastIndexOf(File.separator) + 1;
        }
        buffer.insert(lastSeparatorIndex, prefix);
        return new File(buffer.toString());
    }

    /**
     * Gets the prefixed file.
     *
     * @param filePath the file path
     * @param prefix the prefix
     * @return the prefixed file
     * @throws FileSystemException the file system exception
     */
    public static File getPrefixedFile(String filePath, String prefix) throws FileSystemException {
        return getPrefixedFile(new File(filePath), prefix);
    }

    /**
     * Gets the latest file.
     * 
     * @param files the files
     * @return the latest file
     */
    public static File getLatestFile(File[] files) {
        long lastModified = Long.MIN_VALUE;
        File latestFile = null;
        if (files != null && files.length != 0) {
            for (File file : files) {
                if (file.lastModified() > lastModified) {
                    latestFile = file;
                    lastModified = file.lastModified();
                }
            }
        }
        return latestFile;
    }

	/**
	 * Rename.
	 *
	 * @param oldFile the old file
	 * @param newFileName the new file name
	 * @return true, if successful
	 */
	public static boolean rename(File oldFile, String newFileName) {
		if (oldFile != null && newFileName != null) {
			File newFile = new File(oldFile.getParent(), newFileName);
			return oldFile.renameTo(newFile);
		}
		return false;
	}

}
