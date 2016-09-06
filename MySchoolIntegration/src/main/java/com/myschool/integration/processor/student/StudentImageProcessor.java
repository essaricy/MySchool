package com.myschool.integration.processor.student;

import org.apache.camel.Message;
import org.springframework.stereotype.Component;

import com.myschool.integration.agent.IntegrationImageResource;
import com.myschool.integration.constant.IntegrationConstant;
import com.myschool.integration.exception.CommandExecutionException;
import com.myschool.integration.exception.CommandProcessException;
import com.myschool.integration.processor.common.AbstractDynamicImageProcessor;
import com.myschool.student.dto.StudentDto;

/**
 * The Class StudentImageProcessor.
 */
@Component("StudentImageProcessor")
public class StudentImageProcessor extends AbstractDynamicImageProcessor {

    /** The Constant SELFSUBMITTED. */
    private static final String SELFSUBMITTED = "selfsubmitted";

    /** The Constant REGISTERED. */
    private static final String REGISTERED = "registered";

    /* (non-Javadoc)
     * @see com.myschool.integration.processor.common.AbstractDynamicImageProcessor#preProcess()
     */
    @Override
    public void preProcess() throws CommandProcessException {
    }

    /* (non-Javadoc)
     * @see com.myschool.integration.processor.common.AbstractDynamicImageProcessor#postProcess()
     */
    @Override
    public void postProcess() throws CommandProcessException {
    }

    /* (non-Javadoc)
     * @see com.myschool.integration.processor.common.AbstractDynamicImageProcessor#add(org.apache.camel.Message, java.lang.String)
     */
    @Override
    public void add(Message message, String body) throws CommandExecutionException {
        System.out.println("add()");
        IntegrationImageResource integrationImageResource = null;

        StudentDto student = validateAndeGetStudent(body);
        String admissionNumber = student.getAdmissionNumber();
        String imageName = student.getImageName();
        if (imageName == null || imageName.trim().length() == 0) {
            throw new CommandExecutionException("Student imageName is not provided");
        }
        // TODO retrieve the details and check if the student exists or not and verified or not.
        boolean verified = student.isVerified();
        System.out.println("verified " + verified);
        if (verified) {
            integrationImageResource = integrationImageResourceFactory.getImageResource(
                    IntegrationConstant.STUDENT_REGISTERED, REGISTERED);
        } else {
            integrationImageResource = integrationImageResourceFactory.getImageResource(
                    IntegrationConstant.STUDENT_SELF_SUBMITTED, SELFSUBMITTED);
        }
        addDymanicImage(integrationImageResource, admissionNumber, imageName);
    }

    /* (non-Javadoc)
     * @see com.myschool.integration.processor.common.AbstractDynamicImageProcessor#update(org.apache.camel.Message, java.lang.String)
     */
    @Override
    public void update(Message message, String body) throws CommandExecutionException {
        System.out.println("update()");
        IntegrationImageResource integrationImageResource = null;

        StudentDto student = validateAndeGetStudent(body);
        String admissionNumber = student.getAdmissionNumber();
        String imageName = student.getImageName();
        if (imageName == null || imageName.trim().length() == 0) {
            throw new CommandExecutionException("Student imageName is not provided");
        }
        // TODO retrieve the details and check if the student exists or not and verified or not.
        boolean verified = student.isVerified();
        System.out.println("verified " + verified);
        if (verified) {
            integrationImageResource = integrationImageResourceFactory.getImageResource(
                    IntegrationConstant.STUDENT_REGISTERED, REGISTERED);
        } else {
            integrationImageResource = integrationImageResourceFactory.getImageResource(
                    IntegrationConstant.STUDENT_SELF_SUBMITTED, SELFSUBMITTED);
        }
        // Delete Existing image
        deleteDynamicImage(IntegrationConstant.STUDENT_PROCESS, integrationImageResource, admissionNumber);
        addDymanicImage(integrationImageResource, admissionNumber, imageName);
    }

    /* (non-Javadoc)
     * @see com.myschool.integration.processor.common.AbstractDynamicImageProcessor#delete(org.apache.camel.Message, java.lang.String)
     */
    @Override
    public void delete(Message message, String body) throws CommandExecutionException {
        System.out.println("delete()");
        IntegrationImageResource integrationImageResource = null;

        StudentDto student = validateAndeGetStudent(body);
        String admissionNumber = student.getAdmissionNumber();
        // TODO retrieve the details and check if the student exists or not and verified or not.
        boolean verified = student.isVerified();
        if (verified) {
            integrationImageResource = integrationImageResourceFactory.getImageResource(
                    IntegrationConstant.STUDENT_REGISTERED, REGISTERED);
        } else {
            integrationImageResource = integrationImageResourceFactory.getImageResource(
                    IntegrationConstant.STUDENT_SELF_SUBMITTED, SELFSUBMITTED);
        }
        System.out.println("integrationImageResource " + integrationImageResource);
        deleteDynamicImage(IntegrationConstant.STUDENT_PROCESS, integrationImageResource, admissionNumber);
    }

    /**
     * Verify.
     * 
     * @param message the message
     * @param body the body
     * @throws CommandExecutionException the command execution exception
     */
    public void verify(Message message, String body) throws CommandExecutionException {
        System.out.println("verify()");
        IntegrationImageResource selfSubmittedImageResource = null;
        IntegrationImageResource registeredImageResource = null;

        StudentDto student = validateAndeGetStudent(body);
        String admissionNumber = student.getAdmissionNumber();
        System.out.println("admissionNumber=" + admissionNumber);
        // TODO retrieve the details and check if the student exists or not and verified or not.
        boolean verified = student.isVerified();
        System.out.println("verified=" + verified);
        // Student must be verified to register
        if (!verified) {
            throw new CommandExecutionException("Student (" + admissionNumber + ") is not verified. Cannot update image");
        }
        selfSubmittedImageResource = integrationImageResourceFactory.getImageResource(
                IntegrationConstant.STUDENT_SELF_SUBMITTED, SELFSUBMITTED);
        registeredImageResource = integrationImageResourceFactory.getImageResource(
                IntegrationConstant.STUDENT_REGISTERED, REGISTERED);

        moveDynamicImage(IntegrationConstant.STUDENT_PROCESS, selfSubmittedImageResource, registeredImageResource, admissionNumber);
    }

    /**
     * Unverify.
     * 
     * @param message the message
     * @param body the body
     * @throws CommandExecutionException the command execution exception
     */
    public void unverify(Message message, String body) throws CommandExecutionException {
        System.out.println("unverify()");
        IntegrationImageResource selfSubmittedImageResource = null;
        IntegrationImageResource registeredImageResource = null;

        StudentDto student = validateAndeGetStudent(body);
        String admissionNumber = student.getAdmissionNumber();
        System.out.println("admissionNumber=" + admissionNumber);
        // TODO retrieve the details and check if the student exists or not and verified or not.
        boolean verified = student.isVerified();
        System.out.println("verified=" + verified);
        // Student must not be verified to unregister
        if (verified) {
            throw new CommandExecutionException("Student (" + admissionNumber + ") is not verified. Cannot update image");
        }
        selfSubmittedImageResource = integrationImageResourceFactory.getImageResource(
                IntegrationConstant.STUDENT_SELF_SUBMITTED, SELFSUBMITTED);
        registeredImageResource = integrationImageResourceFactory.getImageResource(
                IntegrationConstant.STUDENT_REGISTERED, REGISTERED);
        moveDynamicImage(IntegrationConstant.STUDENT_PROCESS, registeredImageResource, selfSubmittedImageResource, admissionNumber);
    }

    /**
     * Validate ande get student.
     * 
     * @param studentXml the student xml
     * @return the student dto
     * @throws CommandExecutionException the command execution exception
     */
    private StudentDto validateAndeGetStudent(String studentXml) throws CommandExecutionException {
        StudentDto student = (StudentDto) tempUtil.toObject(studentXml);
        if (student == null) {
            throw new CommandExecutionException("Student information is not provided");
        }
        String admissionNumber = student.getAdmissionNumber();
        if (admissionNumber == null || admissionNumber.trim().length() == 0) {
            throw new CommandExecutionException("Admission Number is not provided");
        }
        return student;
    }

}
