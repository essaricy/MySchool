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

  $('#MostLoginsTable').dataTable({
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

  var LoginsByMonthGraphRef = '.size43'
  $(this).chart({
    id: 'LoginsByMonthGraph',
	data: {
      "LineSeriesNames":["ADMIN","EMPLOYEE","STUDENT"],
      "LineSeries":[
        [10, 43, 24, 7, 22, 39, 33, 42, 4, 40, 43, 14],
        [11, 38, 40, 3, 41, 15, 39, 31, 27, 32, 38, 2],
        [44, 1, 33, 2, 26, 30, 29, 37, 47, 9, 0, 44]
	  ],
      "CHART_TYPE":"LINE_CHART",
      "X-Axis":{
        "Markers":["Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"]
	  }
	},
    width: $('' + LoginsByMonthGraphRef).width()-20,
    height: $('' + LoginsByMonthGraphRef).height()-40,
    'X-Axis': { label: 'Date' },
    'Y-Axis': {     label: '# Logins' },
    lineWidth: 2
  });

  var LoginsByDOMGraphRef = '.size53'
  $(this).chart({
    id: 'LoginsByDOMGraph',
	data: {
      "LineSeriesNames":["ADMIN","EMPLOYEE","STUDENT"],
      "LineSeries":[
        [37, 1, 49, 3, 5, 34, 1, 28, 26, 45, 44, 36, 40, 43, 21, 25, 0, 5, 31, 5, 39, 31, 43, 25, 27, 27, 17, 16, 12, 8, 42],
        [36, 24, 42, 11, 18, 47, 37, 35, 30, 13, 29, 11, 4, 19, 10, 31, 44, 12, 0, 34, 49, 14, 13, 12, 32, 14, 46, 7, 28, 27, 1],
        [35, 0, 3, 16, 1, 37, 46, 14, 44, 25, 18, 15, 44, 19, 18, 18, 39, 32, 35, 16, 37, 31, 4, 25, 35, 44, 28, 26, 37, 38, 27]
	  ],
      "CHART_TYPE":"LINE_CHART",
      "X-Axis":{
        "Markers":[1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31]
	  }
	},
    width: $('' + LoginsByDOMGraphRef).width()-20,
    height: $('' + LoginsByDOMGraphRef).height()-40,
    'X-Axis': { label: 'Date' },
    'Y-Axis': {     label: '# Logins' },
    lineWidth: 2
  });

  var LoginsByDOWGraphRef = '.size43'
  $(this).chart({
    id: 'LoginsByDOWGraph',
	data: {
      "LineSeriesNames":["ADMIN","EMPLOYEE","STUDENT"],
      "LineSeries":[
        [45, 34, 17, 9, 13, 16, 3],
        [17, 49, 48, 7, 24, 43, 45],
        [24, 35, 19, 23, 42, 28, 36]
	  ],
      "CHART_TYPE":"LINE_CHART",
      "X-Axis":{
        "Markers":["Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"]
	  }
	},
    width: $('' + LoginsByDOWGraphRef).width()-20,
    height: $('' + LoginsByDOWGraphRef).height()-40,
    'X-Axis': { label: 'Date' },
    'Y-Axis': {     label: '# Logins' },
    lineWidth: 2
  });

  var LoginsByHODGraphRef = '.size53'
  $(this).chart({
    id: 'LoginsByHODGraph',
	data: {
      "LineSeriesNames":["ADMIN","EMPLOYEE","STUDENT"],
      "LineSeries":[
		[37, 1, 30, 45, 33, 38, 39, 34, 34, 14, 18, 24, 28, 6, 8, 29, 3, 27, 9, 13, 19, 22, 2, 19],
        [22, 29, 24, 49, 16, 21, 7, 8, 11, 47, 49, 9, 33, 34, 20, 44, 0, 22, 6, 23, 31, 43, 22, 15],
		[41, 7, 4, 49, 31, 24, 24, 47, 2, 41, 6, 9, 17, 9, 22, 27, 35, 4, 4, 36, 8, 28, 49, 44]
	  ],
      "CHART_TYPE":"LINE_CHART",
      "X-Axis":{
        "Markers":[1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24]
	  }
	},
    width: $('' + LoginsByHODGraphRef).width()-20,
    height: $('' + LoginsByHODGraphRef).height()-40,
    'X-Axis': { label: 'Date' },
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
      <strong>Most Logins</strong>
    </div>
    <div class="TileContent" align="left">
      <table id="MostLoginsTable" cellpadding="0" cellspacing="0" width="100%" class="display" border="0">
        <thead>
          <tr>
			<th>User ID</th>
			<th>User Type</th>
			<th>User Name</th>
            <th>Login Count</th>
          </tr>
        </thead>
      </table>
    </div>
  </div>
  <div class="brick size43">
    <div class="TileHeader" align="left">
      <strong>Logins By Month</strong>
    </div>
    <div class="TileContent" align="left">
      <div id="LoginsByMonthGraph"></div>
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
      <strong>Logins By Day Of The Week</strong>
    </div>
    <div class="TileContent" align="left">
      <div id="LoginsByDOWGraph"></div>
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
</div>
