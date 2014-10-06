<%@ tag language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ attribute name="value" required="true" %>
<%@ attribute name="id" required="false" %>
<%@ attribute name="name" required="false" %>
<%@ attribute name="checked" required="false" %>
<%@ attribute name="disabled" required="false" %>

<c:if test="${value == 'true'}">
  <c:set var="checked" value="true" />
</c:if>

<c:if test="${value == 'false'}">
  <c:set var="checked" value="false" />
</c:if>

<c:if test="${empty disabled}">
    <c:set var="disabled" value="false" />
</c:if>

<c:if test="${checked == 'true'}">
    <c:if test="${disabled == 'true'}">
        <input type="checkbox" id="${id}" name="${name}" value="${value}" checked disabled />
    </c:if>
    <c:if test="${disabled == 'false'}">
        <input type="checkbox" id="${id}" name="${name}" value="${value}" checked onClick="changeCheckboxValue(this)" />
    </c:if>
</c:if>
<c:if test="${checked == 'false'}">
    <c:if test="${disabled == 'true'}">
        <input type="checkbox" id="${id}" name="${name}" value="${value}" disabled />
    </c:if>
    <c:if test="${disabled == 'false'}">
        <input type="checkbox" id="${id}" name="${name}" value="${value}" onClick="changeCheckboxValue(this)" />
    </c:if>
</c:if>
