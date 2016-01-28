package com.myschool.integration.common.util;

import java.io.File;

import org.apache.camel.Message;

import com.myschool.integration.common.constant.IntegrationConstant;
import com.myschool.integration.common.exception.IntegrationException;

/**
 * The Class IntegrationUtil.
 */
public class IntegrationUtil {

    /**
     * Gets the message directory.
     * 
     * @param message the message
     * @return the message directory
     * @throws Exception the exception
     */
    public static File getMessageDirectory(Message message) throws Exception {
        String camelFileParentValue = (String) message.getHeader(IntegrationConstant.CAMEL_FILE_PARENT);
        if (camelFileParentValue == null) {
            throw new Exception("Missing CamelFileParent in Message Headers");
        }
        return new File(camelFileParentValue);
    }

    /**
     * Gets the file.
     * 
     * @param message the message
     * @param fileName the file name
     * @return the file in current directory
     * @throws Exception the exception
     */
    public static File getFile(Message message, String fileName) throws Exception {
        return new File(getMessageDirectory(message), fileName);
    }

    /**
     * Gets the mandatory string header.
     * 
     * @param inMessage the in message
     * @param headerName the header name
     * @return the mandatory string header
     * @throws Exception the exception
     */
    public static String getMandatoryStringHeader(Message inMessage, String headerName) throws Exception {
        String headerValue = (String) inMessage.getHeader(headerName);
        if (headerValue == null || headerValue.trim().length() == 0) {
            throw new Exception("Missing key " + headerName + " in header");
        }
        return headerValue;
    }

    /**
     * Gets the mandoatory file header.
     * 
     * @param inMessage the in message
     * @param headerName the header name
     * @return the mandoatory file header
     * @throws Exception the exception
     */
    public static File getMandoatoryFileHeader(Message inMessage, String headerName) throws IntegrationException {
        File file = (File) inMessage.getHeader(headerName);
        if (file == null) {
            throw new IntegrationException("Missing key " + headerName + " in header");
        }
        if (!file.exists() || !file.canRead()) {
            throw new IntegrationException("File " + file + " specified in header is not a file or is not accessible");
        }
        return file;
    }

    /**
     * Gets the optional file header.
     * 
     * @param inMessage the in message
     * @param headerName the header name
     * @return the optional file header
     * @throws Exception the exception
     */
    public static File getOptionalFileHeader(Message inMessage, String headerName) throws Exception {
        File file = (File) inMessage.getHeader(headerName);
        if (file == null) {
            throw new Exception("Missing key " + headerName + " in header");
        }
        if (!file.exists()) {
            file.mkdir();
        }
        return file;
    }

}
