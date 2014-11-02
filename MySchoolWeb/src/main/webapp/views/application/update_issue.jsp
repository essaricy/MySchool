<%@page import="com.myschool.application.constants.IssueStatus"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="myschool" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="func" uri="http://www.allstudents.in/dev/functions" %>

<script language="javascript" type="text/javascript">
$(document).ready( function () {
  $('#IssueStatusId').chosen({width: "95%"});

  $('#Update').click(function() {
    var IssueData = new Object();
    IssueData.UserTypeId=$('#UserTypeId').val();
    IssueData.ContactEmailId=$('#ContactEmailId').val();
    IssueData.IssueSubject=$('#IssueSubject').val();
    IssueData.Description=$('#Description').val();
    IssueData.IssueStatusId=$('#IssueStatusId').val();

    jQuery.ajax({
      type: "POST",
      url: "<%=request.getContextPath()%>/issue/doUpdate.htm",
      data: {
        issueId: $('#IssueId').val(),
        IssueData: JSON.stringify(IssueData)
      },
      context: this
    }).done(function(result) {
      parseModelReponse(result);
    }); 
  });
});

</script>

<c:set var="issueStatusOptions" value="<%=IssueStatus.values()%>"/>
<c:if test="${Issue != null}">
<table width="80%" class="userFormTable" align="center" border="0" cellspacing="10" cellpadding="5">
  <input type="hidden" id="IssueId" value="${Issue.issueId}" />
  <tr>
    <td width="40%" class="label">Issue Created By</td>
    <td width="60%" class="value">
      <input type="hidden" id="UserTypeId" value="${Issue.userType.userTypeValue}" />
      <b>${Issue.userType}</b>
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">Issue Status</td>
    <td width="60%" class="value">
      <input type="hidden" id="IssueStatus" value="${Issue.issueStatus}" />
      <select id="IssueStatusId">
        <c:forEach var="issueStatusOption" items="${issueStatusOptions}">
          <c:if test="${Issue.issueStatus == issueStatusOption}">
          <option value="${issueStatusOption.statusId}" selected>${issueStatusOption}</option>
          </c:if>
          <c:if test="${Issue.issueStatus != issueStatusOption}">
          <option value="${issueStatusOption}">${issueStatusOption}</option>
          </c:if>
        </c:forEach>
      </select>
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">Reported Date</td>
    <td width="60%" class="value">
      <input type="hidden" id="ReportedDate" value="${Issue.reportedDate}" />
      <b>${Issue.reportedDate}</b>
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">Closed Date</td>
    <td width="60%" class="value">
      <input type="hidden" id="ClosedDate" value="${Issue.closedDate}" />
      <b>${Issue.closedDate}</b>
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">Subject</td>
    <td width="60%" class="value">
      <input type="hidden" id="IssueSubject" value="${Issue.issueSubject}" />
      <b>${Issue.issueSubject}</b>
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">Email Address</td>
    <td width="60%" class="value">
      <input type="hidden" id="ContactEmailId" value="${Issue.contactEmailId}" />
      <b>${Issue.contactEmailId}</b>
    </td>
  </tr>
  <tr>
    <td width="40%" class="label">Description</td>
    <td width="60%" class="value">
      <input type="hidden" id="Description" value="" />
      <textarea disabled>${Issue.description}</textarea>
    </td>
  </tr>
  <tr>
    <td colspan="2" align="center">
      <input type="button" id="Update" value="Update" class="active" />
    </td>
  </tr>
</table>
</c:if>
