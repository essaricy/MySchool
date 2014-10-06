<style>
#EmployeeDocumentsTable tr th,
#EmployeeDocumentsTable tr td {
  font-size: 0.8em;
}
</style>

<script type="text/javascript">
var EmployeeDocument_columnNames = ['Employee Document Id', 'Document Id', 'Document Name', 'Document Number', 'Expiry Date', 'Issued By' ];

var EmployeeDocument_url = '<%=request.getContextPath()%>/employee-attribute/jsonList.htm?attribute=EmployeeDocument&EmployeeNumber=' + $('#EmployeeNumber').val() + '&sid=' + new Date().getTime();
var EmployeeDocument_dataTableSettings = {
  "bPaginate": false,
  "bFilter": false,
  "bInfo": false,
  "bJQueryUI": false,
  "sPaginationType": "full_numbers",
  "aoColumnDefs": [ { "bSearchable": false, "bVisible": false, "aTargets": [ 0, 1 ] } ],
  "sAjaxSource": EmployeeDocument_url,
}

$(document).ready(function(){
  $('#EmployeeDocumentsTable').activeTable( {
    containerName: 'EmployeeDocumentsTable',
    title: 'Document',
    width: "100%",
    columns: EmployeeDocument_columnNames,
    dataTableSettings: EmployeeDocument_dataTableSettings,
  });
});
</script>
<div id="EmployeeDocumentsTable"></div>
