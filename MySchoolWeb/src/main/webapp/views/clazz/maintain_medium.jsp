<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<script type="text/javascript">
$(document).ready(function() {
  $('#create').click(function() {
    $.ajax({
      type: "POST",
      url: "<%=request.getContextPath()%>/medium/doCreate.htm",
      data: {
        description: $('#description').val()
      }, 
      context: this
    }).done(function(result) {
      handleServerResponseOnModal(result);
    });
  });

  $('#update').click(function() {
    $.ajax({
      type: "POST",
      url: "<%=request.getContextPath()%>/medium/doUpdate.htm",
      data: {
         mediumId: $('#mediumId').val(),
         description: $('#description').val()
      }, 
      context: this
    }).done(function(result) {
      handleServerResponseOnModal(result);
    });
  });

  $('#description').textcounter({
    id: 'description'
  });
});
</script>

<c:if test="${medium == null}">
  <table class="formTable_Data">
    <tr>
      <td class="label" width="40%"><spring:message code="common.description"/><label class="mandatory">*</label></td>
      <td class="value" width="60%">
        <textArea id="description" maxlength="16"></textArea>
      </td>
    </tr>
    <tr>
      <td colspan="2" align="center">
        <input type="button" id="create" value='<spring:message code="common.create"/>' />
      </td>
    </tr>
  </table>
</c:if>

<c:if test="${medium != null}">
  <table class="formTable_Data">
    <tr>
      <td class="label" width="40%"><spring:message code="common.description"/><label class="mandatory">*</label></td>
      <td class="value" width="60%">
        <textArea name="description" id="description" maxlength="16">${medium.description}</textArea>
      </td>
    </tr>
    <tr>
      <td colspan="2" align="center">
        <input type="hidden" id="mediumId" value="${medium.mediumId}" />
        <input type="button" id="update" value='<spring:message code="common.update"/>' />
      </td>
    </tr>
  </table>
</c:if>