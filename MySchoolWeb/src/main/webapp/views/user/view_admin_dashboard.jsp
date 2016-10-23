<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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
$(document).ready(function() {
  /*********************** Open/Pending Issues ***********************/
  $('#IssuesTable').dataTable({
    "sAjaxSource": '<%=request.getContextPath()%>/issue/jsonOpenIssues.htm',
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
        "aTargets": [ 0, 1, 3, 4, 6, 8, 9 ]
      }
    ],
  });

  /*********************** WTD Logins By User Type Graph Activation ***********************/
  var WTDLoginsByUserTypeGraphRef = '.size43'
  $(this).chart({
    id: 'WTDLoginsByUserTypeGraph',
    url: '<%=request.getContextPath()%>/admin-dashboard/getLoginsToDate.htm?ToDateType=WTD&sid=' + new Date().getTime(),
    width: $('' + WTDLoginsByUserTypeGraphRef).width()-20,
    height: $('' + WTDLoginsByUserTypeGraphRef).height()-40,
    'X-Axis': { label: 'Date' },
    'Y-Axis': {     label: '# Logins' },
    lineWidth: 2
  });

  /*********************** WTD Issues By User Type Graph Activation ***********************/
  var WTDIssuesByUserTypeGraphRef = '.size43'
  $(this).chart({
    id: 'WTDIssuesByUserTypeGraph',
    url: '<%=request.getContextPath()%>/admin-dashboard/getIssuesToDate.htm?ToDateType=WTD&sid=' + new Date().getTime(),
    width: $('' + WTDIssuesByUserTypeGraphRef).width()-20,
    height: $('' + WTDIssuesByUserTypeGraphRef).height()-40,
    'X-Axis': { label: 'Date' },
    'Y-Axis': {     label: '# Issues' },
    lineWidth: 2
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
  $('.TileHeader a img').tooltipster();
});

</script>

<div id="freewall">
  <!-- PUBLIC Announcements -->
  <div class="brick size42">
    <jsp:include page="/views/application/view_announcements.jsp">
      <jsp:param name="ANNOUNCEMENT_TYPE" value="PUBLIC"/>
    </jsp:include>
  </div>

  <!-- Web statistics -->
  <div class="brick size43">
    <div class="TileHeader" align="left">
      <strong>Logins By User Type [ Week-To-Date ]</strong>
      <a href="<%=request.getContextPath()%>/usage/list.htm"><img src="<%=request.getContextPath()%>/images/icons/more.png" title="Findout more..." /></a>
    </div>
    <div class="TileContent" align="left">
      <div id="WTDLoginsByUserTypeGraph"></div>
    </div>
  </div>

  <!-- Open/Pending Issues -->
  <div id="IssuesBrick" class="brick size54">
    <div class="TileHeader" align="left">
      <strong>Open/Pending Issues</strong>
      <a href="#"><img src="<%=request.getContextPath()%>/images/icons/more.png" title="Findout more..." style="cursor: pointer;"/></a>
    </div>
    <div class="TileContent" align="left">
      <table id="IssuesTable" cellpadding="0" cellspacing="0" width="90%" class="display" border="0">
        <thead>
          <tr>
            <th>Issue ID</th>
            <th>User Type ID</th>
            <th>User Type</th>
            <th>Status ID</th>
            <th>Status</th>
            <th>Reported Date</th>
            <th>Closed Date</th>
            <th>Subject</th>
            <th>Description</th>
            <th>Contact Email Id</th>
          </tr>
        </thead>
      </table>
    </div>
  </div>

  <!-- Web statistics -->
  <div class="brick size43">
    <div class="TileHeader" align="left">
      <strong>Issues By User Type [ Week-To-Date ]</strong>
      <a href="#"><img src="<%=request.getContextPath()%>/images/icons/more.png" title="Findout more..." style="cursor: pointer;"/></a>
    </div>
    <div class="TileContent" align="left">
      <div id="WTDIssuesByUserTypeGraph"></div>
    </div>
  </div>
</div>

<!-- ####################################### GALLERY ############################################### -->
<%@ include file="/views/common/latest_gallery_strip.jsp" %>
