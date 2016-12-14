package com.myschool.infra.email.agent;

import java.io.File;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Component;

import com.myschool.common.exception.AgentException;
import com.myschool.common.exception.ConfigurationException;
import com.myschool.common.exception.EmailException;
import com.myschool.common.exception.FileSystemException;
import com.myschool.common.util.PropertiesUtil;
import com.myschool.infra.agent.AbstractAgent;
import com.myschool.infra.email.constants.EmailConstants;
import com.myschool.infra.web.constants.MimeTypes;

/**
 * The Class EmailServerAgent.
 */
@Component
public class EmailServerAgent extends AbstractAgent {

    /* (non-Javadoc)
     * @see com.myschool.infra.agent.Agent#loadConfiguration(java.io.File)
     */
    @Override
    public void loadConfiguration(File configFile)
            throws ConfigurationException {
        try {
            properties = PropertiesUtil.loadProperties(configFile);
        } catch (FileSystemException fileSystemException) {
            throw new ConfigurationException(fileSystemException.getMessage(), fileSystemException);
        }
    }

    /* (non-Javadoc)
     * @see com.myschool.infra.Agent#validate()
     */
    @Override
    public void validate() throws AgentException {
        // Establish a session with the email server and close.
        getEmailSession();
    }

    /**
     * Gets the email session.
     *
     * @return the email session
     */
    private Session getEmailSession() {
        return Session.getDefaultInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(
                        properties.getProperty(EmailConstants.MYSCHOOL_EMAIL_ID),
                        properties.getProperty(EmailConstants.MYSCHOOL_EMAIL_PASSWORD));
            } 
        });
    }

    /**
     * Gets the admin email id.
     *
     * @return the admin email id
     */
    public String getAdminEmailId() {
        return properties.getProperty(EmailConstants.EMAIL_ID_ADMIN);
    }

    /**
     * Gets the support email id.
     *
     * @return the support email id
     */
    public String getSupportEmailId() {
        return properties.getProperty(EmailConstants.EMAIL_ID_SUPPORT);
    }

    /**
     * Gets the test email id.
     *
     * @return the test email id
     */
    public String getTestEmailId() {
        return properties.getProperty(EmailConstants.EMAIL_ID_TEST);
    }

    /**
     * Send email.
     *
     * @param fromAddress the from address
     * @param toAddress the to address
     * @param aSubject the a subject
     * @param aBody the a body
     * @throws EmailException the email exception
     */
    public void sendEmail(String fromAddress, String[] toAddress,
            String aSubject, String aBody) throws EmailException {
        try {
            Session session = getEmailSession();
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);
            // Set From: header field of the header.
            message.setFrom(new InternetAddress(fromAddress));
            // Set To: header field of the header.
            for (int index = 0; index < toAddress.length; index++) {
                addRecipients(toAddress[index], message);
            }
            // Set Subject: header field
            message.setSubject(aSubject);
            // Send the actual HTML message, as big as you like
            message.setContent(aBody, MimeTypes.TEXT_HTML);
            // Send message
            Transport.send(message);
        } catch (AddressException addressException) {
            throw new EmailException(addressException.getMessage(),
                    addressException);
        } catch (MessagingException messagingException) {
            throw new EmailException(messagingException.getMessage(),
                    messagingException);
        }
    }

    /**
     * Send email.
     *
     * @param fromAddress the from address
     * @param toAddress the to address
     * @param aSubject the a subject
     * @param aBody the a body
     * @throws EmailException the email exception
     */
    public void sendEmail(/*String fromAddress, */String toAddress,
            String aSubject, String aBody) throws EmailException {
        try {
            Session session = getEmailSession();
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);
            // Set From: header field of the header.
            //message.setFrom(new InternetAddress(fromAddress));
            // Set To: header field of the header.
            addRecipients(toAddress, message);
            // Set Subject: header field
            message.setSubject(aSubject);
            // Send the actual HTML message, as big as you like
            message.setContent(aBody, MimeTypes.TEXT_HTML);
            // Send message
            Transport.send(message);
        } catch (AddressException addressException) {
            throw new EmailException(addressException.getMessage(),
                    addressException);
        } catch (MessagingException messagingException) {
            throw new EmailException(messagingException.getMessage(),
                    messagingException);
        }
    }

    /**
     * Adds the recipients.
     *
     * @param toAddress the to address
     * @param message the message
     * @throws MessagingException the messaging exception
     * @throws AddressException the address exception
     */
    private void addRecipients(String toAddress, MimeMessage message)
            throws MessagingException, AddressException {
        if (toAddress.indexOf(",") != -1) {
            String[] split = toAddress.split(",");
            for (int index = 0; index < split.length; index++) {
                message.addRecipient(Message.RecipientType.TO,
                        new InternetAddress(split[index]));
            }
        } else {
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(
                    toAddress));
        }
    }

}
