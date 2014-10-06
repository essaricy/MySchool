package com.myschool.clazz.dto;

import java.io.Serializable;

import com.myschool.school.dto.SchoolDto;

/**
 * The Class RegisteredClassDto.
 */
public class RegisteredClassDto implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The class id. */
    private int classId;

    /** The class dto. */
    private ClassDto classDto;

    /** The medium. */
    private MediumDto medium;

    /** The section. */
    private SectionDto section;

    /** The school. */
    private SchoolDto school;

    /**
     * Gets the school.
     *
     * @return the school
     */
    public SchoolDto getSchool() {
        return school;
    }

    /**
     * Sets the school.
     *
     * @param school the new school
     */
    public void setSchool(SchoolDto school) {
        this.school = school;
    }

    /**
     * Gets the class id.
     *
     * @return the class id
     */
    public int getClassId() {
        return classId;
    }

    /**
     * Sets the class id.
     *
     * @param classId the new class id
     */
    public void setClassId(int classId) {
        this.classId = classId;
    }

    /**
     * Gets the class dto.
     *
     * @return the class dto
     */
    public ClassDto getClassDto() {
        return classDto;
    }

    /**
     * Sets the class dto.
     *
     * @param classDto the new class dto
     */
    public void setClassDto(ClassDto classDto) {
        this.classDto = classDto;
    }

    /**
     * Gets the medium.
     *
     * @return the medium
     */
    public MediumDto getMedium() {
        return medium;
    }

    /**
     * Sets the medium.
     *
     * @param medium the new medium
     */
    public void setMedium(MediumDto medium) {
        this.medium = medium;
    }

    /**
     * Gets the section.
     *
     * @return the section
     */
    public SectionDto getSection() {
        return section;
    }

    /**
     * Sets the section.
     *
     * @param section the new section
     */
    public void setSection(SectionDto section) {
        this.section = section;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + classId;
        return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        RegisteredClassDto other = (RegisteredClassDto) obj;
        if (classId != other.classId) {
            return false;
        }
        return true;
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
        retValue.append("RegisteredClassDto ( ")
            .append(super.toString()).append(SEPARATOR)
            .append("classDto = ").append(this.classDto).append(SEPARATOR)
            .append("classId = ").append(this.classId).append(SEPARATOR)
            .append("medium = ").append(this.medium).append(SEPARATOR)
            .append("school = ").append(this.school).append(SEPARATOR)
            .append("section = ").append(this.section).append(SEPARATOR)
            .append(" )\n");
        return retValue.toString();
    }

}
