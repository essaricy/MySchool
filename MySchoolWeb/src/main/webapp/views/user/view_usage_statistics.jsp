<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- Full Calendar -->
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/BLUE/css/dashboard.css" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/themes/BLUE/css/dashboard-${USER_CONTEXT.userType}.css" />
<link type="text/css" rel="stylesheet" href="/demo/widgets/jquery.freewall/styles/style.css">

<script type="text/javascript" language="javascript" src="<%=request.getContextPath()%>/widgets/jquery.freewall/scripts/freewall.js"></script>

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


  $('#ActiveSessionsTable').dataTable({
	"sAjaxSource": '<%=request.getContextPath()%>/usage/getActiveSessions.htm',
    "bPaginate": true,
    "bFilter": false,
    "bInfo": false,
    "bLengthChange": false,
    "sPaginationType": "full_numbers",
    "bAutoWidth": false,
    "aoColumnDefs": [
      { 
        "bSearchable": false,
        "bVisible": false,
        "aTargets": [ 2, 3, 5, 7, 8, 9]
      }
    ],
  });

  $('#UsageCountTable').dataTable({
	"sAjaxSource": '<%=request.getContextPath()%>/usage/getUsageCount.htm',
    "bPaginate": true,
    "bFilter": false,
    "bInfo": false,
    "bLengthChange": false,
    "sPaginationType": "full_numbers",
    "bAutoWidth": false,
    "aoColumnDefs": [
      { 
        "bSearchable": false,
        "bVisible": false,
        "aTargets": [ 0 ]
      }
    ],
  });

  var LoginsByMonthGraphRef = '.size53';
  $(this).chart({
    id: 'LoginsByMonthGraph',
	url: '<%=request.getContextPath()%>/usage/getLoginsTrend.htm?TrendType=MonthOfYear&sid=' + new Date().getTime(),
	width: $('' + LoginsByMonthGraphRef).width()-20,
	height: $('' + LoginsByMonthGraphRef).height()-40,
	'X-Axis': { label: 'Month' },
	'Y-Axis': { label: '# Logins' },
	lineWidth: 2
  });

  var LoginsByDOMGraphRef = '.size53'
  $(this).chart({
    id: 'LoginsByDOMGraph',
    url: '<%=request.getContextPath()%>/usage/getLoginsTrend.htm?TrendType=DayOfMonth&sid=' + new Date().getTime(),
    width: $('' + LoginsByDOMGraphRef).width()-20,
    height: $('' + LoginsByDOMGraphRef).height()-40,
    'X-Axis': { label: 'Date' },
    'Y-Axis': {     label: '# Logins' },
    lineWidth: 2
  });

  var LoginsByDOWGraphRef = '.size53'
  $(this).chart({
    id: 'LoginsByDOWGraph',
    url: '<%=request.getContextPath()%>/usage/getLoginsTrend.htm?TrendType=DayOfWeek&sid=' + new Date().getTime(),
    width: $('' + LoginsByDOWGraphRef).width()-20,
    height: $('' + LoginsByDOWGraphRef).height()-40,
    'X-Axis': { label: 'Day' },
    'Y-Axis': {     label: '# Logins' },
    lineWidth: 2
  });

  var LoginsByHODGraphRef = '.size53'
  $(this).chart({
    id: 'LoginsByHODGraph',
    url: '<%=request.getContextPath()%>/usage/getLoginsTrend.htm?TrendType=HourOfDay&sid=' + new Date().getTime(),
    width: $('' + LoginsByHODGraphRef).width()-20,
    height: $('' + LoginsByHODGraphRef).height()-40,
    'X-Axis': { label: 'Hour' },
    'Y-Axis': {     label: '# Logins' },
    lineWidth: 2
  });

  $('#ResourceCountTable').dataTable({
    "bPaginate": true,
    "bFilter": false,
    "bInfo": false,
    "bLengthChange": false,
    "sPaginationType": "full_numbers",
    "bAutoWidth": false,
    "aoColumnDefs": [
      { 
        "bSearchable": false,
        "bVisible": false,
        "aTargets": [ ]
      }
    ],
  });

  $('#ResourceServeTimeTable').dataTable({
    "bPaginate": true,
    "bFilter": false,
    "bInfo": false,
    "bLengthChange": false,
    "sPaginationType": "full_numbers",
    "bAutoWidth": false,
    "aoColumnDefs": [
      { 
        "bSearchable": false,
        "bVisible": false,
        "aTargets": [ ]
      }
    ],
  });

});

</script>
<div id="freewall">
  <!-- Web statistics -->
  <div class="brick size94">
    <div class="TileHeader" align="left">
      <strong>Active Sessions</strong>
    </div>
    <div class="TileContent" align="left">
      <table id="ActiveSessionsTable" cellpadding="0" cellspacing="0" width="100%" class="display" border="0">
        <thead>
          <tr>
			<th>Session ID</th>
            <th>User Type</th>
			<th>User ID</th>
			<th>Ref User ID</th>
			<th>User Name</th>
			<th>Display Name</th>
            <th>Session Start Time</th>
			<th>IP Address</th>
			<th>Device</th>
            <th>Browser</th>
          </tr>
        </thead>
      </table>
    </div>
  </div>

  <div class="brick size53">
    <div class="TileHeader" align="left">
      <strong>Logins By Hour Of The Day</strong>
    </div>
    <div class="TileContent" align="left">
      <div id="LoginsByHODGraph"></div>
    </div>
  </div>
  <div class="brick size43">
    <div class="TileHeader" align="left">
      <strong>Most Logins</strong>
    </div>
    <div class="TileContent" align="left">
      <table id="UsageCountTable" cellpadding="0" cellspacing="0" width="100%" class="display" border="0">
        <thead>
          <tr>
			<th>User ID</th>
			<th>User Type</th>
			<th>User Name</th>
            <th>Number Of Visits</th>
            <th>Last Visited On</th>
          </tr>
        </thead>
      </table>
    </div>
  </div>

  <div class="brick size53">
    <div class="TileHeader" align="left">
      <strong>Logins By Day Of The Week</strong>
    </div>
    <div class="TileContent" align="left">
      <div id="LoginsByDOWGraph"></div>
    </div>
  </div>
  <div class="brick size43">
    <div class="TileHeader" align="left">
      <strong>Count of Resource Requested</strong>
    </div>
    <div class="TileContent" align="left">
      <table id="ResourceCountTable" cellpadding="0" cellspacing="0" width="100%" class="display" border="0">
        <thead>
          <tr>
            <th>Resource</th>
            <th>ADMIN</th>
			<th>EMPLOYEE</th>
            <th>STUDENT</th>
          </tr>
        </thead>
      </table>
    </div>
  </div>

  <div class="brick size53">
    <div class="TileHeader" align="left">
      <strong>Logins By Day Of The Month</strong>
    </div>
    <div class="TileContent" align="left">
      <div id="LoginsByDOMGraph"></div>
    </div>
  </div>
  <div class="brick size43">
    <div class="TileHeader" align="left">
      <strong>Service Time (Performance)</strong>
    </div>
    <div class="TileContent" align="left">
      <table id="ResourceServeTimeTable" cellpadding="0" cellspacing="0" width="100%" class="display" border="0">
        <thead>
          <tr>
            <th>Resource</th>
            <th>ADMIN</th>
			<th>EMPLOYEE</th>
            <th>STUDENT</th>
          </tr>
        </thead>
      </table>
    </div>
  </div>

  <div class="brick size53">
    <div class="TileHeader" align="left">
      <strong>Logins By Month</strong>
    </div>
    <div class="TileContent" align="left">
      <div id="LoginsByMonthGraph"></div>
    </div>
  </div>
</div>
