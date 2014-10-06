<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="myschool" tagdir="/WEB-INF/tags" %>

<script type="text/javascript" charset="utf-8">
$(document).ready(function() {
  $('#AttendanceProfilesTable').activeTable({
    containerName: 'AttendanceProfilesTable',
    title: 'Manage Attendance Profiles',
    width: "70%",
    caption: 'Attendance Profiles',
    columns: [
      'Profile Id',
      'Profile Name',
      'Effective Academic',
      'Active',
    ],
    dataTableSettings: {
      "bPaginate": true,
      "sAjaxSource": '<%=request.getContextPath()%>/attendance/jsonList.htm',
      "aoColumnDefs": [ {
        "bSearchable": false,
        "bVisible": false,
        "aTargets": [ 0 ]
      }],
      "sPaginationType": "full_numbers",
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
      title: 'Create Attendance Profile',
      url: '<%=request.getContextPath()%>/attendance/launch.htm',
      width: $(document).width()-50,
      height: $(document).height()-50
    },
    'update': {
      title: 'Update Attendance Profile',
      url: '<%=request.getContextPath()%>/attendance/launch.htm',
      width: $(document).width()-50,
      height: $(document).height()-50,
      selectRowMessage: '<spring:message code="common.selectRow.update"/>',
      sendParams: [ {
        refTable: 'self',
        columnIndex: 0,
        paramName: 'AttendanceProfileId'
      } ]
    },
    'delete': {
      title: 'Delete Attendance Profile',
      url: '<%=request.getContextPath()%>/attendance/doDelete.htm',
      selectRowMessage: '<spring:message code="common.selectRow.delete"/>',
      confirmMessage: 'Deleting an attendance profile would also delete the associated data. This may impact assigned students/employees attendance. Are you sure?',
      sendParams: [ {
        refTable: 'self',
        columnIndex: 0,
        paramName: 'AttendanceProfileId'
      } ]
    },
  });
});

</script>

<div id="AttendanceProfilesTable"></div>
<myschool:actions />
