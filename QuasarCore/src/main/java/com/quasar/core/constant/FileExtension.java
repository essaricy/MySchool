package com.quasar.core.constant;

/**
 * The Enum FileExtension.
 */
public enum FileExtension {

    /** The CSV. */
    CSV,
    
    /** The PDF. */
    PDF,
    
    /** The XLS. */
    XLS,
    
    /** The XSL. */
    XSL,
    
    /** The XML. */
    XML,
    
    /** The HTML. */
    HTML,
    
    /** The PROPERTIES. */
    PROPERTIES,
    
    /** The JPG. */
    JPG,

    /** The JPEG. */
    JPEG,

    /** The PNG. */
    PNG,

    BMP;

    /**
     * Gets the file extension.
     *
     * @return the file extension
     */
    public String getFileExtension() {
        return toString().toLowerCase();
    }

    /**
     * Checks if is image.
     * 
     * @param extension the extension
     * @return true, if is image
     */
    public static boolean isImage(String extension) {
        if (extension != null && (
        		extension.equalsIgnoreCase(JPG.toString())
        		|| extension.equalsIgnoreCase(JPEG.toString())
        		|| extension.equalsIgnoreCase(PNG.toString())
        		|| extension.equalsIgnoreCase(BMP.toString()))) {
            return true;
        }
        return false;
    }

    /**
     * Checks if is image.
     * 
     * @param fileExtension the file extension
     * @return true, if is image
     */
    public static boolean isImage(FileExtension fileExtension) {
        if (fileExtension != null) {
            return isImage(fileExtension.getFileExtension());
        }
        return false;
    }

    /**
     * Gets the.
     * 
     * @param extension the extension
     * @return the file extension
     */
    public static FileExtension get(String extension) {
        if (extension != null) {
            for (FileExtension fileExtension : values()) {
                if (extension.equalsIgnoreCase(fileExtension.toString())) {
                    return fileExtension;
                }
            }
        }
        return null;
    }

}
