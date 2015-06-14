<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="html" />
    <xsl:decimal-format name="amount" decimal-separator="." grouping-separator=","/>
    <xsl:template match="/Message">
        <xsl:variable name="MySchool" select="MySchool" />
        <xsl:variable name="ToPerson" select="ToPerson" />
        <xsl:variable name="EmployeePay" select="Content/EmployeePay" />
        <xsl:variable name="Employee" select="$EmployeePay/Employee" />
        <xsl:variable name="PersonalDetails" select="$Employee" />
        <xsl:variable name="RegisteredClass" select="$Employee/RegisteredClass" />
        <xsl:variable name="Payment" select="$EmployeePay/Payment" />
        <xsl:variable name="MonthAttendance" select="$EmployeePay/attendance" />
        <xsl:variable name="PayYear" select="substring($Payment/@PaymentDate, 0, 5)" />
        <xsl:variable name="PayMonth">
            <xsl:call-template name="getMonth">
                <xsl:with-param name="monthNbr" select="substring($Payment/@PaymentDate, 6, 2)" />
            </xsl:call-template>
        </xsl:variable>
        <xsl:variable name="TotalEarnings" select="sum($EmployeePay/PayComponents/PayComponent[@Ledger='CR']/@Amount)" />
        <xsl:variable name="TotalDeductions" select="sum($EmployeePay/PayComponents/PayComponent[@Ledger='DR']/@Amount)" />
        <xsl:variable name="NetPay" select="$TotalEarnings - $TotalDeductions" />
        <pre>
Dear <xsl:value-of select="$ToPerson/@FirstName" /> <xsl:text> </xsl:text> <xsl:value-of select="$ToPerson/@LastName" />,
Your (Employee #<xsl:value-of select="$Employee/@EmployeeNumber" />) payslip has been generated and processed.

Academic Year: <xsl:value-of select="OrganizationProfile/@CurrentAcademicYear" />,
Payment Year: <xsl:value-of select="$PayYear" />,
Payment Month: <xsl:value-of select="$PayMonth" />,
Total Earnings: <xsl:value-of select="format-number($TotalEarnings, '##,###.00', 'amount')"/>,
Total Deductions: <xsl:value-of select="format-number($TotalDeductions, '##,###.00', 'amount')"/>,
Net Pay: <xsl:value-of select="format-number($NetPay, '##,###.00', 'amount')"/>

Visit our website<xsl:attribute name="href"><xsl:value-of select="$MySchool/@WebUrl" /></xsl:attribute>, write to <xsl:attribute name="href"><xsl:value-of select="$RegisteredClass/Branch/@EmailId" /></xsl:attribute> us or call us on <xsl:value-of select="$RegisteredClass/Branch/@PhoneNumber" /> for more information.

Thank you,
<xsl:value-of select="OrganizationProfile/@OrganizationName" />
        </pre>
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