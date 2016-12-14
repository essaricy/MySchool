package com.myschool.report.builder;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.application.domain.RelationshipManager;
import com.myschool.common.dto.Relationship;
import com.myschool.common.exception.DataException;
import com.myschool.infra.report.builders.SimpleListingReportBuilder;
import com.myschool.infra.report.exception.ReportException;
import com.myschool.report.dto.ReportCriteria;

/**
 * The Class RelationshipCodeReportBuilder.
 */
@Component
public class RelationshipCodeReportBuilder extends SimpleListingReportBuilder {

    /** The relationship manager. */
    @Autowired
    private RelationshipManager relationshipManager;

    /* (non-Javadoc)
     * @see com.myschool.infra.report.builders.SimpleListingReportBuilder#getListingHeaders()
     */
    @Override
    public String[] getListingHeaders() {
        int index = 0;
        String[] listingHeaders = new String[6];
        listingHeaders[index++] = "Code";
        listingHeaders[index++] = "Name";
        return listingHeaders;
    }

    /* (non-Javadoc)
     * @see com.myschool.infra.report.builders.SimpleListingReportBuilder#getListingData(com.myschool.report.dto.ReportCriteria)
     */
    @Override
    public List<Object[]> getListingData(ReportCriteria reportCriteria) throws ReportException {
        List<Object[]> reportData = null;
        try {
            List<Relationship> relationships = relationshipManager.getAll();
            if (relationships != null && !relationships.isEmpty()) {
                reportData = new ArrayList<Object[]>();
                for (Relationship relationship : relationships) {
                    int index = 0;
                    Object[] rowData = new Object[6];
                    rowData[index++] = relationship.getCode();
                    rowData[index++] = relationship.getName();
                    reportData.add(rowData);
                }
            }
            return reportData;
        } catch (DataException dataException) {
            throw new ReportException(dataException.getMessage(), dataException);
        }
    }

}
