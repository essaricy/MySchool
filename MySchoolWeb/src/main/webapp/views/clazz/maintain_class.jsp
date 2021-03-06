<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<script type="text/javascript">
$(document).ready(function() {
  $('#create').click(function() {
    $.ajax({
      type: "POST",
      url: "<%=request.getContextPath()%>/class/doCreate.htm",
      data: {
        className: $('#className').val(),
        promotionOrder: $('#promotionOrder').val()
      }, 
      context: this
    }).done(function(result) {
      handleServerResponseOnModal(result);
    });
  });

  $('#update').click(function() {
    $.ajax({
      type: "POST",
      url: "<%=request.getContextPath()%>/class/doUpdate.htm",
      data: {
        classId: $('#classId').val(),
        className: $('#className').val(),
        promotionOrder: $('#promotionOrder').val()
      }, 
      context: this
    }).done(function(result) {
      handleServerResponseOnModal(result);
    });
  });
});
</script>

<table class="formTable_Data">
  <c:if test="${classDto == null}">
    <tr>
      <td class="label" width="40%"><spring:message code="class.name"/><label class="mandatory">*</label></td>
      <td width="60%" class="value">
        <input type="text" id="className" maxlength="16"/>
      </td>
    </tr>
    <tr>
      <td class="label" width="40%"><spring:message code="class.promotionOrder"/><label class="mandatory">*</label></td>
      <td width="60%" class="value">
        <input type="text" id="promotionOrder" maxlength="3"/>
      </td>
    </tr>
    <tr>
      <td colspan="2" align="center">
        <input type="button" id="create" value='<spring:message code="common.create"/>' />
      </td>
    </tr>
  </c:if>

  <c:if test="${classDto != null}">
    <tr>
      <td class="label" width="40%"><spring:message code="class.name"/><label class="mandatory">*</label></td>
      <td width="60%" class="value">
        <input type="text" id="className"  maxlength="16" value="${classDto.className}"/>
      </td>
    </tr>
    <tr>
      <td class="label" width="40%"><spring:message code="class.promotionOrder"/><label class="mandatory">*</label></td>
      <td width="60%" class="value">
        <input type="text" id="promotionOrder"  maxlength="3" value="${classDto.promotionOrder}"/>
      </td>
    </tr>
    <tr>
      <td colspan="2" align="center">
        <input type="hidden" id="classId" value="${classDto.classId}" />
        <input type="button" id="update" value='<spring:message code="common.update"/>' />
      </td>
    </tr>
  </c:if>
</table>
