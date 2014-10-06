<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="myschool" tagdir="/WEB-INF/tags" %>

<script type="text/javascript" charset="utf-8">
$(document).ready(function() {
    $('#ActiveTable').activeTable({
        width: "60%",
        caption: 'Reports',
        columns: [
            '<spring:message code="report.key"/>',
            '<spring:message code="report.name"/>'
        ],
        dataTableSettings: {
            "bPaginate": true,
            "sAjaxSource": '<%=request.getContextPath()%>/reports/jsonList.htm',
            "aoColumnDefs": [ {
                "bSearchable": false,
                "bVisible": false,
                "aTargets": [ 0 ]
            }],
            "sPaginationType": "full_numbers",
        },
        buttons: actionButtons,
        'report': {
            title: 'Report Criteria',
            url: '<%=request.getContextPath()%>/reports/launch.htm',
            width: $(window).width()/2,
            height: $(window).height()/2,
            selectRowMessage: 'Select a report to generate.',
            sendParams: [ {
                refTable: 'self',
                columnIndex: 0,
                paramName: 'ReportKey'
            } ]
        },
    });
});
</script>

<div id="ActiveTable"></div>
<myschool:actions add="false" update="false" delete="false" report="true" print="false" />
