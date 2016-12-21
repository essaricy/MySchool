package com.myschool.email;

import java.io.File;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.io.FileUtils;

public class EmailTest {

    public Session getEmailSession() {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        /*properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("from.email.id.admin", "admin@allstudents.in");
        properties.put("from.email.id.support", "support@allstudents.in");
        properties.put("from.email.id.test", "test@allstudents.in");
        properties.put("myschool.email.id", "allstudents.in@gmail.com");
        properties.put("myschool.email.password", "myschool@2013");*/

        Session session = Session.getInstance(properties,
                new Authenticator() {
                  protected PasswordAuthentication getPasswordAuthentication() {
                      return new PasswordAuthentication("allstudents.in@gmail.com", "myschool@2013");
                  } 
        });
        return session;
        //return Session.getDefaultInstance(properties);
    }

    public void sendEmail(String fromAddress, String[] toAddress,
            String aSubject, String aBody) throws Exception {
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
            message.setContent(aBody, "text/html");
            // Send message
            Transport.send(message);
        } catch (AddressException addressException) {
            throw new Exception(addressException.getMessage(),
                    addressException);
        } catch (MessagingException messagingException) {
            throw new Exception(messagingException.getMessage(),
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
    public void sendEmail(String fromAddress, String toAddress,
            String aSubject, String aBody) throws Exception {
        try {
            Session session = getEmailSession();
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);
            message.addHeader("List-Unsubscribe","<http://192.168.0.1:8080/demo/public/unsubscribe.htm>");
            // Set From: header field of the header.
            message.setFrom(new InternetAddress(fromAddress));
            // Set To: header field of the header.
            addRecipients(toAddress, message);
            // Set Subject: header field
            message.setSubject(aSubject);
            // Send the actual HTML message, as big as you like
            message.setContent(aBody, "text/html");
            // Send message
            Transport.send(message);
        } catch (AddressException addressException) {
            throw new Exception(addressException.getMessage(),
                    addressException);
        } catch (MessagingException messagingException) {
            throw new Exception(messagingException.getMessage(),
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

    public void transform(File xslFile, File xmlFile, File outputFile) throws TransformerException {
        TransformerFactory factory = TransformerFactory.newInstance();
        StreamSource xslStream = new StreamSource(xslFile);
        Transformer transformer = factory.newTransformer(xslStream);

        StreamSource in = new StreamSource(xmlFile);
        StreamResult out = new StreamResult(outputFile);
        transformer.transform(in, out);
    }

    public static void main(String[] args) throws Exception {
        EmailTest emailTest = new EmailTest();
        //emailTest.sendAllEmployeeTemplates();
        //emailTest.sendAllStudentTemplates();

        File dir = new File("C:\\Users\\Srikanth\\Desktop\\myschool\\email-templates\\designs\\");
        //File htmlFile = new File(dir, "Lawn_Newsletter\\6\\mailer.html");
        //emailTest.sendEmailFromFile("Lawn_Newsletter", htmlFile);

        //File dirNew = new File(dir, "New");
        //String[] files = {"antwort-master/single-column/build.html", "antwort-master/three-cols-images/build.html", "antwort-master/two-cols-simple/build.html"};
        //String[] files = {"basic/basic/index.html"};
        //String[] files = {"blitz/blitz_darkblue/index.html", "blitz/blitz_green/index.html", "blitz/blitz_lightblue/index.html", "blitz/blitz_red/index.html"};
        //String[] files = {"clickme/index.html"};
        //String[] files  = {"email-templates-master/general.html", "email-templates-master/explorational.html", "email-templates-master/promotional.html"};
        //String[] files  = {"fabulous/fabulous_blue/index.html", "fabulous/fabulous_green/index.html", "fabulous/fabulous_pink/index.html", "fabulous/fabulous_red/index.html"};
        //String[] files  = {"fabulous/fabulous_blue/index.html", "fabulous/fabulous_green/index.html", "fabulous/fabulous_pink/index.html", "fabulous/fabulous_red/index.html"};
        //String[] files = {"postmark-templates-master/templates/resetpassword.html", "postmark-templates-master/templates/welcome.html"};
        //String[] files = {"postmark-templates-master/templates/resetpassword.html", "postmark-templates-master/templates/welcome.html"};

        
        String[] files = {"MySchool/Employee/AttendanceReport.html", "MySchool/Employee/EmailConfirmation.html",
                "MySchool/Employee/LeaveNotification.html", "MySchool/Employee/Payslip.html",
                "MySchool/Employee/RegistrationComplete.html"};
        for (String filepath : files) {
            File htmlFile = new File(dir, filepath);
            System.out.println("htmlFile exists? " + htmlFile.exists());
            String fileName = null;
            //String fileName = htmlFile.getName();
            fileName = filepath.substring(filepath.indexOf("/") + 1, filepath.indexOf(".")).replaceAll("/", " - ");
            System.out.println("fileName=" + fileName);
            emailTest.sendEmailFromFile(fileName, htmlFile);
        }
        //File htmlFile = new File(dir, "MySchool/AccountActivation.html");
        //File htmlFile = new File(dir, "Homemade.html");
        //File htmlFile = new File(dir, "mosaico.io.html");
        //System.out.println("htmlFile exists? " + htmlFile.exists());
        //emailTest.sendEmailFromFile("Account Activation", htmlFile);

    }

    public void sendAllStudentTemplates() {
        String module = "STUDENT";
        String studentEmails[] = {"ExamResult", "FeeSummary", "LeaveNotification", "MonthlyAttendanceReport", "Registration", "YearlyAttendanceReport"};
        sendAllTemplates(module, studentEmails);
    }

    public void sendAllEmployeeTemplates() {
        String module = "EMPLOYEE";
        String employeeEmails[] = {"LeaveNotification", "MonthlyAttendanceReport", "Payslip", "Registration", "YearlyAttendanceReport"};
        sendAllTemplates(module, employeeEmails);
    }

    private void sendAllTemplates(String module, String[] emails) {

        for (String email : emails) {
            try {
                String subject = module + "-" + email;
                System.out.println("subject=" + subject);

                File xslFile = new File("C:\\Users\\Srikanth\\Desktop\\myschool\\email-templates\\templates\\notification\\email\\" + module.toLowerCase() + "\\" + email + ".xsl");
                File xmlFile = new File("C:\\Users\\Srikanth\\Desktop\\myschool\\email-templates\\data\\" + module.toLowerCase() + "\\" + email + ".xml");
                File outputFile = new File("C:\\Users\\Srikanth\\Desktop\\myschool\\email-templates\\output\\" + subject + ".html");


                transform(xslFile, xmlFile, outputFile);
                sendEmailFromFile(subject, outputFile);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
            
        }
    }

    private void sendEmailFromFile(String subject, File outputFile) throws Exception {
        sendEmailFromFile("srikanthkumar.ragi@gmail.com", subject, outputFile);
        //sendEmailFromFile("srikanth.ragi@partners.hbc.ca", subject, outputFile);
    }

    private void sendEmailFromFile(String to, String subject, File outputFile) throws Exception {
        String templateText = FileUtils.readFileToString(outputFile);
        sendEmail("allstudents.in@gmail.com", to, subject, templateText);
        System.out.println("Email has been sent.");
    }

}
