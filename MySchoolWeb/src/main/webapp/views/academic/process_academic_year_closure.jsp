<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<script type="text/javascript" charset="utf-8">
$(document).ready(function () {
  $('.chosen-select').chosen({width: "40%"});
});
</script>

<c:if test="${AcademicYearClosure != null}">
<table width="70%" class="userFormTable" align="center" cellspacing="10" cellpadding="5">
  <caption class="dataTableCaption"><spring:message code="aye.closure" /></caption>
  <tr>
    <td width="100%" align="center">
      <c:set var="MySchoolProfile" value="${AcademicYearClosure.mySchoolProfile}" />
      <c:set var="CurrentAcademic" value="${AcademicYearClosure.currentAcademic}" />
      <c:set var="NextAcademic" value="${AcademicYearClosure.nextAcademic}" />
      <c:set var="ExamGrades" value="${AcademicYearClosure.examGrades}" />

      <c:if test="${MySchoolProfile.ayeInProgress}">
        <font class="error"><spring:message code="aye.in.progress" /></font>
      </c:if>
      <!-- MySchool Configuration -->
      <table class="userFormTable" align="center" border="0" cellspacing="10" cellpadding="5">
          <tr>
            <td colspan="2" align="left">
              <b><spring:message code="aye.start.note" /></b><br/>
              [<a href="<%=request.getContextPath()%>/profile/list.htm" class="formLink"><spring:message code="change.config" /></a>]
            </td>
          </tr>
          <!-- Email service configuration -->
          <tr>
            <td width="50%" class="label"><spring:message code="aye.use.emails" /></td>
            <td width="50%" class="value">
            <c:if test="${MySchoolProfile.emailActive}"><spring:message code="common.yes" /></c:if>
            <c:if test="${!MySchoolProfile.emailActive}"><spring:message code="common.no" /></c:if>
            </td>
          </tr>

          <c:if test="${MySchoolProfile.emailActive}">
          <tr>
            <td width="50%" class="label"><spring:message code="aye.use.emails.employee" /></td>
            <td width="50%" class="value">
            <c:if test="${MySchoolProfile.emailEmployees}"><spring:message code="common.yes" /></c:if>
            <c:if test="${!MySchoolProfile.emailEmployees}"><spring:message code="common.no" /></c:if>
            </td>
          </tr>
          </c:if>

          <c:if test="${MySchoolProfile.emailActive}">
          <tr>
            <td width="50%" class="label"><spring:message code="aye.use.emails.student" /></td>
            <td width="50%" class="value">
            <c:if test="${MySchoolProfile.emailStudents}"><spring:message code="common.yes" /></c:if>
            <c:if test="${!MySchoolProfile.emailStudents}"><spring:message code="common.no" /></c:if>
            </td>
          </tr>
          </c:if>

          <!-- SMS service configuration -->
          <tr>
            <td width="50%" class="label"><spring:message code="aye.use.sms" /></td>
            <td width="50%" class="value">
            <c:if test="${MySchoolProfile.smsActive}"><spring:message code="common.yes" /></c:if>
            <c:if test="${!MySchoolProfile.smsActive}"><spring:message code="common.no" /></c:if>
            </td>
          </tr>

          <c:if test="${MySchoolProfile.emailActive}">
          <tr>
            <td width="50%" class="label"><spring:message code="aye.use.sms.employee" /></td>
            <td width="50%" class="value">
            <c:if test="${MySchoolProfile.smsEmployees}"><spring:message code="common.yes" /></c:if>
            <c:if test="${!MySchoolProfile.smsEmployees}"><spring:message code="common.no" /></c:if>
            </td>
          </tr>
          </c:if>

          <c:if test="${MySchoolProfile.emailActive}">
          <tr>
            <td width="50%" class="label"><spring:message code="aye.use.sms.student" /></td>
            <td width="50%" class="value">
            <c:if test="${MySchoolProfile.smsStudents}"><spring:message code="common.yes" /></c:if>
            <c:if test="${!MySchoolProfile.smsStudents}"><spring:message code="common.no" /></c:if>
            </td>
          </tr>
          </c:if>
      </table>

      <!-- Academic Year information -->
      <table class="userFormTable" align="center" border="0" cellspacing="10" cellpadding="5" width="100%">
          <tr>
            <td colspan="2" align="left">
              <b><spring:message code="aye.review" /></b><br/>
              <c:if test="${CurrentAcademic == null or NextAcademic == null}">
                [<a href="<%=request.getContextPath()%>/academic/list.htm" class="formLink"><spring:message code="aye.setup" /></a>]
              </c:if>
            </td>
          </tr>
          <!-- Current Academic Year -->
          <c:if test="${CurrentAcademic == null}">
          <tr>
            <td colspan="2" align="left">
              <spring:message code="aye.no.current" /><br />
            </td>
          </tr>
          </c:if>

          <c:if test="${CurrentAcademic != null}">
          <tr>
            <td width="50%" class="label"><spring:message code="ay.current.name" /></td>
            <td width="50%" class="value">${CurrentAcademic.academicYearName}</td>
          </tr>
          <tr>
            <td width="50%" class="label"><spring:message code="ay.current.startDate" /></td>
            <td width="50%" class="value">${CurrentAcademic.academicYearStartDate}</td>
          </tr>
          <tr>
            <td width="50%" class="label"><spring:message code="ay.current.endDate" /></td>
            <td width="50%" class="value">${CurrentAcademic.academicYearEndDate}</td>
          </tr>
          </c:if>

          <!-- Next Academic Year -->
          <c:if test="${NextAcademic == null}">
          <tr>
            <td colspan="2" align="left">
              <spring:message code="aye.no.next" />
            </td>
          </tr>
          </c:if>

          <c:if test="${NextAcademic != null}">
          <tr>
            <td width="50%" class="label"><spring:message code="ay.next.name" /></td>
            <td width="50%" class="value">${NextAcademic.academicYearName}</td>
          </tr>
          <tr>
            <td width="50%" class="label"><spring:message code="ay.next.startDate" /></td>
            <td width="50%" class="value">${NextAcademic.academicYearStartDate}</td>
          </tr>
          <tr>
            <td width="50%" class="label"><spring:message code="ay.next.endDate" /></td>
            <td width="50%" class="value">${NextAcademic.academicYearEndDate}</td>
          </tr>
          </c:if>
      </table>

      <table class="userFormTable" align="center" border="0" cellspacing="10" cellpadding="5" width="100%">
          <tr>
            <td colspan="2" align="left">
              <b><spring:message code="aye.examGrade.note" /></b><br/>
              <c:if test="${ExamGrades == null}">
                [<a href="<%=request.getContextPath()%>/academic/list.htm" class="formLink"><spring:message code="aye.examGrade.setup" /></a>]
              </c:if>
            </td>
          </tr>
          <c:if test="${ExamGrades == null}">
          <tr>
            <td colspan="2" align="left">
              <spring:message code="aye.no.examGrade" />
            </td>
          </tr>
          </c:if>
          <c:if test="${ExamGrades != null}">
          <tr>
            <td width="50%" class="label"><spring:message code="examGrade" /></td>
            <td width="50%" class="value">
              <select id="examGrades" class="chosen-select">
                <c:forEach var="examGrade" items="${ExamGrades}">
                  <option value="${examGrade.examGradeId}">${examGrade.gradeName}</option>
                </c:forEach>
              </select>
            </td>
          </tr>
          </c:if>
      </table>

      <c:set var="canProcess" value="${PAGE_ACCESS != null && PAGE_ACCESS.update && MySchoolProfile.ayeInProgress != true and CurrentAcademic != null and NextAcademic != null}" />
      <table class="userFormTable" align="center" border="0" cellspacing="10" cellpadding="5" width="100%">
        <tr>
          <td align="center">
            <c:if test="${canProcess}">
              <input type="button" id="InitiateAYC" class="active" value='<spring:message code="aye.start" />' />
            </c:if>
            <c:if test="${not canProcess}">
              <input type="button" class="inactive" value='<spring:message code="aye.start" />' disabled />
            </c:if>
          </td>
        </tr>
      </table>

    </td>
  </tr>
</table>
</c:if>
