<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<script type="text/javascript">
$(document).ready(function() {
  $('#create').click(function() {
    $.ajax({
      type: "POST",
      url: "<%=request.getContextPath()%>/holiday/doCreate.htm",
      data: {
        holidayName: $('#holidayName').val(),
        startDate: $('#startDate').val(),
        endDate: $('#endDate').val()
      },
      context: this
    }).done(function(result) {
      handleServerResponseOnModal(result);
    });
  });

  $('#update').click(function() {
    $.ajax({
      type: "POST",
      url: "<%=request.getContextPath()%>/holiday/doUpdate.htm",
      data: {
        holidayId: $('#holidayId').val(),
        holidayName: $('#holidayName').val(),
        startDate: $('#startDate').val(),
        endDate: $('#endDate').val()
      },
      context: this
    }).done(function(result) {
      handleServerResponseOnModal(result);
    });
  });

  $(this).datePicker({
    rangeId1: 'startDate',
    rangeId2: 'endDate'
  });

  $('#startDate').change(function() { 
    var holidayType = $('#holidayType').val();
    if (holidayType == 'SingleDay') {
      $('#endDate').val($('#startDate').val());
    }
  });
});
</script>

<c:if test="${holiday == null}">
  <table class="formTable_Data">
    <tr>
      <td class="label" width="40%"><spring:message code="holiday.holidayName"/><label class="mandatory">*</label></td>
      <td class="value" width="60%">
        <input type="text" id="holidayName" maxlength="32" />
      </td>
    </tr>
    <tr>
      <td class="label" width="40%">&nbsp;</td>
      <td class="value" width="60%">
        <input type="radio" name="holidayType" id="holidayType" value="SingleDay" checked/> <spring:message code="holiday.single.day"/>
        <input type="radio" name="holidayType" id="holidayType" value="MoreThanOneDay" /> <spring:message code="holiday.more.days"/>
      </td>
    </tr>
    <tr>
      <td class="label" width="40%"><spring:message code="common.startDate"/><label class="mandatory">*</label></td>
      <td class="value" width="60%">
        <input type="text" id="startDate" class="datepicker" />
      </td>
    </tr>
    <tr>
      <td class="label" width="40%"><spring:message code="common.endDate"/><label class="mandatory">*</label></td>
      <td class="value" width="60%">
        <input type="text" id="endDate" class="datepicker" />
      </td>
    </tr>
    <tr>
      <td colspan="2" align="center">
        <input type="button" id="create" value='<spring:message code="common.create"/>' />
      </td>
    </tr>
  </table>
</c:if>

<c:if test="${holiday != null}">
  <table class="formTable_Data">
    <tr>
      <td class="label" width="40%"><spring:message code="holiday.holidayName"/><label class="mandatory">*</label></td>
      <td class="value" width="60%">
        <input type="text" id="holidayName" maxlength="32" value="${holiday.holidayName}" />
      </td>
    </tr>
    <tr>
      <td class="label" width="40%"><spring:message code="common.startDate"/><label class="mandatory">*</label></td>
      <td class="value" width="60%">
        <input type="text" id="startDate" class="datepicker" value="${holiday.startDate}" />
      </td>
    </tr>
    <tr>
      <td class="label" width="40%"><spring:message code="common.endDate"/><label class="mandatory">*</label></td>
      <td class="value" width="60%">
        <input type="text" id="endDate" class="datepicker" value="${holiday.endDate}" />
      </td>
    </tr>
    <tr>
      <td colspan="2" align="center">
          <input type="hidden" id="holidayId" value="${holiday.holidayId}" />
          <input type="button" id="update" value='<spring:message code="common.update"/>' />
      </td>
    </tr>
  </table>
</c:if>
