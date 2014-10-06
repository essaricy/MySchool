<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="myschool" tagdir="/WEB-INF/tags" %>

<script type="text/javascript" charset="utf-8">
$(document).ready(function() {
  var holidaysData = [];
  
  $.ajax({
    dataType: "json",
    url: '<%=request.getContextPath()%>/holiday/jsonList.htm',
    async: false,
    success: function(respnose) {
      $(respnose.aaData).each(function () {
        var holiday_data = null;
        var holiday_month = null;
        var holiday_year = null;
        var startDate = $(this)[2];
        var endDate = $(this)[3];
        holidaysData[holidaysData.length] = {
          id: $(this)[0],
          title: $(this)[1],
          start: startDate.substring(6, 10) + '/' + startDate.substring(3, 5) + '/' + startDate.substring(0, 2),
          end: endDate.substring(6, 10) + '/' + endDate.substring(3, 5) + '/' + endDate.substring(0, 2),
          allDay: true
        };
      });
    }
  });

  var calendarSettings = {
    theme: true,
    header: {
      left: 'month,basicWeek',
      center: 'title',
      right: 'prev,next today'
    },
    editable: false,
    eventSources: [
      {
        events: holidaysData,
        backgroundColor: '#3A87AD',
        textColor: '#FFFFFF'
      }
    ]
  };

  //alert(JSON.stringify(holidaysData));
  $('#ActiveTable').activeTable({
    title: 'Holiday',
    width: "60%",
    caption: 'Holidays',
    columns: [
      '<spring:message code="holiday.holidayId"/>',
      '<spring:message code="holiday.holidayName"/>',
      '<spring:message code="common.startDate"/>',
      '<spring:message code="common.endDate"/>'
    ],
    dataTableSettings: {
      "bPaginate": true,
      "sAjaxSource": '<%=request.getContextPath()%>/holiday/jsonList.htm',
      "aoColumnDefs": [ {
        "bSearchable": false,
        "bVisible": false,
        "aTargets": [ 0 ]
      }],
      "sPaginationType": "full_numbers",
    },
    buttons: actionButtons,
    'add' : {
      title: '<spring:message code="holiday.create" />',
      url: '<%=request.getContextPath()%>/holiday/launchNew.htm',
      width: 420,
      height: 300
    },
    'update': {
      title: '<spring:message code="holiday.update" />',
      url: '<%=request.getContextPath()%>/holiday/launchUpdate.htm',
      width: 420,
      height: 300,
      selectRowMessage: '<spring:message code="common.selectRow.update"/>',
      sendParams: [
        {
          refTable: 'self',
          columnIndex: 0,
          paramName: 'holidayId'
        }
      ]
    },
    'delete': {
      url: '<%=request.getContextPath()%>/holiday/doDelete.htm',
      selectRowMessage: '<spring:message code="common.selectRow.delete"/>',
      confirmMessage: '<spring:message code="holiday.delete.warning"/>',
      sendParams: [
        {
          refTable: 'self',
          columnIndex: 0,
          paramName: 'holidayId'
        }
      ]
    },
    'calendar': {
      calendarName: 'HolidaysCalendar',
      calendarSettings: calendarSettings
    }
  });
});
</script>

<div id="ActiveTable"></div>
<myschool:actions calendar="true" />
