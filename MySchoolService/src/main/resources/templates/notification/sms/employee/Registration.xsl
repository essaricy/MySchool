<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="html" />
    <xsl:template match="/Message">
        <xsl:variable name="MySchool" select="MySchool" />
        <xsl:variable name="ToPerson" select="ToPerson" />
        <xsl:variable name="Employee" select="Content[@class='Employee']" />
        <xsl:variable name="PersonalDetails" select="$Employee" />
        <xsl:variable name="RegisteredClass" select="$Employee/RegisteredClass" />
        <pre>
Dear <xsl:value-of select="$ToPerson/@FirstName" /><xsl:text> </xsl:text><xsl:value-of select="$ToPerson/@LastName" />,

Your employment with employee number (<xsl:value-of select="$Employee/@EmployeeNumber" />) has been completed successfully at <xsl:value-of select="OrganizationProfile/@OrganizationName" />.
Visit our website <xsl:attribute name="href"><xsl:value-of select="$MySchool/@WebUrl" /></xsl:attribute>, write to <xsl:attribute name="href"><xsl:value-of select="$RegisteredClass/Branch/@EmailId" /></xsl:attribute> us or call us on <xsl:value-of select="$RegisteredClass/Branch/@PhoneNumber" /> for more information.

Your login credentials as follows.
User Name: <xsl:value-of select="$Employee/@EmployeeNumber" />
Password: Your child's DOB (DD/MM/YYYY). Ignore Zeros in date and month while entering.

Thank you,
<xsl:value-of select="OrganizationProfile/@OrganizationName" />
        </pre>
    </xsl:template>
</xsl:stylesheet>