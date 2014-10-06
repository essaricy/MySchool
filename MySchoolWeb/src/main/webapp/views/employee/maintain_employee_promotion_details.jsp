<style>
#EmployeePromotionsTable tr th,
#EmployeePromotionsTable tr td {
  font-size: 0.8em;
}
</style>

<script type="text/javascript">
var EmployeePromotion_dataTableSettings = null;
var employeePromotionAttributeSequence = ['EmployeePromotionId', 'PriorDesignationId', 'PriorDesignation', 'CurrentDesignationId', 'CurrentDesignation', 'EffectiveFrom' ];
var EmployeePromotion_columnNames = [ 'Promotion Id', 'Prior Designation Id', 'Prior Designation', 'Next Designation Id', 'Next Designation', 'Effective From' ];

<c:if test="${Employee == null}">
EmployeePromotion_dataTableSettings = {
  "bPaginate": false,
  "bFilter": false,
  "bInfo": false,
  "bJQueryUI": false,
  "sPaginationType": "full_numbers",
  "aoColumnDefs": [ { "bSearchable": false, "bVisible": false, "aTargets": [ 0, 1, 3 ] } ]
}
</c:if>

<c:if test="${Employee != null}">
var EmployeePromotion_url = '<%=request.getContextPath()%>/employee-attribute/jsonList.htm?attribute=EmployeePromotion&EmployeeNumber=' + $('#EmployeeNumber').val() + '&sid=' + new Date().getTime();
EmployeePromotion_dataTableSettings = {
  "bPaginate": false,
  "bFilter": false,
  "bInfo": false,
  "bJQueryUI": false,
  "sPaginationType": "full_numbers",
  "aoColumnDefs": [ { "bSearchable": false, "bVisible": false, "aTargets": [ 0, 1, 3 ] } ],
  "sAjaxSource": EmployeePromotion_url,
}
</c:if>

$(document).ready(function(){
  $('#EmployeePromotionsTable').activeTable( {
    containerName: 'EmployeePromotionsTable',
    title: 'Promotion',
    width: "100%",
    columns: EmployeePromotion_columnNames,
    dataTableSettings: EmployeePromotion_dataTableSettings,
    buttons: ['add', 'update', 'delete'],
    'add' : {
      title: 'Add Promotion',
      url: '<%=request.getContextPath()%>/employee-attribute/launch.htm',
      sendParams: [ {
        refTable: 'none',
        paramName: 'attribute',
        paramValue: 'EmployeePromotion'
      }],
      width: 420,
      height: 320
    },
    'update': {
      title: 'Update Promotion',
      url: '<%=request.getContextPath()%>/employee-attribute/launch.htm',
      width: 420,
      height: 320,
      selectRowMessage: '<spring:message code="common.selectRow.update"/>',
      sendParams: [ {
        refTable: 'none',
        paramName: 'attribute',
        paramValue: 'EmployeePromotion'
      }, {
        refTable: 'self',
        paramName: 'EmployeePromotion',
        columnIndex: -1,
        columnNames: employeePromotionAttributeSequence
      } ]
    },
    'delete': {
      title: 'Delete Promotion',
      url: '<%=request.getContextPath()%>/employee-attribute/doDelete.htm',
      selectRowMessage: '<spring:message code="common.selectRow.delete"/>',
      confirmMessage: '<spring:message code="branch.delete.warning"/>',
      confirmCallback: deleteEmployeePromotion
    }
  });

  function deleteEmployeePromotion(decision) {
    if (decision == "Yes") {
      var anSelected = fnGetSelected( currentDataTable );
      if (anSelected != null) {
        var selectedRow = currentDataTable.fnGetData(anSelected);
        var employeePromotionId = selectedRow[0];
        if (employeePromotionId > 0) {
          $.ajax({
            url: "<%=request.getContextPath()%>/employee-attribute/doDelete.htm?attribute=EmployeePromotion&attributeId=" + employeePromotionId,
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

function getEmployeePromotions() {
  return getEmployeeAttributesData('CREATE', null, 'EmployeePromotion', employeePromotionAttributeSequence);
}
</script>

<div id="EmployeePromotionsTable"></div>
