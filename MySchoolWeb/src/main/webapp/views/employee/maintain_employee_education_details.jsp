<style>
#EmployeeEducationsTable tr th,
#EmployeeEducationsTable tr td {
  font-size: 0.8em;
}
</style>

<script type="text/javascript">
var EmployeeEducation_dataTableSettings = null;
var employeeEducationAttributeSequence = ['EmployeeEducationId', 'Degree', 'Specialization', 'College', 'University', 'YearOfGraduation', 'Percentage' ];
var EmployeeEducation_columnNames = [ 'Education Id', 'Degree', 'Specialization', 'College', 'University', 'Year Of Graduation', 'Percentage' ];


<c:if test="${Employee == null}">
EmployeeEducation_dataTableSettings = {
  "bPaginate": false,
  "bFilter": false,
  "bInfo": false,
  "bJQueryUI": false,
  "sPaginationType": "full_numbers",
  "aoColumnDefs": [ { "bSearchable": false, "bVisible": false, "aTargets": [ 0 ] } ]
}
</c:if>

<c:if test="${Employee != null}">
var EmployeeEducation_url = '<%=request.getContextPath()%>/employee-attribute/jsonList.htm?attribute=EmployeeEducation&EmployeeNumber=' + $('#EmployeeNumber').val() + '&sid=' + new Date().getTime();
EmployeeEducation_dataTableSettings = {
  "bPaginate": false,
  "bFilter": false,
  "bInfo": false,
  "bJQueryUI": false,
  "sPaginationType": "full_numbers",
  "aoColumnDefs": [ { "bSearchable": false, "bVisible": false, "aTargets": [ 0 ] } ],
  "sAjaxSource": EmployeeEducation_url
}
</c:if>

$(document).ready(function(){
  $('#EmployeeEducationsTable').activeTable( {
    containerName: 'EmployeeEducationsTable',
    title: 'Education',
    width: "100%",
    columns: EmployeeEducation_columnNames,
    dataTableSettings: EmployeeEducation_dataTableSettings,
    buttons: ['add', 'update', 'delete'],
    'add' : {
      title: 'Add Education',
      url: '<%=request.getContextPath()%>/employee-attribute/launch.htm',
      sendParams: [ {
        refTable: 'none',
        paramName: 'attribute',
        paramValue: 'EmployeeEducation'
      }],
      width: 420,
      height: 320
    },
    'update': {
      title: 'Update Education',
      url: '<%=request.getContextPath()%>/employee-attribute/launch.htm',
      width: 420,
      height: 320,
      selectRowMessage: '<spring:message code="common.selectRow.update"/>',
      sendParams: [ {
        refTable: 'none',
        paramName: 'attribute',
        paramValue: 'EmployeeEducation'
      }, {
        refTable: 'self',
        paramName: 'EmployeeEducation',
        columnIndex: -1,
        columnNames: employeeEducationAttributeSequence
      } ]
    },
    'delete': {
      title: 'Delete Education',
      url: '<%=request.getContextPath()%>/employee-attribute/doDelete.htm',
      selectRowMessage: '<spring:message code="common.selectRow.delete"/>',
      confirmMessage: '<spring:message code="branch.delete.warning"/>',
      confirmCallback: deleteEmployeeEducation
    }
  });

  function deleteEmployeeEducation(decision) {
    var anSelected = fnGetSelected( currentDataTable );
    if (anSelected != null) {
      var selectedRow = currentDataTable.fnGetData(anSelected);
      var employeeEducationId = selectedRow[0];
      if (employeeEducationId > 0) {
        $.ajax({
          url: "<%=request.getContextPath()%>/employee-attribute/doDelete.htm?attribute=EmployeeEducation&attributeId=" + employeeEducationId,
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

function getEmployeeEducations() {
    return getEmployeeAttributesData('CREATE', null, 'EmployeeEducation', employeeEducationAttributeSequence);
}
</script>

<div id="EmployeeEducationsTable"></div>
