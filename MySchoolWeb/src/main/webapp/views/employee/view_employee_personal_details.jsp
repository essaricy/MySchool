<style>
.userFormTable td.label,
.userFormTable td.value {
    font-size: 0.8em;
}
</style>

<c:if test="${Employee != null}">
<table width="80%" class="userFormTable" align="center" border="0" cellspacing="10" cellpadding="5">
  <tr>
    <td width="40%" class="label"><spring:message code="common.firstName"/></td>
    <td width="60%" class="value">${Employee.firstName}</td>
  </tr>
  <tr>
    <td width="40%" class="label"><spring:message code="common.middleName"/></td>
    <td width="60%" class="value">${Employee.middleName}</td>
  </tr>
  <tr>
    <td width="40%" class="label"><spring:message code="common.lastName"/></td>
    <td width="60%" class="value">${Employee.lastName}</td>
  </tr>
  <tr>
    <td width="40%" class="label"><spring:message code="common.gender"/></td>
    <td  width="60%" class="value">
      <myschool:gender id="Gender" uiControl="label" value="${Employee.gender}" />
    </td>
  </tr>
  <tr>
    <td width="40%" class="label"><spring:message code="common.dateOfBirth"/></td>
    <td width="60%" class="value">${Employee.dateOfBirth}</td>
  </tr>
  <tr>
    <td width="40%" class="label">Blood Group</td>
    <td width="60%" class="value">
      <myschool:bloodgroup id="BloodGroup" uiControl="label" value="${Employee.bloodGroup}" />
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">Nationality</td>
    <td width="60%" class="value">
      <myschool:nationality id="Nationality" uiControl="label" value="${Employee.nationality}" />
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">Marital Status</td>
    <td width="60%" class="value">
      <myschool:marital-status id="MaritalStatus" uiControl="label" value="${Employee.maritalStatus}" />
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">Wedding Day</td>
    <td width="60%" class="value">${Employee.weddingDay}</td>
  </tr>
</table>
</c:if>
