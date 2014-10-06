<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="myschool" tagdir="/WEB-INF/tags" %>

<script type="text/javascript" charset="utf-8">
$(document).ready(function() {
    $('#ActiveTable').activeTable({
        title: 'School',
        width: "80%",
        caption: 'Schools',
        columns: [
          '<spring:message code="school.id"/>',
          '<spring:message code="map.url"/>',
          '<spring:message code="school.name"/>',
          '<spring:message code="common.address"/>',
          '<spring:message code="common.primaryPhoneNumber"/>',
          '<spring:message code="common.secondaryPhoneNumber"/>',
          '<spring:message code="common.mobileNumber"/>',
          '<spring:message code="common.faxNumber"/>',
          '<spring:message code="common.email"/>',
          '<spring:message code="branch"/>',
          '<spring:message code="division"/>'
        ],
        dataTableSettings: {
            "bPaginate": true,
            "sAjaxSource": '<%=request.getContextPath()%>/school/jsonList.htm',
            "aoColumnDefs": [ {
                "bSearchable": false,
                "bVisible": false,
                "aTargets": [ 0, 1 ]
            }],
            "sPaginationType": "full_numbers",
        },
        buttons: actionButtons,
        'add' : {
            title: 'Create School',
            url: '<%=request.getContextPath()%>/school/launchNew.htm',
            width: 500,
            height: 440
        },
        'update': {
            title: 'Update School',
            url: '<%=request.getContextPath()%>/school/launchUpdate.htm',
            width: 500,
            height: 440,
            selectRowMessage: '<spring:message code="common.selectRow.update"/>',
            sendParams: [ {
                refTable: 'self',
                columnIndex: 0,
                paramName: 'schoolId'
            } ]
        },
        'delete': {
            url: '<%=request.getContextPath()%>/school/doDelete.htm',
            selectRowMessage: '<spring:message code="common.selectRow.delete"/>',
            confirmMessage: '<spring:message code="school.delete.warning"/>',
            sendParams: [ {
                refTable: 'self',
                columnIndex: 0,
                paramName: 'schoolId'
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
<myschool:actions map="true"/>
