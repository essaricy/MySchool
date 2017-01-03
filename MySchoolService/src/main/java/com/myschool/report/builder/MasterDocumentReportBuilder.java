package com.myschool.report.builder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.application.assembler.DocumentDataAssembler;
import com.myschool.application.domain.DocumentManager;
import com.myschool.common.dto.DocumentDto;
import com.myschool.common.dto.DocumentSearchCriteria;
import com.myschool.infra.report.builders.SimpleListingReportBuilder;
import com.myschool.infra.report.exception.ReportException;
import com.myschool.report.dto.ReportCriteria;
import com.myschool.report.dto.ReportCriteriaToken;
import com.quasar.core.exception.DataException;

/**
 * The Class MasterDocumentReportBuilder.
 */
@Component
public class MasterDocumentReportBuilder extends SimpleListingReportBuilder {

    /** The number of columns. */
    protected static int numberOfColumns = 4;

    /** The document manager. */
    @Autowired
    private DocumentManager documentManager;

    /* (non-Javadoc)
     * @see com.myschool.infra.report.builders.SimpleListingReportBuilder#getListingHeaders()
     */
    @Override
    public String[] getListingHeaders() {
        int index = 0;
        String[] listingHeaders = new String[numberOfColumns];
        listingHeaders[index++] = "Document Name";
        listingHeaders[index++] = "Description";
        listingHeaders[index++] = "For Employees";
        listingHeaders[index++] = "For Students";
        return listingHeaders;
    }

    /* (non-Javadoc)
     * @see com.myschool.infra.report.builders.SimpleListingReportBuilder#getListingData(com.myschool.report.dto.ReportCriteria)
     */
    @Override
    public List<Object[]> getListingData(ReportCriteria reportCriteria) throws ReportException {
        List<Object[]> reportData = null;
        try {
            // search criteria: documents only for students, only for employees
            Map<ReportCriteriaToken, String> reportCriteriaValues = reportCriteria.getReportCriteriaValues();
            DocumentSearchCriteria documentSearchCriteria = DocumentDataAssembler.create(reportCriteriaValues);
            List<DocumentDto> documents = documentManager.getAll(documentSearchCriteria);
            if (documents != null && !documents.isEmpty()) {
                reportData = new ArrayList<Object[]>();
                for (DocumentDto document : documents) {
                    int index = 0;
                    Object[] rowData = new Object[numberOfColumns];
                    rowData[index++] = document.getName();
                    rowData[index++] = document.getDescription();
                    rowData[index++] = document.getApplicabilityForEmployee().toString();
                    rowData[index++] = document.getApplicabilityForStudent().toString();
                    reportData.add(rowData);
                }
            }
            return reportData;
        } catch (DataException dataException) {
            throw new ReportException(dataException.getMessage(), dataException);
        }
    }

}
