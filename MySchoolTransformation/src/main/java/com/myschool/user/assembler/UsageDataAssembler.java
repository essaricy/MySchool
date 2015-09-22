package com.myschool.user.assembler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.myschool.application.dto.NumberNameValueDto;

/**
 * The Class UsageDataAssembler.
 */
public class UsageDataAssembler {

	/**
	 * Creates the.
	 *
	 * @param resultSet the result set
	 * @return the number name value dto
	 * @throws SQLException the sQL exception
	 */
	public static NumberNameValueDto create(ResultSet resultSet) throws SQLException {
		NumberNameValueDto numberNameValueDto = new NumberNameValueDto();
		numberNameValueDto.setNumber(resultSet.getInt("SEQ_NUMBER"));
		numberNameValueDto.setName(resultSet.getString("DESCRIPTION"));
		numberNameValueDto.setValue(resultSet.getInt("VALUE"));
		return numberNameValueDto;
	}

	/**
	 * Fill.
	 *
	 * @param inNumberNameValues the in number name values
	 * @param type the type
	 * @return the list
	 */
	public static List<NumberNameValueDto> fill(List<NumberNameValueDto> inNumberNameValues, int type) {
		Calendar calendar = Calendar.getInstance();
		int startValue = 0;
		int endValue = calendar.getActualMaximum(type);
		String[] names = null;
		List<NumberNameValueDto> outNumberNameValues = new ArrayList<NumberNameValueDto>();
		if (type == Calendar.MONTH) {
			names = DateFormatSymbols.getInstance().getShortMonths();
			startValue = 1;
		} else if (type == Calendar.DAY_OF_YEAR) {
			startValue = 1;
		} else if (type == Calendar.DAY_OF_WEEK) {
			names = DateFormatSymbols.getInstance().getShortWeekdays();
			startValue = 1;
		} else if (type == Calendar.HOUR_OF_DAY) {
			startValue = 0;
		}
		System.out.println("startValue " + startValue + ", endValue= " + endValue);
		for (int loopCount = startValue; loopCount <= endValue; loopCount++) {
			double value = 0;
			NumberNameValueDto nameNumberValue = new NumberNameValueDto();
			NumberNameValueDto gotNameNumberValuec = get(inNumberNameValues, loopCount);
			if (gotNameNumberValuec != null) {
				value = gotNameNumberValuec.getValue();
			}
			nameNumberValue.setNumber(loopCount);
			if (names == null) {
				nameNumberValue.setName(String.valueOf(loopCount));
			} else {
				nameNumberValue.setName(names[loopCount-1]);
			}
			nameNumberValue.setValue(value);
			outNumberNameValues.add(nameNumberValue);
		}
		if (inNumberNameValues != null) {
			inNumberNameValues.clear();
		}
		return outNumberNameValues;
	}

	/**
	 * Gets the.
	 *
	 * @param numberNameValues the number name values
	 * @param numberToGet the number to get
	 * @return the number name value dto
	 */
	private static NumberNameValueDto get(List<NumberNameValueDto> numberNameValues,
			int numberToGet) {
		if (numberNameValues != null) {
			for (NumberNameValueDto numberNameValue : numberNameValues) {
				if (numberNameValue != null && numberNameValue.getNumber() == numberToGet) {
					return numberNameValue;
				}
			}
		}
		return null;
	}

}
