<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="myschool" tagdir="/WEB-INF/tags" %>

<script type="text/javascript" charset="utf-8">
var studentsSearchTable = null;
$(document).ready(function() {

    enableActionButtons(false);
    $('#CheckUncheckAll').attr('disabled' , 'true');

    studentsSearchTable = $('#StudentsTable').dataTable({
        "bPaginate": true,
        "bRetrieve": true,
        "bDestroy": true,
        "sAjaxSource": '<%=request.getContextPath()%>/dummy/jsonList.htm?sid=' + new Date().getTime(),
        "aoColumnDefs": [ {
            "bSearchable": false,
            "bVisible": false,
            "aTargets": [  5, 6, 8, 9, 10, 11, 12, 13 ]
        }],
        "aoColumns": [
          {
            "fnRender": function ( o, val ) {
              var imagePath = '<%=request.getContextPath()%>/image/getImage.htm?type=student&contentId=' + o.aData[1]
                + '&sid=<%= new java.util.Date().getTime()%>';
              var linkedImage ='<a href="javascript: showImage(' + o.aData[1] + ')"><img src="' + imagePath + '" class="thumbnail" /></a>';
              return linkedImage
                + '<input type="checkbox" value="'+ o.aData[0] + '" class="thumbnailCheck" />'; 
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
            null
        ],
        "sPaginationType": "full_numbers",
        "bJQueryUI": false
    }); 

    $('#CheckUncheckAll').click(function () {
        var checked = this.checked;
        $(studentsSearchTable.fnGetNodes()).each(function(index, row) {
            $.each(this.cells, function(columnIndex) {
                if (columnIndex == 0) {
                    var chkBox = $(this).find('input[type="checkbox"]')[0];
                    chkBox.checked = checked;
                }
            });
        });
    });

    $('#notify').click(function() {
        var numberOfRows = studentsSearchTable.fnGetData().length;
        if (numberOfRows !=0) {
            var selectedStudentIds = getIds();
            if (selectedStudentIds.length == 0) {
                $('#TemplatesTable_Outer').hide();
                error_ac('Please select at least one student to send notification.');
            } else {
                $('#TemplatesTable_Outer').show();
            }
        }
    });

    function getIds() {
        var selectedStudentIds = new Array();
        $(studentsSearchTable.fnGetNodes()).each(function(index, row) {
            $.each(this.cells, function(columnIndex) {
                if (columnIndex == 0) {
                    var chkBox = $(this).find('input[type="checkbox"]');
                    if (chkBox.is(':checked')) {
                        selectedStudentIds[selectedStudentIds.length] = $(this).find('input[type="checkbox"]').val();
                    }
                }
            });
        });
        return selectedStudentIds;
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
            "sAjaxSource": '<%=request.getContextPath()%>/notification/jsonListParents.htm?sid='+new Date().getTime(),
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

function showInstruction1(chkObjVal) {
    if (chkObjVal) {
        $('#StudentsTable').find('tfoot').find('tr').find('th').before('hi');
    }
}

function reloadStudentTable(searchString) {
    $('#TemplatesTable_Outer').hide();
    $('#CheckUncheckAll').prop('checked' , false);

    oSettings = studentsSearchTable.fnSettings();
    oSettings.sAjaxSource = '<%=request.getContextPath()%>/student/searchStudent.htm?' + searchString;

    studentsSearchTable.fnClearTable(studentsSearchTable);
    var jsonFunction = $.getJSON(oSettings.sAjaxSource, function(respnose) {
        enableActionButtons(false);
        if (respnose.StudentsData == '' || respnose.StudentsData.length == 0) {
            $('#CheckUncheckAll').attr('disabled' , 'true');
        } else {
            $('#CheckUncheckAll').prop('disabled' , false);
            
            for (var i=0; i<respnose.StudentsData.length; i++) {
                studentsSearchTable.oApi._fnAddData(oSettings, respnose.StudentsData[i]);
            }
            studentsSearchTable.fnDraw(studentsSearchTable);
            studentsSearchTable.oApi._fnProcessingDisplay(oSettings, false);
            enableActionButtons(respnose.StudentsData.length > 0);
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

function showImage(admissionNumber) {
    var studentImageDialog = dhtmlwindow.open("studentImageDialog", "iframe",
        '<%=request.getContextPath()%>/image/getImage.htm?type=student&contentId=' + admissionNumber,
        "Admission Number : " + admissionNumber, "width=400px,height=500px,resize=0,scrolling=1,center=1");
}

</script>

<%@ include file="/views/student/student_search_criteria.jsp" %>
<table align="center" width="80%" cellpadding="0" cellspacing="0" border="0" class="display" id="StudentsTable">
    <thead>
        <tr>
            <th>
                <input type="checkbox" id="CheckUncheckAll" />
            </th>
            <th><spring:message code="student.admissionNumber"/></th>
            <th><spring:message code="common.firstName"/></th>
            <th><spring:message code="common.middleName"/></th>
            <th><spring:message code="common.lastName"/></th>
            <th><spring:message code="common.gender"/></th>
            <th><spring:message code="common.dateOfBirth"/></th>
            <th><spring:message code="common.dateOfJoining"/></th>
            <th><spring:message code="common.motherTongue"/></th>
            <th><spring:message code="common.nationality"/></th>
            <th><spring:message code="common.religion"/></th>
            <th><spring:message code="common.caste"/></th>
            <th><spring:message code="common.bloodGroup"/></th>
            <th><spring:message code="class.id"/></th>
        </tr>
    </thead>
    <tbody></tbody>
    <tfoot>
        <tr>
            <th colspan="15" align="right">
                <img id="notify" src="<%=request.getContextPath()%>/images/icons/notify.png" class="iconImage" alt="Notify" />
            </th>
        </tr>
    </tfoot>
</table>
<br />
<br />
<br />
<br />

<div id="TemplatesTable"></div>
<myschool:actions add="false" update="false" delete="false" notify="true" />