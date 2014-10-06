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
                <xsl:variable name="Student" select="Content/Student" />
                <xsl:variable name="PersonalDetails" select="$Student/PersonalDetails" />
                <xsl:variable name="RegisteredClass" select="$Student/RegisteredClass" />
                <xsl:variable name="Leave" select="Content/Leave" />

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
                            We are pleased to inform you that your leave request has been processed successfully.<br />
                            More information on your leave request as follows.
                        </p>

                        <table width="100%" class="formTable" align="center" border="0" cellspacing="2" cellpadding="3">
                            <tr>
                                <td class="titleRight">Branch</td>
                                <td class="value">
                                    <xsl:value-of select="$RegisteredClass/Branch/@BranchCode" />
                                    -
                                    <xsl:value-of select="$RegisteredClass/Branch/@Description" />
                                </td>
                                <td class="titleRight">Division</td>
                                <td class="value">
                                    <xsl:value-of select="$RegisteredClass/Division/@DivisionCode" />
                                    -
                                    <xsl:value-of select="$RegisteredClass/Division/@Description" />
                                </td>
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
                                <td class="titleRight">Section</td>
                                <td class="value">
                                    <xsl:value-of select="$RegisteredClass/Section/@SectionName" />
                                </td>
                                <td class="titleRight">Medium</td>
                                <td class="value">
                                    <xsl:value-of select="$RegisteredClass/Medium/@Description" />
                                </td>
                            </tr>
                            <tr>
                                <td class="titleRight">Academic Year</td>
                                <td class="value">
                                    <xsl:value-of select="OrganizationProfile/@CurrentAcademicYear" />
                                </td>
                                <td class="titleRight">Student Name</td>
                                <td class="value">
                                    <xsl:value-of select="$PersonalDetails/@FirstName" />
		                            <xsl:value-of select="$PersonalDetails/@LastName" />
                                </td>
                                <td class="titleRight">Admission Number</td>
                                <td class="value">
                                    <xsl:value-of select="$Student/@AdmissionNumber" />
                                </td>
                            </tr>
                        </table>
                        <p />

                        <table width="100%" class="formTable" align="left" border="1" cellspacing="10" cellpadding="5" frame="border">
							<caption class="dataTableCaption">Leave Details</caption>
                            <tr>
                                <td class="titleRight" style="border:0px solid black;">Leave Type</td>
                                <td class="value" style="border:0px solid black;"><xsl:value-of select="$Leave/@Type" /></td>
                            </tr>
                            <tr>
                                <td class="titleRight" style="border:0px solid black;">Start Date</td>
                                <td class="value" style="border:0px solid black;"><xsl:value-of select="$Leave/@StartDate" /></td>
                            </tr>
                            <tr>
                                <td class="titleRight" style="border:0px solid black;">End Date</td>
                                <td class="value" style="border:0px solid black;"><xsl:value-of select="$Leave/@EndDate" /></td>
                            </tr>
                            <tr>
                                <td class="titleRight" style="border:0px solid black;">Number Of Days</td>
                                <td class="value" style="border:0px solid black;"><xsl:value-of select="$Leave/@NumberOfDays" /></td>
                            </tr>
                            <tr>
                                <td class="titleRight" style="border:0px solid black;">Comments</td>
                                <td class="value" style="border:0px solid black;"><xsl:value-of select="$Leave/@Comments" /></td>
                            </tr>
                            <tr>
                                <td class="titleRight" style="border:0px solid black;">Leave Status</td>
                                <td class="value" style="border:0px solid black;"><xsl:value-of select="$Leave/@LeaveStatus" /></td>
                            </tr>
                        </table>
						<br />
						<p/>

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
</xsl:stylesheet>
