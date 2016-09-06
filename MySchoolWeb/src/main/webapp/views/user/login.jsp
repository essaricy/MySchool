<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<!DOCTYPE html>
<html>
  <head>
    <title><tiles:insertAttribute name="title" ignore="true" /></title>

    <c:set var="current_theme" value="SECRET_KEY" />
    <tiles:useAttribute id="css_imports" name="css_imports" classname="java.util.List" />
    <c:forEach var="css_import" items="${css_imports}">
        <c:set var="modified_css_import_name" value="${fn:replace(css_import, '${current_theme}', current_theme)}" />
        <link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/${modified_css_import_name}" />
    </c:forEach>

    <tiles:useAttribute id="script_imports" name="script_imports" classname="java.util.List" />
    <c:forEach var="script_import" items="${script_imports}">
        <script type="text/javascript" language="javascript" src="<%=request.getContextPath() %>/${script_import}"></script>
    </c:forEach>

    <script>
      var unit=100;
      $(document).ready(function() {
        $(document).social({
          title: '${ORGANIZATION_PROFILE.organizationName}'
        });
        var wall = new freewall("#freewall");
        wall.reset({
          selector: '.brick',
          animate: true,
          //cellW: unit,
          //cellH: unit,
          fixSize: 0,
          onResize: function() {
            wall.refresh();
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
        <!-- Header end -->
      </div>
      <div id="PageBody">
      <!-- Body start -->
        <div id="freewall" class="free-wall">
          <!-- PUBLIC Announcements -->
          <div class="brick size64">
            <tiles:insertAttribute name="features" ignore="true" />
          </div>
          <div class="brick size32">
            <%-- <div class="TileHeader" align="left">
              <strong>LOGIN</strong>
            </div>
            <div class="TileContent" align="left">
              <tiles:insertAttribute name="login_box" ignore="true" />
            </div> --%>
            <tiles:insertAttribute name="login_box" ignore="true" />
          </div>
          <div class="brick size31">
            <div class="TileHeader" align="left">
              <strong>Share</strong>
            </div>
            <div class="TileContent" align="left">
              <div id="Socialize"></div>
            </div>
          </div>
        </div>
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
