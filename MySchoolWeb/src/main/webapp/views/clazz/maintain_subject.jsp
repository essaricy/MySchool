<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<script type="text/javascript">
$(document).ready(function() {
  $('#create').click(function() {
    $.ajax({
      type: "POST",
      url: "<%=request.getContextPath()%>/subject/doCreate.htm",
      data: {
        subjectName: $('#subjectName').val()
      },
      context: this
    }).done(function(result) {
      parseModelReponse(result);
    });
  });

  $('#update').click(function() {
    $.ajax({
      type: "POST",
      url: "<%=request.getContextPath()%>/subject/doUpdate.htm",
      data: {
        subjectId: $('#subjectId').val(),
        subjectName: $('#subjectName').val()
      },
      context: this
    }).done(function(result) {
      parseModelReponse(result);
    });
  });

  $('#subjectName').textcounter({
    id: 'subjectName'
  });
});
</script>

<c:if test="${subject == null}">
  <table width="80%" class="userFormTable" align="center" border="0" cellspacing="10" cellpadding="5">
    <tr>
      <td class="label" width="40%"><spring:message code="subject.name"/><label class="mandatory">*</label></td>
      <td class="value" width="60%">
        <input type="text" id="subjectName" maxlength="24" />
      </td>
    </tr>
    <tr>
      <td colspan="2" align="center">
        <input type="button" id="create" class="active" value='<spring:message code="common.create"/>' />
      </td>
    </tr>
  </table>
</c:if>

<c:if test="${subject != null}">
  <table width="80%" class="userFormTable" align="center" border="0" cellspacing="10" cellpadding="5">
    <tr>
      <td class="label" width="40%"><spring:message code="subject.name"/><label class="mandatory">*</label></td>
      <td class="value" width="60%">
        <input type="text" id="subjectName" maxlength="24" value="${subject.subjectName}" />
      </td>
    </tr>
    <tr>
      <td colspan="2" align="center">
          <input type="hidden" id="subjectId" value="${subject.subjectId}" />
          <input type="button" id="update" class="active" value='<spring:message code="common.update"/>' />
      </td>
    </tr>
  </table>
</c:if>
