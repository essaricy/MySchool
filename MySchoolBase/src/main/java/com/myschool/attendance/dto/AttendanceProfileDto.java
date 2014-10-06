package com.myschool.attendance.dto;

import java.io.Serializable;
import java.util.List;

import com.myschool.academic.dto.AcademicDto;
import com.myschool.branch.dto.BranchDto;
import com.myschool.branch.dto.RegionDto;
import com.myschool.branch.dto.StateDto;
import com.myschool.clazz.dto.RegisteredClassDto;
import com.myschool.school.dto.SchoolDto;

/**
 * The Class AttendanceProfileDto.
 */
public class AttendanceProfileDto implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The attendance profile id. */
    private int attendanceProfileId;

    /** The profile name. */
    private String profileName;

    /** The effective academic. */
    private AcademicDto effectiveAcademic;

    /** The active. */
    private boolean active;

    /** The year attendance. */
    private List<MonthAttendance> yearAttendance;

    /** The states. */
    private List<StateDto> states;

    /** The regions. */
    private List<RegionDto> regions;

    /** The branches. */
    private List<BranchDto> branches;

    /** The schools. */
    private List<SchoolDto> schools;

    /** The registered classes. */
    private List<RegisteredClassDto> registeredClasses;

    /**
     * Checks if is active.
     * 
     * @return true, if is active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Sets the active.
     * 
     * @param active the new active
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Gets the attendance profile id.
     * 
     * @return the attendance profile id
     */
    public int getAttendanceProfileId() {
        return attendanceProfileId;
    }

    /**
     * Sets the attendance profile id.
     * 
     * @param attendanceProfileId the new attendance profile id
     */
    public void setAttendanceProfileId(int attendanceProfileId) {
        this.attendanceProfileId = attendanceProfileId;
    }

    /**
     * Gets the profile name.
     * 
     * @return the profile name
     */
    public String getProfileName() {
        return profileName;
    }

    /**
     * Sets the profile name.
     * 
     * @param profileName the new profile name
     */
    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    /**
     * Gets the effective academic.
     * 
     * @return the effective academic
     */
    public AcademicDto getEffectiveAcademic() {
        return effectiveAcademic;
    }

    /**
     * Sets the effective academic.
     * 
     * @param effectiveAcademic the new effective academic
     */
    public void setEffectiveAcademic(AcademicDto effectiveAcademic) {
        this.effectiveAcademic = effectiveAcademic;
    }

    /**
     * Gets the year attendance.
     * 
     * @return the year attendance
     */
    public List<MonthAttendance> getYearAttendance() {
        return yearAttendance;
    }

    /**
     * Sets the year attendance.
     * 
     * @param yearAttendance the new year attendance
     */
    public void setYearAttendance(List<MonthAttendance> yearAttendance) {
        this.yearAttendance = yearAttendance;
    }

    /**
     * Gets the states.
     * 
     * @return the states
     */
    public List<StateDto> getStates() {
        return states;
    }

    /**
     * Sets the states.
     * 
     * @param states the new states
     */
    public void setStates(List<StateDto> states) {
        this.states = states;
    }

    /**
     * Gets the regions.
     * 
     * @return the regions
     */
    public List<RegionDto> getRegions() {
        return regions;
    }

    /**
     * Sets the regions.
     * 
     * @param regions the new regions
     */
    public void setRegions(List<RegionDto> regions) {
        this.regions = regions;
    }

    /**
     * Gets the branches.
     * 
     * @return the branches
     */
    public List<BranchDto> getBranches() {
        return branches;
    }

    /**
     * Sets the branches.
     * 
     * @param branches the new branches
     */
    public void setBranches(List<BranchDto> branches) {
        this.branches = branches;
    }

    /**
     * Gets the schools.
     * 
     * @return the schools
     */
    public List<SchoolDto> getSchools() {
        return schools;
    }

    /**
     * Sets the schools.
     * 
     * @param schools the new schools
     */
    public void setSchools(List<SchoolDto> schools) {
        this.schools = schools;
    }

    /**
     * Gets the registered classes.
     * 
     * @return the registered classes
     */
    public List<RegisteredClassDto> getRegisteredClasses() {
        return registeredClasses;
    }

    /**
     * Sets the registered classes.
     * 
     * @param registeredClasses the new registered classes
     */
    public void setRegisteredClasses(List<RegisteredClassDto> registeredClasses) {
        this.registeredClasses = registeredClasses;
    }

    /**
     * Constructs a <code>String</code> with all attributes
     * in name = value format.
     *
     * @return a <code>String</code> representation 
     * of this object.
     */
    public String toString() {
        final String SEPARATOR = ", ";
        StringBuilder retValue = new StringBuilder();
        retValue.append("AttendanceProfileDto ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("attendanceProfileId = ").append(this.attendanceProfileId).append(SEPARATOR)
            .append("profileName = ").append(this.profileName).append(SEPARATOR)
            .append("effectiveAcademic = ").append(this.effectiveAcademic).append(SEPARATOR)
            .append("active = ").append(this.active).append(SEPARATOR)
            .append("yearAttendance = ").append(this.yearAttendance).append(SEPARATOR)
            .append("states = ").append(this.states).append(SEPARATOR)
            .append("regions = ").append(this.regions).append(SEPARATOR)
            .append("branches = ").append(this.branches).append(SEPARATOR)
            .append("schools = ").append(this.schools).append(SEPARATOR)
            .append("registeredClasses = ").append(this.registeredClasses).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}
