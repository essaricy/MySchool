package com.myschool.infra.filesystem.filefilter;

import java.io.File;
import java.io.FileFilter;

import org.springframework.stereotype.Component;

/**
 * The Class GalleryPinFileFilter.
 */
@Component
public class GalleryPinFileFilter implements FileFilter {

	/** The Constant PIN_FILE. */
	private static final String PIN_FILE = "pin";

    /* (non-Javadoc)
     * @see java.io.FileFilter#accept(java.io.File)
     */
    @Override
    public boolean accept(File file) {
        // If it is a file then list the file
        if (file.isFile() && file.getName().equals(PIN_FILE)) {
            return true;
        }
        return false;
    }

    /**
     * Gets the pin.
     *
     * @param parent the parent
     * @return the pin
     */
    public File getPin(File parent) {
    	if (parent != null && parent.isDirectory()) {
    		return new File(parent, PIN_FILE);
    	}
		return parent;
    }

}
