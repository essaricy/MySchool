package com.myschool.attendance.dao;

import java.util.List;

import com.myschool.attendance.dto.DayAttendance;
import com.myschool.attendance.dto.MonthAttendance;
import com.myschool.attendance.dto.ReferenceAttendanceDto;
import com.myschool.attendance.dto.StudentAttendanceDto;

/**
 * The Class AttendanceDaoSql.
 */
public class AttendanceDaoSql {

    /** The Constant COLUMN_VALUE. */
    private static final String COLUMN_VALUE = "COLUMN_VALUE";

    /** The Constant COLUMN_NAME. */
    private static final String COLUMN_NAME = "COLUMN_NAME";

    /** The Constant COLUMN_NAME_AND_VALUE. */
    private static final String COLUMN_NAME_AND_VALUE = "COLUMN_NAME_AND_VALUE";

    /** The Constant DAY. */
    private static final String DAY = "DAY_";

    /**
     * Builds the reference attendance query.
     *
     * @return the string
     */
    public static String buildReferenceAttendanceQuery() {
        StringBuffer queryBuffer = new StringBuffer();
        queryBuffer.append("SELECT * FROM REF_ATTENDANCE WHERE YEAR = ? AND MONTH = ? AND CLASS_ID = ?");
        return queryBuffer.toString();
    }

    /**
     * Builds the reference attendance insert query.
     *
     * @param referenceAttendance the reference attendance
     * @return the string
     */
    public static String buildReferenceAttendanceInsertQuery(
            ReferenceAttendanceDto referenceAttendance) {
        StringBuffer queryBuffer = new StringBuffer();
        queryBuffer.append("INSERT INTO REF_ATTENDANCE ( ");
        queryBuffer.append("ATTENDANCE_ID, YEAR, MONTH, CLASS_ID, ");
        queryBuffer.append(getAttendanceDayValuesQueryPart(COLUMN_NAME, referenceAttendance.getDayAttendances()));
        queryBuffer.append(" ) VALUES ( ?, ");
        queryBuffer.append(referenceAttendance.getYear()).append(", ");
        queryBuffer.append(referenceAttendance.getMonth()).append(", ");
        queryBuffer.append(referenceAttendance.getRegisteredClass().getClassId()).append(", ");
        queryBuffer.append(getAttendanceDayValuesQueryPart(COLUMN_VALUE, referenceAttendance.getDayAttendances())).append(") ");
        return queryBuffer.toString();
    }

    /**
     * Gets the attendance day values query part.
     *
     * @param appendWhat the append what
     * @param dayAttendances the day attendances
     * @return the attendance day values query part
     */
    private static String getAttendanceDayValuesQueryPart(String appendWhat,
            List<DayAttendance> dayAttendances) {
        DayAttendance dayAttendance = null;
        StringBuffer queryBuffer = new StringBuffer();

        if (appendWhat != null && dayAttendances != null && !dayAttendances.isEmpty()) {
            for (int index = 0; index < dayAttendances.size(); index++) {
                dayAttendance = dayAttendances.get(index);
                if (dayAttendance != null) {
                    if (appendWhat.equals(COLUMN_NAME)) {
                        queryBuffer.append(DAY).append(index + 1);
                    } else if (appendWhat.equals(COLUMN_VALUE)) {
                        //queryBuffer.append(DatabaseUtil.getNullableStringValue(ConversionUtil.toYN(dayAttendance.isPresent())));
                    } else if (appendWhat.equals(COLUMN_NAME_AND_VALUE)) {
                        queryBuffer.append(DAY).append(index + 1).append(" = ");
                        //queryBuffer.append(DatabaseUtil.getNullableStringValue(ConversionUtil.toYN(dayAttendance.isPresent())));
                    }
                    if (index != dayAttendances.size() -1) {
                        queryBuffer.append(", ");
                    }
                }
            }
        }
        return queryBuffer.toString();
    }

    /**
     * Builds the reference attendance update query.
     *
     * @param referenceAttendanceId the reference attendance id
     * @param referenceAttendance the reference attendance
     * @return the string
     */
    public static String buildReferenceAttendanceUpdateQuery(
            int referenceAttendanceId,
            ReferenceAttendanceDto referenceAttendance) {
        StringBuffer queryBuffer = new StringBuffer();
        queryBuffer.append("UPDATE REF_ATTENDANCE  ");
        queryBuffer.append("SET YEAR = ").append(referenceAttendance.getYear());
        queryBuffer.append(", MONTH = ").append(referenceAttendance.getMonth());
        queryBuffer.append(", CLASS_ID = ").append(referenceAttendance.getRegisteredClass().getClassId()).append(", ");
        queryBuffer.append(getAttendanceDayValuesQueryPart(COLUMN_NAME_AND_VALUE, referenceAttendance.getDayAttendances()));
        queryBuffer.append(" WHERE ATTENDANCE_ID = ").append(referenceAttendanceId);
        return queryBuffer.toString();
    }

    /**
     * Builds the student attendance query.
     *
     * @return the string
     */
    public static String buildStudentAttendanceQuery() {
        StringBuffer queryBuffer = new StringBuffer();
        queryBuffer.append("SELECT * FROM STUDENT_ATTENDANCE WHERE YEAR = ? AND MONTH = ? AND STUDENT_ID = ?");
        return queryBuffer.toString();
    }

    /**
     * Builds the student attendance insert query.
     *
     * @param referenceAttendance the reference attendance
     * @param studentAttendance the student attendance
     * @return the string
     */
    public static String buildStudentAttendanceInsertQuery(
            ReferenceAttendanceDto referenceAttendance,
            StudentAttendanceDto studentAttendance) {
        StringBuffer queryBuffer = new StringBuffer();
        MonthAttendance monthAttendance = (MonthAttendance) studentAttendance.getAttendance();
        queryBuffer.append("INSERT INTO STUDENT_ATTENDANCE ( ");
        queryBuffer.append("ATTENDANCE_ID, YEAR, MONTH, STUDENT_ID, ");
        queryBuffer.append(getAttendanceDayValuesQueryPart(COLUMN_NAME, referenceAttendance.getDayAttendances()));
        queryBuffer.append(" ) VALUES ( ?, ");
        queryBuffer.append(referenceAttendance.getYear()).append(", ");
        queryBuffer.append(referenceAttendance.getMonth()).append(", ");
        queryBuffer.append(studentAttendance.getStudent().getStudentId()).append(", ");
        queryBuffer.append(getAttendanceDayValuesQueryPart(COLUMN_VALUE, monthAttendance.getDayAttendances())).append(") ");
        return queryBuffer.toString();
    }

    /**
     * Builds the student attendance update query.
     *
     * @param monthAttendanceId the month attendance id
     * @param referenceAttendance the reference attendance
     * @param studentAttendance the student attendance
     * @return the string
     */
    public static String buildStudentAttendanceUpdateQuery(
            int monthAttendanceId, ReferenceAttendanceDto referenceAttendance,
            StudentAttendanceDto studentAttendance) {
        StringBuffer queryBuffer = new StringBuffer();
        MonthAttendance monthAttendance = (MonthAttendance) studentAttendance.getAttendance();
        queryBuffer.append("UPDATE STUDENT_ATTENDANCE  ");
        queryBuffer.append("SET ");
        /*queryBuffer.append("YEAR = ").append(referenceAttendance.getYear());
        queryBuffer.append(", MONTH = ").append(referenceAttendance.getMonth());
        queryBuffer.append(", STUDENT_ID = ").append(studentAttendance.getStudent().getStudentId()).append(", ");*/
        queryBuffer.append(getAttendanceDayValuesQueryPart(COLUMN_NAME_AND_VALUE, monthAttendance.getDayAttendances()));
        queryBuffer.append(" WHERE ATTENDANCE_ID = ").append(monthAttendanceId);
        return queryBuffer.toString();
    }


}
