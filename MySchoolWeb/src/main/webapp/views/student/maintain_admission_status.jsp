<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<script type="text/javascript">
$(document).ready(function() {
  $('#create').click(function() {
    $.ajax({
      type: "POST",
      url: "<%=request.getContextPath()%>/admission-status/doCreate.htm",
      data: {
        Description: $('#Description').val(),
        sid: new Date().getTime()
      },
      context: this
    }).done(function(result) {
      parseModelReponse(result);
    });
  });

  $('#update').click(function() {
    $.ajax({
      type: "POST",
      url: "<%=request.getContextPath()%>/admission-status/doUpdate.htm",
      data: {
    	  AdmissionStatusId: $('#AdmissionStatusId').val(),
          Description: $('#Description').val(),
          sid: new Date().getTime()
      },
      context: this
    }).done(function(result) {
      parseModelReponse(result);
    });
  });
});
</script>

<c:if test="${AdmissionStatus == null}">
<table width="80%" class="userFormTable" align="center" border="0" cellspacing="10" cellpadding="5">
  <tr>
    <td width="40%" class="label">Admission Status<label class="mandatory">*</label></td>
    <td width="60%" class="value">
      <input type="text" id="Description" maxlength="64"/>
    </td>
  </tr>
  <tr>
    <td colspan="2" align="center">
        <input type="button" id="create" class="active" value='<spring:message code="common.create"/>' />
    </td>
  </tr>
</table>
</c:if>

<c:if test="${AdmissionStatus != null}">
<table width="80%" class="userFormTable" align="center" border="0" cellspacing="10" cellpadding="5">
  <tr>
    <td width="40%" class="label">Admission Status<label class="mandatory">*</label></td>
    <td width="60%" class="value">
      <input type="text" id="Description" maxlength="64" value="${AdmissionStatus.description}" />
    </td>
  </tr>
  <tr>
    <td colspan="2" align="center">
        <input type="hidden" id="AdmissionStatusId" value="${AdmissionStatus.statusId}" />
        <input type="button" id="update" class="active" value='<spring:message code="common.update"/>' />
    </td>
  </tr>
</table>
</c:if>