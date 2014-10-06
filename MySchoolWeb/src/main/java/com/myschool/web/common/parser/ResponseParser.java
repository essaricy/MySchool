package com.myschool.web.common.parser;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.myschool.common.dto.ResultDto;
import com.myschool.infra.web.constants.MimeTypes;

/**
 * The Class ResponseParser.
 */
public class ResponseParser {

    /**
     * Write response.
     *
     * @param response the response
     * @param resultDto the result dto
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static void writeResponse(HttpServletResponse response,
            ResultDto resultDto) throws IOException {

        PrintWriter writer = null;

        try {
            if (resultDto != null) {
                response.setContentType(MimeTypes.TEXT_XML);
                writer = response.getWriter();
                writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
                writer.write(resultDto.toXml());
                writer.flush();
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
     * @param result the result
     * @throws JSONException the jSON exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static void writeJson(HttpServletResponse response, ResultDto result)
            throws JSONException, IOException {
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
            writer.write(jsonObject.toString());
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

}
