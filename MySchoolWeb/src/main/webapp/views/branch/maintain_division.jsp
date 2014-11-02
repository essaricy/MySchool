<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<script type="text/javascript">
$(document).ready(function() {
  $('#create').click(function() {
    $.ajax({
      type: "POST",
      url: "<%=request.getContextPath()%>/division/doCreate.htm",
      dataType: 'xml',
      data: {
        divisionCode: $('#divisionCode').val(),
        description: $('#description').val()
      }, 
      context: this
    }).done(function(result) {
      parseModelReponse(result);
    });
  });

  $('#update').click(function() {
    $.ajax({
      type: "POST",
      url: "<%=request.getContextPath()%>/division/doUpdate.htm",
      dataType: 'xml',
      data: {
        divisionId: $('#divisionId').val(),
        divisionCode: $('#divisionCode').val(),
        description: $('#description').val()
      }, 
      context: this
    }).done(function(result) {
      parseModelReponse(result);
    });
  });
 
  $('#description').textcounter({
    id: 'description'
  });
});
</script>

<c:if test="${division == null}">
  <table width="80%" class="userFormTable" align="center" border="0" cellspacing="10" cellpadding="5">
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
        <input type="button" id="create" class="active" value='<spring:message code="common.create"/>' />
      </td>
    </tr>
  </table>
</c:if>

<c:if test="${division != null}">
  <table width="80%" class="userFormTable" align="center" border="0" cellspacing="10" cellpadding="5">
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
        <input type="button" id="update" class="active" value='<spring:message code="common.update"/>' />
      </td>
    </tr>
  </table>
</c:if>
