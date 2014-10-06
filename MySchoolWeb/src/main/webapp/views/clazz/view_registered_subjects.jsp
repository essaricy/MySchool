<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="myschool" tagdir="/WEB-INF/tags" %>

<script>
$(document).ready(function() {
    var subjectsActiveTableData = {
        title: 'Subject',
        containerName: 'SubjectsTable',
        width: "60%",
        caption: 'Subjects in this Class',
        selectRowMessage: 'Select a row to view details.',
        sendParams: [ {
            refTable: 'ClassesTable',
            columnIndex: 0,
            paramName: 'classId'
        } ],
        columns: [
            '<spring:message code="subject.id"/>',
                        '<spring:message code="subject.id"/>',
            '<spring:message code="subject.name"/>'
        ],
        dataTableSettings: {
            "bPaginate": true,
            "sAjaxSource": '<%=request.getContextPath()%>/registeredSubject/jsonList.htm',
            "aoColumnDefs": [ {
                "bSearchable": false,
                "bVisible": false,
                "aTargets": [ 0, 1 ]
            }],
            "sPaginationType": "full_numbers",
            "bJQueryUI": false
        },
        buttons: actionButtons,
        'add' : {
            title: 'Create Subject',
            url: '<%=request.getContextPath()%>/registeredSubject/launchNew.htm',
            width: 400,
            height: 200,
            sendParams: [ {
                refTable: 'ClassesTable',
                columnIndex: 0,
                paramName: 'classId'
            } ]
        },
        'update': {
            title: 'Update Exam',
            url: '<%=request.getContextPath()%>/registeredSubject/launchUpdate.htm',
            width: 400,
            height: 200,
            selectRowMessage: '<spring:message code="common.selectRow.update"/>',
            sendParams: [ {
                refTable: 'ClassesTable',
                columnIndex: 0,
                paramName: 'classId'
            }, {
                refTable: 'SubjectsTable',
                columnIndex: 0,
                paramName: 'registeredSubjectId'
            } ]
        },
        'delete': {
            url: '<%=request.getContextPath()%>/registeredSubject/doDelete.htm',
            selectRowMessage: '<spring:message code="common.selectRow.delete"/>',
            confirmMessage: '<spring:message code="subject.delete.warning"/>',
            sendParams: [ {
                refTable: 'SubjectsTable',
                columnIndex: 0,
                paramName: 'RegisteredSubjectId'
            } ]
        }
    };

    var classesActiveTableData = {
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
        buttons: ['linked'],
        'linked': subjectsActiveTableData
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
        containerName: 'ActiveTable',
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
<myschool:actions add="true" update="true" delete="true" export="false" />

