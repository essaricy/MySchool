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

    <c:if test="${value == 'A+'}"><option value="A+" selected>A+</option></c:if>
    <c:if test="${value != 'A+'}"><option value="A+">A+</option></c:if>

    <c:if test="${value == 'A-'}"><option value="A-" selected>A-</option></c:if>
    <c:if test="${value != 'A-'}"><option value="A-">A-</option></c:if>

    <c:if test="${value == 'B+'}"><option value="B+" selected>B+</option></c:if>
    <c:if test="${value != 'B+'}"><option value="B+">B+</option></c:if>

    <c:if test="${value == 'B-'}"><option value="B-" selected>B-</option></c:if>
    <c:if test="${value != 'B-'}"><option value="B-">B-</option></c:if>

    <c:if test="${value == 'AB+'}"><option value="AB+" selected>AB+</option></c:if>
    <c:if test="${value != 'AB+'}"><option value="AB+">AB+</option></c:if>
    
    <c:if test="${value == 'AB-'}"><option value="AB-" selected>AB-</option></c:if>
    <c:if test="${value != 'AB-'}"><option value="AB-">AB-</option></c:if>

    <c:if test="${value == 'O+'}"><option value="O+" selected>O+</option></c:if>
    <c:if test="${value != 'O+'}"><option value="O+">O+</option></c:if>

    <c:if test="${value == 'O-'}"><option value="O-" selected>O-</option></c:if>
    <c:if test="${value != 'O-'}"><option value="O-">O-</option></c:if>

    </select>
</c:if>
