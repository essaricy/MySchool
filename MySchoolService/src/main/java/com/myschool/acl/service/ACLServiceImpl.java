package com.myschool.acl.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myschool.common.exception.ServiceException;
import com.quasar.core.exception.DataException;
import com.myschool.acl.domain.ACLManager;

/**
 * The Class ACLServiceImpl.
 */
@Service
public class ACLServiceImpl implements ACLService {

    /** The acl manager. */
    @Autowired
    private ACLManager aclManager;

    /* (non-Javadoc)
     * @see com.myschool.acl.service.ACLService#processChangePasswordRequest(java.lang.String)
     */
    @Override
    public void processChangePasswordRequest(String emailId)
            throws ServiceException {
        try {
            aclManager.processChangePasswordRequest(emailId);
        } catch(DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.acl.service.ACLService#processChangePasswordComplete(java.lang.String, java.lang.String)
     */
    @Override
    public void processChangePasswordComplete(String emailId, String tokenId)
            throws ServiceException {
        /*try {
            
        } catch(DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }*/
    }

}
