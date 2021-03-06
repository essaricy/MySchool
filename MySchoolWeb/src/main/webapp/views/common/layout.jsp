<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html>
  <head>
    <title><tiles:insertAttribute name="title" ignore="true" /></title>
    <link rel="icon" type="image/gif" href="<%=request.getContextPath() %>/images/school/favicon.png" />
    <tiles:useAttribute id="css_imports" name="css_imports" classname="java.util.List" />
    <!-- TODO: Get the default theme name from myschool profile.  -->
    <c:set var="current_theme" value="${(USER_CONTEXT == null || USER_CONTEXT.userPreference == null || USER_CONTEXT.userPreference.userTheme == null)? 'SECRET_KEY' : USER_CONTEXT.userPreference.userTheme.code}" />

    <c:forEach var="css_import" items="${css_imports}">
      <c:set var="modified_css_import_name" value="${fn:replace(css_import, '${current_theme}', current_theme)}" />
      <link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/${modified_css_import_name}" />
    </c:forEach>
    <tiles:useAttribute id="script_imports" name="script_imports" classname="java.util.List" />
    <c:forEach var="script_import" items="${script_imports}">
      <script type="text/javascript" language="javascript" src="<%=request.getContextPath() %>/${script_import}"></script>
    </c:forEach>
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
        <table cellpadding="0" cellspacing="0" width="100%">
          <tr>
            <td align="center" valign="top">
              <form:form method="POST" commandName="${command_name}" enctype="multipart/form-data">
                <tiles:insertAttribute name="body" ignore="true" />
              </form:form>
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
    <div id="shadow" class="opaqueLayer"></div>
  </body>
</html>
