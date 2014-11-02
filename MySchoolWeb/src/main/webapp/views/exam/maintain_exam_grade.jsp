<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<script type="text/javascript">
$(document).ready(function() {
  $('#create').click(function() {
    $.ajax({
      type: "POST",
      url: "<%=request.getContextPath()%>/examGrade/doCreate.htm",
      dataType: 'xml',
      data: {
        gradeName: $('#gradeName').val(),
        qualifyingPercentage: $('#qualifyingPercentage').val()
      }, 
      context: this
    }).done(function(result) {
      parseModelReponse(result);
    });
  });

  $('#update').click(function() {
    $.ajax({
      type: "POST",
      url: "<%=request.getContextPath()%>/examGrade/doUpdate.htm",
      dataType: 'xml',
      data: {
         examGradeId: $('#examGradeId').val(),
         gradeName: $('#gradeName').val(),
         qualifyingPercentage: $('#qualifyingPercentage').val()
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

<c:if test="${examGrade == null}">
  <table width="80%" class="userFormTable" align="center" border="0" cellspacing="10" cellpadding="5">
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
        <input type="button" id="create" class="active" value='<spring:message code="common.create"/>' />
      </td>
    </tr>
  </table>
</c:if>

<c:if test="${examGrade != null}">
  <table width="80%" class="userFormTable" align="center" border="0" cellspacing="10" cellpadding="5">
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
        <input type="button" id="update" class="active" value='<spring:message code="common.update"/>' />
      </td>
    </tr>
</c:if>
</table>