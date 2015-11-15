<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<style>
#StudentAccordion p {
  font-size: 0.7em;
  font-weight: bold;
  text-align: left;
}
</style>
<script type="text/javascript" charset="utf-8">
jQuery(document).ready(function() {
  $(this).myAccordion({id: 'StudentAccordion'});
  $("#StudentAccordion").accordion( "option", "active", 0);
});
</script>
<c:if test="${Student != null}">
  <input type="hidden" id="AdmissionNumber" value="${Student.admissionNumber}" />
  <c:set var="StudentFamilyMembers" value="${Student.familyMembers}" />
  <c:set var="StudentPersonalDetails" value="${Student.personalDetails}" />
  <c:set var="StudentDocuments" value="${Student.documentsSubmitted}" />
</c:if>

<table cellpadding="2" width="90%" align="center" cellspacing="0" border="0">
  <tr>
    <td width="15%" valign="top">
      <!-- Student Photo -->
      <table cellpadding="5" cellspacing="0" border="0" width="100%" height="100%" class="formTable">
        <tr>
          <td align="center">
            <c:if test="${Student.verified}">
              <img id="studentImage" name="studentImage" src="${RESOURCE_PROFILE.studentRegistered.resourceUrl}/${Student.admissionNumber}" border="1" width="150px" height="180px"/>
            </c:if>
            <c:if test="${! Student.verified}">
              <img id="studentImage" name="studentImage" src="${RESOURCE_PROFILE.studentPortal.resourceUrl}/${Student.admissionNumber}" border="1" width="150px" height="180px"/>
            </c:if>
          </td>
        </tr>
      </table>
    </td>
    <td width="85%" valign="top">
      <div id="StudentAccordion">
        <p class="title"><spring:message code="student.admission.details"/></p>
        <div><%@ include file="/views/student/view_student_admission_details.jsp" %></div>
        <p class="title"><spring:message code="student.personal.details"/></p>
        <div><%@ include file="/views/student/view_student_personal_details.jsp" %></div>
        <p class="title"><spring:message code="student.family.details"/></p>
        <div><%@ include file="/views/student/view_student_family_details.jsp" %></div>
        <p class="title">Documents</p>
        <div><%@ include file="/views/student/view_student_document_details.jsp" %></div>
      </div>
    </td>
  </tr>
</table>
