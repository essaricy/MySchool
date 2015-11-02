<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="myschool" tagdir="/WEB-INF/tags" %>

<script>
$(document).ready(function() {
    var examsActiveTableData = {
        title: 'Exam',
        containerName: 'ExamsTable',
        width: "60%",
        caption: 'Exams in this Class',
        selectRowMessage: 'Select a row to view details.',
        sendParams: [ {
            refTable: 'self',
            columnIndex: 0,
            paramName: 'classId'
        } ],
        columns: [
            '<spring:message code="exam.examId"/>',
            '<spring:message code="exam.examName"/>',
            '<spring:message code="exam.examDate"/>',
            '<spring:message code="exam.completed"/>'
        ],
        dataTableSettings: {
            "bPaginate": true,
            "sAjaxSource": '<%=request.getContextPath()%>/exam/jsonListByClass.htm',
            "aoColumnDefs": [ {
                "bSearchable": false,
                "bVisible": false,
                "aTargets": [ 0 ]
            }],
            "sPaginationType": "full_numbers",
            "bJQueryUI": false,
            "aoColumns": [ 
                null,
                null,
                null,
                { 
                    "fnRender": function ( row, val ) {
                        if (row.aData[3] || row.aData[3] == 'true') {
                            return '<img src="<%=request.getContextPath()%>/images/icons/checked.png" class="iconImage" alt="Completed"/>';
                        } else {
                            return ' ';
                        }
                    }
                }
            ]
        },
        buttons: actionButtons,
        'add' : {
            title: 'Create Exam',
            url: '<%=request.getContextPath()%>/exam/launchExam.htm',
            width: 600,
            height: 500,
            sendParams: [ {
                refTable: 'ClassesTable',
                columnIndex: 0,
                paramName: 'classId'
            } ]
        },
        'update': {
            title: 'Update Exam',
            url: '<%=request.getContextPath()%>/exam/launchExam.htm',
            width: 600,
            height: 520,
            selectRowMessage: '<spring:message code="common.selectRow.update"/>',
            sendParams: [ {
                refTable: 'ClassesTable',
                columnIndex: 0,
                paramName: 'classId'
            }, {
                refTable: 'ExamsTable',
                columnIndex: 0,
                paramName: 'examId'
            } ]
        },
        'delete': {
            url: '<%=request.getContextPath()%>/exam/doDelete.htm',
            selectRowMessage: '<spring:message code="common.selectRow.delete"/>',
            confirmMessage: '<spring:message code="exam.delete.warning"/>',
            sendParams: [ {
                refTable: 'ExamsTable',
                columnIndex: 0,
                paramName: 'examId'
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
            "sAjaxSource": '<%=request.getContextPath()%>/class/jsonListRegistered.htm',
            "aoColumnDefs": [ {
                "bSearchable": false,
                "bVisible": false,
                "aTargets": [ 0, 1, 3, 5]
            }],
            "sPaginationType": "full_numbers",
            "bJQueryUI": false
        },
        buttons: ['linked'],
        'linked': examsActiveTableData
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
