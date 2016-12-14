package com.myschool.acl.service;

import com.myschool.common.exception.ServiceException;

/**
 * The Interface ACLService.
 */
public interface ACLService {

    /**
     * Process change password request.
     *
     * @param emailId the email id
     * @throws ServiceException the service exception
     */
    void processChangePasswordRequest(String emailId) throws ServiceException;

    /**
     * Process change password complete.
     *
     * @param emailId the email id
     * @param tokenId the token id
     * @throws ServiceException the service exception
     */
    void processChangePasswordComplete(String emailId, String tokenId) throws ServiceException;

}
