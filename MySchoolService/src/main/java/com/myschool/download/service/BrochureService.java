package com.myschool.download.service;

import com.myschool.application.service.Servicable;
import com.myschool.common.exception.ServiceException;
import com.myschool.download.dto.BrochureDto;

/**
 * The Interface BrochureService.
 */
public interface BrochureService extends Servicable<BrochureDto> {

    /**
     * Gets the brochure.
     * 
     * @param fileName the file name
     * @return the brochure
     * @throws ServiceException the service exception
     */
    BrochureDto getBrochure(String fileName) throws ServiceException;

}
