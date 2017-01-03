package com.myschool.attendance.assembler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.myschool.academic.assembler.AcademicDataAssembler;
import com.myschool.academic.dto.AcademicDto;
import com.myschool.academic.dto.HolidayDto;
import com.myschool.attendance.dto.AttendanceCode;
import com.myschool.attendance.dto.AttendanceDay;
import com.myschool.attendance.dto.AttendanceMonth;
import com.myschool.attendance.dto.AttendanceProfileDto;
import com.myschool.clazz.assembler.RegisteredClassDataAssembler;
import com.myschool.clazz.dto.RegisteredClassDto;
import com.myschool.common.util.JsonUtil;
import com.myschool.school.assembler.SchoolDataAssembler;
import com.myschool.school.dto.SchoolDto;
import com.quasar.core.util.ConversionUtil;
import com.quasar.core.util.DateUtil;

/**
 * The Class AttendanceProfileDataAssembler.
 */
public class AttendanceProfileDataAssembler {

    /**
     * Creates the.
     * 
     * @param resultSet the result set
     * @return the attendance profile dto
     * @throws SQLException the sQL exception
     */
    public static AttendanceProfileDto create(ResultSet resultSet) throws SQLException {
        AttendanceProfileDto attendanceProfile = new AttendanceProfileDto();
        attendanceProfile.setProfileId(resultSet.getInt("PROFILE_ID"));
        attendanceProfile.setProfileName(resultSet.getString("PROFILE_NAME"));
        attendanceProfile.setEffectiveAcademic(AcademicDataAssembler.create(resultSet, true));
        return attendanceProfile;
    }

    /**
     * Creates the attendance month.
     * 
     * @param resultSet the result set
     * @return the attendance month
     * @throws SQLException the sQL exception
     */
    public static AttendanceMonth createAttendanceMonth(ResultSet resultSet) throws SQLException {
        AttendanceMonth monthAttendance = new AttendanceMonth();
        //Calendar calendar = DateUtil.getNewCalendarIgnoreHours();
        List<AttendanceDay> attendanceDays = new ArrayList<AttendanceDay>();
        for (int date = 1; date <= 31; date++) {
            AttendanceCode attendanceCode = AttendanceCode.get(resultSet.getString("DAY_" + date));
            AttendanceDay attendanceDay = new AttendanceDay();
            attendanceDay.setDate(date);
            attendanceDay.setReference(attendanceCode);
            attendanceDays.add(attendanceDay);
        }
        monthAttendance.setAttendanceDays(attendanceDays);
        monthAttendance.setAttendanceMonthId(resultSet.getInt("ATTENDANCE_MONTH_ID"));
        int monthNumber = resultSet.getInt("MONTH");
        monthAttendance.setMonthName(DateUtil.getMonthName(monthNumber));
        monthAttendance.setMonthNumber(monthNumber);
        monthAttendance.setYear(resultSet.getInt("ATTENDANCE_YEAR"));
        return monthAttendance;
    }

    /**
     * Align.
     * 
     * @param academicDto the academic dto
     * @param holidays the holidays
     * @param attendanceMonths the attendance months
     * @return the list
     */
    public static List<AttendanceMonth> align(AcademicDto academicDto,
            List<HolidayDto> holidays, List<AttendanceMonth> attendanceMonths) {

        AttendanceMonth attendanceMonth = null;
        AttendanceDay attendanceDay = null;
        int previousMonth = -1;
        int currentMonth = -1;
        //Date currentDate = new Date();
        try {
            if (academicDto == null) {
                return attendanceMonths;
            }
            Date academicYearStartDate = ConversionUtil.fromApplicationDate(academicDto.getAcademicYearStartDate());
            Date academicYearEndDate = ConversionUtil.fromApplicationDate(academicDto.getAcademicYearEndDate());
            if (academicYearStartDate == null || academicYearEndDate == null) {
                return attendanceMonths;
            }
            // check if exists, otherwise create a blank entry
            if (attendanceMonths == null) {
                attendanceMonths = new ArrayList<AttendanceMonth>();
            }
            // Academic start calendar
            Calendar academicStartCalendar = DateUtil.getNewCalendarIgnoreHours();
            academicStartCalendar.setTime(academicYearStartDate);
            int academicStartYear = academicStartCalendar.get(Calendar.YEAR);
            int academicStartMonth = academicStartCalendar.get(Calendar.MONTH);
            //int academicStartDate = academicStartCalendar.get(Calendar.DAY_OF_MONTH);
            // Academic End calendar
            Calendar academicEndCalendar = DateUtil.getNewCalendarIgnoreHours();
            academicEndCalendar.setTime(academicYearEndDate);
            // Rolling calendar
            Calendar rollingCalendar = DateUtil.getNewCalendarIgnoreHours();
            rollingCalendar.set(Calendar.YEAR, academicStartYear);
            rollingCalendar.set(Calendar.MONTH, academicStartMonth);
            rollingCalendar.set(Calendar.DAY_OF_MONTH, academicStartCalendar.getActualMinimum(Calendar.DAY_OF_MONTH));
            previousMonth = academicStartMonth;
            currentMonth = -1;
            List<AttendanceDay> attendanceDays = null;
            // Iterate till it reaches the last day of the academic year's last month
            while (!((rollingCalendar.get(Calendar.YEAR) == academicEndCalendar.get(Calendar.YEAR))
                    && (rollingCalendar.get(Calendar.MONTH) == academicEndCalendar.get(Calendar.MONTH))
                    && (rollingCalendar.get(Calendar.DAY_OF_MONTH) == academicEndCalendar.getActualMaximum(Calendar.DAY_OF_MONTH)))) {
                if (previousMonth != currentMonth) {
                    attendanceMonth = getOrCreateAttendanceMonth(attendanceMonths, rollingCalendar);
                    attendanceDays = attendanceMonth.getAttendanceDays();
                    if (attendanceDays == null) {
                        attendanceDays = new ArrayList<AttendanceDay>();
                        attendanceMonth.setAttendanceDays(attendanceDays);
                    }
                    previousMonth = rollingCalendar.get(Calendar.MONTH);
                }
                attendanceDay = getOrCreateAttendanceDay(attendanceDays, rollingCalendar);
                if (rollingCalendar.getTime().before(academicStartCalendar.getTime())) {
                    // If academic year start date is March 03, then the below code fills March 01, 02 as null
                    attendanceDays.set(rollingCalendar.get(Calendar.DAY_OF_MONTH)-1, null);
                } else if (rollingCalendar.getTime().after(academicEndCalendar.getTime())) {
                    // If academic year end date is Apr 05, then the below code fills Apr 05 to 30 as null
                    attendanceDays.set(rollingCalendar.get(Calendar.DAY_OF_MONTH)-1, null);
                } else {
                    // This date falls within the academic year
                    updateAttendanceDay(attendanceDay, rollingCalendar, holidays);
                }
                rollingCalendar.set(Calendar.DAY_OF_YEAR, rollingCalendar.get(Calendar.DAY_OF_YEAR)+1);
            }
            // Update all the months to make all 31 day months. fill up with unaccounted
            for (int index=0; index<attendanceMonths.size(); index++) {
                attendanceMonth = attendanceMonths.get(index);
                attendanceDays = attendanceMonth.getAttendanceDays();
                for (int jindex = attendanceDays.size(); jindex < 31; jindex++) {
                    attendanceDays.add(null);
                }
            }
            debug(attendanceMonths);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return attendanceMonths;
    }

    /**
     * Debug.
     * 
     * @param attendanceMonths the attendance months
     */
    public static void debug(List<AttendanceMonth> attendanceMonths) {
        StringBuffer buffer = new StringBuffer();
        System.out.println("#####################################################################");
        if (attendanceMonths == null) {
            buffer.append("months are null");
        } else {
            for (AttendanceMonth attendanceMonth : attendanceMonths) {
                if (attendanceMonth == null) {
                    buffer.append("null");
                } else {
                    int attendanceMonthId = attendanceMonth.getAttendanceMonthId();
                    buffer.append(attendanceMonthId).append("#");
                    int monthNumber = attendanceMonth.getMonthNumber();
                    String monthName = attendanceMonth.getMonthName();
                    buffer.append(String.format("%02d", monthNumber)).append("#");
                    buffer.append(String.format("%1$-10s", monthName)).append("#");
                    int year = attendanceMonth.getYear();
                    buffer.append(year).append("#");
                    List<AttendanceDay> attendanceDays = attendanceMonth.getAttendanceDays();
                    for (AttendanceDay attendanceDay : attendanceDays) {
                        if (attendanceDay == null) {
                            buffer.append("X1 ");
                        } else {
                            AttendanceCode reference = attendanceDay.getReference();
                            if (reference == null) {
                                buffer.append("X2 ");
                            } else {
                                buffer.append(reference.getCode()).append(" ");
                            }
                        }
                    }
                }
                buffer.append("\n");
            }
        }
        System.out.println(buffer);
        System.out.println("#####################################################################");
    }

    /**
     * Gets the or create attendance month.
     * 
     * @param attendanceMonths the attendance months
     * @param calendar the calendar
     * @return the or create attendance month
     */
    private static AttendanceMonth getOrCreateAttendanceMonth(
            List<AttendanceMonth> attendanceMonths, Calendar calendar) {
        AttendanceMonth attendanceMonth = null;

        int currentMonth = calendar.get(Calendar.MONTH) + 1;
        int currentYear = calendar.get(Calendar.YEAR);

        for (AttendanceMonth existingAttendanceMonth : attendanceMonths) {
            if (existingAttendanceMonth != null) {
                int monthNumber = existingAttendanceMonth.getMonthNumber();
                if (monthNumber == currentMonth
                        && currentYear == existingAttendanceMonth.getYear()) {
                    attendanceMonth = existingAttendanceMonth;
                    break;
                }
            }
        }
        if (attendanceMonth == null) {
            attendanceMonth = new AttendanceMonth();
            attendanceMonth.setMonthNumber(currentMonth);
            attendanceMonth.setAttendanceDays(null);
            attendanceMonth.setAttendanceMonthId(0);
            attendanceMonth.setLocked(false);
            attendanceMonth.setYear(currentYear);
            attendanceMonths.add(attendanceMonth);
        }
        attendanceMonth.setMonthName(DateUtil.getMonthName(currentMonth));
        return attendanceMonth;
    }

    /**
     * Gets the or create attendance day.
     * 
     * @param attendanceDays the attendance days
     * @param calendar the calendar
     * @return the or create attendance day
     */
    private static AttendanceDay getOrCreateAttendanceDay(
            List<AttendanceDay> attendanceDays, Calendar calendar) {

        AttendanceDay attendanceDay = null;
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        Date attendanceDate = calendar.getTime();

        for (AttendanceDay existingAttendanceDay : attendanceDays) {
            if (existingAttendanceDay != null) {
                if (existingAttendanceDay.getDate() == currentDay) {
                    attendanceDay = existingAttendanceDay;
                    break;
                }
            }
        }
        if (attendanceDay == null) {
            attendanceDay = new AttendanceDay();
            attendanceDay.setDate(calendar.get(Calendar.DAY_OF_MONTH));
            attendanceDays.add(attendanceDay);
        }
        attendanceDay.setDayName(DateUtil.DAY_FULL_NAME_FORMAT.format(attendanceDate));
        attendanceDay.setDayNumberInWeek(calendar.get(Calendar.DAY_OF_WEEK));
        return attendanceDay;
    }

    /**
     * Update attendance day.
     * 
     * @param attendanceDay the attendance day
     * @param calendar the calendar
     * @param holidays the holidays
     */
    private static void updateAttendanceDay(
            AttendanceDay attendanceDay, Calendar calendar,
            List<HolidayDto> holidays) {
        // TODO Update holiday information
        /*int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        AttendanceCode reference = attendanceDay.getReference();
        if (reference == null && holidays != null) {
            for (HolidayDto holiday : holidays) {
                //holiday.get
            }
        }*/
    }

    /**
     * Update existing or add month.
     * 
     * @param attendanceProfile the attendance profile
     * @return the attendance month
     *//*
    private static AttendanceMonth updateExistingOrAddMonth(
            List<AttendanceMonth> attendanceMonths, Calendar calendar) {
        AttendanceMonth attendanceMonth = null;

        int currentMonth = calendar.get(Calendar.MONTH) + 1;
        int currentYear = calendar.get(Calendar.YEAR);

        for (AttendanceMonth existingAttendanceMonth : attendanceMonths) {
            if (existingAttendanceMonth != null) {
                int monthNumber = existingAttendanceMonth.getMonthNumber();
                if (monthNumber == currentMonth
                        && currentYear == existingAttendanceMonth.getYear()) {
                    attendanceMonth = existingAttendanceMonth;
                    break;
                }
            }
        }
        if (attendanceMonth == null) {
            attendanceMonth = new AttendanceMonth();
            attendanceMonth.setMonthNumber(currentMonth);
            attendanceMonth.setAttendanceDays(null);
            attendanceMonth.setAttendanceMonthId(0);
            attendanceMonth.setLocked(false);
            attendanceMonth.setYear(currentYear);
            attendanceMonths.add(attendanceMonth);
        }
        attendanceMonth.setMonthName(DateUtil.getMonthName(currentMonth));
        return attendanceMonth;
    }

    *//**
     * Update existing or add date.
     * 
     * @param attendanceMonth the attendance month
     * @param calendar the calendar
     * @param holidays the holidays
     * @param currentDate the current date
     * @return the attendance day
     *//*
    private static AttendanceDay updateExistingOrAddDate(
            AttendanceMonth attendanceMonth, Calendar calendar,
            List<HolidayDto> holidays, Date currentDate) {
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        int currentYear = calendar.get(Calendar.YEAR);
        Date attendanceDate = calendar.getTime();

        AttendanceDay attendanceDay = null;
        List<AttendanceDay> attendanceDays = attendanceMonth.getAttendanceDays();
        if (attendanceDays == null) {
            attendanceDays = new ArrayList<AttendanceDay>();
            attendanceMonth.setYear(currentYear);
            attendanceMonth.setAttendanceDays(attendanceDays);
        }
        for (AttendanceDay existingAttendanceDay : attendanceDays) {
            if (existingAttendanceDay != null) {
                if (existingAttendanceDay.getDate() == currentDay) {
                    attendanceDay = existingAttendanceDay;
                    break;
                }
            }
        }
        if (attendanceDay == null) {
            attendanceDay = new AttendanceDay();
            attendanceDay.setDate(calendar.get(Calendar.DAY_OF_MONTH));
            attendanceDays.add(attendanceDay);
        }
        attendanceDay.setDayName(DateUtil.DAY_FULL_NAME_FORMAT.format(attendanceDate));
        attendanceDay.setDayNumberInWeek(calendar.get(Calendar.DAY_OF_WEEK));
        return attendanceDay;
    }*/

    /**
     * Creates the.
     * 
     * @param attendanceProfile the attendance profile
     * @return the jSON object
     */
    public static JSONObject create(AttendanceProfileDto attendanceProfile) {
        JSONObject jsonObject = null;
        if (attendanceProfile != null) {
            jsonObject = new JSONObject();
            jsonObject.put("ProfileId", attendanceProfile.getProfileId());
            jsonObject.put("ProfileName", attendanceProfile.getProfileName());
            jsonObject.put("EffectiveAcademic", AcademicDataAssembler.create(attendanceProfile.getEffectiveAcademic()));

            jsonObject.put("AttendanceMonths", getAttendanceMonths(attendanceProfile.getAttendanceMonths()));
            jsonObject.put("AssignedSchools", SchoolDataAssembler.create(attendanceProfile.getAssignedSchools()));
            jsonObject.put("AssignedClasses", RegisteredClassDataAssembler.create(attendanceProfile.getAssignedClasses()));
        }
        return jsonObject;
    }

    /**
     * Gets the attendance months.
     * 
     * @param attendanceMonths the attendance months
     * @return the attendance months
     */
    private static JSONArray getAttendanceMonths(List<AttendanceMonth> attendanceMonths) {
        JSONArray attendanceMonthJSON = null;
        if (attendanceMonths != null && !attendanceMonths.isEmpty()) {
            attendanceMonthJSON = new JSONArray();
            for (AttendanceMonth attendanceMonth : attendanceMonths) {
                attendanceMonthJSON.put(getAttendanceMonth(attendanceMonth));
            }
        }
        return attendanceMonthJSON;
    }

    /**
     * Gets the attendance month.
     * 
     * @param attendanceMonth the attendance month
     * @return the attendance month
     */
    private static JSONObject getAttendanceMonth(AttendanceMonth attendanceMonth) {
        JSONObject attendanceMonthJSON = null;
        if (attendanceMonth != null) {
            attendanceMonthJSON = new JSONObject();
            attendanceMonthJSON.put("AttendanceDays", getAttendanceDays(attendanceMonth.getAttendanceDays()));
            attendanceMonthJSON.put("AttendanceMonthId", attendanceMonth.getAttendanceMonthId());
            attendanceMonthJSON.put("MonthName", attendanceMonth.getMonthName());
            attendanceMonthJSON.put("MonthNumber", attendanceMonth.getMonthNumber());
            attendanceMonthJSON.put("Year", attendanceMonth.getYear());
        }
        return attendanceMonthJSON;
    }

    /**
     * Gets the attendance days.
     * 
     * @param attendanceDays the attendance days
     * @return the attendance days
     */
    private static JSONArray getAttendanceDays(List<AttendanceDay> attendanceDays) {
        JSONArray attendanceDaysJSON = null;
        if (attendanceDays != null && !attendanceDays.isEmpty()) {
            attendanceDaysJSON = new JSONArray();
            for (AttendanceDay attendanceDay : attendanceDays) {
                attendanceDaysJSON.put(getAttendanceDay(attendanceDay));
            }
        }
        return attendanceDaysJSON;
    }

    /**
     * Gets the attendance day.
     * 
     * @param attendanceDay the attendance day
     * @return the attendance day
     */
    private static JSONObject getAttendanceDay(AttendanceDay attendanceDay) {
        JSONObject attendanceDayJSON = null;
        if (attendanceDay != null) {
            attendanceDayJSON = new JSONObject();
            attendanceDayJSON.put("Date", attendanceDay.getDate());
            attendanceDayJSON.put("DayNumberInWeek", attendanceDay.getDayNumberInWeek());
            attendanceDayJSON.put("DayName", attendanceDay.getDayName());

            AttendanceCode reference = attendanceDay.getReference();
            if (reference == null) {
                attendanceDayJSON.put("Reference", null);
            } else {
                attendanceDayJSON.put("Reference", reference.getCode());
            }
            AttendanceCode assigned = attendanceDay.getAssigned();
            if (assigned == null) {
                attendanceDayJSON.put("Assigned", null);
            } else {
                attendanceDayJSON.put("Assigned", assigned.getCode());
            }
        }
        return attendanceDayJSON;
    }

    /**
     * Creates the.
     * 
     * @param attendanceProfileData the attendance profile data
     * @return the attendance profile dto
     * @throws ParseException the parse exception
     */
    public static AttendanceProfileDto create(String attendanceProfileData)
            throws ParseException {
        AttendanceProfileDto attendanceProfile = new AttendanceProfileDto();
        JSONObject attendanceProfileJSON = new JSONObject(attendanceProfileData);
        attendanceProfile.setAssignedClasses(createAssignedClasses(
                JsonUtil.getArray(attendanceProfileJSON, "AssignedClasses")));
        attendanceProfile.setAssignedSchools(createAssignedSchools(
                JsonUtil.getArray(attendanceProfileJSON, "AssignedSchools")));
        attendanceProfile.setAttendanceMonths(createAttendanceMonths(
                JsonUtil.getArray(attendanceProfileJSON, "AttendanceMonths")));
        attendanceProfile.setEffectiveAcademic(AcademicDataAssembler.create(
                JsonUtil.getObject(attendanceProfileJSON, "EffectiveAcademic")));
        attendanceProfile.setProfileId(
                JsonUtil.getInt(attendanceProfileJSON, "AttendanceProfileId"));
        attendanceProfile.setProfileName(
                JsonUtil.getString(attendanceProfileJSON, "AttendanceProfileName"));
        return attendanceProfile;
    }

    /**
     * Creates the attendance months.
     * 
     * @param attendanceMonthsArray the attendance months array
     * @return the list
     */
    private static List<AttendanceMonth> createAttendanceMonths(JSONArray attendanceMonthsArray) {
        List<AttendanceMonth> attendanceMonths = null;
        if (attendanceMonthsArray != null && attendanceMonthsArray.length() != 0) {
            attendanceMonths = new ArrayList<AttendanceMonth>();
            for (int index = 0; index < attendanceMonthsArray.length(); index++) {
                attendanceMonths.add(createAttendanceMonth((JSONObject) attendanceMonthsArray.get(index)));
            }
        }
        return attendanceMonths;
    }

    /**
     * Creates the attendance month.
     * 
     * @param attendanceMonthJson the attendance month json
     * @return the attendance month
     */
    private static AttendanceMonth createAttendanceMonth(JSONObject attendanceMonthJson) {
        AttendanceMonth monthAttendance = null;
        if (attendanceMonthJson != null) {
            monthAttendance = new AttendanceMonth();
            monthAttendance.setAttendanceMonthId(JsonUtil.getInt(attendanceMonthJson, "AttendanceMonthId"));
            monthAttendance.setMonthNumber(JsonUtil.getInt(attendanceMonthJson, "MonthNumber"));
            monthAttendance.setYear(JsonUtil.getInt(attendanceMonthJson, "Year"));
            monthAttendance.setAttendanceDays(createAttendanceDays(
                    JsonUtil.getArray(attendanceMonthJson, "AttendanceDays")));
        }
        return monthAttendance;
    }

    /**
     * Creates the attendance days.
     * 
     * @param attendanceDaysJson the attendance days json
     * @return the list
     */
    private static List<AttendanceDay> createAttendanceDays(JSONArray attendanceDaysJson) {
        List<AttendanceDay> attendanceDays = null;

        if (attendanceDaysJson != null && attendanceDaysJson.length() != 0) {
            attendanceDays = new ArrayList<AttendanceDay>();
            for (int index = 0; index < attendanceDaysJson.length(); index++) {
                attendanceDays.add(createAttendanceDay((JSONObject) attendanceDaysJson.get(index)));
            }
        }
        return attendanceDays;
    }

    /**
     * Creates the attendance day.
     * 
     * @param attendanceDayJson the attendance day json
     * @return the attendance day
     */
    private static AttendanceDay createAttendanceDay(JSONObject attendanceDayJson) {
        AttendanceDay attendanceDay = null;
        if (attendanceDayJson != null) {
            attendanceDay = new AttendanceDay();
            attendanceDay.setDate(JsonUtil.getInt(attendanceDayJson, "Date"));
            attendanceDay.setReference(AttendanceCode.get(JsonUtil.getString(
                    attendanceDayJson, "Reference")));
        }
        return attendanceDay;
    }

    /**
     * Creates the assigned schools.
     * 
     * @param jsonArray the json array
     * @return the list
     */
    private static List<SchoolDto> createAssignedSchools(JSONArray jsonArray) {
        List<SchoolDto> schools = null;
        if (jsonArray != null && jsonArray.length() != 0) {
            schools = new ArrayList<SchoolDto>();
            for (int index = 0; index < jsonArray.length(); index++) {
                SchoolDto school = new SchoolDto();
                school.setSchoolId(jsonArray.getInt(index));
                schools.add(school);
            }
        }
        return schools;
    }

    /**
     * Creates the assigned classes.
     * 
     * @param jsonArray the json array
     * @return the list
     */
    private static List<RegisteredClassDto> createAssignedClasses(JSONArray jsonArray) {
        List<RegisteredClassDto> registeredClasses = null;
        if (jsonArray != null && jsonArray.length() != 0) {
            registeredClasses = new ArrayList<RegisteredClassDto>();
            for (int index = 0; index < jsonArray.length(); index++) {
                RegisteredClassDto registeredClass = new RegisteredClassDto();
                registeredClass.setClassId(jsonArray.getInt(index));
                registeredClasses.add(registeredClass);
            }
        }
        return registeredClasses;
    }

}
