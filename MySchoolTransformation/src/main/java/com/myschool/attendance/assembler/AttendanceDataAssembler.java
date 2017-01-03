package com.myschool.attendance.assembler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.myschool.attendance.dto.AttendanceCodeDto;
import com.quasar.core.util.ConversionUtil;

/**
 * The Class AttendanceDataAssembler.
 */
public class AttendanceDataAssembler {

    /**
     * Gets the month.
     * 
     * @param resultSet the result set
     * @return the month
     * @throws SQLException the sQL exception
     *//*
    private static JSONObject getMonth(Month month) {
        JSONObject jsonObject = null;
        if (month != null) {
            jsonObject = new JSONObject();
            jsonObject.put("Number", month.getNumber());
            jsonObject.put("FullName", month.getFullName());
            jsonObject.put("ShortName", month.getShortName());
        }
        return jsonObject;
    }

    *//**
     * Gets the year attendance.
     * 
     * @param academicYearStartDate the academic year start date
     * @param academicYearEndDate the academic year end date
     * @param holidays the holidays
     * @param yearAttendance the year attendance
     * @return the year attendance
     *//*
    public static List<AttendanceMonth> getYearAttendance(Date academicYearStartDate,
            Date academicYearEndDate, List<HolidayDto> holidays, List<AttendanceMonth> yearAttendance) {

        AttendanceMonth monthAttendance = null;
        int previousMonth = -1;
        int currentMonth = 0;
        Date currentDate = new Date();

        if (academicYearStartDate != null && academicYearEndDate != null && academicYearStartDate.before(academicYearEndDate)) {
            // check if exists, otherwise create a blank entry
            if (yearAttendance == null) {
                yearAttendance = new ArrayList<AttendanceMonth>();
            }

            Calendar attendanceDateCalendar = DateUtil.getNewCalendarIgnoreHours();

            // Fill the gaps from first date of month of the academic start month to academic start date
            AttendanceDay dayAttendance = null;
            // The below code is to fill up the unaccounted days of start of the month
            // If academic year start date is March 03, then the below code fills March 01, 02 as UNACCOUNTED
            attendanceDateCalendar.setTime(academicYearStartDate);
            int avademicStartYear = attendanceDateCalendar.get(Calendar.YEAR);
            int avademicStartMonth = attendanceDateCalendar.get(Calendar.MONTH);
            int academicStartDate = attendanceDateCalendar.get(Calendar.DAY_OF_MONTH);

            attendanceDateCalendar.setTime(academicYearEndDate);
            //int academicEndDate = attendanceDateCalendar.get(Calendar.DAY_OF_MONTH);
            //int avademicEndYear = attendanceDateCalendar.get(Calendar.YEAR);

            // Fill up starting of the month to effective academic start day
            for (attendanceDateCalendar.set(Calendar.YEAR, avademicStartYear),
                    attendanceDateCalendar.set(Calendar.MONTH, avademicStartMonth),
                    attendanceDateCalendar.set(Calendar.DAY_OF_MONTH, attendanceDateCalendar.getActualMinimum(Calendar.DAY_OF_MONTH));
                    attendanceDateCalendar.get(Calendar.DAY_OF_MONTH) < academicStartDate;
                    attendanceDateCalendar.set(Calendar.DAY_OF_YEAR, attendanceDateCalendar.get(Calendar.DAY_OF_YEAR)+1)) {
                if (monthAttendance == null) {
                    //currentMonth = attendanceDateCalendar.get(Calendar.MONTH) + 1;
                    monthAttendance = updateExistingOrAddMonth(yearAttendance, attendanceDateCalendar);
                    List<AttendanceDay> dayAttendances = monthAttendance.getDayAttendances();
                    if (dayAttendances != null && (dayAttendances.size() == attendanceDateCalendar.getActualMaximum(Calendar.DAY_OF_MONTH) || dayAttendances.size() == 31)) {
                        break;
                    }
                }
                dayAttendance = updateExistingOrAddDate(monthAttendance, attendanceDateCalendar, holidays, currentDate);
                dayAttendance.setAttendanceCode(AttendanceCode.UNACCOUNTED);
            }
            previousMonth = currentMonth = attendanceDateCalendar.get(Calendar.MONTH);
            // Process all the days that come under academic year
            for (attendanceDateCalendar.setTime(academicYearStartDate);
                    !attendanceDateCalendar.getTime().after(academicYearEndDate);
                    attendanceDateCalendar.set(Calendar.DAY_OF_YEAR, attendanceDateCalendar.get(Calendar.DAY_OF_YEAR)+1)) {

                currentMonth = attendanceDateCalendar.get(Calendar.MONTH);

                if (previousMonth != currentMonth) {
                    // wrap up previous month details
                    wrapUpMonthAttendance(monthAttendance);
                    // update Existing month Or Add a new Month
                    monthAttendance = updateExistingOrAddMonth(yearAttendance, attendanceDateCalendar);
                    previousMonth = currentMonth;
                }
                dayAttendance = updateExistingOrAddDate(monthAttendance, attendanceDateCalendar, holidays, currentDate);
            }
            // The below code is to fill up the unaccounted days of end of the month
            // If academic year end date is Feb 20, then below code fills Feb 21-28 and 28-31 as UNACCOUNTED
            List<AttendanceDay> dayAttendances = monthAttendance.getDayAttendances();
            int actualMaximum = attendanceDateCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
            int numberOfGapDays = actualMaximum - dayAttendances.size();
            for (int index=0; index < numberOfGapDays; index++,
                    attendanceDateCalendar.set(Calendar.DAY_OF_YEAR, attendanceDateCalendar.get(Calendar.DAY_OF_YEAR)+1)) {
                dayAttendance = updateExistingOrAddDate(monthAttendance, attendanceDateCalendar, holidays, currentDate);
                dayAttendance.setAttendanceCode(AttendanceCode.UNACCOUNTED);
            }
            wrapUpMonthAttendance(monthAttendance);

            // Update all the months to make all 31 day months. fill up with unaccounted
            for (int index=0; index<yearAttendance.size(); index++) {
                monthAttendance = yearAttendance.get(index);
                dayAttendances = monthAttendance.getDayAttendances();
                int daysTo31=31-dayAttendances.size();
                for (int jindex = 0; jindex < daysTo31; jindex++) {
                    dayAttendance = new AttendanceDay();
                    updateUnAccountedDay(dayAttendance);
                    dayAttendances.add(dayAttendance);
                }
            }
        }
        return yearAttendance;
    }

    *//**
     * Update existing or add month.
     * 
     * @param yearAttendance the year attendance
     * @param calendar the calendar
     * @return the month attendance
     *//*

    *//**
     * Checks if is general holiday.
     * 
     * @param dayNumber the day number
     * @return true, if is general holiday
     *//*
    public static boolean isGeneralHoliday(int dayNumber) {
        if (dayNumber == Calendar.SUNDAY) {
            return true;
        }
        return false;
    }

    *//**
     * Checks if is declared holiday.
     * 
     * @param holidays the holidays
     * @param calendar the calendar
     * @return true, if is declared holiday
     * @throws InvalidDataException the invalid data exception
     *//*
    public static boolean isDeclaredHoliday(List<HolidayDto> holidays,
            Calendar calendar) throws InvalidDataException {
        boolean declaredHoliday = false;
        if (calendar != null && holidays != null) {
            Date date = calendar.getTime();
            for (HolidayDto holiday : holidays) {
                Date holidayStartDate = ConversionUtil.fromApplicationDate(holiday.getStartDate());
                Date holidayEndDate = ConversionUtil.fromApplicationDate(holiday.getEndDate());
                if (holidayStartDate != null && holidayEndDate != null) {
                    declaredHoliday = DateUtil.isDateInRange(date, holidayStartDate, holidayEndDate);
                }
                if (declaredHoliday) {
                    break;
                }
            }
        }
        return declaredHoliday;
    }

    *//**
     * Gets the assigned.
     * 
     * @return the assigned
     *//*
    public static JSONArray getAssigned(List<? extends Object> assignments) {
        JSONArray assignedArray = null;
        JSONObject assignedJson = null;
        if (assignments != null && !assignments.isEmpty()) {
            assignedArray = new JSONArray();
            for (Object object : assignments) {
                assignedJson = new JSONObject();
                if (object instanceof RegionDto) {
                    RegionDto region = (RegionDto) object;
                    assignedJson = RegionDataAssembler.create(region);
                    assignedJson.put("IDName", "RegionId");
                } else if (object instanceof BranchDto) {
                    BranchDto branch = (BranchDto) object;
                    assignedJson = BranchDataAssembler.create(branch);
                    assignedJson.put("IDName", "BranchId");
                } else if (object instanceof SchoolDto) {
                    SchoolDto school = (SchoolDto) object;
                    assignedJson = SchoolDataAssembler.create(school);
                    assignedJson.put("IDName", "SchoolId");
                } else if (object instanceof RegisteredClassDto) {
                    RegisteredClassDto registeredClass = (RegisteredClassDto) object;
                    assignedJson = RegisteredClassDataAssembler.create(registeredClass);
                    assignedJson.put("IDName", "ClassId");
                }
                assignedArray.put(assignedJson);
            }
        }
        return assignedArray;
    }

     *//**
     * Creates the.
     * 
     * @param attendanceProfileJson the attendance profile json
     * @return the attendance profile dto
     *//*
    public static AttendanceProfileDto create(JSONObject attendanceProfileJson) {
        AttendanceProfileDto attendanceProfile = null;
        if (attendanceProfileJson != null) {
            attendanceProfile = new AttendanceProfileDto();
            attendanceProfile.setAttendanceProfileId(attendanceProfileJson.getInt("AttendanceProfileId"));
            attendanceProfile.setProfileName(attendanceProfileJson.getString("ProfileName"));

            AcademicDto effectiveAcademic = new AcademicDto();
            effectiveAcademic.setAcademicYearName(attendanceProfileJson.getString("EffectiveAcademicYear"));
            attendanceProfile.setEffectiveAcademic(effectiveAcademic);

            attendanceProfile.setYearAttendance(createYearAttendance(attendanceProfileJson.getJSONArray("YearAttendance")));
            attendanceProfile.setRegions(getAssigned(attendanceProfileJson.getJSONArray("AssignedRegions"), RegionDto.class));
            attendanceProfile.setBranches(getAssigned(attendanceProfileJson.getJSONArray("AssignedBranches"), BranchDto.class));
            attendanceProfile.setSchools(getAssigned(attendanceProfileJson.getJSONArray("AssignedSchools"), SchoolDto.class));
            attendanceProfile.setRegisteredClasses(getAssigned(attendanceProfileJson.getJSONArray("AssignedClasses"), RegisteredClassDto.class));
        }
        return attendanceProfile;
    }

    *//**
     * Gets the assigned.
     * 
     * @param <T> the generic type
     * @param jsonArray the json array
     * @param assignmentType the assignment type
     * @return the assigned
     *//*
    private static <T extends Object> List<T> getAssigned(JSONArray jsonArray,
            Class<T> assignmentType) {
        T assignment = null;
        List<T> assignments = null;
        String idValue = null;

        try {
            if (jsonArray != null && jsonArray.length() != 0) {
                assignments = new ArrayList<T>();
                for (int index = 0; index < jsonArray.length(); index++) {
                    idValue = (String) jsonArray.get(index);
                    if (idValue != null) {
                        assignment = assignmentType.getConstructor().newInstance();
                        if (assignment instanceof RegionDto) {
                            ((RegionDto) assignment).setRegionId(Integer.parseInt(idValue));
                        } else if (assignment instanceof BranchDto) {
                            ((BranchDto) assignment).setBranchId(Integer.parseInt(idValue));
                        } else if (assignment instanceof SchoolDto) {
                            ((SchoolDto) assignment).setSchoolId(Integer.parseInt(idValue));
                        } else if (assignment instanceof RegisteredClassDto) {
                            ((RegisteredClassDto) assignment).setClassId(Integer.parseInt(idValue));
                        } else {
                            // Avoid possibility of adding incompatible type to the list
                            continue;
                        }
                        assignments.add(assignment);
                    }
                }
            }
        } catch (Exception exception) {
            // Nothing to do. skip this entry
        }
        return assignments;
    }

    *//**
     * Creates the year attendance.
     * 
     * @param monthAttendanceArray the month attendance array
     * @return the list
     *//*
    private static List<AttendanceMonth> createYearAttendance(JSONArray monthAttendanceArray) {
        List<AttendanceMonth> monthAttendances = null;

        if (monthAttendanceArray != null && monthAttendanceArray.length() != 0) {
            monthAttendances = new ArrayList<AttendanceMonth>();
            for (int index = 0; index < monthAttendanceArray.length(); index++) {
                monthAttendances.add(createMonthAttendance((JSONObject) monthAttendanceArray.get(index)));
            }
        }
        return monthAttendances;
    }

    *//**
     * Creates the month attendance.
     * 
     * @param monthAttendanceJsonObject the month attendance json object
     * @return the month attendance
     *//*
    private static AttendanceMonth createMonthAttendance(JSONObject monthAttendanceJsonObject) {
        JSONObject monthDataJsonObject = null;
        JSONArray daysAttendanceJsonArray = null;


        Month month = null;
        AttendanceMonth monthAttendance = null;

        if (monthAttendanceJsonObject != null) {
            monthAttendance = new AttendanceMonth();
            monthAttendance.setMonthAttendanceId(monthAttendanceJsonObject.getInt("MonthAttendanceId"));
            monthDataJsonObject = monthAttendanceJsonObject.getJSONObject("MonthData");
            daysAttendanceJsonArray = monthAttendanceJsonObject.getJSONArray("DaysAttendance");
            if (monthDataJsonObject != null && daysAttendanceJsonArray != null) {
                month = new Month();
                month.setNumber(monthDataJsonObject.getInt("Number"));
                monthAttendance.setMonth(month);
                monthAttendance.setAttendanceYear(monthDataJsonObject.getInt("Year"));
                monthAttendance.setDayAttendances(createDaysAttendance(daysAttendanceJsonArray));
            }
        }
        return monthAttendance;
    }

    *//**
     * Creates the days attendance.
     * 
     * @param daysAttendanceJsonArray the days attendance json array
     * @return the list
     *//*
    private static List<AttendanceDay> createDaysAttendance(
            JSONArray daysAttendanceJsonArray) {
        List<AttendanceDay> dayAttendances = null;

        if (daysAttendanceJsonArray.length() != 0) {
            dayAttendances = new ArrayList<AttendanceDay>();
            for (int jindex = 0; jindex < daysAttendanceJsonArray.length(); jindex++) {
                dayAttendances.add(createDayAttendance((JSONObject) daysAttendanceJsonArray.get(jindex)));
            }
        }
        return dayAttendances;
    }

    *//**
     * Creates the day attendance.
     * 
     * @param dayAttendanceJsonObject the day attendance json object
     * @return the day attendance
     * @throws SQLException 
     *//*
    private static AttendanceDay createDayAttendance(JSONObject dayAttendanceJsonObject) {
        Day day = null;
        AttendanceDay dayAttendance = null;

        if (dayAttendanceJsonObject != null) {
            day = new Day();
            dayAttendance = new AttendanceDay();
            day.setDate(dayAttendanceJsonObject.getInt("Date"));
            dayAttendance.setDay(day);
            dayAttendance.setAttendanceCode(AttendanceCode.getByName(dayAttendanceJsonObject.getString("AttendanceCode")));
        }
        return dayAttendance;
    }*/

    ////////////////////////////////////////////////////////////////////////////////////////////////

    public static AttendanceCodeDto create(ResultSet resultSet) throws SQLException {
        AttendanceCodeDto attendanceCodeDto = new AttendanceCodeDto();
        attendanceCodeDto.setCode(resultSet.getString("CODE"));
        attendanceCodeDto.setShortDescription(resultSet.getString("SHORT_DESCRIPTION"));
        attendanceCodeDto.setLongDescription(resultSet.getString("LONG_DESCRIPTION"));
        attendanceCodeDto.setUseInReference(ConversionUtil.toBoolean(resultSet.getString("USE_IN_REFERENCE")));
        attendanceCodeDto.setUseInAssignment(ConversionUtil.toBoolean(resultSet.getString("USE_IN_ASSIGNMENT")));
        return attendanceCodeDto;
    }

    /**
     * Creates the.
     * 
     * @param attendanceCodes the attendance codes
     * @return the jSON array
     */
    public static JSONArray create(List<AttendanceCodeDto> attendanceCodes) {
        JSONArray jsonArray = null;
        if (attendanceCodes != null && !attendanceCodes.isEmpty()) {
            jsonArray = new JSONArray();
            for (AttendanceCodeDto attendanceCode : attendanceCodes) {
                jsonArray.put(create(attendanceCode));
            }
        }
        return jsonArray;
    }

    /**
     * Creates the.
     * 
     * @param attendanceCode the attendance code
     * @return the jSON object
     */
    private static JSONObject create(AttendanceCodeDto attendanceCode) {
        JSONObject jsonObject = null;
        if (attendanceCode != null) {
            jsonObject = new JSONObject();
            jsonObject.put("Code", attendanceCode.getCode());
            jsonObject.put("ShortDescription", attendanceCode.getShortDescription());
            jsonObject.put("LongDescription", attendanceCode.getLongDescription());
            jsonObject.put("UseInReference", attendanceCode.isUseInReference());
            jsonObject.put("UseInAssignment", attendanceCode.isUseInAssignment());
        }
        return jsonObject;
    }

}
