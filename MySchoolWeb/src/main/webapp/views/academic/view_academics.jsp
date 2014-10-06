<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="myschool" tagdir="/WEB-INF/tags" %>


<script type="text/javascript" charset="utf-8">
$(document).ready(function() {
  $('#ActiveTable').activeTable({
    width: "60%",
    caption: 'Academics',
    title: 'Academic',
    columns: [
      '<spring:message code="academic.yearName"/>',
      '<spring:message code="common.startDate"/>',
      '<spring:message code="common.endDate"/>'
    ],
    dataTableSettings: {
      "bPaginate": true,
      "sAjaxSource": '<%=request.getContextPath()%>/academic/jsonList.htm',
      "aoColumnDefs": [ {
        "bSearchable": false,
        "bVisible": false,
        "aTargets": [ ]
      }],
      "sPaginationType": "full_numbers",
    },
    buttons: actionButtons,
    'add' : {
      title: '<spring:message code="academic.create" />',
      url: '<%=request.getContextPath()%>/academic/launchNew.htm',
      width: 420,
      height: 240
    },
    'update': {
      title: '<spring:message code="academic.update" />',
      url: '<%=request.getContextPath()%>/academic/launchUpdate.htm',
      width: 420,
      height: 240,
      sendParams: [ {
        refTable: 'self',
        columnIndex: 0,
        paramName: 'academicYearName'
      } ]
    },
    'delete': {
      url: '<%=request.getContextPath()%>/academic/doDelete.htm',
      selectRowMessage: '<spring:message code="common.selectRow.delete"/>',
      confirmMessage: 'Deleting an academic year will delete the associated data with it. Do you really want to continue?',
      sendParams: [ {
        refTable: 'self',
        columnIndex: 0,
        paramName: 'academicYearName'
      } ]
    }
  });
});
</script>

<div id="ActiveTable"></div>
<myschool:actions />
