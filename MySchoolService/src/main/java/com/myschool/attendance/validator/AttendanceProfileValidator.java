package com.myschool.attendance.validator;

import java.text.MessageFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.academic.dao.AcademicDao;
import com.myschool.academic.dto.AcademicDto;
import com.myschool.attendance.dao.AttendanceAssignmentsDao;
import com.myschool.attendance.dao.AttendanceProfileDao;
import com.myschool.attendance.dto.AttendanceCode;
import com.myschool.attendance.dto.AttendanceProfileDto;
import com.myschool.attendance.dto.Day;
import com.myschool.attendance.dto.DayAttendance;
import com.myschool.attendance.dto.Month;
import com.myschool.attendance.dto.MonthAttendance;
import com.myschool.branch.dao.BranchDao;
import com.myschool.branch.dao.RegionDao;
import com.myschool.branch.dto.BranchDto;
import com.myschool.branch.dto.RegionDto;
import com.myschool.clazz.dao.RegisteredClassDao;
import com.myschool.clazz.dto.RegisteredClassDto;
import com.myschool.common.exception.DaoException;
import com.myschool.common.exception.InvalidDataException;
import com.myschool.common.exception.ValidationException;
import com.myschool.common.util.ConversionUtil;
import com.myschool.common.util.DateUtil;
import com.myschool.common.validator.AbstractValidator;
import com.myschool.common.validator.DataTypeValidator;
import com.myschool.school.dao.SchoolDao;
import com.myschool.school.dto.SchoolDto;

/**
 * The Class AttendanceProfileValidator.
 */
@Component
public class AttendanceProfileValidator extends AbstractValidator<AttendanceProfileDto> {

    /** The Constant PROFILES_CONFICT_ASSIGNEMNTS. */
    private static final String PROFILES_CONFICT_ASSIGNEMNTS = "PROFILES CONFLICT: Profile ({0}) assignments conflicts with another profile ({1}). Conflicts {2}.";

    /** The Constant PROFILE_ERROR_DEPENDANCY. */
    private static final String PROFILE_ERROR_DEPENDANCY = "PROFILE ERROR: Cannot assign a profile to a {0} without assiging to a {1}.";

    /** The Constant PROFILE_ERROR_MISSING_MONTHS. */
    private static final String PROFILE_ERROR_MISMATCH_MONTHS = "PROFILE ERROR: There are {0} months in the academic year and profile attendance has {1} months.";

    /** The Constant PROFILE_ERROR_MISSING_DAYS. */
    private static final String PROFILE_ERROR_MISSING_DAYS = "PROFILE ERROR: Missing/Excess attendance days for month {0}.";

    /** The Constant PROFILE_ERROR_INVALID_ATTENDANCE_CODE. */
    private static final String PROFILE_ERROR_INVALID_ATTENDANCE_CODE = "PROFILE ERROR: Invalid attendance code ({0}) for Day {1} of {2}";

    /** The Constant PROFILE_ERROR_INVALID_ATTENDANCE_CODE_SUGGEST. */
    private static final String PROFILE_ERROR_INVALID_ATTENDANCE_CODE_SUGGEST = "PROFILE ERROR: Invalid attendance code ({0}) for Day {1} of {2}. {3}";

    /** The Constant PROFILE_ERROR_MISSING. */
    private static final String PROFILE_ERROR_MISSING = "PROFILE ERROR: {0} does not exist.";

    /** The academic dao. */
    @Autowired
    private AcademicDao academicDao;

    /** The region dao. */
    @Autowired
    private RegionDao regionDao;

    /** The branch dao. */
    @Autowired
    private BranchDao branchDao;

    /** The school dao. */
    @Autowired
    private SchoolDao schoolDao;

    /** The registered class dao. */
    @Autowired
    private RegisteredClassDao registeredClassDao;

    /** The attendance profile dao. */
    @Autowired
    private AttendanceProfileDao attendanceProfileDao;

    /** The attendance assignments dao. */
    @Autowired
    private AttendanceAssignmentsDao attendanceAssignmentsDao;

    /**
     * Do validate.
     * 
     * @param attendanceProfile the attendance profile
     * @param strict the strict
     * @param activate the activate
     * @throws ValidationException the validation exception
     */
    public void doValidate(AttendanceProfileDto attendanceProfile) throws ValidationException {
        try {
            System.out.println("validate()");
            if (attendanceProfile == null) {
                throw new ValidationException(MessageFormat.format(PROFILE_ERROR_MISSING, "Attendance Profile"));
            }
            boolean activate = attendanceProfile.isActive();
            System.out.println("activate? " + activate);
            int attendanceProfileId = attendanceProfile.getAttendanceProfileId();
            if (activate && attendanceProfileId == 0) {
                throw new ValidationException(MessageFormat.format(PROFILE_ERROR_MISSING, "Attendance Profile"));
            }
            String profileName = attendanceProfile.getProfileName();
            validate(profileName, "Attendance Profile Name", DataTypeValidator.ANY_CHARACTER, true);
            AcademicDto currentAcademic = academicDao.getCurrentAcademic();
            if (currentAcademic == null) {
                throw new ValidationException("Current Academic is not setup.");
            }
            AcademicDto effectiveAcademic = attendanceProfile.getEffectiveAcademic();
            if (effectiveAcademic == null) {
                throw new ValidationException(MessageFormat.format(PROFILE_ERROR_MISSING, "Effective Academic"));
            }
            String academicYearName = effectiveAcademic.getAcademicYearName();
            validate(academicYearName, "Effective Academic", DataTypeValidator.ANY_CHARACTER, true);
            effectiveAcademic = academicDao.get(academicYearName);
            if (effectiveAcademic == null) {
                throw new ValidationException(MessageFormat.format(PROFILE_ERROR_MISSING, "Academic Year (" + academicYearName + ")"));
            }
            attendanceProfile.setEffectiveAcademic(effectiveAcademic);
            String effectiveAcademicYearStartDateValue = effectiveAcademic.getAcademicYearStartDate();
            String effectiveAcademicYearEndDateValue = effectiveAcademic.getAcademicYearEndDate();
            Date effectiveAcademicYearStartDate = ConversionUtil.fromApplicationDateToStorageDate(effectiveAcademicYearStartDateValue);
            Date effectiveAcademicYearEndDate = ConversionUtil.fromApplicationDateToStorageDate(effectiveAcademicYearEndDateValue);

            Date currentAcademicYearStartDate = ConversionUtil.fromApplicationDateToStorageDate(currentAcademic.getAcademicYearStartDate());
            //Date currentAcademicEndStartDate = ConversionUtil.fromApplicationDateToStorageDate(currentAcademic.getAcademicYearEndDate());

            if (effectiveAcademicYearEndDate.before(currentAcademicYearStartDate)) {
                throw new ValidationException("Academic profiles with past academics cannot be created.");
            }

            validateYearAttendance(attendanceProfile.getYearAttendance(), effectiveAcademicYearStartDate, effectiveAcademicYearEndDate, activate);
            validateAssignments(attendanceProfile, activate);

            if (activate) {
                validateAssignmentConflicts(attendanceProfile);
            }
        } catch (DaoException daoException) {
            throw new ValidationException(daoException.getMessage(), daoException);
        } catch (InvalidDataException invalidDataException) {
            throw new ValidationException(invalidDataException.getMessage(), invalidDataException);
        }
    }

    /**
     * Validate assignment conflicts.
     * 
     * @param attendanceProfile the attendance profile
     * @throws ValidationException the validation exception
     * @throws DaoException the dao exception
     */
    public void validateAssignmentConflicts(
            AttendanceProfileDto attendanceProfile) throws ValidationException,
            DaoException {
        System.out.println("validateAssignmentConflicts()");
        //int attendanceProfileId = attendanceProfile.getAttendanceProfileId();
        String profileName = attendanceProfile.getProfileName();
        AcademicDto effectiveAcademic = attendanceProfile.getEffectiveAcademic();
        //String academicYearName = effectiveAcademic.getAcademicYearName();

        // Check if it conflicts with other academic profiles assignment.
        List<AttendanceProfileDto> allAttendanceProfiles = attendanceProfileDao.getAllExcluding(attendanceProfile);
        if (allAttendanceProfiles != null) {
            int profileForAYear = allAttendanceProfiles.size();
            System.out.println("There are " + profileForAYear + " profiles to compare for academic " + effectiveAcademic.getAcademicYearName());
            for (int index=0; index<allAttendanceProfiles.size(); index++) {
                AttendanceProfileDto existingAttendanceProfile = allAttendanceProfiles.get(index);
                if (existingAttendanceProfile.isActive()) {
                    int existingAttendanceProfileId = existingAttendanceProfile.getAttendanceProfileId();
                    existingAttendanceProfile.setStates(attendanceAssignmentsDao.getAssignedStates(existingAttendanceProfileId));
                    existingAttendanceProfile.setRegions(attendanceAssignmentsDao.getAssignedRegions(existingAttendanceProfileId));
                    existingAttendanceProfile.setBranches(attendanceAssignmentsDao.getAssignedBranches(existingAttendanceProfileId));
                    existingAttendanceProfile.setSchools(attendanceAssignmentsDao.getAssignedSchools(existingAttendanceProfileId));
                    existingAttendanceProfile.setRegisteredClasses(attendanceAssignmentsDao.getAssignedClasses(existingAttendanceProfileId));
                } else {
                    allAttendanceProfiles.remove(index);
                    index--;
                }
            }

            System.out.println("Filtered out self, other academics, past academics, inactive attendance profiles. left " + allAttendanceProfiles.size());
            // If still there are some profile to compare then compare.
            if (!allAttendanceProfiles.isEmpty()) {
                System.out.println("There are some other profiles to validate before activate.");
                /*List<RegionDto> assignedRegions = attendanceAssignmentsDao.getAssignedRegions(attendanceProfileId);
                List<BranchDto> assignedBranches = attendanceAssignmentsDao.getAssignedBranches(attendanceProfileId);
                List<SchoolDto> assignedSchools = attendanceAssignmentsDao.getAssignedSchools(attendanceProfileId);
                List<RegisteredClassDto> assignedClasses = attendanceAssignmentsDao.getAssignedClasses(attendanceProfileId);*/
                List<RegionDto> assignedRegions = attendanceProfile.getRegions();
                List<BranchDto> assignedBranches = attendanceProfile.getBranches();
                List<SchoolDto> assignedSchools = attendanceProfile.getSchools();
                List<RegisteredClassDto> assignedClasses = attendanceProfile.getRegisteredClasses();

                boolean assignedNoRegions = (assignedRegions == null || assignedRegions.isEmpty());
                /*boolean assignedNoBranches = assignedBranches == null || assignedBranches.isEmpty();
                boolean assignedNoSchools = assignedSchools == null || assignedSchools.isEmpty();
                boolean assignedNoClasses = assignedClasses == null || assignedClasses.isEmpty();*/

                List<RegionDto> allRegions = regionDao.getAll();
                List<BranchDto> allBranches = branchDao.getAll();
                List<SchoolDto> allSchools = schoolDao.getAll();
                List<RegisteredClassDto> allRegisteredClasses = registeredClassDao.getAll();

                for (AttendanceProfileDto existingAttendanceProfile : allAttendanceProfiles) {
                    String existingProfileName = existingAttendanceProfile.getProfileName();
                    System.out.println("existingProfileName " + existingProfileName);
                    List<RegionDto> assignedRegionsInOther = existingAttendanceProfile.getRegions();
                    List<BranchDto> assignedBranchesInOther = existingAttendanceProfile.getBranches();
                    List<SchoolDto> assignedSchoolsInOther = existingAttendanceProfile.getSchools();
                    List<RegisteredClassDto> assignedClassesInOther = existingAttendanceProfile.getRegisteredClasses();

                    /*boolean existingNoRegions = assignedRegionsInOther == null || assignedRegionsInOther.isEmpty();
                    boolean existingNoBranches = assignedBranchesInOther == null || assignedBranchesInOther.isEmpty();
                    boolean existingNoSchools = assignedSchoolsInOther == null || assignedSchoolsInOther.isEmpty();
                    boolean existingNoClasses = assignedClassesInOther == null || assignedClassesInOther.isEmpty();*/

                    System.out.println("assignedNoRegions " + assignedNoRegions);

                    if (!assignedNoRegions) {
                    } else {
                        // Check if any of the assigned region is used in any of the assigned regions of the other assigned regions
                        boolean sameAssignmentExists = sameAssignmentExists(assignedRegions,
                                assignedRegionsInOther, allRegions);
                        System.out.println("REGION sameAssignmentExists?" + sameAssignmentExists);
                        if (sameAssignmentExists) {
                            // regions conflict.
                            throw new ValidationException(MessageFormat.format(
                                    PROFILES_CONFICT_ASSIGNEMNTS, profileName, existingProfileName, "REGION"));
                        } else {
                            sameAssignmentExists = sameAssignmentExists(assignedBranches, assignedBranchesInOther,
                                    allBranches);
                            System.out.println("REGION->BRANCH sameAssignmentExists?" + sameAssignmentExists);
                            if (sameAssignmentExists) {
                                // branches conflict.
                                throw new ValidationException(MessageFormat.format(
                                        PROFILES_CONFICT_ASSIGNEMNTS, profileName, existingProfileName, "REGION->BRANCH"));
                            } else {
                                sameAssignmentExists = sameAssignmentExists(assignedSchools,
                                        assignedSchoolsInOther, allSchools);
                                System.out.println("REGION->BRANCH->SCHOOL sameAssignmentExists?" + sameAssignmentExists);
                                if (sameAssignmentExists) {
                                    // schools conflict.
                                    throw new ValidationException(MessageFormat.format(
                                            PROFILES_CONFICT_ASSIGNEMNTS, profileName, existingProfileName, "REGION->BRANCH->SCHOOL"));
                                } else {
                                    sameAssignmentExists = sameAssignmentExists(assignedClasses,
                                            assignedClassesInOther,
                                            allRegisteredClasses);
                                    System.out.println("REGION->BRANCH->SCHOOL->CLASS sameAssignmentExists?" + sameAssignmentExists);
                                    if (sameAssignmentExists) {
                                        // classes conflict.
                                        throw new ValidationException(MessageFormat.format(
                                                PROFILES_CONFICT_ASSIGNEMNTS, profileName, existingProfileName, "REGION->BRANCH->SCHOOL->CLASS"));
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 
     * @param attendanceProfile the attendance profile
     * @param activate 
     * @throws ValidationException the validation exception
     * @throws DaoException the dao exception
     */
    private void validateAssignments(
            AttendanceProfileDto attendanceProfile, boolean activate)
            throws ValidationException, DaoException {
        List<RegionDto> regions = attendanceProfile.getRegions();
        List<BranchDto> branches = attendanceProfile.getBranches();
        List<SchoolDto> schools = attendanceProfile.getSchools();
        List<RegisteredClassDto> registeredClasses = attendanceProfile.getRegisteredClasses();
        System.out.println("validateAssignments() ");

        boolean noRegions = regions == null || regions.isEmpty();
        boolean noBranches = branches == null || branches.isEmpty();
        boolean noSchools = schools == null || schools.isEmpty();
        boolean noClasses = registeredClasses == null || registeredClasses.isEmpty();

        System.out.println("noRegions " + noRegions);
        if (noRegions) {
            // If there are no regions to assign then branches, schools, classes cannot be assigned.
            if (!noBranches) {
                throw new ValidationException(MessageFormat.format(PROFILE_ERROR_DEPENDANCY, "BRANCH", "REGION"));
            }
            if (!noSchools) {
                throw new ValidationException(MessageFormat.format(PROFILE_ERROR_DEPENDANCY, "SCHOOL", "BRANCH"));
            }
            if (!noClasses) {
                throw new ValidationException(MessageFormat.format(PROFILE_ERROR_DEPENDANCY, "CLASS", "SCHOOL"));
            }
            if (activate) {
                //throw new ValidationException(PROFILE_ERROR_USE_ORG_OFF_NO_ASSIGNEMNTS);
            }
        } else {
            System.out.println("noBranches " + noBranches);
            // There are some regions to assign
            if (noBranches) {
                System.out.println("noSchools " + noSchools);
                // If there are no branches to assign then schools, classes cannot be assigned.
                if (!noSchools) {
                    throw new ValidationException(MessageFormat.format(PROFILE_ERROR_DEPENDANCY, "SCHOOL", "BRANCH"));
                }
                System.out.println("noClasses " + noClasses);
                if (!noClasses) {
                    throw new ValidationException(MessageFormat.format(PROFILE_ERROR_DEPENDANCY, "CLASS", "SCHOOL"));
                }
            } else {
                System.out.println("noSchools " + noSchools);
                // There are some branches to assign
                if (noSchools) {
                    System.out.println("noClasses " + noClasses);
                    // If there are no schools to assign then classes cannot be assigned.
                    if (!noClasses) {
                        throw new ValidationException(MessageFormat.format(PROFILE_ERROR_DEPENDANCY, "CLASS", "SCHOOL"));
                    }
                }
            }
        }
        System.out.println("!noRegions " + !noRegions);
        if (!noRegions) {
            for (RegionDto region : regions) {
                System.out.println("region.getRegionId() " + region.getRegionId());
                if (region == null || region.getRegionId() == 0) {
                    throw new ValidationException(MessageFormat.format(PROFILE_ERROR_MISSING, "REGION"));
                }
                RegionDto existingRegion = regionDao.get(region.getRegionId());
                if (existingRegion == null) {
                    throw new ValidationException(MessageFormat.format(PROFILE_ERROR_MISSING, "REGION"));
                }
            }
        }
        System.out.println("!noBranches " + !noBranches);
        if (!noBranches) {
            for (BranchDto branch : branches) {
                System.out.println("branch.getBranchId() " + branch.getBranchId());
                if (branch == null || branch.getBranchId() == 0) {
                    throw new ValidationException(MessageFormat.format(PROFILE_ERROR_MISSING, "BRANCH"));
                }
                BranchDto existingBranch = branchDao.get(branch.getBranchId());
                if (existingBranch == null) {
                    throw new ValidationException(MessageFormat.format(PROFILE_ERROR_MISSING, "BRANCH"));
                }
            }
        }
        System.out.println("!noSchools " + !noSchools);
        if (!noSchools) {
            for (SchoolDto school : schools) {
                System.out.println("school.getSchoolId() " + school.getSchoolId());
                if (school == null || school.getSchoolId() == 0) {
                    throw new ValidationException(MessageFormat.format(PROFILE_ERROR_MISSING, "SCHOOL"));
                }
                SchoolDto existingSchool = schoolDao.get(school.getSchoolId());
                if (existingSchool == null) {
                    throw new ValidationException(MessageFormat.format(PROFILE_ERROR_MISSING, "SCHOOL"));
                }
            }
        }
        System.out.println("!noClasses " + !noClasses);
        if (!noClasses) {
            for (RegisteredClassDto registeredClass : registeredClasses) {
                System.out.println("registeredClass.getClassId() " + registeredClass.getClassId());
                if (registeredClass == null || registeredClass.getClassId() == 0) {
                    throw new ValidationException(MessageFormat.format(PROFILE_ERROR_MISSING, "CLASS"));
                }
                RegisteredClassDto existingClass = registeredClassDao.get(registeredClass.getClassId());
                if (existingClass == null) {
                    throw new ValidationException(MessageFormat.format(PROFILE_ERROR_MISSING, "CLASS"));
                }
            }
        }
    
    }

    /**
     * Validate year attendance.
     * 
     * @param yearAttendance the year attendance
     * @param effectiveAcademicYearStartDate the effective academic year start
     *            date
     * @param effectiveAcademicYearEndDate the effective academic year end date
     * @param strict the strict
     * @throws ValidationException the validation exception
     * @throws InvalidDataException the invalid data exception
     */
    private void validateYearAttendance(List<MonthAttendance> yearAttendance,
            Date effectiveAcademicYearStartDate,
            Date effectiveAcademicYearEndDate, boolean strict) throws ValidationException, InvalidDataException {
        if (strict) {
            if (yearAttendance == null) {
                throw new ValidationException("Attendance profile definition is incomplete.");
            }
        }
        if (yearAttendance != null) {
            int numberOfMonthsInProfile = yearAttendance.size();
            int numberOfMonthsInAcademic = DateUtil.dateDiffInMonthNumbers(effectiveAcademicYearStartDate, effectiveAcademicYearEndDate);
            if (numberOfMonthsInProfile != numberOfMonthsInAcademic) {
                throw new ValidationException(MessageFormat.format(PROFILE_ERROR_MISMATCH_MONTHS, numberOfMonthsInAcademic, numberOfMonthsInProfile));
            }
            Calendar attendanceDateCalendar = DateUtil.getNewCalendarIgnoreHours();
            attendanceDateCalendar.setTime(effectiveAcademicYearStartDate);
            attendanceDateCalendar.set(Calendar.DAY_OF_MONTH, attendanceDateCalendar.getActualMinimum(Calendar.DAY_OF_MONTH));
            Date effectiveAcademicYearStartMonth = attendanceDateCalendar.getTime();
            System.out.println("effectiveAcademicYearStartMonth " + effectiveAcademicYearStartMonth);
            attendanceDateCalendar.setTime(effectiveAcademicYearEndDate);
            attendanceDateCalendar.set(Calendar.DAY_OF_MONTH, attendanceDateCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
            Date effectiveAcademicYearEndMonth = attendanceDateCalendar.getTime();
            System.out.println("effectiveAcademicYearEndMonth " + effectiveAcademicYearEndMonth);

            attendanceDateCalendar.setTime(effectiveAcademicYearStartMonth);
            for (int index = 0; index < numberOfMonthsInProfile; index++) {
                int currentMonth = attendanceDateCalendar.get(Calendar.MONTH) + 1;
                String monthShortNameYear = DateUtil.MONTH_SHORT_NAME_YEAR_FORMAT.format(attendanceDateCalendar.getTime());
                MonthAttendance monthAttendance = getAttendanceObject(currentMonth, yearAttendance);
                if (monthAttendance == null) {
                    throw new ValidationException(MessageFormat.format(PROFILE_ERROR_MISSING, "Attendance for " + monthShortNameYear));
                }
                List<DayAttendance> dayAttendances = monthAttendance.getDayAttendances();
                if (dayAttendances == null || dayAttendances.isEmpty()) {
                    throw new ValidationException(MessageFormat.format(PROFILE_ERROR_MISSING, "Attendance for " + monthShortNameYear));
                }
                int numberOfDays = dayAttendances.size();
                int numberOfDaysInMonth = attendanceDateCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
                if (numberOfDays < numberOfDaysInMonth || numberOfDays > 31) {
                    throw new ValidationException(MessageFormat.format(PROFILE_ERROR_MISSING_DAYS, monthShortNameYear));
                }
                for (int jindex = numberOfDays; jindex < 31; jindex++) {
                    DayAttendance dayAttendance = new DayAttendance();
                    dayAttendance.setAttendanceCode(AttendanceCode.UNACCOUNTED);
                    dayAttendance.setDaysInDifference(DayAttendance.INVALID);
                    dayAttendances.add(dayAttendance);
                }
                for (int dayIndex = 1; dayIndex <= numberOfDaysInMonth; dayIndex++) {
                    DayAttendance dayAttendance = getAttendanceObject(dayIndex, dayAttendances);
                    if (dayAttendance == null) {
                        throw new ValidationException(MessageFormat.format(PROFILE_ERROR_INVALID_ATTENDANCE_CODE, "", dayIndex, monthShortNameYear));
                    }
                    AttendanceCode attendanceCode = dayAttendance.getAttendanceCode();
                    if (attendanceCode == null) {
                        throw new ValidationException(MessageFormat.format(PROFILE_ERROR_INVALID_ATTENDANCE_CODE, "", dayIndex, monthShortNameYear));
                    }
                    Date currentDate = attendanceDateCalendar.getTime();
                    if (currentDate.before(effectiveAcademicYearStartDate) || currentDate.after(effectiveAcademicYearEndDate)) {
                        if (attendanceCode != AttendanceCode.UNACCOUNTED) {
                            throw new ValidationException(MessageFormat.format(PROFILE_ERROR_INVALID_ATTENDANCE_CODE_SUGGEST, attendanceCode, dayIndex, monthShortNameYear, "Must be " + AttendanceCode.UNACCOUNTED));
                        }
                    } else {
                        if (attendanceCode == AttendanceCode.UNACCOUNTED) {
                            throw new ValidationException(MessageFormat.format(PROFILE_ERROR_INVALID_ATTENDANCE_CODE_SUGGEST, attendanceCode, dayIndex, monthShortNameYear, "Must not be " + AttendanceCode.UNACCOUNTED));
                        } else if (attendanceCode == AttendanceCode.UNASSIGNED) {
                            if (strict) {
                                throw new ValidationException(MessageFormat.format(PROFILE_ERROR_INVALID_ATTENDANCE_CODE, attendanceCode, dayIndex, monthShortNameYear));
                            }
                        }
                    }
                    attendanceDateCalendar.set(Calendar.DAY_OF_MONTH, attendanceDateCalendar.get(Calendar.DAY_OF_MONTH)+1);
                }
                // Validate excess days of month.
                for (int jindex = 0; jindex < numberOfDays - numberOfDaysInMonth; jindex++) {
                    int dayIndex = numberOfDaysInMonth + jindex + 1;
                    DayAttendance dayAttendance = getAttendanceObject(dayIndex, dayAttendances);
                    // If it is null then no problem
                    if (dayAttendance != null) {
                        AttendanceCode attendanceCode = dayAttendance.getAttendanceCode();
                        if (attendanceCode != null && attendanceCode != AttendanceCode.UNACCOUNTED) {
                            throw new ValidationException(MessageFormat.format(PROFILE_ERROR_INVALID_ATTENDANCE_CODE_SUGGEST, attendanceCode, dayIndex, monthShortNameYear, "Must be " + AttendanceCode.UNACCOUNTED));
                        }
                        // overwrite to UNACCOUNTED. in case if it was null.
                        dayAttendance.setAttendanceCode(AttendanceCode.UNACCOUNTED);
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
                if (attendance instanceof MonthAttendance) {
                    MonthAttendance monthAttendance = (MonthAttendance) attendance;
                    Month month = monthAttendance.getMonth();
                    if (month != null && number == month.getNumber()) {
                        return attendance;
                    }
                } else if (attendance instanceof DayAttendance) {
                    DayAttendance dayAttendance = (DayAttendance) attendance;
                    Day day = dayAttendance.getDay();
                    if (day != null && number == day.getDate()) {
                        return attendance;
                    }
                }
            }
        }
        return null;
    }

    private <T> boolean sameAssignmentExists(List<T> assignments,
            List<T> assignmentsInExisting, List<T> allAssignable) {
        boolean currentHasAssignments = (assignments != null && !assignments.isEmpty());
        boolean existingHasAssignments = (assignmentsInExisting != null && !assignmentsInExisting.isEmpty());

        if (!currentHasAssignments) {
            return true;
        }
        if (existingHasAssignments) {
            return true;
        }

        boolean existingHasAll = hasExistingAll(assignmentsInExisting, allAssignable);
        System.out.println("existingHasAll? " + existingHasAll);
        // If existing has all the assignments and current has at least one, its a conflict
        if (existingHasAll && !currentHasAssignments) {
            return true;
        }
        for (Object assignment : assignments) {
            for (Object assigned : assignmentsInExisting) {
                if (assignment instanceof RegionDto && assigned instanceof RegionDto) {
                    RegionDto assignmentRegion = (RegionDto) assignment;
                    RegionDto assignedRegion = (RegionDto) assigned;
                    if (assignmentRegion.getRegionId() == assignedRegion.getRegionId()) {
                        return true;
                    }
                } else if (assignment instanceof BranchDto && assigned instanceof BranchDto) {
                    BranchDto assignmentBranch = (BranchDto) assignment;
                    BranchDto assignedBranch = (BranchDto) assigned;
                    if (assignmentBranch.getBranchId() == assignedBranch.getBranchId()) {
                        return true;
                    }
                } else if (assignment instanceof SchoolDto && assigned instanceof SchoolDto) {
                    SchoolDto assignmentSchool = (SchoolDto) assignment;
                    SchoolDto assignedSchool = (SchoolDto) assigned;
                    if (assignmentSchool.getSchoolId() == assignedSchool.getSchoolId()) {
                        return true;
                    }
                } else if (assignment instanceof RegisteredClassDto && assigned instanceof RegisteredClassDto) {
                    RegisteredClassDto assignmentRegisteredClass = (RegisteredClassDto) assignment;
                    RegisteredClassDto assignedRegisteredClass = (RegisteredClassDto) assigned;
                    if (assignmentRegisteredClass.getClassId() == assignedRegisteredClass.getClassId()) {
                        return true;
                    }
                }
            }
        }
        System.out.println("sameAssignmentExists ? false");
        return false;
    }

    private <T> boolean hasExistingAll(List<T> existingAssignments, List<T> allAssignable) {
        boolean existingHasAll = true;
        boolean existingHasAssignments = (existingAssignments != null && !existingAssignments.isEmpty());

        if (existingHasAssignments) {
            if (allAssignable != null && !allAssignable.isEmpty() && existingHasAssignments) {
                for (T assignable : allAssignable) {
                    for (T assignmentInExisting : existingAssignments) {
                        if (assignable instanceof RegionDto && assignmentInExisting instanceof RegionDto) {
                            RegionDto assignableRegion = (RegionDto) assignable;
                            RegionDto existingRegion = (RegionDto) assignmentInExisting;
                            if (existingRegion.getRegionId() !=0 && assignableRegion.getRegionId() != existingRegion.getRegionId()) {
                                existingHasAll = false;
                            }
                        } else if (assignable instanceof BranchDto && assignmentInExisting instanceof BranchDto) {
                            BranchDto assignableBranch = (BranchDto) assignable;
                            BranchDto existingBranch = (BranchDto) assignmentInExisting;
                            if (existingBranch.getBranchId() !=0 && assignableBranch.getBranchId() != existingBranch.getBranchId()) {
                                existingHasAll = false;
                            }
                        } else if (assignable instanceof SchoolDto && assignmentInExisting instanceof SchoolDto) {
                            SchoolDto assignableSchool = (SchoolDto) assignable;
                            SchoolDto existingSchool = (SchoolDto) assignmentInExisting;
                            if (existingSchool.getSchoolId() !=0 && assignableSchool.getSchoolId() != existingSchool.getSchoolId()) {
                                existingHasAll = false;
                            }
                        } else if (assignable instanceof RegisteredClassDto && assignmentInExisting instanceof RegisteredClassDto) {
                            RegisteredClassDto assignableRegisteredClass = (RegisteredClassDto) assignable;
                            RegisteredClassDto existingRegisteredClass = (RegisteredClassDto) assignmentInExisting;
                            if (existingRegisteredClass.getClassId() !=0 && assignableRegisteredClass.getClassId() != existingRegisteredClass.getClassId()) {
                                existingHasAll = false;
                            }
                        }
                        if (!existingHasAll) {
                            break;
                        }
                    }
                }
            }
        } else {
            existingHasAll = false;
        }
        return existingHasAll;
    }
}
