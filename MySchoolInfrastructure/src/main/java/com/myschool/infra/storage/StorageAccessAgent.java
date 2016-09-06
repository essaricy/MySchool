package com.myschool.infra.storage;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.common.exception.AgentException;
import com.myschool.common.exception.ConfigurationException;
import com.myschool.common.exception.FileSystemException;
import com.myschool.filesystem.dto.DirectoryDto;
import com.myschool.infra.agent.AbstractAgent;
import com.myschool.infra.image.agent.ImageScalingAgent;
import com.myschool.infra.image.constants.ImageSize;
import com.myschool.infra.storage.exception.StorageAccessException;
import com.myschool.infra.storage.reader.StorageConfigReader;
import com.myschool.storage.constant.StorageAccessConstant;
import com.myschool.storage.constant.WriteMode;
import com.myschool.storage.dto.StorageConfig;
import com.myschool.storage.dto.StorageItem;

/**
 * The Class StorageAccessAgent.
 *
 * @param <T> the generic type
 */
@Component
public abstract class StorageAccessAgent<T> extends AbstractAgent {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(StorageAccessAgent.class);

    /** The file separator. */
    protected static String FILE_SEPARATOR;

    /** The file path image map. */
    protected Map<String, T> FILE_PATH_IMAGE_MAP;

    /** The storage config. */
    protected StorageConfig storageConfig;

    /** The image scaling agent. */
    @Autowired
    private ImageScalingAgent imageScalingAgent;

    /** The storage config reader. */
    @Autowired
    private StorageConfigReader storageConfigReader;

    /** The brochure storage. */
    public FileStorage BROCHURE_STORAGE;

    /** The logs storage. */
    public FileStorage LOGS_STORAGE;

    /** The feature storage. */
    public ImageStorage FEATURE_STORAGE;

    /** The gallery storage. */
    public ImageStorage GALLERY_STORAGE;

    /** The greeting storage. */
    public ImageStorage GREETING_STORAGE;

    /** The organization storage. */
    public ImageStorage ORGANIZATION_STORAGE;

    /** The product storage. */
    public ImageStorage PRODUCT_STORAGE;

    /** The employee storage. */
    public ProfileStorage EMPLOYEE_STORAGE;

    /** The student storage. */
    public ProfileStorage STUDENT_STORAGE;

    /**
     * Initialize storage.
     *
     * @throws StorageAccessException the storage access exception
     */
    protected abstract void initializeStorage()  throws StorageAccessException;

    /**
     * Gets the file seperator.
     *
     * @return the file seperator
     */
    protected abstract String getFileSeperator();

    /**
     * Gets the storage id.
     *
     * @param file the file
     * @return the storage id
     */
    protected abstract String getStorageId(T file);

    /**
     * Gets the storage name.
     *
     * @param file the file
     * @return the storage name
     */
    protected abstract String getStorageName(T file);

    /**
     * Gets the storage location.
     *
     * @param file the file
     * @return the storage location
     */
    protected abstract String getStorageLocation(T file);

    /**
     * Gets the storage path.
     *
     * @param file the file
     * @return the storage path
     */
    protected abstract String getStoragePath(T file);

    /**
     * Sets the storage location.
     *
     * @param file the file
     * @param location the location
     */
    protected abstract void setStorageLocation(T file, String location);

    /**
     * Sets the storage path.
     *
     * @param file the file
     * @param path the path
     */
    protected abstract void setStoragePath(T file, String path);

    /**
     * Checks if is folder.
     *
     * @param file the file
     * @return true, if is folder
     */
    protected abstract boolean isFolder(T file);

    /**
     * Adds the folder to storage.
     *
     * @param parentFile the parent file
     * @param folderName the folder name
     * @return the t
     * @throws StorageAccessException the storage access exception
     */
    protected abstract T addFolderToStorage(T parentFile, String folderName) throws StorageAccessException;

    /**
     * Adds the file to storage.
     *
     * @param parentFile the parent file
     * @param file the file
     * @param fileName the file name
     * @return the t
     * @throws StorageAccessException the storage access exception
     */
    protected abstract T addFileToStorage(T parentFile, File file, String fileName) throws StorageAccessException;

    /**
     * Gets the all from storage.
     *
     * @param folderId the folder id
     * @param onlyFiles the only files
     * @return the all from storage
     * @throws StorageAccessException the storage access exception
     */
    protected abstract List<T> getAllFromStorage(String folderId, Boolean onlyFiles) throws StorageAccessException;

    /**
     * Creates the storage item.
     *
     * @param file the file
     * @return the storage item
     * @throws StorageAccessException the storage access exception
     */
    protected abstract StorageItem createStorageItem(T file) throws StorageAccessException;

    /**
     * Delete file from storage.
     *
     * @param file the file
     * @return true, if successful
     * @throws StorageAccessException the storage access exception
     */
    protected abstract boolean deleteFileFromStorage(T file) throws StorageAccessException;

    /**
     * Move file in storage.
     *
     * @param from the from
     * @param to the to
     * @param file the file
     * @return the t
     * @throws StorageAccessException the storage access exception
     */
    protected abstract T moveFileInStorage(T from, T to, T file) throws StorageAccessException;

    /* (non-Javadoc)
     * @see com.myschool.infra.agent.Agent#loadConfiguration(java.io.File)
     */
    @Override
    public void loadConfiguration(File configFile)
            throws ConfigurationException {

        storageConfig = storageConfigReader.getStorageConfig(configFile);
        FILE_SEPARATOR = getFileSeperator();
        FILE_PATH_IMAGE_MAP = new HashMap<String, T>() {

            private static final long serialVersionUID = 1L;

            @Override
            public T put(String key, T value) {
                if (containsKey(key)) {
                    LOGGER.info("Update: " + key + "=" + getStorageId(value));
                } else {
                    LOGGER.info("Add: " + key + "=" + getStorageId(value));
                }
                return super.put(key, value);
            }

            @Override
            public T remove(Object key) {
                LOGGER.info("Remove: " + key + "=" + getStorageId(get(key)));
                return super.remove(key);
            }
        };
    }

    /* (non-Javadoc)
     * @see com.myschool.infra.agent.Agent#validate()
     */
    @Override
    public void validate() throws AgentException {
        try {
            initializeStorage();
            createFilePathIdMap(null);

            List<DirectoryDto> directories = storageConfig.getDirectories();
            createHierarchy(directories);

        } catch (StorageAccessException storageAccessException) {
            throw new AgentException(storageAccessException.getMessage(), storageAccessException);
        }
    }

    /**
     * Creates the hierarchy.
     *
     * @param directories the directories
     * @throws StorageAccessException the storage access exception
     */
    private void createHierarchy(List<DirectoryDto> directories)
            throws StorageAccessException {
        if (directories != null && !directories.isEmpty()) {
            for (DirectoryDto directory : directories) {
                createDirectory(directory);
                createHierarchy(directory.getDirectories());
            }
        }
    }

    /**
     * Creates the directory.
     *
     * @param directory the directory
     * @throws StorageAccessException the storage access exception
     */
    private void createDirectory(DirectoryDto directory) throws StorageAccessException {
        String id = directory.getId();
        String name = directory.getName();
        String path = directory.getPath();
        String parentPath = directory.getParentPath();

        String filePath = (path == null) ? name : path;

        if (id == null || id.trim().length() == 0) {
            createDirectory(filePath);
        } else if (id.equals(StorageAccessConstant.LOGS_STORAGE)) {
            LOGS_STORAGE = new FileStorage(name);
        } else if (id.equals(StorageAccessConstant.BROCHURE_STORAGE)) {
            BROCHURE_STORAGE = new FileStorage(filePath);
        } else if (id.equals(StorageAccessConstant.EMPLOYEE_STORAGE)) {
            EMPLOYEE_STORAGE = new ProfileStorage(filePath);
        } else if (id.equals(StorageAccessConstant.FEATURE_STORAGE)) {
            FEATURE_STORAGE = new ImageStorage(parentPath, name);
        } else if (id.equals(StorageAccessConstant.GALLERY_STORAGE)) {
            GALLERY_STORAGE = new ImageStorage(filePath);
        } else if (id.equals(StorageAccessConstant.GREETING_STORAGE)) {
            GREETING_STORAGE = new ImageStorage(filePath);
            List<DirectoryDto> directories = directory.getDirectories();
            if (directories != null && !directories.isEmpty()) {
                for (DirectoryDto childDirectory : directories) {
                    GREETING_STORAGE.addStore(childDirectory.getName());
                }
            }
        } else if (id.equals(StorageAccessConstant.ORGANIZATION_STORAGE)) {
            ORGANIZATION_STORAGE = new ImageStorage(parentPath, name);
        } else if (id.equals(StorageAccessConstant.PRODUCT_STORAGE)) {
            PRODUCT_STORAGE = new ImageStorage(parentPath, name);
        } else if (id.equals(StorageAccessConstant.STUDENT_STORAGE)) {
            STUDENT_STORAGE = new ProfileStorage(filePath);
        }
    }

    /**
     * The Class AbstractStorage.
     */
    private abstract class AbstractStorage {

        /** The storage path. */
        protected String storagePath;

        /**
         * Instantiates a new abstract storage.
         *
         * @param storagePath the storage path
         */
        AbstractStorage(String storagePath) {
            this.storagePath=storagePath;
        }

        /**
         * Gets the storage.
         *
         * @return the storage
         * @throws StorageAccessException the storage access exception
         */
        protected T getStorage() throws StorageAccessException {
            return getFile(storagePath);
        }

        /**
         * Gets the storage path.
         *
         * @return the storage path
         * @throws StorageAccessException the storage access exception
         */
        protected String getStoragePath() throws StorageAccessException {
            return storagePath;
        }

        /**
         * Adds the.
         *
         * @param file the file
         * @return the storage item
         * @throws StorageAccessException the storage access exception
         */
        protected abstract StorageItem add(File file) throws StorageAccessException;

        /**
         * Update.
         *
         * @param file the file
         * @return the storage item
         * @throws StorageAccessException the storage access exception
         */
        protected abstract StorageItem update(File file) throws StorageAccessException;

        /**
         * Delete.
         *
         * @param fileName the file name
         * @return true, if successful
         * @throws StorageAccessException the storage access exception
         */
        protected abstract boolean delete(String fileName) throws StorageAccessException;

    }

    /**
     * The Class FileStorage.
     */
    public class FileStorage extends AbstractStorage {

        /**
         * Instantiates a new file storage.
         *
         * @param storagePath the storage path
         * @throws StorageAccessException the storage access exception
         */
        FileStorage(String storagePath) throws StorageAccessException {
            super(storagePath);
            createDirectory(storagePath);
        }

        /* (non-Javadoc)
         * @see com.myschool.infra.storage.StorageAccessAgent.AbstractStorage#add(java.io.File)
         */
        @Override
        public StorageItem add(File file) throws StorageAccessException {
            return createStorageItem(addFile(getStorage(), file));
        }

        /* (non-Javadoc)
         * @see com.myschool.infra.storage.StorageAccessAgent.AbstractStorage#update(java.io.File)
         */
        @Override
        public StorageItem update(File file) throws StorageAccessException {
            return createStorageItem(updateFile(getStorage(), file));
        }

        /* (non-Javadoc)
         * @see com.myschool.infra.storage.StorageAccessAgent.AbstractStorage#delete(java.lang.String)
         */
        @Override
        public boolean delete(String fileName) throws StorageAccessException {
            return deleteFile(getStorage(), fileName);
        }

    }

    /**
     * The Class ProfileStorage.
     */
    public class ProfileStorage extends AbstractStorage {

        /**
         * Instantiates a new profile storage.
         *
         * @param storagePath the storage path
         * @throws StorageAccessException the storage access exception
         */
        ProfileStorage(String storagePath) throws StorageAccessException {
            super(storagePath);
            createDirectory(storagePath);

            for (RecordStatus recordStatus : RecordStatus.values()) {
                addImageStore(storagePath, recordStatus.toString());
            }
        }

        /* (non-Javadoc)
         * @see com.myschool.infra.storage.StorageAccessAgent.AbstractStorage#add(java.io.File)
         */
        @Override
        public StorageItem add(File file) throws StorageAccessException {
            throw new StorageAccessException("Use add(file, recordStatus, identifier)");
        }

        /* (non-Javadoc)
         * @see com.myschool.infra.storage.StorageAccessAgent.AbstractStorage#update(java.io.File)
         */
        @Override
        public StorageItem update(File file) throws StorageAccessException {
            throw new StorageAccessException("Use update(file, recordStatus, identifier)");
        }

        /* (non-Javadoc)
         * @see com.myschool.infra.storage.StorageAccessAgent.AbstractStorage#delete(java.lang.String)
         */
        @Override
        public boolean delete(String fileName) throws StorageAccessException {
            throw new StorageAccessException("Use delete(recordStatus, identifier)");
        }

        /**
         * Gets the storage path.
         *
         * @param recordStatus the record status
         * @return the storage path
         * @throws StorageAccessException the storage access exception
         */
        protected String getStoragePath(RecordStatus recordStatus) throws StorageAccessException {
            return getStoragePath() + FILE_SEPARATOR + recordStatus.toString();
        }

        /**
         * Adds the.
         *
         * @param file the file
         * @param recordStatus the record status
         * @param id the id
         * @return the storage item
         * @throws StorageAccessException the storage access exception
         */
        public StorageItem add(File file, RecordStatus recordStatus, String id) throws StorageAccessException {
            return addImageStoreItem(getStoragePath(recordStatus), file, id, WriteMode.ADD_STRICT);
        }

        /**
         * Update.
         *
         * @param file the file
         * @param recordStatus the record status
         * @param id the id
         * @return the storage item
         * @throws StorageAccessException the storage access exception
         */
        public StorageItem update(File file, RecordStatus recordStatus, String id) throws StorageAccessException {
            return addImageStoreItem(getStoragePath(recordStatus), file, id, WriteMode.ADD_OR_UPDATE);
        }

        /**
         * Delete.
         *
         * @param recordStatus the record status
         * @param id the id
         * @return true, if successful
         * @throws StorageAccessException the storage access exception
         */
        public boolean delete(RecordStatus recordStatus, String id) throws StorageAccessException {
            return deleteImageStoreItem(getStoragePath(recordStatus), id);
        }

        /**
         * Verify.
         *
         * @param employeeNumber the employee number
         * @return true, if successful
         * @throws StorageAccessException the storage access exception
         */
        public boolean verify(String employeeNumber) throws StorageAccessException {
            return moveImageStoreItem(getStoragePath(RecordStatus.UNVERIFIED),
                    getStoragePath(RecordStatus.VERIFIED), employeeNumber);
        }
        
        /**
         * Unverify.
         *
         * @param employeeNumber the employee number
         * @return true, if successful
         * @throws StorageAccessException the storage access exception
         */
        public boolean unverify(String employeeNumber) throws StorageAccessException {
            return moveImageStoreItem(getStoragePath(RecordStatus.VERIFIED),
                    getStoragePath(RecordStatus.UNVERIFIED), employeeNumber);
        }

    }

    /**
     * The Class ImageStorage.
     */
    public class ImageStorage extends AbstractStorage {

        /**
         * Instantiates a new image storage.
         *
         * @param storagePath the storage path
         * @throws StorageAccessException the storage access exception
         */
        ImageStorage(String storagePath) throws StorageAccessException {
            super(storagePath);
            createDirectory(storagePath);
        }

        /**
         * Instantiates a new image storage.
         *
         * @param storageParent the storage parent
         * @param storeName the store name
         * @throws StorageAccessException the storage access exception
         */
        ImageStorage(String storageParent, String storeName) throws StorageAccessException {
            super(storageParent + FILE_SEPARATOR + storeName);
            addImageStore(storageParent, storeName);
        }

        /**
         * Gets the store path.
         *
         * @param storeName the store name
         * @return the store path
         * @throws StorageAccessException the storage access exception
         */
        protected String getStorePath(String storeName) throws StorageAccessException {
            return getStoragePath() + FILE_SEPARATOR + storeName;
        }

        /* (non-Javadoc)
         * @see com.myschool.infra.storage.StorageAccessAgent.AbstractStorage#add(java.io.File)
         */
        public StorageItem add(File file) throws StorageAccessException {
            return addImageStoreItem(getStoragePath(), file, null, WriteMode.ADD_STRICT);
        }

        /* (non-Javadoc)
         * @see com.myschool.infra.storage.StorageAccessAgent.AbstractStorage#update(java.io.File)
         */
        @Override
        public StorageItem update(File file) throws StorageAccessException {
            return addImageStoreItem(getStoragePath(), file, null, WriteMode.ADD_OR_UPDATE);
        }

        /* (non-Javadoc)
         * @see com.myschool.infra.storage.StorageAccessAgent.AbstractStorage#delete(java.lang.String)
         */
        @Override
        public boolean delete(String name) throws StorageAccessException {
            return deleteImageStoreItem(getStoragePath(), name);
        }

        /**
         * Adds the store.
         *
         * @param storeName the store name
         * @return true, if successful
         * @throws StorageAccessException the storage access exception
         */
        public boolean addStore(String storeName) throws StorageAccessException {
            return addImageStore(getStoragePath(), storeName);
        }

        /**
         * Delete store.
         *
         * @param storeName the store name
         * @return true, if successful
         * @throws StorageAccessException the storage access exception
         */
        public boolean deleteStore(String storeName) throws StorageAccessException {
            return deleteDirectory(getStorePath(storeName));
        }

        /**
         * Adds the store item.
         *
         * @param storeName the store name
         * @param file the file
         * @return true, if successful
         * @throws StorageAccessException the storage access exception
         */
        public StorageItem addStoreItem(String storeName, File file) throws StorageAccessException {
            return addImageStoreItem(getStorePath(storeName), file, null, WriteMode.ADD_OR_UPDATE);
        }

        /**
         * Adds the store items.
         *
         * @param storeName the store name
         * @param files the files
         * @return the list
         * @throws StorageAccessException the storage access exception
         */
        public List<StorageItem> addStoreItems(String storeName, File[] files) throws StorageAccessException {
            List<StorageItem> result = new ArrayList<StorageItem>();

            if (files != null && files.length != 0) {
                for (File file : files) {
                    result.add(addStoreItem(storeName, file));
                }
            }
            return result;
        }

        /**
         * Delete store item.
         *
         * @param storeName the store name
         * @param name the name
         * @return true, if successful
         * @throws StorageAccessException the storage access exception
         */
        public boolean deleteStoreItem(String storeName, String name) throws StorageAccessException {
            return deleteImageStoreItem(getStorePath(storeName), name);
        }

        /**
         * Gets the all.
         *
         * @return the all
         * @throws StorageAccessException the storage access exception
         */
        public List<StorageItem> getAll() throws StorageAccessException {
            String storagePath = getStoragePath();
            T file = getFile(storagePath);
            return StorageAccessAgent.this.getAllFromCache(file, Boolean.FALSE);
        }

        /**
         * Gets the all.
         *
         * @param storeName the store name
         * @return the all
         * @throws StorageAccessException the storage access exception
         */
        public List<StorageItem> getAll(String storeName) throws StorageAccessException {
            T storage = getStorage();
            T file = getFile(storage, storeName);
            return StorageAccessAgent.this.getAllFromCache(file, Boolean.TRUE);
        }
    }

    /**
     * Adds the image store.
     *
     * @param path the path
     * @param fileName the file name
     * @return true, if successful
     * @throws StorageAccessException the storage access exception
     */
    private boolean addImageStore(String path, String fileName) throws StorageAccessException {
        createDirectory(path + FILE_SEPARATOR + fileName);
        createDirectory(path + FILE_SEPARATOR + fileName + FILE_SEPARATOR + ImageSize.PASSPORT);
        createDirectory(path + FILE_SEPARATOR + fileName + FILE_SEPARATOR + ImageSize.THUMBNAIL);
        return true;
    }

    /**
     * Adds the image store item.
     *
     * @param path the path
     * @param file the file
     * @param name the name
     * @param writeMode the write mode
     * @return the storage item
     * @throws StorageAccessException the storage access exception
     */
    private StorageItem addImageStoreItem(String path, File file, String name,
            WriteMode writeMode) throws StorageAccessException {
        StorageItem storageItem = null;
        try {
            T imageStore = getFile(path);
            T passportStore = getFile(path + FILE_SEPARATOR + ImageSize.PASSPORT);
            T thumbnailStore = getFile(path + FILE_SEPARATOR + ImageSize.THUMBNAIL);

            if (imageStore == null) {
                throw new StorageAccessException("Image Store '" + path + "' does not exist. Create the store, then add items.");
            }
            if (file == null || !file.exists() || !file.isFile()) {
                throw new StorageAccessException("Could not find the item file. It is missing.");
            }
            if (writeMode == WriteMode.ADD_STRICT) {
                T addFile = addFile(imageStore, file, name);
                addFile(passportStore, imageScalingAgent.createPassportSizeImage(file), name);
                addFile(thumbnailStore, imageScalingAgent.createThumbnailImage(file), name);
                storageItem = createStorageItem(addFile);
            } else if (writeMode == WriteMode.ADD_OR_UPDATE) {
                T updateFile = addOrUpdateFile(imageStore, file, name);
                addOrUpdateFile(passportStore, imageScalingAgent.createPassportSizeImage(file), name);
                addOrUpdateFile(thumbnailStore, imageScalingAgent.createThumbnailImage(file), name);
                storageItem = createStorageItem(updateFile); 
            }
        } catch (FileSystemException fileSystemException) {
            throw new StorageAccessException(fileSystemException.getMessage(), fileSystemException);
        }
        return storageItem;
    }

    /**
     * Delete image store item.
     *
     * @param path the path
     * @param fileName the file name
     * @return true, if successful
     * @throws StorageAccessException the storage access exception
     */
    private boolean deleteImageStoreItem(String path, String fileName) throws StorageAccessException {
        T imageStore = getFile(path);
        T passportStore = getFile(path + FILE_SEPARATOR + ImageSize.PASSPORT);
        T thumbnailStore = getFile(path + FILE_SEPARATOR + ImageSize.THUMBNAIL);

        if (imageStore == null) {
            throw new StorageAccessException("Image Store '" + path + "' does not exist. Create the store, then add items.");
        }
        deleteFile(imageStore, fileName);
        FILE_PATH_IMAGE_MAP.remove(path);
        deleteFile(passportStore, fileName);
        deleteFile(thumbnailStore, fileName);

        return true;
    }

    /**
     * Move image store item.
     *
     * @param fromPath the from path
     * @param toPath the to path
     * @param fileName the file name
     * @return true, if successful
     * @throws StorageAccessException the storage access exception
     */
    private boolean moveImageStoreItem(String fromPath, String toPath, String fileName) throws StorageAccessException {

        T fromImageStore = getFile(fromPath);
        T fromPassportStore = getFile(fromPath + FILE_SEPARATOR + ImageSize.PASSPORT);
        T fromThumbnailStore = getFile(fromPath + FILE_SEPARATOR + ImageSize.THUMBNAIL);

        T toImageStore = getFile(toPath);
        T toPassportStore = getFile(toPath + FILE_SEPARATOR + ImageSize.PASSPORT);
        T toThumbnailStore = getFile(toPath + FILE_SEPARATOR + ImageSize.THUMBNAIL);

        if (fromImageStore == null) {
            throw new StorageAccessException("Image Store '" + fromPath + "' does not exist.");
        }
        if (toImageStore == null) {
            throw new StorageAccessException("Image Store '" + toPath + "' does not exist.");
        }
        moveFile(fromImageStore, toImageStore, fileName);
        moveFile(fromPassportStore, toPassportStore, fileName);
        moveFile(fromThumbnailStore, toThumbnailStore, fileName);
        return true;
    }

    /**
     * Creates the file path id map.
     *
     * @param folder the folder
     * @throws StorageAccessException the storage access exception
     */
    protected void createFilePathIdMap(T folder) throws StorageAccessException {
        List<T> childFiles = null;
        String location = null;
        if (folder == null) {
            childFiles = getAllFromStorage(null, null);
        } else {
            childFiles = getAllFromStorage(getStorageId(folder), null);
            location = getStorageLocation(folder);
            if (location == null) {
                location = getStorageName(folder);
            } else {
                location = location + FILE_SEPARATOR + getStorageName(folder);
            }
        }
        if (childFiles != null && !childFiles.isEmpty()) {
            for (T childFile : childFiles) {
                String fileName = getStorageName(childFile);
                String filePath = (location == null? fileName : location + FILE_SEPARATOR + fileName);

                setStorageLocation(childFile, location);
                setStoragePath(childFile, filePath);

                FILE_PATH_IMAGE_MAP.put(filePath, childFile);

                if (isFolder(childFile)) {
                    createFilePathIdMap(childFile);
                }
            }
        }
    }

    /**
     * Gets the all from cache.
     *
     * @param store the store
     * @param onlyFiles the only files
     * @return the all from cache
     */
    private List<StorageItem> getAllFromCache(T store, Boolean onlyFiles) {
        StorageItem storageItem = null;
        List<StorageItem> storageItems = null;
        String storagePath = getStoragePath(store);
        System.out.println("storagePath=" + storagePath);

        if (storagePath != null) {
            Set<Entry<String, T>> entrySet = FILE_PATH_IMAGE_MAP.entrySet();
            for (Iterator<Entry<String, T>> iterator = entrySet.iterator(); iterator.hasNext();) {
                Entry<String, T> entry = iterator.next();
                String key = entry.getKey();
                if (!key.startsWith(storagePath)) {
                    continue;
                }

                String path = key.substring(0, key.lastIndexOf(FILE_SEPARATOR));
                if (!path.matches(storagePath)) {
                    continue;
                }

                T file = FILE_PATH_IMAGE_MAP.get(key);
                if (onlyFiles != null) {
                    if ((onlyFiles == Boolean.TRUE && isFolder(file)
                            || (onlyFiles == Boolean.FALSE && !isFolder(file)))) {
                        continue;
                    }
                }
                try {
                    if (storageItems == null) {
                        storageItems = new ArrayList<StorageItem>();
                    }
                    String name = key.substring(storagePath.length() + 1);
                    System.out.println("name=" + name);
                    storageItem = createStorageItem(file);
                    System.out.println("storageItem=" + storageItem);
                    storageItems.add(storageItem);
                } catch (StorageAccessException storageAccessException) {
                    LOGGER.error(storageAccessException.getMessage(), storageAccessException);
                }
            }
        }
        return storageItems;
    }

    /**
     * Gets the file.
     *
     * @param filePath the file path
     * @return the file
     * @throws StorageAccessException the storage access exception
     */
    public T getFile(String filePath) throws StorageAccessException {
        return FILE_PATH_IMAGE_MAP.get(filePath);
    }

    /**
     * Gets the file.
     *
     * @param parent the parent
     * @param fileName the file name
     * @return the file
     * @throws StorageAccessException the storage access exception
     */
    public T getFile(T parent, String fileName) throws StorageAccessException {
        String key = null;
        if (parent == null) {
            key = fileName;
        } else {
            String parentPath = getStoragePath(parent);
            if (parentPath == null) {
                key = fileName;
            } else {
                key = parentPath + FILE_SEPARATOR + fileName;
            }
        }
        return FILE_PATH_IMAGE_MAP.get(key);
    }

    /**
     * Creates the directory.
     *
     * @param filePath the file path
     * @return true, if successful
     * @throws StorageAccessException the storage access exception
     */
    public boolean createDirectory(String filePath) throws StorageAccessException {
        String parentPath = null;
        String folderName = null;

        if (filePath == null || filePath.trim().length() == 0 ) {
            throw new StorageAccessException("Invalid file name: " + filePath);
        }
        T file = getFile(filePath);
        if (file == null) {
            if (filePath.indexOf(FILE_SEPARATOR) != -1) {
                parentPath = filePath.substring(0, filePath.lastIndexOf(FILE_SEPARATOR));
                folderName = filePath.substring(filePath.lastIndexOf(FILE_SEPARATOR) + 1, filePath.length());

                T parentFile = getFile(parentPath);
                if (parentFile == null) {
                    throw new StorageAccessException("Parent File '" + parentPath + "' does not exists. You must create it before adding childs.");
                }
                //LOGGER.info("Creating '" + filePath + "'");
                file = addFolderToStorage(parentFile, folderName);
            } else {
                file = addFolderToStorage(null, filePath);
            }
            FILE_PATH_IMAGE_MAP.put(filePath, file);
        } else {
            LOGGER.info("File '" + filePath + "' already exist. Skipped command");
        }
        return file!=null;
    }

    /**
     * Delete directory.
     *
     * @param filePath the file path
     * @return true, if successful
     * @throws StorageAccessException the storage access exception
     */
    public boolean deleteDirectory(String filePath) throws StorageAccessException {
        boolean status = false;
        if (filePath == null || filePath.trim().length() == 0 ) {
            throw new StorageAccessException("Invalid folder name: " + filePath);
        }
        T file = getFile(filePath);
        if (file == null) {
            throw new StorageAccessException("Folder does not exist.");
        }
        status = deleteFileFromStorage(file);
        FILE_PATH_IMAGE_MAP.remove(filePath);
        return status;
    }

    /**
     * Adds the files.
     *
     * @param parentFile the parent file
     * @param files the files
     * @return the list
     * @throws StorageAccessException the storage access exception
     */
    public List<T> addFiles(T parentFile, File[] files) throws StorageAccessException {
        if (files != null && files.length != 0) {
            List<File> asList = Arrays.asList(files);
            return addFiles(parentFile, asList);
        }
        return null;
    }

    /**
     * Adds the files.
     *
     * @param parentFile the parent file
     * @param files the files
     * @return the list
     * @throws StorageAccessException the storage access exception
     */
    public List<T> addFiles(T parentFile, List<File> files) throws StorageAccessException {
        List<T> statuses = null;
        if (files != null && !files.isEmpty()) {
            statuses = new ArrayList<T>();

            for (File file : files) {
                statuses.add(handleFile(parentFile, file, null, WriteMode.ADD_STRICT));
            }
        }
        return statuses;
    }

    /**
     * Adds the file.
     *
     * @param parentFile the parent file
     * @param file the file
     * @return the t
     * @throws StorageAccessException the storage access exception
     */
    public T addFile(T parentFile, File file) throws StorageAccessException {
        return handleFile(parentFile, file, null, WriteMode.ADD_STRICT);
    }

    /**
     * Adds the file.
     *
     * @param parentFile the parent file
     * @param file the file
     * @param name the name
     * @return the t
     * @throws StorageAccessException the storage access exception
     */
    public T addFile(T parentFile, File file, String name) throws StorageAccessException {
        return handleFile(parentFile, file, name, WriteMode.ADD_STRICT);
    }

    /**
     * Adds the or update file.
     *
     * @param parentFile the parent file
     * @param file the file
     * @return the t
     * @throws StorageAccessException the storage access exception
     */
    public T addOrUpdateFile(T parentFile, File file) throws StorageAccessException {
        return handleFile(parentFile, file, null, WriteMode.ADD_OR_UPDATE);
    }

    /**
     * Adds the or update file.
     *
     * @param parentFile the parent file
     * @param file the file
     * @param name the name
     * @return the t
     * @throws StorageAccessException the storage access exception
     */
    public T addOrUpdateFile(T parentFile, File file, String name) throws StorageAccessException {
        return handleFile(parentFile, file, name, WriteMode.ADD_OR_UPDATE);
    }

    /**
     * Update file.
     *
     * @param parentFile the parent file
     * @param file the file
     * @return the t
     * @throws StorageAccessException the storage access exception
     */
    public T updateFile(T parentFile, File file) throws StorageAccessException {
        return handleFile(parentFile, file, null, WriteMode.UPDATE_STRICT);
    }

    /**
     * Delete file.
     *
     * @param parentFile the parent file
     * @param fileName the file name
     * @return true, if successful
     * @throws StorageAccessException the storage access exception
     */
    public boolean deleteFile(T parentFile, String fileName) throws StorageAccessException {
        handleFile(parentFile, null, fileName, WriteMode.DELETE);
        return true;
    }

    /**
     * Move file.
     *
     * @param from the from
     * @param to the to
     * @param fileName the file name
     * @return true, if successful
     * @throws StorageAccessException the storage access exception
     */
    public boolean moveFile(T from, T to, String fileName) throws StorageAccessException {
        boolean status = false;
        T srcFile = getFile(from, fileName);
        if (srcFile == null) {
            throw new StorageAccessException("Cannot move. Could not find the file " + fileName);
        }
        String path = getStoragePath(srcFile);
        T movedFile = moveFileInStorage(from, to, srcFile);
        if (movedFile != null) {
            status = true;
            FILE_PATH_IMAGE_MAP.put(path, movedFile);
        }
        return status;
    }

    /**
     * Handle file.
     *
     * @param parentFile the parent file
     * @param file the file
     * @param alias the alias
     * @param writeMode the write mode
     * @return true, if successful
     * @throws StorageAccessException the storage access exception
     */
    public T handleFile(T parentFile, File file, String alias, WriteMode writeMode) throws StorageAccessException {
        T t = null;
        String fileName = null;

        if ((alias == null || alias.trim().length() == 0) && (file == null || !file.exists() || !file.isFile())) {
            throw new StorageAccessException(writeMode + "> Cannot handle file. File name is missing.");
        }
        fileName = (alias != null && alias.trim().length() != 0) ? alias : file.getName();

        T existingFile = getFile(parentFile, fileName);
        if (writeMode == WriteMode.ADD_STRICT) {
            // File must not exist before adding. Cannot overwrite
            if (existingFile != null) {
                throw new StorageAccessException("File '" + fileName + "' already exists.");
            }
            t = addFileToStorage(parentFile, file, fileName);
            FILE_PATH_IMAGE_MAP.put(getStoragePath(t), t);
        } else if (writeMode == WriteMode.ADD_OR_UPDATE) {
            // If the file already exists then delete the file and add new file.
            if (existingFile != null) {
                deleteFile(existingFile);
            }
            t = addFileToStorage(parentFile, file, fileName);
            FILE_PATH_IMAGE_MAP.put(getStoragePath(t), t);
        } else if (writeMode == WriteMode.UPDATE_STRICT) {
            if (existingFile == null) {
                throw new StorageAccessException("File '" + fileName + "' does not exists.");
            } else {
                deleteFile(existingFile);
            }
            t = addFileToStorage(parentFile, file, fileName);
            FILE_PATH_IMAGE_MAP.put(getStoragePath(t), t);
        } else if (writeMode == WriteMode.DELETE_STRICT) {
            if (existingFile == null) {
                throw new StorageAccessException("File '" + fileName + "' does not exists.");
            }
            deleteFile(existingFile);
        }  else if (writeMode == WriteMode.DELETE) {
            deleteFile(existingFile);
        }
        return t;
    }

    /**
     * Delete file.
     *
     * @param file the file
     * @return true, if successful
     * @throws StorageAccessException the storage access exception
     */
    private boolean deleteFile(T file) throws StorageAccessException {
        boolean deleted = false;
        if (file != null) {
            String path = getStoragePath(file);
            deleted = deleteFileFromStorage(file);
            if (!deleted) {
                throw new StorageAccessException("Unable to delete: " + path);
            }
            FILE_PATH_IMAGE_MAP.remove(path);
        }
        return deleted;
    }

    /**
     * The Enum RecordStatus.
     */
    public enum RecordStatus {

        /** The verified. */
        VERIFIED, 

        /** The unverified. */
        UNVERIFIED;

    }

}
