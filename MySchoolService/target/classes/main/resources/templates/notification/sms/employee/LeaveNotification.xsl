<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="html" />
    <xsl:template match="/Message">
    <xsl:variable name="MySchool" select="MySchool" />
    <xsl:variable name="ToPerson" select="ToPerson" />
    <xsl:variable name="Employee" select="Content/Employee" />
    <xsl:variable name="PersonalDetails" select="$Employee" />
    <xsl:variable name="RegisteredClass" select="$Employee/RegisteredClass" />
    <xsl:variable name="Leave" select="Content/Leave" />
        <pre>
Dear <xsl:value-of select="$ToPerson/@FirstName" /> <xsl:text> </xsl:text> <xsl:value-of select="$ToPerson/@LastName" />,
Your (Employee #<xsl:value-of select="$Employee/@EmployeeNumber" />), your leave request has been processed successfully.

Leave Type: <xsl:value-of select="$Leave/@Type" />,
Start Date: <xsl:value-of select="$Leave/@StartDate" />,
End Date: <xsl:value-of select="$Leave/@EndDate" />,
Number Of Days: <xsl:value-of select="$Leave/@NumberOfDays" />,
Comments: <xsl:value-of select="$Leave/@Comments" />,
Leave Status: <xsl:value-of select="$Leave/@LeaveStatus" />

Visit our website<xsl:attribute name="href"><xsl:value-of select="$MySchool/@WebUrl" /></xsl:attribute>, write to <xsl:attribute name="href"><xsl:value-of select="$RegisteredClass/Branch/@EmailId" /></xsl:attribute> us or call us on <xsl:value-of select="$RegisteredClass/Branch/@PhoneNumber" /> for more information.

Thank you,
<xsl:value-of select="OrganizationProfile/@OrganizationName" />
        </pre>
    </xsl:template>
</xsl:stylesheet>