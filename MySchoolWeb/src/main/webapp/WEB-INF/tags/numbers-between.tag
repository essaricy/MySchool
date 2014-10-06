<%@ tag language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ attribute name="id" required="false" %>
<%@ attribute name="startValue" required="false" %>
<%@ attribute name="endValue" required="false" %>
<%@ attribute name="clazz" required="false" %>
<%@ attribute name="style" required="false" %>
<%@ attribute name="disabled" required="false" %>

<%@ attribute name="showFirstSelectOption" required="false" %>
<%@ attribute name="firstSelectOptionValue" required="false" %>
<%@ attribute name="firstSelectOptionText" required="false" %>
<%@ attribute name="bridgeText" required="false" %>

<c:if test="${empty disabled}">
    <c:set var="disabled" value="false" />
</c:if>
<c:if test="${empty style}">
    <c:set var="style" value="" />
</c:if>
<c:if test="${empty clazz}">
    <c:set var="clazz" value="" />
</c:if>
<c:if test="${empty startValue}">
    <c:set var="startValue" value="0" />
</c:if>
<c:if test="${empty endValue}">
    <c:set var="endValue" value="1000" />
</c:if>
<c:if test="${empty bridgeText}">
    <c:set var="bridgeText" value="" />
</c:if>

<c:if test="${empty showFirstSelectOption}">
    <c:set var="showFirstSelectOption" value="false" />
</c:if>

<select id="${id}_Min" name="${name}" style="${style}" class="${clazz}"
  <c:if test="${disabled == 'true'}">disabled</c:if>> <!-- select close -->

  <c:if test="${showFirstSelectOption == 'true'}">
    <option value="${firstSelectOptionValue}">${firstSelectOptionText}</option>
  </c:if>

  <c:forEach var="number" begin="${startValue}" end="${endValue}" step="1">
    <option value="${number}">${number}</option>
  </c:forEach>
</select>
${bridgeText}
<select id="${id}_Max" name="${name}" style="${style}" class="${clazz}"
  <c:if test="${disabled == 'true'}">disabled</c:if>> <!-- select close -->

  <c:if test="${showFirstSelectOption == 'true'}">
    <option value="${firstSelectOptionValue}">${firstSelectOptionText}</option>
  </c:if>

  <c:forEach var="number" begin="${startValue}" end="${endValue}" step="1">
    <option value="${number}">${number}</option>
  </c:forEach>
</select>