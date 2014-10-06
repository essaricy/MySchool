<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<!DOCTYPE html>
<html>
  <head>
    <title><tiles:insertAttribute name="title" ignore="true" /></title>
    <tiles:useAttribute id="css_imports" name="css_imports" classname="java.util.List" />
    <c:set var="current_theme" value="${USER_CONTEXT.userPreference.userTheme}" />
    <c:forEach var="css_import" items="${css_imports}">
      <c:set var="modified_css_import_name" value="${fn:replace(css_import, '${theme}', current_theme)}" />
      <link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/${modified_css_import_name}" />
    </c:forEach>
    <tiles:useAttribute id="script_imports" name="script_imports" classname="java.util.List" />
    <c:forEach var="script_import" items="${script_imports}">
      <script type="text/javascript" language="javascript" src="<%=request.getContextPath() %>/${script_import}"></script>
    </c:forEach>

    <script type="text/javascript" language="javascript" src="<%=request.getContextPath()%>/widgets/awesome-grid-master/awesome-grid.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/widgets/zweatherfeed-121/jquery.zweatherfeed.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/scripts/myschool-activeTable.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/widgets/jquery.freewall/scripts/freewall.js"></script>

    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/widgets/zweatherfeed-121/example.css" />
    <link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/widgets/awesome-grid-master/demo/css/custom.styles.css" />
    <link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/widgets/jquery.freewall/styles/style.css" />
    <link type="text/css" rel="stylesheet" href="/demo/widgets/tooltipster-master/css/tooltipster.css" />
    <link rel="stylesheet" type="text/css" hrf="<%=request.getContextPath()%>/widgets/jquery.jqplot/dist/jquery.jqplot.min.css" />

<script type="text/javascript" src="<%=request.getContextPath()%>/widgets/jquery.jqplot/dist/jquery.jqplot.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/widgets/jquery.jqplot/dist/plugins/jqplot.canvasTextRenderer.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/widgets/jquery.jqplot/dist/plugins/jqplot.canvasAxisLabelRenderer.min.js"></script>
<script type="text/javascript" language="javascript" src="/demo/widgets/tooltipster-master/js/jquery.tooltipster.js"></script>

<script>
$(document).ready(function(){
var wall = new freewall("#freewall");
wall.reset({
  selector: '.brick',
  animate: true,
  cellW: 90,
  cellH: 110,
  delay: 50,
  onResize: function() {
    wall.fitWidth();
  }
});
wall.fitWidth();
});

    </script>
  </head>
  <body>
  <div id="PageContainer">
    <div id="PageHeader">
      <!-- Header start -->
      <tiles:insertAttribute name="header" ignore="true" />
      <tiles:insertAttribute name="menu" ignore="true" />
      <!-- Header end -->
    </div>
    <div id="PageBody">
      <!-- Body start -->
      <table width="99%" class="userFormTable" align="center" cellspacing="10" cellpadding="5">
        <tr>
          <td align="center">
            <c:if test="${USER_CONTEXT.userType == 'ADMIN'}">
              <%@ include file="/views/user/view_admin_dashboard.jsp" %>
            </c:if>
            <c:if test="${USER_CONTEXT.userType == 'STUDENT'}">
              <%@ include file="/views/student/view_student_dashboard.jsp" %>
            </c:if>
            <c:if test="${USER_CONTEXT.userType == 'EMPLOYEE'}">
              <%@ include file="/views/employee/view_employee_dashboard.jsp" %>
            </c:if>
         </td>
        </tr>
      </table>
      <!-- Body end -->
    </div>
    <div id="PageFooterLinks">
      <!-- Footer Links start -->
      <%@ include file="/views/common/footer-links.jsp" %>
      <!-- Footer Links end -->
    </div>
    <div id="PageFooter">
      <!-- Footer start -->
      <tiles:insertAttribute name="footer" ignore="true" />
      <!-- Footer end -->
    </div>
  </div>
</body>
</html>
