<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="myschool" tagdir="/WEB-INF/tags" %>

<style>
#EmployeeRegistrationTabs p {
  font-size: 0.7em;
  font-weight: bold;
  text-align: left;
}
</style>
<script type="text/javascript" language="javascript" src="<%=request.getContextPath()%>/scripts/myschool-employee-attributes.js"></script>
<script>

jQuery(document).ready(function() {
  $('#EmployeeRegistrationTabs').tabs({id: 'EmployeeRegistrationTabs'});
  $("#EmployeeRegistrationTabs").tabs("option", "active", 0);
});
</script>

<c:if test="${Employee != null}">
  <input type="hidden" id="EmployeeNumber" value="${Employee.employeeNumber}" />
  <c:set var="EmployeeContact" value="${Employee.employeeContact}" />
  <c:set var="EmployeeDocuments" value="${Employee.employeeDocuments}" />
  <c:set var="EmployeeEducations" value="${Employee.employeeDocuments}" />
  <c:set var="EmployeeExperiences" value="${Employee.employeeExperiences}" />
  <c:set var="EmployeePromotions" value="${Employee.employeePromotions}" />
  <c:set var="EmployeeTeachingSubjects" value="${Employee.employeeSubjects}" />
</c:if>

<table cellpadding="2" width="90%" align="center" cellspacing="0" border="0">
  <tr>
    <td width="15%" valign="top">
      <!-- Employee Photo -->
      <table class="formTable_Data">
        <tr>
          <td align="center">
            <c:if test="${Employee.imageAccess == null || Employee.imageAccess.passportLink == null}">
              <img id="employeeImage" name="employeeImage" src="<%=request.getContextPath()%>/images/icons/no-image-yet.png" border="1" width="150px" height="180px"/>
            </c:if>
            <c:if test="${Employee.imageAccess != null && Employee.imageAccess.passportLink != null}">
              <img id="employeeImage" name="employeeImage" src="${Employee.imageAccess.passportLink}" border="1" width="150px" height="180px"/>
            </c:if>
          </td>
        </tr>
      </table>
    </td>
    <td width="85%" valign="top">
      <div id="EmployeeRegistrationTabs">
        <ul>
          <li><a href="#EmploymentDetailsTab">Employment</a></li>
          <li><a href="#EmployeePersonalDetailsTab">Personal</a></li>
          <li><a href="#EmployeeFamilyDetailsTab">Contacts</a></li>
          <li><a href="#EmployeeDocumentDetailsTab">Documents</a></li>
          <li><a href="#EmployeeEducationDetailsTab">Education</a></li>
          <li><a href="#EmployeeExperienceDetailsTab">Experience</a></li>
          <li><a href="#EmployeePromotionDetailsTab">Promotions</a></li>
          <li><a href="#EmployeeTeachingSubjectsDetailsTab">Teaching Subjects</a></li>
        </ul>

        <div id="EmploymentDetailsTab"><%@ include file="/views/employee/view_employment_details.jsp" %></div>
        <div id="EmployeePersonalDetailsTab"><%@ include file="/views/employee/view_employee_personal_details.jsp" %></div>
        <div id="EmployeeFamilyDetailsTab"><%@ include file="/views/employee/view_employee_contact_details.jsp" %></div>
        <div id="EmployeeDocumentDetailsTab"><%@ include file="/views/employee/view_employee_document_details.jsp" %></div>
        <div id="EmployeeEducationDetailsTab"><%@ include file="/views/employee/view_employee_education_details.jsp" %></div>
        <div id="EmployeeExperienceDetailsTab"><%@ include file="/views/employee/view_employee_experience_details.jsp" %></div>
        <div id="EmployeePromotionDetailsTab"><%@ include file="/views/employee/view_employee_promotion_details.jsp" %></div>
        <div id="EmployeeTeachingSubjectsDetailsTab"><%@ include file="/views/employee/view_employee_teaching_subjects.jsp" %></div>

      </div>
    </td>
  </tr>
</table>
