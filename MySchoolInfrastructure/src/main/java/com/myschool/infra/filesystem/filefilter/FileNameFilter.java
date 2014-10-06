package com.myschool.infra.filesystem.filefilter;

import java.io.File;
import java.io.FilenameFilter;

/**
 * The Class FileNameFilter.
 */
public class FileNameFilter implements FilenameFilter {

    /** The Constant INSTANCE. */
    private static final FileNameFilter INSTANCE = new FileNameFilter();

    /** The file name to filter. */
    private String fileNameToFilter;

    /**
     * Instantiates a new file name filter.
     */
    private FileNameFilter() {
    }

    /**
     * Gets the single instance of FileNameFilter.
     *
     * @return single instance of FileNameFilter
     */
    public static FileNameFilter getInstance() {
        return INSTANCE;
    }

    /**
     * Gets the file name to filter.
     *
     * @return the file name to filter
     */
    public String getFileNameToFilter() {
        return fileNameToFilter;
    }

    /**
     * Sets the file name to filter.
     *
     * @param fileNameToFilter the new file name to filter
     */
    public void setFileNameToFilter(String fileNameToFilter) {
        this.fileNameToFilter = fileNameToFilter;
    }

    /* (non-Javadoc)
     * @see java.io.FilenameFilter#accept(java.io.File, java.lang.String)
     */
    @Override
    public boolean accept(File dir, String fileName) {
        if (fileNameToFilter != null) {
            if (fileName.startsWith(fileNameToFilter)) {
                return true;
            }
        }
        return false;
    }

}
