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
                <xsl:variable name="Employee" select="Content/Employee" />
                <xsl:variable name="PersonalDetails" select="$Employee" />
                <xsl:variable name="RegisteredClass" select="$Employee/RegisteredClass" />
                <xsl:variable name="Attendance" select="Content/MonthAttendance" />
                <xsl:variable name="DayAttendances" select="$Attendance/DayAttendances" />

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
                            We are pleased to present you the monthly attendance report.
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
                            <xsl:if test="$DayAttendances">
                                <tr>
                                    <xsl:for-each select="$DayAttendances/DayAttendance[position() &lt; 8]">
                                        <xsl:call-template name="display_attendance_week">
                                            <xsl:with-param name="attendanceNode" select="current()" />
                                        </xsl:call-template>
                                    </xsl:for-each>
                                </tr>
                                <tr>
                                    <xsl:for-each select="$DayAttendances/DayAttendance[position() &gt; 7 and position() &lt; 15]">
                                        <xsl:call-template name="display_attendance_week">
                                            <xsl:with-param name="attendanceNode" select="current()" />
                                        </xsl:call-template>
                                    </xsl:for-each>
                                </tr>
                                <tr>
                                    <xsl:for-each select="$DayAttendances/DayAttendance[position() &gt; 14 and position() &lt; 22]">
                                        <xsl:call-template name="display_attendance_week">
                                            <xsl:with-param name="attendanceNode" select="current()" />
                                        </xsl:call-template>
                                    </xsl:for-each>
                                </tr>
                                <tr>
                                    <xsl:for-each select="$DayAttendances/DayAttendance[position() &gt; 21 and position() &lt; 29]">
                                        <xsl:call-template name="display_attendance_week">
                                            <xsl:with-param name="attendanceNode" select="current()" />
                                        </xsl:call-template>
                                    </xsl:for-each>
                                </tr>
                                <tr>
                                    <xsl:for-each select="$DayAttendances/DayAttendance[position() &gt; 28 and position()&lt; 32]">
                                        <xsl:call-template name="display_attendance_week">
                                            <xsl:with-param name="attendanceNode" select="current()" />
                                        </xsl:call-template>
                                    </xsl:for-each>
                                </tr>
                            </xsl:if>
                        </table>

                        <p class="formLabel">
                            Now you can find out your attendance, leaves, payslips and many more
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
    <xsl:template name="display_attendance_week">
        <xsl:param name="attendanceNode" />
        <td class="value">
            <h2>
                <xsl:value-of select="Day/@Date" />
            </h2>
            <table cellpadding="2" cellspacing="2" width="100px">
                <tr>
                    <xsl:if test='@GeneralHoliday="true"'>
                        <td class="GeneralHoliday">General Holiday</td>
                    </xsl:if>
                    <xsl:if test='@GeneralHoliday="false"'>
                        <xsl:if test='@DeclaredHoliday="true"'>
                            <td class="DeclaredHoliday">Declared Holiday</td>
                        </xsl:if>
                        <xsl:if test='@DeclaredHoliday="false"'>
                            <xsl:if test='@Holiday="true"'>
                                <td class="OtherHoliday">Other Holiday</td>
                            </xsl:if>
                            <xsl:if test='@Holiday="false"'>
                                <xsl:if test='@Present="true"'>
                                    <td class="Present">Present</td>
                                </xsl:if>
                                <xsl:if test='@Present="false"'>
                                    <xsl:if test='@OnLeave="true"'>
                                        <td class="OnLeave">On Leave</td>
                                    </xsl:if>
                                    <xsl:if test='@OnLeave="false"'>
                                        <td class="Absent">Absent</td>
                                    </xsl:if>
                                </xsl:if>
                            </xsl:if>
                        </xsl:if>
                    </xsl:if>
                </tr>
            </table>
            <br />
            <strong>
                <xsl:value-of select="Day/@DayFullName" />
            </strong>
        </td>
    </xsl:template>
</xsl:stylesheet>