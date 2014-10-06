package com.myschool.application.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myschool.application.domain.DocumentManager;
import com.myschool.common.dto.DocumentDto;
import com.myschool.common.exception.DataException;
import com.myschool.common.exception.ServiceException;
import com.myschool.user.constants.UserType;

/**
 * The Class DocumentServiceImpl.
 */
@Service
public class DocumentServiceImpl implements DocumentService {

    /** The document manager. */
    @Autowired
    private DocumentManager documentManager;

    /* (non-Javadoc)
     * @see com.myschool.application.service.Servicable#create(java.lang.Object)
     */
    @Override
    public boolean create(DocumentDto document) throws ServiceException {
        try {
            return documentManager.create(document) > 0;
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.application.service.Servicable#getAll()
     */
    @Override
    public List<DocumentDto> getAll() throws ServiceException {
        try {
            return documentManager.getAll();
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
    }

    @Override
    public List<DocumentDto> getAll(UserType userType) throws ServiceException {
        try {
            return documentManager.getAll(userType);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.application.service.Servicable#get(int)
     */
    @Override
    public DocumentDto get(int documentId) throws ServiceException {
        try {
            return documentManager.get(documentId);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.application.service.Servicable#update(int, java.lang.Object)
     */
    @Override
    public boolean update(int documentId, DocumentDto document) throws ServiceException {
        try {
            return documentManager.update(documentId, document);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.application.service.Servicable#delete(int)
     */
    @Override
    public boolean delete(int documentId) throws ServiceException {
        try {
            return documentManager.delete(documentId);
        } catch (DataException dataException) {
            throw new ServiceException(dataException.getMessage(), dataException);
        }
    }

}
