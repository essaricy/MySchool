package com.myschool.exim.domain;

import java.text.MessageFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.academic.dao.HolidayDao;
import com.myschool.academic.dto.HolidayDto;
import com.myschool.academic.fields.HolidayFieldNames;
import com.myschool.common.dto.Rule;
import com.myschool.common.exception.DaoException;
import com.myschool.common.exception.DataException;
import com.myschool.common.exception.ValidationException;
import com.myschool.common.validator.DataTypeValidator;
import com.myschool.exim.constants.EximPolicy;
import com.myschool.exim.dto.ImportRecordStatusDto;

/**
 * The Class HolidayEximManager.
 */
@Component
public class HolidayEximManager extends AbstractEximManager {

    /** The Constant ADD_SUCCESS. */
    private static final String ADD_SUCCESS = "Holiday ({0}) has been added successfully.";

    /** The Constant ADD_FAILED. */
    private static final String ADD_FAILED = "System encountered problems while adding Holiday ({0})";

    /** The Constant ALREADY_EXISTS. */
    private static final String ALREADY_EXISTS = "Holiday ({0}) has already been defined.";

    /** The holiday dao. */
    @Autowired
    private HolidayDao holidayDao;

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.myschool.exim.domain.AbstractEximManager#updateContent(com.myschool
     * .exim.constants.EximPolicy, java.lang.Object,
     * com.myschool.common.dto.Rule, java.lang.String)
     */
    @Override
    protected Object updateContent(EximPolicy eximPolicy, Object content,
            Rule rule, String fieldValue) throws DataException,
            ValidationException {
        if (eximPolicy == EximPolicy.HOLIDAYS) {
            if (content == null) {
                content = new HolidayDto();
            }
            content = updateHoliday((HolidayDto) content, rule, fieldValue);
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
    protected void processRecord(EximPolicy eximPolicy,
            ImportRecordStatusDto importRecordStatus) {
        try {
            if (eximPolicy == EximPolicy.HOLIDAYS) {
                HolidayDto holiday = (HolidayDto) importRecordStatus.getContent();
                String holidayName = holiday.getHolidayName();
                HolidayDto existingHoliday = holidayDao.get(holiday);
                if (existingHoliday == null) {
                    if (holidayDao.create(holiday) > 0) {
                        importRecordStatus.setStatusCode(ImportRecordStatusDto.STATUS_ADDED);
                        importRecordStatus.setStatusDescription(MessageFormat.format(ADD_SUCCESS, holidayName));
                    } else {
                        importRecordStatus.setStatusCode(ImportRecordStatusDto.STATUS_FAILED);
                        importRecordStatus.setStatusDescription(MessageFormat.format(ADD_FAILED, holidayName));
                    }
                } else {
                    importRecordStatus.setStatusCode(ImportRecordStatusDto.STATUS_UNPROCESSED);
                    importRecordStatus.setStatusDescription(MessageFormat.format(ALREADY_EXISTS, holidayName));
                }
            }
        } catch (DaoException daoException) {
            importRecordStatus.setStatusCode(ImportRecordStatusDto.STATUS_FAILED);
            importRecordStatus.setStatusDescription(daoException.getMessage());
        }
    }

    /**
     * Update holiday.
     * 
     * @param holiday the holiday
     * @param rule the rule
     * @param fieldValue the field value
     * @return the object
     * @throws DataException the data exception
     */
    private Object updateHoliday(HolidayDto holiday, Rule rule,
            String fieldValue) throws DataException {
        String fieldName = rule.getFieldName();
        String dataType = rule.getDataType();
        if (fieldName.equals(HolidayFieldNames.HOLIDAY_NAME)) {
            holiday.setHolidayName(DataTypeValidator.validate(fieldValue, dataType, fieldName));
        } else if (fieldName.equals(HolidayFieldNames.START_DATE)) {
            holiday.setStartDate(DataTypeValidator.validate(fieldValue, dataType, fieldName));
        } else if (fieldName.equals(HolidayFieldNames.END_DATE)) {
            holiday.setEndDate(DataTypeValidator.validate(fieldValue, dataType, fieldName));
        }
        return holiday;
    }

}
