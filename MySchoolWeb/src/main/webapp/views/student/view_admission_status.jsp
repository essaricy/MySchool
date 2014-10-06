<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="myschool" tagdir="/WEB-INF/tags" %>


<script type="text/javascript" charset="utf-8">
$(document).ready(function() {
  $('#ActiveTable').activeTable({
    width: "80%",
    caption: 'Admission Status',
    title: 'Admission Status',
    columns: [
      'Admission Status Id',
      'Admission Status'
    ],
    dataTableSettings: {
      "bPaginate": true,
      "sAjaxSource": '<%=request.getContextPath()%>/admission-status/jsonList.htm',
      "aoColumnDefs": [ {
        "bSearchable": false,
        "bVisible": false,
        "aTargets": [ 0 ]
      }],
      "sPaginationType": "full_numbers",
    },
    buttons: actionButtons,
    'add' : {
      title: 'Create Admission Status',
      url: '<%=request.getContextPath()%>/admission-status/launch.htm',
      width: 420,
      height: 240
    },
    'update': {
      title: 'Update Admission Status',
      url: '<%=request.getContextPath()%>/admission-status/launch.htm',
      width: 420,
      height: 240,
      sendParams: [ {
        refTable: 'self',
        columnIndex: 0,
        paramName: 'AdmissionStatusId'
      } ]
    },
    'delete': {
      url: '<%=request.getContextPath()%>/admission-status/doDelete.htm',
      selectRowMessage: '<spring:message code="common.selectRow.delete"/>',
      confirmMessage: 'Deleting an Admission Status will also delete the data associated with it. Do you really want to continue?',
      sendParams: [ {
        refTable: 'self',
        columnIndex: 0,
        paramName: 'AdmissionStatusId'
      } ]
    }
  });
});
</script>

<div id="ActiveTable"></div>
<myschool:actions />
