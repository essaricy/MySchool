<style>
#EmployeeExperiencesTable tr th,
#EmployeeExperiencesTable tr td {
  font-size: 0.8em;
}
</style>
<script type="text/javascript">
var EmployeeExperience_dataTableSettings = null;
var employeeExperienceAttributeSequence = ['EmployeeExperienceId', 'Employer', 'JobTitle', 'FromDate', 'ToDate', 'ExperienceInMonths' ];
var EmployeeExperience_columnNames = [ 'Experience Id', 'Employer', 'Job Title', 'From Date', 'To Date', 'Experience In Months' ];

<c:if test="${Employee == null}">
EmployeeExperience_dataTableSettings = {
  "bPaginate": false,
  "bFilter": false,
  "bInfo": false,
  "bJQueryUI": false,
  "sPaginationType": "full_numbers",
  "aoColumnDefs": [ { "bSearchable": false, "bVisible": false, "aTargets": [ 0 ] } ]
}
</c:if>

<c:if test="${Employee != null}">
var EmployeeExperience_url = '<%=request.getContextPath()%>/employee-attribute/jsonList.htm?attribute=EmployeeExperience&EmployeeNumber=' + $('#EmployeeNumber').val() + '&sid=' + new Date().getTime();
EmployeeExperience_dataTableSettings = {
  "bPaginate": false,
  "bFilter": false,
  "bInfo": false,
  "bJQueryUI": false,
  "sPaginationType": "full_numbers",
  "aoColumnDefs": [ { "bSearchable": false, "bVisible": false, "aTargets": [ 0 ] } ],
  "sAjaxSource": EmployeeExperience_url,
}
</c:if>

$(document).ready(function(){
  $('#EmployeeExperiencesTable').activeTable( {
    containerName: 'EmployeeExperiencesTable',
    title: 'Experience',
    width: "100%",
    columns: EmployeeExperience_columnNames,
    dataTableSettings: EmployeeExperience_dataTableSettings,
    buttons: ['add', 'update', 'delete'],
    'add' : {
      title: 'Add Experience',
      url: '<%=request.getContextPath()%>/employee-attribute/launch.htm',
      sendParams: [ {
        refTable: 'none',
        paramName: 'attribute',
        paramValue: 'EmployeeExperience'
      }],
      width: 420,
      height: 320
    },
    'update': {
      title: 'Update Experience',
      url: '<%=request.getContextPath()%>/employee-attribute/launch.htm',
      width: 420,
      height: 320,
      selectRowMessage: '<spring:message code="common.selectRow.update"/>',
      sendParams: [ {
        refTable: 'none',
        paramName: 'attribute',
        paramValue: 'EmployeeExperience'
      }, {
        refTable: 'self',
        paramName: 'EmployeeExperience',
        columnIndex: -1,
        columnNames: employeeExperienceAttributeSequence
      } ]
    },
    'delete': {
      title: 'Delete Experience',
      url: '<%=request.getContextPath()%>/employee-attribute/doDelete.htm',
      selectRowMessage: '<spring:message code="common.selectRow.delete"/>',
      confirmMessage: '<spring:message code="branch.delete.warning"/>',
      confirmCallback: deleteEmployeeExperience
    }
  });

  function deleteEmployeeExperience() {
    var anSelected = fnGetSelected( currentDataTable );
    if (anSelected != null) {
      var selectedRow = currentDataTable.fnGetData(anSelected);
      var employeeExperienceId = selectedRow[0];
      if (employeeExperienceId > 0) {
        $.ajax({
          url: "<%=request.getContextPath()%>/employee-attribute/doDelete.htm?attribute=EmployeeExperience&attributeId=" + employeeExperienceId,
          context: document.body,
          success: function(result) {
            $(this).addClass("done");
          }
        });
      }
      currentDataTable.fnDeleteRow(anSelected);
    }
  }
});

function getEmployeeExperiences() {
  return getEmployeeAttributesData('CREATE', null, 'EmployeeExperience', employeeExperienceAttributeSequence);
}
</script>

<div id="EmployeeExperiencesTable"></div>
