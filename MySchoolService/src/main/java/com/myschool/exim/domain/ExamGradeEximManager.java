package com.myschool.exim.domain;

import java.text.MessageFormat;

import org.springframework.stereotype.Component;

import com.myschool.common.dto.Rule;
import com.myschool.common.exception.DaoException;
import com.myschool.common.exception.DataException;
import com.myschool.common.exception.ValidationException;
import com.myschool.common.validator.DataTypeValidator;
import com.myschool.exam.dto.ExamGradeDto;
import com.myschool.exam.fields.ExamGradeFieldNames;
import com.myschool.exim.constants.EximPolicy;
import com.myschool.exim.dto.ImportRecordStatusDto;

/**
 * The Class ExamGradeEximManager.
 */
@Component
public class ExamGradeEximManager extends AbstractEximManager {

    /** The Constant ADD_SUCCESS. */
    private static final String ADD_SUCCESS = "Exam Grade ({0}) has been added successfully.";

    /** The Constant ADD_FAILED. */
    private static final String ADD_FAILED = "System encountered problems while adding Exam Grade ({0})";

    /** The Constant ALREADY_EXISTS. */
    private static final String ALREADY_EXISTS = "Exam Grade ({0}) has already been defined.";

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.myschool.exim.domain.AbstractEximManager#updateContent(com.myschool
     * .exim.constants.EximPolicy, java.lang.Object,
     * com.myschool.common.dto.Rule, java.lang.String)
     */
    @Override
    protected Object updateContent(EximPolicy eximPolicy, Object content, Rule rule,
            String fieldValue) throws DataException, ValidationException {
        if (eximPolicy == EximPolicy.EXAM_GRADES) {
            if (content == null) {
                content = new ExamGradeDto();
            }
            content = updateExamGrade((ExamGradeDto) content, rule, fieldValue);
        }
        return content;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.myschool.exim.domain.AbstractEximManager#validateRecord(com.myschool
     * .exim.constants.EximPolicy, java.lang.Object)
     */
    @Override
    protected ImportRecordStatusDto validateRecord(EximPolicy eximPolicy,
            Object content) {
        ImportRecordStatusDto importRecordStatus = new ImportRecordStatusDto();
        return importRecordStatus;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.myschool.exim.domain.AbstractEximManager#processRecord(com.myschool
     * .exim.constants.EximPolicy, com.myschool.exim.dto.ImportRecordStatusDto)
     */
    @Override
    protected void processRecord(EximPolicy eximPolicy, ImportRecordStatusDto importRecordStatus) {
        try {
            if (eximPolicy == EximPolicy.EXAM_GRADES) {
                ExamGradeDto examGrade = (ExamGradeDto) importRecordStatus.getContent();
                String examGradeName = examGrade.getGradeName();
                if (examGradeDao.get(examGradeName) == null) {
                    if (examGradeDao.create(examGrade) > 0) {
                        importRecordStatus.setStatusCode(ImportRecordStatusDto.STATUS_ADDED);
                        importRecordStatus.setStatusDescription(MessageFormat.format(ADD_SUCCESS, examGradeName));
                    } else {
                        importRecordStatus.setStatusCode(ImportRecordStatusDto.STATUS_FAILED);
                        importRecordStatus.setStatusDescription(MessageFormat.format(ADD_FAILED, examGradeName));
                    }
                } else {
                    importRecordStatus.setStatusCode(ImportRecordStatusDto.STATUS_UNPROCESSED);
                    importRecordStatus.setStatusDescription(MessageFormat.format(ALREADY_EXISTS, examGradeName));
                }
            }
        } catch (DaoException daoException) {
            importRecordStatus.setStatusCode(ImportRecordStatusDto.STATUS_FAILED);
            importRecordStatus.setStatusDescription(daoException.getMessage());
        }
    }

    /**
     * Update examGrade.
     *
     * @param examGrade the examGrade
     * @param rule the rule
     * @param fieldValue the field value
     * @return the examGrade dto
     * @throws DataException the data exception
     */
    private ExamGradeDto updateExamGrade(ExamGradeDto examGrade, Rule rule,
            String fieldValue) throws DataException {
        String fieldName = rule.getFieldName();
        String dataType = rule.getDataType();
        if (fieldName.equals(ExamGradeFieldNames.GRADE_NAME)) {
            examGrade.setGradeName(DataTypeValidator.validate(fieldValue, dataType, fieldName));
        } else if (fieldName.equals(ExamGradeFieldNames.PERCENTAGE)) {
            examGrade.setQualifyingPercentage(Integer.parseInt(DataTypeValidator.validate(fieldValue, dataType, fieldName)));
        }
        return examGrade;
    }

}
