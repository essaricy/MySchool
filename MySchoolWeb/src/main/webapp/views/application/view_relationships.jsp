<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="myschool" tagdir="/WEB-INF/tags" %>


<script type="text/javascript" charset="utf-8">
$(document).ready(function() {
  $('#ActiveTable').activeTable({
    width: "80%",
    caption: 'Relationship Codes',
    title: 'Relationship',
    columns: [
      'Relationship Code',
      'Relationship Name'
    ],
    dataTableSettings: {
      "bPaginate": true,
      "sAjaxSource": '<%=request.getContextPath()%>/relationship/jsonList.htm',
      "aoColumnDefs": [ {
        "bSearchable": false,
        "bVisible": false,
        "aTargets": [  ]
      }],
      "sPaginationType": "full_numbers",
    },
    buttons: actionButtons,
    'add' : {
      title: 'Create Relationship Code',
      url: '<%=request.getContextPath()%>/relationship/launch.htm',
      width: 420,
      height: 240
    },
    'update': {
      title: 'Update Relationship Code',
      url: '<%=request.getContextPath()%>/relationship/launch.htm',
      width: 420,
      height: 240,
      sendParams: [ {
        refTable: 'self',
        columnIndex: 0,
        paramName: 'code'
      } ]
    },
    'delete': {
      url: '<%=request.getContextPath()%>/relationship/doDelete.htm',
      selectRowMessage: '<spring:message code="common.selectRow.delete"/>',
      confirmMessage: 'Deleting a relationship code will also delete the data associated with it. Do you really want to continue?',
      sendParams: [ {
        refTable: 'self',
        columnIndex: 0,
        paramName: 'code'
      } ]
    }
  });
});
</script>

<div id="ActiveTable"></div>
<myschool:actions />
