<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<html>
  <head>
    <title><tiles:insertAttribute name="title" ignore="true" /></title>
    <tiles:useAttribute id="css_imports" name="css_imports" classname="java.util.List" />
    <c:forEach var="css_import" items="${css_imports}">
        <link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/${css_import}" />
    </c:forEach>
    <tiles:useAttribute id="script_imports" name="script_imports" classname="java.util.List" />
    <c:forEach var="script_import" items="${script_imports}">
        <script type="text/javascript" language="javascript" src="<%=request.getContextPath() %>/${script_import}"></script>
    </c:forEach>
  </head>
  <body>
  <form method="POST">
    <tiles:insertAttribute name="body" ignore="true" />
  </form>
  </body>
</html>