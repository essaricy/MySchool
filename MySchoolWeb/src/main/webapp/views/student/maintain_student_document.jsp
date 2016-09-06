<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<style>
.userFormTable {
  font-size: 0.8em;
}
</style>
<script type="text/javascript">
$(document).ready(function() {
  $(this).lazySelect({id: "StudentDocument_Document", url: '<%=request.getContextPath()%>/document/jsonList.htm?type=student', selectOnCode: $('#StudentDocument_DocumentId').val()});
  $(this).datePicker({id: 'StudentDocument_DocumentExpiryDate'});

  $('#create').click(function() {
    validateStudentAttribute('CREATE',
        '<%=request.getContextPath()%>/student-attribute/doValidate.htm', 'StudentDocument',
        getStudentDocument(), studentDocumentAttributeSequence);
  });

  $('#update').click(function() {
    validateStudentAttribute('UPDATE',
        '<%=request.getContextPath()%>/student-attribute/doValidate.htm', 'StudentDocument',
        getStudentDocument(), studentDocumentAttributeSequence);
  });

  function getStudentDocument() {
    var StudentDocument = new Object();
    StudentDocument.StudentDocumentId=$('#StudentDocument_StudentDocumentId').val();
    StudentDocument.DocumentId=$('#StudentDocument_Document').val();
    StudentDocument.DocumentName=$('#StudentDocument_Document option:selected').html();
    StudentDocument.DocumentNumber=$('#StudentDocument_DocumentNumber').val();
    StudentDocument.DocumentExpiryDate=$('#StudentDocument_DocumentExpiryDate').val();
    StudentDocument.DocumentIssuedBy=$('#StudentDocument_DocumentIssuedBy').val();
    return StudentDocument;
  }
});

</script>
<c:if test="${StudentDocument == null}">
<table class="formTable_Data">
  <tr>
    <td width="40%" class="label">Document Type<label class="mandatory">*</label></td>
    <td width="60%" class="value">
      <input type="hidden" id="StudentDocument_DocumentId" value="0" />
      <select id="StudentDocument_Document" class="chosen-select">
      </select>
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">Document Number<label class="mandatory">*</label></td>
    <td width="60%" class="value">
      <input type="text" id="StudentDocument_DocumentNumber" maxlength="32" />
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">Document Expiry Date</td>
    <td width="60%" class="value">
      <input type="text" id="StudentDocument_DocumentExpiryDate" class="datepicker" />
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">Issued By</td>
    <td width="60%" class="value">
      <input type="text" id="StudentDocument_DocumentIssuedBy" maxlength="80" />
    </td>
  </tr>
  <tr>
    <td colspan="2" align="center">
      <input type="hidden" id="StudentDocument_StudentDocumentId" value="0" />
      <input type="button" id="create" value='<spring:message code="common.create"/>' />
    </td>
  </tr>
</table>
</c:if>

<c:if test="${StudentDocument != null}">
<table class="formTable_Data">
  <tr>
    <td width="40%" class="label">Document Type<label class="mandatory">*</label></td>
    <td width="60%" class="value">
      <input type="hidden" id="StudentDocument_DocumentId" value="${StudentDocument.document.documentId}" />
      <select id="StudentDocument_Document" class="chosen-select">
      </select>
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">Document Number<label class="mandatory">*</label></td>
    <td width="60%" class="value">
      <input type="text" id="StudentDocument_DocumentNumber" maxlength="32" value="${StudentDocument.documentNumber}" />
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">Document Expiry Date</td>
    <td width="60%" class="value">
      <input type="text" id="StudentDocument_DocumentExpiryDate" class="datepicker" value="${StudentDocument.documentExpiryDate}" />
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">Issued By</td>
    <td width="60%" class="value">
      <input type="text" id="StudentDocument_DocumentIssuedBy" maxlength="80" value="${StudentDocument.documentIssuedBy}" />
    </td>
  </tr>
  <tr>
    <td colspan="2" align="center">
      <input type="hidden" id="StudentDocument_StudentDocumentId" value="${StudentDocument.studentDocumentId}" />
      <input type="button" id="update" value='<spring:message code="common.update"/>' />
    </td>
  </tr>
</table>
</c:if>
