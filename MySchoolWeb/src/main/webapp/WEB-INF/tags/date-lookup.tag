<%@ tag language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ attribute name="id" required="false" %>
<%@ attribute name="name" required="false" %>
<%@ attribute name="mandatory" required="true" %>
<%@ attribute name="maxlength" required="false" %>
<%@ attribute name="readOnly" required="true" %>
<%@ attribute name="value" required="false" %>

<c:if test="${mandatory == 'true'}">
	<label class="mandatory">*</label>
</c:if>

<c:if test="${readOnly == 'true'}">
	${value}
</c:if>
<c:if test="${readOnly == 'false'}">
	<input type="text" id="${id}" name="${name}" class="datepicker" maxlength="${maxlength}" value="${value}" />
</c:if>
