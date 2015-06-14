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
                <xsl:variable name="Student" select="Content[@class='Student']" />
                <xsl:variable name="PersonalDetails" select="$Student/PersonalDetails" />
                <xsl:variable name="FamilyMembers" select="$Student/FamilyMembers" />
                <xsl:variable name="RegisteredClass" select="$Student/RegisteredClass" />
                <xsl:variable name="DocumentsSubmitted" select="$Student/DocumentsSubmitted" />

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
                            We are pleased to inform you that your child's,
                            <strong>
                                <xsl:value-of select="$PersonalDetails/@FirstName" />
                                <xsl:text> </xsl:text>
                                <xsl:value-of select="$PersonalDetails/@LastName" />
                            </strong>
                            enrollment with admission number
                            <strong>
                                (<xsl:value-of select="$Student/@AdmissionNumber" />)
                            </strong>
                            has been completed successfully at
                            <xsl:value-of select="OrganizationProfile/@OrganizationName" />.
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
                                <xsl:value-of select="$Student/@AdmissionNumber" />
                            </strong>
                            <br />
                            Password: Your child's DOB as in records (DD/MM/YYYY). For
                            example, if your child's Date Of Birth is 12/12/2000 then
                            the password will be 12122000.
                            If your child's Date Of Birth is 01/01/2000 then the password
                            will be 112000.
                        </p>

                        <p class="formLabel">Your admission details with our records are as follows</p>

                        <!-- Personal Details -->
                        <table width="100%" class="formTable" border="0" cellspacing="0" cellpadding="10">
                            <caption class="dataTableCaption">Personal Details</caption>
                            <tbody>
                                <tr>
                                    <td class="titleRight">First Name</td>
                                    <td class="value">
                                        <xsl:value-of select="$PersonalDetails/@FirstName" />
                                    </td>
                                </tr>
                            </tbody>
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
                                <td class="titleRight">Religion</td>
                                <td class="value">
                                    <xsl:value-of select="$PersonalDetails/@Religion" />
                                </td>
                            </tr>
                            <tr>
                                <td class="titleRight">Caste</td>
                                <td class="value">
                                    <xsl:value-of select="$PersonalDetails/@Caste" />
                                </td>
                            </tr>
                            <tr>
                                <td class="titleRight">Nationality</td>
                                <td class="value">
                                    <xsl:value-of select="$PersonalDetails/@Nationality" />
                                </td>
                            </tr>
                            <tr>
                                <td class="titleRight">Mother Tongue</td>
                                <td class="value">
                                    <xsl:value-of select="$PersonalDetails/@MotherTongue" />
                                </td>
                            </tr>
                            <tr>
                                <td class="titleRight">Mobile Number</td>
                                <td class="value">
                                    <xsl:value-of select="$PersonalDetails/@MobileNumber" />
                                </td>
                            </tr>
                            <tr>
                                <td class="titleRight">Blood Group</td>
                                <td class="value">
                                    <xsl:value-of select="$PersonalDetails/@BloodGroup" />
                                </td>
                            </tr>
                            <tr>
                                <td class="titleRight">Permanent Address</td>
                                <td class="value">
                                    <xsl:value-of select="$PersonalDetails/@PermanentAddress" />
                                </td>
                            </tr>
                            <tr>
                                <td class="titleRight">Correspondence Address</td>
                                <td class="value">
                                    <xsl:value-of select="$PersonalDetails/@CorrespondenceAddress" />
                                </td>
                            </tr>
                            <tr>
                                <td class="titleRight">Identification Marks</td>
                                <td class="value">
                                    <xsl:value-of select="$PersonalDetails/@IdentificationMarks" />
                                </td>
                            </tr>
                        </table>
                        <p />

                        <!-- Family Details -->
                        <table width="100%" class="formTable" border="0"
                            cellspacing="0" cellpadding="10">
                            <caption class="dataTableCaption">Family Details</caption>
                            <xsl:if test="$FamilyMembers">
                                <tbody>
                                    <tr>
                                        <td class="titleLeft">Relationship</td>
                                        <td class="titleLeft">Name</td>
                                        <td class="titleLeft">Occupation</td>
                                        <td class="titleLeft">MobileNumber</td>
                                        <td class="titleLeft">Email ID</td>
                                        <td class="titleLeft">AvailEmail</td>
                                        <td class="titleLeft">AvailSMS</td>
                                    </tr>
                                    <xsl:for-each select="$FamilyMembers/FamilyMember">
                                        <tr>
                                            <td class="value">
                                                <xsl:value-of select="@Relationship" />
                                            </td>
                                            <td class="value">
                                                <xsl:value-of select="@Name" />
                                            </td>
                                            <td class="value">
                                                <xsl:value-of select="@Occupation" />
                                            </td>
                                            <td class="value">
                                                <xsl:value-of select="@MobileNumber" />
                                            </td>
                                            <td class="value">
                                                <xsl:value-of select="@EmailId" />
                                            </td>
                                            <td class="value">
                                                <xsl:call-template name="show_yes_no">
                                                    <xsl:with-param name="value" select="@AvailEmail" />
                                                    <xsl:with-param name="webUrl" select="$MySchool/@WebUrl" />
                                                </xsl:call-template>
                                            </td>
                                            <td class="value">
                                                <xsl:call-template name="show_yes_no">
                                                    <xsl:with-param name="value" select="@AvailSMS" />
                                                    <xsl:with-param name="webUrl" select="$MySchool/@WebUrl" />
                                                </xsl:call-template>
                                            </td>
                                        </tr>
                                    </xsl:for-each>
                                </tbody>
                            </xsl:if>
                        </table>
                        <p />

                        <!-- Admission Details -->
                        <table width="100%" class="formTable" border="0"
                            cellspacing="0" cellpadding="10">
                            <caption class="dataTableCaption">Admission Details</caption>
                            <tr>
                                <td class="titleRight">Admission Number</td>
                                <td class="value">
                                    <xsl:value-of select="$Student/@AdmissionNumber" />
                                </td>
                                <td rowspan="8" align="right" valign="top" width="20%">
                                    <img>
                                        <xsl:attribute name="src">
                                            <xsl:value-of select="$MySchool/@WebUrl" />/image/getImage.htm?type=student&amp;contentId=<xsl:value-of select="$Student/@AdmissionNumber" /></xsl:attribute>
                                            <xsl:attribute name="class">passportSizeImage</xsl:attribute>
                                            <xsl:attribute name="alt"><xsl:value-of select="$MySchool/@WebUrl" /></xsl:attribute>
                                    </img>
                                </td>
                            </tr>
                            <tr>
                                <td class="titleRight">Branch</td>
                                <td class="value">
                                    <xsl:value-of select="$RegisteredClass/Branch/@Description" />
                                </td>
                            </tr>
                            <tr>
                                <td class="titleRight">School</td>
                                <td class="value">
                                    <xsl:value-of select="$RegisteredClass/School/@SchoolName" />
                                </td>
                            </tr>
                            <tr>
                                <td class="titleRight">Class</td>
                                <td class="value">
                                    <xsl:value-of select="$RegisteredClass/Class/@ClassName" />
                                </td>
                            </tr>
                            <tr>
                                <td class="titleRight">Section</td>
                                <td class="value">
                                    <xsl:value-of select="$RegisteredClass/Section/@SectionName" />
                                </td>
                            </tr>
                            <tr>
                                <td class="titleRight">Medium</td>
                                <td class="value">
                                    <xsl:value-of select="$RegisteredClass/Medium/@Description" />
                                </td>
                            </tr>
                            <tr>
                                <td class="titleRight">Admission Date</td>
                                <td class="value">
                                    <xsl:value-of select="$Student/@DateOfJoining" />
                                </td>
                            </tr>
                            <tr>
                                <td class="titleRight">Remarks</td>
                                <td class="value">
                                    <xsl:value-of select="$Student/@Remarks" />
                                </td>
                            </tr>
                        </table>
                        <p />

                        <!-- Document Details -->
                        <table width="100%" class="formTable" border="0"
                            cellspacing="0" cellpadding="10">
                            <caption class="dataTableCaption">Document Details</caption>
                            <xsl:if test="$DocumentsSubmitted">
                                <tr>
                                    <td class="titleLeft">Document Name</td>
                                    <td class="titleLeft">Submitted?</td>
                                </tr>
                                <xsl:for-each select="$DocumentsSubmitted/Document">
                                    <tr>
                                        <td class="value">
                                            <xsl:value-of select="@DocumentName" />
                                        </td>
                                        <td class="value">
                                            <xsl:call-template name="show_yes_no">
                                                <xsl:with-param name="value" select="@Submitted" />
                                                <xsl:with-param name="webUrl" select="$MySchool/@WebUrl" />
                                            </xsl:call-template>
                                        </td>
                                    </tr>
                                </xsl:for-each>
                            </xsl:if>
                        </table>

                        <p class="formLabel">
                            <label>In case any of the above certificate is not furnished
                                along with the application form, the same will be submitted
                                within one month from the date of admission. Otherwise admission
                                will be rendered invalid and canceled.</label>
                        </p>

                        <p class="formLabel">
                            Now you can find out your child's attendance, progress report,
                            fee particulars and many more
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