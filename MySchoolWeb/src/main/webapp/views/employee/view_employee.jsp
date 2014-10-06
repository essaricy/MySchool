<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="myschool" tagdir="/WEB-INF/tags" %>

<style>
#EmployeeAccordion p {
  font-size: 0.7em;
  font-weight: bold;
  text-align: left;
}
</style>
<script type="text/javascript" language="javascript" src="<%=request.getContextPath()%>/scripts/myschool-employee-attributes.js"></script>
<script>

jQuery(document).ready(function() {
  $(this).myAccordion({id: 'EmployeeAccordion'});
  $("#EmployeeAccordion").accordion( "option", "active", 0);
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
      <table cellpadding="5" cellspacing="0" border="0" width="100%" height="100%" class="formTable">
        <tr>
          <td align="center">
            <img id="employeeImage" name="employeeImage" src="<%=request.getContextPath()%>/image/getImage.htm?type=employee&imageSize=ORIGINAL&contentId=${Employee.employeeNumber}&sid=<%= new java.util.Date().getTime()%>" border="1" width="150px" height="180px"/>
          </td>
        </tr>
      </table>
    </td>
    <td width="85%" valign="top">
      <div id="EmployeeAccordion">
        <p class="title">Employment Details</p>
        <div><%@ include file="/views/employee/view_employment_details.jsp" %></div>
        <p class="title">Personal Details</p>
        <div><%@ include file="/views/employee/view_employee_personal_details.jsp" %></div>
        <p class="title">Employee Contact Details</p>
        <div><%@ include file="/views/employee/view_employee_contact_details.jsp" %></div>
        <p class="title">Employee Documents</p>
        <div><%@ include file="/views/employee/view_employee_document_details.jsp" %></div>
        <p class="title">Employee Education</p>
        <div><%@ include file="/views/employee/view_employee_education_details.jsp" %></div>
        <p class="title">Employee Experience</p>
        <div><%@ include file="/views/employee/view_employee_experience_details.jsp" %></div>
        <p class="title">Employee Promotions</p>
        <div><%@ include file="/views/employee/view_employee_promotion_details.jsp" %></div>
        <p class="title">Employee Teaching Subjects</p>
        <div><%@ include file="/views/employee/view_employee_teaching_subjects.jsp" %></div>
      </div>
    </td>
  </tr>
</table>
