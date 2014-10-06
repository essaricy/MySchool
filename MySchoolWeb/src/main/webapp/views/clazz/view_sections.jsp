<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="myschool" tagdir="/WEB-INF/tags" %>

<script type="text/javascript" charset="utf-8">
$(document).ready(function() {
    $('#ActiveTable').activeTable({
        title: 'Section',
        width: "60%",
        caption: 'Sections',
        columns: [
          '<spring:message code="section.id"/>',
          '<spring:message code="section.name"/>'
        ],
        dataTableSettings: {
            "bPaginate": true,
            "sAjaxSource": '<%=request.getContextPath()%>/section/jsonList.htm',
            "aoColumnDefs": [ {
                "bSearchable": false,
                "bVisible": false,
                "aTargets": [ 0 ]
            }],
            "sPaginationType": "full_numbers",
        },
        buttons: actionButtons,
        'add' : {
            title: 'Create Section',
            url: '<%=request.getContextPath()%>/section/launchNew.htm',
            width: 400,
            height: 100
        },
        'update': {
            title: 'Update Section',
            url: '<%=request.getContextPath()%>/section/launchUpdate.htm',
            width: 400,
            height: 100,
            selectRowMessage: '<spring:message code="common.selectRow.update"/>',
            sendParams: [ {
                refTable: 'self',
                columnIndex: 0,
                paramName: 'sectionId'
            } ]
        },
        'delete': {
            url: '<%=request.getContextPath()%>/section/doDelete.htm',
            selectRowMessage: '<spring:message code="common.selectRow.delete"/>',
            confirmMessage: '<spring:message code="section.delete.warning"/>',
            sendParams: [ {
                refTable: 'self',
                columnIndex: 0,
                paramName: 'sectionId'
            } ]
        }
    });
});
</script>

<div id="ActiveTable"></div>
<myschool:actions />
