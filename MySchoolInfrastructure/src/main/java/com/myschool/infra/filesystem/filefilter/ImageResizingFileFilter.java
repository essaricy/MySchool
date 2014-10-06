package com.myschool.infra.filesystem.filefilter;

import java.io.File;
import java.io.FileFilter;
import java.util.List;

import org.springframework.stereotype.Component;

import com.myschool.infra.image.constants.ImageSize;

/**
 * The Class ImageResizingFileFilter.
 */
@Component
public class ImageResizingFileFilter implements FileFilter {

    /* (non-Javadoc)
     * @see java.io.FileFilter#accept(java.io.File)
     */
    @Override
    public boolean accept(File file) {
        // If it is a file then list the file
        if (file.isFile()) {
            return true;
        }
        String fileName = file.getName();
        List<ImageSize> nonOriginal = ImageSize.getNonOriginal();
        // If it is not an original image file then do not list the file.
        for (ImageSize imageResizeType : nonOriginal) {
            if (file.isDirectory() && fileName.equalsIgnoreCase(imageResizeType.toString())) {
                return false;
            }
        }
        return true;
    }

}
