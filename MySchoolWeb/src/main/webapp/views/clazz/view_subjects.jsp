<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="myschool" tagdir="/WEB-INF/tags" %>

<script type="text/javascript" charset="utf-8">
$(document).ready(function() {
    $('#ActiveTable').activeTable({
        title: 'Subject',
        width: "60%",
        caption: 'Subjects',
        columns: [
          '<spring:message code="subject.id"/>',
          '<spring:message code="subject.name"/>'
        ],
        dataTableSettings: {
            "bPaginate": true,
            "sAjaxSource": '<%=request.getContextPath()%>/subject/jsonList.htm',
            "aoColumnDefs": [ {
                "bSearchable": false,
                "bVisible": false,
                "aTargets": [ 0 ]
            }],
            "sPaginationType": "full_numbers",
        },
        buttons: actionButtons,
        'add' : {
            title: 'Create Subject',
            url: '<%=request.getContextPath()%>/subject/launchNew.htm',
            width: 400,
            height: 160,
        },
        'update': {
            title: 'Update Subject',
            url: '<%=request.getContextPath()%>/subject/launchUpdate.htm',
            width: 400,
            height: 160,
            selectRowMessage: '<spring:message code="common.selectRow.update"/>',
            sendParams: [ {
                refTable: 'self',
                columnIndex: 0,
                paramName: 'subjectId'
            } ]
        },
        'delete': {
            url: '<%=request.getContextPath()%>/subject/doDelete.htm',
            selectRowMessage: '<spring:message code="common.selectRow.delete"/>',
            confirmMessage: '<spring:message code="subject.delete.warning"/>',
            sendParams: [ {
                refTable: 'self',
                columnIndex: 0,
                paramName: 'subjectId'
            } ]
        }
    });
});
</script>

<div id="ActiveTable"></div>
<myschool:actions />
