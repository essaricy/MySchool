<style>
#EmployeeDocumentsTable tr th,
#EmployeeDocumentsTable tr td {
  font-size: 0.8em;
}
</style>

<script type="text/javascript">
var EmployeeDocument_dataTableSettings = null;
var employeeDocumentAttributeSequence = ['EmployeeDocumentId', 'DocumentId', 'DocumentName', 'DocumentNumber', 'DocumentExpiryDate', 'DocumentIssuedBy'];
var EmployeeDocument_columnNames = ['Employee Document Id', 'Document Id', 'Document Name', 'Document Number', 'Expiry Date', 'Issued By' ];

<c:if test="${Employee == null}">
EmployeeDocument_dataTableSettings = {
  "bPaginate": false,
  "bFilter": false,
  "bInfo": false,
  "bJQueryUI": false,
  "sPaginationType": "full_numbers",
  "aoColumnDefs": [ { "bSearchable": false, "bVisible": false, "aTargets": [ 0, 1 ] } ]
}
</c:if>

<c:if test="${Employee != null}">
var EmployeeDocument_url = '<%=request.getContextPath()%>/employee-attribute/jsonList.htm?attribute=EmployeeDocument&EmployeeNumber=' + $('#EmployeeNumber').val() + '&sid=' + new Date().getTime();
EmployeeDocument_dataTableSettings = {
  "bPaginate": false,
  "bFilter": false,
  "bInfo": false,
  "bJQueryUI": false,
  "sPaginationType": "full_numbers",
  "aoColumnDefs": [ { "bSearchable": false, "bVisible": false, "aTargets": [ 0, 1 ] } ],
  "sAjaxSource": EmployeeDocument_url,
}
</c:if>

$(document).ready(function(){
  $('#EmployeeDocumentsTable').activeTable( {
    containerName: 'EmployeeDocumentsTable',
    title: 'Document',
    width: "100%",
    columns: EmployeeDocument_columnNames,
    dataTableSettings: EmployeeDocument_dataTableSettings,
    buttons: ['add', 'update', 'delete'],
    'add' : {
      title: 'Add Document',
      url: '<%=request.getContextPath()%>/employee-attribute/launch.htm',
      sendParams: [ {
        refTable: 'none',
        paramName: 'attribute',
        paramValue: 'EmployeeDocument'
      }],
      width: 420,
      height: 320
    },
    'update': {
      title: 'Update Document',
      url: '<%=request.getContextPath()%>/employee-attribute/launch.htm',
      width: 420,
      height: 320,
      selectRowMessage: '<spring:message code="common.selectRow.update"/>',
      sendParams: [ {
        refTable: 'none',
        paramName: 'attribute',
        paramValue: 'EmployeeDocument'
      }, {
        refTable: 'self',
        paramName: 'EmployeeDocument',
        columnIndex: -1,
        columnNames: employeeDocumentAttributeSequence
      } ]
    },
    'delete': {
      title: 'Delete Document',
      url: '<%=request.getContextPath()%>/employee-attribute/doDelete.htm',
      selectRowMessage: '<spring:message code="common.selectRow.delete"/>',
      confirmMessage: '<spring:message code="branch.delete.warning"/>',
      confirmCallback: deleteEmployeeDocument
    }
  });

  function deleteEmployeeDocument() {
    var anSelected = fnGetSelected( currentDataTable );
    if (anSelected != null) {
      var selectedRow = currentDataTable.fnGetData(anSelected);
      var employeeDocumentId = selectedRow[0];
      if (employeeDocumentId > 0) {
        $.ajax({
          url: "<%=request.getContextPath()%>/employee-attribute/doDelete.htm?attribute=EmployeeDocument&attributeId=" + employeeDocumentId,
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

function getEmployeeDocuments() {
    return getEmployeeAttributesData('CREATE', null, 'EmployeeDocument', employeeDocumentAttributeSequence);
}
</script>

<div id="EmployeeDocumentsTable"></div>
