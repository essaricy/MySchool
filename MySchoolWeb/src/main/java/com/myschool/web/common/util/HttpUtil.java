package com.myschool.web.common.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import com.myschool.common.dto.ResultDto;
import com.myschool.common.exception.FileSystemException;
import com.myschool.common.util.ResourceUtil;
import com.myschool.common.validator.DataTypeValidator;
import com.myschool.infra.web.constants.MimeTypes;

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

    /**
     * Write json.
     * 
     * @param response the response
     * @param result the result
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static void writeAsJson(HttpServletResponse response, ResultDto result)
            throws IOException {
        System.out.println("writeAsJson result " + result);
        PrintWriter writer = null;
        JSONObject jsonObject = new JSONObject();
        response.setContentType(MimeTypes.APPLICATION_JSON);
        writer = response.getWriter();
        try {
            if (result != null) {
                jsonObject.put("Successful", result.isSuccessful());
                jsonObject.put("StatusMessage", result.getStatusMessage());
                jsonObject.put("ReferenceNumber", result.getReferenceNumber());
            }
            System.out.println("jsonObject " + jsonObject);
            writer.write(jsonObject.toString());
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    /**
     * Write json.
     * 
     * @param response the response
     * @param jsonResponse the json response
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static void writeJson(HttpServletResponse response,
            JSONObject jsonResponse) throws IOException {
        PrintWriter writer = null;
        try {
            if (jsonResponse != null) {
                response.setContentType(MimeTypes.APPLICATION_JSON);
                writer = response.getWriter();
                writer.print(jsonResponse.toString());
            }
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
        
    }

    /**
     * Write json.
     * 
     * @param response the response
     * @param jsonResponse the json response
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static void writeJson(HttpServletResponse response,
            JSONArray jsonResponse) throws IOException {
        PrintWriter writer = null;
        try {
            if (jsonResponse != null) {
                response.setContentType(MimeTypes.APPLICATION_JSON);
                writer = response.getWriter();
                writer.print(jsonResponse.toString());
            }
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
        
    }

    /**
     * Write as aa data.
     * 
     * @param response the response
     * @param data the data
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static void wrapAndWriteAsAAData(HttpServletResponse response,
            JSONArray data) throws IOException {
        wrapAndWriteJson(response, DataTypeValidator.AA_DATA, data);
    }

    /**
     * Wrap and write json.
     * 
     * @param response the response
     * @param keyName the key name
     * @param data the data
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static void wrapAndWriteJson(HttpServletResponse response, String keyName,
            JSONArray data) throws IOException {
        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put(keyName, data);
        writeJson(response, jsonResponse);
    }

    /**
     * Wrap and write json.
     * 
     * @param response the response
     * @param keyName the key name
     * @param data the data
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static void wrapAndWriteJson(HttpServletResponse response, String keyName,
            JSONObject data) throws IOException {
        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put(keyName, data);
        writeJson(response, jsonResponse);
    }

}
