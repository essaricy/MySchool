package com.myschool.image.constant;

import java.util.ArrayList;
import java.util.List;

/**
 * The Enum ImageSize.
 */
public enum ImageSize {

    /** The THUMBNAIL. */
    THUMBNAIL,

    /** The PASSPORT. */
    PASSPORT,

    /** The ORIGINAL. */
    ORIGINAL;

    /**
     * Gets the image type.
     * 
     * @param description the description
     * @return the image type
     */
    public static ImageSize getImageType(String description) {
        if (description != null){
            for (ImageSize imageType : values()) {
                if (description.equalsIgnoreCase(imageType.toString())) {
                    return imageType;
                }
            }
        }
        return null;
    }

    /**
     * Gets the non original.
     * 
     * @return the non original
     */
    public static List<ImageSize> getNonOriginal() {
        return exclude(ORIGINAL);
    }

    /**
     * Exclude.
     * 
     * @param toExlude the to exlude
     * @return the list
     */
    public static List<ImageSize> exclude(ImageSize toExlude) {
        List<ImageSize> excludedImageTypes = null;
        if (toExlude != null) {
            excludedImageTypes = new ArrayList<ImageSize>();
            for (ImageSize imageType : values()) {
                if (toExlude != imageType) {
                    excludedImageTypes.add(imageType);
                }
            }
        }
        return excludedImageTypes;
    }

    /**
     * Exclude.
     * 
     * @param toExlude the to exlude
     * @return the list
     */
    public static List<ImageSize> exclude(List<ImageSize> toExlude) {
        List<ImageSize> excludedImageTypes = null;
        if (toExlude != null && !toExlude.isEmpty()) {
            excludedImageTypes = new ArrayList<ImageSize>();
            for (ImageSize imageType : values()) {
                if (!toExlude.contains(imageType)) {
                    excludedImageTypes.add(imageType);
                }
            }
        }
        return excludedImageTypes;
    }

}
