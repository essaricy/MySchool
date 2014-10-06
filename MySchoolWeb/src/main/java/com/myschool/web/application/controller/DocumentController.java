package com.myschool.web.application.controller;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.myschool.application.service.DocumentService;
import com.myschool.common.constants.DocumentApplicability;
import com.myschool.common.dto.DocumentDto;
import com.myschool.common.dto.ResultDto;
import com.myschool.common.exception.DataException;
import com.myschool.common.exception.ServiceException;
import com.myschool.common.util.StringUtil;
import com.myschool.common.validator.DataTypeValidator;
import com.myschool.infra.web.constants.MimeTypes;
import com.myschool.user.constants.UserType;
import com.myschool.web.application.constants.ApplicationViewNames;
import com.myschool.web.common.parser.ResponseParser;
import com.myschool.web.common.util.ViewDelegationController;

/**
 * The Class DocumentController.
 */
@Controller
@RequestMapping("document")
public class DocumentController {

    /** The document service. */
    @Autowired
    private DocumentService documentService;

    /**
     * List.
     * 
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value="list")
    public ModelAndView list(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        return ViewDelegationController.delegateWholePageView(request, ApplicationViewNames.VIEW_DOCUMENTS);
    }

    /**
     * Json list.
     *
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value="jsonList")
    public ModelAndView jsonList(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        List<DocumentDto> documents = null;
        JSONArray data = new JSONArray();
        JSONObject jsonResponse = new JSONObject();
        String type = request.getParameter("type");

        if (StringUtil.isNullOrBlank(type)) {
            documents = documentService.getAll();
        } else {
            documents = documentService.getAll(UserType.get(type));
        }

        if (documents != null) {
            for(DocumentDto document : documents) {
                JSONArray row = new JSONArray();
                row.put(document.getDocumentId())
                .put(document.getName())
                .put(document.getDescription())
                .put(document.getApplicabilityForEmployee().toString())
                .put(document.getApplicabilityForStudent().toString());
                data.put(row);
            }
        }
        jsonResponse.put(DataTypeValidator.AA_DATA, data);
        response.setContentType(MimeTypes.APPLICATION_JSON);
        PrintWriter writer = response.getWriter();
        writer.print(jsonResponse.toString());
        writer.close();
        return null;
    }

    /**
     * Launch.
     * 
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value="launch")
    public ModelAndView launch(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        Map<String, Object> map = new HashMap<String, Object>();
        String documentId = request.getParameter("documentId");

        if (!StringUtil.isNullOrBlank(documentId)) {
            DocumentDto document = documentService.get(Integer.parseInt(documentId));
            map.put("document", document);
        }
        return ViewDelegationController.delegateModelPageView(request, ApplicationViewNames.MAINTAIN_DOCUMENT, map);
    }

    /**
     * Do create.
     *
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value="doCreate")
    public ModelAndView doCreate(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        ResultDto resultDto = new ResultDto();

        try {
            DocumentDto document = validateAndGetDocument(request);
            resultDto.setSuccessful(documentService.create(document));
        } catch (DataException dataException) {
            resultDto.setStatusMessage(dataException.getMessage());
        } catch (ServiceException serviceException) {
            resultDto.setStatusMessage(serviceException.getMessage());
        } finally {
            ResponseParser.writeResponse(response, resultDto);
        }
        return null;
    }

    /**
     * Do update.
     *
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value="doUpdate")
    public ModelAndView doUpdate(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        ResultDto resultDto = new ResultDto();

        try {
            String documentId = request.getParameter("documentId");
            if (!StringUtil.isNullOrBlank(documentId)) {
                DocumentDto document = validateAndGetDocument(request);
                resultDto.setSuccessful(documentService.update(Integer.parseInt(documentId), document));
                resultDto.setStatusMessage("Document has been updated.");
            }
        } catch (DataException dataException) {
            resultDto.setStatusMessage(dataException.getMessage());
        } catch (ServiceException serviceException) {
            resultDto.setStatusMessage(serviceException.getMessage());
        } finally {
            ResponseParser.writeResponse(response, resultDto);
        }
        return null;
    }

    /**
     * Do delete.
     *
     * @param request the request
     * @param response the response
     * @return the model and view
     * @throws Exception the exception
     */
    @RequestMapping(value="doDelete")
    public ModelAndView doDelete(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        ResultDto resultDto = new ResultDto();
        try {
            String documentId = request.getParameter("documentId");
            resultDto.setSuccessful(documentService.delete(Integer.parseInt(documentId)));
        } catch (ServiceException serviceException) {
            serviceException.printStackTrace();
            resultDto.setStatusMessage(serviceException.getMessage());
        } finally {
            ResponseParser.writeResponse(response, resultDto);
        }
        return null;
    }

    /**
     * Validate and get document.
     * 
     * @param request the request
     * @return the document dto
     * @throws DataException the data exception
     */
    private DocumentDto validateAndGetDocument(HttpServletRequest request) throws DataException {
        DocumentDto document = new DocumentDto();
        document.setName(request.getParameter("documentName"));
        document.setDescription(request.getParameter("description"));
        document.setApplicabilityForEmployee(
                DocumentApplicability.getByCode(request.getParameter("applicabilityForEmployee")));
        document.setApplicabilityForStudent(
                DocumentApplicability.getByCode(request.getParameter("applicabilityForStudent")));
        return document;
    }

}
