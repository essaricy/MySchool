<%@ tag language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ attribute name="id" required="false" %>
<%@ attribute name="name" required="false" %>
<%@ attribute name="uiControl" required="true" %>
<%@ attribute name="value" required="false" %>
<%@ attribute name="clazz" required="false" %>
<%@ attribute name="style" required="false" %>
<%@ attribute name="disabled" required="false" %>
<%@ attribute name="category" required="false" %>

<c:if test="${empty category}">
	<c:set var="category" value="none" />
</c:if>
<c:if test="${empty uiControl}">
	<c:set var="uiControl" value="label" />
</c:if>
<c:if test="${empty disabled}">
	<c:set var="disabled" value="false" />
</c:if>
<c:if test="${empty style}">
	<c:set var="style" value="" />
</c:if>
<c:if test="${empty clazz}">
	<c:set var="clazz" value="" />
</c:if>

<!-- This is a patch work. need to introduce codes -->
<c:if test="${value == 'Indian'}">
<c:set var="value" value="IN" />
</c:if>

<c:if test="${uiControl == 'label'}">
	<c:if test="${value == 'IN'}">Indian</c:if>
</c:if>

<c:if test="${uiControl == 'text'}">
	<input type="text" id="${id}" name="${name}" value="${value}" style="${style}" class="${clazz}" 
	<c:if test="${disabled == 'true'}">
		disabled 
	</c:if>
	/>
</c:if>

<c:if test="${uiControl == 'hidden'}">
	<input type="hidden" id="${id}" name="${name}" value="${value}" />
</c:if>

<c:if test="${uiControl == 'select'}">
	<select id="${id}" name="${name}" style="${style}" class="${clazz}" 
	<c:if test="${disabled == 'true'}">
		disabled
	</c:if>
	> <!-- select close -->

	<c:if test="${value == 'IN'}"><option value="IN" selected>Indian</option></c:if>
	<c:if test="${value != 'IN'}"><option value="IN">Indian</option></c:if>

	</select>
</c:if>
