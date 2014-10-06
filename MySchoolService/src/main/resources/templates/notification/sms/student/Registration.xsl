<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="html" />
    <xsl:template match="/Message">
        <xsl:variable name="MySchool" select="MySchool" />
        <xsl:variable name="ToPerson" select="ToPerson" />
        <xsl:variable name="Student" select="Content[@class='Student']" />
        <xsl:variable name="PersonalDetails" select="$Student/PersonalDetails" />
        <xsl:variable name="RegisteredClass" select="$Student/RegisteredClass" />
        <pre>
Dear <xsl:value-of select="$ToPerson/@FirstName" /><xsl:text> </xsl:text><xsl:value-of select="$ToPerson/@LastName" />,

Your child (<xsl:value-of select="$PersonalDetails/@FirstName" /><xsl:text> </xsl:text><xsl:value-of select="$PersonalDetails/@LastName" />) enrollment with admission number (<xsl:value-of select="$Student/@AdmissionNumber" />) has been completed successfully at <xsl:value-of select="OrganizationProfile/@OrganizationName" />.
Visit our website <xsl:attribute name="href"><xsl:value-of select="$MySchool/@WebUrl" /></xsl:attribute>, write to <xsl:attribute name="href"><xsl:value-of select="$RegisteredClass/Branch/@EmailId" /></xsl:attribute> us or call us on <xsl:value-of select="$RegisteredClass/Branch/@PhoneNumber" /> for more information.

Your login credentials as follows.
User Name: <xsl:value-of select="$Student/@AdmissionNumber" />
Password: Your child's DOB (DD/MM/YYYY). Ignore Zeros in date and month while entering.

Thank you,
<xsl:value-of select="OrganizationProfile/@OrganizationName" />
        </pre>
    </xsl:template>
</xsl:stylesheet>