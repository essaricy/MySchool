<%@page import="com.myschool.common.constants.DocumentApplicability"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<script type="text/javascript">
$(document).ready(function() {
  $('#create').click(function() {
    $.ajax({
      type: "POST",
      url: "<%=request.getContextPath()%>/document/doCreate.htm",
      data: {
        documentName: $('#documentName').val(),
        description: $('#description').val(),
        applicabilityForEmployee: $('#applicabilityForEmployee').val(),
        applicabilityForStudent: $('#applicabilityForStudent').val(),
        sid: new Date().getTime()
      },
      context: this
    }).done(function(result) {
      handleServerResponseOnModal(result);
    });
  });

  $('#update').click(function() {
    $.ajax({
      type: "POST",
      url: "<%=request.getContextPath()%>/document/doUpdate.htm",
      data: {
          documentId: $('#documentId').val(),
          documentName: $('#documentName').val(),
          description: $('#description').val(),
          applicabilityForEmployee: $('#applicabilityForEmployee').val(),
          applicabilityForStudent: $('#applicabilityForStudent').val(),
          sid: new Date().getTime()
      },
      context: this
    }).done(function(result) {
      handleServerResponseOnModal(result);
    });
  });
  $('#applicabilityForEmployee').chosen({"width": "93%"});
  $('#applicabilityForStudent').chosen({"width": "93%"});
});
</script>

<c:set var="documentApplicabilityOptions" value="<%=DocumentApplicability.values()%>"/>

<c:if test="${document == null}">
<table class="formTable_Data">
  <tr>
    <td width="40%" class="label">Document Name<label class="mandatory">*</label></td>
    <td width="60%" class="value">
      <input type="text" id="documentName" maxlength="64"/>
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">Description<label class="mandatory">*</label></td>
    <td width="60%" class="value">
      <input type="text" id="description" maxlength="512"/>
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">Applicability For Employee<label class="mandatory">*</label></td>
    <td width="60%" class="value">
      <select id="applicabilityForEmployee">
        <c:forEach var="documentApplicabilityOption" items="${documentApplicabilityOptions}">
        <option value="${documentApplicabilityOption.applicabilityCode}">${documentApplicabilityOption}</option>
        </c:forEach>
      </select>
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">Applicability For Student<label class="mandatory">*</label></td>
    <td width="60%" class="value">
      <select id="applicabilityForStudent">
        <c:forEach var="documentApplicabilityOption" items="${documentApplicabilityOptions}">
        <option value="${documentApplicabilityOption.applicabilityCode}">${documentApplicabilityOption}</option>
        </c:forEach>
      </select>
    </td>
  </tr>
  <tr>
    <td colspan="2" align="center">
        <input type="button" id="create" value='<spring:message code="common.create"/>' />
    </td>
  </tr>
</table>
</c:if>

<c:if test="${document != null}">
<table class="formTable_Data">
  <tr>
    <td width="40%" class="label">Document Name<label class="mandatory">*</label></td>
    <td width="60%" class="value">
      <input type="text" id="documentName" maxlength="64" value="${document.name}" />
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">Description<label class="mandatory">*</label></td>
    <td width="60%" class="value">
      <input type="text" id="description" maxlength="512"value="${document.description}" />
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">Applicability For Employee<label class="mandatory">*</label></td>
    <td width="60%" class="value">
      <select id="applicabilityForEmployee">
        <c:forEach var="documentApplicabilityOption" items="${documentApplicabilityOptions}">
          <c:if test="${documentApplicabilityOption == document.applicabilityForEmployee}">
            <option value="${documentApplicabilityOption.applicabilityCode}" selected>${documentApplicabilityOption}</option>
          </c:if>
          <c:if test="${documentApplicabilityOption != document.applicabilityForEmployee}">
            <option value="${documentApplicabilityOption.applicabilityCode}">${documentApplicabilityOption}</option>
          </c:if>
        </c:forEach>
      </select>
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">Applicability For Student<label class="mandatory">*</label></td>
    <td width="60%" class="value">
      <select id="applicabilityForStudent">
        <c:forEach var="documentApplicabilityOption" items="${documentApplicabilityOptions}">
          <c:if test="${documentApplicabilityOption == document.applicabilityForStudent}">
            <option value="${documentApplicabilityOption.applicabilityCode}" selected>${documentApplicabilityOption}</option>
          </c:if>
          <c:if test="${documentApplicabilityOption != document.applicabilityForStudent}">
            <option value="${documentApplicabilityOption.applicabilityCode}">${documentApplicabilityOption}</option>
          </c:if>
        </c:forEach>
      </select>
    </td>
  </tr>
  <tr>
    <td colspan="2" align="center">
        <input type="hidden" id="documentId" value="${document.documentId}" />
        <input type="button" id="update" value='<spring:message code="common.update"/>' />
    </td>
  </tr>
</table>
</c:if>
