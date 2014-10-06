<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="myschool" tagdir="/WEB-INF/tags" %>


<script type="text/javascript" charset="utf-8">
$(document).ready(function() {
  $('#ActiveTable').activeTable({
    width: "80%",
    caption: 'Employment Status',
    title: 'Employment Status',
    columns: [
      'Employment Status Id',
      'Employment Status'
    ],
    dataTableSettings: {
      "bPaginate": true,
      "sAjaxSource": '<%=request.getContextPath()%>/employment/jsonList.htm',
      "aoColumnDefs": [ {
        "bSearchable": false,
        "bVisible": false,
        "aTargets": [ 0 ]
      }],
      "sPaginationType": "full_numbers",
    },
    buttons: actionButtons,
    'add' : {
      title: 'Create Employment Status',
      url: '<%=request.getContextPath()%>/employment/launch.htm',
      width: 420,
      height: 240
    },
    'update': {
      title: 'Update Employment Status',
      url: '<%=request.getContextPath()%>/employment/launch.htm',
      width: 420,
      height: 240,
      sendParams: [ {
        refTable: 'self',
        columnIndex: 0,
        paramName: 'employmentStatusId'
      } ]
    },
    'delete': {
      url: '<%=request.getContextPath()%>/employment/doDelete.htm',
      selectRowMessage: '<spring:message code="common.selectRow.delete"/>',
      confirmMessage: 'Deleting an Employment Status will also delete the data associated with it. Do you really want to continue?',
      sendParams: [ {
        refTable: 'self',
        columnIndex: 0,
        paramName: 'employmentStatusId'
      } ]
    }
  });
});
</script>

<div id="ActiveTable"></div>
<myschool:actions />
