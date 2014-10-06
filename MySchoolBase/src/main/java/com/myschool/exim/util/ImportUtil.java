package com.myschool.exim.util;

import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;

import com.myschool.common.dto.Rule;
import com.myschool.exim.dto.ImportRecordStatusDto;
import com.myschool.exim.dto.ImportStatusDto;
import com.myschool.exim.exception.EximException;

/**
 * The Class ImportUtil.
 */
public class ImportUtil {

    /**
     * Gets the cell value.
     *
     * @param currentRow the current row
     * @param fieldPosition the field position
     * @return the cell value
     */
    public static String getCellValue(HSSFRow currentRow, int fieldPosition) {
        String cellValue = null;
        HSSFCell currentCell = currentRow.getCell(fieldPosition);

        if (currentCell != null) {
            currentCell.setCellType(Cell.CELL_TYPE_STRING);
            cellValue = currentCell.getStringCellValue();
        }
        return cellValue;
    }

    /**
     * Gets the cell value.
     *
     * @param currentRow the current row
     * @param fieldPosition the field position
     * @param fieldName the field name
     * @return the cell value
     */
    public static String getCellValue(HSSFRow currentRow, int fieldPosition, String fieldName) {
        String cellValue = null;
        HSSFCell currentCell = currentRow.getCell(fieldPosition);

        if (currentCell != null) {
            if (fieldName.indexOf("DATE") != -1) {
                if (DateUtil.isCellDateFormatted(currentCell)) {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); 
                    cellValue =  sdf.format(currentCell.getDateCellValue()); 
                }
            } else {
                cellValue = getCellValue(currentRow, fieldPosition);
            }
        }
        return cellValue;
    }

    /**
     * Process preliminary rules.
     *
     * @param rule the rule
     * @param fieldValue the field value
     * @throws EximException the import exception
     */
    public static void processPreliminaryRules(Rule rule, String fieldValue) throws EximException {
        // Check mandatory constraint
        boolean fieldHasValue = false;
        boolean mandatory = rule.isMandatory();
        String fieldName = rule.getFieldName();
        int maxLength = rule.getMaxLength();

        if (fieldValue != null && !fieldValue.trim().equals("")) {
            fieldHasValue = true;
        }
        if (mandatory) {
            if (fieldHasValue) {
                // Check max length constraint
                if (fieldValue.length() > maxLength) {
                    throw new EximException("Field (" + fieldName + ") exceeds allowed max length. Max is " + maxLength + " characters. Found " + fieldValue.length() + " characters");
                }
            } else {
                throw new EximException("Field (" + fieldName + ") is mandatory and data is null.");
            }
        } else {
            if (fieldHasValue) {
                // Check max length constraint
                if (fieldValue.length() > maxLength) {
                    throw new EximException("Field (" + fieldName + ") exceeds allowed max length. Max is " + maxLength + " characters. Found " + fieldValue.length() + " characters");
                }
            }
        }
    }

    /**
     * Gets the import status.
     *
     * @param importRecordStatusList the import record status list
     * @return the import status
     */
    public static ImportStatusDto getImportStatus(
            List<ImportRecordStatusDto> importRecordStatusList) {
        int statusCode = 0;
        int failedImports = 0;
        int successfulImports = 0;
        ImportStatusDto importStatus = new ImportStatusDto();
        if (importRecordStatusList != null) {
            for (ImportRecordStatusDto importRecordStatus : importRecordStatusList) {
                statusCode = importRecordStatus.getStatusCode();
                if (statusCode == ImportRecordStatusDto.STATUS_FAILED
                        || statusCode == ImportRecordStatusDto.STATUS_INVALID_DATA
                        || statusCode == ImportRecordStatusDto.STATUS_UNPROCESSED) {
                    failedImports++;
                } else if (statusCode == ImportRecordStatusDto.STATUS_ADDED
                        || statusCode == ImportRecordStatusDto.STATUS_UPDATED
                        || statusCode == ImportRecordStatusDto.STATUS_DELETED) {
                    successfulImports++;
                }
            }
            importStatus.setFailedImports(failedImports);
            importStatus.setSuccessfulImports(successfulImports);
            importStatus.setImportRecordStatusList(importRecordStatusList);
            String importStatusMessage = null;
            if (failedImports == 0 && successfulImports == 0) {
                importStatusMessage = "No Updates Happened";
            } else if (failedImports == 0 && successfulImports > 0) {
                importStatusMessage = "All Updates Successful";
            } else if (failedImports > 0 && successfulImports == 0) {
                importStatusMessage = "All Updates Failed";
            } else if (failedImports > 0 && successfulImports > 0) {
                importStatusMessage = "Some Records Updated and Some Failed";
            }
            importStatus.setStatusDescription(importStatusMessage);
        }
        return importStatus;
    }

}
