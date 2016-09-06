<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/widgets/jquery.jqplot/dist/jquery.jqplot.min.css" />

<script type="text/javascript" src="<%=request.getContextPath()%>/widgets/jquery.jqplot/dist/jquery.jqplot.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/widgets/jquery.jqplot/dist/plugins/jqplot.canvasTextRenderer.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/widgets/jquery.jqplot/dist/plugins/jqplot.canvasAxisLabelRenderer.min.js"></script>

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

<table width="99%" align="center" cellspacing="10" cellpadding="5">
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
