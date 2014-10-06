<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="myschool" tagdir="/WEB-INF/tags" %>

<script type="text/javascript" charset="utf-8">
var employeesSearchTable = null;
$(document).ready(function() {
    enableActionButtons(false);

    employeesSearchTable = $('#EmployeesTable').dataTable({
        "bPaginate": true,
        "bRetrieve": true,
        "bDestroy": true,
        "sAjaxSource": '<%=request.getContextPath()%>/dummy/jsonList.htm?sid=' + new Date().getTime(),
        "aoColumnDefs": [ {
            "bSearchable": false,
            "bVisible": false,
            "aTargets": [ 4, 9, 10, 11, 12, 13, 14, 15 ]
        }],
        "aoColumns": [
          {
            "fnRender": function ( o, val ) {
              var imagePath = '<%=request.getContextPath()%>/image/getImage.htm?type=employee&contentId=' + o.aData[1]
                + '&sid=<%= new java.util.Date().getTime()%>';
                            var linkedImage ='<a href="javascript: showImage(' + o.aData[1] + ')"><img src="' + imagePath + '" class="thumbnail" /></a>';
              return linkedImage
                + '<input type="checkbox" value="'+ o.aData[0] + '" class="thumbnailCheck"/>'; 
               }
            },
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null
        ],
        "sPaginationType": "full_numbers",
        "bJQueryUI": false
    }); 

    $('#CheckUncheckAll').click(function () {
        var checked = this.checked;
        $(employeesSearchTable.fnGetNodes()).each(function(index, row) {
            $.each(this.cells, function(columnIndex) {
                if (columnIndex == 0) {
                    var chkBox = $(this).find('input[type="checkbox"]')[0];
                    chkBox.checked = checked;
                }
            });
        });
    });

    $('#notify').click(function() {
        var numberOfRows = employeesSearchTable.fnGetData().length;
        if (numberOfRows !=0) {
            var selectedEmployeeIds = getIds();
            if (selectedEmployeeIds.length == 0) {
                $('#TemplatesTable_Outer').hide();
                error_ac('Please select at least one employee to send notification.');
            } else {
                $('#TemplatesTable_Outer').show();
            }
        }
    });

    function getIds() {
        var selectedEmployeeIds = new Array();
        $(employeesSearchTable.fnGetNodes()).each(function(index, row) {
            $.each(this.cells, function(columnIndex) {
                if (columnIndex == 0) {
                    var chkBox = $(this).find('input[type="checkbox"]');
                    if (chkBox.is(':checked')) {
                        selectedEmployeeIds[selectedEmployeeIds.length] = $(this).find('input[type="checkbox"]').val();
                    }
                }
            });
        });
        return selectedEmployeeIds;
    }

    $('#TemplatesTable').activeTable({
        containerName: 'TemplatesTable',
        width: "80%",
        caption: 'Templates',
        columns: [
            'Notification Id',
            'End Point',
            'Mode',
            'Type'
        ],
        dataTableSettings: {
            "bPaginate": true,
            "sAjaxSource": '<%=request.getContextPath()%>/notification/jsonListEmployees.htm?sid='+new Date().getTime(),
            "aoColumnDefs": [ {
                "bSearchable": false,
                "bVisible": false,
                "aTargets": [ 0 ]
            }],
            "sPaginationType": "full_numbers",
            "aoColumns": [ 
                {
                    "sWidth": "20%"
                }, {
                    "sWidth": "20%"
                }, {
                    "sWidth": "20%"
                }, {
                    "sWidth": "40%",
                    "fnRender": function ( o, val ) {
                        return '<a href="javascript: showNotificationTemplate(\'' + o.aData[0] + '\')">' + o.aData[3] + '</a>'; 
                    }
                }
            ]
        },
        buttons: actionButtons,
        'notify': {
            url: '<%=request.getContextPath()%>/notification/notifyEndPoints.htm',
            selectRowMessage: 'Please select a template to send notification.',
            sendParams: [ {
                refTable: 'self',
                columnIndex: 0,
                paramName: 'notificationTemplateId'
            },{
                refTable: 'none',
                paramName: 'sendToIds',
                paramValueFunction: getIds
            }]
        }
    });
    $('#TemplatesTable_Outer').hide();
});

function reloadEmployeesTable(searchString) {
    $('#TemplatesTable_Outer').hide();

    oSettings = employeesSearchTable.fnSettings();
    oSettings.sAjaxSource = '<%=request.getContextPath()%>/employee/searchVerifiedEmployees.htm?' + searchString;

    employeesSearchTable.fnClearTable(employeesSearchTable);
    var jsonFunction = $.getJSON(oSettings.sAjaxSource, function(respnose) {
        enableActionButtons(false);
        if (respnose.EmployeesData != '') {
            for (var i=0; i<respnose.EmployeesData.length; i++) {
                employeesSearchTable.oApi._fnAddData(oSettings, respnose.EmployeesData[i]);
            }
            employeesSearchTable.fnDraw(employeesSearchTable);
            employeesSearchTable.oApi._fnProcessingDisplay(oSettings, false);
            enableActionButtons(respnose.EmployeesData.length > 0);
        }
    });
}

function enableActionButtons(enable) {
    if (enable) {
        $("#notify").show(1000);
    } else {
        $("#notify").hide(1000);
    }
}

function showNotificationTemplate(templateId) {
    var viewNotificationTemplateDialog = dhtmlwindow.open("viewNotificationTemplateDialog", "iframe",
        "<%=request.getContextPath()%>/notification/getTemplate.htm?templateId=" + templateId,
        "View Notification Template", "width=900px,height=560px,resize=0,scrolling=1,center=1");
}

function showImage(employeeNumber) {
    var viewEmployeeImageDialog = dhtmlwindow.open("viewEmployeeImageDialog", "iframe",
        '<%=request.getContextPath()%>/image/getImage.htm?type=employee&contentId=' + employeeNumber,
        "Employee Number : " + employeeNumber, "width=400px,height=500px,resize=0,scrolling=1,center=1");
}

</script>

<%@ include file="/views/employee/employee_search_criteria.jsp" %>
<table width="100%" align="center" cellpadding="0" cellspacing="0" border="0" class="display" id="EmployeesTable">
    <thead>
        <tr>
            <th><input type="checkbox" id="CheckUncheckAll" /></th>
            <th><spring:message code="employee.number"/></th>
            <th><spring:message code="common.firstName"/></th>
            <th><spring:message code="common.lastName"/></th>
            <th><spring:message code="common.gender"/></th>
            <th><spring:message code="common.dateOfBirth"/></th>
            <th><spring:message code="common.dateOfJoining"/></th>
            <th><spring:message code="employee.qualification"/></th>
            <th><spring:message code="employee.years.of.experience"/></th>
            <th><spring:message code="common.address"/></th>
            <th><spring:message code="common.mobileNumber"/></th>
            <th><spring:message code="employee.emergency.contact.number"/></th>
            <th><spring:message code="common.emailid"/></th>
            <th><spring:message code="employee.service.end.date"/></th>
            <th><spring:message code="common.active"/></th>
            <th><spring:message code="designation.id"/></th>
            <th><spring:message code="employee.designation"/></th>
        </tr>
    </thead>
    <tbody></tbody>
    <tfoot>
        <tr>
            <th colspan="17" align="right">
                <img id="notify" src="<%=request.getContextPath()%>/images/icons/notify.png" class="iconImage" alt="Notify" />
            </th>
        </tr>
    </tfoot>
</table>
<br />

<div id="TemplatesTable"></div>
<myschool:actions add="false" update="false" delete="false" notify="true" />