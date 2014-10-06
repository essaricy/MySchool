package com.myschool.clazz.dao;

import java.util.List;

import com.myschool.clazz.dto.SectionDto;
import com.myschool.common.exception.DaoException;

/**
 * The Interface SectionDao.
 */
public interface SectionDao {

    /**
     * Creates the section.
     *
     * @param sectionDto the section dto
     * @return the int
     * @throws DaoException the dao exception
     */
    public int createSection(SectionDto sectionDto) throws DaoException;

    /**
     * Gets the all.
     *
     * @return the all
     * @throws DaoException the dao exception
     */
    public List<SectionDto> getAll() throws DaoException;

    /**
     * Gets the.
     *
     * @param sectionId the section id
     * @return the section dto
     * @throws DaoException the dao exception
     */
    public SectionDto get(int sectionId) throws DaoException;

    /**
     * Update section.
     *
     * @param sectionId the section id
     * @param sectionDto the section dto
     * @return true, if successful
     * @throws DaoException the dao exception
     */
    public boolean updateSection(int sectionId, SectionDto sectionDto) throws DaoException;

    /**
     * Delete section.
     *
     * @param sectionId the section id
     * @return true, if successful
     * @throws DaoException the dao exception
     */
    public boolean deleteSection(int sectionId) throws DaoException;

    /**
     * Gets the.
     *
     * @param sectionName the section name
     * @return the section dto
     * @throws DaoException the dao exception
     */
    public SectionDto get(String sectionName) throws DaoException;

    /**
     * Creates the.
     *
     * @param sectionName the section name
     * @return the int
     * @throws DaoException the dao exception
     */
    int create(String sectionName) throws DaoException;

}
