<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="myschool" tagdir="/WEB-INF/tags" %>

<script type="text/javascript" charset="utf-8">
$(document).ready(function() {
    $('#ActiveTable').activeTable({
        title: 'Class',
        width: "60%",
        caption: 'Master Classes',
        columns: [
          '<spring:message code="class.id"/>',
          '<spring:message code="class.name"/>',
          '<spring:message code="class.promotionOrder"/>'
        ],
        dataTableSettings: {
            "bPaginate": true,
            "sAjaxSource": '<%=request.getContextPath()%>/class/jsonList.htm',
            "aoColumnDefs": [ {
                "bSearchable": false,
                "bVisible": false,
                "aTargets": [ 0 ]
            }],
            "sPaginationType": "full_numbers",
            "bJQueryUI": false
        },
        buttons: actionButtons,
        'add' : {
            title: 'Create Class',
            url: '<%=request.getContextPath()%>/class/launchNew.htm',
            width: 420,
            height: 120
        },
        'update': {
            title: 'Update Class',
            url: '<%=request.getContextPath()%>/class/launchUpdate.htm',
            width: 400,
            height: 120,
            selectRowMessage: '<spring:message code="common.selectRow.update"/>',
            sendParams: [ {
                refTable: 'self',
                columnIndex: 0,
                paramName: 'classId'
            } ]
        },
        'delete': {
            url: '<%=request.getContextPath()%>/class/doDelete.htm',
            selectRowMessage: '<spring:message code="common.selectRow.delete"/>',
            confirmMessage: '<spring:message code="class.delete.warning"/>',
            sendParams: [ {
                refTable: 'self',
                columnIndex: 0,
                paramName: 'classId'
            } ]
        }
    });
});

</script>

<div id="ActiveTable"></div>
<myschool:actions />
