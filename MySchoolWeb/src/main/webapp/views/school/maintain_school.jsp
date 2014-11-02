<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<script type="text/javascript">
$(document).ready(function() {
  $(this).lazySelect({id: "branches", url: '<%=request.getContextPath()%>/branch/jsonList.htm', valueIndices: [2], selectOnCode: $('#branchId').val()});
  $(this).lazySelect({id: "divisions", url: '<%=request.getContextPath()%>/division/jsonList.htm', selectOnCode: $('#divisionId').val()});

  $('#address').textcounter({id: 'address'});
  $('#create').click(function() {
    $.ajax({
      type: "POST",
      url: "<%=request.getContextPath()%>/school/doCreate.htm",
      data: {
        branchId: $('#branches').val(),
        divisionId: $('#divisions').val(),
        schoolName: $('#schoolName').val(), 
        address: $('#address').val(),
        primaryPhoneNumber: $('#primaryPhoneNumber').val(),
        secondaryPhoneNumber: $('#secondaryPhoneNumber').val(),
        mobileNumber: $('#mobileNumber').val(),
        faxNumber: $('#faxNumber').val(),
        emailId: $('#emailId').val()
      },
      context: this
    }).done(function(result) {
      parseModelReponse(result);
    });
  });

  $('#update').click(function() {
    $.ajax({
      type: "POST",
      url: "<%=request.getContextPath()%>/school/doUpdate.htm",
      data: {
        schoolId: $('#schoolId').val(),
        branchId: $('#branches').val(),
        divisionId: $('#divisions').val(),
        schoolName: $('#schoolName').val(), 
        address: $('#address').val(),
        primaryPhoneNumber: $('#primaryPhoneNumber').val(),
        secondaryPhoneNumber: $('#secondaryPhoneNumber').val(),
        mobileNumber: $('#mobileNumber').val(),
        faxNumber: $('#faxNumber').val(),
        emailId: $('#emailId').val()
      },
      context: this
    }).done(function(result) {
      parseModelReponse(result);
    });
  });
});
</script>

<c:if test="${school == null}">
  <table width="80%" class="userFormTable" align="center" border="0" cellspacing="10" cellpadding="5">
    <tr>
      <td class="label" width="40%"><spring:message code="branch"/><label class="mandatory">*</label></td>
      <td class="value" width="60%">
        <input type="hidden" id="branchId" value="${school.branch.branchId}" />
        <select id="branches" name="branches" class="chosen-select">
        </select>
      </td>
    </tr>
    <tr>
      <td class="label" width="40%"><spring:message code="division"/><label class="mandatory">*</label></td>
      <td class="value" width="60%">
        <input type="hidden" id="divisionId" value="-1" />
        <select id="divisions" name="divisions" class="chosen-select">
        </select>
      </td>
    </tr>
    <tr>
      <td class="label" width="40%"><spring:message code="school.name"/><label class="mandatory">*</label></td>
      <td class="value" width="60%">
        <input type="text" id="schoolName" maxlength="80"/>
      </td>
    </tr>
    <tr>
      <td class="label" width="40%" valign="top">
        <spring:message code="common.address"/><label class="mandatory">*</label>
      </td>
      <td class="value" width="60%">
        <textArea id="address" maxlength="128" rows="5"></textArea>
      </td>
    </tr>
    <tr>
      <td class="label" width="40%"><spring:message code="common.primaryPhoneNumber"/><label class="mandatory">*</label></td>
      <td class="value" width="60%">
        <input type="text" id="primaryPhoneNumber" maxlength="16" />
      </td>
    </tr>
    <tr>
      <td class="label" width="40%"><spring:message code="common.secondaryPhoneNumber"/></td>
      <td class="value" width="60%">
        <input type="text" id="secondaryPhoneNumber" maxlength="16" />
      </td>
    </tr>
    <tr>
      <td class="label" width="40%"><spring:message code="common.mobileNumber"/><label class="mandatory">*</label></td>
      <td class="value" width="60%">
        <input type="text" id="mobileNumber" maxlength="16" />
      </td>
    </tr>
    <tr>
      <td class="label" width="40%"><spring:message code="common.faxNumber"/></td>
      <td class="value" width="60%">
        <input type="text" id="faxNumber" maxlength="16" />
      </td>
    </tr>
    <tr>
      <td class="label" width="40%"><spring:message code="common.email"/></td>
      <td class="value" width="60%">
        <input type="text" id="emailId" maxlength="32"/>
      </td>
    </tr>
    <tr>
      <td colspan="2" align="center">
        <input type="button" id="create" class="active" value='<spring:message code="common.create"/>' />
      </td>
    </tr>
  </table>
</c:if>

<c:if test="${school != null}">
  <table width="80%" class="userFormTable" align="center" border="0" cellspacing="10" cellpadding="5">
    <tr>
      <td class="label" width="40%"><spring:message code="branch"/><label class="mandatory">*</label></td>
      <td class="value" width="60%">
        <input type="hidden" id="branchId" value="${school.branch.branchId}" />
        <select id="branches" name="branches" class="chosen-select">
        </select>
      </td>
    </tr>
    <tr>
      <td class="label" width="40%"><spring:message code="division"/><label class="mandatory">*</label></td>
      <td class="value" width="60%">
        <input type="hidden" id="divisionId" value="${school.division.divisionId}" />
        <select id="divisions" name="divisions" class="chosen-select">
        </select>
      </td>
    </tr>
    <tr>
      <td class="label" width="40%"><spring:message code="school.name"/><label class="mandatory">*</label></td>
      <td class="value" width="60%">
        <input type="text" id="schoolName" maxlength="80" value="${school.schoolName}" />
      </td>
    </tr>
    <tr>
      <td class="label" width="40%" valign="top">
        <spring:message code="common.address"/><label class="mandatory">*</label>
      </td>
      <td class="value" width="60%">
        <textArea id="address" maxlength="128" rows="5">${school.address}</textArea>
      </td>
    </tr>
    <tr>
      <td class="label" width="40%"><spring:message code="common.primaryPhoneNumber"/><label class="mandatory">*</label></td>
      <td class="value" width="60%">
        <input type="text" id="primaryPhoneNumber" maxlength="16" value="${school.primaryPhoneNumber}" />
      </td>
    </tr>
    <tr>
      <td class="label" width="40%"><spring:message code="common.secondaryPhoneNumber"/></td>
      <td class="value" width="60%">
        <input type="text" id="secondaryPhoneNumber" maxlength="16" value="${school.secondaryPhoneNumber}" />
      </td>
    </tr>
    <tr>
      <td class="label" width="40%"><spring:message code="common.mobileNumber"/><label class="mandatory">*</label></td>
      <td class="value" width="60%">
        <input type="text" id="mobileNumber" maxlength="16" value="${school.mobileNumber}" />
      </td>
    </tr>
    <tr>
      <td class="label" width="40%"><spring:message code="common.faxNumber"/></td>
      <td class="value" width="60%">
        <input type="text" id="faxNumber" maxlength="16" value="${school.faxNumber}" />
      </td>
    </tr>
    <tr>
      <td class="label" width="40%"><spring:message code="common.email"/></td>
      <td class="value" width="60%">
        <input type="text" id="emailId" maxlength="32" value="${school.emailId}" />
      </td>
    </tr>
    <tr>
      <td colspan="2" align="center">
          <input type="hidden" id="schoolId" value="${school.schoolId}" />
          <input type="button" id="update" class="active" value='<spring:message code="common.update"/>' />
      </td>
    </tr>
  </table>
</c:if>
