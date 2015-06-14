<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="html" />
    <xsl:template match="/Message">
        <xsl:variable name="MySchool" select="MySchool" />
        <xsl:variable name="ToPerson" select="ToPerson" />
        <xsl:variable name="Employee" select="Content/Employee" />
        <xsl:variable name="PersonalDetails" select="$Employee" />
        <xsl:variable name="RegisteredClass" select="$Employee/RegisteredClass" />
        <xsl:variable name="Attendance" select="Content/YearAttendance" />
        <xsl:variable name="MonthAttendances" select="$Attendance/MonthAttendances" />
        <pre>
Dear <xsl:value-of select="$ToPerson/@FirstName" /> <xsl:text> </xsl:text> <xsl:value-of select="$ToPerson/@LastName" />,
Your (Employee #<xsl:value-of select="$Employee/@EmployeeNumber" />), attendance details for the month of <xsl:value-of select="$Attendance/Month/@FullName" />,<xsl:value-of select="$Attendance/@AttendanceYear" />.
Days in Month: <xsl:value-of select="count($Attendance/Month/Days/Day)" />,
General Holidays: <xsl:value-of select="$Attendance/@NumberOfGeneralHolidays" />,
Declared Holidays: <xsl:value-of select="$Attendance/@NumberOfDeclaredHolidays" />,
Presents: <xsl:value-of select="$Attendance/@NumberOfPresents" />,
Leaves: <xsl:value-of select="$Attendance/@NumberOfLeaves" />,
Absents: <xsl:value-of select="$Attendance/@NumberOfAbsents" />

Visit our website<xsl:attribute name="href"><xsl:value-of select="$MySchool/@WebUrl" /></xsl:attribute>, write to <xsl:attribute name="href"><xsl:value-of select="$RegisteredClass/Branch/@EmailId" /></xsl:attribute> us or call us on <xsl:value-of select="$RegisteredClass/Branch/@PhoneNumber" /> for more information.

Thank you,
<xsl:value-of select="OrganizationProfile/@OrganizationName" />
        </pre>
    </xsl:template>
</xsl:stylesheet>