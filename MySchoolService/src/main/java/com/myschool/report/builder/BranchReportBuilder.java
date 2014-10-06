package com.myschool.report.builder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.branch.assembler.BranchDataAssembler;
import com.myschool.branch.domain.BranchManager;
import com.myschool.branch.dto.BranchDto;
import com.myschool.common.exception.DataException;
import com.myschool.common.util.MessageUtil;
import com.myschool.infra.report.builders.SimpleListingReportBuilder;
import com.myschool.infra.report.exception.ReportException;
import com.myschool.report.dto.ReportCriteria;
import com.myschool.report.dto.ReportCriteriaToken;

/**
 * The Class BranchReportBuilder.
 */
@Component
public class BranchReportBuilder extends SimpleListingReportBuilder {

    /** The number of columns. */
    protected static int numberOfColumns = 6;

    /** The branch manager. */
    @Autowired
    private BranchManager branchManager;

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
        listingHeaders[index++] = messageUtil.getMessage("branch.branchCode");
        listingHeaders[index++] = messageUtil.getMessage("common.region");
        listingHeaders[index++] = messageUtil.getMessage("common.description");
        listingHeaders[index++] = messageUtil.getMessage("common.address");
        listingHeaders[index++] = messageUtil.getMessage("common.phoneNumber");
        listingHeaders[index++] = messageUtil.getMessage("common.email");
        return listingHeaders;
    }

    /* (non-Javadoc)
     * @see com.myschool.infra.report.builders.SimpleListingReportBuilder#getListingData(com.myschool.report.dto.ReportCriteria)
     */
    @Override
    public List<Object[]> getListingData(ReportCriteria reportCriteria) throws ReportException {
        List<Object[]> reportData = null;
        try {
            Map<ReportCriteriaToken, String> reportCriteriaValues = reportCriteria.getReportCriteriaValues();
            BranchDto branchCriteria = BranchDataAssembler.create(reportCriteriaValues);
            List<BranchDto> branches = branchManager.getAll(branchCriteria);
            if (branches != null && !branches.isEmpty()) {
                reportData = new ArrayList<Object[]>();
                for (BranchDto branch : branches) {
                    int index = 0;
                    Object[] rowData = new Object[numberOfColumns];
                    rowData[index++] = branch.getBranchCode();
                    rowData[index++] = branch.getRegion().getRegionName();
                    rowData[index++] = branch.getDescription();
                    rowData[index++] = branch.getAddress();
                    rowData[index++] = branch.getPhoneNumber();
                    rowData[index++] = branch.getEmailId();
                    reportData.add(rowData);
                }
            }
            return reportData;
        } catch (DataException dataException) {
            throw new ReportException(dataException.getMessage(), dataException);
        }
    }

}
