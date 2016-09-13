package com.myschool.infra.storage.local.filesystem;

import java.io.File;
import java.io.FileFilter;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.myschool.image.constant.ImageSize;
import com.myschool.infra.storage.StorageAccessAgent;
import com.myschool.infra.storage.exception.StorageAccessException;
import com.myschool.storage.dto.StorageItem;
import com.myschool.storage.dto.StorageProviderDto;

/**
 * The Class FileSystemStorageAccess.
 */
//@Component
public class FileSystemStorageAccess extends StorageAccessAgent<File> {

    /** The datastore dir. */
    private static File DATASTORE_DIR;

    /** The file filter. */
    private static FileFilter FILE_FILTER;

    /** The directory filter. */
    private static FileFilter DIRECTORY_FILTER;

    /* (non-Javadoc)
     * @see com.myschool.infra.storage.StorageAccessAgent#initializeStorage()
     */
    @Override
    protected void initializeStorage() throws StorageAccessException {
        StorageProviderDto storageProvider = storageConfig.getStorageProvider();
        Map<String, String> params = storageProvider.getParams();
        String datastore = params.get("DATA_STORE_DIR");

        DATASTORE_DIR = new File(datastore);
        if (!DATASTORE_DIR.exists()) {
            boolean mkdir = DATASTORE_DIR.mkdir();
            if (!mkdir) {
                throw new StorageAccessException("Unable to create base directory: " + DATASTORE_DIR);
            }
        }

        FILE_FILTER = new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.isFile();
            }
        };

        DIRECTORY_FILTER = new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.isDirectory();
            }
        };
    }

    /* (non-Javadoc)
     * @see com.myschool.infra.storage.StorageAccessAgent#getFileSeperator()
     */
    @Override
    protected String getFileSeperator() {
        return File.separator;
    }

    /* (non-Javadoc)
     * @see com.myschool.infra.storage.StorageAccessAgent#getStorageId(java.lang.Object)
     */
    @Override
    protected String getStorageId(File file) {
        return getStoragePath(file);
    }

    /* (non-Javadoc)
     * @see com.myschool.infra.storage.StorageAccessAgent#getStorageName(java.lang.Object)
     */
    @Override
    protected String getStorageName(File file) {
        return (file == null) ? null: file.getName();
    }

    /* (non-Javadoc)
     * @see com.myschool.infra.storage.StorageAccessAgent#getStorageLocation(java.lang.Object)
     */
    @Override
    protected String getStorageLocation(File file)
            {
        return (file == null) ? null: file.getParent();
    }

    /* (non-Javadoc)
     * @see com.myschool.infra.storage.StorageAccessAgent#getStoragePath(java.lang.Object)
     */
    @Override
    protected String getStoragePath(File file) {
        return (file == null) ? null: file.getAbsolutePath();
    }

    /* (non-Javadoc)
     * @see com.myschool.infra.storage.StorageAccessAgent#setStorageLocation(java.lang.Object, java.lang.String)
     */
    @Override
    protected void setStorageLocation(File file, String location) {
    }

    /* (non-Javadoc)
     * @see com.myschool.infra.storage.StorageAccessAgent#setStoragePath(java.lang.Object, java.lang.String)
     */
    @Override
    protected void setStoragePath(File file, String path) {
    }

    /* (non-Javadoc)
     * @see com.myschool.infra.storage.StorageAccessAgent#isFolder(java.lang.Object)
     */
    @Override
    protected boolean isFolder(File file) {
        return (file != null && file.isDirectory());
    }

    /* (non-Javadoc)
     * @see com.myschool.infra.storage.StorageAccessAgent#addFolderToStorage(java.lang.Object, java.lang.String)
     */
    @Override
    protected File addFolderToStorage(File parentFile, String folderName)
            throws StorageAccessException {
        File directory = null;
        if (parentFile == null) {
            directory = new File(DATASTORE_DIR, folderName);
        } else {
            directory = new File(parentFile, folderName);
        }
        if (!directory.exists()) {
            boolean mkdir = directory.mkdir();
            if (!mkdir) {
                throw new StorageAccessException("Unable to create directory: " + folderName);
            }
        }
        return directory;
    }

    /* (non-Javadoc)
     * @see com.myschool.infra.storage.StorageAccessAgent#addFileToStorage(java.lang.Object, java.io.File, java.lang.String)
     */
    @Override
    protected File addFileToStorage(File parentFile, File file, String fileName)
            throws StorageAccessException {
        File newFile = new File(parentFile, fileName);
        boolean status = file.renameTo(newFile);
        if (!status) {
            throw new StorageAccessException("Unable to add file '" + fileName + "' to parent: " + parentFile.getAbsolutePath());
        }
        return newFile;
    }

    @Override
    protected List<File> getAllFromStorage(String folderId, Boolean onlyFiles)
            throws StorageAccessException {
        return getAllFromStorage((folderId == null) ? DATASTORE_DIR : new File(folderId), onlyFiles);
    }

    protected List<File> getAllFromStorage(File file, Boolean onlyFiles)
            throws StorageAccessException {
        File[] files = null;
        if (onlyFiles == null) {
            files = file.listFiles();
        } else if (onlyFiles == Boolean.TRUE) {
            files = file.listFiles(FILE_FILTER);
        } else if (onlyFiles == Boolean.TRUE) {
            files = file.listFiles(DIRECTORY_FILTER);
        }
        if (files != null) {
            return Arrays.asList(files);
        }
        return null;
    }

    /* (non-Javadoc)
     * @see com.myschool.infra.storage.StorageAccessAgent#createStorageItem(java.lang.Object)
     */
    @Override
    protected StorageItem createStorageItem(File file) throws StorageAccessException {
        StorageItem storageItem = null;
        if (file != null) {
            storageItem = new StorageItem();
            StorageProviderDto storageProvider = storageConfig.getStorageProvider();

            storageItem.setId(file.getAbsolutePath());
            storageItem.setName(file.getName());
            storageItem.setSize(file.length());
            storageItem.setCreatedTime(file.lastModified());
            storageItem.setModifiedTime(file.lastModified());

            String storageLocation = getStorageLocation(file);
            String storageName = getStorageName(file);
            storageItem.setDirectLink(MessageFormat.format(storageProvider.getDirectLink(), file.getAbsolutePath()));

            String passportPath = storageLocation + FILE_SEPARATOR + ImageSize.PASSPORT + FILE_SEPARATOR + storageName;
            File passportFile = getFile(passportPath);
            if (passportFile != null) {
                storageItem.setPassportLink(MessageFormat.format(storageProvider.getPassportLink(), passportFile.getAbsolutePath()));
            }
            String thumbnailPath = storageLocation + FILE_SEPARATOR + ImageSize.THUMBNAIL + FILE_SEPARATOR + storageName;
            File thumbnailFile = getFile(thumbnailPath);
            if (thumbnailFile != null) {
                storageItem.setThumbnailLink(MessageFormat.format(storageProvider.getThumbnailLink(), thumbnailFile.getAbsolutePath()));
            }
        }
        return storageItem;
    }

    /* (non-Javadoc)
     * @see com.myschool.infra.storage.StorageAccessAgent#createStorageItems(java.util.List)
     
    @Override
    protected List<StorageItem> createStorageItems(List<File> files)
            throws StorageAccessException {
        List<StorageItem> storageItems = null;
        if (files != null && !files.isEmpty()) {
            storageItems = new ArrayList<StorageItem>();

            StorageProviderDto storageProvider = storageConfig.getStorageProvider();
            for (File file : files) {
                StorageItem storageItem = new StorageItem();
                storageItem.setCreatedTime(file.lastModified());
                storageItem.setId(file.getAbsolutePath());
                storageItem.setDirectLink(MessageFormat.format(storageProvider.getDirectLink(), storageItem.getId()));
                storageItem.setModifiedTime(file.lastModified());
                storageItem.setName(file.getName());
                storageItem.setPassportLink(MessageFormat.format(storageProvider.getPassportLink(), storageItem.getId()));
                storageItem.setSize(file.length());
                storageItem.setThumbnailLink(MessageFormat.format(storageProvider.getThumbnailLink(), storageItem.getId()));
                storageItems.add(storageItem);
            }
        }
        return storageItems;
    }*/

    /* (non-Javadoc)
     * @see com.myschool.infra.storage.StorageAccessAgent#deleteFileFromStorage(java.lang.Object)
     */
    @Override
    protected boolean deleteFileFromStorage(File file)
            throws StorageAccessException {
        return false;
    }

    /* (non-Javadoc)
     * @see com.myschool.infra.storage.StorageAccessAgent#moveFileInStorage(java.lang.Object, java.lang.Object, java.lang.Object)
     */
    @Override
    protected File moveFileInStorage(File from, File to, File file)
            throws StorageAccessException {
        return null;
    }

}
