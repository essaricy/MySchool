<%@ tag language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ attribute name="start" required="false" %>
<%@ attribute name="end" required="false" %>
<%@ attribute name="value" required="false" %>

<c:if test="${empty start}">
	<c:set var="start" value="1" />
</c:if>
<c:if test="${empty end}">
	<c:set var="end" value="120" />
</c:if>
<c:if test="${empty set}">
	<c:set var="value" value="1" />
</c:if>

<select class="chosen-select">
	<c:forEach var="age" begin="${start}" end="${end}">
		<c:if test="${value == age}">
			<option value="${age}" selected>${age}</option>
		</c:if>
		<c:if test="${value != age}">
			<option value="${age}">${age}</option>
		</c:if>
	</c:forEach>
</select>
