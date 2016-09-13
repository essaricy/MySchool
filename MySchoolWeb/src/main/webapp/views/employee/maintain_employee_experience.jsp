<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<style>
.formTable_Data {
  font-size: 1.2em;
}
</style>
<script type="text/javascript">
$(document).ready(function() {
  $(this).datePicker({rangeId1: 'FromDate', rangeId2: 'ToDate'});

  $('#create').click(function() {
    validateEmployeeAttribute('CREATE',
        '<%=request.getContextPath()%>/employee-attribute/doValidate.htm', 'EmployeeExperience',
        getEmployeeExperience(), employeeExperienceAttributeSequence);
  });

  $('#update').click(function() {
    validateEmployeeAttribute('UPDATE',
        '<%=request.getContextPath()%>/employee-attribute/doValidate.htm', 'EmployeeExperience',
        getEmployeeExperience(), employeeExperienceAttributeSequence);
  });
});

function getEmployeeExperience() {
    var EmployeeExperience = new Object();
    EmployeeExperience.EmployeeExperienceId=$('#EmployeeExperienceId').val();
    EmployeeExperience.Employer=$('#Employer').val();
    EmployeeExperience.JobTitle=$('#JobTitle').val();
    EmployeeExperience.FromDate=$('#FromDate').val();
    EmployeeExperience.ToDate=$('#ToDate').val();
    EmployeeExperience.ExperienceInMonths='-';
    return EmployeeExperience;
}
</script>

<c:if test="${EmployeeExperience == null}">
<table class="formTable_Data">
  <tr>
    <td width="40%" class="label">Employer<label class="mandatory">*</label></td>
    <td width="60%" class="value">
      <input type="text" id="Employer" maxlength="64" />
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">Job Title<label class="mandatory">*</label></td>
    <td width="60%" class="value">
      <input type="text" id="JobTitle" maxlength="64" />
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">From<label class="mandatory">*</label></td>
    <td width="60%" class="value">
      <input id="FromDate" type="text" class="datepicker" />
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">Till<label class="mandatory">*</label></td>
    <td width="60%" class="value">
      <input id="ToDate" type="text" class="datepicker" />
    </td>
  </tr>
  <tr>
    <td colspan="2" align="center">
      <input type="hidden" id="EmployeeExperienceId" value="0" />
      <input type="button" id="create" value='<spring:message code="common.create"/>' />
    </td>
  </tr>
</table>
</c:if>

<c:if test="${EmployeeExperience != null}">
<table class="formTable_Data">
  <tr>
    <td width="40%" class="label">Employer<label class="mandatory">*</label></td>
    <td width="60%" class="value">
      <input type="text" id="Employer" maxlength="64" value="${EmployeeExperience.employer}" />
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">Job Title<label class="mandatory">*</label></td>
    <td width="60%" class="value">
      <input type="text" id="JobTitle" maxlength="64"  value="${EmployeeExperience.jobTitle}"/>
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">From<label class="mandatory">*</label></td>
    <td width="60%" class="value">
      <input id="FromDate" type="text" class="datepicker"  value="${EmployeeExperience.fromDate}"/>
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">Till<label class="mandatory">*</label></td>
    <td width="60%" class="value">
      <input id="ToDate" type="text" class="datepicker"  value="${EmployeeExperience.toDate}"/>
    </td>
  </tr>
  <tr>
    <td colspan="2" align="center">
      <input type="hidden" id="EmployeeExperienceId" value="${EmployeeExperience.experienceId}" />
      <input type="button" id="update" value='<spring:message code="common.update"/>' />
    </td>
  </tr>
</table>
</c:if>
