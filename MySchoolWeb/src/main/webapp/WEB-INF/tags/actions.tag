<%@ tag language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ attribute name="add" required="false" %>
<%@ attribute name="update" required="false" %>
<%@ attribute name="delete" required="false" %>

<%@ attribute name="export" required="false" %>
<%@ attribute name="report" required="false" %>
<%@ attribute name="print" required="false" %>

<%@ attribute name="table" required="false" %>
<%@ attribute name="calendar" required="false" %>
<%@ attribute name="map" required="false" %>

<%@ attribute name="notify" required="false" %>

<script>
var actionButtons = [];

<c:if test="${ empty add}">
    <c:set var="add" value="true"/>
</c:if>
<c:if test="${ empty update}">
    <c:set var="update" value="true"/>
</c:if>
<c:if test="${ empty delete}">
    <c:set var="delete" value="true"/>
</c:if>
<c:if test="${ empty export}">
    <c:set var="export" value="false"/>
</c:if>
<c:if test="${ empty report}">
    <c:set var="report" value="false"/>
</c:if>
<c:if test="${ empty print}">
    <c:set var="print" value="false"/>
</c:if>
<c:if test="${ empty email}">
    <c:set var="email" value="false"/>
</c:if>
<c:if test="${ empty sms}">
    <c:set var="sms" value="false"/>
</c:if>

<c:if test="${PAGE_ACCESS != null}">
    <c:if test="${PAGE_ACCESS.create == 'true' && add == 'true'}">
        actionButtons[actionButtons.length] = 'add';
    </c:if>
    <c:if test="${PAGE_ACCESS.update == 'true' && update == 'true'}">
        actionButtons[actionButtons.length] = 'update';
    </c:if>
    <c:if test="${PAGE_ACCESS.delete == 'true' && delete == 'true'}">
        actionButtons[actionButtons.length] = 'delete';
    </c:if>
    <c:if test="${PAGE_ACCESS.update == 'true' && export == 'true'}">
        actionButtons[actionButtons.length] = 'export';
    </c:if>
</c:if>

<c:if test="${report == 'true'}">
    actionButtons[actionButtons.length] = 'report';
</c:if>
<c:if test="${print == 'true'}">
    actionButtons[actionButtons.length] = 'print';
</c:if>

<c:if test="${table == 'true'}">
    actionButtons[actionButtons.length] = 'table';
</c:if>
<c:if test="${calendar == 'true'}">
    actionButtons[actionButtons.length] = 'calendar';
</c:if>
<c:if test="${map == 'true'}">
    actionButtons[actionButtons.length] = 'map';
</c:if>

<c:if test="${notify == 'true'}">
    actionButtons[actionButtons.length] = 'notify';
</c:if>
</script>