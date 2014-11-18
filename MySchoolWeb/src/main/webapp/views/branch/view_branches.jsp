<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="myschool" tagdir="/WEB-INF/tags" %>

<script type="text/javascript" charset="utf-8">
$(document).ready(function() {
    $('#ActiveTable').activeTable({
        title: 'Branch',
        width: "70%",
        caption: 'Branches',
        columns: [
          '<spring:message code="branch.branchId"/>',
          '<spring:message code="map.url"/>',
          '<spring:message code="branch.branchCode"/>',
          '<spring:message code="common.description"/>',
          '<spring:message code="common.address"/>',
          '<spring:message code="common.region"/>',
          '<spring:message code="common.phoneNumber"/>',
          '<spring:message code="common.email"/>'
        ],
        dataTableSettings: {
            "bPaginate": true,
            "sAjaxSource": '<%=request.getContextPath()%>/branch/jsonList.htm',
            "aoColumnDefs": [ {
                "bSearchable": false,
                "bVisible": false,
                "aTargets": [ 0, 1 ]
            }],
            "sPaginationType": "full_numbers",
        },
        buttons: actionButtons,
        'add' : {
            title: 'Create Branch',
            url: '<%=request.getContextPath()%>/branch/launchNew.htm',
            width: 420,
            height: 320
        },
        'update': {
            title: 'Update Branch',
            url: '<%=request.getContextPath()%>/branch/launchUpdate.htm',
            width: 420,
            height: 320,
            selectRowMessage: '<spring:message code="common.selectRow.update"/>',
            sendParams: [ {
                refTable: 'self',
                columnIndex: 0,
                paramName: 'branchId'
            } ]
        },
        'delete': {
            title: 'Delete Branch',
            url: '<%=request.getContextPath()%>/branch/doDelete.htm',
            selectRowMessage: '<spring:message code="common.selectRow.delete"/>',
            confirmMessage: '<spring:message code="branch.delete.warning"/>',
            sendParams: [ {
                refTable: 'self',
                columnIndex: 0,
                paramName: 'branchId'
            } ]
        },
        'map': {
            selectRowMessage: '<spring:message code="common.selectRow.mapView"/>',
            mapUrlIndex: 1,
            width: 600,
            height: 480
        }
    });
});

</script>

<div id="ActiveTable"></div>
<myschool:actions map="true" />
