<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="myschool" tagdir="/WEB-INF/tags" %>

<script type="text/javascript" charset="utf-8">
$(document).ready(function() {
    $('#ActiveTable').activeTable({
        title: 'Region',
        width: "60%",
        caption: 'Regions',
        columns: [
            '<spring:message code="region.name"/>',
            '<spring:message code="state.name"/>'
        ],
        dataTableSettings: {
            "bPaginate": true,
            "sAjaxSource": '<%=request.getContextPath()%>/region/jsonList.htm',
            "aoColumnDefs": [ {
                "bSearchable": false,
                "bVisible": false,
                "aTargets": [ 0 ]
            }],
            "sPaginationType": "full_numbers",
        },
        buttons: actionButtons
    });
});
</script>

<div id="ActiveTable"></div>
<myschool:actions add="false" update="false" delete="false" />
