package com.myschool.attendance.domain;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.attendance.dao.AttendanceDao;
import com.myschool.attendance.dto.AttendanceCodeDto;
import com.myschool.common.exception.DaoException;
import com.quasar.core.exception.DataException;

/**
 * The Class AttendanceManager.
 */
@Component
public class AttendanceManager {

    /** The Constant LOGGER. */
    //private static final Logger LOGGER = Logger.getLogger(AttendanceManager.class);

    /** The attendance dao. */
    @Autowired
    private AttendanceDao attendanceDao;

    /**
     * Gets the reference attendance.
     *
     * @param attendanceCriteria the attendance criteria
     * @return the reference attendance
     * @throws DataException the data exception
     *//*
    public AttendanceMonth getReferenceAttendance(
            AttendanceCriteria attendanceCriteria) throws DataException {

        AttendanceMonth monthAttendance = null;
        //ReferenceAttendanceDto referenceAttendance = null;

        try {
            int month = attendanceCriteria.getMonth();
            int year = attendanceCriteria.getYear();
            int classId = attendanceCriteria.getClassId();

            //referenceAttendance = attendanceDao.getReferenceAttendance(classId, year, month);
            List<HolidayDto> holidays = holidayDao.getAll();

            if (referenceAttendance == null) {
                monthAttendance = AttendanceAssembler.getMonthAttendance(DateUtil.getNewCalendarIgnoreHours(), holidays, month, year);
            } else {
                monthAttendance = AttendanceAssembler.getMonthAttendance(referenceAttendance, holidays);
            }
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        } catch (ValidationException validationException) {
            throw new DataException(validationException.getMessage(), validationException);
        }
        return monthAttendance;
    }

    *//**
     * Gets the student attendances.
     *
     * @param classId the class id
     * @param attendanceCriteria the attendance criteria
     * @return the student attendances
     * @throws DataException the data exception
     *//*
    public List<StudentAttendanceDto> getStudentAttendances(int classId,
            AttendanceCriteria attendanceCriteria) throws DataException {

        AttendanceDto attendance = null;
        StudentAttendanceDto studentAttendance = null;
        List<StudentAttendanceDto> studentAttendances = null;

        try {
            List<StudentDto> students = studentDao.getCurrentAyStudents(classId);
            if (students != null && !students.isEmpty()) {
                studentAttendances = new ArrayList<StudentAttendanceDto>();
                
                int month = attendanceCriteria.getMonth();
                int year = attendanceCriteria.getYear();
                
                //List<HolidayDto> holidays = holidayDao.getAll();
                for (StudentDto student : students) {
                    if (student != null) {
                        attendance = attendanceDao.getStudentAttendance(student.getStudentId(), year, month);
                        if (attendance == null) {
                            attendance = AttendanceAssembler.getMonthAttendance(DateUtil.getNewCalendarIgnoreHours(), holidays, month, year);
                        } else {
                            AttendanceAssembler.updateHolidays((MonthAttendance) attendance, holidays, month, year);
                        }
                        studentAttendance = new StudentAttendanceDto();
                        studentAttendance.setStudent(student);
                        studentAttendance.setAttendance(attendance);
                        studentAttendances.add(studentAttendance);
                    }
                }
            }
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        } catch (ValidationException validationException) {
            throw new DataException(validationException.getMessage(), validationException);
        }
        return studentAttendances;
    }

    *//**
     * Update.
     *
     * @param referenceAttendance the reference attendance
     * @param studentsAttendance the students attendance
     * @return true, if successful
     * @throws DataException the data exception
     *//*
    public boolean update(ReferenceAttendanceDto referenceAttendance,
            List<StudentAttendanceDto> studentsAttendance) throws DataException {
        // Implementation in progress
        boolean updated = false;
        boolean refAttendanceFound = false;
        boolean refAttendanceUpdated = false;
        try {
            if (referenceAttendance != null) {
                int month = referenceAttendance.getMonth();
                int year = referenceAttendance.getYear();
                int classId = referenceAttendance.getRegisteredClass().getClassId();
                List<AttendanceDay> dayAttendances = referenceAttendance.getDayAttendances();
                if (dayAttendances != null) {
                    for (AttendanceDay dayAttendance : dayAttendances) {
                        if (dayAttendance != null && dayAttendance.isPresent()) {
                            refAttendanceFound = true;
                            break;
                        }
                    }
                    if (refAttendanceFound) {
                        ReferenceAttendanceDto existingReferenceAttendance = attendanceDao.getReferenceAttendance(classId, year, month);
                        if (existingReferenceAttendance == null) {
                            // There is no attendance exists for the given month. Create it now.
                            int referenceAttendanceId = attendanceDao.createReferenceAttendance(referenceAttendance);
                            if (referenceAttendanceId > 0) {
                                refAttendanceUpdated = true;
                            }
                        } else {
                            // Reference attendance is already found for this month. Update the given reference.
                            refAttendanceUpdated = attendanceDao.updateReferenceAttendance(
                                    existingReferenceAttendance.getReferenceAttendanceId(), referenceAttendance);
                        }
                    } else {
                        throw new DataException("These are no attendances to save.");
                    }
                    if (refAttendanceUpdated) {
                        // Reference attendance is updated. Now update the students attendance.
                        if (studentsAttendance != null && !studentsAttendance.isEmpty()) {
                            for (StudentAttendanceDto studentAttendance : studentsAttendance) {
                                if (studentAttendance != null) {
                                    boolean studentAttendanceUpdated = false;
                                    int studentId = studentAttendance.getStudent().getStudentId();
                                    AttendanceMonth existingStudentAttendance = (AttendanceMonth) attendanceDao.getStudentAttendance(studentId, year, month);
                                    if (existingStudentAttendance == null) {
                                        // The student attendance is not created for this month. Create a new record.
                                        int studentAttendanceId = attendanceDao.createStudentAttendance(referenceAttendance, studentAttendance);
                                        if (studentAttendanceId > 0) {
                                            studentAttendanceUpdated = true;
                                        }
                                    } else {
                                        // Student attendance is already found. Update the existing student attendance.
                                        studentAttendanceUpdated = attendanceDao.updateStudentAttendance(
                                                existingStudentAttendance.getMonthAttendanceId(), referenceAttendance, studentAttendance);
                                    }
                                    if (!studentAttendanceUpdated) {
                                        LOGGER.error("Unable to update the attendance for the student with id " + studentId
                                                + ". Please contact the system administrator.");
                                    }
                                }
                            }
                            updated = true;
                        }
                    }
                }
            }
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return updated;
    }*/

    public List<AttendanceCodeDto> getAttendanceCodes(String type) throws DataException {
        List<AttendanceCodeDto> attendanceCodeDtos;
        try {
            attendanceCodeDtos = null;
            if (type == null) {
                attendanceCodeDtos = attendanceDao.getAttendanceCodes();
            } else {
                if (type.equals("REFERRED")) {
                    attendanceCodeDtos = attendanceDao.getReferredAttendanceCodes();
                } else if (type.equals("ASSIGNED")) {
                    attendanceCodeDtos = attendanceDao.getAssignedAttendanceCodes();
                } else {
                    throw new DataException("Invalid attendance code type: " + type);
                }
            }
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return attendanceCodeDtos;
    }

}
