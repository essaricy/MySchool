package com.myschool.email;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModelException;

public class ContentGenerator {

    //Freemarker configuration object
    Configuration cfg = new Configuration(Configuration.VERSION_2_3_25);

    public ContentGenerator() {
        try {
            cfg.setDirectoryForTemplateLoading(new File("D:/projects/GitHub/MySchool/MySchoolService/src/main/resources/config/template"));
            cfg.setSharedVariable("organization", getOrganization());
            cfg.setSharedVariable("webProfile", getWebProfile());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateModelException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        //String templateName = "ChangePasswordRequest.html";
        String templateName = "ChangePasswordComplete.html";

        ContentGenerator generator = new ContentGenerator();

        Person person = new Person();
        person.setFirstName("Srikanth");
        person.setLastName("Ragi");
        //person.setEmailId("srikanthkumar.ragi@gmail.com");
        //person.setEmailId("srikanth.ragi@partners.hbc.ca");

        /*person.setFirstName("Arun");
        person.setLastName("Jyothi");
        person.setEmailId("arun67jyothi@gmail.com");*/

        generator.send(templateName, person);
    }

    private void send(String templateName, Person person) {
        //Load template from source folder
        try {
            Message message = new Message();
            message.setId(UUID.randomUUID().toString());
            message.setTimestamp(System.currentTimeMillis());
            message.setSendTo(person);
            message.setType(templateName);

            Template template = cfg.getTemplate("notification/email/acl/" + templateName);

            // Build the data-model
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("message", message);

            System.out.println("messageId=" + message.getId());
            // Console output
            /*Writer out = new OutputStreamWriter(System.out);
            template.process(data, out);
            out.flush();*/
            /*// File output
            File output = new File("C:\\Users\\Srikanth\\Desktop\\myschool\\email-templates\\output");
            Writer file = new FileWriter (new File(output, templateName));
            template.process(data, file);
            file.flush();
            file.close();*/

            StringWriter stringWriter = new StringWriter();
            template.process(data, stringWriter);
            EmailTest emailTest = new EmailTest();
            System.out.println(stringWriter.toString());
            emailTest.sendEmail("MySchool", person.getEmailId(), "Forgot Password?", stringWriter.toString());
            System.out.println("Email sent");

        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static WebProfile getWebProfile() {
        /*
         * ${myschool.logo.directLink} = https://docs.google.com/uc?id=0BxiOyY7BD-0XTGhIQVRLYy0wU00
         * ${myschool.mySchoolName} Your School Name Here
         * ${myschool.webUrl} = http://192.168.0.101:8080/demo
         * 
         * sitepage                                                             http://192.168.0.101:8080/demo
         * contactus                                                            http://192.168.0.101:8080/demo/public/contactUs.htm
         * unsubscribe                                                          http://192.168.0.101:8080/demo/public/unsubscribe.htm
         * 
         * logo     https://docs.google.com/uc?id=0BxiOyY7BD-0XTGhIQVRLYy0wU00  http://192.168.0.101:8080/demo
         * facebook https://docs.google.com/uc?id=0BxiOyY7BD-0XUmdDVjZtNjdlU2s  http://192.168.0.101:8080/demo/social/facebook
         * twitter  https://docs.google.com/uc?id=0BxiOyY7BD-0Xak1tMUQxRllFcUk  http://192.168.0.101:8080/demo/social/twitter
         * g+       https://docs.google.com/uc?id=0BxiOyY7BD-0XdjVvVUV4ZnpDWmM  http://192.168.0.101:8080/demo/social/googleplus
         * */

        WebProfile webProfile = new WebProfile();

        String webUrl = "http://192.168.0.101:8080/demo";

        WebContent changePassword = new WebContent();
        changePassword.setContentLink(webUrl + "/acl/changePassword.htm");
        webProfile.setChangePassword(changePassword);

        WebContent contactUs = new WebContent();
        contactUs.setContentLink(webUrl + "/public/contactUs.htm");
        webProfile.setContactUs(contactUs);

        WebContent facebook = new WebContent();
        facebook.setContentLink(webUrl + "/social/facebook");
        facebook.setOriginalImageLink("https://docs.google.com/uc?id=0BxiOyY7BD-0XUmdDVjZtNjdlU2s");
        webProfile.setFacebook(facebook);

        WebContent twitter = new WebContent();
        twitter.setContentLink(webUrl + "/social/twitter");
        twitter.setOriginalImageLink("https://docs.google.com/uc?id=0BxiOyY7BD-0Xak1tMUQxRllFcUk");
        webProfile.setTwitter(twitter);

        WebContent googleplus = new WebContent();
        googleplus.setContentLink(webUrl + "/social/googleplus");
        googleplus.setOriginalImageLink("https://docs.google.com/uc?id=0BxiOyY7BD-0XdjVvVUV4ZnpDWmM");
        webProfile.setGoogleplus(googleplus);

        WebContent logo = new WebContent();
        logo.setContentLink(webUrl);
        logo.setOriginalImageLink("https://docs.google.com/uc?id=0BxiOyY7BD-0XTGhIQVRLYy0wU00");
        webProfile.setLogo(logo);

        WebContent unsubscribe = new WebContent();
        unsubscribe.setContentLink(webUrl + "/public/unsubscribe.htm");
        webProfile.setUnsubscribe(unsubscribe);

        return webProfile;
    }

    public static WebContent getWebContent() {
        WebContent webContent = new WebContent();
        webContent.setContentLink("ContentLink");
        webContent.setOriginalImageLink("originalImageLink");
        webContent.setPassportImageLink("passportImageLink");
        webContent.setThumbnailImageLink("thumbnailImageLink");
        return webContent;
    }

    public static Organization getOrganization() {
        Organization organizationProfile = new Organization();
        organizationProfile.setAddress("Address");
        //organizationProfile.setCurrentAcademicYear("2016");
        organizationProfile.setFaxNumber("Fax");
        organizationProfile.setName("Organization Name");
        organizationProfile.setPhoneNumber("Phone Number");
        return organizationProfile;
    }

}
