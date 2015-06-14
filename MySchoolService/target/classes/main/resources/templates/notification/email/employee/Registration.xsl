<?xml version="1.0"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:template match="/Message">
        <html xmlns="http://www.w3.org/1999/xhtml">
            <head>
                <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
                <title>Registration Complete</title>
                <link type="text/css" rel="stylesheet" href="/demo/styles/common-styles.css" />
                <style>
                    body {
                        font-family: Arial;
                        font-size: 12px;
                    }
                    .formLabel {
                        text-align: left;
                        color: #666666;
                    }
                    .formLabel .mandatoryItalic {
                        text-align: left;
                        font-style: italic;
                        color: red;
                    }
                    .formTable .titleLeft {
                        text-align: left;
                        color: #000000;
                        font-family: Arial;
                        font-size: 12px;
                        font-weight: bold;
                    }
                    .formTable .titleRight {
                        text-align: right;
                        color: #000000;
                        font-family: Arial;
                        font-size: 12px;
                        font-weight: bold;
                    }
                    .formTable .value {
                        text-align: left;
                        color: #666666;
                        font-family: Arial;
                        font-size: 12px;
                    }
                </style>
            </head>
            <body>
                <xsl:variable name="MySchool" select="MySchool" />
                <xsl:variable name="ToPerson" select="ToPerson" />
                <xsl:variable name="ToPerson" select="ToPerson" />
                <xsl:variable name="Employee" select="Content[@class='Employee']" />
                <xsl:variable name="PersonalDetails" select="$Employee" />
                <xsl:variable name="FamilyMembers" select="$Employee/FamilyMembers" />
                <xsl:variable name="RegisteredClass" select="$Employee/RegisteredClass" />
                <xsl:variable name="DocumentsSubmitted" select="$Employee/DocumentsSubmitted" />

                <div id="PageContainer">
                    <div id="PageHeader">
                        <!-- Header start -->
                        <table cellpadding="0" cellspacing="0" class="headerTable" border="0">
                            <tr>
                                <td width="109px" style="padding-left:8px;padding-top:2px;">
                                    <img>
                                        <xsl:attribute name="src">
                                            <xsl:value-of select="$MySchool/@WebUrl" />/image/getImage.htm?type=logo</xsl:attribute>
                                            <xsl:attribute name="class">logo</xsl:attribute>
                                            <xsl:attribute name="alt"><xsl:value-of select="$MySchool/@WebUrl" /></xsl:attribute>
                                    </img>
                                </td>
                                <td>
                                    <table cellpadding="0" cellspacing="0" width="100%" border="0">
                                        <tr>
                                            <td style="color:white;font-size:30px;letter-spacing:2px;word-spacing:4px;text-transform:uppercase;">
                                                <xsl:value-of select="OrganizationProfile/@OrganizationName" />
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                                <td width="*" style="color:white;font-size:14px;font-weight:bold;" valign="bottom" align="right">
                                    Student Information Management System
                                </td>
                            </tr>
                        </table>
                        <!-- Header end -->
                    </div>
                    <div id="PageBody">
                        <!-- Body start -->
                        <strong>
                            Dear <xsl:value-of select="$ToPerson/@FirstName" /><xsl:text> </xsl:text><xsl:value-of select="$ToPerson/@LastName" />,
                        </strong>

                        <p class="formLabel">
                            We are pleased to inform you that your employement details have been processed successfully at <xsl:value-of select="OrganizationProfile/@OrganizationName" />. Your employee number is 
                            <strong>
                                <xsl:value-of select="$Employee/@EmployeeNumber" />
                            </strong>.
                        </p>

                        <p class="formLabel">
                            Use the below credentials to login to our
                            <a>
                                <xsl:attribute name="href">
                                    <xsl:value-of select="$MySchool/@WebUrl" />
                                </xsl:attribute>
                                <xsl:value-of select="OrganizationProfile/@Name" />web site
                            </a>
                        </p>

                        <p class="formLabel">
                            User Name:
                            <strong>
                                <xsl:value-of select="$Employee/@EmployeeNumber" />
                            </strong>
                            <br />
                            Password: Your DOB as in records (DD/MM/YYYY). For example, if your Date Of Birth is 12/12/2000 then the password will be 12122000.
                            If your child's Date Of Birth is 01/01/2000 then the password will be 112000.
                        </p>

                        <p class="formLabel">Your employment details with our records are as follows</p>

                        <!-- Personal Details -->
                        <table width="100%" class="formTable" border="0" cellspacing="0" cellpadding="10">
                            <caption class="dataTableCaption">Personal Details</caption>
                            <tbody>
                                <tr>
                                    <td class="titleRight" width="50%">First Name</td>
                                    <td class="value">
                                        <xsl:value-of select="$PersonalDetails/@FirstName" />
                                    </td>
                                </tr>
                                <tr>
                                    <td class="titleRight">Middle Name</td>
                                    <td class="value">
                                        <xsl:value-of select="$PersonalDetails/@MiddleName" />
                                    </td>
                                </tr>
                                <tr>
                                    <td class="titleRight">Last Name</td>
                                    <td class="value">
                                        <xsl:value-of select="$PersonalDetails/@LastName" />
                                    </td>
                                </tr>
                                <tr>
                                    <td class="titleRight">Gender</td>
                                    <td class="value">
                                        <xsl:call-template name="get_gender">
                                            <xsl:with-param name="value" select="$PersonalDetails/@Gender" />
                                        </xsl:call-template>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="titleRight">Date Of Birth</td>
                                    <td class="value">
                                        <xsl:value-of select="$PersonalDetails/@DateOfBirth" />
                                    </td>
                                </tr>
                                <tr>
                                    <td class="titleRight">Address</td>
                                    <td class="value">
                                        <xsl:value-of select="$PersonalDetails/@Address" />
                                    </td>
                                </tr>
                                <tr>
                                    <td class="titleRight">EmailId</td>
                                    <td class="value">
                                        <xsl:value-of select="$PersonalDetails/@EmailId" />
                                    </td>
                                </tr>
                                <tr>
                                    <td class="titleRight">Mobile Number</td>
                                    <td class="value">
                                        <xsl:value-of select="$PersonalDetails/@MobileNumber" />
                                    </td>
                                </tr>
                                <tr>
                                    <td class="titleRight">Emergency Contact Number</td>
                                    <td class="value">
                                        <xsl:value-of select="$PersonalDetails/@EmergencyContactNumber" />
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                        <p />

                        <!-- Professional Details -->
                        <table width="100%" class="formTable" border="0" cellspacing="0" cellpadding="10">
                            <caption class="dataTableCaption">Professional Details</caption>
                            <tbody>
                                <tr>
                                    <td class="titleRight" width="50%">Qualification</td>
                                    <td class="value">
                                        <xsl:value-of select="$PersonalDetails/@Qualification" />
                                    </td>
                                </tr>
                                <tr>
                                    <td class="titleRight">Years Of Experience</td>
                                    <td class="value">
                                        <xsl:value-of select="$PersonalDetails/@YearsOfExperience" /> years
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                        <p />

                        <!-- Employee Details -->
                        <table width="100%" class="formTable" border="0" cellspacing="0" cellpadding="10">
                            <caption class="dataTableCaption">Employment Details</caption>
                            <tbody>
                                <tr>
                                    <td class="titleRight">Employee Number</td>
                                    <td class="value">
                                        <xsl:value-of select="$Employee/@EmployeeNumber" />
                                    </td>
                                    <td rowspan="4" align="right" valign="top" width="20%">
                                        <img>
                                            <xsl:attribute name="src">
                                                <xsl:value-of select="$MySchool/@WebUrl" />/image/getImage.htm?type=employee&amp;contentId=<xsl:value-of select="$Employee/@EmployeeNumber" /></xsl:attribute>
                                                <xsl:attribute name="class">passportSizeImage</xsl:attribute>
                                                <xsl:attribute name="alt"><xsl:value-of select="$MySchool/@WebUrl" /></xsl:attribute>
                                        </img>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="titleRight">Designation</td>
                                    <td class="value">
                                        <xsl:value-of select="$Employee/Designation/@Designation" />
                                    </td>
                                </tr>
                                <tr>
                                    <td class="titleRight">Date Of Joining</td>
                                    <td class="value">
                                        <xsl:value-of select="$PersonalDetails/@DateOfJoining" />
                                    </td>
                                </tr>
                                <tr>
                                    <td class="titleRight">Service End Date</td>
                                    <td class="value">
                                        <xsl:value-of select="$PersonalDetails/@ServiceEndDate" />
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                        <p />

                        <p class="formLabel">
                            Now you can find out your attendance, track leaves, payslips and many more
                            <a>
                                <xsl:attribute name="href">
                                    <xsl:value-of select="$MySchool/@WebUrl" />
                                </xsl:attribute>
                                <xsl:value-of select="OrganizationProfile/@Name" />
                                online
                            </a>
                        </p>

                        <p class="formLabel">
                            If you have any questions or requires any changes to the
                            information in our records, you can
                            <a>
                                <xsl:attribute name="href">mailto:<xsl:value-of select="$RegisteredClass/Branch/@EmailId" /></xsl:attribute>
                                e-mail
                            </a>
                            us or call us on
                            <xsl:value-of select="$RegisteredClass/Branch/@PhoneNumber" />
                            any day, any time.
                        </p>

                        <p class="formLabel">
                            <label class="mandatoryItalic">*** We recommend you to change your password
                                immediately for many security reasons.
                            </label>
                        </p>

                        <p>
                            Sincerely,
                            <br />
                            <a>
                                <xsl:attribute name="href"><xsl:value-of select="$MySchool/@WebUrl" /></xsl:attribute>
                                <xsl:value-of select="OrganizationProfile/@OrganizationName" />
                            </a>
                        </p>

                        <p class="formLabel">This is a system generated e-mail. Hence, please do not
                            reply to this e-mail.</p>
                        <!-- Body end -->
                    </div>
                </div>
            </body>
        </html>
    </xsl:template>

    <xsl:template name="get_gender">
        <xsl:param name="value" />
        <xsl:if test="$value = 'M'">
            Male
        </xsl:if>
        <xsl:if test="$value = 'F'">
            Female
        </xsl:if>
    </xsl:template>

    <xsl:template name="show_yes_no">
        <xsl:param name="value" />
        <xsl:param name="webUrl" />

        <xsl:if test="$value = 'true'">
            <img>
                <xsl:attribute name="src"><xsl:value-of select="$webUrl" />/images/icons/checked.png</xsl:attribute>
                <xsl:attribute name="class">iconImage</xsl:attribute>
                <xsl:attribute name="alt"><xsl:value-of select="$webUrl" /></xsl:attribute>
            </img>
        </xsl:if>
        <xsl:if test="$value = 'false'">
            No
        </xsl:if>
    </xsl:template>

</xsl:stylesheet>