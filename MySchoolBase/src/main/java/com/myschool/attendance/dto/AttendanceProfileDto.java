package com.myschool.attendance.dto;

import java.io.Serializable;
import java.util.List;

import com.myschool.academic.dto.AcademicDto;
import com.myschool.clazz.dto.RegisteredClassDto;
import com.myschool.school.dto.SchoolDto;

/**
 * The Class AttendanceProfileDto.
 */
public class AttendanceProfileDto implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The profile id. */
    private int profileId;

    /** The profile name. */
    private String profileName;

    /** The effective academic. */
    private AcademicDto effectiveAcademic;

    /** The attendance months. */
    private List<AttendanceMonth> attendanceMonths;

    /** The assigned schools. */
    private List<SchoolDto> assignedSchools;

    /** The assigned classes. */
    private List<RegisteredClassDto> assignedClasses;

    /**
     * Gets the profile id.
     * 
     * @return the profile id
     */
    public int getProfileId() {
        return profileId;
    }

    /**
     * Sets the profile id.
     * 
     * @param profileId the new profile id
     */
    public void setProfileId(int profileId) {
        this.profileId = profileId;
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
     * Gets the attendance months.
     * 
     * @return the attendance months
     */
    public List<AttendanceMonth> getAttendanceMonths() {
        return attendanceMonths;
    }

    /**
     * Sets the attendance months.
     * 
     * @param attendanceMonths the new attendance months
     */
    public void setAttendanceMonths(List<AttendanceMonth> attendanceMonths) {
        this.attendanceMonths = attendanceMonths;
    }

    /**
     * Gets the assigned schools.
     * 
     * @return the assigned schools
     */
    public List<SchoolDto> getAssignedSchools() {
        return assignedSchools;
    }

    /**
     * Sets the assigned schools.
     * 
     * @param assignedSchools the new assigned schools
     */
    public void setAssignedSchools(List<SchoolDto> assignedSchools) {
        this.assignedSchools = assignedSchools;
    }

    /**
     * Gets the assigned classes.
     * 
     * @return the assigned classes
     */
    public List<RegisteredClassDto> getAssignedClasses() {
        return assignedClasses;
    }

    /**
     * Sets the assigned classes.
     * 
     * @param assignedClasses the new assigned classes
     */
    public void setAssignedClasses(List<RegisteredClassDto> assignedClasses) {
        this.assignedClasses = assignedClasses;
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
            .append("profileId = ").append(this.profileId).append(SEPARATOR)
            .append("profileName = ").append(this.profileName).append(SEPARATOR)
            .append("effectiveAcademic = ").append(this.effectiveAcademic).append(SEPARATOR)
            .append("attendanceMonths = ").append(this.attendanceMonths).append(SEPARATOR)
            .append("assignedSchools = ").append(this.assignedSchools).append(SEPARATOR)
            .append("assignedClasses = ").append(this.assignedClasses).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}
