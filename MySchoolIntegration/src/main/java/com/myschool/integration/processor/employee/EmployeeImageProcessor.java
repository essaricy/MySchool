package com.myschool.integration.processor.employee;

import org.apache.camel.Message;
import org.springframework.stereotype.Component;

import com.myschool.employee.dto.EmployeeDto;
import com.myschool.integration.agent.IntegrationImageResource;
import com.myschool.integration.constant.IntegrationConstant;
import com.myschool.integration.exception.CommandExecutionException;
import com.myschool.integration.exception.CommandProcessException;
import com.myschool.integration.processor.common.AbstractDynamicImageProcessor;

/**
 * The Class EmployeeImageProcessor.
 */
@Component("EmployeeImageProcessor")
public class EmployeeImageProcessor extends AbstractDynamicImageProcessor {

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

        EmployeeDto employee = validateAndeGetEmployee(body);
        String employeeNumber = employee.getEmployeeNumber();
        String imageName = employee.getImageName();
        if (imageName == null || imageName.trim().length() == 0) {
            throw new CommandExecutionException("Employee imageName is not provided");
        }
        // TODO retrieve the details and check if the employee exists or not and verified or not.
        boolean verified = employee.isVerified();
        System.out.println("verified " + verified);
        if (verified) {
            integrationImageResource = integrationImageResourceFactory.getImageResource(
                    IntegrationConstant.EMPLOYEE_REGISTERED, REGISTERED);
        } else {
            integrationImageResource = integrationImageResourceFactory.getImageResource(
                    IntegrationConstant.EMPLOYEE_SELF_SUBMITTED, SELFSUBMITTED);
        }
        addDymanicImage(integrationImageResource, employeeNumber, imageName);
    }

    /* (non-Javadoc)
     * @see com.myschool.integration.processor.common.AbstractDynamicImageProcessor#update(org.apache.camel.Message, java.lang.String)
     */
    @Override
    public void update(Message message, String body) throws CommandExecutionException {
        System.out.println("update()");
        IntegrationImageResource integrationImageResource = null;

        EmployeeDto employee = validateAndeGetEmployee(body);
        String employeeNumber = employee.getEmployeeNumber();
        String imageName = employee.getImageName();
        if (imageName == null || imageName.trim().length() == 0) {
            throw new CommandExecutionException("Employee imageName is not provided");
        }
        // TODO retrieve the details and check if the employee exists or not and verified or not.
        boolean verified = employee.isVerified();
        System.out.println("verified " + verified);
        if (verified) {
            integrationImageResource = integrationImageResourceFactory.getImageResource(
                    IntegrationConstant.EMPLOYEE_REGISTERED, REGISTERED);
        } else {
            integrationImageResource = integrationImageResourceFactory.getImageResource(
                    IntegrationConstant.EMPLOYEE_SELF_SUBMITTED, SELFSUBMITTED);
        }
        // Delete Existing image
        deleteDynamicImage(IntegrationConstant.EMPLOYEE_PROCESS, integrationImageResource, employeeNumber);
        addDymanicImage(integrationImageResource, employeeNumber, imageName);
    }

    /* (non-Javadoc)
     * @see com.myschool.integration.processor.common.AbstractDynamicImageProcessor#delete(org.apache.camel.Message, java.lang.String)
     */
    @Override
    public void delete(Message message, String body) throws CommandExecutionException {
        System.out.println("delete()");
        IntegrationImageResource integrationImageResource = null;

        EmployeeDto employee = validateAndeGetEmployee(body);
        String employeeNumber = employee.getEmployeeNumber();
        // TODO retrieve the details and check if the employee exists or not and verified or not.
        boolean verified = employee.isVerified();
        if (verified) {
            integrationImageResource = integrationImageResourceFactory.getImageResource(
                    IntegrationConstant.EMPLOYEE_REGISTERED, REGISTERED);
        } else {
            integrationImageResource = integrationImageResourceFactory.getImageResource(
                    IntegrationConstant.EMPLOYEE_SELF_SUBMITTED, SELFSUBMITTED);
        }
        System.out.println("integrationImageResource " + integrationImageResource);
        deleteDynamicImage(IntegrationConstant.EMPLOYEE_PROCESS, integrationImageResource, employeeNumber);
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

        EmployeeDto employee = validateAndeGetEmployee(body);
        String employeeNumber = employee.getEmployeeNumber();
        // TODO retrieve the details and check if the employee exists or not and verified or not.
        boolean verified = employee.isVerified();
        // Employee must be verified to register
        if (!verified) {
            throw new CommandExecutionException("Employee (" + employeeNumber + ") is not verified. Cannot update image");
        }
        selfSubmittedImageResource = integrationImageResourceFactory.getImageResource(
                IntegrationConstant.EMPLOYEE_SELF_SUBMITTED, SELFSUBMITTED);
        registeredImageResource = integrationImageResourceFactory.getImageResource(
                IntegrationConstant.EMPLOYEE_REGISTERED, REGISTERED);

        moveDynamicImage(IntegrationConstant.EMPLOYEE_PROCESS, selfSubmittedImageResource, registeredImageResource, employeeNumber);
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

        EmployeeDto employee = validateAndeGetEmployee(body);
        String employeeNumber = employee.getEmployeeNumber();
        // TODO retrieve the details and check if the employee exists or not and verified or not.
        boolean verified = employee.isVerified();
        // Employee must not be verified to unregister
        if (verified) {
            throw new CommandExecutionException("Employee (" + employeeNumber + ") is not verified. Cannot update image");
        }
        selfSubmittedImageResource = integrationImageResourceFactory.getImageResource(
                IntegrationConstant.EMPLOYEE_SELF_SUBMITTED, SELFSUBMITTED);
        registeredImageResource = integrationImageResourceFactory.getImageResource(
                IntegrationConstant.EMPLOYEE_REGISTERED, REGISTERED);
        moveDynamicImage(IntegrationConstant.EMPLOYEE_PROCESS, registeredImageResource, selfSubmittedImageResource, employeeNumber);
    }

    /**
     * Validate ande get employee.
     * 
     * @param employeeXml the employee xml
     * @return the employee dto
     * @throws CommandExecutionException the command execution exception
     */
    private EmployeeDto validateAndeGetEmployee(String employeeXml) throws CommandExecutionException {
        EmployeeDto employee = (EmployeeDto) tempUtil.toObject(employeeXml);
        if (employee == null) {
            throw new CommandExecutionException("Employee information is not provided");
        }
        String employeeNumber = employee.getEmployeeNumber();
        if (employeeNumber == null || employeeNumber.trim().length() == 0) {
            throw new CommandExecutionException("Employee Number is not provided");
        }
        return employee;
    }

}
