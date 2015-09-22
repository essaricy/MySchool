package com.myschool.application.assembler;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.myschool.application.dto.DateValueDto;
import com.myschool.application.dto.NumberNameValueDto;
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
     * @param toDateType the to date type
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
	 * Creates the line series.
	 *
	 * @param keySet the key set
	 * @param nameNumberValuesByUserType the name number values by user type
	 * @return the list
	 */
	private static List<List<BigDecimal>> createLineSeries(Set<UserType> keySet,
			Map<UserType, List<NumberNameValueDto>> nameNumberValuesByUserType) {
        UserType userType = null;
        List<BigDecimal> lineSeries = null;
        List<List<BigDecimal>> lineSeriesData = null;

        if (keySet != null && !keySet.isEmpty()
                && nameNumberValuesByUserType != null && !nameNumberValuesByUserType.isEmpty()) {
            lineSeriesData = new ArrayList<List<BigDecimal>>();
            for (Iterator<UserType> iterator = keySet.iterator(); iterator.hasNext();) {
            	userType = (UserType) iterator.next();
            	List<NumberNameValueDto> nameNumberValues = nameNumberValuesByUserType.get(userType);
            	if (nameNumberValues != null && !nameNumberValues.isEmpty()) {
            		lineSeries = new ArrayList<BigDecimal>();
            		for (NumberNameValueDto nameNumberValue : nameNumberValues) {
            			lineSeries.add(new BigDecimal(nameNumberValue.getValue()));
            		}
            	}
            	lineSeriesData.add(lineSeries);
            }
        }
        System.out.println("lineSeries = " + lineSeries);
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
     * @param nameNumberValues the date values
     * @return the axis dto
     */
    private static AxisDto createXAxis(List<NumberNameValueDto> nameNumberValues) {
        AxisDto axisDto = null;
        List<String> markers = null;
        if (nameNumberValues != null && !nameNumberValues.isEmpty()) {
            axisDto = new AxisDto();
            markers = new ArrayList<String>();
            axisDto.setMarkers(markers);
            for (NumberNameValueDto numberNameValue : nameNumberValues) {
            	markers.add(numberNameValue.getName());
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

	/**
	 * Creates the.
	 *
	 * @param numberNameValuesByUserType the number name values by user type
	 * @return the line chart dto
	 */
	public static LineChartDto create(
			Map<UserType, List<NumberNameValueDto>> numberNameValuesByUserType) {
        UserType userType = null;
        LineChartDto lineChart = null;
        if (numberNameValuesByUserType != null && !numberNameValuesByUserType.isEmpty()) {
            lineChart = new LineChartDto();
            Set<UserType> keySet = numberNameValuesByUserType.keySet();
            for (Iterator<UserType> iterator = keySet.iterator(); iterator.hasNext();) {
                userType = (UserType) iterator.next();
                lineChart.setXAxis(createXAxis(numberNameValuesByUserType.get(userType)));
                break;
            }
            lineChart.setSeriesNames(createSeriesNames(keySet));
            lineChart.setLineSeries(createLineSeries(keySet, numberNameValuesByUserType));
        }
        System.out.println("lineChart " + lineChart);
        return lineChart;
    }

	/**
	 * Gets the name number values by user type map.
	 *
	 * @param dateValuesByUserType the date values by user type
	 * @return the name number values by user type map
	 */
	public static Map<UserType, List<NumberNameValueDto>> getNameNumberValuesByUserTypeMap(
            Map<UserType, List<DateValueDto>> dateValuesByUserType) {
        UserType userType = null;
        Map<UserType, List<NumberNameValueDto>> nameNumberValuesByUserTypeMap = null;
        if (dateValuesByUserType != null && !dateValuesByUserType.isEmpty()) {
        	nameNumberValuesByUserTypeMap = new HashMap<UserType, List<NumberNameValueDto>>();
            Set<UserType> keySet = dateValuesByUserType.keySet();
            for (Iterator<UserType> iterator = keySet.iterator(); iterator.hasNext();) {
                userType = (UserType) iterator.next();
                nameNumberValuesByUserTypeMap.put(userType, getNameNumberValues(dateValuesByUserType.get(userType)));
            }
        }
        return nameNumberValuesByUserTypeMap;
    }

	/**
	 * Gets the name number values.
	 *
	 * @param dateValues the date values
	 * @return the name number values
	 */
	private static List<NumberNameValueDto> getNameNumberValues(
			List<DateValueDto> dateValues) {
		List<NumberNameValueDto> numberNameValues = null;
		if (dateValues != null) {
			numberNameValues = new ArrayList<NumberNameValueDto>();
			for (DateValueDto dateValue : dateValues) {
				numberNameValues.add(create(dateValue));
			}
		}
		return numberNameValues;
	}

	/**
	 * Creates the.
	 *
	 * @param dateValue the date value
	 * @return the number name value dto
	 */
	private static NumberNameValueDto create(DateValueDto dateValue) {
		NumberNameValueDto numberNameValue = null;
		if (dateValue != null) {
			Date date = dateValue.getDate();
			numberNameValue = new NumberNameValueDto();
			if (date != null) {
				numberNameValue.setName(ConversionUtil.toApplicationDateNoYear(date.getTime()));
			}
			numberNameValue.setNumber(0);
			numberNameValue.setValue(dateValue.getValue());
		}
		return numberNameValue;
	}

}
