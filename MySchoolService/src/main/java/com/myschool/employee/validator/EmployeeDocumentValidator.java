package com.myschool.employee.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.myschool.common.exception.DaoException;
import com.myschool.common.exception.DataException;
import com.myschool.common.exception.InvalidDataException;
import com.myschool.common.exception.ValidationException;
import com.myschool.common.util.DateUtil;
import com.myschool.common.util.StringUtil;
import com.myschool.common.validator.AbstractValidator;
import com.myschool.common.validator.DataTypeValidator;
import com.myschool.employee.domain.EmployeeDocumentManager;
import com.myschool.employee.dto.EmployeeDocument;
import com.myschool.user.constants.UserType;

/**
 * The Class EmployeeDocumentValidator.
 */
@Component
public class EmployeeDocumentValidator extends AbstractValidator<EmployeeDocument> {

    /** The employee document manager. */
    @Autowired
    private EmployeeDocumentManager employeeDocumentManager;

    /* (non-Javadoc)
     * @see com.myschool.common.validator.AbstractValidator#doValidate(java.lang.Object)
     */
    @Override
    protected void doValidate(EmployeeDocument employeeDocument) throws ValidationException {
        try {
            employeeDocument.setDocument(validateDocument(employeeDocument.getDocument(), UserType.EMPLOYEE));
            employeeDocument.setDocumentNumber(validate(
                    employeeDocument.getDocumentNumber(), "Document Number", DataTypeValidator.ANY_CHARACTER, true));
            String documentExpiryDate = validate(employeeDocument.getDocumentExpiryDate(), "Document Expiry Date", DataTypeValidator.DATE, false);
            if (!StringUtil.isNullOrBlank(documentExpiryDate)) {
                // ensure expiry date is a future date.
                if (!DateUtil.isFutureDate(documentExpiryDate)) {
                    throw new ValidationException("Document Expiry Date must be a future date");
                }
            }
            employeeDocument.setDocumentIssuedBy(validate(
                    employeeDocument.getDocumentIssuedBy(), "Document Issued By", DataTypeValidator.ANY_CHARACTER, false));
        } catch (InvalidDataException invalidDataException) {
            throw new ValidationException(invalidDataException.getMessage(), invalidDataException);
        } catch (DaoException daoException) {
            throw new ValidationException(daoException.getMessage(), daoException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.common.validator.AbstractValidator#validate(int)
     */
    @Override
    public void validate(int employeeDocumentId) throws ValidationException {
        try {
            super.validate(employeeDocumentId);
            EmployeeDocument existingEmployeeDocument = employeeDocumentManager.get(employeeDocumentId);
            if (existingEmployeeDocument == null) {
                throw new ValidationException("There is no such document for employee to update.");
            }
        } catch (DataException dataException) {
            throw new ValidationException(dataException.getMessage(), dataException);
        }
    }

}
