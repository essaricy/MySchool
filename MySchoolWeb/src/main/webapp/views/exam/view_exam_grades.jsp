<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="myschool" tagdir="/WEB-INF/tags" %>

<script type="text/javascript" charset="utf-8">
$(document).ready(function() {
    $('#ActiveTable').activeTable({
        title: 'Exam Grade',
        width: "60%",
        caption: 'Exam Grades',
        columns: [
          '<spring:message code="examGrade.id"/>',
          '<spring:message code="examGrade.name"/>',
          '<spring:message code="examGrade.qualifyingPercentage"/>'
        ],
        dataTableSettings: {
            "bPaginate": true,
            "sAjaxSource": '<%=request.getContextPath()%>/examGrade/jsonList.htm',
            "aoColumnDefs": [ {
                "bSearchable": false,
                "bVisible": false,
                "aTargets": [ 0 ]
            }],
            "sPaginationType": "full_numbers",
        },
        buttons: actionButtons,
        'add' : {
            title: 'Create Exam Grade',
            url: '<%=request.getContextPath()%>/examGrade/launchNew.htm',
            width: 420,
            height: 300
        },
        'update': {
            title: 'Update Exam Grade',
            url: '<%=request.getContextPath()%>/examGrade/launchUpdate.htm',
            width: 420,
            height: 300,
            selectRowMessage: '<spring:message code="common.selectRow.update"/>',
            sendParams: [ {
                refTable: 'self',
                columnIndex: 0,
                paramName: 'examGradeId'
            } ]
        },
        'delete': {
            url: '<%=request.getContextPath()%>/examGrade/doDelete.htm',
            selectRowMessage: '<spring:message code="common.selectRow.delete"/>',
            confirmMessage: '<spring:message code="examGrade.delete.warning"/>',
            sendParams: [ {
                refTable: 'self',
                columnIndex: 0,
                paramName: 'examGradeId'
            } ]
        }
    });
});
</script>

<div id="ActiveTable"></div>
<myschool:actions />
