package com.myschool.attendance.domain;

import java.text.MessageFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.academic.assembler.HolidayDataAssembler;
import com.myschool.academic.domain.AcademicManager;
import com.myschool.academic.domain.HolidayManager;
import com.myschool.academic.dto.AcademicDto;
import com.myschool.academic.dto.HolidayDto;
import com.myschool.attendance.assembler.AttendanceProfileDataAssembler;
import com.myschool.attendance.constants.AttendanceConstant;
import com.myschool.attendance.dao.AttendanceAssignmentsDao;
import com.myschool.attendance.dao.AttendanceProfileDao;
import com.myschool.attendance.dto.AttendanceMonth;
import com.myschool.attendance.dto.AttendanceProfileDto;
import com.myschool.attendance.validator.AttendanceProfileValidator;
import com.myschool.clazz.dto.RegisteredClassDto;
import com.myschool.common.exception.DaoException;
import com.myschool.common.exception.ValidationException;
import com.myschool.school.dto.SchoolDto;
import com.quasar.core.exception.DataException;
import com.quasar.core.exception.InvalidDataException;

/**
 * The Class AttendanceProfileManager.
 */
@Component
public class AttendanceProfileManager {

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
     * Gets the all.
     * 
     * @param academicYearName the academic year name
     * @return the all
     * @throws DataException the data exception
     */
    public List<AttendanceProfileDto> getAll(String academicYearName) throws DataException {
        List<AttendanceProfileDto> attendanceProfiles;
        try {
            attendanceProfiles = null;
            if (academicYearName == null) {
                throw new DataException(MessageFormat.format(AttendanceConstant.ERROR_INVALID_ACADEMIC, academicYearName));
            }
            AcademicDto academic = academicManager.get(academicYearName);
            if (academic == null) {
                throw new DataException(MessageFormat.format(AttendanceConstant.ERROR_INVALID_ACADEMIC, academicYearName));
            }
            attendanceProfiles = attendanceProfileDao.getAll(academicYearName);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return attendanceProfiles;
    }

    /**
     * Gets the all in detail.
     * 
     * @return the all in detail
     * @throws DataException the data exception
     */
    public List<AttendanceProfileDto> getAllInDetail() throws DataException {
        List<AttendanceProfileDto> attendanceProfiles = null;
        try {
            attendanceProfiles = attendanceProfileDao.getAll();
            if (attendanceProfiles != null && !attendanceProfiles.isEmpty()) {
                for (AttendanceProfileDto attendanceProfile : attendanceProfiles) {
                    setProfileAssociatedData(attendanceProfile);
                }
            }
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return attendanceProfiles;
    }

    /**
     * Gets the all in detail.
     * 
     * @param academicYearName the academic year name
     * @return the all in detail
     * @throws DataException the data exception
     */
    public List<AttendanceProfileDto> getAllInDetail(String academicYearName) throws DataException {
        List<AttendanceProfileDto> attendanceProfiles;
        try {
            attendanceProfiles = null;
            if (academicYearName == null) {
                throw new DataException(MessageFormat.format(AttendanceConstant.ERROR_INVALID_ACADEMIC, academicYearName));
            }
            AcademicDto academic = academicManager.get(academicYearName);
            if (academic == null) {
                throw new DataException(MessageFormat.format(AttendanceConstant.ERROR_INVALID_ACADEMIC, academicYearName));
            }
            attendanceProfiles = attendanceProfileDao.getAll(academicYearName);
            if (attendanceProfiles != null && !attendanceProfiles.isEmpty()) {
                for (AttendanceProfileDto attendanceProfile : attendanceProfiles) {
                    setProfileAssociatedData(attendanceProfile);
                }
            }
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return attendanceProfiles;
    }

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
                throw new DataException(AttendanceConstant.ERROR_NO_PROFILE);
            }
            attendanceProfile = attendanceProfileDao.get(attendanceProfileId);
            if (attendanceProfile == null) {
                throw new DataException(AttendanceConstant.ERROR_NO_PROFILE);
            }
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return attendanceProfile;
    }

    /**
     * Gets the in detail.
     * 
     * @param attendanceProfileId the attendance profile id
     * @return the in detail
     * @throws DataException the data exception
     */
    public AttendanceProfileDto getInDetail(int attendanceProfileId) throws DataException {
        AttendanceProfileDto attendanceProfile = null;
        try {
            if (attendanceProfileId == 0) {
                throw new DataException(AttendanceConstant.ERROR_NO_PROFILE);
            }
            attendanceProfile = attendanceProfileDao.get(attendanceProfileId);
            if (attendanceProfile == null) {
                throw new DataException(AttendanceConstant.ERROR_NO_PROFILE);
            }
            setProfileAssociatedData(attendanceProfile);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return attendanceProfile;
    }

    /**
     * Gets the blank.
     * 
     * @return the blank
     * @throws DataException the data exception
     */
    public AttendanceProfileDto getBlank() throws DataException {
        AttendanceProfileDto attendanceProfile = null;
        try {
            AcademicDto academic = academicManager.getCurrentAcademic();
            if (academic == null) {
                throw new DataException(MessageFormat.format(AttendanceConstant.ERROR_INVALID_ACADEMIC, "null"));
            }
            attendanceProfile = new AttendanceProfileDto();
            attendanceProfile.setEffectiveAcademic(academic);
            setProfileAssociatedData(attendanceProfile);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return attendanceProfile;
    }

    /**
     * Gets the blank.
     * 
     * @param academicYearName the academic year name
     * @return the blank
     * @throws DataException the data exception
     */
    public AttendanceProfileDto getBlank(String academicYearName) throws DataException {
        AttendanceProfileDto attendanceProfile = null;
        try {
            if (academicYearName == null) {
                throw new DataException(MessageFormat.format(AttendanceConstant.ERROR_INVALID_ACADEMIC, academicYearName));
            }
            AcademicDto academic = academicManager.get(academicYearName);
            if (academic == null) {
                throw new DataException(MessageFormat.format(AttendanceConstant.ERROR_INVALID_ACADEMIC, academicYearName));
            }
            attendanceProfile = new AttendanceProfileDto();
            attendanceProfile.setEffectiveAcademic(academic);
            setProfileAssociatedData(attendanceProfile);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return attendanceProfile;
    }

    /**
     * Creates the.
     * 
     * @param attendanceProfile the attendance profile
     * @return true, if successful
     * @throws DataException the data exception
     */
    public boolean create(AttendanceProfileDto attendanceProfile) throws DataException {
        int attendanceProfileId = 0;
        try {
            // Validate attendance profile
            attendanceProfileValidator.validate(attendanceProfile);
            attendanceProfileId = attendanceProfile.getProfileId();
            String profileName = attendanceProfile.getProfileName();
            AttendanceProfileDto existingProfile = attendanceProfileDao.get(profileName);
            if (existingProfile != null) {
                throw new DataException("Attendance Profile (" + profileName + ") already exists.");
            }
            attendanceProfileId = attendanceProfileDao.create(attendanceProfile);
            if (attendanceProfileId == 0) {
                throw new DataException("Unable to create Attendance Profile now.");
            }
            // Create attendance profile associated data.
            attendanceProfileDao.create(attendanceProfileId, attendanceProfile.getAttendanceMonths());
            attendanceAssignmentsDao.create(attendanceProfileId, attendanceProfile.getAssignedSchools());
            attendanceAssignmentsDao.create(attendanceProfileId, attendanceProfile.getAssignedClasses());
        } catch (ValidationException validationException) {
            if (attendanceProfileId != 0) {
                delete(attendanceProfileId);
            }
            throw new DataException(validationException.getMessage(), validationException);
        } catch (DaoException daoException) {
            if (attendanceProfileId != 0) {
                delete(attendanceProfileId);
            }
            //throw new DataException("Unable to create Attendance Profile now. Please try again.");
            throw new DataException(daoException.getMessage(), daoException);
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
            // Validate attendance profile
            attendanceProfileValidator.validate(attendanceProfile);
            attendanceProfileId = attendanceProfile.getProfileId();
            profileName = attendanceProfile.getProfileName();

            AttendanceProfileDto existingAttendanceProfile = attendanceProfileDao.get(attendanceProfileId);
            if (existingAttendanceProfile == null) {
                throw new DataException(AttendanceConstant.ERROR_NO_PROFILE);
            }
            profileName = attendanceProfile.getProfileName();
            AttendanceProfileDto existingProfile = attendanceProfileDao.get(profileName);
            if (existingProfile != null && existingProfile.getProfileId() != attendanceProfileId) {
                throw new DataException(MessageFormat.format(AttendanceConstant.ERROR_PROFILE_EXISTS, profileName));
            }
            attendanceProfileId = existingAttendanceProfile.getProfileId();
            attendanceProfileDao.update(existingAttendanceProfile.getProfileId(), attendanceProfile);

            // Update attendance profile data.
            List<AttendanceMonth> attendanceMonths = attendanceProfile.getAttendanceMonths();
            if (attendanceMonths != null && !attendanceMonths.isEmpty()) {
                for (AttendanceMonth attendanceMonth : attendanceMonths) {
                    int monthAttendanceId = attendanceMonth.getAttendanceMonthId();
                    if (monthAttendanceId == 0) {
                        attendanceProfileDao.create(attendanceProfileId, attendanceMonth);
                    } else {
                        attendanceProfileDao.update(monthAttendanceId, attendanceMonth);
                    }
                }
            }
            // delete all the previous assignments and assign again
            attendanceAssignmentsDao.delete(attendanceProfileId, SchoolDto.class);
            attendanceAssignmentsDao.delete(attendanceProfileId, RegisteredClassDto.class);
            attendanceAssignmentsDao.create(attendanceProfileId, attendanceProfile.getAssignedSchools());
            attendanceAssignmentsDao.create(attendanceProfileId, attendanceProfile.getAssignedClasses());
        } catch (ValidationException validationException) {
            throw new DataException(validationException.getMessage(), validationException);
        } catch (DaoException daoException) {
            throw new DataException("Unable to update Attendance Profile now. Please try again.");
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
                throw new InvalidDataException(AttendanceConstant.ERROR_NO_PROFILE);
            }
            deleted = attendanceProfileDao.delete(attendanceProfileId);
        } catch (DaoException daoException) {
            throw new DataException(daoException.getMessage(), daoException);
        }
        return deleted;
    }

    /**
     * Sets the profile associated data.
     * 
     * @param attendanceProfile the new profile associated data
     * @throws DataException the data exception
     * @throws DaoException the dao exception
     */
    private void setProfileAssociatedData(AttendanceProfileDto attendanceProfile) throws DataException, DaoException {
        List<AttendanceMonth> attendanceMonths = null;
        if (attendanceProfile != null) {
            AcademicDto academic = attendanceProfile.getEffectiveAcademic();
            if (academic != null) {
                int profileId = attendanceProfile.getProfileId();
                List<HolidayDto> holidays = holidayManager.getAll(HolidayDataAssembler.createSearchCriteria(academic));
                if (profileId == 0) {
                    attendanceMonths = AttendanceProfileDataAssembler.align(academic, holidays, null);
                } else {
                    attendanceMonths = AttendanceProfileDataAssembler.align(academic, holidays, attendanceProfileDao.getAttendanceMonths(profileId));
                    attendanceProfile.setAssignedSchools(
                            attendanceAssignmentsDao.getAssignedSchools(profileId));
                    attendanceProfile.setAssignedClasses(
                            attendanceAssignmentsDao.getAssignedClasses(profileId));
                }
                attendanceProfile.setAttendanceMonths(attendanceMonths);
            }
        }
    }

}
