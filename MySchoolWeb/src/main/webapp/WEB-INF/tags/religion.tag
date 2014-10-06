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

<%@ attribute name="showFirstSelectOption" required="false" %>
<%@ attribute name="firstSelectOptionValue" required="false" %>
<%@ attribute name="firstSelectOptionText" required="false" %>

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

<c:if test="${empty showFirstSelectOption}">
    <c:set var="showFirstSelectOption" value="false" />
</c:if>


<c:if test="${uiControl == 'label'}">
    <c:if test="${value == 'BU'}">Budh</c:if>
    <c:if test="${value == 'CH'}">Christian</c:if>
    <c:if test="${value == 'HI'}">Hindu</c:if>
    <c:if test="${value == 'JA'}">Jain</c:if>
    <c:if test="${value == 'JU'}">Judaism</c:if>
    <c:if test="${value == 'IS'}">Muslim</c:if>
    <c:if test="${value == 'SI'}">Sikh</c:if>
    <c:if test="${value == 'ZR'}">Zoroastrian</c:if>
</c:if>

<c:if test="${uiControl == 'text'}">
    <input type="text" id="${id}" name="${name}" value="${value}" style="${style}" class="${clazz}" 
    <c:if test="${disabled == 'true'}">
        disabled 
    </c:if>
    <c:if test="${not empty maxlength}">
        maxlength="${maxlength}"
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

    <c:if test="${value == 'BU'}"><option value="BU" selected>Budh</option></c:if>
    <c:if test="${value != 'BU'}"><option value="BU">Budh</option></c:if>

    <c:if test="${value == 'CH'}"><option value="CH" selected>Christian</option></c:if>
    <c:if test="${value != 'CH'}"><option value="CH">Christian</option></c:if>

    <c:if test="${value == 'HI'}"><option value="HI" selected>Hindu</option></c:if>
    <c:if test="${value != 'HI'}"><option value="HI">Hindu</option></c:if>

    <c:if test="${value == 'JA'}"><option value="JA" selected>Jain</option></c:if>
    <c:if test="${value != 'JA'}"><option value="JA">Jain</option></c:if>

    <c:if test="${value == 'JU'}"><option value="JU" selected>Judaism</option></c:if>
    <c:if test="${value != 'JU'}"><option value="JU">Judaism</option></c:if>

    <c:if test="${value == 'MU'}"><option value="MU" selected>Muslim</option></c:if>
    <c:if test="${value != 'MU'}"><option value="MU">Muslim</option></c:if>

    <c:if test="${value == 'SI'}"><option value="SI" selected>Sikh</option></c:if>
    <c:if test="${value != 'SI'}"><option value="SI">Sikh</option></c:if>

    <c:if test="${value == 'ZR'}"><option value="ZR" selected>Zoroastrian</option></c:if>
    <c:if test="${value != 'ZR'}"><option value="ZR">Zoroastrian</option></c:if>

    </select>
</c:if>
