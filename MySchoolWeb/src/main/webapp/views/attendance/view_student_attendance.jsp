<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/widgets/fullcalendar/cupertino/theme.css" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/widgets/fullcalendar/fullcalendar.css" />

<script type="text/javascript" src="<%=request.getContextPath()%>/widgets/fullcalendar/fullcalendar.min.js"></script>
<style type='text/css'>
#MontlyAttendanceCalendar {
  font-size: 0.8em;
}
.fc-event {
  min-height: 20px;
}
.fc-view-month .fc-event {
  font-size: 0.6em;
}
</style>
<script type='text/javascript'>
$(document).ready(function() {
  $('#MontlyAttendanceCalendar').width($('.size22').width()+20);
  //$('#MontlyAttendanceCalendar').height($('.size22').height()-50);
  var holidaysData = new Array();
  var holiday = null;
  $.ajax({
    dataType: "json",
    url: '<%=request.getContextPath()%>/holiday/jsonList.htm',
    data: {sid: new Date().getTime()},
    async: false,
    success: function(respnose) {
      $(respnose.aaData).each(function () {
        var startDate = $(this)[2];
        var endDate = $(this)[3];
        holiday = {
          id: $(this)[0],
          title: $(this)[1],
          start: startDate.substring(6, 10) + '/' + startDate.substring(3, 5) + '/' + startDate.substring(0, 2),
          end: endDate.substring(6, 10) + '/' + endDate.substring(3, 5) + '/' + endDate.substring(0, 2),
          allDay: true
        };
        holidaysData[holidaysData.length] = holiday;
      });
    }
  });

  $('#MontlyAttendanceCalendar').fullCalendar({
    theme: true,
    weekMode: 'liquid',
    height: $('.size22').height()-50,
    header: {
      left: 'prev,next,today',
      center: 'title',
      right: ''
    },
    editable: false,
    eventSources: [
      {
        events: holidaysData,
        backgroundColor: '#3A87AD',
        textColor: '#FFFFFF'
      }
    ],
    eventRender: function(event, element) {
      element.tooltipster({
        content: event.title
      });
    }
  });
});
</script>
<table cellpadding="0" cellspacing="0" width="100%" class="TileTable">
  <caption>Monthly Attendance</caption>
  <tbody>
    <tr>
      <td><div id="MontlyAttendanceCalendar"></div></td>
    </tr>
    <tr>
      <td align="right"><a href="<%=request.getContextPath()%>/gallery/launchGallery.htm">View Complete Attendance</a></td>
    </tr>
  </tbody>
</table>
