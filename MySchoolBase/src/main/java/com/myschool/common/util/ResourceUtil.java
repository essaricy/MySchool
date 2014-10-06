package com.myschool.common.util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;

import com.myschool.common.exception.FileSystemException;

/**
 * The Class ResourceUtil.
 */
public class ResourceUtil {

    /**
     * Release resource.
     *
     * @param reader the reader
     * @throws FileSystemException the file system exception
     */
    public static void releaseResource(Reader reader) throws FileSystemException {
        try {
            if (reader != null) {
                reader.close();
            }
        } catch (IOException ioException) {
            throw new FileSystemException(ioException.getMessage(), ioException);
        }
    }

    /**
     * Release resource.
     *
     * @param inputStream the input stream
     * @throws FileSystemException the file system exception
     */
    public static void releaseResource(InputStream inputStream) throws FileSystemException {
        try {
            if (inputStream != null) {
                inputStream.close();
            }
        } catch (IOException ioException) {
            throw new FileSystemException(ioException.getMessage(), ioException);
        }
    }

    /**
     * Gets the resource as stream.
     *
     * @param fileName the file name
     * @return the resource as stream
     */
    public InputStream getResourceAsStream(String fileName) {
        return getClass().getClassLoader().getResourceAsStream(fileName);
    }

    /**
     * Release resource.
     *
     * @param outputStream the output stream
     * @throws FileSystemException the file system exception
     */
    public static void releaseResource(OutputStream outputStream) throws FileSystemException {
        try {
            if (outputStream != null) {
                outputStream.close();
            }
        } catch (IOException ioException) {
            throw new FileSystemException(ioException.getMessage(), ioException);
        }
    }

    /**
     * Release resource.
     *
     * @param fileWriter the file writer
     * @throws FileSystemException the file system exception
     */
    public static void releaseResource(FileWriter fileWriter) throws FileSystemException {
        try {
            if (fileWriter != null) {
                fileWriter.close();
            }
        } catch (IOException ioException) {
            throw new FileSystemException(ioException.getMessage(), ioException);
        }
    }

}
