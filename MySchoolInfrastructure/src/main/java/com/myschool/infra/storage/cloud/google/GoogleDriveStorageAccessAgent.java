package com.myschool.infra.storage.cloud.google;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLConnection;
import java.security.GeneralSecurityException;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.google.api.services.drive.model.Permission;
import com.myschool.infra.image.constants.ImageSize;
import com.myschool.infra.storage.StorageAccessAgent;
import com.myschool.infra.storage.exception.StorageAccessException;
import com.myschool.storage.dto.StorageItem;
import com.myschool.storage.dto.StorageProviderDto;

/**
 * The Class GoogleDriveStorageAccessAgent.
 */
@Component
public class GoogleDriveStorageAccessAgent extends StorageAccessAgent<File> {

    /** The Constant LOCATION. */
    private static final String LOCATION = "LOCATION";

    /** The Constant FILE_PATH. */
    private static final String FILE_PATH = "FILE_PATH";

    /** The file fields. */
    private static String FILE_FIELDS;

    /** The list fields. */
    private static String LIST_FIELDS;

    /** The mime type folder. */
    private static String MIME_TYPE_FOLDER;

    /** The service. */
    private Drive service;

    /**
     * Initialize storage.
     *
     * @throws StorageAccessException the storage access exception
     */
    /* (non-Javadoc)
     * @see com.myschool.infra.storage.StorageAccessAgent#initializeStorage()
     */
    protected void initializeStorage() throws StorageAccessException {
        try {
            StorageProviderDto storageProvider = storageConfig.getStorageProvider();
            Map<String, String> params = storageProvider.getParams();

            String oauthClientIds = params.get("CLIENT_ID_FILE");
            String datastore = params.get("DATA_STORE_DIR");
            String datastoreFileName = params.get("DATA_STORE_FILE");

            MIME_TYPE_FOLDER = params.get("mime.folder");
            FILE_FIELDS = params.get("file.fields");
            LIST_FIELDS = params.get("list.fields");

            java.io.File oauthClientIdFile = new java.io.File(oauthClientIds);
            java.io.File dataStoreFolder = new java.io.File(datastore);
            java.io.File dataStoreFile = new java.io.File(dataStoreFolder, datastoreFileName);

            if (!oauthClientIdFile.exists()) {
                throw new StorageAccessException("Missing Client ID File. Create WebClient ID for OAuth from Google Developer Console and download the credentials file.");
            }
            if (!dataStoreFile.exists()) {
                throw new StorageAccessException("Missing Stored Credentials. Complete OAuth and provide StoredCredentials");
            }

            HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
            FileDataStoreFactory dataStoreFactory = new FileDataStoreFactory(dataStoreFolder);

            InputStream in = new FileInputStream(oauthClientIdFile);
            JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
            GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(jsonFactory, new InputStreamReader(in));

            // Build flow and trigger user authorization request.
            AuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                    httpTransport, jsonFactory, clientSecrets,
                    Arrays.asList(DriveScopes.DRIVE_FILE))
                    .setDataStoreFactory(dataStoreFactory)
                    .setAccessType("offline")
                    .build();

            Credential credential = new AuthorizationCodeInstalledApp(
                flow, new LocalServerReceiver()).authorize("user");

            // Build drive service
            service = new Drive.Builder(httpTransport, jsonFactory, credential)
                    .setApplicationName(storageProvider.getAppname())
                    .build();
        } catch (GeneralSecurityException generalSecurityException) {
            throw new StorageAccessException(generalSecurityException.getMessage(), generalSecurityException);
        } catch (IOException ioException) {
            throw new StorageAccessException(ioException.getMessage(), ioException);
        }
    }

    /**
     * Gets the file seperator.
     *
     * @return the file seperator
     */
    /* (non-Javadoc)
     * @see com.myschool.infra.storage.StorageAccessAgent#getFileSeperator()
     */
    @Override
    protected String getFileSeperator() {
        return "/";
    }

    /**
     * Gets the storage id.
     *
     * @param file the file
     * @return the storage id
     */
    /* (non-Javadoc)
     * @see com.myschool.infra.storage.StorageAccessAgent#getStorageId(java.lang.Object)
     */
    @Override
    protected String getStorageId(File file) {
        if (file != null) {
            return file.getId();
        }
        return null;
    }

    /**
     * Gets the storage name.
     *
     * @param file the file
     * @return the storage name
     */
    /* (non-Javadoc)
     * @see com.myschool.infra.storage.StorageAccessAgent#getStorageName(java.lang.Object)
     */
    @Override
    protected String getStorageName(File file) {
        if (file != null) {
            return file.getName();
        }
        return null;
    }

    /**
     * Gets the storage location.
     *
     * @param file the file
     * @return the storage location
     */
    /* (non-Javadoc)
     * @see com.myschool.infra.storage.StorageAccessAgent#getStorageLocation(java.lang.Object)
     */
    @Override
    protected String getStorageLocation(File file) {
        if (file != null) {
            return (String) file.get(LOCATION);
        }
        return null;
    }

    /**
     * Gets the storage path.
     *
     * @param file the file
     * @return the storage path
     */
    /* (non-Javadoc)
     * @see com.myschool.infra.storage.StorageAccessAgent#getStoragePath(java.lang.Object)
     */
    @Override
    protected String getStoragePath(File file) {
        if (file != null) {
            return (String) file.get(FILE_PATH);
        }
        return null;
    }

    /**
     * Sets the storage location.
     *
     * @param file the file
     * @param location the location
     */
    /* (non-Javadoc)
     * @see com.myschool.infra.storage.StorageAccessAgent#setStorageLocation(java.lang.Object, java.lang.String)
     */
    @Override
    protected void setStorageLocation(File file, String location) {
        if (file != null) {
            file.set(LOCATION, location);
        }
    }

    /**
     * Sets the storage path.
     *
     * @param file the file
     * @param filePath the file path
     */
    /* (non-Javadoc)
     * @see com.myschool.infra.storage.StorageAccessAgent#setStoragePath(java.lang.Object, java.lang.String)
     */
    @Override
    protected void setStoragePath(File file, String filePath) {
        if (file != null) {
            file.set(FILE_PATH, filePath);
        }
    }

    /**
     * Checks if is folder.
     *
     * @param file the file
     * @return true, if is folder
     */
    /* (non-Javadoc)
     * @see com.myschool.infra.storage.StorageAccessAgent#isFolder(java.lang.Object)
     */
    @Override
    protected boolean isFolder(File file) {
        return (file == null)? null: MIME_TYPE_FOLDER.equals(file.getMimeType());
    }

    /**
     * Gets the all from storage.
     *
     * @param fileId the file id
     * @param onlyFiles the only files
     * @return the all from storage
     * @throws StorageAccessException the storage access exception
     */
    /* (non-Javadoc)
     * @see com.myschool.infra.storage.StorageAccessAgent#getAllFromStorage(java.lang.String, java.lang.Boolean)
     */
    @Override
    protected List<File> getAllFromStorage(String fileId, Boolean onlyFiles) throws StorageAccessException {
        List<File> files = null;
        // Print the names and IDs for up to 10 files.
        FileList result;
        try {
            StringBuffer query = new StringBuffer();
            query.append("trashed = false ");

            if (fileId == null) {
                query.append("and 'root' in parents ");
            } else {
                query.append("and '").append(fileId).append("' in parents ");
            }

            if (onlyFiles == Boolean.TRUE) {
                query.append("and mimeType != '").append(MIME_TYPE_FOLDER).append("' ");
            } else if (onlyFiles == Boolean.TRUE) {
                query.append("and mimeType = '").append(MIME_TYPE_FOLDER).append("' ");
            }

            result = service.files()
                    .list()
                    .setQ(query.toString())
                    .setSpaces("drive")
                    .setFields(LIST_FIELDS)
                    .execute();
            files = result.getFiles();
        } catch (IOException ioException) {
            throw new StorageAccessException(ioException.getMessage(), ioException); 
        }
        return files;
    }

    /**
     * Adds the folder to storage.
     *
     * @param parentFolder the parent folder
     * @param folderName the folder name
     * @return the file
     * @throws StorageAccessException the storage access exception
     */
    /* (non-Javadoc)
     * @see com.myschool.infra.storage.StorageAccessAgent#addFolderToStorage(java.lang.Object, java.lang.String)
     */
    @Override
    protected File addFolderToStorage(File parentFolder, String folderName) throws StorageAccessException {
        File driveFile = null;
        String location = null;
        String filePath = null;

        try {
            File folder = new File();
            folder.setMimeType(MIME_TYPE_FOLDER);
            folder.setName(folderName);

            if (parentFolder != null) {
                folder.setParents(Arrays.asList(parentFolder.getId()));
            }
            driveFile = service.files().create(folder).execute();
            if (parentFolder == null) {
                filePath = folderName;
            } else {
                location = getStoragePath(parentFolder);
                filePath = location + FILE_SEPARATOR + folderName;
            }
            setStorageLocation(driveFile, location);
            setStoragePath(driveFile, filePath);
        } catch (IOException ioException) {
            throw new StorageAccessException(ioException.getMessage(), ioException); 
        }
        return driveFile;
    }

    /**
     * Adds the file to storage.
     *
     * @param parentFolder the parent folder
     * @param file the file
     * @param fileName the file name
     * @return the file
     * @throws StorageAccessException the storage access exception
     */
    /* (non-Javadoc)
     * @see com.myschool.infra.storage.StorageAccessAgent#addFileToStorage(java.lang.Object, java.io.File, java.lang.String)
     */
    @Override
    protected File addFileToStorage(File parentFolder, java.io.File file,
            String fileName) throws StorageAccessException {

        File driveFile = null;
        FileContent mediaContent = null;

        try {
            String contentType = URLConnection.guessContentTypeFromName(fileName);

            File body = new File();
            body.setName(fileName);
            body.setMimeType(contentType);
            mediaContent = new FileContent(contentType, file);

            if (parentFolder != null) {
                body.setParents(Arrays.asList(parentFolder.getId()));
            }

            driveFile = service.files().create(body, mediaContent).execute();

            if (driveFile != null) {
                Permission newPermission = new Permission();
                newPermission.setType("anyone");
                newPermission.setRole("reader");
                //newPermission.setValue("");
                //newPermission.setWithLink(true);
                service.permissions().create(driveFile.getId(), newPermission).execute();

                String parentPath = getStoragePath(parentFolder);
                if (parentPath == null) {
                    setStoragePath(driveFile, fileName);
                } else {
                    setStoragePath(driveFile, parentPath + FILE_SEPARATOR + fileName);
                }
                setStorageLocation(driveFile, parentPath);
            }
        } catch (IOException ioException) {
            throw new StorageAccessException(ioException.getMessage(), ioException); 
        }
        return driveFile;
    }

    /**
     * Delete file from storage.
     *
     * @param file the file
     * @return true, if successful
     * @throws StorageAccessException the storage access exception
     */
    /* (non-Javadoc)
     * @see com.myschool.infra.storage.StorageAccessAgent#deleteFileFromStorage(java.lang.Object)
     */
    @Override
    protected boolean deleteFileFromStorage(File file)
            throws StorageAccessException {
        try {
            service.files().delete(file.getId()).execute();
        } catch (IOException ioException) {
            throw new StorageAccessException(ioException.getMessage(), ioException); 
        }
        return true;
    }

    /**
     * Move file in storage.
     *
     * @param from the from
     * @param to the to
     * @param file the file
     * @return the file
     * @throws StorageAccessException the storage access exception
     */
    /* (non-Javadoc)
     * @see com.myschool.infra.storage.StorageAccessAgent#moveFileInStorage(java.lang.Object, java.lang.Object, java.lang.Object)
     */
    @Override
    protected File moveFileInStorage(File from, File to, File file)
            throws StorageAccessException {
        try {
            return service.files().update(file.getId(), null)
                    .setRemoveParents(from.getId())
                    .setAddParents(to.getId())
                    .setFields(FILE_FIELDS)
                    .execute();
        } catch (IOException ioException) {
            throw new StorageAccessException(ioException.getMessage(), ioException); 
        }
    }

    /**
     * Creates the storage item.
     *
     * @param file the file
     * @return the storage item
     * @throws StorageAccessException the storage access exception
     */
    /* (non-Javadoc)
     * @see com.myschool.infra.storage.StorageAccessAgent#createStorageItem(java.lang.Object)
     */
    @Override
    protected StorageItem createStorageItem(File file) throws StorageAccessException {
        StorageItem storageItem = null;
        if (file != null) {
            storageItem = new StorageItem();
            StorageProviderDto storageProvider = storageConfig.getStorageProvider();

            storageItem.setId(file.getId());
            storageItem.setName(file.getName());
            storageItem.setSize(get(file.getSize()));
            storageItem.setCreatedTime(get(file.getCreatedTime()));
            storageItem.setModifiedTime(get(file.getModifiedTime()));

            if (!isFolder(file)) {
                String storageLocation = getStorageLocation(file);
                String storageName = getStorageName(file);
                storageItem.setDirectLink(MessageFormat.format(storageProvider.getDirectLink(), file.getId()));
                
                String passportPath = storageLocation + FILE_SEPARATOR + ImageSize.PASSPORT + FILE_SEPARATOR + storageName;
                File passportFile = getFile(passportPath);
                if (passportFile != null) {
                    storageItem.setPassportLink(MessageFormat.format(storageProvider.getPassportLink(), passportFile.getId()));
                }
                String thumbnailPath = storageLocation + FILE_SEPARATOR + ImageSize.THUMBNAIL + FILE_SEPARATOR + storageName;
                File thumbnailFile = getFile(thumbnailPath);
                if (thumbnailFile != null) {
                    storageItem.setThumbnailLink(MessageFormat.format(storageProvider.getThumbnailLink(), thumbnailFile.getId()));
                }
            }
        }
        return storageItem;
    }

    /**
     * Gets the.
     *
     * @param dateTime the date time
     * @return the long
     */
    private long get(DateTime dateTime) {
        return (dateTime == null) ? 0 : dateTime.getValue();
    }

    /**
     * Gets the.
     *
     * @param value the value
     * @return the long
     */
    private long get(Long value) {
        return (value == null) ? 0 : value;
    }

}
