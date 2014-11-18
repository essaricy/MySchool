<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<script type="text/javascript">
$(document).ready(function() {
  $('#create').click(function() {
    $.ajax({
      type: "POST",
      url: "<%=request.getContextPath()%>/designation/doCreate.htm",
      data: {
        designationId: $('#designationId').val(),
        designation: $('#designation').val()
      }, 
      context: this
    }).done(function(result) {
      parseModelResponse(result);
    });
  });

  $('#update').click(function() {
    $.ajax({
      type: "POST",
      url: "<%=request.getContextPath()%>/designation/doUpdate.htm",
      data: {
        designationId: $('#designationId').val(),
        designation: $('#designation').val()
      }, 
      context: this
    }).done(function(result) {
      parseModelResponse(result);
    });
  });
});
</script>

<c:if test="${designation == null}">
<table width="80%" class="userFormTable" align="center" border="0" cellspacing="10" cellpadding="5">
  <tr>
    <td class="label" width="40%"><spring:message code="designation.id"/><label class="mandatory">*</label></td>
    <td width="60%" class="value">
      <input type="text" name="designationId" id="designationId" maxlength="3" />
    </td>
  </tr>
  <tr>
    <td class="label" width="40%"><spring:message code="designation.name"/><label class="mandatory">*</label></td>
    <td width="60%" class="value">
      <input type="text" name="designation" id="designation" maxlength="64" />
    </td>
  </tr>
  <tr>
    <td colspan="2" align="center">
      <input type="button" id="create" class="active" value='<spring:message code="common.create"/>' />
    </td>
  </tr>
</table>
</c:if>

<c:if test="${designation != null}">
<table width="80%" class="userFormTable" align="center" border="0" cellspacing="10" cellpadding="5">
  <tr>
    <td class="label" width="40%"><spring:message code="designation.id"/><label class="mandatory">*</label></td>
    <td width="60%" class="value">
      <input type="text" name="designationId" id="designationId" maxlength="3" value="${designation.designationId}" />
    </td>
  </tr>
  <tr>
    <td class="label" width="40%"><spring:message code="designation.name"/><label class="mandatory">*</label></td>
    <td width="60%" class="value">
      <input type="text" name="designation" id="designation" maxlength="64" value="${designation.designation}" />
    </td>
  </tr>
  <tr>
    <td colspan="2" align="center">
      <input type="hidden" id="designationId" value="${designation.designationId}" />
      <input type="button" id="update" class="active" value='<spring:message code="common.update"/>' />
    </td>
  </tr>
</table>
</c:if>
