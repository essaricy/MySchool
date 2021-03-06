<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<script type="text/javascript">
$(document).ready(function() {
  $('#create').click(function() {
    $.ajax({
      type: "POST",
      url: "<%=request.getContextPath()%>/division/doCreate.htm",
      data: {
        divisionCode: $('#divisionCode').val(),
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
      url: "<%=request.getContextPath()%>/division/doUpdate.htm",
      data: {
        divisionId: $('#divisionId').val(),
        divisionCode: $('#divisionCode').val(),
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

<c:if test="${division == null}">
  <table class="formTable_Data">
    <tr>
      <td class="label" width="40%"><spring:message code="division.code"/> <label class="mandatory">*</label></td>
      <td class="value" width="60%">
        <input type="text" id="divisionCode" maxlength="8"/>
      </td>
    </tr>
    <tr>
      <td class="label" width="40%" valign="top"><spring:message code="common.description"/><label class="mandatory">*</label></td>
      <td class="value" width="60%">
        <textarea id="description" maxlength="256"></textarea>
      </td>
    </tr>
    <tr>
      <td colspan="2" align="center">
        <input type="button" id="create" value='<spring:message code="common.create"/>' />
      </td>
    </tr>
  </table>
</c:if>

<c:if test="${division != null}">
  <table class="formTable_Data">
    <tr>
      <td class="label" width="40%"><spring:message code="division.code"/> <label class="mandatory">*</label></td>
      <td class="value" width="60%">
        <input type="text" id="divisionCode" maxlength="8" value="${division.divisionCode}"/>
      </td>
    </tr>
    <tr>
      <td class="label" width="40%" valign="top"><spring:message code="common.description"/><label class="mandatory">*</label></td>
      <td class="value" width="60%">
        <textarea id="description" maxlength="256">${division.description}</textarea>
      </td>
    </tr>
    <tr>
      <td colspan="2" align="center">
        <input type="hidden" id="divisionId" value="${division.divisionId}" />
        <input type="button" id="update" value='<spring:message code="common.update"/>' />
      </td>
    </tr>
  </table>
</c:if>
