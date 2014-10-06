<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="myschool" tagdir="/WEB-INF/tags" %>

<style>
.ui-tabs .ui-tabs-nav li a {font-size: 0.6em !important;}
</style>

<script type="text/javascript" language="javascript" src="<%=request.getContextPath()%>/widgets/jquery-ui-1.10.2/ui/jquery.ui.tabs.js"></script>
<%-- <script type="text/javascript" language="javascript" src="<%=request.getContextPath()%>/widgets/jquery.blockUI/jquery.blockUI.js"></script> --%>
<script type="text/javascript" charset="utf-8">
$(document).ready(function() {
  $("#SearchTabs").tabs();

  $(this).lazySelect({id: "Search_Designation", url: '<%=request.getContextPath()%>/designation/jsonList.htm', prefixCode: true});
  $(this).lazySelect({id: "AdvancedSearch_Designation", url: '<%=request.getContextPath()%>/designation/jsonList.htm', prefixCode: true});

  $(this).lazySelect({id: "Search_ReportingTo", url: '<%=request.getContextPath()%>/employee/verifiedEmployeesJSONList.htm', prefixCode: true, codeIndex: 1, valueIndices: [2, 3, 4] });
  $(this).lazySelect({id: "AdvancedSearch_ReportingTo", url: '<%=request.getContextPath()%>/employee/verifiedEmployeesJSONList.htm', prefixCode: true, codeIndex: 1, valueIndices: [2, 3, 4] });

  $(this).lazySelect({id: "Search_EmployedAt", url: '<%=request.getContextPath()%>/branch/jsonList.htm', valueIndices: [2]});
  $(this).lazySelect({id: "AdvancedSearch_EmployedAt", url: '<%=request.getContextPath()%>/branch/jsonList.htm', valueIndices: [2]});

  $(this).lazySelect({id: "Search_EmploymentStatus", url: '<%=request.getContextPath()%>/employment/jsonList.htm'});
  $(this).lazySelect({id: "AdvancedSearch_EmploymentStatus", url: '<%=request.getContextPath()%>/employment/jsonList.htm'});

  $(this).datePicker({rangeId1: 'AdvancedSearch_DateOfBirthMin', rangeId2: 'AdvancedSearch_DateOfBirthMax'});
  $(this).datePicker({rangeId1: 'AdvancedSearch_EmploymentStartDateMin', rangeId2: 'AdvancedSearch_EmploymentStartDateMax'});

  $('#AdvancedSearch_Gender').chosen({width: "100%"});
  $('#AdvancedSearch_BloodGroup').chosen({width: "100%"});
  $('#AdvancedSearch_Experience_Min').chosen({width: "20%"});
  $('#AdvancedSearch_Experience_Max').chosen({width: "20%"});

  var destinationUrl = null;
  if ($('#SEARCH_MODE').val() == 'VERIFIED') {
      destinationUrl = '<%=request.getContextPath()%>/employee/searchVerifiedEmployees.htm';
  } else if ($('#SEARCH_MODE').val() == 'UNVERIFIED') {
      destinationUrl = '<%=request.getContextPath()%>/employee/searchUnverifiedEmployees.htm';
  }

  $('#Search').click(function() {
    var EmployeeSearchCriteria = new Object();
    EmployeeSearchCriteria.SearchType='Regular';
    EmployeeSearchCriteria.EmployeeNumber=$('#Search_EmployeeNumber').val();
    EmployeeSearchCriteria.EmployeeName=$('#Search_EmployeeName').val();
    EmployeeSearchCriteria.Designation=$('#Search_Designation').val();
    EmployeeSearchCriteria.ReportingTo=$('#Search_ReportingTo').val();
    EmployeeSearchCriteria.EmployedAt=$('#Search_EmployedAt').val();
    EmployeeSearchCriteria.EmploymentStatus=$('#Search_EmploymentStatus').val();
    EmployeeSearchCriteria.Verified=$('#Search_EmploymentStatus').val();
    var searchString = 'EmployeeSearchCriteria=' + JSON.stringify(EmployeeSearchCriteria) + '&sid=' + new Date().getTime();

    reloadEmployeesTable(destinationUrl + '?' + searchString);
  });
  $('#AdvancedSearch').click(function() {
    var EmployeeSearchCriteria = new Object();
    EmployeeSearchCriteria.SearchType='Advanced';
    EmployeeSearchCriteria.EmployeeNumber=$('#AdvancedSearch_EmployeeNumber').val();
    EmployeeSearchCriteria.EmployeeName=$('#AdvancedSearch_EmployeeName').val();
    EmployeeSearchCriteria.Designation=$('#AdvancedSearch_Designation').val();
    EmployeeSearchCriteria.ReportingTo=$('#AdvancedSearch_ReportingTo').val();
    EmployeeSearchCriteria.EmployedAt=$('#AdvancedSearch_EmployedAt').val();
    EmployeeSearchCriteria.EmploymentStatus=$('#AdvancedSearch_EmploymentStatus').val();
    EmployeeSearchCriteria.Gender=$('#AdvancedSearch_Gender').val();
    EmployeeSearchCriteria.BloodGroup=$('#AdvancedSearch_BloodGroup').val();
    EmployeeSearchCriteria.ExperienceMin=$('#AdvancedSearch_Experience_Min').val();
    EmployeeSearchCriteria.ExperienceMax=$('#AdvancedSearch_Experience_Max').val();
    EmployeeSearchCriteria.DateOfBirthMin=$('#AdvancedSearch_DateOfBirthMin').val();
    EmployeeSearchCriteria.DateOfBirthMax=$('#AdvancedSearch_DateOfBirthMax').val();
    EmployeeSearchCriteria.EmploymentStartDateMin=$('#AdvancedSearch_EmploymentStartDateMin').val();
    EmployeeSearchCriteria.EmploymentStartDateMax=$('#AdvancedSearch_EmploymentStartDateMax').val();

    var searchString = 'EmployeeSearchCriteria=' + JSON.stringify(EmployeeSearchCriteria) + '&sid=' + new Date().getTime();
    reloadEmployeesTable(destinationUrl + '?' + searchString);
  });
});

</script>

<input type="hidden" id="SEARCH_MODE" name="SEARCH_MODE" value="${SEARCH_MODE}" />
<table width="80%" class="userFormTable" align="center" border="0" cellspacing="10" cellpadding="5">
  <caption class="dataTableCaption">${TITLE}</caption>
  <tr>
    <td>
      <div id="SearchTabs">
        <ul>
          <li><a href="#SearchCriteria">Search</a></li>
          <li><a href="#AdvancedSearchCriteria">Advanced Search</a></li>
        </ul>
        <div id="SearchCriteria">
          <table class="formDataTable" align="center" border="0" cellspacing="10" cellpadding="5" width="100%">
            <tr>
              <td align="right"><spring:message code="employee.number"/></td>
              <td>
                <input type="text" id="Search_EmployeeNumber" class="formInputText" />
              </td>
              <td align="right"><spring:message code="employee.name"/></td>
              <td>
                <input type="text" id="Search_EmployeeName" class="formInputText" />
              </td>
            </tr>
            <tr>
              <td align="right">Designation</td>
              <td>
                <select id="Search_Designation" class="chosen-select">
                  <option value="">Any</option>
                </select>
              </td>
              <td align="right">Reporting To</td>
              <td>
                <select id="Search_ReportingTo" class="chosen-select">
                  <option value="">Any</option>
                </select>
              </td>
            </tr>
            <tr>
              <td align="right">Employed At</td>
              <td>
                <select id="Search_EmployedAt" class="chosen-select">
                  <option value="">Any</option>
                </select>
              </td>
              <td align="right">Employment Status</td>
              <td>
                <select id="Search_EmploymentStatus" class="chosen-select">
                  <option value="">Any</option>
                </select>
              </td>
            </tr>
            <tr>
              <td align="right" colspan="4">
                <input type="button" id="Search" value="Search" class="formButton" />
              </td>
            </tr>
          </table>
        </div>
        <div id="AdvancedSearchCriteria">
          <table width="100%" class="formDataTable" align="center" border="0" cellspacing="10" cellpadding="5">
            <tr>
              <td align="right"><spring:message code="employee.number"/></td>
              <td>
                <input type="text" id="AdvancedSearch_EmployeeNumber" class="formInputText" />
              </td>
              <td align="right"><spring:message code="employee.name"/></td>
              <td>
                <input type="text" id="AdvancedSearch_EmployeeName" class="formInputText" />
              </td>
            </tr>
            <tr>
              <td align="right">Designation</td>
              <td>
                <select id="AdvancedSearch_Designation" class="chosen-select">
                  <option value="">Any</option>
                </select>
              </td>
              <td align="right">Reporting To</td>
              <td>
                <select id="AdvancedSearch_ReportingTo" class="chosen-select">
                  <option value="">Any</option>
                </select>
              </td>
            </tr>
            <tr>
              <td align="right">Employed At</td>
              <td>
                <select id="AdvancedSearch_EmployedAt" class="chosen-select">
                  <option value="">Any</option>
                </select>
              </td>
              <td align="right">Employment Status</td>
              <td>
                <select id="AdvancedSearch_EmploymentStatus" class="chosen-select">
                  <option value="">Any</option>
                </select>
              </td>
            </tr>
            <tr>
              <td align="right">Gender</td>
              <td>
                <myschool:gender id="AdvancedSearch_Gender" clazz="chosen-select" uiControl="select" showFirstSelectOption="true" firstSelectOptionValue="" firstSelectOptionText="Any" />
              </td>
              <td align="right">Blood Group</td>
              <td>
                <myschool:bloodgroup id="AdvancedSearch_BloodGroup" clazz="chosen-select" uiControl="select" showFirstSelectOption="true" firstSelectOptionValue="" firstSelectOptionText="Any" />
              </td>
            </tr>
            <tr>
              <td align="center">Experience In Months</td>
              <td align="left" colspan="3">Between
                <myschool:numbers-between id="AdvancedSearch_Experience" clazz="chosen-select" showFirstSelectOption="true" firstSelectOptionValue="" firstSelectOptionText="Any" bridgeText="and" />
              </td>
            </tr>
            <tr>
              <td align="right">Date Of Birth</td>
              <td align="left" colspan="3">Between
                <input type="text" id="AdvancedSearch_DateOfBirthMin" class="datepicker" style="width: 100px;" />
                and
                <input type="text" id="AdvancedSearch_DateOfBirthMax" class="datepicker" style="width: 100px;" />
              </td>
            </tr>
            <tr>
              <td align="right">Employment Start Date</td>
              <td align="left" colspan="3">Between
                <input type="text" id="AdvancedSearch_EmploymentStartDateMin" class="datepicker" style="width: 100px;" />
                and
                <input type="text" id="AdvancedSearch_EmploymentStartDateMax" class="datepicker" style="width: 100px;" />
              </td>
            </tr>
            <tr>
              <td align="right" colspan="4">
                <input type="button" id="AdvancedSearch" value="Search" class="formButton" />
              </td>
            </tr>
          </table>
        </div>
      </div>
    </td>
  </tr>
</table>
