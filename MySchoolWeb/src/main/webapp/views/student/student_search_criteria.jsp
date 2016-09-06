<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="myschool" tagdir="/WEB-INF/tags" %>

<style>
.ui-tabs .ui-tabs-nav li a {font-size: 0.6em !important;}
</style>

<script type="text/javascript" language="javascript" src="<%=request.getContextPath()%>/widgets/jquery-ui-1.10.2/ui/jquery.ui.tabs.js"></script>
<script type="text/javascript" charset="utf-8">
$(document).ready(function() {
  $("#SearchTabs").tabs();

  $(this).lazySelect({id: "Search_Branch", url: '<%=request.getContextPath()%>/branch/jsonList.htm', valueIndices: [2]});
  $(this).lazySelect({id: "Search_Division", url: '<%=request.getContextPath()%>/division/jsonList.htm', valueIndices: [1]});
  $(this).lazySelect({id: "Search_School", url: '<%=request.getContextPath()%>/school/jsonList.htm', valueIndices: [2], valueDelimiter: ', '});
  $(this).lazySelect({id: "Search_Class", url: '<%=request.getContextPath()%>/class/jsonList.htm'});
  $(this).lazySelect({id: "Search_Medium", url: '<%=request.getContextPath()%>/medium/jsonList.htm'});
  $(this).lazySelect({id: "Search_Section", url: '<%=request.getContextPath()%>/section/jsonList.htm'});
  $(this).lazySelect({id: "Search_Class", url: '<%=request.getContextPath()%>/class/jsonList.htm'});

  $(this).lazySelect({id: "AdvancedSearch_Branch", url: '<%=request.getContextPath()%>/branch/jsonList.htm', valueIndices: [2]});
  $(this).lazySelect({id: "AdvancedSearch_Division", url: '<%=request.getContextPath()%>/division/jsonList.htm', valueIndices: [1]});
  $(this).lazySelect({id: "AdvancedSearch_School", url: '<%=request.getContextPath()%>/school/jsonList.htm', valueIndices: [2], valueDelimiter: ', '});
  $(this).lazySelect({id: "AdvancedSearch_Class", url: '<%=request.getContextPath()%>/class/jsonList.htm'});
  $(this).lazySelect({id: "AdvancedSearch_Medium", url: '<%=request.getContextPath()%>/medium/jsonList.htm'});
  $(this).lazySelect({id: "AdvancedSearch_Section", url: '<%=request.getContextPath()%>/section/jsonList.htm'});
  $(this).lazySelect({id: "AdvancedSearch_Class", url: '<%=request.getContextPath()%>/class/jsonList.htm'});

  $('#Search_Branch').chosen({width: "100%"});
  $('#Search_Division').chosen({width: "100%"});
  $('#Search_School').chosen({width: "100%"});
  $('#Search_Class').chosen({width: "100%"});
  $('#Search_Medium').chosen({width: "100%"});
  $('#Search_Section').chosen({width: "100%"});

  $('#AdvancedSearch_Branch').chosen({width: "100%"});
  $('#AdvancedSearch_Division').chosen({width: "100%"});
  $('#AdvancedSearch_School').chosen({width: "100%"});
  $('#AdvancedSearch_Class').chosen({width: "100%"});
  $('#AdvancedSearch_Medium').chosen({width: "100%"});
  $('#AdvancedSearch_Section').chosen({width: "100%"});
  $('#AdvancedSearch_Gender').chosen({width: "100%"});
  $('#AdvancedSearch_BloodGroup').chosen({width: "100%"});
  $(this).datePicker({rangeId1: 'AdvancedSearch_DateOfBirthMin', rangeId2: 'AdvancedSearch_DateOfBirthMax'});
  $(this).datePicker({rangeId1: 'AdvancedSearch_DateOfJoiningMin', rangeId2: 'AdvancedSearch_DateOfJoiningMax'});

  var destinationUrl = null;
  if ($('#RECORD_STATUS').val() == 'VERIFIED') {
      destinationUrl = '<%=request.getContextPath()%>/student/searchVerifiedStudents.htm';
  } else if ($('#RECORD_STATUS').val() == 'UNVERIFIED') {
      destinationUrl = '<%=request.getContextPath()%>/student/searchUnverifiedStudents.htm';
  }

  $('#Search').click(function() {
    var StudentSearchCriteria = new Object();
    StudentSearchCriteria.SearchType='Regular';
    StudentSearchCriteria.AdmissionNumber=$('#Search_AdmissionNumber').val();
    StudentSearchCriteria.StudentName=$('#Search_StudentName').val();
    StudentSearchCriteria.Branch=$('#Search_Branch').val();
    StudentSearchCriteria.Division=$('#Search_Division').val();
    StudentSearchCriteria.School=$('#Search_School').val();
    StudentSearchCriteria.Class=$('#Search_Class').val();
    StudentSearchCriteria.Medium=$('#Search_Medium').val();
    StudentSearchCriteria.Section=$('#Search_Section').val();
    var searchString = 'StudentSearchCriteria=' + JSON.stringify(StudentSearchCriteria) + '&sid=' + new Date().getTime();
    reloadStudentsTable(destinationUrl + '?' + searchString);
    //alert(destinationUrl + '?' + searchString);
  });
  $('#AdvancedSearch').click(function() {
    var StudentSearchCriteria = new Object();
    StudentSearchCriteria.SearchType='Advanced';
    StudentSearchCriteria.AdmissionNumber=$('#AdvancedSearch_AdmissionNumber').val();
    StudentSearchCriteria.StudentName=$('#AdvancedSearch_StudentName').val();
    StudentSearchCriteria.Branch=$('#AdvancedSearch_Branch').val();
    StudentSearchCriteria.Division=$('#AdvancedSearch_Division').val();
    StudentSearchCriteria.School=$('#AdvancedSearch_School').val();
    StudentSearchCriteria.Class=$('#AdvancedSearch_Class').val();
    StudentSearchCriteria.Medium=$('#AdvancedSearch_Medium').val();
    StudentSearchCriteria.Section=$('#AdvancedSearch_Section').val();
    StudentSearchCriteria.Gender=$('#AdvancedSearch_Gender').val();
    StudentSearchCriteria.BloodGroup=$('#AdvancedSearch_BloodGroup').val();
    StudentSearchCriteria.DateOfBirthMin=$('#AdvancedSearch_DateOfBirthMin').val();
    StudentSearchCriteria.DateOfBirthMax=$('#AdvancedSearch_DateOfBirthMax').val();
    StudentSearchCriteria.DateOfJoiningMin=$('#AdvancedSearch_DateOfJoiningMin').val();
    StudentSearchCriteria.DateOfJoiningMax=$('#AdvancedSearch_DateOfJoiningMax').val();
    var searchString = 'StudentSearchCriteria=' + JSON.stringify(StudentSearchCriteria) + '&sid=' + new Date().getTime();
    reloadStudentsTable(destinationUrl + '?' + searchString);
    //alert(destinationUrl + '?' + searchString);
  });
});

</script>

<input type="hidden" id="RECORD_STATUS" name="RECORD_STATUS" value="${RECORD_STATUS}" />
<table class="formTable_Data">
  <caption>${TITLE}</caption>
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
              <td align="right">Admission Number</td>
              <td>
                <input type="text" id="Search_AdmissionNumber" />
              </td>
              <td align="right">Student Name</td>
              <td>
                <input type="text" id="Search_StudentName" />
              </td>
            </tr>
            <tr>
              <td align="right">Branch</td>
              <td>
                <select id="Search_Branch" class="chosen-select">
                  <option value="">Any</option>
                </select>
              </td>
              <td align="right">Division</td>
              <td>
                <select id="Search_Division" class="chosen-select">
                  <option value="">Any</option>
                </select>
              </td>
            </tr>
            <tr>
              <td align="right">School</td>
              <td>
                <select id="Search_School" class="chosen-select">
                  <option value="">Any</option>
                </select>
              </td>
              <td align="right">Class</td>
              <td>
                <select id="Search_Class" class="chosen-select">
                  <option value="">Any</option>
                </select>
              </td>
            </tr>
            <tr>
              <td align="right">Medium</td>
              <td>
                <select id="Search_Medium" class="chosen-select">
                  <option value="">Any</option>
                </select>
              </td>
              <td align="right">Section</td>
              <td>
                <select id="Search_Section" class="chosen-select">
                  <option value="">Any</option>
                </select>
              </td>
            </tr>
            <tr>
              <td align="right" colspan="4">
                <input type="button" id="Search" value="Search" />
              </td>
            </tr>
          </table>
        </div>
        <div id="AdvancedSearchCriteria">
          <table width="100%" class="formDataTable" align="center" border="0" cellspacing="10" cellpadding="5">
            <tr>
              <td align="right">Admission Number</td>
              <td>
                <input type="text" id="AdvancedSearch_AdmissionNumber" />
              </td>
              <td align="right">Student Name</td>
              <td>
                <input type="text" id="AdvancedSearch_StudentName" />
              </td>
            </tr>
            <tr>
              <td align="right">Branch</td>
              <td>
                <select id="AdvancedSearch_Branch" class="chosen-select">
                  <option value="">Any</option>
                </select>
              </td>
              <td align="right">Division</td>
              <td>
                <select id="AdvancedSearch_Division" class="chosen-select">
                  <option value="">Any</option>
                </select>
              </td>
            </tr>
            <tr>
              <td align="right">School</td>
              <td>
                <select id="AdvancedSearch_School" class="chosen-select">
                  <option value="">Any</option>
                </select>
              </td>
              <td align="right">Class</td>
              <td>
                <select id="AdvancedSearch_Class" class="chosen-select">
                  <option value="">Any</option>
                </select>
              </td>
            </tr>
            <tr>
              <td align="right">Medium</td>
              <td>
                <select id="AdvancedSearch_Medium" class="chosen-select">
                  <option value="">Any</option>
                </select>
              </td>
              <td align="right">Section</td>
              <td>
                <select id="AdvancedSearch_Section" class="chosen-select">
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
              <td align="right">Date Of Birth</td>
              <td align="left" colspan="3">Between
                <input type="text" id="AdvancedSearch_DateOfBirthMin" class="datepicker" style="width: 100px;" />
                and
                <input type="text" id="AdvancedSearch_DateOfBirthMax" class="datepicker" style="width: 100px;" />
              </td>
            </tr>
            <tr>
              <td align="right">Date Of Joining</td>
              <td align="left" colspan="3">Between
                <input type="text" id="AdvancedSearch_DateOfJoiningMin" class="datepicker" style="width: 100px;" />
                and
                <input type="text" id="AdvancedSearch_DateOfJoiningMax" class="datepicker" style="width: 100px;" />
              </td>
            </tr>
            <tr>
              <td align="right" colspan="4">
                <input type="button" id="AdvancedSearch" value="Search" />
              </td>
            </tr>
          </table>
        </div>
      </div>
    </td>
  </tr>
</table>
