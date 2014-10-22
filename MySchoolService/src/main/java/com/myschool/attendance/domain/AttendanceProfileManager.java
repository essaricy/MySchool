package com.myschool.attendance.domain;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.academic.domain.AcademicManager;
import com.myschool.academic.domain.HolidayManager;
import com.myschool.academic.dto.AcademicDto;
import com.myschool.academic.dto.HolidayDto;
import com.myschool.academic.dto.HolidaySearchCriteria;
import com.myschool.attendance.assembler.AttendanceDataAssembler;
import com.myschool.attendance.dao.AttendanceAssignmentsDao;
import com.myschool.attendance.dao.AttendanceProfileDao;
import com.myschool.attendance.dto.AttendanceProfileDto;
import com.myschool.attendance.dto.MonthAttendance;
import com.myschool.attendance.validator.AttendanceProfileValidator;
import com.myschool.branch.domain.BranchManager;
import com.myschool.branch.domain.RegionManager;
import com.myschool.branch.domain.StateManager;
import com.myschool.branch.dto.BranchDto;
import com.myschool.branch.dto.RegionDto;
import com.myschool.branch.dto.StateDto;
import com.myschool.clazz.domain.RegisteredClassManager;
import com.myschool.clazz.dto.RegisteredClassDto;
import com.myschool.common.exception.DaoException;
import com.myschool.common.exception.DataException;
import com.myschool.common.exception.InvalidDataException;
import com.myschool.common.exception.ValidationException;
import com.myschool.common.util.ConversionUtil;
import com.myschool.school.domain.SchoolManager;
import com.myschool.school.dto.SchoolDto;

/**
 * The Class AttendanceProfileManager.
 */
@Component
public class AttendanceProfileManager {

    /** The Constant ACADEMIC_YEAR_DOES_NOT_EXIST. */
    private static final String ACADEMIC_YEAR_DOES_NOT_EXIST = "Academic Year does not exist.";

    /** The attendance profile dao. */
    @Autowired
    private AttendanceProfileDao attendanceProfileDao;

    /** The attendance assignments dao. */
    @Autowired
    private AttendanceAssignmentsDao attendanceAssignmentsDao;

    /** The academic manager. */
    @Autowired
    private AcademicManager academicManager;

    /** The holiday manager. */
    @Autowired
    private HolidayManager holidayManager;

    /** The attendance profile validator. */
    @Autowired
    private AttendanceProfileValidator attendanceProfileValidator;

    @Autowired
    private StateManager stateManager;

    /** The region manager. */
    @Autowired
    private RegionManager regionManager;

    /** The branch manager. */
    @Autowired
    private BranchManager branchManager;

    /** The school manager. */
    @Autowired
    private SchoolManager schoolManager;

    /** The registered class manager. */
    @Autowired
    private RegisteredClassManager registeredClassManager;

    /**
     * Gets the.
     * 
     * @param attendanceProfileId the attendance profile id
     * @return the attendance profile dto
     * @throws DataException the data exception
     */
    public AttendanceProfileDto get(int attendanceProfileId) throws DataException {
        AttendanceProfileDto attendanceProfile = null;
        try {
            if (attendanceProfileId == 0) {
                throw new DataException("Invalid attendance profile ID.");
            }
            attendanceProfile = attendanceProfileDao.get(attendanceProfileId);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return attendanceProfile;
    }

    /**
     * Gets the.
     * 
     * @param attendanceProfileId the attendance profile id
     * @param academicYearName the academic year name
     * @return the attendance profile dto
     * @throws DataException the data exception
     */
    public AttendanceProfileDto get(int attendanceProfileId,
            String academicYearName) throws DataException {
        AttendanceProfileDto attendanceProfile = null;
        try {
            if (attendanceProfileId != 0) {
                attendanceProfile = attendanceProfileDao.get(attendanceProfileId);
            }
            List<MonthAttendance> yearAttendance = getProfileAttendance(attendanceProfile, academicYearName);
            if (attendanceProfile == null) {
                attendanceProfile = new AttendanceProfileDto();
            } else {
                attendanceProfile.setStates(attendanceAssignmentsDao.getAssignedStates(attendanceProfileId));
                attendanceProfile.setRegions(attendanceAssignmentsDao.getAssignedRegions(attendanceProfileId));
                attendanceProfile.setBranches(attendanceAssignmentsDao.getAssignedBranches(attendanceProfileId));
                attendanceProfile.setSchools(attendanceAssignmentsDao.getAssignedSchools(attendanceProfileId));
                attendanceProfile.setRegisteredClasses(attendanceAssignmentsDao.getAssignedClasses(attendanceProfileId));
            }
            attendanceProfile.setYearAttendance(yearAttendance);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return attendanceProfile;
    }

    /**
     * Gets the profile attendance.
     * 
     * @param attendanceProfile the attendance profile
     * @param academicYearName the academic year name
     * @return the profile attendance
     * @throws DataException the data exception
     * @throws DaoException the dao exception
     */
    private List<MonthAttendance> getProfileAttendance(AttendanceProfileDto attendanceProfile,
            String academicYearName) throws DataException, DaoException {
        List<MonthAttendance> yearAttendance = null;

        if (attendanceProfile == null) {
            AcademicDto academic = academicManager.get(academicYearName);
            if (academic == null) {
                throw new DataException(ACADEMIC_YEAR_DOES_NOT_EXIST);
            } else {
                // prepare attendance
                yearAttendance = getYearAttendance(academic, null);
            }
        } else {
            // get the profile attendances from the database
            // fill empties only
            int attendanceProfileId = attendanceProfile.getAttendanceProfileId();
            AcademicDto effectiveAcademic = attendanceProfile.getEffectiveAcademic();
            yearAttendance = attendanceProfileDao.getProfileAttendance(attendanceProfileId);
            // If there are no attendances found then fill it with empty attendances.
            if (yearAttendance == null) {
                // There is no attendance profile. prepare attendance
                AcademicDto academic = academicManager.get(effectiveAcademic.getAcademicYearName());
                if (academic == null) {
                    throw new DataException(ACADEMIC_YEAR_DOES_NOT_EXIST);
                } else {
                    yearAttendance = getYearAttendance(academic, null);
                }
            } else {
                attendanceProfile.setYearAttendance(yearAttendance);
                yearAttendance = getYearAttendance(effectiveAcademic, yearAttendance);
            }
        }
        return yearAttendance;
    }

    /**
     * Gets the year attendance.
     * 
     * @param academic the academic
     * @param yearAttendance the year attendance
     * @return the year attendance
     * @throws DataException the data exception
     */
    private List<MonthAttendance> getYearAttendance(AcademicDto academic,
            List<MonthAttendance> yearAttendance) throws DataException {
        String academicYearStartDateVal = academic.getAcademicYearStartDate();
        String academicYearEndDateVal = academic.getAcademicYearEndDate();
        Date academicYearStartDate = ConversionUtil.fromApplicationDate(academicYearStartDateVal);
        Date academicYearEndDate = ConversionUtil.fromApplicationDate(academicYearEndDateVal);
        HolidaySearchCriteria holidaySearchCriteria = new HolidaySearchCriteria();
        holidaySearchCriteria.setAcademicYear(academic.getAcademicYearName());
        holidaySearchCriteria.setStartDate(academicYearStartDateVal);
        holidaySearchCriteria.setEndDate(academicYearEndDateVal);
        // get holidays
        List<HolidayDto> holidays = holidayManager.getAll(holidaySearchCriteria);
        return AttendanceDataAssembler.getYearAttendance(academicYearStartDate, academicYearEndDate, holidays, yearAttendance);
    }

    /**
     * Gets the all.
     * 
     * @return the all
     * @throws DataException the data exception
     */
    public List<AttendanceProfileDto> getAll() throws DataException {
        List<AttendanceProfileDto> attendanceProfiles = null;
        try {
            attendanceProfiles = attendanceProfileDao.getAll();
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return attendanceProfiles;
    }

    /**
     * Creates the.
     * 
     * @param attendanceProfile the attendance profile
     * @return the int
     * @throws DataException the data exception
     */
    public boolean create(AttendanceProfileDto attendanceProfile) throws DataException {
        int attendanceProfileId = 0;
        try {
            System.out.println("create()");
            // Validate attendance profile
            attendanceProfileValidator.validate(attendanceProfile);
            attendanceProfileId = attendanceProfile.getAttendanceProfileId();

            /*if (attendanceProfileId != 0) {
                throw new DataException("Attendance Profile must not be specified when creating a new.");
            }*/
            String profileName = attendanceProfile.getProfileName();
            AttendanceProfileDto existingProfile = attendanceProfileDao.get(profileName);
            System.out.println("existingProfile " + existingProfile);
            if (existingProfile != null) {
                throw new DataException("Attendance Profile (" + profileName + ") already exists.");
            }
            attendanceProfile.setActive(false);
            attendanceProfileId = attendanceProfileDao.create(attendanceProfile);
            if (attendanceProfileId == 0) {
                throw new DataException("Unable to create Attendance Profile now.");
            }
            //attendanceProfile.setActive(false);
            // Create attendance profile associated data.
            attendanceProfileDao.create(attendanceProfileId, attendanceProfile.getYearAttendance());
            attendanceProfile.setAttendanceProfileId(attendanceProfileId);
            assignProfilesTo(attendanceProfile);
        } catch (ValidationException validationException) {
            validationException.printStackTrace();
            if (attendanceProfileId != 0) {
                delete(attendanceProfileId);
            }
            throw new DataException(validationException.getMessage(), validationException);
        } catch (DaoException daoException) {
            daoException.printStackTrace();
            if (attendanceProfileId != 0) {
                delete(attendanceProfileId);
            }
            throw new DataException("Unable to create Attendance Profile now. Please try again.");
        }
        return attendanceProfileId>0;
    }

    /**
     * Update.
     * 
     * @param attendanceProfileId the attendance profile id
     * @param attendanceProfile the attendance profile
     * @return true, if successful
     * @throws DataException the data exception
     */
    public boolean update(int attendanceProfileId,
            AttendanceProfileDto attendanceProfile) throws DataException {

        String profileName = null;
        try {
            System.out.println("update(" + attendanceProfileId + ")");
            // Validate attendance profile
            attendanceProfileValidator.validate(attendanceProfile);
            attendanceProfileId = attendanceProfile.getAttendanceProfileId();
            profileName = attendanceProfile.getProfileName();

            AttendanceProfileDto existingAttendanceProfile = attendanceProfileDao.get(attendanceProfileId);
            if (existingAttendanceProfile == null) {
                throw new DataException("Attendance Profile (" + profileName + ") does not exist.");
            }
            profileName = attendanceProfile.getProfileName();
            AttendanceProfileDto existingProfile = attendanceProfileDao.get(profileName);
            if (existingProfile != null && existingProfile.getAttendanceProfileId() != attendanceProfileId) {
                throw new DataException("Attendance Profile (" + profileName + ") already exists.");
            }
            if (!attendanceProfile.isActive() && existingAttendanceProfile.isActive()) {
                attendanceProfile.setActive(existingAttendanceProfile.isActive());
                attendanceProfileValidator.validateAssignmentConflicts(attendanceProfile);
            }
            attendanceProfileId = existingAttendanceProfile.getAttendanceProfileId();
            attendanceProfileDao.update(existingAttendanceProfile.getAttendanceProfileId(), attendanceProfile);

            // Update attendance profile data.
            List<MonthAttendance> yearAttendance = attendanceProfile.getYearAttendance();
            if (yearAttendance != null && !yearAttendance.isEmpty()) {
                for (MonthAttendance monthAttendance : yearAttendance) {
                    int monthAttendanceId = monthAttendance.getMonthAttendanceId();
                    if (monthAttendanceId == 0) {
                        attendanceProfileDao.create(attendanceProfileId, monthAttendance);
                    } else {
                        attendanceProfileDao.update(monthAttendanceId, monthAttendance);
                    }
                }
            }
            System.out.println("Deleting all the previous assignments.");
            // delete all the previous assignments
            attendanceAssignmentsDao.delete(attendanceProfileId, StateDto.class);
            attendanceAssignmentsDao.delete(attendanceProfileId, RegionDto.class);
            attendanceAssignmentsDao.delete(attendanceProfileId, BranchDto.class);
            attendanceAssignmentsDao.delete(attendanceProfileId, SchoolDto.class);
            attendanceAssignmentsDao.delete(attendanceProfileId, RegisteredClassDto.class);

            assignProfilesTo(attendanceProfile);
        } catch (ValidationException validationException) {
            throw new DataException(validationException.getMessage(), validationException);
        } catch (DaoException daoException) {
            throw new DataException("Unable to update Attendance Profile now. Please try again.");
        }
        return true;
    }

    /**
     * Activate attendance profile.
     * 
     * @param attendanceProfileId the attendance profile id
     * @return true, if successful
     * @throws DataException the data exception
     */
    public boolean activate(int attendanceProfileId) throws DataException {
        try {
            // If requested to activate this attendance profile then activate
            AttendanceProfileDto attendanceProfile = get(attendanceProfileId, null);
            if (attendanceProfile == null) {
                throw new DataException("Attendance Profile does not exists!");
            }
            boolean active = attendanceProfile.isActive();
            if (active) {
                throw new DataException("Attendance Profile is active already.");
            }
            attendanceProfile.setActive(true);
            attendanceProfileValidator.validate(attendanceProfile);
            return attendanceProfileDao.update(attendanceProfileId, attendanceProfile);
        } catch (ValidationException validationException) {
            throw new DataException(validationException.getMessage(), validationException);
        } catch (DaoException daoException) {
            throw new DataException("Unable to activate Attendance Profile now. Please try again.");
        }

    }

    /**
     * Assign profiles to.
     * 
     * @param attendanceProfile the attendance profile
     * @throws DaoException the dao exception
     * @throws DataException the data exception
     */
    private void assignProfilesTo(AttendanceProfileDto attendanceProfile)
            throws DaoException, DataException {
        int attendanceProfileId = attendanceProfile.getAttendanceProfileId();

        List<StateDto> assignToStates = attendanceProfile.getStates();
        System.out.println("Assigned States -----> " + assignToStates);
        attendanceAssignmentsDao.create(attendanceProfileId, assignToStates);
        if (!isAssignedToAll(assignToStates, stateManager.getAll())) {
            List<RegionDto> assignToRegions = attendanceProfile.getRegions();
            System.out.println("Assigned Regions -----> " + assignToRegions);
            attendanceAssignmentsDao.create(attendanceProfileId, assignToRegions);
            // if all regions are not selected then create branches
            if (!isAssignedToAll(assignToRegions, regionManager.getAll())) {
                List<BranchDto> assignToBranches = attendanceProfile.getBranches();
                System.out.println("Assigned Branches -----> " + assignToRegions);
                attendanceAssignmentsDao.create(attendanceProfileId, assignToBranches);
                // if all branches are not selected then create schools
                if (!isAssignedToAll(assignToBranches, branchManager.getAll())) {
                    List<SchoolDto> assignToSchools = attendanceProfile.getSchools();
                    System.out.println("Assigned Schools -----> " + assignToSchools);
                    attendanceAssignmentsDao.create(attendanceProfileId, assignToSchools);
                    // if all schools are not selected then create classes
                    if (!isAssignedToAll(assignToSchools, schoolManager.getAll())) {
                        List<RegisteredClassDto> assignToRegisteredClasses = attendanceProfile.getRegisteredClasses();
                        System.out.println("Assigned Classes -----> " + assignToRegisteredClasses);
                        attendanceAssignmentsDao.create(attendanceProfileId, assignToRegisteredClasses);
                    }
                }
            }
        }
    }

    private boolean isAssignedToAll(List<? extends Object> assignTo, List<? extends Object> all) {
        int id1=0,id2=0;
        if (assignTo != null && all != null) {
            for (Object allObject : all) {
                boolean present = false;
                for (Object assignToObject : assignTo) {
                    if (allObject instanceof StateDto && assignToObject instanceof StateDto) {
                        id1 = ((StateDto) allObject).getStateId();
                        id2 = ((StateDto) assignToObject).getStateId();
                    } else if (allObject instanceof RegionDto && assignToObject instanceof RegionDto) {
                        id1 = ((RegionDto) allObject).getRegionId();
                        id2 = ((RegionDto) assignToObject).getRegionId();
                    } else if (allObject instanceof BranchDto && assignToObject instanceof BranchDto) {
                        id1 = ((BranchDto) allObject).getBranchId();
                        id2 = ((BranchDto) assignToObject).getBranchId();
                    } else if (allObject instanceof SchoolDto && assignToObject instanceof SchoolDto) {
                        id1 = ((SchoolDto) allObject).getSchoolId();
                        id2 = ((SchoolDto) assignToObject).getSchoolId();
                    } else if (allObject instanceof RegisteredClassDto && assignToObject instanceof RegisteredClassDto) {
                        id1 = ((RegisteredClassDto) allObject).getClassId();
                        id2 = ((RegisteredClassDto) assignToObject).getClassId();
                    }
                    if (id1 == id2) {
                        present = true;
                        break;
                    }
                }
                if (!present) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Delete.
     * 
     * @param attendanceProfileId the attendance profile id
     * @return true, if successful
     * @throws DataException the data exception
     */
    public boolean delete(int attendanceProfileId) throws DataException {
        boolean deleted = false;
        try {
            if (attendanceProfileId <= 0) {
                throw new InvalidDataException("Invalid Attendance Profile ID.");
            }
            deleted = attendanceProfileDao.delete(attendanceProfileId);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return deleted;
    }

}
