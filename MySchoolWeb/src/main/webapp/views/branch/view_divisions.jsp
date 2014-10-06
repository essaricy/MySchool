<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="myschool" tagdir="/WEB-INF/tags" %>

<script type="text/javascript" charset="utf-8">
$(document).ready(function() {
    $('#ActiveTable').activeTable({
        title: 'Division',
        width: "60%",
        caption: 'Divisions',
        columns: [
          '<spring:message code="division.id"/>',
          '<spring:message code="division.code"/>',
          '<spring:message code="common.description"/>'
        ],
        dataTableSettings: {
            "bPaginate": true,
            "sAjaxSource": '<%=request.getContextPath()%>/division/jsonList.htm',
            "aoColumnDefs": [ {
                "bSearchable": false,
                "bVisible": false,
                "aTargets": [ 0 ]
            }],
            "sPaginationType": "full_numbers",
        },
        buttons: actionButtons,
        'add' : {
            title: 'Create Division',
            url: '<%=request.getContextPath()%>/division/launchNew.htm',
            width: 420,
            height: 300
        },
        'update': {
            title: 'Update Division',
            url: '<%=request.getContextPath()%>/division/launchUpdate.htm',
            width: 420,
            height: 300,
            selectRowMessage: '<spring:message code="common.selectRow.update"/>',
            sendParams: [ {
                refTable: 'self',
                columnIndex: 0,
                paramName: 'divisionId'
            } ]
        },
        'delete': {
            url: '<%=request.getContextPath()%>/division/doDelete.htm',
            selectRowMessage: '<spring:message code="common.selectRow.delete"/>',
            confirmMessage: '<spring:message code="division.delete.warning"/>',
            sendParams: [ {
                refTable: 'self',
                columnIndex: 0,
                paramName: 'divisionId'
            } ]
        }
    });
});

</script>

<div id="ActiveTable"></div>
<myschool:actions />
