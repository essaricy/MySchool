<style>
#StudentDocumentsTable tr th,
#StudentDocumentsTable tr td {
  font-size: 0.8em;
}
</style>
<script type="text/javascript">
var StudentDocument_dataTableSettings = null;
var studentDocumentAttributeSequence = ['StudentDocumentId', 'DocumentId', 'DocumentName', 'DocumentNumber', 'DocumentExpiryDate', 'DocumentIssuedBy'];
var StudentDocument_columnNames = [ 'Student Document Id', 'Document Id', 'Document Name', 'Document Number', 'Expiry Date', 'Issued By' ];

<c:if test="${Student == null}">
StudentDocument_dataTableSettings = {
  "bPaginate": false,
  "bFilter": false,
  "bInfo": false,
  "bJQueryUI": false,
  "sPaginationType": "full_numbers",
  "aoColumnDefs": [ { "bSearchable": false, "bVisible": false, "aTargets": [ 0, 1 ] } ]
}
</c:if>

<c:if test="${Student != null}">
var StudentDocument_url = '<%=request.getContextPath()%>/student-attribute/jsonList.htm?attribute=StudentDocument&AdmissionNumber=' + $('#AdmissionNumber').val() + '&sid=' + new Date().getTime();
StudentDocument_dataTableSettings = {
  "bPaginate": false,
  "bFilter": false,
  "bInfo": false,
  "bJQueryUI": false,
  "sPaginationType": "full_numbers",
  "aoColumnDefs": [ { "bSearchable": false, "bVisible": false, "aTargets": [ 0, 1 ] } ],
  "sAjaxSource": StudentDocument_url,
}
</c:if>

$(document).ready(function(){
  $('#StudentDocumentsTable').activeTable( {
    containerName: 'StudentDocumentsTable',
    title: 'Document',
    width: "100%",
    columns: StudentDocument_columnNames,
    dataTableSettings: StudentDocument_dataTableSettings,
    buttons: ['add', 'update', 'delete'],
    'add' : {
      title: 'Add Document',
      url: '<%=request.getContextPath()%>/student-attribute/launch.htm',
      sendParams: [ {
        refTable: 'none',
        paramName: 'attribute',
        paramValue: 'StudentDocument'
      }],
      width: 420,
      height: 320
    },
    'update': {
      title: 'Update Document',
      url: '<%=request.getContextPath()%>/student-attribute/launch.htm',
      width: 420,
      height: 320,
      selectRowMessage: '<spring:message code="common.selectRow.update"/>',
      sendParams: [ {
        refTable: 'none',
        paramName: 'attribute',
        paramValue: 'StudentDocument'
      }, {
        refTable: 'self',
        paramName: 'StudentDocument',
        columnIndex: -1,
        columnNames: studentDocumentAttributeSequence
      } ]
    },
    'delete': {
      title: 'Delete Document',
      url: '<%=request.getContextPath()%>/student-attribute/doDelete.htm',
      selectRowMessage: '<spring:message code="common.selectRow.delete"/>',
      confirmMessage: '<spring:message code="branch.delete.warning"/>',
      confirmCallback: deleteStudentDocument
    }
  });

  function deleteStudentDocument(decision) {
    if (decision == "Yes") {
      var anSelected = fnGetSelected( currentDataTable );
      if (anSelected != null) {
        var selectedRow = currentDataTable.fnGetData(anSelected);
        var studentDocumentId = selectedRow[0];
        if (studentDocumentId > 0) {
          $.ajax({
            url: "<%=request.getContextPath()%>/student-attribute/doDelete.htm?attribute=StudentDocument&attributeId=" + studentDocumentId,
            context: document.body,
            success: function(result) {
              $(this).addClass("done");
            }
          });
        }
        currentDataTable.fnDeleteRow(anSelected);
      }
    }
  }
});

function getStudentDocuments() {
  return getStudentAttributesData('CREATE', null, 'StudentDocument', studentDocumentAttributeSequence);
}
</script>

<div id="StudentDocumentsTable"></div>

