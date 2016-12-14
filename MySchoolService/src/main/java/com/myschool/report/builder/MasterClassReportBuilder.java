package com.myschool.report.builder;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.clazz.domain.ClassManager;
import com.myschool.clazz.dto.ClassDto;
import com.myschool.common.exception.DataException;
import com.myschool.infra.report.builders.SimpleListingReportBuilder;
import com.myschool.infra.report.exception.ReportException;
import com.myschool.report.dto.ReportCriteria;

/**
 * The Class MasterClassReportBuilder.
 */
@Component
public class MasterClassReportBuilder extends SimpleListingReportBuilder {

    /** The number of columns. */
    protected static int numberOfColumns = 2;

    /** The class manager. */
    @Autowired
    private ClassManager classManager;

    /* (non-Javadoc)
     * @see com.myschool.infra.report.builders.SimpleListingReportBuilder#getListingHeaders()
     */
    @Override
    public String[] getListingHeaders() {
        int index = 0;
        String[] listingHeaders = new String[numberOfColumns];
        listingHeaders[index++] = "Class Name";
        listingHeaders[index++] = "Promotion Order";
        return listingHeaders;
    }

    /* (non-Javadoc)
     * @see com.myschool.infra.report.builders.SimpleListingReportBuilder#getListingData(com.myschool.report.dto.ReportCriteria)
     */
    @Override
    public List<Object[]> getListingData(ReportCriteria reportCriteria) throws ReportException {
        List<Object[]> reportData = null;
        try {
            List<ClassDto> classes = classManager.getAll();
            if (classes != null && !classes.isEmpty()) {
                reportData = new ArrayList<Object[]>();
                for (ClassDto clazz : classes) {
                    int index = 0;
                    Object[] rowData = new Object[numberOfColumns];
                    rowData[index++] = clazz.getClassName();
                    rowData[index++] = String.valueOf(clazz.getPromotionOrder());
                    reportData.add(rowData);
                }
            }
            return reportData;
        } catch (DataException dataException) {
            throw new ReportException(dataException.getMessage(), dataException);
        }
    }

}
