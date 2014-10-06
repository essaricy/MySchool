<style>
#EmployeeEducationsTable tr th,
#EmployeeEducationsTable tr td {
  font-size: 0.8em;
}
</style>

<script type="text/javascript">
var EmployeeEducation_columnNames = [ 'Education Id', 'Degree', 'Specialization', 'College', 'University', 'Year Of Graduation', 'Percentage' ];
var EmployeeEducation_url = '<%=request.getContextPath()%>/employee-attribute/jsonList.htm?attribute=EmployeeEducation&EmployeeNumber=' + $('#EmployeeNumber').val() + '&sid=' + new Date().getTime();
var EmployeeEducation_dataTableSettings = {
  "bPaginate": false,
  "bFilter": false,
  "bInfo": false,
  "bJQueryUI": false,
  "sPaginationType": "full_numbers",
  "aoColumnDefs": [ { "bSearchable": false, "bVisible": false, "aTargets": [ 0 ] } ],
  "sAjaxSource": EmployeeEducation_url
}

$(document).ready(function(){
  $('#EmployeeEducationsTable').activeTable( {
    containerName: 'EmployeeEducationsTable',
    title: 'Education',
    width: "100%",
    columns: EmployeeEducation_columnNames,
    dataTableSettings: EmployeeEducation_dataTableSettings,
  });
});
</script>
<div id="EmployeeEducationsTable"></div>
