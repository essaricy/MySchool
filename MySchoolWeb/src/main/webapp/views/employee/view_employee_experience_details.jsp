<style>
#EmployeeExperiencesTable tr th,
#EmployeeExperiencesTable tr td {
  font-size: 0.8em;
}
</style>
<script type="text/javascript">
var EmployeeExperience_columnNames = [ 'Experience Id', 'Employer', 'Job Title', 'From Date', 'To Date', 'Experience In Months' ];
var EmployeeExperience_url = '<%=request.getContextPath()%>/employee-attribute/jsonList.htm?attribute=EmployeeExperience&EmployeeNumber=' + $('#EmployeeNumber').val() + '&sid=' + new Date().getTime();
var EmployeeExperience_dataTableSettings = {
  "bPaginate": false,
  "bFilter": false,
  "bInfo": false,
  "bJQueryUI": false,
  "sPaginationType": "full_numbers",
  "aoColumnDefs": [ { "bSearchable": false, "bVisible": false, "aTargets": [ 0 ] } ],
  "sAjaxSource": EmployeeExperience_url,
}

$(document).ready(function(){
  $('#EmployeeExperiencesTable').activeTable( {
    containerName: 'EmployeeExperiencesTable',
    title: 'Experience',
    width: "100%",
    columns: EmployeeExperience_columnNames,
    dataTableSettings: EmployeeExperience_dataTableSettings,
  });
});
</script>
<div id="EmployeeExperiencesTable"></div>
