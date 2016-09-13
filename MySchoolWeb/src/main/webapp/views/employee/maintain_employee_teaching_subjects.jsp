<style>
#EmployeeTeachingSubjectsTable tr th,
#EmployeeTeachingSubjectsTable tr td {
  font-size: 0.8em;
}
</style>

<script type="text/javascript">
var EmployeeTeachingSubject_dataTableSettings = null;
var employeeSubjectAttributeSequence = [ 'EmployeeSubjectId', 'RegisteredSubjectId', 'BranchId', 'Branch', 'SchoolId', 'School', 'RegisteredClassId', 'ClassId', 'Class', 'SectionId', 'Section', 'MediumId', 'Medium', 'SubjectId', 'Subject' ];
var EmployeeTeachingSubject_columnNames = [ 'Employee Subject Id', 'Registered Subject Id', 'Branch Id', 'Branch', 'School Id', 'School', 'Registered Class Id', 'Class Id', 'Class', 'Section Id', 'Section', 'Medium Id', 'Medium', 'Subject Id', 'Subject' ];

<c:if test="${Employee == null}">
EmployeeTeachingSubject_dataTableSettings = {
  "bPaginate": false,
  "bFilter": false,
  "bInfo": false,
  "bJQueryUI": false,
  "sPaginationType": "full_numbers",
  "aoColumnDefs": [ { "bSearchable": false, "bVisible": false, "aTargets": [ 0, 1, 2, 4, 6, 7, 9, 11, 13 ] } ]
}
</c:if>

<c:if test="${Employee != null}">
var EmployeeTeachingSubject_url = '<%=request.getContextPath()%>/employee-attribute/jsonList.htm?attribute=EmployeeTeachingSubject&EmployeeNumber=' + $('#EmployeeNumber').val() + '&sid=' + new Date().getTime();
EmployeeTeachingSubject_dataTableSettings = {
  "bPaginate": false,
  "bFilter": false,
  "bInfo": false,
  "bJQueryUI": false,
  "sPaginationType": "full_numbers",
  "aoColumnDefs": [ { "bSearchable": false, "bVisible": false, "aTargets": [ 0, 1, 2, 4, 6, 7, 9, 11, 13 ] } ],
  "sAjaxSource": EmployeeTeachingSubject_url,
}
</c:if>

$(document).ready(function(){
  $('#EmployeeTeachingSubjectsTable').activeTable( {
    containerName: 'EmployeeTeachingSubjectsTable',
    title: 'Teaching Subject',
    width: "100%",
    columns: EmployeeTeachingSubject_columnNames,
    dataTableSettings: EmployeeTeachingSubject_dataTableSettings,
    buttons: ['add', 'update', 'delete'],
    'add' : {
      title: 'Add Teaching Subject',
      url: '<%=request.getContextPath()%>/employee-attribute/launch.htm',
      sendParams: [ {
        refTable: 'none',
        paramName: 'attribute',
        paramValue: 'EmployeeTeachingSubject'
      }],
      width: 420,
      height: 320
    },
    'update': {
      title: 'Update Teaching Subject',
      url: '<%=request.getContextPath()%>/employee-attribute/launch.htm',
      width: 420,
      height: 320,
      selectRowMessage: '<spring:message code="common.selectRow.update"/>',
      sendParams: [ {
        refTable: 'none',
        paramName: 'attribute',
        paramValue: 'EmployeeTeachingSubject'
      }, {
        refTable: 'self',
        paramName: 'EmployeeTeachingSubject',
        columnIndex: -1,
        columnNames: employeeSubjectAttributeSequence
      }, {
        refTable: 'self',
        paramName: 'attributeId',
        columnIndex: 0
      } ]
    },
    'delete': {
      title: 'Delete Teaching Subject',
      url: '<%=request.getContextPath()%>/employee-attribute/doDelete.htm',
      selectRowMessage: '<spring:message code="common.selectRow.delete"/>',
      confirmMessage: '<spring:message code="branch.delete.warning"/>',
      confirmCallback: deleteEmployeeSubject
    }
  });

  function deleteEmployeeSubject() {
    var anSelected = fnGetSelected( currentDataTable );
    if (anSelected != null) {
      var selectedRow = currentDataTable.fnGetData(anSelected);
      var registeredSubjectId = selectedRow[0];
      if (registeredSubjectId > 0) {
        $.ajax({
          url: "<%=request.getContextPath()%>/employee-attribute/doDelete.htm?attribute=EmployeeTeachingSubject&attributeId=" + registeredSubjectId,
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

function getEmployeeTeachingSubjects() {
  return getEmployeeAttributesData('CREATE', null, 'EmployeeTeachingSubject', employeeSubjectAttributeSequence);
}
</script>

<div id="EmployeeTeachingSubjectsTable"></div>
