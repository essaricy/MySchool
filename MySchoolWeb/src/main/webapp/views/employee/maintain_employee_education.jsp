<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<style>
.userFormTable {
  font-size: 1.2em;
}
</style>
<script type="text/javascript">
$(document).ready(function() {
  $('#create').click(function() {
    validateEmployeeAttribute('CREATE',
        '<%=request.getContextPath()%>/employee-attribute/doValidate.htm', 'EmployeeEducation',
        getEmployeeEducation(), employeeEducationAttributeSequence);
  });

  $('#update').click(function() {
    validateEmployeeAttribute('UPDATE',
        '<%=request.getContextPath()%>/employee-attribute/doValidate.htm', 'EmployeeEducation',
        getEmployeeEducation(), employeeEducationAttributeSequence);
  });
});

function getEmployeeEducation() {
    var EmployeeEducation = new Object();
    EmployeeEducation.EmployeeEducationId=$('#EmployeeEducationId').val();
    EmployeeEducation.Degree=$('#Degree').val();
    EmployeeEducation.Specialization=$('#Specialization').val();
    EmployeeEducation.College=$('#College').val();
    EmployeeEducation.University=$('#University').val();
    EmployeeEducation.YearOfGraduation=$('#YearOfGraduation').val();
    EmployeeEducation.Percentage=$('#Percentage').val();
  return EmployeeEducation;
}
</script>

<c:if test="${EmployeeEducation == null}">
<table width="80%" class="userFormTable" align="center" border="0" cellspacing="10" cellpadding="5">
  <tr>
    <td width="40%" class="label">Degree<label class="mandatory">*</label></td>
    <td width="60%" class="value">
      <input type="text" id="Degree" maxlength="32" />
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">Specialization</td>
    <td width="60%" class="value">
      <input type="text" id="Specialization" maxlength="32" />
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">College<label class="mandatory">*</label></td>
    <td width="60%" class="value">
      <input id="College" type="text" maxlength="64" />
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">University<label class="mandatory">*</label></td>
    <td width="60%" class="value">
      <input id="University" type="text" maxlength="64" />
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">Year Of Graduation<label class="mandatory">*</label></td>
    <td width="60%" class="value">
      <input id="YearOfGraduation" type="text" maxlength="4" />
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">Percentage<label class="mandatory">*</label></td>
    <td width="60%" class="value">
      <input id="Percentage" type="text" maxlength="3" />
    </td>
  </tr>
  <tr>
    <td colspan="2" align="center">
      <input type="hidden" id="EmployeeEducationId" value="0" />
      <input type="button" id="create" class="active" value='<spring:message code="common.create"/>' />
    </td>
  </tr>
</table>
</c:if>

<c:if test="${EmployeeEducation != null}">
<table width="80%" class="userFormTable" align="center" border="0" cellspacing="10" cellpadding="5">
  <tr>
    <td width="40%" class="label">Degree<label class="mandatory">*</label></td>
    <td width="60%" class="value">
      <input type="text" id="Degree" maxlength="32" value="${EmployeeEducation.degree}" />
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">Specialization</td>
    <td width="60%" class="value">
      <input type="text" id="Specialization" maxlength="32" value="${EmployeeEducation.specialization}" />
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">College<label class="mandatory">*</label></td>
    <td width="60%" class="value">
      <input id="College" type="text" maxlength="64" value="${EmployeeEducation.college}" />
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">University<label class="mandatory">*</label></td>
    <td width="60%" class="value">
      <input id="University" type="text" maxlength="64" value="${EmployeeEducation.university}" />
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">Year Of Graduation<label class="mandatory">*</label></td>
    <td width="60%" class="value">
      <input id="YearOfGraduation" type="text" maxlength="4" value="${EmployeeEducation.yearOfGraduation}" />
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">Percentage<label class="mandatory">*</label></td>
    <td width="60%" class="value">
      <input id="Percentage" type="text" maxlength="3" value="${EmployeeEducation.percentage}" />
    </td>
  </tr>
  <tr>
    <td colspan="2" align="center">
      <input type="hidden" id="EmployeeEducationId" value="${EmployeeEducation.educationId}" />
      <input type="button" id="update" class="active" value='<spring:message code="common.update"/>' />
    </td>
  </tr>
</table>
</c:if>