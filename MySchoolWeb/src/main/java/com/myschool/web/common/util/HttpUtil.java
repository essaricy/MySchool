package com.myschool.web.common.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import com.myschool.common.exception.FileSystemException;
import com.myschool.common.util.ResourceUtil;

/**
 * The Class HttpUtil.
 */
public class HttpUtil {

    /**
     * Write to response.
     *
     * @param httpServletResponse the http servlet response
     * @param content the content
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static void writeToResponse(HttpServletResponse httpServletResponse, String content) throws IOException {
        ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
        servletOutputStream.write(content.getBytes());
        servletOutputStream.close();
    }

    /**
     * Adds the attachment.
     * 
     * @param response the response
     * @param fileToAttach the file to attach
     * @throws FileSystemException the file system exception
     */
    public static void addAttachment(HttpServletResponse response,
            File fileToAttach) throws FileSystemException {
        addAttachment(response, fileToAttach, null);
    }

    /**
     * Adds the attachment.
     * 
     * @param response the response
     * @param fileToAttach the file to attach
     * @param mimeType the mime type
     * @throws FileSystemException the file system exception
     */
    public static void addAttachment(HttpServletResponse response,
            File fileToAttach, String mimeType) throws FileSystemException {
        addAttachment(response, fileToAttach, mimeType, true);
    }

    /**
     * Write to response.
     * 
     * @param response the response
     * @param file the file
     * @throws FileSystemException the file system exception
     */
    public static void writeToResponse(HttpServletResponse response, File file)
            throws FileSystemException {
        writeToResponse(response, file, null);
    }

    /**
     * Write to response.
     * 
     * @param response the response
     * @param file the file
     * @param mimeType the mime type
     * @throws FileSystemException the file system exception
     */
    public static void writeToResponse(HttpServletResponse response, File file, String mimeType)
            throws FileSystemException {
        FileInputStream fileInputStream = null;
        ServletOutputStream outputStream = null;

        try {
            if (file != null && file.exists()) {
                byte[] bytes = new byte[1024];
                response.setContentLength((int) file.length());
                if (mimeType != null) {
                    response.setContentType(mimeType);
                }
                fileInputStream = new FileInputStream(file);
                outputStream = response.getOutputStream();
                while (fileInputStream.available() > 0) {
                    fileInputStream.read(bytes);
                    outputStream.write(bytes);
                }
            }
        } catch (IOException ioException) {
            throw new FileSystemException(ioException.getMessage(), ioException);
        } finally {
            ResourceUtil.releaseResource(outputStream);
            ResourceUtil.releaseResource(fileInputStream);
        }
    }

    /**
     * Adds the attachment.
     * 
     * @param response the response
     * @param fileToAttach the file to attach
     * @param mimeType the mime type
     * @param attach the attach
     * @throws FileSystemException the file system exception
     */
    public static void addAttachment(HttpServletResponse response,
            File fileToAttach, String mimeType, boolean attach) throws FileSystemException {

        FileInputStream fileInputStream = null;
        BufferedOutputStream bufferedOutputStream = null;

        try {
            if (fileToAttach != null) {
                response.setContentLength((int) fileToAttach.length());
                if (attach) {
                    response.setHeader("Content-Disposition", "attachment; filename=\""
                            + fileToAttach.getName() + "\"");
                }
                if (mimeType != null){
                    response.setContentType(mimeType);
                }
                fileInputStream = new FileInputStream(fileToAttach);
                bufferedOutputStream = new BufferedOutputStream(response.getOutputStream());
                
                byte[] buffer = new byte[8192]; 
                for (int length = 0; (length = fileInputStream.read(buffer)) > 0;) { 
                    bufferedOutputStream.write(buffer, 0, length); 
                }
                bufferedOutputStream.flush();
            }
        } catch (IOException ioException) {
            throw new FileSystemException(ioException.getMessage(), ioException);
        } finally {
            ResourceUtil.releaseResource(bufferedOutputStream);
            ResourceUtil.releaseResource(fileInputStream);
        }
    }

}
