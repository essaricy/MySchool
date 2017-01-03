package com.myschool.attendance.validator;

import java.text.MessageFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.academic.dao.AcademicDao;
import com.myschool.academic.dto.AcademicDto;
import com.myschool.attendance.assembler.AttendanceProfileDataAssembler;
import com.myschool.attendance.dao.AttendanceDao;
import com.myschool.attendance.domain.AttendanceProfileManager;
import com.myschool.attendance.dto.AttendanceCode;
import com.myschool.attendance.dto.AttendanceCodeDto;
import com.myschool.attendance.dto.AttendanceDay;
import com.myschool.attendance.dto.AttendanceMonth;
import com.myschool.attendance.dto.AttendanceProfileDto;
import com.myschool.branch.dto.BranchDto;
import com.myschool.branch.dto.DivisionDto;
import com.myschool.clazz.dao.RegisteredClassDao;
import com.myschool.clazz.dto.ClassDto;
import com.myschool.clazz.dto.MediumDto;
import com.myschool.clazz.dto.RegisteredClassDto;
import com.myschool.clazz.dto.SectionDto;
import com.myschool.common.exception.DaoException;
import com.myschool.common.exception.ValidationException;
import com.myschool.common.validator.AbstractValidator;
import com.myschool.common.validator.DataTypeValidator;
import com.myschool.school.dao.SchoolDao;
import com.myschool.school.dto.SchoolDto;
import com.quasar.core.exception.DataException;
import com.quasar.core.exception.InvalidDataException;
import com.quasar.core.util.ConversionUtil;
import com.quasar.core.util.DateUtil;

/**
 * The Class AttendanceProfileValidator.
 */
@Component
public class AttendanceProfileValidator extends AbstractValidator<AttendanceProfileDto> {

    /** The Constant ERROR_DOESNT_EXIST. */
    private static final String ERROR_DOESNT_EXIST = "PROFILE ERROR: {0} does not exist";
    
    /** The Constant ERROR_MONTHS_MISMATCH. */
    private static final String ERROR_MONTHS_MISMATCH = "PROFILE ERROR: There are {0} months in the academic year and profile attendance has {1} months";
    
    /** The Constant ERROR_DAYS_MISMATCH. */
    private static final String ERROR_DAYS_MISMATCH = "PROFILE ERROR: Number of attendance days does not match with the number of days in month {0}";
    
    /** The Constant ERROR_INVALID_CODE. */
    private static final String ERROR_INVALID_CODE = "PROFILE ERROR: Invalid attendance code ({0}) for Day {1} of {2}";
    
    /** The Constant ERROR_DAY_OUTOF_AY. */
    private static final String ERROR_DAY_OUTOF_AY = "PROFILE ERROR: Day {0} of {1} is not in academic year and cant assign attendance code";
    
    /** The Constant ERROR_INVALID_DATE. */
    private static final String ERROR_INVALID_DATE = "PROFILE ERROR: Invalid date {0} of {1} and cant assign attendance code";
    
    /** The Constant ERROR_SCHOOL_DOESNT_EXIST. */
    private static final String ERROR_SCHOOL_DOESNT_EXIST = "PROFILE ERROR: Assigned School does not exist";
    
    /** The Constant ERROR_CLASS_DOESNT_EXIST. */
    private static final String ERROR_CLASS_DOESNT_EXIST = "PROFILE ERROR: Assigned Class does not exist";

    /** The academic dao. */
    @Autowired
    private AcademicDao academicDao;

    /** The school dao. */
    @Autowired
    private SchoolDao schoolDao;

    /** The registered class dao. */
    @Autowired
    private RegisteredClassDao registeredClassDao;

    /** The attendance dao. */
    @Autowired
    private AttendanceDao attendanceDao;

    /** The attendance profile manager. */
    @Autowired
    private AttendanceProfileManager attendanceProfileManager;

    /**
     * Do validate.
     * 
     * @param attendanceProfile the attendance profile
     * @throws ValidationException the validation exception
     */
    public void doValidate(AttendanceProfileDto attendanceProfile) throws ValidationException {
        try {
            if (attendanceProfile == null) {
                throw new ValidationException(MessageFormat.format(ERROR_DOESNT_EXIST, "Attendance Profile"));
            }
            // Profile name is mandatory
            String profileName = attendanceProfile.getProfileName();
            validate(profileName, "Attendance Profile Name", DataTypeValidator.ANY_CHARACTER, true);
            // Current academic must be setup
            AcademicDto currentAcademic = academicDao.getCurrentAcademic();
            if (currentAcademic == null) {
                throw new ValidationException("Current Academic is not setup.");
            }
            // Effective academic must be given
            AcademicDto effectiveAcademic = attendanceProfile.getEffectiveAcademic();
            if (effectiveAcademic == null) {
                throw new ValidationException(MessageFormat.format(ERROR_DOESNT_EXIST, "Effective Academic"));
            }
            String academicYearName = effectiveAcademic.getAcademicYearName();
            validate(academicYearName, "Effective Academic", DataTypeValidator.ANY_CHARACTER, true);
            // Effective academic must exist
            effectiveAcademic = academicDao.get(academicYearName);
            if (effectiveAcademic == null) {
                throw new ValidationException(MessageFormat.format(ERROR_DOESNT_EXIST, "Academic Year (" + academicYearName + ")"));
            }
            attendanceProfile.setEffectiveAcademic(effectiveAcademic);
            String effectiveAcademicYearStartDateValue = effectiveAcademic.getAcademicYearStartDate();
            String effectiveAcademicYearEndDateValue = effectiveAcademic.getAcademicYearEndDate();
            Date effectiveAcademicYearStartDate = ConversionUtil.fromApplicationDateToStorageDate(effectiveAcademicYearStartDateValue);
            Date effectiveAcademicYearEndDate = ConversionUtil.fromApplicationDateToStorageDate(effectiveAcademicYearEndDateValue);

            Date currentAcademicYearStartDate = ConversionUtil.fromApplicationDateToStorageDate(currentAcademic.getAcademicYearStartDate());
            //Date currentAcademicEndStartDate = ConversionUtil.fromApplicationDateToStorageDate(currentAcademic.getAcademicYearEndDate());

            // Do not allow creating attendance profiles for the past academic year
            if (effectiveAcademicYearEndDate.before(currentAcademicYearStartDate)) {
                throw new ValidationException("Academic profiles with past academics cannot be created.");
            }
            // Validate each day of the attendance days
            validateAttendanceMonths(attendanceProfile.getAttendanceMonths(), effectiveAcademicYearStartDate, effectiveAcademicYearEndDate);
            validateAssignments(attendanceProfile);
        } catch (DaoException daoException) {
            throw new ValidationException(daoException.getMessage(), daoException);
        } catch (DataException dataException) {
            throw new ValidationException(dataException.getMessage(), dataException);
        }
    }

    /**
     * Validate assignments.
     * 
     * @param attendanceProfile the attendance profile
     * @throws ValidationException the validation exception
     * @throws DaoException the dao exception
     * @throws DataException the data exception
     */
    private void validateAssignments(AttendanceProfileDto attendanceProfile)
            throws ValidationException, DaoException, DataException {
        List<SchoolDto> schools = attendanceProfile.getAssignedSchools();
        List<RegisteredClassDto> registeredClasses = attendanceProfile.getAssignedClasses();
        if ((schools == null || schools.isEmpty()) && (registeredClasses == null || registeredClasses.isEmpty())) {
            throw new ValidationException("Profile must be assigned to at least one School/Class");
        }
        if (schools != null && !schools.isEmpty()) {
            for (SchoolDto school : schools) {
                if (school == null || school.getSchoolId() == 0) {
                    throw new ValidationException(ERROR_SCHOOL_DOESNT_EXIST);
                }
                SchoolDto existingSchool = schoolDao.get(school.getSchoolId());
                if (existingSchool == null) {
                    throw new ValidationException(ERROR_SCHOOL_DOESNT_EXIST);
                }
            }
        }
        if (registeredClasses != null && !registeredClasses.isEmpty()) {
            for (RegisteredClassDto registeredClass : registeredClasses) {
                if (registeredClass == null || registeredClass.getClassId() == 0) {
                    throw new ValidationException(ERROR_CLASS_DOESNT_EXIST);
                }
                RegisteredClassDto existingClass = registeredClassDao.get(registeredClass.getClassId());
                if (existingClass == null) {
                    throw new ValidationException(ERROR_CLASS_DOESNT_EXIST);
                }
            }
        }
        validateAssignmentConflicts(attendanceProfile);
    }

    /**
     * Validate assignment conflicts.
     * 
     * @param attendanceProfile the attendance profile
     * @throws ValidationException the validation exception
     * @throws DataException the data exception
     */
    public void validateAssignmentConflicts(
            AttendanceProfileDto attendanceProfile) throws ValidationException,
            DataException {
        int attendanceProfileId = attendanceProfile.getProfileId();
        AcademicDto effectiveAcademic = attendanceProfile.getEffectiveAcademic();
        List<SchoolDto> assignedSchools = attendanceProfile.getAssignedSchools();
        List<RegisteredClassDto> assignedClasses = attendanceProfile.getAssignedClasses();
        String academicYearName = effectiveAcademic.getAcademicYearName();

        // retrieve only this academic year's attendance profiles to compare
        List<AttendanceProfileDto> allAttendanceProfiles = attendanceProfileManager.getAllInDetail(academicYearName);
        if (allAttendanceProfiles != null && !allAttendanceProfiles.isEmpty()) {
            //int profilesForAnYear = allAttendanceProfiles.size();

            // Check if it conflicts with other academic profiles school assignment.
            for (AttendanceProfileDto existingAttendanceProfile : allAttendanceProfiles) {
                int existingAttendanceProfileId = existingAttendanceProfile.getProfileId();
                if (existingAttendanceProfileId != attendanceProfileId) {
                    checkSchoolConflict(assignedSchools, existingAttendanceProfile);
                }
            }
            // Check if it conflicts with other academic profiles class assignment.
            for (AttendanceProfileDto existingAttendanceProfile : allAttendanceProfiles) {
                int existingAttendanceProfileId = existingAttendanceProfile.getProfileId();
                if (existingAttendanceProfileId != attendanceProfileId) {
                    checkClassConflict(assignedClasses, existingAttendanceProfile);
                }
            }
        }
    }

    private void checkSchoolConflict(List<SchoolDto> assignedSchools,
            AttendanceProfileDto existingAttendanceProfile) throws ValidationException {
        String existingProfileName = existingAttendanceProfile.getProfileName();
        List<SchoolDto> existingAssignedSchools = existingAttendanceProfile.getAssignedSchools();

        if (assignedSchools != null && existingAssignedSchools != null) {
            // Validate if any of the attendance profile is assigned to this school
            for (SchoolDto assignedSchool : assignedSchools) {
                if (assignedSchool != null) {
                    int schoolId = assignedSchool.getSchoolId();
                    for (SchoolDto existingAssignedSchool : existingAssignedSchools) {
                        if (existingAssignedSchool != null) {
                            int existingSchoolId = existingAssignedSchool.getSchoolId();
                            if (schoolId == existingSchoolId) {
                                DivisionDto division = existingAssignedSchool.getDivision();
                                String divisionCode = division.getDivisionCode();
                                BranchDto branch = existingAssignedSchool.getBranch();
                                String branchCode = branch.getBranchCode();
                                String schoolName = existingAssignedSchool.getSchoolName();
                                String conflictedSchoolName = divisionCode + "/" + branchCode + "/" + schoolName;
                                throw new ValidationException("School '" + conflictedSchoolName + "' has already been assigned to profile '" + existingProfileName + "'");
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Check class conflict.
     * 
     * @param assignedClasses the assigned classes
     * @param existingAssignedClasses the existing assigned classes
     * @throws ValidationException the validation exception
     */
    private void checkClassConflict(List<RegisteredClassDto> assignedClasses,
            AttendanceProfileDto existingAttendanceProfile) throws ValidationException {
        String existingProfileName = existingAttendanceProfile.getProfileName();
        List<RegisteredClassDto> existingAssignedClasses = existingAttendanceProfile.getAssignedClasses();

        // Validate if any of the attendance profile is assigned to this class
        if (assignedClasses != null && existingAssignedClasses != null) {
            for (RegisteredClassDto assignedClass : assignedClasses) {
                if (assignedClass != null) {
                    int classId = assignedClass.getClassId();
                    for (RegisteredClassDto existingAssignedClass : existingAssignedClasses) {
                        if (existingAssignedClass != null) {
                            int existingClassId = existingAssignedClass.getClassId();
                            if (classId == existingClassId) {
                                SchoolDto school = existingAssignedClass.getSchool();
                                ClassDto classDto = existingAssignedClass.getClassDto();
                                MediumDto medium = existingAssignedClass.getMedium();
                                SectionDto section = existingAssignedClass.getSection();
                                DivisionDto division = school.getDivision();
                                String divisionCode = division.getDivisionCode();
                                BranchDto branch = school.getBranch();
                                String branchCode = branch.getBranchCode();
                                String schoolName = school.getSchoolName();
                                String className = classDto.getClassName();
                                String mediumName = medium.getDescription();
                                String sectionName = section.getSectionName();

                                String conflictedClassName = divisionCode + "/" + branchCode + "/" + schoolName + "/" + className + "/" + mediumName + "/" + sectionName;
                                throw new ValidationException("Class '" + conflictedClassName + "' has already been assigned to profile '" + existingProfileName + "'");
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Gets the attendance object.
     * 
     * @param <T> the generic type
     * @param number the number
     * @param attendances the attendances
     * @return the attendance object
     */
    private <T> T getAttendanceObject(int number, List<T> attendances) {
        if (attendances != null && !attendances.isEmpty()) {
            for (T attendance : attendances) {
                if (attendance instanceof AttendanceMonth) {
                    AttendanceMonth attendanceMonth = (AttendanceMonth) attendance;
                    if (attendanceMonth != null && number == attendanceMonth.getMonthNumber()) {
                        return attendance;
                    }
                } else if (attendance instanceof AttendanceDay) {
                    AttendanceDay attendanceDay = (AttendanceDay) attendance;
                    if (attendanceDay != null && number == attendanceDay.getDate()) {
                        return attendance;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Validate attendance months.
     * 
     * @param attendanceMonths the attendance months
     * @param academicStartDate the academic start date
     * @param academicEndDate the academic end date
     * @throws ValidationException the validation exception
     * @throws InvalidDataException the invalid data exception
     * @throws DaoException the dao exception
     */
    private void validateAttendanceMonths(
            List<AttendanceMonth> attendanceMonths, Date academicStartDate,
            Date academicEndDate) throws ValidationException,
            InvalidDataException, DaoException {
        AttendanceProfileDataAssembler.debug(attendanceMonths);

        if (attendanceMonths == null) {
            throw new ValidationException("Attendance profile definition is incomplete.");
        } else {
            // The number of months in attendance profile must match with the number of months in the academic year
            int numberOfMonthsInProfile = attendanceMonths.size();
            int numberOfMonthsInAcademic = DateUtil.dateDiffInMonthNumbers(academicStartDate, academicEndDate);
            if (numberOfMonthsInProfile != numberOfMonthsInAcademic) {
                throw new ValidationException(MessageFormat.format(ERROR_MONTHS_MISMATCH, numberOfMonthsInAcademic, numberOfMonthsInProfile));
            }
            // Academic start calendar
            Calendar academicStartCalendar = DateUtil.getNewCalendarIgnoreHours();
            academicStartCalendar.setTime(academicStartDate);
            int academicStartYear = academicStartCalendar.get(Calendar.YEAR);
            int academicStartMonth = academicStartCalendar.get(Calendar.MONTH);
            // Academic End calendar
            Calendar academicEndCalendar = DateUtil.getNewCalendarIgnoreHours();
            academicEndCalendar.setTime(academicEndDate);
            // Rolling calendar
            Calendar rollingCalendar = DateUtil.getNewCalendarIgnoreHours();
            rollingCalendar.set(Calendar.YEAR, academicStartYear);
            rollingCalendar.set(Calendar.MONTH, academicStartMonth);
            rollingCalendar.set(Calendar.DAY_OF_MONTH, academicStartCalendar.getActualMinimum(Calendar.DAY_OF_MONTH));

            List<AttendanceDay> attendanceDays = null;
            List<AttendanceCodeDto> attendanceCodes = attendanceDao.getReferredAttendanceCodes();
            // Iterate till it reaches the last day of the academic year's last month
            for (int monthIndex = 0; monthIndex < numberOfMonthsInProfile; monthIndex++) {
                int currentMonth = rollingCalendar.get(Calendar.MONTH) + 1;
                String monthShortNameYear = DateUtil.MONTH_SHORT_NAME_YEAR_FORMAT.format(rollingCalendar.getTime());

                AttendanceMonth attendanceMonth = getAttendanceObject(currentMonth, attendanceMonths);
                if (attendanceMonth == null) {
                    throw new ValidationException(MessageFormat.format(ERROR_DOESNT_EXIST, "Attendance for " + monthShortNameYear));
                }
                attendanceDays = attendanceMonth.getAttendanceDays();
                if (attendanceDays == null || attendanceDays.isEmpty()) {
                    throw new ValidationException(MessageFormat.format(ERROR_DOESNT_EXIST, "Attendance for " + monthShortNameYear));
                }
                // Validate the number of days in a month to the number of attendance days in the month
                int numberOfDays = attendanceDays.size();
                int maxDaysInMonth = rollingCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
                if (numberOfDays < maxDaysInMonth || numberOfDays > 31) {
                    throw new ValidationException(MessageFormat.format(ERROR_DAYS_MISMATCH, monthShortNameYear));
                }
                rollingCalendar.set(Calendar.YEAR, attendanceMonth.getYear());
                rollingCalendar.set(Calendar.MONTH, attendanceMonth.getMonthNumber()-1);

                //int daysInMonth = (numberOfDays > maxDaysInMonth) ? numberOfDays : maxDaysInMonth;
                for (int dayIndex = 1; dayIndex <= maxDaysInMonth; dayIndex++) {
                    rollingCalendar.set(Calendar.DAY_OF_MONTH, dayIndex);
                    AttendanceDay attendanceDay = getAttendanceObject(dayIndex, attendanceDays);
                    if (rollingCalendar.getTime().before(academicStartCalendar.getTime())) {
                        // Cannot assign reference attendance to the days before the academic year start date
                        if (attendanceDay != null && attendanceDay.getReference() != null) {
                            throw new ValidationException(MessageFormat.format(ERROR_DAY_OUTOF_AY, dayIndex, monthShortNameYear));
                        }
                    } else if (rollingCalendar.getTime().after(academicEndCalendar.getTime())) {
                        // AttendanceCode referenceAttendance = attendanceDay.getReference();
                        // Cannot assign reference attendance to the days after the academic year end date
                        if (attendanceDay != null && attendanceDay.getReference() != null) {
                            throw new ValidationException(MessageFormat.format(ERROR_DAY_OUTOF_AY, dayIndex, monthShortNameYear));
                        }
                    } else {
                        if (attendanceDay == null || attendanceDay.getReference() == null) {
                            throw new ValidationException(MessageFormat.format(ERROR_INVALID_CODE, null, dayIndex, monthShortNameYear));
                        }
                        validateReferenceAttendanceCode(attendanceDay.getReference(), attendanceCodes);
                    }
                }
                // Put nulls from the last day of the month to 31
                for (int dayIndex = maxDaysInMonth + 1; dayIndex <= 31; dayIndex++) {
                    AttendanceDay attendanceDay = getAttendanceObject(dayIndex, attendanceDays);
                    if (attendanceDay != null && attendanceDay.getReference() != null) {
                        throw new ValidationException(MessageFormat.format(ERROR_INVALID_DATE, dayIndex, monthShortNameYear));
                    }
                }
            }
        }
    }

    /**
     * Validate reference attendance code.
     * 
     * @param referenceAttendance the reference attendance
     * @param attendanceCodes the attendance codes
     * @throws ValidationException the validation exception
     */
    private void validateReferenceAttendanceCode(
            AttendanceCode referenceAttendance, List<AttendanceCodeDto> attendanceCodes) throws ValidationException {
        boolean valid = false;
        if (referenceAttendance != null && attendanceCodes != null) {
            for (AttendanceCodeDto attendanceCode : attendanceCodes) {
                if (attendanceCode.isUseInReference()
                        && referenceAttendance.getCode().equals(attendanceCode.getCode())) {
                    valid = true;
                    break;
                }
            }
            if (!valid) {
                throw new ValidationException(MessageFormat.format("Attendance Code {0} cannot be used for reference attendance", referenceAttendance.getCode()));
            }
        }
    }

    /*private void validateAssignedAttendanceCode(
            AttendanceCode assignedAttendance,
            List<AttendanceCodeDto> attendanceCodes) throws ValidationException {
        boolean valid = false;
        if (assignedAttendance != null && attendanceCodes != null) {
            for (AttendanceCodeDto attendanceCode : attendanceCodes) {
                if (attendanceCode.isUseInReference()) {
                    if (assignedAttendance.getCode().equals(attendanceCode.getCode())) {
                        valid = true;
                        break;
                    }
                }
            }
            if (!valid) {
                throw new ValidationException(MessageFormat.format("Attendance Code {0} cannot be used for assigning", assignedAttendance));
            }
        }
    }*/

    /*private void validate(Date academicStartDate, Date academicEndDate,
            Date currentDate, String monthShortNameYear,
            int dayIndex, AttendanceCode attendanceCode)
            throws ValidationException {
        if (currentDate.before(academicStartDate) || currentDate.after(academicEndDate)) {
            if (attendanceCode != null) {
                throw new ValidationException(MessageFormat.format(PROFILE_ERROR_INVALID_CODE, attendanceCode, dayIndex, monthShortNameYear));
            }
        } else {
            if (attendanceCode == null) {
                throw new ValidationException(MessageFormat.format(PROFILE_ERROR_INVALID_CODE, attendanceCode, dayIndex, monthShortNameYear));
            }
        }
    }*/

}
