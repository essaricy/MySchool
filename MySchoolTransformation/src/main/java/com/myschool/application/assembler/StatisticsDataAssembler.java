package com.myschool.application.assembler;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.myschool.application.dto.DateValueDto;
import com.myschool.common.util.ConversionUtil;
import com.myschool.common.util.DateUtil;
import com.myschool.graph.constant.ToDateType;
import com.myschool.graph.dto.AxisDto;
import com.myschool.graph.dto.LineChartDto;
import com.myschool.user.constants.UserType;

/**
 * The Class StatisticsDataAssembler.
 */
public class StatisticsDataAssembler {

    /**
     * Fill empty dates.
     * 
     * @param dateValues the date values
     * @param toDateType 
     */
    public static void fillEmptyDates(List<DateValueDto> dateValues, ToDateType toDateType) {
        Calendar calendar = DateUtil.getNewCalendarIgnoreHours();

        if (dateValues != null && !dateValues.isEmpty()) {
            // Sort by dates
            Collections.sort(dateValues);
            DateValueDto dateValue = dateValues.get(0);
            if (dateValue != null && dateValue.getDate() != null) {
                calendar.setTime(dateValue.getDate());
            }
        }
        // Stop exactly at +1 day of previous month
        for (int index = 0; index < toDateType.getDuration(); index++) {
            // If an object is not present for the date, insert a dummy
            if (!contains(dateValues, calendar.getTime())) {
                DateValueDto dateValue = new DateValueDto();
                dateValue.setDate(calendar.getTime());
                dateValues.add(dateValue);
            }
            // decrease date by 1
            calendar.set(Calendar.DATE, calendar.get(Calendar.DATE)-1);
        }
        // Finally sort them again to arrange the fillers
        Collections.sort(dateValues);
        Collections.reverse(dateValues);
    }

    /**
     * Contains.
     * 
     * @param dateValues the date values
     * @param date the date
     * @return true, if successful
     */
    private static boolean contains(List<DateValueDto> dateValues, Date date) {
        if (dateValues != null && !dateValues.isEmpty() && date != null) {
            for (DateValueDto dateValue : dateValues) {
                if (dateValue != null && date.equals(dateValue.getDate())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Creates the.
     * 
     * @param mtdLoginsByUserType the mtd logins by user type
     * @return the line chart dto
     */
    public static LineChartDto create(
            Map<UserType, List<DateValueDto>> mtdLoginsByUserType) {
        UserType userType = null;
        LineChartDto lineChart = null;
        if (mtdLoginsByUserType != null && !mtdLoginsByUserType.isEmpty()) {
            lineChart = new LineChartDto();
            Set<UserType> keySet = mtdLoginsByUserType.keySet();
            for (Iterator<UserType> iterator = keySet.iterator(); iterator.hasNext();) {
                userType = (UserType) iterator.next();
                lineChart.setXAxis(createXAxis(mtdLoginsByUserType.get(userType)));
                break;
            }
            lineChart.setSeriesNames(createSeriesNames(keySet));
            lineChart.setLineSeries(createLineSeries(keySet, mtdLoginsByUserType));
        }
        return lineChart;
    }

    /**
     * Creates the line series.
     * 
     * @param keySet the key set
     * @param mtdLoginsByUserType the mtd logins by user type
     * @return the list
     */
    private static List<List<BigDecimal>> createLineSeries(
            Set<UserType> keySet, Map<UserType, List<DateValueDto>> mtdLoginsByUserType) {
        UserType userType = null;
        List<DateValueDto> dateValues = null;
        List<BigDecimal> lineSeries = null;
        List<List<BigDecimal>> lineSeriesData = null;

        if (keySet != null && !keySet.isEmpty()
                && mtdLoginsByUserType != null && !mtdLoginsByUserType.isEmpty()) {
            lineSeriesData = new ArrayList<List<BigDecimal>>();
            for (Iterator<UserType> iterator = keySet.iterator(); iterator.hasNext();) {
                userType = (UserType) iterator.next();
                dateValues = mtdLoginsByUserType.get(userType);
                if (dateValues != null && !dateValues.isEmpty()) {
                    lineSeries = new ArrayList<BigDecimal>();
                    for (DateValueDto dateValue : dateValues) {
                        if (dateValue != null) {
                            lineSeries.add(new BigDecimal(dateValue.getValue()));
                            //lineSeries.add(new BigDecimal(20 + (Math.random() * ( 99 - 20 ))));
                        }
                    }
                    lineSeriesData.add(lineSeries);
                }
            }
        }
        return lineSeriesData;
    }

    /**
     * Creates the series names.
     * 
     * @param keySet the key set
     * @return the list
     */
    private static List<String> createSeriesNames(Set<UserType> keySet) {
        UserType userType = null;
        List<String> seriesNames = new ArrayList<String>();
        for (Iterator<UserType> iterator = keySet.iterator(); iterator.hasNext();) {
            userType = (UserType) iterator.next();
            seriesNames.add(userType.toString());
        }
        return seriesNames;
    }

    /**
     * Creates the x axis.
     * 
     * @param dateValues the date values
     * @return the axis dto
     */
    private static AxisDto createXAxis(List<DateValueDto> dateValues) {
        AxisDto axisDto = null;
        List<String> markers = null;
        if (dateValues != null && !dateValues.isEmpty()) {
            axisDto = new AxisDto();
            markers = new ArrayList<String>();
            axisDto.setMarkers(markers);
            for (DateValueDto dateValue : dateValues) {
                if (dateValue != null && dateValue.getDate() != null) {
                    markers.add(ConversionUtil.toApplicationDateNoYear(dateValue.getDate().getTime()));
                }
            }
        }
        return axisDto;
    }

    /**
     * Creates the date value.
     * 
     * @param resultSet the result set
     * @return the date value dto
     * @throws SQLException the sQL exception
     */
    public static DateValueDto createDateValue(ResultSet resultSet) throws SQLException {
        DateValueDto dateValue = new DateValueDto();
        dateValue.setDate(resultSet.getDate("TO_DATE"));
        dateValue.setValue(resultSet.getInt("TO_DATE_VALUE"));
        return dateValue;
    }

}
