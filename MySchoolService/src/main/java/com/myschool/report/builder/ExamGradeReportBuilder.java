package com.myschool.report.builder;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.common.exception.DataException;
import com.myschool.common.util.MessageUtil;
import com.myschool.exam.domain.ExamGradeManager;
import com.myschool.exam.dto.ExamGradeDto;
import com.myschool.infra.report.builders.SimpleListingReportBuilder;
import com.myschool.infra.report.exception.ReportException;
import com.myschool.report.dto.ReportCriteria;

/**
 * The Class ExamGradeReportBuilder.
 */
@Component
public class ExamGradeReportBuilder extends SimpleListingReportBuilder {

    /** The number of columns. */
    protected static int numberOfColumns = 2;

    /** The exam grade manager. */
    @Autowired
    private ExamGradeManager examGradeManager;

    /** The message util. */
    @Autowired
    private MessageUtil messageUtil;

    /* (non-Javadoc)
     * @see com.myschool.infra.report.builders.SimpleListingReportBuilder#getListingHeaders()
     */
    @Override
    public String[] getListingHeaders() {
        int index = 0;
        String[] listingHeaders = new String[numberOfColumns];
        listingHeaders[index++] = "Exam Grade Name";
        listingHeaders[index++] = "Qualifying Percentage";
        return listingHeaders;
    }

    /* (non-Javadoc)
     * @see com.myschool.infra.report.builders.SimpleListingReportBuilder#getListingData(com.myschool.report.dto.ReportCriteria)
     */
    @Override
    public List<Object[]> getListingData(ReportCriteria reportCriteria) throws ReportException {
        List<Object[]> reportData = null;
        try {
            List<ExamGradeDto> examGrades = examGradeManager.getGrades();
            if (examGrades != null && !examGrades.isEmpty()) {
                reportData = new ArrayList<Object[]>();
                for (ExamGradeDto examGrade : examGrades) {
                    int index = 0;
                    Object[] rowData = new Object[numberOfColumns];
                    rowData[index++] = examGrade.getGradeName();
                    rowData[index++] = examGrade.getQualifyingPercentage() + " %";
                    reportData.add(rowData);
                }
            }
            return reportData;
        } catch (DataException dataException) {
            throw new ReportException(dataException.getMessage(), dataException);
        }
    }

}
