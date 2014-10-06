<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="html" />
    <xsl:template match="/Message">
    <xsl:variable name="MySchool" select="MySchool" />
    <xsl:variable name="ToPerson" select="ToPerson" />
    <xsl:variable name="Student" select="Content/Student" />
    <xsl:variable name="PersonalDetails" select="$Student/PersonalDetails" />
    <xsl:variable name="RegisteredClass" select="$Student/RegisteredClass" />
    <xsl:variable name="FeeSummary" select="Content/FeeSummary" />
        <pre>
Dear <xsl:value-of select="$ToPerson/@FirstName" /> <xsl:text> </xsl:text> <xsl:value-of select="$ToPerson/@LastName" />,
We are pleased to present you the fee particulars and transactions for the academic year <xsl:value-of select="OrganizationProfile/@CurrentAcademicYear" /> of your child (<xsl:value-of select="$PersonalDetails/@FirstName" /><xsl:text> </xsl:text><xsl:value-of select="$PersonalDetails/@LastName" />) enrollment with admission #<xsl:value-of select="$Student/@AdmissionNumber" />).

Total Fee to Pay: <xsl:value-of select="$FeeSummary/@TotalFeeToPay" />,
Total Fee Paid: <xsl:value-of select="$FeeSummary/@TotalFeePaid" />,
Remaining Fee: <xsl:value-of select="$FeeSummary/@TotalRemainingFeeToPay" />

Visit our website<xsl:attribute name="href"><xsl:value-of select="$MySchool/@WebUrl" /></xsl:attribute>, write to <xsl:attribute name="href"><xsl:value-of select="$RegisteredClass/Branch/@EmailId" /></xsl:attribute> us or call us on <xsl:value-of select="$RegisteredClass/Branch/@PhoneNumber" /> for more information.

Thank you,
<xsl:value-of select="OrganizationProfile/@OrganizationName" />
        </pre>
    </xsl:template>
</xsl:stylesheet>