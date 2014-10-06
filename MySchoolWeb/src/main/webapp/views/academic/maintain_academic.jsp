<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<script type="text/javascript">
$(document).ready(function() {
  $('#create').click(function() {
    $.ajax({
      type: "POST",
      url: "<%=request.getContextPath()%>/academic/doCreate.htm",
      data: {
        academicYearName: $('#academicYearName').val(),
        academicYearStartDate: $('#academicYearStartDate').val(),
        academicYearEndDate: $('#academicYearEndDate').val()
      },
      context: this
    }).done(function(result) {
      parseModelReponse(result);
    });
  });

  $('#update').click(function() {
    $.ajax({
      type: "POST",
      url: "<%=request.getContextPath()%>/academic/doUpdate.htm",
      data: {
        academicYearName: $('#academicYearName').val(),
        academicYearStartDate: $('#academicYearStartDate').val(),
        academicYearEndDate: $('#academicYearEndDate').val()
      },
      context: this
    }).done(function(result) {
      parseModelReponse(result);
    });
  });

  $(this).datePicker({
    rangeId1: 'academicYearStartDate',
    rangeId2: 'academicYearEndDate'
  });
});
</script>

<c:if test="${academic == null}">
<table width="80%" class="userFormTable" align="center" border="0" cellspacing="10" cellpadding="5">
  <tr>
    <td class="label" width="40%"><spring:message code="academic.yearName"/><label class="mandatory">*</label></td>
    <td class="value" width="60%">
      <input type="text" id="academicYearName" maxlength="16" />
    </td>
  </tr>
  <tr>
    <td class="label" width="40%"><spring:message code="common.startDate"/><label class="mandatory">*</label></td>
    <td class="value" width="60%">
      <input type="text" id="academicYearStartDate" class="datepicker" /></td>
    </td>
  </tr>
  <tr>
    <td class="label" width="40%"><spring:message code="common.endDate"/><label class="mandatory">*</label></td>
    <td class="value" width="60%">
      <input type="text" id="academicYearEndDate" class="datepicker" /></td>
    </td>
  </tr>
  <tr>
    <td colspan="2" align="center">
        <input type="button" id="create" class="active" value='<spring:message code="common.create"/>' />
    </td>
  </tr>
</table>
</c:if>

<c:if test="${academic != null}">
<table width="80%" class="userFormTable" align="center" border="0" cellspacing="10" cellpadding="5">
  <tr>
    <td class="label" width="40%"><spring:message code="academic.yearName"/><label class="mandatory">*</label></td>
    <td class="value" width="60%">
      <input type="text" id="academicYearName" maxlength="16" value="${academic.academicYearName}"/>
    </td>
  </tr>
  <tr>
    <td class="label" width="40%"><spring:message code="common.startDate"/><label class="mandatory">*</label></td>
    <td class="value" width="60%">
      <input type="text" id="academicYearStartDate" class="datepicker" value="${academic.academicYearStartDate}" /></td>
    </td>
  </tr>
  <tr>
    <td class="label" width="40%"><spring:message code="common.endDate"/><label class="mandatory">*</label></td>
    <td class="value" width="60%">
      <input type="text" id="academicYearEndDate" class="datepicker" value="${academic.academicYearEndDate}" /></td>
    </td>
  </tr>
  <tr>
    <td colspan="2" align="center">
      <input type="button" id="update" class="active" value='<spring:message code="common.update"/>' />
    </td>
  </tr>
</table>
</c:if>
