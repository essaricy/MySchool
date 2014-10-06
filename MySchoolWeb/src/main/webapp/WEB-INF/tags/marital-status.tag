<%@ tag language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ attribute name="id" required="false" %>
<%@ attribute name="name" required="false" %>
<%@ attribute name="uiControl" required="true" %>
<%@ attribute name="value" required="false" %>
<%@ attribute name="clazz" required="false" %>
<%@ attribute name="style" required="false" %>
<%@ attribute name="disabled" required="false" %>

<%@ attribute name="showFirstSelectOption" required="false" %>
<%@ attribute name="firstSelectOptionValue" required="false" %>
<%@ attribute name="firstSelectOptionText" required="false" %>

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

<c:if test="${empty showFirstSelectOption}">
    <c:set var="showFirstSelectOption" value="false" />
</c:if>

<c:if test="${uiControl == 'label'}">
${value}
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

    <c:if test="${showFirstSelectOption == 'true'}">
        <option value="${firstSelectOptionValue}">${firstSelectOptionText}</option>
    </c:if>

    <c:if test="${value == 'Not Married'}"><option value="Not Married" selected>Not Married</option></c:if>
    <c:if test="${value != 'Not Married'}"><option value="Not Married">Not Married</option></c:if>

    <c:if test="${value == 'Married'}"><option value="Married" selected>Married</option></c:if>
    <c:if test="${value != 'Married'}"><option value="Married">Married</option></c:if>

    <c:if test="${value == 'Divorced'}"><option value="Divorced" selected>Divorced</option></c:if>
    <c:if test="${value != 'Divorced'}"><option value="Divorced">Divorced</option></c:if>

    <c:if test="${value == 'Widowed'}"><option value="Widowed" selected>Widowed</option></c:if>
    <c:if test="${value != 'Widowed'}"><option value="Widowed">Widowed</option></c:if>

    <c:if test="${value == 'Separated'}"><option value="Separated" selected>Separated</option></c:if>
    <c:if test="${value != 'Separated'}"><option value="Separated">Separated</option></c:if>

    <c:if test="${value == 'Unknown'}"><option value="Unknown" selected>Unknown</option></c:if>
    <c:if test="${value != 'Unknown'}"><option value="Unknown">Unknown</option></c:if>
    </select>
</c:if>
