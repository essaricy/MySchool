<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<script type="text/javascript">
$(document).ready(function() {
  $('.chosen-select').chosen({width: "80%"});

  $('#create').click(function() {
    $.ajax({
      type: "POST",
      url: "<%=request.getContextPath()%>/class/doCreateRegistered.htm",
      data: {
        schoolId: $('#schoolId').val(),
        classId: $('#classes').val(),
        mediumId: $('#mediums').val(),
        sectionId: $('#sections').val(),
        sid: new Date().getTime()
      },
      context: this
    }).done(function(result) {
      parseModelReponse(result);
    });
  });

  $('#update').click(function() {
    $.ajax({
      type: "POST",
      url: "<%=request.getContextPath()%>/class/doUpdateRegistered.htm",
      data: {
        registeredClassId: $('#registeredClassId').val(),
        schoolId: $('#schoolId').val(),
        classId: $('#classes').val(),
        mediumId: $('#mediums').val(),
        sectionId: $('#sections').val(),
        sid: new Date().getTime()
      },
      context: this
    }).done(function(result) {
      parseModelReponse(result);
    });
  });
});
</script>

<c:if test="${registeredClass == null}">
<table width="80%" class="userFormTable" align="center" border="0" cellspacing="10" cellpadding="5">
  <c:if test="${school != null}">
    <input type="hidden" id="schoolId" name="schoolId" value="${school.schoolId}" />
  </c:if>
  <tr>
    <td width="40%" class="label"><spring:message code="class.name"/><label class="mandatory">*</label></td>
    <td width="60%" class="value">
      <select id="classes" class="chosen-select">
        <c:forEach var="clazz" items="${classes}">
          <option value="${clazz.classId}">${clazz.className}</option>
        </c:forEach>
      </select>
    </td>
  </tr>
  <tr>
    <td width="40%" class="label"><spring:message code="medium"/><label class="mandatory">*</label></td>
    <td width="60%" class="value">
      <select id="mediums" class="chosen-select">
        <c:forEach var="medium" items="${mediums}">
          <option value="${medium.mediumId}">${medium.description}</option>
        </c:forEach>
      </select>
    </td>
  </tr>
  <tr>
    <td width="40%" class="label"><spring:message code="section"/><label class="mandatory">*</label></td>
    <td width="60%" class="value">
      <select id="sections" class="chosen-select">
        <c:forEach var="section" items="${sections}">
          <option value="${section.sectionId}">${section.sectionName}</option>
        </c:forEach>
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
  <c:if test="${school != null}">
    <input type="hidden" id="schoolId" name="schoolId" value="${school.schoolId}" />
  </c:if>
  <tr>
    <td width="40%" class="label"><spring:message code="class.name"/><label class="mandatory">*</label></td>
    <td width="60%" class="value">
      <select id="classes" class="chosen-select">
        <c:forEach var="clazz" items="${classes}">
          <c:if test="${registeredClass.classDto.classId == clazz.classId}">
            <option value="${clazz.classId}" selected>${clazz.className}</option>
          </c:if>
          <c:if test="${registeredClass.classDto.classId != clazz.classId}">
            <option value="${clazz.classId}">${clazz.className}</option>
          </c:if>
        </c:forEach>
      </select>
    </td>
  </tr>
  <tr>
    <td width="40%" class="label"><spring:message code="medium"/><label class="mandatory">*</label></td>
    <td width="60%" class="value">
      <select id="mediums" class="chosen-select">
        <c:forEach var="medium" items="${mediums}">
          <c:if test="${registeredClass.medium.mediumId == medium.mediumId}">
            <option value="${medium.mediumId}" selected>${medium.description}</option>
          </c:if>
          <c:if test="${registeredClass.medium.mediumId != medium.mediumId}">
            <option value="${medium.mediumId}">${medium.description}</option>
          </c:if>
        </c:forEach>
      </select>
    </td>
  </tr>
  <tr>
    <td width="40%" class="label"><spring:message code="section"/><label class="mandatory">*</label></td>
    <td width="60%" class="value">
      <select id="sections" class="chosen-select">
        <c:forEach var="section" items="${sections}">
          <c:if test="${registeredClass.section.sectionId == section.sectionId}">
            <option value="${section.sectionId}" selected>${section.sectionName}</option>
          </c:if>
          <c:if test="${registeredClass.section.sectionId != section.sectionId}">
            <option value="${section.sectionId}">${section.sectionName}</option>
          </c:if>
        </c:forEach>
      </select>
    </td>
  </tr>
  <tr>
    <td colspan="2" align="center">
      <input type="hidden" id="registeredClassId" value="${registeredClass.classId}" />
      <input type="button" id="update" class="active" value='<spring:message code="common.update"/>' />
    </td>
  </tr>
</table>
</c:if>
