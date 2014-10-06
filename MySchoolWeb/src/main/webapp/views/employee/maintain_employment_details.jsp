<script type="text/javascript">
$(document).ready(function(){
  $("#EmployedAt").lazySelect({
    id: "EmployedAt",
    url: '<%=request.getContextPath()%>/branch/jsonList.htm',
    valueIndices: [2],
    selectOnCode: $('#EmployedAtBranchId').val()
  });
  $("#Designation").lazySelect({
    id: "Designation",
    url: '<%=request.getContextPath()%>/designation/jsonList.htm',
    prefixCode: true,
    selectOnCode: $('#DesignationId').val()
  });
  $("#EmploymentStatus").lazySelect({
    id: "EmploymentStatus",
    url: '<%=request.getContextPath()%>/employment/jsonList.htm',
    selectOnCode: $('#EmploymentStatusId').val()
  });

  $("#ReportingTo").lazySelect({
    id: "ReportingTo",
    url: '<%=request.getContextPath()%>/employee/verifiedEmployeesJSONList.htm',
    prefixCode: true,
    codeIndex: 1,
    valueIndices: [2, 3, 4],
    selectOnCode: $('#ReportingToId').val()
  });
  $('#Remarks').textcounter({id: 'Remarks'});

  $(this).datePicker({
    rangeId1: 'EmploymentStartDate',
    rangeId2: 'EmploymentEndDate'
  });
});

function getEmploymentDetails() {
  var EmploymentData = new Object();
  EmploymentData.EmployeeNumber=jQuery('#EmployeeNumber').val();
  EmploymentData.EmployedAtBranch=jQuery('#EmployedAt').val();
  EmploymentData.DesignationId=jQuery('#Designation').val();
  EmploymentData.EmploymentStatusId=jQuery('#EmploymentStatus').val();
  EmploymentData.EmploymentStartDate=jQuery('#EmploymentStartDate').val();
  EmploymentData.EmploymentEndDate=jQuery('#EmploymentEndDate').val();
  EmploymentData.ReportingTo=jQuery('#ReportingTo').val();
  EmploymentData.Remarks=jQuery('#Remarks').val();
  return EmploymentData;
}
</script>
<c:if test="${Employee == null}">
<table width="80%" class="userFormTable" align="center" border="0" cellspacing="10" cellpadding="5">
  <tr>
    <td width="40%" class="label"><spring:message code="employee.number"/><label class="mandatory">*</label></td>
    <td width="60%" class="value">
      <input type="text" id="EmployeeNumber" maxlength="10" />
      <a href="#" id="LastEmployeeNumber" class="formDataLink">Get Last Employee Number</a>
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">Employed At<label class="mandatory">*</label></td>
    <td width="60%" class="value">
      <select id="EmployedAt" class="chosen-select">
        <option value="0">Select</option>
      </select>
    </td>
  </tr>
  <tr>
    <td width="40%" class="label"><spring:message code="employee.designation"/><label class="mandatory">*</label></td>
    <td width="60%" class="value">
      <select id="Designation" class="chosen-select">
        <option value="0">Select</option>
      </select>
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">Employment Status<label class="mandatory">*</label></td>
    <td width="60%" class="value">
      <select id="EmploymentStatus" class="chosen-select">
        <option value="0">Select</option>
      </select>
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">Employment Start Date<label class="mandatory">*</label></td>
    <td width="60%" class="value">
      <input type="text" id="EmploymentStartDate" class="datepicker" />
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">Employment End Date</td>
    <td width="60%" class="value">
      <input type="text" id="EmploymentEndDate" class="datepicker" />
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">Reporting To</td>
    <td width="60%" class="value">
      <select id="ReportingTo" class="chosen-select">
        <option value="0">Select</option>
      </select>
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">Remarks</td>
    <td width="60%" class="value">
      <textarea id="Remarks" rows="5" maxlength="512"></textarea>
    </td>
  </tr>
</table>
</c:if>

<c:if test="${Employee != null}">
<table width="80%" class="userFormTable" align="center" border="0" cellspacing="10" cellpadding="5">
  <tr>
    <td width="40%" class="label"><spring:message code="employee.number"/></td>
    <td width="60%" class="value">
      <input type="hidden" id="EmployeeNumber" value="${Employee.employeeNumber}" />
      <b>${Employee.employeeNumber}</b>
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">Employed At<label class="mandatory">*</label></td>
    <td width="60%" class="value">
      <input type="hidden" id="EmployedAtBranchId" value="${Employee.employedAtBranch.branchId}" />
      <select id="EmployedAt" class="chosen-select">
        <option value="0">Select</option>
      </select>
    </td>
  </tr>
  <tr>
    <td width="40%" class="label"><spring:message code="employee.designation"/><label class="mandatory">*</label></td>
    <td width="60%" class="value">
      <input type="hidden" id="DesignationId" value="${Employee.designation.designationId}" />
      <select id="Designation" class="chosen-select">
        <option value="0">Select</option>
      </select>
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">Employment Status<label class="mandatory">*</label></td>
    <td width="60%" class="value">
      <input type="hidden" id="EmploymentStatusId" value="${Employee.employmentStatus.statusId}" />
      <select id="EmploymentStatus" class="chosen-select">
        <option value="0">Select</option>
      </select>
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">Employment Start Date<label class="mandatory">*</label></td>
    <td width="60%" class="value">
      <input type="text" id="EmploymentStartDate" class="datepicker" value="${Employee.employmentStartDate}" />
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">Employment End Date</td>
    <td width="60%" class="value">
      <input type="text" id="EmploymentEndDate" class="datepicker" value="${Employee.employmentEndDate}" />
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">Reporting To</td>
    <td width="60%" class="value">
      <input type="hidden" id="ReportingToId" value="${Employee.reportingTo.employeeNumber}" />
      <select id="ReportingTo" class="chosen-select">
        <option value="0">Select</option>
      </select>
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">Remarks</td>
    <td width="60%" class="value">
      <textarea id="Remarks" rows="5" maxlength="512">${Employee.remarks}</textarea>
    </td>
  </tr>
</table>
</c:if>