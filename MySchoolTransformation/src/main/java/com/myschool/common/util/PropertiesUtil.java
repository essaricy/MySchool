package com.myschool.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import com.myschool.common.exception.FileSystemException;

/**
 * The Class PropertiesUtil.
 */
public class PropertiesUtil {

    /** The Constant DYNAMIC_PROPERTY_SUFFIX. */
    private static final java.lang.String DYNAMIC_PROPERTY_SUFFIX = "}";
    
    /** The Constant DYNAMIC_PROPERTY_PREFIX. */
    private static final java.lang.String DYNAMIC_PROPERTY_PREFIX = "${";

    /**
     * Load properties.
     * 
     * @param fileToLoad the file to load
     * @return the properties
     * @throws FileSystemException the file system exception
     */
    public static Properties loadProperties(File fileToLoad) throws FileSystemException {
        FileInputStream fileInputStream = null;
        Properties properties;
        try {
            properties = new Properties();
            // Load the file. 
            fileInputStream = new FileInputStream(fileToLoad);
            properties.load(fileInputStream);
        } catch (IOException ioException) {
            throw new FileSystemException(ioException.getMessage(), ioException);
        } finally {
            ResourceUtil.releaseResource(fileInputStream);
        }
        return properties;
    }

    /**
     * Load properties.
     * 
     * @param inputStream the input stream
     * @param closeStream the close stream
     * @return the properties
     * @throws FileSystemException the file system exception
     */
    public static Properties loadProperties(InputStream inputStream,
            boolean closeStream) throws FileSystemException {
        Properties properties;
        try {
            properties = new Properties();
            properties.load(inputStream);
        } catch (IOException ioException) {
            throw new FileSystemException(ioException.getMessage(), ioException);
        } finally {
            if (closeStream){
                ResourceUtil.releaseResource(inputStream);
            }
        }
        return properties;
    }

    /**
     * Load nested properties.
     * 
     * @param inputStream the input stream
     * @return the properties
     * @throws FileSystemException the file system exception
     */
    public static Properties loadNestedProperties(InputStream inputStream) throws FileSystemException {
        Properties properties = PropertiesUtil.loadProperties(inputStream, true);
        Set<Object> keySet = properties.keySet();
        for (Iterator<Object> iterator = keySet.iterator(); iterator.hasNext();) {
            String key = (String) iterator.next();
            String value = properties.getProperty(key);
            while (value.indexOf(DYNAMIC_PROPERTY_PREFIX) != -1) {
                value = resolveProperty(properties, value);
            }
            properties.put(key, value);
        }
        return properties;
    }

    /**
     * Load nested properties.
     * 
     * @param fileToLoad the file to load
     * @return the properties
     * @throws FileSystemException the file system exception
     */
    public static Properties loadNestedProperties(File fileToLoad) throws FileSystemException {
        try {
            return loadNestedProperties(new FileInputStream(fileToLoad));
        } catch (FileNotFoundException fileNotFoundException) {
            throw new FileSystemException(fileNotFoundException.getMessage(), fileNotFoundException);
        }
    }

    /**
     * Resolve property.
     *
     * @param properties the properties
     * @param value the value
     * @return the string
     */
    public static String resolveProperty(Properties properties, String value) {
        int length = 0;
        String keyName = null;
        String keyValue = null;
        int dunamicPropertyIndex = value.indexOf(DYNAMIC_PROPERTY_PREFIX);
        if (dunamicPropertyIndex != -1) {
            length = value.length();
            keyName = value.substring(dunamicPropertyIndex + DYNAMIC_PROPERTY_PREFIX.length(), value.indexOf(DYNAMIC_PROPERTY_SUFFIX));
            keyValue = properties.getProperty(keyName);
            if (dunamicPropertyIndex == 0) {
                value = keyValue + value.substring(value.indexOf(DYNAMIC_PROPERTY_SUFFIX) + 1, length); 
            } else {
                value = value.substring(0, dunamicPropertyIndex) + keyValue + value.substring(value.indexOf(DYNAMIC_PROPERTY_SUFFIX) + 1, length);
            }
        }
        return value;
    }
}
