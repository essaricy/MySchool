package com.myschool.student.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myschool.common.exception.ServiceException;
import com.myschool.student.domain.AdmissionStatusManager;
import com.myschool.student.dto.AdmissionStatus;
import com.quasar.core.exception.DataException;

/**
 * The Class AdmissionStatusServiceImpl.
 */
@Service
public class AdmissionStatusServiceImpl implements AdmissionStatusService {

    /** The admission status manager. */
    @Autowired
    private AdmissionStatusManager admissionStatusManager;

    /* (non-Javadoc)
     * @see com.myschool.application.service.Servicable#create(java.lang.Object)
     */
    @Override
    public boolean create(AdmissionStatus admissionStatus) throws ServiceException {
        try {
            return admissionStatusManager.create(admissionStatus) > 0;
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.application.service.Servicable#getAll()
     */
    @Override
    public List<AdmissionStatus> getAll() throws ServiceException {
        try {
            return admissionStatusManager.getAll();
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.application.service.Servicable#get(int)
     */
    @Override
    public AdmissionStatus get(int admissionStatusId) throws ServiceException {
        try {
            return admissionStatusManager.get(admissionStatusId);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.application.service.Servicable#update(int, java.lang.Object)
     */
    @Override
    public boolean update(int admissionStatusId,
            AdmissionStatus admissionStatus) throws ServiceException {
        try {
            return admissionStatusManager.update(admissionStatusId, admissionStatus);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.application.service.Servicable#delete(int)
     */
    @Override
    public boolean delete(int admissionStatusId) throws ServiceException {
        try {
            return admissionStatusManager.delete(admissionStatusId);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
    }

}
