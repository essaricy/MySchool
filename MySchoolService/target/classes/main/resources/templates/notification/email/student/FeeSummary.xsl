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
                <xsl:variable name="Student" select="Content/Student" />
                <xsl:variable name="PersonalDetails" select="$Student/PersonalDetails" />
                <xsl:variable name="RegisteredClass" select="$Student/RegisteredClass" />
                <xsl:variable name="FeeSummary" select="Content/FeeSummary" />

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
                            We are pleased to present you the fee particulars and transactions for the academic year <xsl:value-of select="OrganizationProfile/@CurrentAcademicYear" /> for your child (
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
                        </table>
                        <p />

                        <!-- Month Attendance Details -->
                        <table width="90%" class="formTable" align="center" border="1" cellspacing="2" cellpadding="3">
                            <tr>
                                <td class="titleLeft">Particulars</td>
                                <td class="titleLeft">Due Date</td>
                                <td class="titleLeft">Transaction Date</td>
                                <td class="titleLeft">Amount</td>
                                <td class="titleLeft">Cumulative</td>
                            </tr>
                            <xsl:variable name="cumulative" select="0" />
                            <xsl:for-each select="$FeeSummary/FeeTransactions/FeeTransaction">
                                <tr>
                                    <td class="value">
                                        <xsl:if test="@Ledger='CR'">
                                            Fee To Pay - <xsl:value-of select="@Particulars" />
                                        </xsl:if>
                                        <xsl:if test="@Ledger='DR'">
                                            Payment - <xsl:value-of select="@Particulars" /><br/>
                                            <xsl:if test="Payment/@class='CashPayment'">
                                                <br/>
                                                Paid By# Cash
                                            </xsl:if>
                                            <xsl:if test="Payment/@class='ChequePayment'">
                                                <br/>
                                                Paid By# Cheque
                                                <br/>
                                                Cheque# <xsl:value-of select="Payment/@ChequeNumber" />
                                                <br/>
                                                Dated: <xsl:value-of select="Payment/@CheckRealizationDate" />
                                                <br/>
                                                Bank: <xsl:value-of select="Payment/@IssuingBanker" />
                                            </xsl:if>
                                            <xsl:if test="Payment/@class='CreditCardPayment'">
                                                <br/>
                                                Paid By# Credit Card
                                                <br/>
                                                Cheque# <xsl:value-of select="Payment/@CardNumber" />
                                            </xsl:if>
                                        </xsl:if>
                                        
                                    </td>
                                    <td class="value">
                                        <xsl:value-of select="@DueDate" />
                                    </td>
                                    <td class="value">
                                        <xsl:value-of select="@TransactionDate" />
                                    </td>
                                    <td class="value" style="text-align:right;">
                                        <xsl:if test="@Ledger='CR'">
                                            <xsl:value-of select="@Amount" />
                                        </xsl:if>
                                        <xsl:if test="@Ledger='DR'">
                                            - <xsl:value-of select="@Amount" />
                                        </xsl:if>
                                    </td>
                                    <td class="value">
                                        <xsl:value-of select="$cumulative" />
                                    </td>
                                </tr>
                            </xsl:for-each>
                            <tr>
                                <td colspan="3" style="font-size: 11px; font-family: arial; font-weight:bold; background-color: #D0D0D0; text-align:right;">Total Fee to Pay</td>
                                <td style="font-size: 11px; font-family: arial; font-weight:bold; background-color: #D0D0D0; text-align:right;">
                                    <xsl:value-of select="$FeeSummary/@TotalFeeToPay" />
                                </td>
                                <td style="background-color: #D0D0D0;">
                                    <xsl:text> </xsl:text>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="3" style="font-size: 11px; font-family: arial; font-weight:bold; background-color: #D0D0D0; text-align:right;">Total Fee Paid</td>
                                <td style="font-size: 11px; font-family: arial; font-weight:bold; background-color: #D0D0D0; text-align:right;">
                                    <xsl:value-of select="$FeeSummary/@TotalFeePaid" />
                                </td>
                                <td style="background-color: #D0D0D0;">
                                    <xsl:text> </xsl:text>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="3" style="font-size: 11px; font-family: arial; font-weight:bold; background-color: #D0D0D0; text-align:right;">Remaining Fee</td>
                                <td style="font-size: 11px; font-family: arial; font-weight:bold; background-color: #D0D0D0; text-align:right;">
                                    <xsl:value-of select="$FeeSummary/@TotalRemainingFeeToPay" />
                                </td>
                                <td style="background-color: #D0D0D0;">
                                    <xsl:text> </xsl:text>
                                </td>
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
