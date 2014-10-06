<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="myschool" tagdir="/WEB-INF/tags" %>


<script type="text/javascript" charset="utf-8">
$(document).ready(function() {
  $('#ActiveTable').activeTable({
    width: "80%",
    caption: 'Documents',
    title: 'Document',
    columns: [
      'Document Id',
      'Document Name',
      'Description',
      'Applicability For Employees',
      'Applicability For Students'
    ],
    dataTableSettings: {
      "bPaginate": true,
      "sAjaxSource": '<%=request.getContextPath()%>/document/jsonList.htm',
      "aoColumnDefs": [ {
        "bSearchable": false,
        "bVisible": false,
        "aTargets": [ 0 ]
      }],
      "sPaginationType": "full_numbers",
    },
    buttons: actionButtons,
    'add' : {
      title: 'Create Document',
      url: '<%=request.getContextPath()%>/document/launch.htm',
      width: 420,
      height: 240
    },
    'update': {
      title: 'Update Document',
      url: '<%=request.getContextPath()%>/document/launch.htm',
      width: 420,
      height: 240,
      sendParams: [ {
        refTable: 'self',
        columnIndex: 0,
        paramName: 'documentId'
      } ]
    },
    'delete': {
      url: '<%=request.getContextPath()%>/document/doDelete.htm',
      selectRowMessage: '<spring:message code="common.selectRow.delete"/>',
      confirmMessage: 'Deleting a document will also delete the data associated with it. Do you really want to continue?',
      sendParams: [ {
        refTable: 'self',
        columnIndex: 0,
        paramName: 'documentId'
      } ]
    }
  });
});
</script>

<div id="ActiveTable"></div>
<myschool:actions />
