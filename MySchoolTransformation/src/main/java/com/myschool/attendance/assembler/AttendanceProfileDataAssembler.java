package com.myschool.attendance.assembler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.myschool.academic.assembler.AcademicDataAssembler;
import com.myschool.attendance.dto.AttendanceCode;
import com.myschool.attendance.dto.AttendanceProfileDto;
import com.myschool.attendance.dto.Day;
import com.myschool.attendance.dto.DayAttendance;
import com.myschool.attendance.dto.Month;
import com.myschool.attendance.dto.MonthAttendance;
import com.myschool.branch.dto.BranchDto;
import com.myschool.branch.dto.RegionDto;
import com.myschool.branch.dto.StateDto;
import com.myschool.clazz.dto.RegisteredClassDto;
import com.myschool.school.dto.SchoolDto;

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
        attendanceProfile.setAttendanceProfileId(resultSet.getInt("PROFILE_ID"));
        attendanceProfile.setProfileName(resultSet.getString("PROFILE_NAME"));
        attendanceProfile.setEffectiveAcademic(AcademicDataAssembler.create(resultSet, true));
        return attendanceProfile;
    }

    /**
     * Creates the month attendance.
     * 
     * @param resultSet the result set
     * @return the month attendance
     * @throws SQLException the sQL exception
     */
    public static MonthAttendance createMonthAttendance(ResultSet resultSet) throws SQLException {
        MonthAttendance monthAttendance = new MonthAttendance();
        monthAttendance.setMonthAttendanceId(resultSet.getInt("ATTENDANCE_MONTH_ID"));
        monthAttendance.setAttendanceYear(resultSet.getInt("ATTENDANCE_YEAR"));
        Month month = new Month();
        month.setNumber(resultSet.getInt("MONTH"));
        monthAttendance.setMonth(month);
        monthAttendance.setDayAttendances(createDayAttendances(resultSet));
        AttendanceDataAssembler.wrapUpMonthAttendance(monthAttendance);
        return monthAttendance;
    }

    /**
     * Creates the day attendances.
     * 
     * @param resultSet the result set
     * @return the list
     * @throws SQLException the sQL exception
     */
    private static List<DayAttendance> createDayAttendances(ResultSet resultSet) throws SQLException {
        Day day = null;
        DayAttendance dayAttendance = null;
        int attendanceYear = resultSet.getInt("ATTENDANCE_YEAR");
        List<DayAttendance> dayAttendances = new ArrayList<DayAttendance>();
        for (int index = 1; index <= 31; index++) {
            dayAttendance = new DayAttendance();
            dayAttendance.setAttendanceCode(AttendanceCode.get(resultSet.getString("DAY_" + index)));
            dayAttendance.setAttendanceYear(attendanceYear);
            day = new Day();
            day.setDate(index);
            dayAttendance.setDay(day);
            dayAttendances.add(dayAttendance);
        }
        return dayAttendances;
    }

    /**
     * Debug year attendance.
     * 
     * @param yearAttendance the year attendance
     */
    private static void debugYearAttendance(List<MonthAttendance> yearAttendance) {
        // TODO added for debugging remove later.
        if (yearAttendance == null) {
            System.out.println("Year Attendance is null.");
        } else {
            System.out.println("Number Of months = " + yearAttendance.size());
            for (MonthAttendance monthAttendance : yearAttendance) {
                debugMonthAttendance(monthAttendance);
            }
        }
    }

    public static void debugMonthAttendance(MonthAttendance monthAttendance) {
        StringBuffer printString = new StringBuffer();
        printString.append(monthAttendance.getMonth().getShortName() + " - " + monthAttendance.getAttendanceYear() + "| ");

        List<DayAttendance> dayAttendances = monthAttendance.getDayAttendances();
        for (DayAttendance dayAttendance : dayAttendances) {
            printString.append((dayAttendance.getAttendanceCode().getCode() == null)? "#": dayAttendance.getAttendanceCode().getCode());
        }
        System.out.println(printString.toString());
    }

    /**
     * Debug attendance profile.
     * 
     * @param attendanceProfile the attendance profile
     */
    public static void debugAttendanceProfile(
            AttendanceProfileDto attendanceProfile) {
        System.out.println("###################################################################");
        if (attendanceProfile == null) {
            System.out.println("Attendance Profile is null");
        } else {
            debugYearAttendance(attendanceProfile.getYearAttendance());
            debugAssignment(attendanceProfile.getStates(), "STATES");
            debugAssignment(attendanceProfile.getRegions(), "REGIONS");
            debugAssignment(attendanceProfile.getBranches(), "BRANCHES");
            debugAssignment(attendanceProfile.getSchools(), "SCHOOLS");
            debugAssignment(attendanceProfile.getRegisteredClasses(), "CLASSES");
        }
    }

    /**
     * Debug assignment.
     * 
     * @param assignments the assignments
     * @param text the text
     */
    private static void debugAssignment(List<? extends Object> assignments, String text) {
        if (assignments == null || assignments.isEmpty()) {
            System.out.println("Assign to " + text + " is null/empty.");
        } else {
            StringBuffer buffer = new StringBuffer();
            buffer.append("Assigned to " + text + " IDs are ");
            for (Object assignment : assignments) {
                if (assignment instanceof StateDto) {
                    StateDto assignmentState = (StateDto) assignment;
                    buffer.append(assignmentState.getStateId()).append(", ");
                } else if (assignment instanceof RegionDto) {
                    RegionDto assignmentRegion = (RegionDto) assignment;
                    buffer.append(assignmentRegion.getRegionId()).append(", ");
                } else if (assignment instanceof BranchDto) {
                    BranchDto assignmentBranch = (BranchDto) assignment;
                    buffer.append(assignmentBranch.getBranchId()).append(", ");
                } else if (assignment instanceof SchoolDto) {
                    SchoolDto assignmentSchool = (SchoolDto) assignment;
                    buffer.append(assignmentSchool.getSchoolId()).append(", ");
                } else if (assignment instanceof RegisteredClassDto) {
                    RegisteredClassDto assignmentRegisteredClass = (RegisteredClassDto) assignment;
                    buffer.append(assignmentRegisteredClass.getClassId()).append(", ");
                }
            }
            System.out.println(buffer);
        }
    }

}
