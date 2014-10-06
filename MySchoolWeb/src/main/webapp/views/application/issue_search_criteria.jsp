<%@page import="com.myschool.user.constants.UserType"%>
<%@page import="com.myschool.application.constants.IssueStatus"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="myschool" tagdir="/WEB-INF/tags" %>

<style>
.ui-tabs .ui-tabs-nav li a {font-size: 0.6em !important;}
</style>

<script type="text/javascript" language="javascript" src="<%=request.getContextPath()%>/widgets/jquery-ui-1.10.2/ui/jquery.ui.tabs.js"></script>
<script type="text/javascript" charset="utf-8">
$(document).ready(function() {
  $("#SearchTabs").tabs();

  $('#Search_UserType').chosen({width: "100%"});
  $('#Search_Status').chosen({width: "100%"});

  $(this).datePicker({rangeId1: 'Search_ReportedDateMin', rangeId2: 'Search_ReportedDateMax', future: false});
  $(this).datePicker({rangeId1: 'Search_ClosedDateMin', rangeId2: 'Search_ClosedDateMax', future: false});

  var destinationUrl = '<%=request.getContextPath()%>/issue/searchIssues.htm';
  $('#Search').click(function() {
    var IssueSearchCriteria = new Object();
    IssueSearchCriteria.UserTypeID=$('#Search_UserType').val();
    IssueSearchCriteria.StatusID=$('#Search_Status').val();
    IssueSearchCriteria.ReportedDateMin=$('#Search_ReportedDateMin').val();
    IssueSearchCriteria.ReportedDateMax=$('#Search_ReportedDateMax').val();
    IssueSearchCriteria.ClosedDateMin=$('#Search_ClosedDateMin').val();
    IssueSearchCriteria.ClosedDateMax=$('#Search_ClosedDateMax').val();
    IssueSearchCriteria.Subject=$('#Search_Subject').val();
    IssueSearchCriteria.Description=$('#Search_Description').val();

    //alert(JSON.stringify(IssueSearchCriteria));
    var searchString = 'IssueSearchCriteria=' + JSON.stringify(IssueSearchCriteria) + '&sid=' + new Date().getTime();
    reloadIssuesTable(destinationUrl + '?' + searchString);
    //alert(destinationUrl + '?' + searchString);
  });
});

</script>

<c:set var="userTypeOptions" value="<%=UserType.values()%>"/>
<c:set var="issueStatusOptions" value="<%=IssueStatus.values()%>"/>
<table width="80%" class="userFormTable" align="center" border="0" cellspacing="10" cellpadding="5">
  <caption class="dataTableCaption">Search Issues</caption>
  <tr>
    <td>
      <div id="SearchTabs">
        <ul>
          <li><a href="#SearchCriteria">Search</a></li>
        </ul>
        <div id="SearchCriteria">
          <table class="formDataTable" align="center" border="0" cellspacing="10" cellpadding="5" width="100%">
            <tr>
              <td align="right">Created By</td>
              <td>
                <select id="Search_UserType">
                  <option value="0">Any</option>
                  <c:forEach var="userTypeOption" items="${userTypeOptions}">
                    <option value="${userTypeOption.userTypeValue}">${userTypeOption}</option>
                  </c:forEach>
                </select>
              </td>
              <td align="right">Status</td>
              <td>
                <select id="Search_Status">
                  <option value="0">Any</option>
                  <c:forEach var="issueStatusOption" items="${issueStatusOptions}">
                    <option value="${issueStatusOption.statusId}">${issueStatusOption}</option>
                  </c:forEach>
                </select>
              </td>
            </tr>
            <tr>
              <td align="right">Subject</td>
              <td>
                <input type="text" id="Search_Subject" class="formInputText" />
              </td>
              <td align="right">Description</td>
              <td>
                <input type="text" id="Search_Description" class="formInputText" />
              </td>
            </tr>
            <tr>
              <td align="right">Reported Date</td>
              <td align="left" colspan="3">Between
                <input type="text" id="Search_ReportedDateMin" class="datepicker" style="width: 100px;" class="formInputText" />
                and
                <input type="text" id="Search_ReportedDateMax" class="datepicker" style="width: 100px;" class="formInputText" />
              </td>
            </tr>
            <tr>
              <td align="right">Closed Date</td>
              <td align="left" colspan="3">Between
                <input type="text" id="Search_ClosedDateMin" class="datepicker" style="width: 100px;" class="formInputText" />
                and
                <input type="text" id="Search_ClosedDateMax" class="datepicker" style="width: 100px;" class="formInputText" />
              </td>
            </tr>
            <tr>
              <td align="right" colspan="4">
                <input type="button" id="Search" value="Search" class="formButton" />
              </td>
            </tr>
          </table>
        </div>
    </td>
  </tr>
</table>
