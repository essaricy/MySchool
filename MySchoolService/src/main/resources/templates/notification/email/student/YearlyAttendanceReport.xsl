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

                    .GeneralHoliday {
                        background-color: #347C17;
                        color: white;
                        font-weight: bold;
                        font-size: 10px;
                    }
                    .DeclaredHoliday {
                        background-color: #728C17;
                        color: white;
                        font-weight: bold;
                        font-size: 10px;
                    }
                    .OtherHoliday {
                        background-color: #4CC417;
                        color: white;
                        font-weight: bold;
                        font-size: 10px;
                    }
                    .Present {
                        background-color: #357EC7;
                        color: white;
                        font-weight: bold;
                        font-size: 10px;
                    }
                    .OnLeave {
                        background-color: #82CAFA;
                        color: white;
                        font-weight: bold;
                        font-size: 10px;
                    }
                    .Absent {
                        background-color: #FF2400;
                        color: white;
                        font-weight: bold;
                        font-size: 10px;
                    }
                </style>
            </head>
            <body>
                <xsl:variable name="MySchool" select="MySchool" />
                <xsl:variable name="ToPerson" select="ToPerson" />
                <xsl:variable name="Student" select="Content[@class='StudentAttendance']/Student" />
                <xsl:variable name="PersonalDetails" select="$Student/PersonalDetails" />
                <xsl:variable name="RegisteredClass" select="$Student/RegisteredClass" />
                <xsl:variable name="Attendance" select="Content[@class='StudentAttendance']/Attendance" />
                <xsl:variable name="MonthAttendances" select="$Attendance/MonthAttendances" />

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
                                        <xsl:attribute name="alt">
                                            <xsl:value-of select="$MySchool/@WebUrl" />
                                        </xsl:attribute>
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
                            We are pleased to present you the monthly attendance report of
                            your child (
                            <strong>
                                <xsl:value-of select="$PersonalDetails/@FirstName" />
                                <xsl:text> </xsl:text>
                                <xsl:value-of select="$PersonalDetails/@LastName" />
                            </strong>
                            enrollment with admission number
                            <strong>
                                <xsl:value-of select="$Student/@AdmissionNumber" />
                            </strong>
                            ).
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
                                <td class="titleRight">Attendance Year</td>
                                <td class="value">
                                    <xsl:value-of select="$Attendance/@AttendanceYear" />
                                </td>
                                <td class="titleRight">Attendance Month</td>
                                <td class="value">
                                    <xsl:value-of select="$Attendance/Month/@FullName" />
                                </td>
                            </tr>
                            <tr>
                                <td class="titleRight">Days in Month</td>
                                <td class="value">
                                    <xsl:value-of select="count($Attendance/Month/Days/Day)" />
                                </td>
                                <td class="titleRight">General Holidays</td>
                                <td class="value">
                                    <xsl:value-of select="$Attendance/@NumberOfGeneralHolidays" />
                                </td>
                                <td class="titleRight">Declared Holidays</td>
                                <td class="value">
                                    <xsl:value-of select="$Attendance/@NumberOfDeclaredHolidays" />
                                </td>
                            </tr>
                            <tr>
                                <td class="titleRight">Presents</td>
                                <td class="value">
                                    <xsl:value-of select="$Attendance/@NumberOfPresents" />
                                </td>
                                <td class="titleRight">Leaves</td>
                                <td class="value">
                                    <xsl:value-of select="$Attendance/@NumberOfLeaves" />
                                </td>
                                <td class="titleRight">Absents</td>
                                <td class="value">
                                    <xsl:value-of select="$Attendance/@NumberOfAbsents" />
                                </td>
                            </tr>
                        </table>
                        <p />

                        <!-- Month Attendance Details -->
                        <table width="100%" class="formTable" align="center" border="1" cellspacing="2" cellpadding="3">
                            <xsl:if test="$MonthAttendances">
                                <tr>
                                    <td class="value">
                                        <xsl:text> </xsl:text>
                                    </td>
                                    <xsl:for-each select="$MonthAttendances/MonthAttendance/Month[@Number=1]/Days/Day">
                                        <td class="value">
                                            <xsl:value-of select="@Date" />
                                        </td>
                                    </xsl:for-each>
                                </tr>
                                <xsl:for-each select="$MonthAttendances/MonthAttendance">
                                    <tr>
                                        <td class="value">
                                            <xsl:value-of select="Month/@ShortName" />
                                        </td>
                                        <xsl:variable name="DayAttendances" select="DayAttendances" />
                                        <xsl:for-each select="$DayAttendances/DayAttendance">
                                            <xsl:if test='@GeneralHoliday="true"'>
                                                <td class="GeneralHoliday"><xsl:text> </xsl:text></td>
                                            </xsl:if>
                                            <xsl:if test='@GeneralHoliday="false"'>
                                                <xsl:if test='@DeclaredHoliday="true"'>
                                                    <td class="DeclaredHoliday"><xsl:text> </xsl:text></td>
                                                </xsl:if>
                                                <xsl:if test='@DeclaredHoliday="false"'>
                                                    <xsl:if test='@Holiday="true"'>
                                                        <td class="OtherHoliday"><xsl:text> </xsl:text></td>
                                                    </xsl:if>
                                                    <xsl:if test='@Holiday="false"'>
                                                        <xsl:if test='@Present="true"'>
                                                            <td class="Present"><xsl:text> </xsl:text></td>
                                                        </xsl:if>
                                                        <xsl:if test='@Present="false"'>
                                                            <xsl:if test='@OnLeave="true"'>
                                                                <td class="OnLeave"><xsl:text> </xsl:text></td>
                                                            </xsl:if>
                                                            <xsl:if test='@OnLeave="false"'>
                                                                <td class="Absent"><xsl:text> </xsl:text></td>
                                                            </xsl:if>
                                                        </xsl:if>
                                                    </xsl:if>
                                                </xsl:if>
                                            </xsl:if>
                                        </xsl:for-each>
                                    </tr>
                                </xsl:for-each>
                            </xsl:if>
                        </table>
                        <p />

                        <table cellpadding="2" class="formTable" cellspacing="2" border="0" width="100%">
                            <tr>
                                <td class="GeneralHoliday" width="20px"><xsl:text> </xsl:text></td>
                                <td class="value">General Holiday</td>
                                <td class="DeclaredHoliday" width="20px"><xsl:text> </xsl:text></td>
                                <td class="value">Declared Holiday</td>
                                <td class="OtherHoliday" width="20px"><xsl:text> </xsl:text></td>
                                <td class="value">Other Holiday</td>
                                <td class="Present" width="20px"><xsl:text> </xsl:text></td>
                                <td class="value">Present</td>
                                <td class="OnLeave" width="20px"><xsl:text> </xsl:text></td>
                                <td class="value">On Leave</td>
                                <td class="Absent" width="20px"><xsl:text> </xsl:text></td>
                                <td class="value">Absent</td>
                            </tr>
                        </table>

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
                                <xsl:attribute name="href">mailto:<xsl:value-of
                                    select="$RegisteredClass/Branch/@EmailId" /></xsl:attribute>
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
                                <xsl:attribute name="href"><xsl:value-of
                                    select="$MySchool/@WebUrl" /></xsl:attribute>
                                <xsl:value-of select="OrganizationProfile/@OrganizationName" />
                            </a>
                        </p>

                        <p class="formLabel">This is a system generated e-mail. Hence, please do not
                            reply to this e-mail.
                        </p>
                        <!-- Body end -->
                    </div>
                </div>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>