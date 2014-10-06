package com.myschool.application.service;

import java.util.List;

import com.myschool.common.exception.ServiceException;

/**
 * The Interface Servicable.
 *
 * @param <DTO> the generic type
 */
public interface Servicable<DTO> {
    
    /**
     * Creates the.
     *
     * @param dto the dto
     * @return true, if successful
     * @throws ServiceException the service exception
     */
    public boolean create(DTO dto) throws ServiceException;

    /**
     * Gets the all.
     *
     * @return the all
     * @throws ServiceException the service exception
     */
    public List<DTO> getAll() throws ServiceException;

    /**
     * Gets the.
     *
     * @param id the id
     * @return the dTO
     * @throws ServiceException the service exception
     */
    public DTO get(int id) throws ServiceException;

    /**
     * Update.
     *
     * @param id the id
     * @param dto the dto
     * @return true, if successful
     * @throws ServiceException the service exception
     */
    public boolean update(int id, DTO dto) throws ServiceException;

    /**
     * Delete.
     *
     * @param id the id
     * @return true, if successful
     * @throws ServiceException the service exception
     */
    public boolean delete(int id) throws ServiceException;

}
