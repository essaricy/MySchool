<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix='cewolf' uri='/WEB-INF/tld/cewolf.tld' %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

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
  $('#studentImage').click(showImage);
});
function showImage() {
  $.magnificPopup.open({
    items: {
      src: '<%=request.getContextPath()%>/image/getImage.htm?type=student&imageSize=ORIGINAL&contentId=' + $('#AdmissionNumber').val()
    },
    type: 'image' // this is default type
  });
}
</script>
<c:if test="${Student != null}">
  <input type="hidden" id="AdmissionNumber" value="${Student.admissionNumber}" />
  <c:set var="StudentFamilyMembers" value="${Student.familyMembers}" />
  <c:set var="StudentPersonalDetails" value="${Student.personalDetails}" />
  <c:set var="StudentDocuments" value="${Student.documentsSubmitted}" />
</c:if>

<table class="formTable_Container">
  <caption>
    ${Student.admissionNumber} - ${StudentPersonalDetails.firstName} ${StudentPersonalDetails.middleName} ${StudentPersonalDetails.lastName} 
  </caption>
  <tr>
    <td width="15%" valign="top">
      <!-- Student Photo -->
      <c:if test="${Student == null}">
      <table class="formTable_Data">
        <tr>
          <td align="center">
            <img id="studentImage" name="studentImage" src="<%=request.getContextPath()%>/image/getImage.htm?type=no-image" border="1" width="150px" height="180px"/>
          </td>
        </tr>
      </table>
      </c:if>
      <c:if test="${Student != null}">
      <table class="formTable_Data">
        <tr>
          <td align="center">
            <img id="studentImage" name="studentImage" src="<%=request.getContextPath()%>/image/getImage.htm?type=student&imageSize=ORIGINAL&contentId=${Student.admissionNumber}&sid=<%= new java.util.Date().getTime()%>" border="1" width="150px" height="180px"/>
          </td>
        </tr>
      </table>
      </c:if>
    </td>
    <td width="85%" valign="top">
      <div id="StudentAccordion">
        <p class="title"><spring:message code="student.admission.details"/></p>
        <div><%@ include file="/views/student/view_student_admission_details.jsp" %></div>
        <p class="title"><spring:message code="student.personal.details"/></p>
        <div><%@ include file="/views/student/view_student_personal_details.jsp" %></div>
        <p class="title"><spring:message code="student.family.details"/></p>
        <div><%@ include file="/views/student/view_student_family_details.jsp" %></div>
        <p class="title">Student Documents</p>
        <div><%@ include file="/views/student/view_student_document_details.jsp" %></div>
      </div>
    </td>
  </tr>
</table>

student_attribute_tile = ${student_attribute_tile}
<tiles:insertAttribute name="student_attribute_tile" ignore="true" />
