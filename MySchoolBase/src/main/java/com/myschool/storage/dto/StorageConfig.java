package com.myschool.storage.dto;

import java.util.List;

import com.myschool.filesystem.dto.DirectoryDto;

/**
 * The Class StorageConfig.
 *
 * @author Srikanth
 */
public class StorageConfig {

    /** The storage provider. */
    private StorageProviderDto storageProvider;

    /** The directories. */
    private List<DirectoryDto> directories;

    /**
     * Gets the storage provider.
     *
     * @return the storageProvider
     */
    public StorageProviderDto getStorageProvider() {
        return storageProvider;
    }

    /**
     * Sets the storage provider.
     *
     * @param storageProvider the storageProvider to set
     */
    public void setStorageProvider(StorageProviderDto storageProvider) {
        this.storageProvider = storageProvider;
    }

    /**
     * Gets the directories.
     *
     * @return the directories
     */
    public List<DirectoryDto> getDirectories() {
        return directories;
    }

    /**
     * Sets the directories.
     *
     * @param directories the directories to set
     */
    public void setDirectories(List<DirectoryDto> directories) {
        this.directories = directories;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        final int maxLen = 10;
        StringBuilder builder = new StringBuilder();
        builder.append("StorageConfig [storageProvider=")
                .append(storageProvider).append(", directories=")
                .append(directories != null ? directories.subList(0,
                        Math.min(directories.size(), maxLen)) : null)
                .append("]");
        return builder.toString();
    }

}
