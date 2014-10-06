<%@ tag language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ attribute name="hiddenId" required="false" %>
<%@ attribute name="hiddenName" required="false" %>
<%@ attribute name="value" required="false" %>

<input type="hidden" id="${hiddenId}" name="${hiddenName}" value="${value}" />
<img id="${hiddenId}_img" src="<%=request.getContextPath()%>/images/icons/magnifier_zoom_in.png" class="iconImage" title="Select Class" />
