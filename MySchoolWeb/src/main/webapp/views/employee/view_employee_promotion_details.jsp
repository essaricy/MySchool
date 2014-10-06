<style>
#EmployeePromotionsTable tr th,
#EmployeePromotionsTable tr td {
  font-size: 0.8em;
}
</style>

<script type="text/javascript">
var EmployeePromotion_columnNames = [ 'Promotion Id', 'Prior Designation Id', 'Prior Designation', 'Next Designation Id', 'Next Designation', 'Effective From' ];
var EmployeePromotion_url = '<%=request.getContextPath()%>/employee-attribute/jsonList.htm?attribute=EmployeePromotion&EmployeeNumber=' + $('#EmployeeNumber').val() + '&sid=' + new Date().getTime();
var EmployeePromotion_dataTableSettings = {
  "bPaginate": false,
  "bFilter": false,
  "bInfo": false,
  "bJQueryUI": false,
  "sPaginationType": "full_numbers",
  "aoColumnDefs": [ { "bSearchable": false, "bVisible": false, "aTargets": [ 0, 1, 3 ] } ],
  "sAjaxSource": EmployeePromotion_url,
}

$(document).ready(function(){
  $('#EmployeePromotionsTable').activeTable( {
    containerName: 'EmployeePromotionsTable',
    title: 'Promotion',
    width: "100%",
    columns: EmployeePromotion_columnNames,
    dataTableSettings: EmployeePromotion_dataTableSettings,
  });
});
</script>

<div id="EmployeePromotionsTable"></div>
