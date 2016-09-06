<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<script type="text/javascript">
$(document).ready(function() {
  $('#create').click(function() {
    $.ajax({
      type: "POST",
      url: "<%=request.getContextPath()%>/examGrade/doCreate.htm",
      data: {
        gradeName: $('#gradeName').val(),
        qualifyingPercentage: $('#qualifyingPercentage').val()
      }, 
      context: this
    }).done(function(result) {
      handleServerResponseOnModal(result);
    });
  });

  $('#update').click(function() {
    $.ajax({
      type: "POST",
      url: "<%=request.getContextPath()%>/examGrade/doUpdate.htm",
      data: {
         examGradeId: $('#examGradeId').val(),
         gradeName: $('#gradeName').val(),
         qualifyingPercentage: $('#qualifyingPercentage').val()
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

<c:if test="${examGrade == null}">
  <table class="formTable_Data">
    <tr>
      <td class="label" width="40%"><spring:message code="examGrade.name"/><label class="mandatory">*</label></td>
      <td class="value" width="60%">
        <input type="text" id="gradeName" maxlength="2" />
      </td>
    </tr>
    <tr>
      <td class="label" width="40%"><spring:message code="examGrade.qualifyingPercentage"/><label class="mandatory">*</label></td>
      <td class="value" width="60%">
        <input type="text" id="qualifyingPercentage" maxlength="2" />
      </td>
    </tr>
    <tr>
      <td colspan="2" align="center">
        <input type="button" id="create" value='<spring:message code="common.create"/>' />
      </td>
    </tr>
  </table>
</c:if>

<c:if test="${examGrade != null}">
  <table class="formTable_Data">
    <tr>
        <td class="label" width="40%"><spring:message code="examGrade.name"/><label class="mandatory">*</label></td>
        <td class="value" width="60%">
            <input type="text" id="gradeName" maxlength="2" value="${examGrade.gradeName}" />
        </td>
    </tr>
    <tr>
        <td class="label" width="40%"><spring:message code="examGrade.qualifyingPercentage"/><label class="mandatory">*</label></td>
        <td class="value" width="60%">
            <input type="text" id="qualifyingPercentage" maxlength="2" value="${examGrade.qualifyingPercentage}" />
        </td>
    </tr>
    <tr>
      <td colspan="2" align="center">
        <input type="hidden" id="examGradeId" value="${examGrade.examGradeId}" />
        <input type="button" id="update" value='<spring:message code="common.update"/>' />
      </td>
    </tr>
</c:if>
</table>
