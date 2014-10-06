<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="myschool" tagdir="/WEB-INF/tags" %>

<script>
$(document).ready(function() {
    var classesActiveTableData = {
        title: 'Class',
        containerName: 'ClassesTable',
        width: "60%",
        caption: 'Classes in this School',
        selectRowMessage: 'Select a row to view details.',
        sendParams: [ {
            refTable: 'self',
            columnIndex: 0,
            paramName: 'schoolId'
        } ],
        columns: [
            '<spring:message code="class.id"/>',
            '<spring:message code="class.id"/>',
            '<spring:message code="class.name"/>',
            '<spring:message code="medium.id"/>',
            '<spring:message code="medium"/>',
            '<spring:message code="section.id"/>',
            '<spring:message code="section.name"/>'
        ],
        dataTableSettings: {
            "bPaginate": true,
            "sAjaxSource": '<%=request.getContextPath()%>/class/jsonListBySchool.htm',
            "aoColumnDefs": [ {
                "bSearchable": false,
                "bVisible": false,
                "aTargets": [ 0, 1, 3, 5]
            }],
            "sPaginationType": "full_numbers",
            "bJQueryUI": false
        },
        buttons: actionButtons,
        'add' : {
            title: 'Assign a Class to School',
            url: '<%=request.getContextPath()%>/class/launchNewRegistered.htm',
            width: 420,
            height: 300,
            sendParams: [ {
                refTable: 'SchoolsTable',
                columnIndex: 0,
                paramName: 'schoolId'
            } ]
        },
        'update': {
            title: 'Update a Class in a School',
            url: '<%=request.getContextPath()%>/class/launchUpdateRegistered.htm',
            width: 420,
            height: 300,
            selectRowMessage: '<spring:message code="common.selectRow.update"/>',
            sendParams: [ {
                refTable: 'self',
                columnIndex: 0,
                paramName: 'registeredClassId'
            },
            {
                refTable: 'SchoolsTable',
                columnIndex: 0,
                paramName: 'schoolId'
            }]
        },
        'delete': {
            url: '<%=request.getContextPath()%>/class/doDeleteRegistered.htm',
            selectRowMessage: '<spring:message code="common.selectRow.delete"/>',
            confirmMessage: '<spring:message code="classInSchool.delete.warning"/>',
            sendParams: [ {
                refTable: 'self',
                columnIndex: 0,
                paramName: 'registeredClassId'
            } ]
        }
    };

    var schoolsActiveTableData = {
        containerName: 'SchoolsTable',
        width: "60%",
        caption: 'Schools',
        selectRowMessage: 'Select a row to view details.',
        sendParams: [ {
            refTable: 'self',
            columnIndex: 0,
            paramName: 'branchId'
        } ],
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
                "aTargets": [ 0, 1, 3, 5 ]
            }],
            "sPaginationType": "full_numbers",
            "bJQueryUI": false
        },
        buttons: ['linked'],
        'linked': classesActiveTableData
    };

    $('#ActiveTable').activeTable({
        width: "60%",
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
        buttons: ['linked'],
        'linked': schoolsActiveTableData
    });
});
</script>

<div id="ActiveTable"></div>
<myschool:actions />
