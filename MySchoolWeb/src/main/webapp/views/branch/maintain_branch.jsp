<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<script type="text/javascript">
$(document).ready(function() {
  $(this).lazySelect({
    id: "regions",
    url: '<%=request.getContextPath()%>/region/jsonList.htm',
    selectOnCode: $('#regionId').val(),
    width: '75%'
  });

  $('#description').textcounter({id: 'description'});
  $('#address').textcounter({id: 'address'});

  $('#create').click(function() {
    $.ajax({
      type: "POST",
      url: "<%=request.getContextPath()%>/branch/doCreate.htm",
      data: {
        regionId: $('#regions').val(),
        branchCode: $('#branchCode').val(),
        description: $('#description').val(),
        address: $('#address').val(),
        phoneNumber: $('#phoneNumber').val(),
        emailId: $('#emailId').val()
      },
      context: this
    }).done(function(result) {
      handleServerResponseOnModal(result);
    });
  });

  $('#update').click(function() {
    $.ajax({
      type: "POST",
      url: "<%=request.getContextPath()%>/branch/doUpdate.htm",
      data: {
        branchId: $('#branchId').val(),
        regionId: $('#regions').val(),
        branchCode: $('#branchCode').val(),
        description: $('#description').val(),
        address: $('#address').val(),
        phoneNumber: $('#phoneNumber').val(),
        emailId: $('#emailId').val()
      },
      context: this
    }).done(function(result) {
      handleServerResponseOnModal(result);
    });
  });
});

</script>

<c:if test="${branch == null}">
<table class="formTable_Data">
  <tr>
    <td width="40%" class="label"><spring:message code="common.region"/><label class="mandatory">*</label></td>
    <td width="60%" class="value">
      <select id="regions" class="chosen-select">
      </select>
    </td>
  </tr>
  <tr>
    <td width="40%" class="label"><spring:message code="branch.branchCode"/><label class="mandatory">*</label></td>
    <td width="60%" class="value">
      <input type="text" id="branchCode" maxlength="8"/>
    </td>
  </tr>
  <tr>
    <td width="40%" class="label"><spring:message code="common.description"/><label class="mandatory">*</label></td>
    <td width="60%" class="value">
      <textArea id="description" maxlength="32"></textArea>
    </td>
  </tr>
  <tr>
    <td width="40%" class="label"><spring:message code="common.address"/><label class="mandatory">*</label></td>
    <td width="60%" class="value">
      <textArea id="address" maxlength="128"></textArea>
    </td>
  </tr>
  <tr>
    <td width="40%" class="label"><spring:message code="common.phoneNumber"/><label class="mandatory">*</label></td>
    <td width="60%" class="value">
      <input type="text" id="phoneNumber" maxlength="16"/>
    </td>
  </tr>
  <tr>
    <td width="40%" class="label"><spring:message code="common.email"/></td>
    <td width="60%" class="value">
      <input type="text" id="emailId" maxlength="32"/>
    </td>
  </tr>
  <tr>
    <td colspan="2" align="center">
        <input type="button" id="create" value='<spring:message code="common.create"/>' />
    </td>
  </tr>
</table>
</c:if>

<c:if test="${branch != null}">
<table class="formTable_Data">
  <tr>
    <td width="40%" class="label"><spring:message code="common.region"/><label class="mandatory">*</label></td>
    <td width="60%" class="value">
      <input type="hidden" id="regionId" value="${branch.region.regionId}" />
      <select id="regions" class="chosen-select">
      </select>
    </td>
  </tr>
  <tr>
    <td width="40%" class="label"><spring:message code="branch.branchCode"/><label class="mandatory">*</label></td>
    <td width="60%" class="value">
      <input type="text" id="branchCode" maxlength="8" value="${branch.branchCode}"/>
    </td>
  </tr>
  <tr>
    <td width="40%" class="label"><spring:message code="common.description"/><label class="mandatory">*</label></td>
    <td width="60%" class="value">
      <textArea id="description" maxlength="32">${branch.description}</textArea>
    </td>
  </tr>
  <tr>
    <td width="40%" class="label"><spring:message code="common.address"/><label class="mandatory">*</label></td>
    <td width="60%" class="value">
      <textArea id="address" maxlength="128">${branch.address}</textArea>
    </td>
  </tr>
  <tr>
    <td width="40%" class="label"><spring:message code="common.phoneNumber"/><label class="mandatory">*</label></td>
    <td width="60%" class="value">
      <input type="text" id="phoneNumber" maxlength="16" value="${branch.phoneNumber}"/>
    </td>
  </tr>
  <tr>
    <td width="40%" class="label"><spring:message code="common.email"/></td>
    <td width="60%" class="value">
      <input type="text" id="emailId" maxlength="32" value="${branch.emailId}"/>
    </td>
  </tr>
  <tr>
    <td colspan="2" align="center">
        <input type="hidden" id="branchId" value="${branch.branchId}" />
        <input type="button" id="update" value='<spring:message code="common.update"/>' />
    </td>
  </tr>
</table>
</c:if>
