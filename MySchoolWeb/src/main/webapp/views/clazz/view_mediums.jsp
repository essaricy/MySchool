<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="myschool" tagdir="/WEB-INF/tags" %>

<script type="text/javascript" charset="utf-8">
$(document).ready(function() {
    $('#ActiveTable').activeTable({
        title: 'Medium',
        width: "60%",
        caption: 'Mediums',
        columns: [
          '<spring:message code="medium.id"/>',
          '<spring:message code="common.description"/>'
        ],
        dataTableSettings: {
            "bPaginate": true,
            "sAjaxSource": '<%=request.getContextPath()%>/medium/jsonList.htm',
            "aoColumnDefs": [ {
                "bSearchable": false,
                "bVisible": false,
                "aTargets": [ 0 ]
            }],
            "sPaginationType": "full_numbers",
        },
        buttons: actionButtons,
        'add' : {
            title: 'Create Medium',
            url: '<%=request.getContextPath()%>/medium/launchNew.htm',
            width: 400,
            height: 150
        },
        'update': {
            title: 'Update Medium',
            url: '<%=request.getContextPath()%>/medium/launchUpdate.htm',
            width: 400,
            height: 150,
            selectRowMessage: '<spring:message code="common.selectRow.update"/>',
            sendParams: [ {
                refTable: 'self',
                columnIndex: 0,
                paramName: 'mediumId'
            } ]
        },
        'delete': {
            url: '<%=request.getContextPath()%>/medium/doDelete.htm',
            selectRowMessage: '<spring:message code="common.selectRow.delete"/>',
            confirmMessage: '<spring:message code="medium.delete.warning"/>',
            sendParams: [ {
                refTable: 'self',
                columnIndex: 0,
                paramName: 'mediumId'
            } ]
        }
    });
});
</script>

<div id="ActiveTable"></div>
<myschool:actions />
