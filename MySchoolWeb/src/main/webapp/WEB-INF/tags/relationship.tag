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

<c:if test="${uiControl == 'label'}">
	<c:if test="${value == 'F'}">Father</c:if>
	<c:if test="${value == 'M'}">Mother</c:if>
	<c:if test="${value == 'G'}">Guardian</c:if>
	<c:if test="${value == 'B'}">Brother</c:if>
	<c:if test="${value == 'S'}">Sister</c:if>
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
	<c:if test="${category == 'none' || category == 'parent'}">
		<c:if test="${value == 'F'}">
			<option value="F" selected>Father</option>
		</c:if>
		<c:if test="${value != 'F'}">
			<option value="F">Father</option>
		</c:if>
		<c:if test="${value == 'G'}">
			<option value="G" selected>Guardian</option>
		</c:if>
		<c:if test="${value != 'G'}">
			<option value="G">Guardian</option>
		</c:if>
		<c:if test="${value == 'M'}">
			<option value="M" selected>Mother</option>
		</c:if>
		<c:if test="${value != 'M'}">
			<option value="M">Mother</option>
		</c:if>
	</c:if>
	<c:if test="${category == 'none' || category == 'sibling'}">
		<c:if test="${value == 'B'}">
			<option value="B" selected>Brother</option>
		</c:if>
		<c:if test="${value != 'B'}">
			<option value="B">Brother</option>
		</c:if>
		<c:if test="${value == 'S'}">
			<option value="S" selected>Sister</option>
		</c:if>
		<c:if test="${value != 'S'}">
			<option value="S">Sister</option>
		</c:if>
	</c:if>
	<c:if test="${category == 'none' || category == 'spouse'}">
		<c:if test="${value == 'H'}">
			<option value="H" selected>Husband</option>
		</c:if>
		<c:if test="${value != 'H'}">
			<option value="H">Husband</option>
		</c:if>
		<c:if test="${value == 'W'}">
			<option value="W" selected>Wife</option>
		</c:if>
		<c:if test="${value != 'W'}">
			<option value="W">Wife</option>
		</c:if>
	</c:if>
	<c:if test="${category == 'none' || category == 'children'}">
		<c:if test="${value == 'D'}">
			<option value="D" selected>Daughter</option>
		</c:if>
		<c:if test="${value != 'D'}">
			<option value="D">Daughter</option>
		</c:if>
		<c:if test="${value == 'S'}">
			<option value="S" selected>Son</option>
		</c:if>
		<c:if test="${value != 'S'}">
			<option value="S">Son</option>
		</c:if>
	</c:if>
</c:if>
