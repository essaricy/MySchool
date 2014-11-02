<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<script type="text/javascript">
$(document).ready(function() {
  $('#create').click(function() {
    $.ajax({
      type: "POST",
      url: "<%=request.getContextPath()%>/employment/doCreate.htm",
      data: {
        description: $('#description').val(),
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
      url: "<%=request.getContextPath()%>/employment/doUpdate.htm",
      data: {
    	  employmentStatusId: $('#employmentStatusId').val(),
          description: $('#description').val(),
          sid: new Date().getTime()
      },
      context: this
    }).done(function(result) {
      parseModelReponse(result);
    });
  });
});
</script>

<c:if test="${employmentStatus == null}">
<table width="80%" class="userFormTable" align="center" border="0" cellspacing="10" cellpadding="5">
  <tr>
    <td width="40%" class="label">Employment Status<label class="mandatory">*</label></td>
    <td width="60%" class="value">
      <input type="text" id="description" maxlength="64"/>
    </td>
  </tr>
  <tr>
    <td colspan="2" align="center">
        <input type="button" id="create" class="active" value='<spring:message code="common.create"/>' />
    </td>
  </tr>
</table>
</c:if>

<c:if test="${employmentStatus != null}">
<table width="80%" class="userFormTable" align="center" border="0" cellspacing="10" cellpadding="5">
  <tr>
    <td width="40%" class="label">Employment Status<label class="mandatory">*</label></td>
    <td width="60%" class="value">
      <input type="text" id="description" maxlength="64" value="${employmentStatus.description}" />
    </td>
  </tr>
  <tr>
    <td colspan="2" align="center">
        <input type="hidden" id="employmentStatusId" value="${employmentStatus.statusId}" />
        <input type="button" id="update" class="active" value='<spring:message code="common.update"/>' />
    </td>
  </tr>
</table>
</c:if>