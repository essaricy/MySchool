<style>
#EmployeeTeachingSubjectsTable tr th,
#EmployeeTeachingSubjectsTable tr td {
  font-size: 0.8em;
}
</style>

<script type="text/javascript">
var EmployeeTeachingSubject_columnNames = [ 'Employee Subject Id', 'Registered Subject Id', 'Branch Id', 'Branch', 'School Id', 'School', 'Registered Class Id', 'Class Id', 'Class', 'Section Id', 'Section', 'Medium Id', 'Medium', 'Subject Id', 'Subject' ];
var EmployeeTeachingSubject_url = '<%=request.getContextPath()%>/employee-attribute/jsonList.htm?attribute=EmployeeTeachingSubject&EmployeeNumber=' + $('#EmployeeNumber').val() + '&sid=' + new Date().getTime();
var EmployeeTeachingSubject_dataTableSettings = {
  "bPaginate": false,
  "bFilter": false,
  "bInfo": false,
  "bJQueryUI": false,
  "sPaginationType": "full_numbers",
  "aoColumnDefs": [ { "bSearchable": false, "bVisible": false, "aTargets": [ 0, 1, 2, 4, 6, 7, 9, 11, 13 ] } ],
  "sAjaxSource": EmployeeTeachingSubject_url,
}

$(document).ready(function(){
  $('#EmployeeTeachingSubjectsTable').activeTable( {
    containerName: 'EmployeeTeachingSubjectsTable',
    title: 'Teaching Subject',
    width: "100%",
    columns: EmployeeTeachingSubject_columnNames,
    dataTableSettings: EmployeeTeachingSubject_dataTableSettings,
  });
});

</script>

<div id="EmployeeTeachingSubjectsTable"></div>
