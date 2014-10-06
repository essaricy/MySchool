package com.myschool.exim.service;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myschool.common.exception.DataException;
import com.myschool.common.exception.ServiceException;
import com.myschool.exim.constants.EximPolicy;
import com.myschool.exim.domain.AbstractEximManager;
import com.myschool.exim.domain.EximManager;
import com.myschool.exim.domain.EximManagerFactory;
import com.myschool.exim.dto.EximDto;
import com.myschool.exim.dto.ExportStatusDto;
import com.myschool.exim.dto.ImportStatusDto;
import com.myschool.exim.dto.UploadFileTrackerDto;

/**
 * The Class EximServiceImpl.
 */
@Service
public class EximServiceImpl implements EximService {

    /** The exim manager. */
    @Autowired
    private EximManager eximManager;

    /** The exim manager factory. */
    @Autowired
    private EximManagerFactory eximManagerFactory;

    /* (non-Javadoc)
     * @see com.myschool.service.interfaces.Servicable#create(java.lang.Object)
     */
    @Override
    public boolean create(EximDto dto) throws ServiceException {
        return false;
    }

    /* (non-Javadoc)
     * @see com.myschool.service.interfaces.Servicable#delete(int)
     */
    @Override
    public boolean delete(int id) throws ServiceException {
        return false;
    }

    /* (non-Javadoc)
     * @see com.myschool.service.interfaces.Servicable#get(int)
     */
    @Override
    public EximDto get(int id) throws ServiceException {
        return null;
    }

    /* (non-Javadoc)
     * @see com.myschool.service.interfaces.Servicable#getAll()
     */
    @Override
    public List<EximDto> getAll() throws ServiceException {
        List<EximDto> exims = null;
        try {
            exims = eximManager.getAll();
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
        return exims;
    }

    /* (non-Javadoc)
     * @see com.myschool.service.interfaces.EximService#getAllImports()
     */
    @Override
    public List<EximDto> getAllImports() throws ServiceException {
        List<EximDto> imports = null;
        try {
            imports = eximManager.getAllImports();
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
        return imports;
    }

    /* (non-Javadoc)
     * @see com.myschool.service.interfaces.EximService#getAllExports()
     */
    @Override
    public List<EximDto> getAllExports() throws ServiceException {
        List<EximDto> exports = null;
        try {
            exports = eximManager.getAllExports();
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
        return exports;
    }

    /* (non-Javadoc)
     * @see com.myschool.service.interfaces.Servicable#update(int, java.lang.Object)
     */
    @Override
    public boolean update(int id, EximDto dto) throws ServiceException {
        return false;
    }

    /* (non-Javadoc)
     * @see com.myschool.exim.service.EximService#uploadData(com.myschool.exim.constants.EximPolicy, java.io.File, com.myschool.exim.dto.UploadFileTrackerDto)
     */
    @Override
    public ImportStatusDto uploadData(EximPolicy eximPolicy, File importFile,
            UploadFileTrackerDto uploadFileTracker) throws ServiceException {
        try {
            AbstractEximManager abstractEximManager = eximManagerFactory.getEximManager(eximPolicy);
            if (abstractEximManager == null) {
                throw new ServiceException("There is no EXIM manager for the policy " + eximPolicy);
            }
            return abstractEximManager.uploadData(eximPolicy, importFile, uploadFileTracker);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.exim.service.EximService#exportData(com.myschool.exim.constants.EximPolicy, java.lang.Object)
     */
    @Override
    public ExportStatusDto exportData(EximPolicy students, Object dataToExport)
            throws ServiceException {
        // TODO Auto-generated method stub
        return null;
    }
}
