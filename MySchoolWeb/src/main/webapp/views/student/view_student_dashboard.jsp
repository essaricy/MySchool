<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- Full Calendar -->
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/widgets/fullcalendar/cupertino/theme.css" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/widgets/fullcalendar/fullcalendar.css" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/BLUE/css/dashboard.css" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/BLUE/css/dashboard-${USER_CONTEXT.userType}.css" />

<script type="text/javascript" language="javascript" src="<%=request.getContextPath()%>/widgets/fullcalendar/fullcalendar.min.js"></script>

<!-- jq plot -->
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/widgets/jquery.jqplot/dist/jquery.jqplot.min.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/widgets/jquery.jqplot/dist/jquery.jqplot.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/widgets/jquery.jqplot/dist/plugins/jqplot.canvasTextRenderer.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/widgets/jquery.jqplot/dist/plugins/jqplot.canvasAxisLabelRenderer.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/widgets/jquery.jqplot/dist/plugins/jqplot.categoryAxisRenderer.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/widgets/jquery.jqplot/dist/plugins/jqplot.enhancedLegendRenderer.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/widgets/jquery.jqplot/dist/plugins/jqplot.pointLabels.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/widgets/jquery.jqplot/dist/plugins/jqplot.highlighter.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/widgets/jquery.jqplot/dist/plugins/jqplot.cursor.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/widgets/jquery.jqplot/dist/plugins/jqplot.dateAxisRenderer.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/widgets/jquery.jqplot/dist/plugins/jqplot.canvasAxisTickRenderer.min.js"></script>

<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/myschool-charts.js"></script>

<script>
var unit=100;
var colours = [/*"#FFDE00", "#6599FF",*/ "#FF9900", /*"#99FF00",*/ "#FFCC00", /*"#0099CC", /*"#CCFFCC",*/ "#66CCFF", "#99CCFF", /*"#99CC99",*/ "#CCCC99", "#666699"];

$(document).ready(function() {
  /*********************** Monthly Attendance table activation ***********************/
  $('#MonthlyAttendanceCalendar').width($('.size32').width()-20);
  $('#MonthlyAttendanceCalendar').fullCalendar({
    theme: true,
    weekMode: 'liquid',
    height: $('.size22').height()-50,
    header: {
      left: 'prev,next',
      center: 'title',
      right: 'today'
    },
    editable: false,
    eventRender: function (event, element) {
      element.tooltipster({content: event.title});
    }
  });

  $.ajax({
    dataType: "json",
    url: '<%=request.getContextPath()%>/student-dashboard/monthlyAttendanceSummary.htm',
    success: function(response) {
      if (response.DeclaredHolidays != null) {
        var calendarEvent_Holidays = new Array();
        var DeclaredHolidays = response.DeclaredHolidays;
        for (var index=0; index<DeclaredHolidays.length; index++) {
          var DeclaredHoliday = DeclaredHolidays[index];
          var startDate = DeclaredHoliday.StartDate;
          var endDate = DeclaredHoliday.EndDate;
          var calendarEvent = {
            id: 'DeclaredHoliday_' + DeclaredHoliday.HolidayId,
            title: DeclaredHoliday.HolidayName,
            start: startDate.substring(6, 10) + '/' + startDate.substring(3, 5) + '/' + startDate.substring(0, 2),
            end: endDate.substring(6, 10) + '/' + endDate.substring(3, 5) + '/' + endDate.substring(0, 2),
            allDay: true
          };
          calendarEvent_Holidays[calendarEvent_Holidays.length] = calendarEvent;
        }
        $("#MonthlyAttendanceCalendar").fullCalendar( 'addEventSource', { events: calendarEvent_Holidays, backgroundColor: '#3A87AD', textColor: '#FFFFFF' }); 
        $("#MonthlyAttendanceCalendar").fullCalendar('rerenderEvents');
      };
    }
  });

  /*********************** Latest Exam Result Table Activation ***********************/
  $.ajax({
    dataType: "json",
    url: '<%=request.getContextPath()%>/student-dashboard/getLatestExam.htm',
    async: false,
    success: function(response) {
      if (response.ExamDetails == null || !response.ExamDetails.ExamCompleted) {
        $('#LastExamResultsTable').dataTable({
          "bPaginate": false,
          "bFilter": false,
          "bInfo": false,
          "bLengthChange": false,
          "sPaginationType": "two_button"
        });
      } else {
        $('#LastExamResultsTable').width($('.size43').width());
        $('#LastExamResultsBrick_Title').text('Exam: ' + response.ExamDetails.ExamName + ' [' + response.ExamDetails.ExamDate + ']');
        $('#LastExamResultsTable').dataTable({
          "sAjaxSource": '<%=request.getContextPath()%>/student-dashboard/jsonLatestExamResult.htm',
          "bPaginate": true,
          "bFilter": false,
          "bInfo": false,
          "bLengthChange": false,
          "bAutoWidth": false,
          "sPaginationType": "full_numbers",
          "aoColumns": [
            null, { 
              // Name
              "fnRender": function ( o, val ) {
                return o.aData[1] /*+ ' ' + o.aData[2]*/ + ' ' + o.aData[3];
              }
            },{ 
              // Total Marks
              "fnRender": function ( o, val ) {
                return o.aData[4];
              }
            },{ 
              // Percentage
              "fnRender": function ( o, val ) {
                return o.aData[5];
              }
            },{ 
              // Grade
              "fnRender": function ( o, val ) {
                return o.aData[6];
              }
            }
          ],
        });
      }
    }
  });

  /*********************** Student Progress Graph Activation ***********************/
  //$.jqplot.config.enablePlugins = true;
  var ProgressGraphRefSize=".size43"
  $(this).chart({
    id: 'ProgressGraph',
    url: '<%=request.getContextPath()%>/student-dashboard/studentExamSummary.htm',
    width: $(''+ProgressGraphRefSize).width()-20,
    height: $(''+ProgressGraphRefSize).height()-40,
    'X-Axis': { label: 'Exams' },
    'Y-Axis': { label: 'Percentage' },
  });

  $('.brick').each(function (index, value) {
      $(this).css("background-color", "" + colours[colours.length * Math.random() << 0]);
  });
  var wall = new freewall("#freewall");
  wall.reset({
    selector: '.brick',
    animate: true,
    cellW: unit,
    cellH: unit,
    fixSize: 0,
    onResize: function() {
      wall.refresh();
    }
  });
  wall.fitWidth();
});
</script>


<div id="freewall" class="free-wall">
  <!-- User Profile wall -->
  <div class="brick size24">
  <c:if test="${Student != null}">
    <c:set var="StudentPersonalDetails" value="${Student.personalDetails}" />
    <c:set var="StudentDocuments" value="${Student.documentsSubmitted}" />
    <c:set var="StudentFamilyMembers" value="${Student.familyMembers}" />

    <div class="TileHeader" align="left">
      <strong>Profile Summary</strong>
      <a href="#"><img src="<%=request.getContextPath()%>/images/icons/more.png" title="Findout more..." style="cursor: pointer;"/></a>
    </div>
    <div class="TileContent" align="left">
    <table cellpadding="0" cellspacing="0" width="100%" align="center">
      <tr>
        <td colspan="2" align="center">
          <b>${StudentPersonalDetails.firstName} ${StudentPersonalDetails.lastName}</b>
        </td>
      </tr>
      <tr>
        <td colspan="2" align="center" valign="top">
          <c:if test="${USER_CONTEXT.login.userDetails.imageAccess.passportLink == null}">
            <img src="<%=request.getContextPath()%>/images/icons/student.png" class="thumbnail" style="padding-top: 5px;"/>
          </c:if>
          <c:if test="${USER_CONTEXT.login.userDetails.imageAccess.passportLink != null}">
            <img src="${USER_CONTEXT.login.userDetails.imageAccess.passportLink}" class="passport" style="padding-top: 5px;"/>
          </c:if>
        </td>
      </tr>
      <tr>
        <td valign="top">Admission#</td>
        <td valign="top"><b>${Student.admissionNumber}</b></td>
      </tr>
      <tr>
        <td valign="top">Status</td>
        <td valign="top"><b>${Student.admissionStatus.description}</b></td>
      </tr>
      <tr>
        <td valign="top">Class</td>
        <td valign="top"><b>${Student.registeredClassDto.classDto.className}</b></td>
      </tr>
      <tr>
        <td valign="top">Medium</td>
        <td valign="top"><b>${Student.registeredClassDto.medium.description}</b></td>
      </tr>
      <tr>
        <td valign="top">Section</td>
        <td valign="top"><b>${Student.registeredClassDto.section.sectionName}</b></td>
      </tr>
      <tr>
        <td valign="top">Date Of Birth</td>
        <td valign="top"><b>${StudentPersonalDetails.dateOfBirth}</b></td>
      </tr>
      <tr>
        <td valign="top">Date Of Joining</td>
        <td valign="top"><b>${Student.dateOfJoining}</b></td>
      </tr>
      <tr>
        <td valign="top">Blood Group</td>
        <td valign="top"><b>${StudentPersonalDetails.bloodGroup}</b></td>
      </tr>
    </table>
    </div>
  </c:if>
  </div>

  <!-- PUBLIC Announcements -->
  <div class="brick size32">
    <jsp:include page="/views/application/view_announcements.jsp">
      <jsp:param name="ANNOUNCEMENT_TYPE" value="PUBLIC"/>
    </jsp:include>
  </div>

  <!-- PRIVATE Announcements -->
  <div class="brick size42">
    <jsp:include page="/views/application/view_announcements.jsp">
      <jsp:param name="ANNOUNCEMENT_TYPE" value="PRIVATE"/>
      <jsp:param name="ANNOUNCEMENTS_OF" value="STUDENT"/>
    </jsp:include>
  </div>

  <!-- Montly Attendance -->
  <div class="brick size33">
    <div class="TileHeader" align="left">
      <strong>Montly Attendance</strong>
      <a href="#"><img src="<%=request.getContextPath()%>/images/icons/more.png" title="Findout more..." style="cursor: pointer;"/></a>
    </div>
    <div class="TileContent" align="left">
    <table cellpadding="0" cellspacing="0" width="100%">
      <caption></caption>
      <tbody>
        <tr>
          <td><div id="MonthlyAttendanceCalendar"></div></td>
        </tr>
      </tbody>
    </table>
    </div>
  </div>

  <!-- Student Progress Graph -->
  <div class="brick size43">
    <div class="TileHeader" align="left">
      <strong>Progress Graph</strong>
      <a href="#"><img src="<%=request.getContextPath()%>/images/icons/more.png" title="Findout more..." style="cursor: pointer;"/></a>
    </div>
    <div class="TileContent" align="left">
    <table cellpadding="0" cellspacing="0" width="100%">
      <tbody>
        <tr>
          <td>
            <div id="ProgressGraph"></div>
          </td>
        </tr>
        <tr>
          <td align="right"><a href="#">more...</a></td>
        </tr>
      </tbody>
    </table>
    </div>
  </div>

  <!-- Last Exam Results -->
  <div id="LastExamResultsBrick" class="brick size53">
    <div class="TileHeader" align="left">
      <strong id="LastExamResultsBrick_Title">Latest Exam Result</strong>
      <a href="#"><img src="<%=request.getContextPath()%>/images/icons/more.png" title="Findout more..." style="cursor: pointer;"/></a>
    </div>
    <div class="TileContent" align="left">
    <table cellpadding="0" cellspacing="0" width="100%">
      <tbody>
        <tr>
          <td>
            <table id="LastExamResultsTable" cellpadding="0" cellspacing="0" width="100%" class="display">
              <thead>
                <tr>
                  <th>Adm#</th>
                  <th>Name</th>
                  <th>Marks</th>
                  <th>%</th>
                  <th>Grade</th>
                </tr>
              </thead>
            </table>
          </td>
        </tr>
      </tbody>
    </table>
    </div>
  </div>

  <!-- Latest Gallery -->
  <div class="brick size43">
    <jsp:include page="/views/application/view_latest_gallery.jsp">
      <jsp:param name="REFERENCE_SIZE" value=".size43" />
    </jsp:include>
  </div>

</div>
