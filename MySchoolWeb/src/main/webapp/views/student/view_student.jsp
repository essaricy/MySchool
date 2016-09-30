<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<style>
#StudentRegistrationTabs {
    font-size: 1.1em;
}
</style>
<script type="text/javascript" charset="utf-8">
jQuery(document).ready(function() {
  $('#StudentRegistrationTabs').tabs({id: 'StudentRegistrationTabs'});
  $("#StudentRegistrationTabs").tabs("option", "active", 0);
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
      <table class="formTable_Data">
        <tr>
          <td align="center">
            <c:if test="${Student.imageAccess == null || Student.imageAccess.passportLink == null}">
              <img id="studentImage" name="studentImage" src="<%=request.getContextPath()%>/images/icons/no-image-yet.png" width="150px" height="180px" class="no-image" />
            </c:if>
            <c:if test="${Student.imageAccess != null && Student.imageAccess.passportLink != null}">
              <img id="studentImage" name="studentImage" src="${Student.imageAccess.passportLink}" border="1" width="150px" height="180px"/>
            </c:if>
          </td>
        </tr>
      </table>
    </td>
    <td width="85%" valign="top">
      <div id="StudentRegistrationTabs">
        <ul>
          <li><a href="#StudentAdmissionDetailsTab">Admission</a></li>
          <li><a href="#StudentPersonalDetailsTab">Personal</a></li>
          <li><a href="#StudentFamilyDetailsTab">Family</a></li>
          <li><a href="#StudentDocumentDetailsTab">Documents</a></li>
        </ul>

        <div id="StudentAdmissionDetailsTab"><%@ include file="/views/student/view_student_admission_details.jsp" %></div>
        <div id="StudentPersonalDetailsTab"><%@ include file="/views/student/view_student_personal_details.jsp" %></div>
        <div id="StudentFamilyDetailsTab"><%@ include file="/views/student/view_student_family_details.jsp" %></div>
        <div id="StudentDocumentDetailsTab"><%@ include file="/views/student/view_student_document_details.jsp" %></div>
      </div>
    </td>
  </tr>
</table>
