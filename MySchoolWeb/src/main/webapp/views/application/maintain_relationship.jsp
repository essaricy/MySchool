<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<script type="text/javascript">
$(document).ready(function() {
  $('#create').click(function() {
    $.ajax({
      type: "POST",
      url: "<%=request.getContextPath()%>/relationship/doCreate.htm",
      data: {
        code: $('#code').val(),
        name: $('#name').val(),
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
      url: "<%=request.getContextPath()%>/relationship/doUpdate.htm",
      data: {
    	  code: $('#code').val(),
          name: $('#name').val(),
          sid: new Date().getTime()
      },
      context: this
    }).done(function(result) {
      parseModelReponse(result);
    });
  });
});
</script>


<c:if test="${relationship == null}">
<table width="80%" class="userFormTable" align="center" border="0" cellspacing="10" cellpadding="5">
  <tr>
    <td width="40%" class="label">Relationship Code<label class="mandatory">*</label></td>
    <td width="60%" class="value">
      <input type="text" id="code" maxlength="1"/>
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">Relationship Name<label class="mandatory">*</label></td>
    <td width="60%" class="value">
      <input type="text" id="name" maxlength="32"/>
    </td>
  </tr>
  <tr>
    <td colspan="2" align="center">
        <input type="button" id="create" class="active" value='<spring:message code="common.create"/>' />
    </td>
  </tr>
</table>
</c:if>

<c:if test="${relationship != null}">
<table width="80%" class="userFormTable" align="center" border="0" cellspacing="10" cellpadding="5">
  <tr>
    <td width="40%" class="label">Relationship Code<label class="mandatory">*</label></td>
    <td width="60%" class="value">
      <input type="text" id="code" maxlength="1" value=${relationship.code} disabled />
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">Relationship Name<label class="mandatory">*</label></td>
    <td width="60%" class="value">
      <input type="text" id="name" maxlength="32" value="${relationship.name}" />
    </td>
  </tr>
  <tr>
    <td colspan="2" align="center">
        <input type="button" id="update" class="active" value='<spring:message code="common.update"/>' />
    </td>
  </tr>
</table>
</c:if>