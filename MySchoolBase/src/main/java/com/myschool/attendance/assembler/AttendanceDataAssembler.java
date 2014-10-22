package com.myschool.attendance.assembler;

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
import com.myschool.attendance.dto.AttendanceProfileDto;
import com.myschool.attendance.dto.Day;
import com.myschool.attendance.dto.DayAttendance;
import com.myschool.attendance.dto.Month;
import com.myschool.attendance.dto.MonthAttendance;
import com.myschool.branch.assembler.BranchDataAssembler;
import com.myschool.branch.assembler.RegionDataAssembler;
import com.myschool.branch.assembler.StateDataAssembler;
import com.myschool.branch.dto.BranchDto;
import com.myschool.branch.dto.RegionDto;
import com.myschool.branch.dto.StateDto;
import com.myschool.clazz.assembler.RegisteredClassDataAssembler;
import com.myschool.clazz.dto.RegisteredClassDto;
import com.myschool.common.assembler.CollectionDataAssembler;
import com.myschool.common.exception.InvalidDataException;
import com.myschool.common.util.ConversionUtil;
import com.myschool.common.util.DateUtil;
import com.myschool.school.assembler.SchoolDataAssembler;
import com.myschool.school.dto.SchoolDto;

/**
 * The Class AttendanceDataAssembler.
 */
public class AttendanceDataAssembler {

    /**
     * Gets the year attendance.
     * 
     * @param yearAttendance the year attendance
     * @return the year attendance
     */
    private static JSONObject getYearAttendance(List<MonthAttendance> yearAttendance) {
        JSONObject yearAttendaenceJSON = null;
        List<String> monthNames = null;
        if (yearAttendance != null && !yearAttendance.isEmpty()) {
            yearAttendaenceJSON = new JSONObject();
            monthNames = new ArrayList<String>();
            for (MonthAttendance monthAttendance : yearAttendance) {
                int attendanceYear = monthAttendance.getAttendanceYear();
                String monthKey = monthAttendance.getMonth().getShortName() + " " + attendanceYear;
                monthNames.add(monthKey);
                JSONObject monthObject = new JSONObject();
                monthObject.put("MonthAttendanceId", monthAttendance.getMonthAttendanceId());
                monthObject.put("NumberOfAbsents", monthAttendance.getNumberOfAbsents());
                monthObject.put("NumberOfDeclaredHolidays", monthAttendance.getNumberOfDeclaredHolidays());
                monthObject.put("NumberOfGeneralHolidays", monthAttendance.getNumberOfGeneralHolidays());
                monthObject.put("NumberOfHalfDays", monthAttendance.getNumberOfHalfDays());
                monthObject.put("NumberOfLeaves", monthAttendance.getNumberOfLeaves());
                monthObject.put("NumberOfPresents", monthAttendance.getNumberOfPresents());
                monthObject.put("Month", getMonth(monthAttendance.getMonth()));
                monthObject.put("Year", String.valueOf(attendanceYear));

                List<DayAttendance> dayAttendances = monthAttendance.getDayAttendances();
                if (dayAttendances != null && !dayAttendances.isEmpty()) {
                    List<Integer> dayNumbers = new ArrayList<Integer>();
                    for (DayAttendance dayAttendance : dayAttendances) {
                        int dayKey = dayAttendance.getDay().getDate();
                        dayNumbers.add(dayKey);
                        JSONObject dayAttendanceJSON = new JSONObject();
                        dayAttendanceJSON.put("DaysInDifference", dayAttendance.getDaysInDifference());
                        AttendanceCode attendanceCode = dayAttendance.getAttendanceCode();
                        if (attendanceCode == null) {
                            dayAttendanceJSON.put("AttendanceCode", null);
                        } else {
                            dayAttendanceJSON.put("AttendanceCode", attendanceCode.toString());
                        }
                        Day day = dayAttendance.getDay();
                        JSONObject dayJSON = new JSONObject();
                        dayJSON.put("Date", day.getDate());
                        dayJSON.put("DayNumber", day.getDayNumberInWeek());
                        dayJSON.put("DayFullName", day.getDayFullName());
                        dayJSON.put("DayShortName", day.getDayShortName());
                        dayAttendanceJSON.put("Day", dayJSON);
                        monthObject.put(String.valueOf(dayKey), dayAttendanceJSON);
                    }
                    monthObject.put("Dates", CollectionDataAssembler.createJSONArray(dayNumbers));
                }
                yearAttendaenceJSON.put(monthKey, monthObject);
            }
            yearAttendaenceJSON.put("MonthNames", CollectionDataAssembler.createJSONArray(monthNames));
        }
        return yearAttendaenceJSON;
    }

    /**
     * Gets the month.
     * 
     * @param month the month
     * @return the month
     */
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

    /**
     * Gets the year attendance.
     * 
     * @param academicYearStartDate the academic year start date
     * @param academicYearEndDate the academic year end date
     * @param holidays the holidays
     * @param yearAttendance the year attendance
     * @return the year attendance
     */
    public static List<MonthAttendance> getYearAttendance(Date academicYearStartDate,
            Date academicYearEndDate, List<HolidayDto> holidays, List<MonthAttendance> yearAttendance) {

        System.out.println("getYearAttendance(" + academicYearStartDate + ", " + academicYearEndDate + ")");
        MonthAttendance monthAttendance = null;
        int previousMonth = -1;
        int currentMonth = 0;
        Date currentDate = new Date();

        if (academicYearStartDate != null && academicYearEndDate != null && academicYearStartDate.before(academicYearEndDate)) {
            // check if exists, otherwise create a blank entry
            if (yearAttendance == null) {
                yearAttendance = new ArrayList<MonthAttendance>();
            }

            Calendar attendanceDateCalendar = DateUtil.getNewCalendarIgnoreHours();

            // Fill the gaps from first date of month of the academic start month to academic start date
            DayAttendance dayAttendance = null;
            // The below code is to fill up the unaccounted days of start of the month
            // If academic year start date is March 03, then the below code fills March 01, 02 as UNACCOUNTED
            attendanceDateCalendar.setTime(academicYearStartDate);
            int avademicStartYear = attendanceDateCalendar.get(Calendar.YEAR);
            int avademicStartMonth = attendanceDateCalendar.get(Calendar.MONTH);
            int academicStartDate = attendanceDateCalendar.get(Calendar.DAY_OF_MONTH);
            System.out.println("academicYearStartDate " + academicYearStartDate);

            attendanceDateCalendar.setTime(academicYearEndDate);
            //int academicEndDate = attendanceDateCalendar.get(Calendar.DAY_OF_MONTH);
            //int avademicEndYear = attendanceDateCalendar.get(Calendar.YEAR);
            System.out.println("academicYearEndDate " + academicYearEndDate);

            // Fill up starting of the month to effective academic start day
            for (attendanceDateCalendar.set(Calendar.YEAR, avademicStartYear),
                    attendanceDateCalendar.set(Calendar.MONTH, avademicStartMonth),
                    attendanceDateCalendar.set(Calendar.DAY_OF_MONTH, attendanceDateCalendar.getActualMinimum(Calendar.DAY_OF_MONTH));
                    attendanceDateCalendar.get(Calendar.DAY_OF_MONTH) < academicStartDate;
                    attendanceDateCalendar.set(Calendar.DAY_OF_YEAR, attendanceDateCalendar.get(Calendar.DAY_OF_YEAR)+1)) {
                if (monthAttendance == null) {
                    //currentMonth = attendanceDateCalendar.get(Calendar.MONTH) + 1;
                    System.out.println("There are " + (academicStartDate - attendanceDateCalendar.get(Calendar.DAY_OF_MONTH)) + " days in the start of the month.");
                    monthAttendance = updateExistingOrAddMonth(yearAttendance, attendanceDateCalendar);
                    List<DayAttendance> dayAttendances = monthAttendance.getDayAttendances();
                    if (dayAttendances != null && (dayAttendances.size() == attendanceDateCalendar.getActualMaximum(Calendar.DAY_OF_MONTH) || dayAttendances.size() == 31)) {
                        System.out.println("evrything was fine already. break doing this.");
                        break;
                    }
                    //System.out.println("Starting gaps month is " + monthAttendance);
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
            List<DayAttendance> dayAttendances = monthAttendance.getDayAttendances();
            int actualMaximum = attendanceDateCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
            int numberOfGapDays = actualMaximum - dayAttendances.size();
            //System.out.println("There are " + numberOfGapDays + " days in the end of the month.");
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
                    dayAttendance = new DayAttendance();
                    updateUnAccountedDay(dayAttendance);
                    dayAttendances.add(dayAttendance);
                }
            }
        }
        return yearAttendance;
    }

    private static void updateUnAccountedDay(DayAttendance dayAttendance) {
        //DayAttendance dayAttendance = new DayAttendance();
        dayAttendance.setAttendanceCode(AttendanceCode.UNACCOUNTED);
        dayAttendance.setDaysInDifference(DayAttendance.INVALID);
        Day day = new Day();
        day.setDate(DayAttendance.INVALID);
        //day.setDayShortName(dayShortName);
        //day.setDayFullName(dayFullName);
        day.setDayNumberInWeek(DayAttendance.INVALID);
        dayAttendance.setDay(day);
    }

    /**
     * Update existing or add date.
     * 
     * @param monthAttendance the month attendance
     * @param calendar the calendar
     * @param holidays the holidays
     * @param currentDate the current date
     * @return 
     */
    private static DayAttendance updateExistingOrAddDate(
            MonthAttendance monthAttendance, Calendar calendar,
            List<HolidayDto> holidays, Date currentDate) {
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        Date attendanceDate = calendar.getTime();

        Day day = null;
        DayAttendance dayAttendance = null;
        List<DayAttendance> dayAttendances = monthAttendance.getDayAttendances();
        if (dayAttendances == null) {
            dayAttendances = new ArrayList<DayAttendance>();
            monthAttendance.setDayAttendances(dayAttendances);
        }
        for (DayAttendance existingDayAttendance : dayAttendances) {
            if (existingDayAttendance != null) {
                Day existingDay = existingDayAttendance.getDay();
                if (existingDay != null) {
                    int date = existingDay.getDate();
                    if (date == currentDay) {
                        dayAttendance = existingDayAttendance;
                        break;
                    }
                }
            }
        }
        if (dayAttendance == null) {
            dayAttendance = new DayAttendance();
            day = new Day();
            day.setDate(calendar.get(Calendar.DAY_OF_MONTH));
            dayAttendance.setDay(day);
            dayAttendances.add(dayAttendance);
        } else {
            day = dayAttendance.getDay();
            if (day == null) {
                day = new Day();
                day.setDate(calendar.get(Calendar.DAY_OF_MONTH));
                dayAttendance.setDay(day);
            }
        }
        dayAttendance.setAttendanceYear(monthAttendance.getAttendanceYear());
        int numberOfDatesBetween = DateUtil.getNumberOfDatesBetween(currentDate, attendanceDate);
        dayAttendance.setDaysInDifference(numberOfDatesBetween);

        day.setDayFullName(DateUtil.DAY_FULL_NAME_FORMAT.format(attendanceDate));
        day.setDayNumberInWeek(calendar.get(Calendar.DAY_OF_WEEK));
        day.setDayShortName(DateUtil.DAY_SHORT_NAME_FORMAT.format(attendanceDate));

        AttendanceCode attendanceCode = dayAttendance.getAttendanceCode();
        try {
            if (attendanceCode == null) {
                if (isDeclaredHoliday(holidays, calendar)) {
                    dayAttendance.setAttendanceCode(AttendanceCode.DECLARED_HOLIDAY);
                } else if (isGeneralHoliday(day.getDayNumberInWeek())) {
                    dayAttendance.setAttendanceCode(AttendanceCode.GENERAL_HOLIDAY);
                } else {
                    dayAttendance.setAttendanceCode(AttendanceCode.UNASSIGNED);
                }
            } else if (attendanceCode == AttendanceCode.UNACCOUNTED) {
            } else if (attendanceCode == AttendanceCode.UNASSIGNED) {
            } else if (attendanceCode == AttendanceCode.PRESENT) {
            } else if (attendanceCode == AttendanceCode.HALF_DAY_LEAVE) {
            } else if (attendanceCode == AttendanceCode.ON_LEAVE) {
            } else if (attendanceCode == AttendanceCode.DECLARED_HOLIDAY) {
            } else if (attendanceCode == AttendanceCode.GENERAL_HOLIDAY) {
            }
        } catch (InvalidDataException invalidDataException) {
            dayAttendance.setAttendanceCode(AttendanceCode.UNASSIGNED);
        }
        return dayAttendance;
    }

    /**
     * Update existing or add month.
     * 
     * @param yearAttendance the year attendance
     * @param calendar the calendar
     * @return the month attendance
     */
    private static MonthAttendance updateExistingOrAddMonth(
            List<MonthAttendance> yearAttendance, Calendar calendar) {
        Month month = null;
        MonthAttendance monthAttendance = null;

        Date attendanceDate = calendar.getTime();
        int currentMonth = calendar.get(Calendar.MONTH) + 1;
        int currentYear = calendar.get(Calendar.YEAR);

        for (MonthAttendance existingMonthAttendance : yearAttendance) {
            if (existingMonthAttendance != null) {
                Month existingMonth = existingMonthAttendance.getMonth();
                if (existingMonth != null) {
                    int number = existingMonth.getNumber();
                    if (number == currentMonth
                            && currentYear == existingMonthAttendance.getAttendanceYear()) {
                        monthAttendance = existingMonthAttendance;
                        break;
                    }
                }
            }
        }
        if (monthAttendance == null) {
            month = new Month();
            month.setNumber(currentMonth);
            //month.setDays(days);
            monthAttendance = new MonthAttendance();
            monthAttendance.setMonth(month);
            monthAttendance.setAttendanceYear(calendar.get(Calendar.YEAR));
            //yearAttendance.set(monthIndex, monthAttendance);
            yearAttendance.add(monthAttendance);
        } else {
            month = monthAttendance.getMonth();
            if (month == null) {
                month = new Month();
                month.setNumber(currentMonth);
                monthAttendance.setMonth(month);
            }
        }
        month.setFullName(DateUtil.MONTH_FULL_NAME_FORMAT.format(attendanceDate));
        month.setShortName(DateUtil.MONTH_SHORT_NAME_FORMAT.format(attendanceDate));
        return monthAttendance;
    }

    /**
     * Wrap up month attendance.
     * 
     * @param monthAttendance the month attendance
     */
    public static void wrapUpMonthAttendance(MonthAttendance monthAttendance) {
        int numberOfAbsents = 0;
        int numberOfDeclaredHolidays = 0;
        int numberOfGeneralHolidays = 0;
        int numberOfHalfDays = 0;
        int numberOfLeaves = 0;
        int numberOfPresents = 0;
        AttendanceCode attendanceCode = null;

        if (monthAttendance != null) {
            List<DayAttendance> dayAttendances = monthAttendance.getDayAttendances();
            if (dayAttendances != null) {
                for (DayAttendance dayAttendance : dayAttendances) {
                    attendanceCode = dayAttendance.getAttendanceCode();
                    if (attendanceCode == AttendanceCode.GENERAL_HOLIDAY) {
                        numberOfGeneralHolidays++;
                    } else if (attendanceCode == AttendanceCode.DECLARED_HOLIDAY) {
                        numberOfDeclaredHolidays++;
                    } else if (attendanceCode == AttendanceCode.ON_LEAVE) {
                        numberOfLeaves++;
                    } else if (attendanceCode == AttendanceCode.HALF_DAY_LEAVE) {
                        numberOfHalfDays++;
                    } else if (attendanceCode == AttendanceCode.PRESENT) {
                        numberOfPresents++;
                    } else if (attendanceCode == AttendanceCode.ABSENT) {
                        numberOfAbsents++;
                    } else if (attendanceCode == AttendanceCode.UNACCOUNTED) {
                        updateUnAccountedDay(dayAttendance);
                    }
                }
            }
            monthAttendance.setNumberOfAbsents(numberOfAbsents);
            monthAttendance.setNumberOfDeclaredHolidays(numberOfDeclaredHolidays);
            monthAttendance.setNumberOfGeneralHolidays(numberOfGeneralHolidays);
            monthAttendance.setNumberOfHalfDays(numberOfHalfDays);
            monthAttendance.setNumberOfLeaves(numberOfLeaves);
            monthAttendance.setNumberOfPresents(numberOfPresents);
        }
    }

    /**
     * Checks if is general holiday.
     * 
     * @param dayNumber the day number
     * @return true, if is general holiday
     */
    public static boolean isGeneralHoliday(int dayNumber) {
        if (dayNumber == Calendar.SUNDAY) {
            return true;
        }
        return false;
    }

    /**
     * Checks if is declared holiday.
     * 
     * @param holidays the holidays
     * @param calendar the calendar
     * @return true, if is declared holiday
     * @throws InvalidDataException the invalid data exception
     */
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

    /**
     * Gets the assigned.
     * 
     * @return the assigned
     */
    public static JSONArray getAssigned(List<? extends Object> assignments) {
        JSONArray assignedArray = null;
        JSONObject assignedJson = null;
        if (assignments != null && !assignments.isEmpty()) {
            assignedArray = new JSONArray();
            for (Object object : assignments) {
                assignedJson = new JSONObject();
                if (object instanceof StateDto) {
                    StateDto state = (StateDto) object;
                    assignedJson = StateDataAssembler.create(state);
                    assignedJson.put("IDName", "StateId");
                } else if (object instanceof RegionDto) {
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
            jsonObject.put("AttendanceProfileId", attendanceProfile.getAttendanceProfileId());
            jsonObject.put("ProfileName", attendanceProfile.getProfileName());
            jsonObject.put("EffectiveAcademic", AcademicDataAssembler.create(attendanceProfile.getEffectiveAcademic()));
            jsonObject.put("Active", attendanceProfile.isActive());

            jsonObject.put("YearAttendance", getYearAttendance(attendanceProfile.getYearAttendance()));
            jsonObject.put("AssignedStates", getAssigned(attendanceProfile.getStates()));
            jsonObject.put("AssignedRegions", getAssigned(attendanceProfile.getRegions()));
            jsonObject.put("AssignedBranches", getAssigned(attendanceProfile.getBranches()));
            jsonObject.put("AssignedSchools", getAssigned(attendanceProfile.getSchools()));
            jsonObject.put("AssignedClasses", getAssigned(attendanceProfile.getRegisteredClasses()));
        }
        return jsonObject;
    }

    /**
     * Creates the.
     * 
     * @param attendanceProfileJson the attendance profile json
     * @return the attendance profile dto
     */
    public static AttendanceProfileDto create(JSONObject attendanceProfileJson) {
        AttendanceProfileDto attendanceProfile = null;
        if (attendanceProfileJson != null) {
            attendanceProfile = new AttendanceProfileDto();
            attendanceProfile.setAttendanceProfileId(attendanceProfileJson.getInt("AttendanceProfileId"));
            attendanceProfile.setProfileName(attendanceProfileJson.getString("ProfileName"));
            attendanceProfile.setActive(attendanceProfileJson.getBoolean("Active"));

            AcademicDto effectiveAcademic = new AcademicDto();
            effectiveAcademic.setAcademicYearName(attendanceProfileJson.getString("EffectiveAcademicYear"));
            attendanceProfile.setEffectiveAcademic(effectiveAcademic);

            attendanceProfile.setYearAttendance(createYearAttendance(attendanceProfileJson.getJSONArray("YearAttendance")));
            attendanceProfile.setStates(getAssigned(attendanceProfileJson.getJSONArray("AssignedStates"), StateDto.class));
            attendanceProfile.setRegions(getAssigned(attendanceProfileJson.getJSONArray("AssignedRegions"), RegionDto.class));
            attendanceProfile.setBranches(getAssigned(attendanceProfileJson.getJSONArray("AssignedBranches"), BranchDto.class));
            attendanceProfile.setSchools(getAssigned(attendanceProfileJson.getJSONArray("AssignedSchools"), SchoolDto.class));
            attendanceProfile.setRegisteredClasses(getAssigned(attendanceProfileJson.getJSONArray("AssignedClasses"), RegisteredClassDto.class));
        }
        return attendanceProfile;
    }

    /**
     * Gets the assigned.
     * 
     * @param <T> the generic type
     * @param jsonArray the json array
     * @param assignmentType the assignment type
     * @return the assigned
     */
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
                        } else if (assignment instanceof StateDto) {
                            ((StateDto) assignment).setStateId(Integer.parseInt(idValue));
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
            exception.printStackTrace();
        }
        return assignments;
    }

    /**
     * Creates the year attendance.
     * 
     * @param monthAttendanceArray the month attendance array
     * @return the list
     */
    private static List<MonthAttendance> createYearAttendance(JSONArray monthAttendanceArray) {
        List<MonthAttendance> monthAttendances = null;

        if (monthAttendanceArray != null && monthAttendanceArray.length() != 0) {
            monthAttendances = new ArrayList<MonthAttendance>();
            for (int index = 0; index < monthAttendanceArray.length(); index++) {
                monthAttendances.add(createMonthAttendance((JSONObject) monthAttendanceArray.get(index)));
            }
        }
        return monthAttendances;
    }

    /**
     * Creates the month attendance.
     * 
     * @param monthAttendanceJsonObject the month attendance json object
     * @return the month attendance
     */
    private static MonthAttendance createMonthAttendance(JSONObject monthAttendanceJsonObject) {
        JSONObject monthDataJsonObject = null;
        JSONArray daysAttendanceJsonArray = null;


        Month month = null;
        MonthAttendance monthAttendance = null;

        if (monthAttendanceJsonObject != null) {
            monthAttendance = new MonthAttendance();
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

    /**
     * Creates the days attendance.
     * 
     * @param daysAttendanceJsonArray the days attendance json array
     * @return the list
     */
    private static List<DayAttendance> createDaysAttendance(
            JSONArray daysAttendanceJsonArray) {
        List<DayAttendance> dayAttendances = null;

        if (daysAttendanceJsonArray.length() != 0) {
            dayAttendances = new ArrayList<DayAttendance>();
            for (int jindex = 0; jindex < daysAttendanceJsonArray.length(); jindex++) {
                dayAttendances.add(createDayAttendance((JSONObject) daysAttendanceJsonArray.get(jindex)));
            }
        }
        return dayAttendances;
    }

    /**
     * Creates the day attendance.
     * 
     * @param dayAttendanceJsonObject the day attendance json object
     * @return the day attendance
     */
    private static DayAttendance createDayAttendance(JSONObject dayAttendanceJsonObject) {
        Day day = null;
        DayAttendance dayAttendance = null;

        if (dayAttendanceJsonObject != null) {
            day = new Day();
            dayAttendance = new DayAttendance();
            day.setDate(dayAttendanceJsonObject.getInt("Date"));
            dayAttendance.setDay(day);
            dayAttendance.setAttendanceCode(AttendanceCode.getByName(dayAttendanceJsonObject.getString("AttendanceCode")));
        }
        return dayAttendance;
    }

}
