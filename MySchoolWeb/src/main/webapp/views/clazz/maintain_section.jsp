<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<script type="text/javascript">
$(document).ready(function() {
  $('#create').click(function() {
    $.ajax({
      type: "POST",
      url: "<%=request.getContextPath()%>/section/doCreate.htm",
      data: {
        sectionName: $('#sectionName').val()
      },
      context: this
    }).done(function(result) {
      parseModelResponse(result);
    });
  });

  $('#update').click(function() {
    $.ajax({
      type: "POST",
      url: "<%=request.getContextPath()%>/section/doUpdate.htm",
      data: {
        sectionId: $('#sectionId').val(),
        sectionName: $('#sectionName').val()
      },
      context: this
    }).done(function(result) {
      parseModelResponse(result);
    });
  });
});
</script>

<c:if test="${section == null}">
  <table width="80%" class="userFormTable" align="center" border="0" cellspacing="10" cellpadding="5">
    <tr>
      <td class="label" width="40%"><spring:message code="section.name"/><label class="mandatory">*</label></td>
      <td class="value" width="60%">
        <input type="text" id="sectionName" maxlength="32" />
      </td>
    </tr>
    <tr>
      <td colspan="2" align="center">
        <input type="button" id="create" class="active" value='<spring:message code="common.create"/>' />
      </td>
    </tr>
  </table>
</c:if>

<c:if test="${section != null}">
  <table width="80%" class="userFormTable" align="center" border="0" cellspacing="10" cellpadding="5">
    <tr>
      <td class="label" width="40%"><spring:message code="section.name"/><label class="mandatory">*</label></td>
      <td class="value" width="60%">
        <input type="text" id="sectionName" maxlength="32" value="${section.sectionName}" />
      </td>
    </tr>
    <tr>
      <td colspan="2" align="center">
          <input type="hidden" id="sectionId" value="${section.sectionId}" />
          <input type="button" id="update" class="active" value='<spring:message code="common.update"/>' />
      </td>
    </tr>
  </table>
</c:if>
