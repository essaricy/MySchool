<?xml version="1.0"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:decimal-format name="amount" decimal-separator="." grouping-separator=","/>
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
                <xsl:variable name="EmployeePay" select="Content/EmployeePay" />
                <xsl:variable name="Employee" select="$EmployeePay/Employee" />
                <xsl:variable name="PersonalDetails" select="$Employee" />
                <xsl:variable name="RegisteredClass" select="$Employee/RegisteredClass" />
                <xsl:variable name="Payment" select="$EmployeePay/Payment" />
                <xsl:variable name="MonthAttendance" select="$EmployeePay/attendance" />

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


                        <xsl:variable name="PayYear" select="substring($Payment/@PaymentDate, 0, 5)" />
                        <xsl:variable name="PayMonth">
                            <xsl:call-template name="getMonth">
                                <xsl:with-param name="monthNbr" select="substring($Payment/@PaymentDate, 6, 2)" />
                            </xsl:call-template>
                        </xsl:variable>

                        <p class="formLabel">
                            We are pleased to present you the pay slip for the month of <strong><xsl:value-of select="$PayMonth" /></strong>
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
                                <td class="titleRight">Payment Year</td>
                                <td class="value">
                                    <xsl:value-of select="$PayYear" />
                                </td>
                                <td class="titleRight">Payment Month</td>
                                <td class="value">
                                    <xsl:value-of select="$PayMonth" />
                                </td>
                            </tr>
                            <tr>
                                <td class="titleRight">Days in Month</td>
                                <td class="value">
                                    <xsl:value-of select="count($MonthAttendance/Month/Days/Day)" />
                                </td>
                                <td class="titleRight">General Holidays</td>
                                <td class="value">
                                    <xsl:value-of select="$MonthAttendance/@NumberOfGeneralHolidays" />
                                </td>
                                <td class="titleRight">Declared Holidays</td>
                                <td class="value">
                                    <xsl:value-of select="$MonthAttendance/@NumberOfDeclaredHolidays" />
                                </td>
                            </tr>
                            <tr>
                                <td class="titleRight">Presents</td>
                                <td class="value">
                                    <xsl:value-of select="$MonthAttendance/@NumberOfPresents" />
                                </td>
                                <td class="titleRight">Leaves</td>
                                <td class="value">
                                    <xsl:value-of select="$MonthAttendance/@NumberOfLeaves" />
                                </td>
                                <td class="titleRight">Absents</td>
                                <td class="value">
                                    <xsl:value-of select="$MonthAttendance/@NumberOfAbsents" />
                                </td>
                            </tr>
                        </table>
                        <p />

                        <xsl:variable name="TotalEarnings" select="sum($EmployeePay/PayComponents/PayComponent[@Ledger='CR']/@Amount)" />
                        <xsl:variable name="TotalDeductions" select="sum($EmployeePay/PayComponents/PayComponent[@Ledger='DR']/@Amount)" />
                        <xsl:variable name="NetPay" select="$TotalEarnings - $TotalDeductions" />
                        <!-- Payment Details -->
                        <table width="100%" class="formTable" align="center" border="1" cellspacing="2" cellpadding="3">
                            <xsl:if test="$EmployeePay/PayComponents">
                                <tr>
                                    <td width="50%">
                                        <table width="100%" class="formTable" align="center" border="0" cellspacing="2" cellpadding="3">
                                            <xsl:for-each select="$EmployeePay/PayComponents/PayComponent">
                                                <xsl:if test="@Ledger='CR'">
                                                <tr>
                                                    <td class="titleLeft">
                                                        <xsl:value-of select="@ComponentName" />
                                                    </td>
                                                    <td class="value" style="text-align: right;">
                                                        <xsl:value-of select="format-number(@Amount, '##,###.00', 'amount')"/>
                                                    </td>
                                                    <td>
                                                        <xsl:text> </xsl:text>
                                                    </td>
                                                </tr>
                                                </xsl:if>
                                            </xsl:for-each>
                                            <tr>
                                                <td class="titleLeft">
                                                    Total Earnings
                                                </td>
                                                <td class="value" style="text-align: right; color: green;">
                                                    <strong>
                                                        <xsl:value-of select="format-number($TotalEarnings, '##,###.00', 'amount')"/>
                                                    </strong>
                                                </td>
                                                <td>
                                                    <xsl:text> </xsl:text>
                                                </td>
                                            </tr>
                                        </table>
                                    </td>
                                    <td valign="top">
                                        <table width="100%" class="formTable" align="center" border="0" cellspacing="2" cellpadding="3">
                                            <xsl:for-each select="$EmployeePay/PayComponents/PayComponent">
                                                <xsl:if test="@Ledger='DR'">
                                                <tr>
                                                    <td class="titleLeft">
                                                        <xsl:value-of select="@ComponentName" />
                                                    </td>
                                                    <td class="value" style="text-align: right;">
                                                        <xsl:value-of select="format-number(@Amount, '##,###.00', 'amount')"/>
                                                    </td>
                                                    <td>
                                                        <xsl:text> </xsl:text>
                                                    </td>
                                                </tr>
                                                </xsl:if>
                                            </xsl:for-each>
                                            <tr>
                                                <td class="titleLeft">
                                                    Total Deductions
                                                </td>
                                                <td class="value" style="text-align: right; color: red;">
                                                    <strong>
                                                        <xsl:value-of select="format-number($TotalDeductions, '##,###.00', 'amount')"/>
                                                    </strong>
                                                </td>
                                                <td>
                                                    <xsl:text> </xsl:text>
                                                </td>
                                            </tr>
                                        </table>
                                    </td>
                                </tr>
                            </xsl:if>
                        </table>
                        <table width="100%" class="formTable" align="center" border="1" cellspacing="2" cellpadding="3">
                            <tr>
                                <td class="titleLeft" width="50%">
                                    Net Pay
                                </td>
                                <xsl:if test="$NetPay > 0">
                                    <td class="value" style="text-align: right;">
                                        <strong style="color: green;"><xsl:value-of select="format-number($NetPay, '##,###.00', 'amount')"/></strong>
                                    </td>
                                </xsl:if>
                                <xsl:if test="$NetPay &lt; 0">
                                    <td class="value" style="text-align: right;">
                                        <strong style="color: red;"><xsl:value-of select="format-number($NetPay, '##,###.00', 'amount')"/></strong>
                                    </td>
                                </xsl:if>
                            </tr>
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
    <xsl:template name="getMonth">
        <xsl:param name="monthNbr" />
        <xsl:choose>
            <xsl:when test="$monthNbr=1">January</xsl:when>
            <xsl:when test="$monthNbr=2">February</xsl:when>
            <xsl:when test="$monthNbr=3">March</xsl:when>
            <xsl:when test="$monthNbr=4">April</xsl:when>
            <xsl:when test="$monthNbr=5">May</xsl:when>
            <xsl:when test="$monthNbr=6">June</xsl:when>
            <xsl:when test="$monthNbr=7">July</xsl:when>
            <xsl:when test="$monthNbr=8">August</xsl:when>
            <xsl:when test="$monthNbr=9">September</xsl:when>
            <xsl:when test="$monthNbr=10">October</xsl:when>
            <xsl:when test="$monthNbr=11">November</xsl:when>
            <xsl:when test="$monthNbr=12">December</xsl:when>
        </xsl:choose>
    </xsl:template>

</xsl:stylesheet>