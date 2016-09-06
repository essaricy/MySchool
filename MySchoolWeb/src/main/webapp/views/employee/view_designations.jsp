<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="myschool" tagdir="/WEB-INF/tags" %>

<script type="text/javascript" charset="utf-8">
$(document).ready(function() {
    $('#ActiveTable').activeTable({
        title: 'Designation',
        width: "60%",
        caption: 'Designations',
        columns: [
          '<spring:message code="designation.id"/>',
          '<spring:message code="designation.name"/>'
        ],
        dataTableSettings: {
            "bPaginate": true,
            "sAjaxSource": '<%=request.getContextPath()%>/designation/jsonList.htm',
            "aoColumnDefs": [ {
                "bSearchable": false,
                "bVisible": false,
                "aTargets": [ ]
            }],
            "sPaginationType": "full_numbers",
        },
        buttons: actionButtons,
        'add' : {
            title: 'Create Designation',
            url: '<%=request.getContextPath()%>/designation/launchNew.htm',
            width: 420,
            height: 200
        },
        'update': {
            title: 'Update Designation',
            url: '<%=request.getContextPath()%>/designation/launchUpdate.htm',
            width: 420,
            height: 200,
            selectRowMessage: '<spring:message code="common.selectRow.update"/>',
            sendParams: [ {
                refTable: 'self',
                columnIndex: 0,
                paramName: 'designationId'
            } ]
        },
        'delete': {
            url: '<%=request.getContextPath()%>/designation/doDelete.htm',
            selectRowMessage: '<spring:message code="common.selectRow.delete"/>',
            confirmMessage: '<spring:message code="designation.delete.warning"/>',
            sendParams: [ {
                refTable: 'self',
                columnIndex: 0,
                paramName: 'designationId'
            } ]
        }
    });
});
</script>

<div id="ActiveTable"></div>
<myschool:actions />
