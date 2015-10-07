package com.myschool.infra.filesystem.filefilter;

import java.io.File;
import java.io.FileFilter;
import java.util.List;

import org.springframework.stereotype.Component;

import com.myschool.infra.filesystem.constants.FileExtension;
import com.myschool.infra.filesystem.util.FileUtil;
import com.myschool.infra.image.constants.ImageSize;

/**
 * The Class ImageResizingFileFilter.
 */
@Component
public class ImageResizingFileFilter implements FileFilter {

	/** The Constant IMAGE_SCALE_DIRECTORIES. */
	private static final List<ImageSize> IMAGE_SCALE_DIRECTORIES = ImageSize.getNonOriginal();

    /* (non-Javadoc)
     * @see java.io.FileFilter#accept(java.io.File)
     */
    @Override
    public boolean accept(File file) {
        // If it is a file then list the file
    	String fileName = file.getName();
        if (file.isFile()) {
            String extension = FileUtil.getExtension(fileName);
            // Filter all files other than images
            if (FileExtension.isImage(extension)) {
            	return true;
            }
            return false;
        } else {
        	// If it is not an original image file then do not list the file.
        	for (ImageSize imageResizeType : IMAGE_SCALE_DIRECTORIES) {
        		if (file.isDirectory() && fileName.equalsIgnoreCase(imageResizeType.toString())) {
        			return false;
        		}
        	}
        }
        return false;
    }

}
