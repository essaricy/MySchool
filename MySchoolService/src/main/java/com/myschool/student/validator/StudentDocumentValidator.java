package com.myschool.student.validator;

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
import com.myschool.student.domain.StudentDocumentManager;
import com.myschool.student.dto.StudentDocument;
import com.myschool.user.constants.UserType;

/**
 * The Class StudentDocumentValidator.
 */
@Component
public class StudentDocumentValidator extends AbstractValidator<StudentDocument> {

    /** The student document manager. */
    @Autowired
    private StudentDocumentManager studentDocumentManager;

    /* (non-Javadoc)
     * @see com.myschool.common.validator.AbstractValidator#doValidate(java.lang.Object)
     */
    @Override
    protected void doValidate(StudentDocument studentDocument) throws ValidationException {
        try {
            studentDocument.setDocument(validateDocument(studentDocument.getDocument(), UserType.STUDENT));
            studentDocument.setDocumentNumber(validate(
                    studentDocument.getDocumentNumber(), "Document Number", DataTypeValidator.ANY_CHARACTER, true));
            String documentExpiryDate = validate(studentDocument.getDocumentExpiryDate(), "Document Expiry Date", DataTypeValidator.DATE, false);
            if (!StringUtil.isNullOrBlank(documentExpiryDate)) {
                // ensure expiry date is a future date.
                if (!DateUtil.isFutureDate(documentExpiryDate)) {
                    throw new ValidationException("Document Expiry Date must be a future date");
                }
            }
            studentDocument.setDocumentIssuedBy(validate(
                    studentDocument.getDocumentIssuedBy(), "Document Issued By", DataTypeValidator.ANY_CHARACTER, false));
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
    public void validate(int studentDocumentId) throws ValidationException {
        try {
            super.validate(studentDocumentId);
            StudentDocument existingStudentDocument = studentDocumentManager.get(studentDocumentId);
            if (existingStudentDocument == null) {
                throw new ValidationException("There is no such document for student to update.");
            }
        } catch (DataException dataException) {
            throw new ValidationException(dataException.getMessage(), dataException);
        }
    }

}
