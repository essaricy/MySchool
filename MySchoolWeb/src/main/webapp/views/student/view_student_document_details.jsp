<%@ taglib prefix="myschool" tagdir="/WEB-INF/tags" %>

<style>
#StudentDocumentsTable tr th,
#StudentDocumentsTable tr td {
  font-size: 0.8em;
}
</style>

<script type="text/javascript">
var StudentDocument_columnNames = [
  'Student Documentr ID', 'Document Id', 'Document Name', 'Document Number', 'Expiry Date', 'Issued By'];
var StudentDocument_url = '<%=request.getContextPath()%>/student-attribute/jsonList.htm?attribute=StudentDocument&AdmissionNumber=' + $('#AdmissionNumber').val() + '&sid=' + new Date().getTime();
var StudentDocument_dataTableSettings = {
  "bPaginate": false,
  "bFilter": false,
  "bInfo": false,
  "bJQueryUI": false,
  "sPaginationType": "full_numbers",
  "aoColumnDefs": [ { "bSearchable": false, "bVisible": false, "aTargets": [ 0, 1 ] } ],
  "sAjaxSource": StudentDocument_url
}

$(document).ready(function(){
  $('#StudentDocumentsTable').activeTable( {
    containerName: 'StudentDocumentsTable',
    title: 'Student Docuemnts',
    width: "100%",
    columns: StudentDocument_columnNames,
    dataTableSettings: StudentDocument_dataTableSettings,
  });
});

</script>
<div id="StudentDocumentsTable"></div>
