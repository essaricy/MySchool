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
  $(this).lazySelect({id: "Document", url: '<%=request.getContextPath()%>/document/jsonList.htm?type=employee', selectOnCode: $('#DocumentId').val()});
  $(this).datePicker({id: 'DocumentExpiryDate'});

  $('#create').click(function() {
    validateEmployeeAttribute('CREATE',
        '<%=request.getContextPath()%>/employee-attribute/doValidate.htm', 'EmployeeDocument',
        getEmployeeDocument(), employeeDocumentAttributeSequence);
  });

  $('#update').click(function() {
    validateEmployeeAttribute('UPDATE',
        '<%=request.getContextPath()%>/employee-attribute/doValidate.htm', 'EmployeeDocument',
        getEmployeeDocument(), employeeDocumentAttributeSequence);
  });

  function getEmployeeDocument() {
    var EmployeeDocument = new Object();
    EmployeeDocument.EmployeeDocumentId=$('#EmployeeDocumentId').val();
    EmployeeDocument.DocumentId=$('#Document').val();
    EmployeeDocument.DocumentName=$('#Document option:selected').html();
    EmployeeDocument.DocumentNumber=$('#DocumentNumber').val();
    EmployeeDocument.DocumentExpiryDate=$('#DocumentExpiryDate').val();
    EmployeeDocument.DocumentIssuedBy=$('#DocumentIssuedBy').val();
    return EmployeeDocument;
  }
});

</script>
<c:if test="${EmployeeDocument == null}">
<table width="80%" class="userFormTable" align="center" border="0" cellspacing="10" cellpadding="5">
  <tr>
    <td width="40%" class="label">Document Type<label class="mandatory">*</label></td>
    <td width="60%" class="value">
      <select id="Document" class="chosen-select">
      </select>
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">Document Number<label class="mandatory">*</label></td>
    <td width="60%" class="value">
      <input type="text" id="DocumentNumber" maxlength="32" />
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">Document Expiry Date</td>
    <td width="60%" class="value">
      <input id="DocumentExpiryDate" type="text" class="datepicker" />
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">Issued By</td>
    <td width="60%" class="value">
      <input type="text" id="DocumentIssuedBy" maxlength="80" />
    </td>
  </tr>
  <tr>
    <td colspan="2" align="center">
      <input type="hidden" id="EmployeeDocumentId" value="0" />
      <input type="button" id="create" class="active" value='<spring:message code="common.create"/>' />
    </td>
  </tr>
</table>
</c:if>

<c:if test="${EmployeeDocument != null}">
<table width="80%" class="userFormTable" align="center" border="0" cellspacing="10" cellpadding="5">
  <tr>
    <td width="40%" class="label">Document Type<label class="mandatory">*</label></td>
    <td width="60%" class="value">
      <input type="hidden" id="DocumentId" value="${EmployeeDocument.document.documentId}" />
      <select id="Document" class="chosen-select">
      </select>
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">Document Number<label class="mandatory">*</label></td>
    <td width="60%" class="value">
      <input type="text" id="DocumentNumber" maxlength="32" value="${EmployeeDocument.documentNumber}" />
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">Document Expiry Date</td>
    <td width="60%" class="value">
      <input id="DocumentExpiryDate" type="text" class="datepicker" value="${EmployeeDocument.documentExpiryDate}" />
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">Issued By</td>
    <td width="60%" class="value">
      <input type="text" id="DocumentIssuedBy" maxlength="80" value="${EmployeeDocument.documentIssuedBy}" />
    </td>
  </tr>
  <tr>
    <td colspan="2" align="center">
      <input type="hidden" id="EmployeeDocumentId" value="${EmployeeDocument.employeeDocumentId}" />
      <input type="button" id="update" class="active" value='<spring:message code="common.update"/>' />
    </td>
  </tr>
</table>
</c:if>