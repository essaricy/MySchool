<c:if test="${Employee != null}">
<table class="formTable_Data">
  <tr>
    <td width="40%" class="label"><spring:message code="employee.number"/></td>
    <td width="60%" class="value"><b>${Employee.employeeNumber}</b></td>
  </tr>
  <tr>
    <td width="40%" class="label">Employed At</td>
    <td width="60%" class="value">${Employee.employedAtBranch.branchCode}</td>
  </tr>
  <tr>
    <td width="40%" class="label"><spring:message code="employee.designation"/></td>
    <td width="60%" class="value">${Employee.designation.designationId} - ${Employee.designation.designation}</td>
  </tr>
  <tr>
    <td width="40%" class="label">Employment Status</td>
    <td width="60%" class="value">${Employee.employmentStatus.description}</td>
  </tr>
  <tr>
    <td width="40%" class="label">Employment Start Date</td>
    <td width="60%" class="value">${Employee.employmentStartDate}</td>
  </tr>
  <tr>
    <td width="40%" class="label">Employment End Date</td>
    <td width="60%" class="value">${Employee.employmentEndDate}</td>
  </tr>
  <tr>
    <td width="40%" class="label">Reporting To</td>
    <td width="60%" class="value">${Employee.reportingTo.employeeNumber}</td>
  </tr>
  <tr>
    <td width="40%" class="label">Remarks</td>
    <td width="60%" class="value">${Employee.remarks}</td>
  </tr>
</table>
</c:if>