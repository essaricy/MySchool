<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<script type="text/javascript">
$(document).ready(function() {
  $('#create').click(function() {
    $.ajax({
      type: "POST",
      url: "<%=request.getContextPath()%>/registeredSubject/doCreate.htm",
      data: {
        RegisteredClassId: $('#RegisteredClassId').val(),
        SubjectId: $('#AvailableSubjects').val()
      },
      context: this
    }).done(function(result) {
      parseModelReponse(result);
    });
  });

  $('#update').click(function() {
    $.ajax({
      type: "POST",
      url: "<%=request.getContextPath()%>/registeredSubject/doUpdate.htm",
      data: {
        RegisteredClassId: $('#RegisteredClassId').val(),
        RegisteredSubjectId: $('#RegisteredSubjectId').val(),
        SubjectId: $('#AvailableSubjects').val()
      },
      context: this
    }).done(function(result) {
      parseModelReponse(result);
    });
  });
  $('.chosen-select').chosen({width: "80%"});
});
</script>

<c:if test="${registeredClass == null}">
<table width="80%" class="userFormTable" align="center" border="0" cellspacing="10" cellpadding="5">
  <tr>
    <td class="label" width="40%">Class</td>
    <td class="value" width="60%">
      <input type="hidden" id="RegisteredClassId" value="${registeredClass.classId}" />
      ${registeredClass.classDto.className}
    </td>
  </tr>
  <tr>
    <td class="label" width="40%">Subject<label class="mandatory">*</label></td>
    <td class="value" width="60%">
      <select class="chosen-select" id="AvailableSubjects">
      <c:if test="${availableSubjects != null}">
      <c:forEach var="availableSubject" items="${availableSubjects}">
        <option value="${availableSubject.subjectId}">${availableSubject.subjectName}</option>
      </c:forEach>
      </c:if>
      </select>
    </td>
  </tr>
  <tr>
    <td colspan="2" align="center">
        <input type="button" id="create" class="active" value='<spring:message code="common.create"/>' />
    </td>
  </tr>
</table>
</c:if>

<c:if test="${registeredClass != null}">
<table width="80%" class="userFormTable" align="center" border="0" cellspacing="10" cellpadding="5">
  <tr>
    <td class="label" width="40%">Class</td>
    <td class="value" width="60%">
      <input type="hidden" id="RegisteredClassId" value="${registeredClass.classId}" />
      ${registeredClass.classDto.className}
    </td>
  </tr>
  <tr>
    <td class="label" width="40%">Subject<label class="mandatory">*</label></td>
    <td class="value" width="60%">
      <select class="chosen-select" id="AvailableSubjects">
      <c:if test="${availableSubjects != null}">
      <c:forEach var="availableSubject" items="${availableSubjects}">
      <c:if test="${registeredSubject.subject.subjectId == availableSubject.subjectId}">
        <option value="${availableSubject.subjectId}" selected>${availableSubject.subjectName}</option>
      </c:if>
      <c:if test="${registeredSubject.subject.subjectId != availableSubject.subjectId}">
        <option value="${availableSubject.subjectId}">${availableSubject.subjectName}</option>
      </c:if>
      </c:forEach>
      </c:if>
      </select>
    </td>
  </tr>
  <tr>
    <td colspan="2" align="center">
        <input type="hidden" id="RegisteredSubjectId" value="${registeredSubject.subjectId}" />
        <input type="button" id="update" class="active" value='<spring:message code="common.update"/>' />
    </td>
  </tr>
</table>
</c:if>
