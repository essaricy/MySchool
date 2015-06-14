<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="html" />
    <xsl:template match="/Message">
        <xsl:variable name="MySchool" select="MySchool" />
        <xsl:variable name="ToPerson" select="ToPerson" />
        <xsl:variable name="StudentInExam" select="Content/StudentInExam" />
        <xsl:variable name="Student" select="Content/StudentInExam/Student" />
        <xsl:variable name="PersonalDetails" select="$Student/PersonalDetails" />
        <xsl:variable name="RegisteredClass" select="$Student/RegisteredClass" />
        <xsl:variable name="StudentExams" select="Content/StudentInExam/StudentExams" />
        <xsl:variable name="Exam" select="Content/Exam" />
        <pre>
Dear <xsl:value-of select="$ToPerson/@FirstName" /> <xsl:text> </xsl:text> <xsl:value-of select="$ToPerson/@LastName" />,
Please find your Child's (<xsl:value-of select="$PersonalDetails/@FirstName" /><xsl:text> </xsl:text><xsl:value-of select="$PersonalDetails/@LastName" />), admission #<xsl:value-of select="$Student/@AdmissionNumber" />, examination result.
Academic Year: <xsl:value-of select="OrganizationProfile/@CurrentAcademicYear" />,
Exam Date: <xsl:value-of select="$Exam/@ExamDate" />,
Exam Name: <xsl:value-of select="$Exam/@ExamName" />,

<xsl:for-each select="$Exam/SubjectExams/SubjectExam">
<xsl:value-of select="RegisteredSubject/Subject/@SubjectName" />: <xsl:value-of select="$StudentExams/StudentExam[@SubjectExamId=current()/@SubjectExamId]/@ObtainedMarks" />/<xsl:value-of select="@MaximumMarks" />, 
</xsl:for-each>

<xsl:variable name="Percentage" select="$StudentInExam/@Percentage" />
Total Marks Secured: <xsl:value-of select="sum($StudentExams/StudentExam/@ObtainedMarks)" />/<xsl:value-of select="sum($Exam/SubjectExams/SubjectExam/@MaximumMarks)" />,
Percentage: <xsl:value-of select="$Percentage" />%, 
Grade: <xsl:value-of select="$StudentInExam/@Grade" />

Visit our website<xsl:attribute name="href"><xsl:value-of select="$MySchool/@WebUrl" /></xsl:attribute>, write to <xsl:attribute name="href"><xsl:value-of select="$RegisteredClass/Branch/@EmailId" /></xsl:attribute> us or call us on <xsl:value-of select="$RegisteredClass/Branch/@PhoneNumber" /> for more information.

Thank you,
<xsl:value-of select="OrganizationProfile/@OrganizationName" />
        </pre>
    </xsl:template>
</xsl:stylesheet>