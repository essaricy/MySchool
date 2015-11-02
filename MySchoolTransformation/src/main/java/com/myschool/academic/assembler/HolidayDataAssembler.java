package com.myschool.academic.assembler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.myschool.academic.dto.AcademicDto;
import com.myschool.academic.dto.HolidayDto;
import com.myschool.academic.dto.HolidaySearchCriteria;
import com.myschool.common.util.ConversionUtil;
import com.myschool.report.assembler.ReportDataAssembler;
import com.myschool.report.constants.ReportCriteriaTokenConstants;
import com.myschool.report.dto.ReportCriteriaToken;

/**
 * The Class HolidayDataAssembler.
 */
public class HolidayDataAssembler {

    /**
     * Creates the holiday.
     *
     * @param resultSet the result set
     * @return the holiday dto
     * @throws SQLException the sQL exception
     */
    public static HolidayDto createHoliday(ResultSet resultSet) throws SQLException {
        HolidayDto holiday = new HolidayDto();
        holiday.setHolidayId(resultSet.getInt("HOLIDAY_ID"));
        holiday.setHolidayName(resultSet.getString("HOLIDAY_NAME"));
        holiday.setStartDate(
                ConversionUtil.toApplicationDateFromStorageDate(resultSet.getString("START_DATE")));
        holiday.setEndDate(
                ConversionUtil.toApplicationDateFromStorageDate(resultSet.getString("END_DATE")));
        return holiday;
    }

    /**
     * Creates the.
     * 
     * @param reportCriteriaValues the report criteria values
     * @return the holiday search criteria
     */
    public static HolidaySearchCriteria create(
            Map<ReportCriteriaToken, String> reportCriteriaValues) {
        HolidaySearchCriteria holidaySearchCriteria = null;
        if (reportCriteriaValues != null) {
            holidaySearchCriteria = new HolidaySearchCriteria();
            holidaySearchCriteria.setAcademicYear(ReportDataAssembler.getString(
                    reportCriteriaValues, ReportCriteriaTokenConstants.ACADEMIC_YEAR));
            String[] holidayBetween = ReportDataAssembler.getDates(reportCriteriaValues, ReportCriteriaTokenConstants.DATE_BETWEEN);
            holidaySearchCriteria.setStartDate(holidayBetween[0]);
            holidaySearchCriteria.setEndDate(holidayBetween[1]);
        }
        return holidaySearchCriteria;
    }

    /**
     * Creates the.
     * 
     * @param holidays the holidays
     * @return the jSON array
     */
    public static JSONArray create(List<HolidayDto> holidays) {
        JSONArray jsonArray = null;
        if (holidays != null && !holidays.isEmpty()) {
            jsonArray = new JSONArray();
            for (HolidayDto holiday : holidays) {
                jsonArray.put(create(holiday));
            }
        }
        return jsonArray;
    }

    /**
     * Creates the.
     * 
     * @param holiday the holiday
     * @return the jSON object
     */
    private static JSONObject create(HolidayDto holiday) {
        JSONObject jsonHoliday = null;
        if (holiday != null) {
            jsonHoliday = new JSONObject();
            jsonHoliday.put("HolidayId", holiday.getHolidayId());
            jsonHoliday.put("HolidayName", holiday.getHolidayName());
            jsonHoliday.put("StartDate", holiday.getStartDate());
            jsonHoliday.put("EndDate", holiday.getEndDate());
        }
        return jsonHoliday;
    }

    /**
     * Creates the search criteria.
     * 
     * @param academic the academic
     * @return the holiday search criteria
     */
    public static HolidaySearchCriteria createSearchCriteria(AcademicDto academic) {
        HolidaySearchCriteria holidaySearchCriteria = null;
        if (academic != null) {
            holidaySearchCriteria = new HolidaySearchCriteria();
            holidaySearchCriteria.setAcademicYear(academic.getAcademicYearName());
            holidaySearchCriteria.setStartDate(academic.getAcademicYearStartDate());
            holidaySearchCriteria.setEndDate(academic.getAcademicYearEndDate());
        }
        return holidaySearchCriteria;
    }

}
